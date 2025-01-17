package it.unibz.inf.ontop.iq.node.normalization.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibz.inf.ontop.exception.MinorOntopInternalBugException;
import it.unibz.inf.ontop.injection.IntermediateQueryFactory;
import it.unibz.inf.ontop.iq.IQTree;
import it.unibz.inf.ontop.iq.IQTreeCache;
import it.unibz.inf.ontop.iq.UnaryIQTree;
import it.unibz.inf.ontop.iq.impl.IQTreeTools;
import it.unibz.inf.ontop.iq.node.*;
import it.unibz.inf.ontop.iq.node.normalization.ConditionSimplifier;
import it.unibz.inf.ontop.iq.node.impl.UnsatisfiableConditionException;
import it.unibz.inf.ontop.iq.node.normalization.ConditionSimplifier.ExpressionAndSubstitution;
import it.unibz.inf.ontop.iq.node.normalization.FilterNormalizer;
import it.unibz.inf.ontop.model.term.ImmutableExpression;
import it.unibz.inf.ontop.model.term.TermFactory;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.utils.ImmutableCollectors;
import it.unibz.inf.ontop.utils.VariableGenerator;

import java.util.Optional;
import java.util.stream.Stream;

@Singleton
public class FilterNormalizerImpl implements FilterNormalizer {

    private static final int MAX_NORMALIZATION_ITERATIONS = 10000;
    private final IntermediateQueryFactory iqFactory;
    private final TermFactory termFactory;
    private final ConditionSimplifier conditionSimplifier;
    private final IQTreeTools iqTreeTools;

    @Inject
    private FilterNormalizerImpl(IntermediateQueryFactory iqFactory, TermFactory termFactory,
                                 ConditionSimplifier conditionSimplifier, IQTreeTools iqTreeTools) {
        this.iqFactory = iqFactory;
        this.termFactory = termFactory;
        this.conditionSimplifier = conditionSimplifier;
        this.iqTreeTools = iqTreeTools;
    }

    /**
     * TODO: Optimization: lift direct construction and filter nodes before normalizing them
     *  (so as to reduce the recursive pressure)
     */
    @Override
    public IQTree normalizeForOptimization(FilterNode initialFilterNode, IQTree initialChild, VariableGenerator variableGenerator, IQTreeCache treeCache) {
        //Non-final
        State state = new State(initialFilterNode, initialChild)
                .normalizeChild(variableGenerator);

        for(int i=0; i < MAX_NORMALIZATION_ITERATIONS; i++) {
            State stateBeforeSimplification = state.liftBindingsAndDistinct()
                    .mergeWithChild();

            State newState = stateBeforeSimplification.simplifyAndPropagateDownConstraint(variableGenerator)
                    .normalizeChild(variableGenerator);

            // Convergence
            if (newState.child.equals(state.child))
                return newState.createNormalizedTree(variableGenerator, treeCache);

            state = newState;
        }

        throw new MinorOntopInternalBugException("Bug: FilterNode.normalizeForOptimization() did not converge after "
                + MAX_NORMALIZATION_ITERATIONS + " iterations");
    }

    /**
     * Immutable
     *
     * Normalization operations are directly done on this structure.
     *
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    protected class State {
        private final ImmutableSet<Variable> projectedVariables;
        // Parent first (should be composed of construction and distinct nodes only)
        private final ImmutableList<UnaryOperatorNode> ancestors;
        private final Optional<ImmutableExpression> condition;
        private final IQTree child;
        /**
         * Initial constructor
         */
        protected State(FilterNode initialFilterNode, IQTree initialChild) {
            projectedVariables = initialChild.getVariables();
            ancestors = ImmutableList.of();
            condition = Optional.of(initialFilterNode.getFilterCondition());
            child = initialChild;
        }

        protected State(ImmutableSet<Variable> projectedVariables, ImmutableList<UnaryOperatorNode> ancestors,
                        Optional<ImmutableExpression> condition, IQTree child) {
            this.projectedVariables = projectedVariables;
            this.ancestors = ancestors;
            this.condition = condition;
            this.child = child;
        }

        private State updateChild(IQTree newChild) {
            return new State(projectedVariables, ancestors, condition, newChild);
        }

        private State updateParentChildAndCondition(UnaryOperatorNode newParent,
                                                                       ImmutableExpression newCondition, IQTree newChild) {
            ImmutableList<UnaryOperatorNode> newAncestors = extendAncestors(newParent);
            return new State(projectedVariables, newAncestors, Optional.of(newCondition), newChild);
        }

        private State addParentRemoveConditionAndUpdateChild(UnaryOperatorNode newParent, IQTree newChild) {
            ImmutableList<UnaryOperatorNode> newAncestors = extendAncestors(newParent);
            return new State(projectedVariables, newAncestors, Optional.empty(), newChild);
        }

        private ImmutableList<UnaryOperatorNode> extendAncestors(UnaryOperatorNode newNode) {
            return Stream.concat(Stream.of(newNode), ancestors.stream()).collect(ImmutableCollectors.toList());
        }

        private State liftChildAsParent(UnaryIQTree formerChildTree) {
            ImmutableList<UnaryOperatorNode> newAncestors = extendAncestors(formerChildTree.getRootNode());
            IQTree newChild = formerChildTree.getChild();
            return new State(projectedVariables, newAncestors, condition, newChild);
        }

        private State updateConditionAndChild(ImmutableExpression newCondition, IQTree newChild) {
            return new State(projectedVariables, ancestors, Optional.of(newCondition), newChild);
        }

        private State removeConditionAndUpdateChild(IQTree newChild) {
            return new State(projectedVariables, ancestors, Optional.empty(), newChild);
        }

        private State createEmptyState() {
            return new State(projectedVariables, ImmutableList.of(), Optional.empty(),
                    iqFactory.createEmptyNode(projectedVariables));
        }

        public State normalizeChild(VariableGenerator variableGenerator) {
            return updateChild(child.normalizeForOptimization(variableGenerator));
        }

        /**
         * Returns a tree in which the "filter-level" sub-tree is declared as normalized.
         */
        public IQTree createNormalizedTree(VariableGenerator variableGenerator, IQTreeCache treeCache) {

            if (child.isDeclaredAsEmpty())
                return iqFactory.createEmptyNode(projectedVariables);

            IQTree filterLevelTree = condition
                    .map(iqFactory::createFilterNode)
                    .<IQTree>map(n -> iqFactory.createUnaryIQTree(n, child, treeCache.declareAsNormalizedForOptimizationWithEffect()))
                    .orElse(child);

            if (ancestors.isEmpty())
                return filterLevelTree;

            return iqTreeTools.createAncestorsUnaryIQTree(ancestors, filterLevelTree)
                    // Normalizes the ancestors (recursive)
                    .normalizeForOptimization(variableGenerator);
        }

        public State liftBindingsAndDistinct() {
            QueryNode childRoot = child.getRootNode();

            if (childRoot instanceof ConstructionNode) {
                ConstructionNode constructionNode = (ConstructionNode) childRoot;
                UnaryIQTree childTree = (UnaryIQTree) child;
                return condition
                        .map(e -> constructionNode.getSubstitution().apply(e))
                        .map(e -> updateParentChildAndCondition(constructionNode, e, childTree.getChild()))
                        .orElseGet(() -> liftChildAsParent(childTree))
                        // Recursive (maybe followed by a distinct)
                        .liftBindingsAndDistinct();
            }
            else if (childRoot instanceof DistinctNode) {
                UnaryIQTree childTree = (UnaryIQTree) child;
                return condition
                        .map(e -> updateParentChildAndCondition((DistinctNode) childRoot, e, childTree.getChild()))
                        .orElseGet(() -> liftChildAsParent(childTree))
                        // Recursive (may be followed by another construction node)
                        .liftBindingsAndDistinct();
            }
            else
                return this;
        }


        /**
         * Tries to merge with the child
         */
        public State mergeWithChild() {
            if (condition.isPresent()) {

                QueryNode childRoot = child.getRootNode();

                if (childRoot instanceof FilterNode) {
                    FilterNode filterChild = (FilterNode) childRoot;

                    ImmutableExpression newCondition = termFactory.getConjunction(condition.get(),
                            filterChild.getFilterCondition());

                    return updateConditionAndChild(newCondition, ((UnaryIQTree)child).getChild());
                }
                else if (childRoot instanceof InnerJoinNode) {
                    ImmutableExpression newJoiningCondition = ((InnerJoinNode) childRoot).getOptionalFilterCondition()
                            .map(c -> termFactory.getConjunction(condition.get(), c))
                            .orElse(condition.get());

                    IQTree newChild = iqFactory.createNaryIQTree(iqFactory.createInnerJoinNode(newJoiningCondition),
                            child.getChildren());
                    return removeConditionAndUpdateChild(newChild);
                }
            }
            return this;
        }

        public State simplifyAndPropagateDownConstraint(VariableGenerator variableGenerator) {
            if (!condition.isPresent()) {
                return this;
            }

            try {
                VariableNullability childVariableNullability = child.getVariableNullability();

                // TODO: also consider the constraint for simplifying the condition
                ExpressionAndSubstitution conditionSimplificationResults = conditionSimplifier.simplifyCondition(
                        condition.get(), ImmutableList.of(child), childVariableNullability);

                Optional<ImmutableExpression> downConstraint = conditionSimplifier.computeDownConstraint(Optional.empty(),
                        conditionSimplificationResults, childVariableNullability);

                IQTree newChild = Optional.of(conditionSimplificationResults.getSubstitution())
                        .filter(s -> !s.isEmpty())
                        .map(s -> child.applyDescendingSubstitution(s, downConstraint, variableGenerator))
                        .orElseGet(() -> downConstraint
                                .map(c -> child.propagateDownConstraint(c, variableGenerator))
                                .orElse(child));

                Optional<ConstructionNode> parentConstructionNode = Optional.of(conditionSimplificationResults.getSubstitution())
                        .filter(s -> !s.isEmpty())
                        .map(s -> iqFactory.createConstructionNode(child.getVariables(), s));

                return conditionSimplificationResults.getOptionalExpression()
                        .map(e -> parentConstructionNode
                                .map(p -> updateParentChildAndCondition(p, e, newChild))
                                .orElseGet(() -> updateConditionAndChild(e, newChild)))
                        .orElseGet(() -> parentConstructionNode
                                .map(p -> addParentRemoveConditionAndUpdateChild(p, newChild))
                                .orElseGet(() -> removeConditionAndUpdateChild(newChild)));
            } catch (UnsatisfiableConditionException e) {
                return createEmptyState();
            }
        }
    }
}

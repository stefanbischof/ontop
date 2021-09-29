package it.unibz.inf.ontop.iq;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import it.unibz.inf.ontop.injection.IntermediateQueryFactory;
import it.unibz.inf.ontop.iq.node.*;
import it.unibz.inf.ontop.model.atom.DistinctVariableOnlyDataAtom;
import it.unibz.inf.ontop.model.term.Variable;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 *
 */
public interface IntermediateQuery {

    QueryNode getRootNode();

    ImmutableList<QueryNode> getNodesInBottomUpOrder();

    ImmutableList<QueryNode> getNodesInTopDownOrder();

    ImmutableList<QueryNode> getChildren(QueryNode node);

    Stream<QueryNode> getChildrenStream(QueryNode node);

    Stream<QueryNode> getOtherChildrenStream(QueryNode parent, QueryNode childToOmit);

    Optional<QueryNode> getChild(QueryNode currentNode, BinaryOrderedOperatorNode.ArgumentPosition position);

    /**
     * From the parent to the oldest ancestor.
     */
    ImmutableList<QueryNode> getAncestors(QueryNode descendantNode);

    Optional<QueryNode> getParent(QueryNode node);

    Optional<QueryNode> getNextSibling(QueryNode node);

    Optional<QueryNode> getFirstChild(QueryNode node);

    /**
     * TODO: explain
     */
    Optional<BinaryOrderedOperatorNode.ArgumentPosition> getOptionalPosition(QueryNode parentNode, QueryNode child);

    Optional<BinaryOrderedOperatorNode.ArgumentPosition> getOptionalPosition(QueryNode child);

    /**
     * EXCLUDES the root of the sub-tree (currentNode).
     * TODO: find a better name
     */
    ImmutableList<QueryNode> getSubTreeNodesInTopDownOrder(QueryNode currentNode);

    Stream<IntensionalDataNode> getIntensionalNodes();

    Stream<TrueNode> getTrueNodes();

    boolean contains(QueryNode node);

    /**
     *
     * Returns itself if is a ConstructionNode or its first ancestor that is a construction node otherwise.
     */
    ConstructionNode getClosestConstructionNode(QueryNode node);

    /**
     * Returns a variable that is not used in the intermediate query.
     */
    Variable generateNewVariable();

    /**
     * Returns a variable that is not used in the intermediate query.
     *
     * The new variable always differs from the former one.
     *
     */
    Variable generateNewVariable(Variable formerVariable);


    DistinctVariableOnlyDataAtom getProjectionAtom();

    ImmutableSet<Variable> getKnownVariables();

    /**
     * Keeps the same query node objects but clones the tree edges
     * (since the latter are mutable by default).
     *
     * TODO: return an immutable Intermediate Query
     */
    IntermediateQuery createSnapshot();

    boolean hasAncestor(QueryNode descendantNode, QueryNode ancestorNode);

    /**
     * Set of variables that are returned by the sub-tree.
     */
    ImmutableSet<Variable> getVariables(QueryNode subTreeRootNode);

    UUID getVersionNumber();

    /**
     * Creates a uninitialized query builder.
     */
    IntermediateQueryBuilder newBuilder();

    IntermediateQueryFactory getFactory();

    /**
     * Minimal set of variables such that a construction node projecting exactly these variables could be inserted
     * just above this node without altering the query semantics.
     *
     * The assumption is made that the query is consistent.
     * Therefore this method should not be used for validation.
     */
    ImmutableSet<Variable> getVariablesRequiredByAncestors(QueryNode queryNode);

    IntermediateQuery getSubquery(QueryNode root, DistinctVariableOnlyDataAtom projectionAtom);
}

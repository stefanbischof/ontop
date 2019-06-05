package it.unibz.inf.ontop.iq.node;

import com.google.common.collect.ImmutableSet;
import it.unibz.inf.ontop.iq.exception.QueryNodeTransformationException;
import it.unibz.inf.ontop.iq.transform.node.HomogeneousQueryNodeTransformer;
import it.unibz.inf.ontop.model.term.ImmutableExpression;
import it.unibz.inf.ontop.model.term.ImmutableFunctionalTerm;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.substitution.ImmutableSubstitution;

import java.util.Optional;

/**
 * Combines GROUP BY, HAVING and a projection
 *
 * See IntermediateQueryFactory for creating a new instance.
 *
 */
public interface AggregationNode extends ExtendedProjectionNode {

    @Override
    ImmutableSubstitution<ImmutableFunctionalTerm> getSubstitution();

    ImmutableSet<Variable> getGroupingVariables();

    @Override
    AggregationNode clone();

    @Override
    AggregationNode acceptNodeTransformer(HomogeneousQueryNodeTransformer transformer)
            throws QueryNodeTransformationException;


    Optional<ImmutableExpression> getOptionalHavingCondition();
}

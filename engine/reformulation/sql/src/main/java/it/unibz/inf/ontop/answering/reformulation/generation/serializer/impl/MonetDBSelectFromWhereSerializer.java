package it.unibz.inf.ontop.answering.reformulation.generation.serializer.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibz.inf.ontop.answering.reformulation.generation.algebra.SelectFromWhereWithModifiers;
import it.unibz.inf.ontop.answering.reformulation.generation.dialect.SQLDialectAdapter;
import it.unibz.inf.ontop.answering.reformulation.generation.serializer.SelectFromWhereSerializer;
import it.unibz.inf.ontop.dbschema.DBParameters;
import it.unibz.inf.ontop.model.term.TermFactory;

@Singleton
public class MonetDBSelectFromWhereSerializer extends DefaultSelectFromWhereSerializer implements SelectFromWhereSerializer {

    @Inject
    private MonetDBSelectFromWhereSerializer(TermFactory termFactory,
                                             SQLDialectAdapter dialectAdapter) {
        super(new DefaultSQLTermSerializer(termFactory) {
            @Override
            protected String serializeStringConstant(String constant) {
                return "'" + constant + "'";
            }
        }, dialectAdapter);
    }

    @Override
    public SelectFromWhereSerializer.QuerySerialization serialize(SelectFromWhereWithModifiers
                                                                          selectFromWhere, DBParameters dbParameters) {
        return selectFromWhere.acceptVisitor(
                new DefaultSelectFromWhereSerializer.DefaultRelationVisitingSerializer(dbParameters.getQuotedIDFactory()) {
                    /**
                     * https://www.monetdb.org/Documentation/SQLreference/TableExpressions
                     *  [ LIMIT posint ]
                     *  [ OFFSET posint ]
                     */

                    // sqlLimit, sqlOffset, sqlTopNSQL are standard

                    @Override
                    protected String serializeLimitOffset(long limit, long offset) {
                        return String.format("LIMIT %d\nOFFSET %d", offset, limit);
                    }
                });
    }

}


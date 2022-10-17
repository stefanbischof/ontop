package it.unibz.inf.ontop.docker.lightweight.db2;

import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.docker.lightweight.AbstractLeftJoinProfTest;
import it.unibz.inf.ontop.docker.lightweight.DB2LightweightTest;
import it.unibz.inf.ontop.owlapi.OntopOWLEngine;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLConnection;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLStatement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@DB2LightweightTest
public class LeftJoinProfDB2Test extends AbstractLeftJoinProfTest {
    private static final String propertyFileName = "/prof/db2/prof-db2.properties";

    private static OntopOWLEngine REASONER;
    private static OntopOWLConnection CONNECTION;

    @BeforeAll
    public static void before() throws OWLOntologyCreationException {
        REASONER = createReasoner(owlFileName, obdaFileName, propertyFileName);
        CONNECTION = REASONER.getConnection();
    }

    @Override
    protected OntopOWLStatement createStatement() throws OWLException {
        return CONNECTION.createStatement();
    }

    @AfterAll
    public static void after() throws Exception {
        CONNECTION.close();
        REASONER.close();
    }

    @Override
    protected ImmutableList<String> getExpectedValuesAvgStudents1() {
        return ImmutableList.of("11.2000000000000000000");
    }

    @Override
    protected ImmutableList<String> getExpectedValuesAvgStudents2() {
        return ImmutableList.of("10.3333333333333333333","12.0000000000000000000", "13.0000000000000000000");
    }

    @Override
    protected ImmutableList<String> getExpectedValuesAvgStudents3() {
        return ImmutableList.of("0", "0", "0", "0", "0", "10.3333333333333333333", "12.0000000000000000000",
                "13.0000000000000000000");
    }

    @Override
    protected ImmutableList<String> getExpectedValuesDuration1() {
        return ImmutableList.of("0", "0", "0", "0", "0", "18.000", "20.000", "84.500");
    }

    @Override
    protected ImmutableList<String> getExpectedValuesMultitypedAvg1() {
        return ImmutableList.of("15.500000000000000000000000", "16.000000000000000000000000", "19.250000000000000000000000");
    }

    @Override
    protected ImmutableList<String> getExpectedValuesMultitypedSum1(){
        return ImmutableList.of("31.000", "32.000", "115.500");
    }

    /**
     * R2RMLIRISafeEncoder triggers tens of nested REPLACE due to "#" symbol in cases where a class is both mapped
     * from the db and provided as facts.
     * DB2 triggers error https://www.ibm.com/docs/en/db2-for-zos/11?topic=codes-134 in these cases
     * "A string value with a length attribute greater than 255 bytes is not allowed in a SELECT list that also
     * specifies DISTINCT."
     */
    @Disabled
    @Test
    @Override
    public void testMinStudents1() throws Exception {
        super.testMinStudents1();
    }

    @Disabled
    @Test
    @Override
    public void testMaxStudents1() throws Exception {
        super.testMaxStudents1();
    }

    @Disabled
    @Test
    @Override
    public void testSumStudents1() throws Exception {
        super.testSumStudents1();
    }

    @Disabled
    @Test
    @Override
    public void testAvgStudents1() throws Exception {
        super.testAvgStudents1();
    }

}
package it.unibz.inf.ontop.docker.lightweight.postgresql;

import it.unibz.inf.ontop.docker.lightweight.AbstractBindTestWithFunctions;
import it.unibz.inf.ontop.docker.lightweight.PostgreSQLLightweightTest;
import it.unibz.inf.ontop.owlapi.OntopOWLEngine;
import it.unibz.inf.ontop.owlapi.connection.OWLConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to test if functions on Strings and Numerics in SPARQL are working properly.
 *
 */
@PostgreSQLLightweightTest
public class BindWithFunctionsPostgreSQLTest extends AbstractBindTestWithFunctions {

    private static final String owlfile = "/books/books.owl";
    private static final String obdafile = "/books/books.obda";
    private static final String propertiesfile = "/books/postgresql/books-postgresql.properties";

    private static OntopOWLEngine REASONER;
    private static OWLConnection CONNECTION;

    public BindWithFunctionsPostgreSQLTest() throws OWLOntologyCreationException {
        super(createReasoner(owlfile, obdafile, propertiesfile));
        REASONER = getReasoner();
        CONNECTION = getConnection();
    }

    @AfterAll
    public static void after() throws Exception {
        CONNECTION.close();
        REASONER.close();
    }

    @Override
    protected List<String> getAbsExpectedValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("\"8.6\"^^xsd:decimal");
        expectedValues.add("\"5.75\"^^xsd:decimal");
        expectedValues.add("\"6.8\"^^xsd:decimal");
        expectedValues.add("\"1.50\"^^xsd:decimal");
        return expectedValues;
    }

    @Disabled("Please enable pgcrypto (CREATE EXTENSION pgcrypto")
    @Test
    @Override
    public void testHashSHA256() throws Exception {
        super.testHashSHA256();
    }

    @Disabled("PostgreSQL v14 introduces trailing 0-s to result")
    @Test
    public void testSeconds() throws Exception {
        super.testSeconds();
    }

    @Override
    protected List<String> getDivideExpectedValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("\"21.5000000000000000\"^^xsd:decimal");
        expectedValues.add("\"11.5000000000000000\"^^xsd:decimal");
        expectedValues.add("\"17.0000000000000000\"^^xsd:decimal");
        expectedValues.add("\"5.0000000000000000\"^^xsd:decimal");
        return expectedValues;
    }

    @Override
    protected List<String> getRoundExpectedValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("\"0, 43\"^^xsd:string");
        expectedValues.add("\"0, 23\"^^xsd:string");
        expectedValues.add("\"0, 34\"^^xsd:string");
        expectedValues.add("\"0, 10\"^^xsd:string");
        return expectedValues;
    }

    @Override
    protected List<String> getDatatypeExpectedValues() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("\"0.2\"^^xsd:decimal");
        expectedValues.add("\"0.25\"^^xsd:decimal");
        expectedValues.add("\"0.2\"^^xsd:decimal");
        expectedValues.add("\"0.15\"^^xsd:decimal");

        return expectedValues;
    }

    @Override
    protected List<String> getConstantIntegerDivideExpectedResults() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("\"0.50000000000000000000\"^^xsd:decimal");
        return expectedValues;
    }

}

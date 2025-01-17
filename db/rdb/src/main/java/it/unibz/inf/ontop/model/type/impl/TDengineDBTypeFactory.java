package it.unibz.inf.ontop.model.type.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import it.unibz.inf.ontop.model.type.*;
import it.unibz.inf.ontop.model.vocabulary.XSD;

import java.util.Map;

import static it.unibz.inf.ontop.model.type.DBTermType.Category.*;
import static it.unibz.inf.ontop.model.type.impl.NonStringNonNumberNonBooleanNonDatetimeDBTermType.StrictEqSupport.NOTHING;
import static it.unibz.inf.ontop.model.type.impl.NonStringNonNumberNonBooleanNonDatetimeDBTermType.StrictEqSupport.SAME_TYPE_NO_CONSTANT;

public class TDengineDBTypeFactory extends DefaultSQLDBTypeFactory {
    public static final String TIMESTAMPTZ_STR = "TIMESTAMP WITH TIME ZONE";
    public static final String TIMETZ_STR = "TIME WITH TIME ZONE";
    public static final String TIMESTAMP = "TIMESTAMP WITHOUT TIME ZONE";
    public static final String BYTEA_STR = "BYTEA";
    private static final String DEFAULT_DECIMAL_STR = "DECIMAL(38, 18)";
    public static final String UUID_STR = "UUID";
    public static final String TEXT_STR = "TEXT";
    public static final String SMALLINT_UNSIGNED = "SMALLINT UNSIGNED";
    public static final String BOOLEAN = "BOOLEAN";


    protected TDengineDBTypeFactory(Map<String, DBTermType> typeMap, ImmutableMap<DefaultTypeCode, String> defaultTypeCodeMap) {
        super(typeMap, defaultTypeCodeMap);
    }
    @AssistedInject
    protected TDengineDBTypeFactory(@Assisted TermType rootTermType, @Assisted TypeFactory typeFactory) {
        super(createTDengineTypeMap(rootTermType, typeFactory), createTDengineCodeMap());
    }

    protected static Map<String, DBTermType> createTDengineTypeMap(TermType rootTermType, TypeFactory typeFactory) {
        TermTypeAncestry rootAncestry = rootTermType.getAncestry();
        RDFDatatype xsdInteger = typeFactory.getXsdIntegerDatatype();
        RDFDatatype xsdDouble = typeFactory.getXsdDoubleDatatype();
        RDFDatatype xsdString = typeFactory.getXsdStringDatatype();
        RDFDatatype xsdBoolean = typeFactory.getXsdBooleanDatatype();

        Map<String, DBTermType> map = createDefaultSQLTypeMap(rootTermType, typeFactory);

        DatetimeDBTermType timestampTz = new DatetimeDBTermType(TIMESTAMPTZ_STR, rootTermType.getAncestry(),
                typeFactory.getXsdDatetimeDatatype());

        DBTermType timeTzType = new NonStringNonNumberNonBooleanNonDatetimeDBTermType(TIMETZ_STR, rootAncestry,
                typeFactory.getDatatype(XSD.TIME), NOTHING);

        DatetimeDBTermType timestamp = new DatetimeDBTermType(TIMESTAMP, rootTermType.getAncestry(),
                typeFactory.getXsdDatetimeDatatype(), false);

        DBTermType dateType = new DateDBTermType(DATE_STR, rootAncestry,
                typeFactory.getDatatype(XSD.DATE));

        DBTermType byteAType = new NonStringNonNumberNonBooleanNonDatetimeDBTermType(BYTEA_STR, rootAncestry,
                typeFactory.getDatatype(XSD.HEXBINARY), SAME_TYPE_NO_CONSTANT);

        DBTermType uuidType = new UUIDDBTermType(UUID_STR, rootTermType.getAncestry(), xsdString);

        NumberDBTermType defaultDecimalType = new NumberDBTermType(DEFAULT_DECIMAL_STR, rootAncestry,
                typeFactory.getXsdDecimalDatatype(), DECIMAL);

        NumberDBTermType defaultDoubleType = new NumberDBTermType(DOUBLE_PREC_STR, rootAncestry,
                typeFactory.getXsdDoubleDatatype(), FLOAT_DOUBLE);

        NumberDBTermType defaultSmallIntType = new NumberDBTermType(SMALLINT_STR, rootAncestry,
                typeFactory.getXsdIntegerDatatype(), INTEGER);

        BooleanDBTermType defaultBooleanType = new BooleanDBTermType(BOOLEAN_STR, rootAncestry,
                typeFactory.getXsdBooleanDatatype());

        DBTermType textType = new StringDBTermType(TEXT_STR, "VARCHAR(500)", rootAncestry, typeFactory.getXsdStringDatatype());

        /*  TODO-SCAFFOLD: Add to or modify the type map:
         *-------------------------------------------------------------------
         *      map.put("TYPE_NAME", DBTermType);
         */

        map.put(TIMESTAMPTZ_STR, timestampTz);
        map.put(TIMETZ_STR, timeTzType);
        map.put(DATE_STR, dateType);
        map.put(UUID_STR, uuidType);
        map.put(BYTEA_STR, byteAType);
        map.put(DEFAULT_DECIMAL_STR, defaultDecimalType);
        map.put(TEXT_STR, textType);
        map.put(TIMESTAMP, timestamp);
        map.put(DOUBLE_PREC_STR, defaultDoubleType);
        map.put(SMALLINT_STR, defaultSmallIntType);
        map.put(BOOLEAN_STR, defaultBooleanType);

        return map;
    }

    protected static ImmutableMap<DefaultTypeCode, String> createTDengineCodeMap() {
        Map<DefaultTypeCode, String> map = createDefaultSQLCodeMap();
        map.put(DefaultTypeCode.DOUBLE, DOUBLE_STR);
        //map.put(DefaultTypeCode.DATETIMESTAMP, TIMESTAMPTZ_STR);
        map.put(DefaultTypeCode.HEXBINARY, BYTEA_STR);
        map.put(DefaultTypeCode.STRING, TEXT_STR);
        map.put(DefaultTypeCode.DECIMAL, DEFAULT_DECIMAL_STR);

        /*  TODO-SCAFFOLD: Add to or modify the code map:
         *-------------------------------------------------------------------
         *      map.put(DefaultTypeCode.CODE, "TYPE_NAME");
         */


        map.put(DefaultTypeCode.DECIMAL, SMALLINT_UNSIGNED);
        map.put(DefaultTypeCode.BOOLEAN, BOOLEAN);
        map.put(DefaultTypeCode.DATETIMESTAMP, TIMESTAMP);

        return ImmutableMap.copyOf(map);
    }




    //TODO-SCAFFOLD change any of these flags, if applicable
    @Override
    public boolean supportsDBGeometryType() {
        return false;
    }

    //TODO-SCAFFOLD change any of these flags, if applicable
    @Override
    public boolean supportsDBGeographyType() {
        return false;
    }

    //TODO-SCAFFOLD change any of these flags, if applicable
    @Override
    public boolean supportsDBDistanceSphere() {
        return false;
    }

    //TODO-SCAFFOLD change any of these flags, if applicable
    @Override
    public boolean supportsJson() {
        return false;
    }

    //TODO-SCAFFOLD change any of these flags, if applicable
    @Override
    public boolean supportsArrayType() {
        return false;
    }
}

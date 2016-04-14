package com.company.ContextualAnalysis;

/**
 * Created by joklost on 12-04-16.
 */
public class TypeConsts {
    public static final int errorType = -1;
    public static final int noType = -2;
    public static final int integerType = 1;
    public static final int stringType = 2;
    public static final int booleanType = 3;
    public static final int voidType = 4;
    public static final int nullType = 5;
    public static final int decimalType = 6;
    public static final int objectTypeDescriptor = 7;
    public static final int arrayTypeDescriptor = 8;
    public static final int functionType = 9;
    public static final int simulationType = 10;
    public static final int simulationStepType = 11;
    public static final int groupType = 12;
    public static final int platoonType = 13;
    public static final int forceType = 14;
    public static final int coordType = 15;
    public static final int soldierType = 16;
    public static final int barrierType = 17;
    public static final int vectorType = 18;
    public static final int terrainType = 19;

    public static final int coordOperator = 100;
    public static final int plusOperator = 101;
    public static final int minusOperator = 102;
    public static final int multiplicationOperator = 103;
    public static final int divisionOperator = 104;
    public static final int moduluOperator = 105;
    public static final int andOperator = 106;
    public static final int orOperator = 107;
    public static final int logicEqualsOperator = 108;
    public static final int lessThanOperator = 109;
    public static final int greaterThanOperator = 110;
    public static final int lessThanEqualsOperator = 111;
    public static final int greaterThanEqualsOperator = 112;

    public static final int notOperator = 113;
    public static final int plusplusOperator = 114;
    public static final int minusminusOperator = 115;
    public static final int unaryMinusOperator = 116;

    public static final int[] arithmeticBinaryOperators  = new int[] { plusOperator, minusOperator, multiplicationOperator, divisionOperator, moduluOperator };
    public static final int[] booleanBinaryOperators     = new int[] { andOperator, orOperator };
    public static final int[] booleanComparisonOperators = new int[] {logicEqualsOperator, lessThanOperator, greaterThanOperator, lessThanEqualsOperator, greaterThanEqualsOperator };

    public static final int[] arithmeticUnaryOperators   = new int[] {  plusplusOperator, minusminusOperator, unaryMinusOperator };
}



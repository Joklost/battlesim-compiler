package com.company.ContextualAnalysis;

import java.util.HashMap;

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
    public static final int listType = 20;
    public static final int array1DType = 21;
    public static final int array2DType = 22;

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

    public static final int equalsAssignmentOperator = 130;
    public static final int plusEqualsAssignmentOperator = 131;
    public static final int minusEqualsAssignmentOperator = 132;
    public static final int modEqualsAssignmentOperator = 133;
    public static final int multEqualsAssignmentOperator = 134;
    public static final int divEqualsAssignmentOperator = 135;

    public static final int toIterator = 140;
    public static final int downToIterator = 141;






    private static HashMap<Integer, String> typeNames = new HashMap<>();
    static {
        typeNames.put(errorType, "ERROR");
        typeNames.put(noType, "No Type"); // skal nok laves om
        typeNames.put(integerType, "Integer");
        typeNames.put(stringType, "String");
        typeNames.put(booleanType, "Boolean");
        typeNames.put(voidType, "Void");
        typeNames.put(nullType, "NULL");
        typeNames.put(decimalType, "Decimal");
        typeNames.put(objectTypeDescriptor, "Object");
        typeNames.put(arrayTypeDescriptor, "Array");
        typeNames.put(functionType, "Function");
        typeNames.put(simulationType, "Simulation");
        typeNames.put(simulationStepType, "Simulation Step");
        typeNames.put(groupType, "Group");
        typeNames.put(platoonType, "Platoon");
        typeNames.put(forceType, "Force");
        typeNames.put(coordType, "Coord");
        typeNames.put(soldierType, "Soldier");
        typeNames.put(barrierType, "Barrier");
        typeNames.put(vectorType, "Vector");
        typeNames.put(terrainType, "Terrain");
        typeNames.put(listType, "List");
        typeNames.put(array1DType, "1D Array");
        typeNames.put(array2DType, "2D Array");


        typeNames.put(coordOperator, "( , )");
        typeNames.put(plusOperator, "+");
        typeNames.put(minusOperator, "-");
        typeNames.put(multiplicationOperator, "*");
        typeNames.put(divisionOperator, "/");
        typeNames.put(moduluOperator, "%");
        typeNames.put(andOperator, "AND");
        typeNames.put(orOperator, "OR");
        typeNames.put(logicEqualsOperator, "==");
        typeNames.put(lessThanOperator, "<");
        typeNames.put(greaterThanOperator, ">");
        typeNames.put(lessThanEqualsOperator, "<=");
        typeNames.put(greaterThanEqualsOperator, ">=");

        typeNames.put(notOperator, "NOT");
        typeNames.put(plusplusOperator, "++");
        typeNames.put(minusminusOperator, "--");
        typeNames.put(unaryMinusOperator, "-");

        typeNames.put(equalsAssignmentOperator, "=");
        typeNames.put(plusEqualsAssignmentOperator, "+=");
        typeNames.put(minusEqualsAssignmentOperator, "-=");
        typeNames.put(modEqualsAssignmentOperator, "%=");
        typeNames.put(multEqualsAssignmentOperator, "*=");
        typeNames.put(divEqualsAssignmentOperator, "/=");

        typeNames.put(toIterator, "To");
        typeNames.put(downToIterator, "DownTo");


    }

    public static String getTypeName(int type) {
        return typeNames.get(type);
    }



}



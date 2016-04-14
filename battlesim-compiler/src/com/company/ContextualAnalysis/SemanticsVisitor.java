package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;

import static com.company.AST.SymbolTable.SymbolTable.retrieveSymbol;
import static com.company.ContextualAnalysis.TypeConsts.*;

/**
 * Created by joklost on 12-04-16.
 */
public class SemanticsVisitor extends Visitor implements VisitorInterface {

    private void errorNoDeclaration(String var) {
        System.err.println(var + " has not been declared.");
    }
    private void error(String s) {
        System.err.println(s);
    }

    private boolean assignable(int target, int expression) {
        return true;    // skal laves
    }

    private int binaryResultType(int operator, int leftType, int rightType) {

        if (operator == coordOperator) {
            if (leftType == integerType && rightType == integerType) return coordType;
        }

        if (operator == plusOperator) {
            if (leftType == stringType && rightType == stringType) return stringType;
            if ((leftType == integerType && rightType == stringType) || (leftType == stringType && rightType == integerType)) return stringType;
            if ((leftType == decimalType && rightType == stringType) || (leftType == stringType && rightType == decimalType)) return stringType;
            if ((leftType == booleanType && rightType == stringType) || (leftType == stringType && rightType == booleanType)) return stringType;
        }

        if (arrayContains(arithmeticBinaryOperators, operator)) {
            if (leftType == integerType && rightType == integerType) return integerType;
            if ((leftType == integerType && rightType == decimalType) || (leftType == decimalType && rightType == integerType)) return decimalType;
            if (leftType == decimalType && rightType == decimalType) return decimalType;
        }

        if (arrayContains(booleanBinaryOperators, operator)) {
            if (leftType == booleanType && rightType == booleanType) return booleanType;
        }

        if (operator == logicEqualsOperator) {
            if (leftType == stringType && rightType == stringType) return booleanType;
        }

        if (arrayContains(booleanComparisonOperators, operator)) {
            if (leftType == integerType && rightType == integerType) return booleanType;
            if (leftType == decimalType && rightType == decimalType) return booleanType;
            if ((leftType == integerType && rightType == decimalType) || (leftType == decimalType && rightType == integerType)) return booleanType;
        }

        // No proper binary result type was found
        return errorType;
    }

    private int unaryResultType(int operator, int type) {

        if (operator == notOperator) {
            if (type == booleanType) return booleanType;
        }

        if (arrayContains(arithmeticUnaryOperators, operator)) {
            if (type == integerType) return integerType;
            if (type == decimalType) return decimalType;
        }

        return errorType;
    }

    private boolean arrayContains(int[] array, int value) {
        for (int index: array) {
            if (index == value) return true;
        }

        return false;
    }


    public void defaultVisit(Object o) {

    }

    public void visit(Start s) {

    }

    public void visit(DclBlock db) {

    }

    public void visit(SimBlock s) {

    }

    public void visit(SimStep s) {

    }

    public void visit(Simulation s) {

    }

    public void visit(Interrupts is) {

    }

    public void visit(FunctionDcl fd) {

    }

    public void visit(Param p) {

    }

    public void visit(Program p) {

    }

    public void visit(Dcl ds) {

    }

    public void visit(Assignment as) {
        LHSSemanticsVisitor lhsSemanticsVisitor = new LHSSemanticsVisitor();
        as.targetName.accept(lhsSemanticsVisitor);
        as.expression.accept(this);

        if (assignable(as.targetName.type, as.expression.type)) {
            as.type = as.targetName.type;
        } else {
            error("Right hand side expression not assignable to left hand side name at line " + as.getLineNumber());
            as.type = errorType;
        }

    }


    public void visit(WhileStmt ws) {

    }

    public void visit(ForeachStmt fes) {

    }

    public void visit(ForStmt fs) {

    }

    public void visit(IfStmt i) {

    }

    public void visit(ElseIfStmt e) {

    }

    public void visit(ElseStmt e) {

    }

    public void visit(EndIfStmt e) {

    }

    public void visit(SwitchStmt ss) {

    }

    public void visit(SwitchCase sc) {

    }

    public void visit(SwitchDef sd) {

    }

    public void visit(ReturnExpr r) {

    }

    public void visit(Return r) {

    }

    public void visit(FunctionCallStmt fcs) {

    }

////////////////////////////////////////////////////////////////////////////////
    public void visit(PlusExpr pe) {
        // binary
        pe.leftExpr.accept(this);
        pe.rightExpr.accept(this);
        pe.type = binaryResultType(plusOperator, pe.leftExpr.type, pe.rightExpr.type);
    }



    public void visit(MinusExpr me) {
        // binary
        me.leftExpr.accept(this);
        me.rightExpr.accept(this);
        me.type = binaryResultType(minusOperator, me.leftExpr.type, me.rightExpr.type);
    }

    public void visit(MultExpr me) {
        // binary
        me.leftExpr.accept(this);
        me.rightExpr.accept(this);
        me.type = binaryResultType(multiplicationOperator, me.leftExpr.type, me.rightExpr.type);
    }

    public void visit(DivExpr de) {
        // binary
        de.leftExpr.accept(this);
        de.rightExpr.accept(this);
        de.type = binaryResultType(divisionOperator, de.leftExpr.type, de.rightExpr.type);
    }

    public void visit(ModExpr me) {
        // binary
        me.leftExpr.accept(this);
        me.rightExpr.accept(this);
        me.type = binaryResultType(moduluOperator, me.leftExpr.type, me.rightExpr.type);
    }

    public void visit(AndExpr ae) {
        // binary
        ae.leftExpr.accept(this);
        ae.rightExpr.accept(this);
        ae.type = binaryResultType(andOperator, ae.leftExpr.type, ae.rightExpr.type);    }

    public void visit(OrExpr oe) {
        // binary
        oe.leftExpr.accept(this);
        oe.rightExpr.accept(this);
        oe.type = binaryResultType(orOperator, oe.leftExpr.type, oe.rightExpr.type);
    }

    public void visit(LogicEqualsExpr le) {
        // binary
        le.leftExpr.accept(this);
        le.rightExpr.accept(this);
        le.type = binaryResultType(logicEqualsOperator, le.leftExpr.type, le.rightExpr.type);
    }

    public void visit(LessThanExpr le) {
        // binary
        le.leftExpr.accept(this);
        le.rightExpr.accept(this);
        le.type = binaryResultType(lessThanOperator, le.leftExpr.type, le.rightExpr.type);
    }

    public void visit(GreaterThanExpr ge) {
        // binary
        ge.leftExpr.accept(this);
        ge.rightExpr.accept(this);
        ge.type = binaryResultType(greaterThanOperator, ge.leftExpr.type, ge.rightExpr.type);
    }

    public void visit(LessThanEqualsExpr le) {
        // binary
        le.leftExpr.accept(this);
        le.rightExpr.accept(this);
        le.type = binaryResultType(lessThanEqualsOperator, le.leftExpr.type, le.rightExpr.type);
    }

    public void visit(GreaterThanEqualsExpr ge) {
        // binary
        ge.leftExpr.accept(this);
        ge.rightExpr.accept(this);
        ge.type = binaryResultType(greaterThanEqualsOperator, ge.leftExpr.type, ge.rightExpr.type);
    }

    public void visit(NotExpr ne) {
        // unary
        ne.expression.accept(this);
        ne.type = unaryResultType(notOperator, ne.expression.type);
    }

    public void visit(UnMinusExpr ue) {
        // unary
        ue.expression.accept(this);
        ue.type = unaryResultType(unaryMinusOperator, ue.expression.type);
    }

    public void visit(PlusPlusExpr pe) {
        // unary
        pe.expression.accept(this);
        pe.type = unaryResultType(plusplusOperator, pe.expression.type);
    }

    public void visit(MinusMinusExpr me) {
        // unary
        me.expression.accept(this);
        me.type = unaryResultType(minusminusOperator, me.expression.type);
    }

    public void visit(FunctionCallExpr fe) {
        fe.functionCall.accept(this);
        fe.type = fe.functionCall.type;
    }

    public void visit(ObjectIdExpr ne) {
        ne.objectName.accept(this);
        ne.type = ne.objectName.type;
    }

    public void visit(StdLiteralExpr se) {
        se.stdLiteral.accept(this);
        se.type = se.stdLiteral.type;
    }

    public void visit(CoordExpr ce) {
        ce.expression1.accept(this);
        ce.expression2.accept(this);

        ce.type = binaryResultType(coordOperator, ce.expression1.type, ce.expression2.type);
    }

////////////////////////////////////////////////////////////////////////////////

    public void visit(EqualsOp eo) {

    }

    public void visit(PlusEqualsOp po) {

    }

    public void visit(MinusEqualsOp mo) {

    }

    public void visit(ModEqualsOp mo) {

    }

    public void visit(MultEqualsOp mo) {

    }

    public void visit(DivEqualsOp deo) {

    }

    public void visit(FunctionCall f) {

    }

    public void visit(ToIterator ti) {

    }

    public void visit(DownToIterator di) {

    }

    public void visit(VariableObjectId vi) {
        vi.objectName.accept(this);
        vi.type = vi.objectName.type;
    }

    public void visit(VariableStdLiteral vs) {
        vs.stdLiteral.accept(this);
        vs.type = vs.stdLiteral.type;
    }

    public void visit(DecimalLiteral dl) {
        dl.type = decimalType;
    }

    public void visit(StringLiteral sl) {
        sl.type = stringType;
    }

    public void visit(BooleanLiteral bl) {
        bl.type = booleanType;
    }

    public void visit(IntegerLiteral il) {
        il.type = integerType;
    }

    public void visit(NullLiteral nl) {
        nl.type = nullType;
    }

    public void visit(Array1D a) {
        //TODO typeVisitor -> arrayDefining
    }

    public void visit(Array2D a) {
        //TODO typeVisitor -> arrayDefining
    }

    public void visit(ListOf l) {
        //TODO typeVisitor -> arrayDefining
    }

    public void visit(Decimal d) {
        d.type = decimalType;
    }

    public void visit(StringT s) {
        s.type = stringType;
    }

    public void visit(BooleanT b) {
        b.type = booleanType;
    }

    public void visit(Group g) {
        g.type = groupType;
    }

    public void visit(Platoon p) {
        p.type = platoonType;
    }

    public void visit(Force f) {
        f.type = forceType;
    }

    public void visit(Coord c) {
        c.type = coordType;
    }

    public void visit(Soldier s) {
        s.type = soldierType;
    }

    public void visit(Barrier b) {
        b.type = barrierType;
    }

    public void visit(VectorT v) {
        v.type = vectorType;
    }

    public void visit(IntegerT i) {
        i.type = integerType;
    }

    public void visit(VoidT v) {
        v.type = voidType;
    }

    public void visit(Terrain t) {
        t.type = terrainType;
    }

    public void visit(Array1DReferencing a) {
        a.arrayName.accept(this);
        a.indexExpr.accept(this);

        if (a.arrayName.type == errorType) {
            a.type = errorType;
        } else {
            if (a.arrayName.type != arrayTypeDescriptor) {
                error(a.arrayName.name + " is not an array."); // [] måske
                a.type = errorType;
            } else {
                a.type = a.arrayName.type; // denne type er bestemt at typen af elementer i arrayet
            }
        }
        if (a.indexExpr.type != errorType && a.indexExpr.type != integerType) {
            error("Index expression is not an integer. ArrayName: " + a.arrayName.name);
        }
    }

    public void visit(Array2DReferencing a) {
        a.arrayName.accept(this);
        a.firstIndexExpr.accept(this);
        a.secondIndexExpr.accept(this);

        if (a.arrayName.type == errorType) {
            a.type = errorType;
        } else {
            if (a.arrayName.type != arrayTypeDescriptor) {
                error(a.arrayName.name + " is not an array."); // [] måske
                a.type = errorType;
            } else {
                a.type = a.arrayName.type; // denne type er bestemt at typen af elementer i arrayet
            }
        }
        if (a.firstIndexExpr.type != errorType && a.firstIndexExpr.type != integerType) {
            error("First index expression is not an integer. ArrayName: " + a.arrayName.name);
        }

        if (a.secondIndexExpr.type != errorType && a.secondIndexExpr.type != integerType) {
            error("Second index expression is not an integer. ArrayName: " + a.arrayName.name);
        }
    }

    public void visit(ObjectReferencing o) {
        o.objectName.accept(this);
        if (o.objectName.type == errorType) {
            o.type = errorType;
        } else {
            if (o.objectName.type != objectTypeDescriptor) {
                error(o.objectName.name + " does not specify an object.");
                o.type = errorType;
            } else {

                ObjectTypeDescriptor obj = (ObjectTypeDescriptor)o.objectName.typeDescriptor;
                SymbolTable st = obj.fields;;
                // Skulle helst ikke smide en exception.. - jkj

                ASTNode def = st.retrieveSymbol(o.fieldName.name);
                if (def == null) {
                    error(o.fieldName.name + " is not a field of " + o.objectName.name);
                    o.type = errorType;
                } else {
                    o.type = def.type;
                }
            }
        }
    }


    // tror denne er som den skal være - jkj
    public void visit(Identifier id) {
        id.type = errorType;
        id.def = null;
        ASTNode newDef = retrieveSymbol(id.name);
        if (newDef == null) {
            errorNoDeclaration(id.name);
        } else {
            id.def = newDef;
            id.type = newDef.type;
        }
    }

}

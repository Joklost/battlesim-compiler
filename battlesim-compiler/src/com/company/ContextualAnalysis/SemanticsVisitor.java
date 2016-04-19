package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;

import java.util.ArrayList;
import java.util.List;

import static com.company.AST.SymbolTable.SymbolTable.*;
import static com.company.ContextualAnalysis.TypeConsts.*;
import static com.company.Main.currentFile;

/**
 * Created by joklost on 12-04-16.
 */
public class SemanticsVisitor extends Visitor implements VisitorInterface {

    public boolean errorFound = false;

    private void errorNoDeclaration(int ln, String var) {
        error(ln, var + " has not been declared.");
    }
    protected void error(int ln, String s) {
        errorFound = true;
        System.err.println(currentFile + ": line " + ln + ": error: " + s);
    }

    FunctionDcl currentFunction;

    public SemanticsVisitor() {
        createStdLib();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void defaultVisit(Object o) {
        error(-1, "Did not find visit method for " + o.getClass());
    }

    public void visit(Start s) {
        s.dclBlock.accept(this);
        s.simBlock.accept(this);

        for (int i = 0; i < s.functionDclList1.size(); i++) {
            s.functionDclList1.elementAt(i).accept(this);
        }

        s.program.accept(this);

        for (int i = 0; i < s.functionDclList2.size(); i++) {
            s.functionDclList2.elementAt(i).accept(this);
        }
    }

    public void visit(DclBlock db) {
        for (int i = 0; i < db.stmtLists.size(); i++) {
            //System.out.println(db.stmtLists.elementAt(i).getClass());
            db.stmtLists.elementAt(i).accept(this);
        }
    }

    public void visit(SimBlock s) {
        for (int i = 0; i < s.simulationList.size(); i++) {
            s.simulationList.accept(this);
        }
    }

    public void visit(SimStep s) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        s.accept(topDeclVisitor);
    }

    public void visit(Simulation s) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        s.accept(topDeclVisitor);
    }

    public void visit(Interrupts is) {
        openScope();

        for (int i = 0; i < is.stmtList.size(); i++) {
            is.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }

    public void visit(FunctionDcl fd) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        fd.accept(topDeclVisitor);
    }

    public void visit(Param p) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        p.accept(topDeclVisitor);
    }

    public void visit(Program p) {
        openScope();

        for (int i = 0; i < p.stmtList.size(); i++) {
            p.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }

    public void visit(Dcl ds) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        ds.accept(topDeclVisitor);
    }

    public void visit(Assignment as) {
        LHSSemanticsVisitor lhsSemanticsVisitor = new LHSSemanticsVisitor();
        as.targetName.accept(lhsSemanticsVisitor);
        as.expression.accept(this);

        if (assignable(as.targetName.type, as.expression.type)) {       // TODO
            as.type = as.targetName.type;
        } else {
            error(as.getLineNumber(), "Unable to assign " + getTypeName(as.expression.type) + " to " + getTypeName(as.targetName.type) + ".");
            as.type = errorType;
        }

    }

    public void visit(WhileStmt ws) {
        ws.condition.accept(this);

        openScope();

        for (int i = 0; i < ws.stmtList.size(); i++) {
            ws.stmtList.elementAt(i).accept(this);
        }

        closeScope();

        if (ws.condition != null) {
            checkBoolean(ws.condition);
        }
    }

    public void visit(ForeachStmt fes) {
        //fes.typeName.type == fes.objectName.type
        openScope();
        fes.typeName.accept(this);

        fes.localName.type = fes.typeName.type;

        enterSymbol(fes.localName.name, fes.localName);
        fes.localName.accept(this);

        fes.objectName.accept(this);

        for (int i = 0; i < fes.stmtList.size(); i++) {
            fes.stmtList.elementAt(i).accept(this);
        }

        closeScope();

        if ((fes.objectName.type != listType) && (fes.objectName.type != array1DType)) {
            error(fes.getLineNumber(), "Object in foreach statement is not a list or 1d array.");
            fes.type = errorType;
        } else {
            ASTNode def = retrieveSymbol(fes.objectName.name);

            if (def != null) {
                int type = noType;
                if (def.type == listType) {
                    type = ((ListOf) def).typeName.type;
                } else if (def.type == array1DType) {
                    type = ((Array1D) def).typeName.type;
                }

                if (fes.typeName.type != type) {
                    error(fes.getLineNumber(), "Mismatched types in foreach statement.");
                    fes.type = errorType;
                }
            } else {
                errorNoDeclaration(fes.getLineNumber(), fes.objectName.name);
                fes.type = errorType;
            }

        }
    }

    // der kan ikke deklareres typer i en for loop
    // så scopet åbnes efter toExpr - jkj
    public void visit(ForStmt fs) {
        fs.initialExpr.accept(this);
        fs.forIterator.accept(this);
        fs.toExpr.accept(this);

        openScope();

        for (int i = 0; i < fs.stmtList.size(); i++) {
            fs.stmtList.elementAt(i).accept(this);
        }

        closeScope();

        if (fs.initialExpr.type != integerType) {
            error(fs.getLineNumber(), "Inital expression in for statement has to be an integer.");
            fs.type = errorType;
        }
        if (fs.toExpr.type != integerType) {
            error(fs.getLineNumber(), "To expression in for statement has to be an integer.");
            fs.type = errorType;
        }
    }

    public void visit(IfStmt i) {
        i.condition.accept(this);

        openScope();

        for (int k = 0; k < i.stmtList.size(); k++) {
            i.stmtList.elementAt(k).accept(this);
        }

        closeScope();

        i.elseStmt.accept(this);

        checkBoolean(i.condition);
    }

    public void visit(ElseIfStmt e) {
        e.condition.accept(this);

        openScope();

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        closeScope();

        e.elifStmt.accept(this);

        checkBoolean(e.condition);
    }

    public void visit(ElseStmt e) {
        openScope();

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i);
        }

        closeScope();
    }

    public void visit(EndIfStmt e) {
        // den er tom - jkj
    }

    public void visit(SwitchStmt ss) {
        ss.variable.accept(this);
        if (ss.variable.type != errorType && !assignable(integerType, ss.variable.type) && !assignable(stringType, ss.variable.type)) {
            error(ss.getLineNumber(), "Illegal type for Switch statement.");
            ss.type = errorType;
        } else {
            ss.type = ss.variable.type;
        }

        for (int i = 0; i < ss.switchCaseList.size(); i++) {
            ss.switchCaseList.elementAt(i).accept(this);
            if (ss.switchCaseList.elementAt(i).type != ss.type) {
                error(ss.switchCaseList.elementAt(i).getLineNumber(), "Mismatched types in switch case.");
                ss.type = errorType;
            }
        }


        // Nedenstående kan gøres smartere... -jkj
        // line number skal med når der er dupes
        if (ss.type == stringType) {
            List<String> labelList = gatherLabelsString(ss.switchCaseList);
            checkForDupesString(labelList);
        } else if (ss.type == integerType) {
            List<Integer> labelList = gatherLabelsInteger(ss.switchCaseList);
            checkForDupesInt(labelList);
        }

    }

    public void visit(SwitchCase sc) {
        sc.label.accept(this);
        sc.type = sc.label.type;

        openScope();

        for (int i = 0; i < sc.stmtList.size(); i++) {
            sc.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }

    public void visit(SwitchDef sd) {
        openScope();

        for (int i = 0; i < sd.stmtList.size(); i++) {
            sd.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }

    public void visit(ReturnExpr r) {
        r.returnVal.accept(this);
        FunctionDcl curFunction = currentFunction;
        if (r.returnVal != null) {
            if (curFunction == null) {
                error(r.getLineNumber(), "A value may not be returned from outside of a function.");
            } else {
                if (!assignable(curFunction.returnType.type, r.returnVal.type)) {
                    error(r.getLineNumber(), "Illegal return value.");
                }
            }
        } else {
            if (curFunction != null && curFunction.returnType.type != voidType) {
                error(curFunction.getLineNumber(), "A value must be returned from function.");
            }
        }
    }

    public void visit(Return r) {
        FunctionDcl curFunction = currentFunction;
        if (curFunction != null && curFunction.returnType.type != voidType) {
            error(curFunction.getLineNumber(), "A value must be returned from function.");
        }
    }

    public void visit(FunctionCallStmt fcs) {
        fcs.functionCall.accept(this);
    }

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
        // coord operator = (e1, e2)
        ce.type = binaryResultType(coordOperator, ce.expression1.type, ce.expression2.type);
    }

    public void visit(EqualsOp eo) {
        eo.type = equalsAssignmentOperator;
    }

    public void visit(PlusEqualsOp po) {
        po.type = plusEqualsAssignmentOperator;
    }

    public void visit(MinusEqualsOp mo) {
        mo.type = minusEqualsAssignmentOperator;
    }

    public void visit(ModEqualsOp mo) {
        mo.type = modEqualsAssignmentOperator;
    }

    public void visit(MultEqualsOp mo) {
        mo.type = multEqualsAssignmentOperator;
    }

    public void visit(DivEqualsOp deo) {
        deo.type = divEqualsAssignmentOperator;
    }

    public void visit(FunctionCall f) {
        //f.type = returnType
        f.objectName.accept(this);

        int[] argTypeList = new int[f.argumentList.size()];
        for (int i = 0; i < f.argumentList.size(); i++) {
            f.argumentList.elementAt(i).accept(this);
            argTypeList[i] = f.argumentList.elementAt(i).type;
        }

        if (f.objectName.type == errorType) {
            error(f.getLineNumber(), "Function " + f.objectName.name + " has not been declared.");
            f.type = errorType;
        } else {
            ASTNode def = retrieveSymbol(f.objectName.name);
            FunctionDcl function = null;

            if (!(def instanceof FunctionDcl)) {
                error(f.getLineNumber(), "Function " + f.objectName.name + " is not a function.");
                f.type = errorType;
            } else {
                function = (FunctionDcl) def;

                if (f.argumentList.size() != function.paramList.size()) {
                    error(f.getLineNumber(), "Found " + f.argumentList.size() + " function arguments. Needs " + function.paramList.size() + ".");
                    f.type = errorType;
                } else {
                    boolean misMatchedArgsFound = false;
                    for (int i = 0; i < function.paramList.size(); i++) {
                        if (f.argumentList.elementAt(i).type != function.paramList.elementAt(i).type) {
                            error(f.getLineNumber(), "Argument " + (i + 1) + " in function call of " + f.objectName.name + " does not match with parameter. Type: " + getTypeName(f.argumentList.elementAt(i).type));
                            f.type = errorType;
                            misMatchedArgsFound = true;
                        }
                    }

                    if (!misMatchedArgsFound) {
                        f.type = function.returnType.type;
                    }
                }
            }
        }
    }

    public void visit(ToIterator ti) {
        ti.type = toIterator;
    }

    public void visit(DownToIterator di) {
        di.type = downToIterator;
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
        TypeVisitor typeVisitor = new TypeVisitor();
        a.accept(typeVisitor);
    }

    public void visit(Array2D a) {
        TypeVisitor typeVisitor = new TypeVisitor();
        a.accept(typeVisitor);
    }

    public void visit(ListOf l) {
        TypeVisitor typeVisitor = new TypeVisitor();
        l.accept(typeVisitor);
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
            if (a.arrayName.type != array1DType) {
                error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                a.type = errorType;
            } else {
                if (!declaredLocally(a.arrayName.name)) {
                    errorNoDeclaration(a.getLineNumber(), a.arrayName.name);
                    a.type = errorType;
                } else {
                    ASTNode def = retrieveSymbol(a.arrayName.name);
                    if (def instanceof Array1D) {
                        a.type = ((Array1D) def).typeName.type;
                    } else {
                        error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                        a.type = errorType;
                    }
                }
            }
        }
        if (a.indexExpr.type != errorType && a.indexExpr.type != integerType) {
            error(a.getLineNumber(), "Index expression is not of type integer.");
        }
    }

    public void visit(Array2DReferencing a) {
        a.arrayName.accept(this);
        a.firstIndexExpr.accept(this);
        a.secondIndexExpr.accept(this);

        if (a.arrayName.type == errorType) {
            a.type = errorType;
        } else {
            if (a.arrayName.type != array2DType) {
                error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                a.type = errorType;
            } else {
                if (!declaredLocally(a.arrayName.name)) {
                    errorNoDeclaration(a.getLineNumber(), a.arrayName.name);
                    a.type = errorType;
                } else {
                    ASTNode def = retrieveSymbol(a.arrayName.name);
                    if (def instanceof Array2D) {
                        a.type = ((Array2D) def).typeName.type;
                    } else {
                        error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                        a.type = errorType;
                    }
                }
            }
        }
        if (a.firstIndexExpr.type != errorType && a.firstIndexExpr.type != integerType) {
            error(a.getLineNumber(), "First index expression is not an integer. ArrayName: " + a.arrayName.name);
        }

        if (a.secondIndexExpr.type != errorType && a.secondIndexExpr.type != integerType) {
            error(a.getLineNumber(), "Second index expression is not an integer. ArrayName: " + a.arrayName.name);
        }
    }

    public void visit(ObjectReferencing o) {
        o.objectName.accept(this);
        if (o.objectName.type == errorType) {
            o.type = errorType;
        } else {
            if (o.objectName.type != objectTypeDescriptor) {
                error(o.getLineNumber(), o.objectName.name + " does not specify an object.");
                o.type = errorType;
            } else {

                ObjectTypeDescriptor obj = (ObjectTypeDescriptor)o.objectName.typeDescriptor;
                SymbolTable st = obj.fields;;
                // Skulle helst ikke smide en exception.. - jkj

                ASTNode def = st.retrieveSymbol(o.fieldName.name);
                if (def == null) {
                    error(o.getLineNumber(), o.fieldName.name + " is not a field of " + o.objectName.name);
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


            errorNoDeclaration(id.getLineNumber(), id.name);
        } else {

            id.def = newDef;
            id.type = newDef.type;

        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean assignable(int target, int expression) {
        if (target == expression) return true;
        if (target == decimalType && expression == integerType) return true;
        if (target == stringType && expression == nullType) return true;
        if ((target == array1DType || target == array2DType || target == listType) && expression == nullType) return true;
        if ((target == groupType || target == platoonType || target == forceType || target == coordType || target == soldierType || target == barrierType || target == vectorType || target == terrainType) && expression == nullType) return true;

        return false;    // skal laves
    }

    private int binaryResultType(int operator, int leftType, int rightType) {

        if (operator == coordOperator) {
            if (leftType == integerType && rightType == integerType) return coordType;
        }

        // string arithmetics
        if (operator == plusOperator) {
            if (leftType == stringType && rightType == stringType) return stringType;
            if ((leftType == integerType && rightType == stringType) || (leftType == stringType && rightType == integerType)) return stringType;
            if ((leftType == decimalType && rightType == stringType) || (leftType == stringType && rightType == decimalType)) return stringType;
            if ((leftType == booleanType && rightType == stringType) || (leftType == stringType && rightType == booleanType)) return stringType;
            if ((leftType == stringType && rightType == coordType)   || (leftType == coordType && rightType == stringType))   return stringType;
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
            if (leftType == coordType && rightType == coordType) return booleanType;
            if (leftType == booleanType && rightType == booleanType) return booleanType;
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

    private void checkBoolean(ASTNode n) {
        if (n.type != booleanType) {
            error(n.getLineNumber(), "A Boolean type is required.");
        }
    }


    // tror den er som den skal være.. -jkj
    private List<String> gatherLabelsString(SwitchCaseList switchCaseList) {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < switchCaseList.size(); i++) {
            labels.add(switchCaseList.elementAt(i).label.value.toString());
        }
        return labels;
    }

    private List<Integer> gatherLabelsInteger(SwitchCaseList switchCaseList) {
        List<Integer> labels = new ArrayList<>();
        for (int i = 0; i < switchCaseList.size(); i++) {
            labels.add((Integer)switchCaseList.elementAt(i).label.value);
        }
        return labels;
    }


    private void checkForDupesString(List<String> labelList) {
        if (labelList.size() > 1) {
            List<String> dupeList = new ArrayList<>();
            for (String s : labelList) {
                if (dupeList.contains(s)) {
                    System.err.println("Duplicate case label: " + s);
                }
                dupeList.add(s);
            }
        }
    }

    private void checkForDupesInt(List<Integer> labelList) {
        if (labelList.size() > 1) {
            List<Integer> dupeList = new ArrayList<>();
            for (Integer i : labelList) {
                if (dupeList.contains(i)) {
                    System.err.println(("Duplicate case label: " + i.toString()));
                }
                dupeList.add(i);
            }
        }
    }


    private void createStdLib() {
        TypeIdentifier tPrint = new VoidT(-1);
        tPrint.type = voidType;
        Identifier iPrint = new Identifier("Print", -1);
        ParamList pPrint = new ParamList(-1);
        Param ppPrint = new Param(new Identifier("s", -1), new StringT(-1), -1);
        ppPrint.type = stringType;
        pPrint.addElement(ppPrint);
        FunctionDcl fPrint = new FunctionDcl(tPrint, iPrint, pPrint, null, -1);
        enterSymbol("Print", fPrint);
    }


}

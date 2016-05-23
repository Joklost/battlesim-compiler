package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;
import com.company.Main;

import java.util.ArrayList;
import java.util.List;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.*;
import static com.company.Main.currentFile;

/**
 * Created by joklost on 12-04-16.
 */
public class SemanticsVisitor extends Visitor implements VisitorInterface {

    private void errorNoDeclaration(int ln, String var) {
        error(ln, var + " has not been declared.");
    }
    protected void error(int ln, String s) {
        Main.errorFound = true;
        System.err.println(currentFile + ": line " + ln + ": error: " + s);
    }

    FunctionDcl currentFunction;

    public SemanticsVisitor() {
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void defaultVisit(Object o) {
        error(-1, "Did not find visit method for " + o.getClass());
    }

    public void visit(Start s) {

        for (int i = 0; i < s.typeDeclarationList.size(); i++) {
            s.typeDeclarationList.elementAt(i).accept(this);
        }

        for (int i = 0; i < s.functionDclList.size(); i++) {
            s.functionDclList.elementAt(i).accept(this);
        }

        s.dclBlock.accept(this);
        s.simBlock.accept(this);

        s.program.accept(this);
    }

    public void visit(DclBlock db) {
        for (int i = 0; i < db.stmtLists.size(); i++) {
            //System.out.println(db.stmtLists.elementAt(i).getClass());
            db.stmtLists.elementAt(i).accept(this);
        }
    }

    public void visit(SimBlock s) {
        for (int i = 0; i < s.simulationList.size(); i++) {
            s.simulationList.elementAt(i).accept(this);
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
        Main.currentSymbolTable.openScope();

        for (int i = 0; i < is.stmtList.size(); i++) {
            is.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();
    }

    public void visit(TypeDeclaration t) {
        TypeVisitor typeVisitor = new TypeVisitor();
        t.accept(typeVisitor);
    }

    public void visit(JavaString j) {

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
        Main.currentSymbolTable.openScope();

        for (int i = 0; i < p.stmtList.size(); i++) {
            p.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();
    }

    public void visit(Dcl ds) {
        TopDeclVisitor topDeclVisitor = new TopDeclVisitor();
        ds.accept(topDeclVisitor);
    }

    public void visit(Assignment as) {
        as.targetName.accept(new LHSSemanticsVisitor());
        as.expression.accept(this);

        if (assignable(as.targetName.type, as.expression.type)) {
            as.type = as.targetName.type;
        } else {
            error(as.getLineNumber(),
                    "Unable to assign " + getTypeName(as.expression.type) +
                            " to " + getTypeName(as.targetName.type) + ".");
            as.type = errorType;
        }
    }

    public void visit(WhileStmt ws) {
        ws.condition.accept(this);

        Main.currentSymbolTable.openScope();

        for (int i = 0; i < ws.stmtList.size(); i++) {
            ws.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();

        if (ws.condition != null) {
            checkBoolean(ws.condition);
        }
    }

    public void visit(ForeachStmt fes) {
        //fes.typeName.type == fes.objectName.type
        Main.currentSymbolTable.openScope();
        fes.typeName.accept(this);

        fes.localName.type = fes.typeName.type;

        Main.currentSymbolTable.enterSymbol(fes.localName.name, fes.typeName);
        fes.localName.accept(this);

        fes.objectName.accept(this);

        for (int i = 0; i < fes.stmtList.size(); i++) {
            fes.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();

        if ((fes.objectName.type != listType) && (fes.objectName.type != array1DType)) {
            error(fes.getLineNumber(), "Object in foreach statement is not a list or 1d array.");
            fes.type = errorType;
        } else {
            ASTNode def = Main.currentSymbolTable.retrieveSymbol(fes.objectName.name);

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

        Main.currentSymbolTable.openScope();

        for (int i = 0; i < fs.stmtList.size(); i++) {
            fs.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();

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

        Main.currentSymbolTable.openScope();

        for (int k = 0; k < i.stmtList.size(); k++) {
            i.stmtList.elementAt(k).accept(this);
        }

        Main.currentSymbolTable.closeScope();

        i.elseStmt.accept(this);

        checkBoolean(i.condition);
    }

    public void visit(ElseIfStmt e) {
        e.condition.accept(this);

        Main.currentSymbolTable.openScope();

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();

        e.elifStmt.accept(this);

        checkBoolean(e.condition);
    }

    public void visit(ElseStmt e) {
        Main.currentSymbolTable.openScope();

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i);
        }

        Main.currentSymbolTable.closeScope();
    }

    public void visit(EndIfStmt e) {
        // den er tom - jkj
    }

    public void visit(SwitchStmt ss) {
        ss.control.accept(this);
        if (ss.control.type != errorType && (ss.control.type == integerType ? (!assignable(integerType, ss.control.type)) : (!assignable(stringType, ss.control.type)))) {
            error(ss.getLineNumber(), "Illegal type for Switch statement.");
            ss.type = errorType;
        } else {
            ss.type = ss.control.type;
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

        Main.currentSymbolTable.openScope();

        for (int i = 0; i < sc.stmtList.size(); i++) {
            sc.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();
    }

    public void visit(SwitchDef sd) {
        Main.currentSymbolTable.openScope();

        for (int i = 0; i < sd.stmtList.size(); i++) {
            sd.stmtList.elementAt(i).accept(this);
        }

        Main.currentSymbolTable.closeScope();
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

    public void visit(PlusPlusStmt s) {
        s.id.accept(this);
        s.type = unaryResultType(plusplusOperator, s.id.type);
    }

    public void visit(MinusMinusStmt s) {
        s.id.accept(this);
        s.type = unaryResultType(minusminusOperator, s.id.type);
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

        int[] argTypeList;
        argTypeList = new int[f.argumentList.size()];
        for (int i = 0; i < f.argumentList.size(); i++) {
            f.argumentList.elementAt(i).accept(this);
            argTypeList[i] = f.argumentList.elementAt(i).type;
        }

        if (f.objectName.type == errorType) {
            error(f.getLineNumber(), "Function " + f.objectName.name + " has not been declared.");
            f.type = errorType;
        } else {
            ASTNode def = null;
            if (f.objectName instanceof ObjectReferencing) {
                ASTNode oDef = Main.currentSymbolTable.retrieveSymbol(((ObjectReferencing) f.objectName).objectName.name);

                if (oDef instanceof CustomTypeIdentifier) {
                    ASTNode tDef = Main.currentSymbolTable.retrieveSymbol(((CustomTypeIdentifier) oDef).name.name);

                    if (tDef instanceof TypeDeclaration) {
                        def = ((TypeDeclaration) tDef).typeDescriptor.fields.retrieveSymbol(((ObjectReferencing) f.objectName).fieldName.name);
                    } else {
                        error(f.getLineNumber(), "Type declaration not found for " + f.objectName.name);
                        f.type = errorType;
                    }
                } else {
                    error(f.getLineNumber(), "Type declaration not found for " + f.objectName.name);
                    f.type = errorType;
                }
            } else {
                def = Main.currentSymbolTable.retrieveSymbol(f.objectName.name);
            }
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

                        if (argTypeList[i] != function.paramList.elementAt(i).type) {
                            String fName = "";
                            if (f.objectName instanceof ObjectReferencing) {
                                fName = ((ObjectReferencing) f.objectName).fieldName.name;
                            } else {
                                fName = f.objectName.name;
                            }

                            error(f.getLineNumber(), "Argument " + (i + 1) + " in function call of " + fName + " does not match with parameter. Type: " + getTypeName(f.argumentList.elementAt(i).type));
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

    public void visit(IntegerT i) {
        i.type = integerType;
    }

    public void visit(VoidT v) {
        v.type = voidType;
    }

    public void visit(CustomTypeIdentifier o) {
        o.type = objectType;
    }

    public void visit(Array1DReferencing a) {
        a.arrayName.accept(this);
        a.indexExpr.accept(this);

        if (a.indexExpr.type != integerType) {
            error(a.getLineNumber(), "Index expression is not of type integer.");
            a.type = errorType;
        }

        if (a.arrayName.type == errorType) {
            error(a.getLineNumber(), "Array identifier error.");
            a.type = errorType;
        } else {
            if (a.arrayName.type != array1DType) {
                error(a.getLineNumber(), a.arrayName.name + " is not an array.");
                a.type = errorType;
            } else {
                ASTNode def = Main.currentSymbolTable.retrieveSymbol(a.arrayName.name);
                if (def instanceof Array1D) {
                    a.type = ((Array1D) def).typeName.type;
                } else {
                    error(a.getLineNumber(), a.arrayName.name + " is not an array.");
                    a.type = errorType;
                }
            }
        }
    }

    public void visit(Array2DReferencing a) {
        a.arrayName.accept(this);
        a.firstIndexExpr.accept(this);
        a.secondIndexExpr.accept(this);

        if (a.firstIndexExpr.type != integerType) {
            error(a.getLineNumber(), "First size expression is not an integer. ArrayName: " + a.arrayName.name);
        }

        if (a.secondIndexExpr.type != integerType) {
            error(a.getLineNumber(), "Second size expression is not an integer. ArrayName: " + a.arrayName.name);
        }

        if (a.arrayName.type == errorType) {
            error(a.getLineNumber(), "Array identifier error.");
            a.type = errorType;
        } else {
            if (a.arrayName.type != array2DType) {
                error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                a.type = errorType;
            } else {
                ASTNode def = Main.currentSymbolTable.retrieveSymbol(a.arrayName.name);
                if (def instanceof Array2D) {
                    a.type = ((Array2D) def).typeName.type;
                } else {
                    error(a.getLineNumber(), a.arrayName.name + " is not an array."); // [] måske
                    a.type = errorType;
                }
            }
        }
    }

    public void visit(ObjectReferencing o) {
        o.objectName.accept(this);
        if (o.objectName.type == errorType) {
            error(o.getLineNumber(), "Object identifier error.");
            o.type = errorType;
        } else {
            if (o.objectName.type != objectType) {
                error(o.getLineNumber(), o.objectName.name + " does not specify an object.");
                o.type = errorType;
            } else {
                ASTNode oDef = Main.currentSymbolTable.retrieveSymbol(o.objectName.name);
                if (oDef instanceof CustomTypeIdentifier) {
                    ASTNode tDef = Main.symbolTable.retrieveSymbol(((CustomTypeIdentifier) oDef).name.name);

                    if (tDef instanceof TypeDeclaration) {
                        SymbolTable st = ((TypeDeclaration) tDef).typeDescriptor.fields;
                        ASTNode def = st.retrieveSymbol(o.fieldName.name);
                        if (def != null) {
                            o.type = def.type;
                        } else {
                            error(o.getLineNumber(), o.fieldName.name + " is not a field of " + o.objectName.name);
                            o.type = errorType;
                        }
                    } else {
                        error(o.getLineNumber(), "No type declaration for object: " + o.objectName.name);
                        o.type = errorType;
                    }
                } else {
                    error(o.getLineNumber(), "No type declaration for object: " + o.objectName.name);
                    o.type = errorType;
                }
            }
        }
    }


    // tror denne er som den skal være - jkj
    public void visit(Identifier id) {
        id.type = errorType;
        id.def = null;
        ASTNode newDef = Main.currentSymbolTable.retrieveSymbol(id.name);
        if (newDef == null) {
            errorNoDeclaration(id.getLineNumber(), id.name);
        } else {
            id.def = newDef;
            id.type = newDef.type;
        }
    }

    public void visit(JavaStringStmt j) {}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean assignable(int target, int expression) {
        if (target == expression) return true;
        if (target == decimalType && expression == integerType) return true;
        if (target == stringType && expression == nullType) return true;
        if ((target == array1DType || target == array2DType || target == listType) && expression == nullType) return true;
        if ((target == objectType) && expression == nullType) return true;

        // er et hack, men vi vil forsøge at leve med det...
        if (target == objectType && expression == coordType) return true;
        return false;
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

}

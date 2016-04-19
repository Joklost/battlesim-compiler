package com.company.CodeGeneration;

import com.company.AST.Nodes.*;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;

import java.util.ArrayList;
import java.util.List;

import static com.company.ContextualAnalysis.TypeConsts.*;


/**
 * Created by joklost on 18-04-16.
 */

public class GenerateJavaVisitor extends Visitor implements VisitorInterface {
    private List<String> code;
    private int indentLevel;

    public GenerateJavaVisitor() {
        code = new ArrayList<>();
    }

    private void emit(String s) {
        code.add(s);
    }

    private void emitIndentation() {
        String spaces = "";
        for (int i = 0; i < indentLevel; i++) {
            spaces += "    ";
        }
        code.add(spaces);
    }

    private void emitIndentation(String s) {
        emitIndentation();
        emit(s);
    }

    private void emitComment(String s) {
        emitIndentation();
        emit("// " + s);
    }

    public List<String> getCode() {
        return code;
    }

    public void defaultVisit(Object o) {

    }

    public void visit(Start s) {
        //emitIndentation();
        //emit("package com.BattleSim;\n");
        emitIndentation("import java.io.*;\n");
        emitIndentation("import java.util.Scanner;\n");
        //emit("import static com.company.StdLib.IO.*;");
        emitComment("BattleSim automatically generated code file.\n");
        emitIndentation("public class Main {\n");

        indentLevel++;

        s.dclBlock.accept(this);
        s.simBlock.accept(this);


        /////
        // stdlib
        emitStdLib();
        /////


        for (int i = 0; i < s.functionDclList1.size(); i++) {
            s.functionDclList1.elementAt(i).accept(this);
        }

        s.program.accept(this);

        for (int i = 0; i < s.functionDclList2.size(); i++) {
            s.functionDclList2.elementAt(i).accept(this);
        }

        indentLevel--;
        emitIndentation("}\n");
    }

    public void visit(DclBlock db) {
        // TODO
    }

    public void visit(SimBlock s) {
        // TODO

    }

    public void visit(SimStep s) {
        // TODO

    }

    public void visit(Simulation s) {
        // TODO

    }

    public void visit(Interrupts is) {
        // TODO

    }

    public void visit(FunctionDcl fd) {
        emitIndentation();
        emitIndentation("public ");
        fd.returnType.accept(this);
        emit(" ");
        fd.functionName.accept(this);
        emit(" (");
        for (int i = 0; i < fd.paramList.size(); i++) {
            fd.paramList.elementAt(i).accept(this);
            if (i < fd.paramList.size() - 1) emit(", ");
        }
        emit(") {\n");

        indentLevel++;

        for (int i = 0; i < fd.stmtList.size(); i++) {
            fd.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("}\n");
    }

    public void visit(Param p) {
        p.typeIdentifier.accept(this);
        emit(" ");
        p.identifier.accept(this);

    }

    public void visit(Program p) {
        emitIndentation("public static void main(String[] args) {\n");
        indentLevel++;

        for (int i = 0; i < p.stmtList.size(); i++) {
            p.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        emitIndentation("}\n");
    }

    public void visit(Dcl ds) {
        emitIndentation();
        ds.typeName.accept(this);
        emit(" ");

        for (int i = 0; i < ds.dclIdList.size(); i++) {
            ds.dclIdList.elementAt(i).accept(this);
            if (i < ds.dclIdList.size() - 1) emit(", ");
        }

        if (ds.typeName.type == listType) {
            emit(" = new ArrayList<>()");
        }

        emit(";\n");
    }

    public void visit(Assignment as) {
        emitIndentation();
        as.targetName.accept(this);
        as.assignOp.accept(this);
        as.expression.accept(this);
        emit(";\n");
    }

    public void visit(WhileStmt ws) {
        emitIndentation("while (");
        ws.condition.accept(this);
        emit(") {\n");
        indentLevel++;

        for (int i = 0; i < ws.stmtList.size(); i++) {
            ws.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        emitIndentation("}\n");
    }

    public void visit(ForeachStmt fes) {
        // TODO
        emitIndentation("for (");
        fes.typeName.accept(this);
        emit(" ");
        fes.localName.accept(this);
        emit(" : ");
        fes.objectName.accept(this);
        emit(") {\n");

        indentLevel++;

        for (int i = 0; i < fes.stmtList.size(); i++) {
            fes.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("}\n");
    }

    public void visit(ForStmt fs) {
        // TODO

    }

    public void visit(IfStmt i) {
        // TODO

    }

    public void visit(ElseIfStmt e) {
        // TODO

    }

    public void visit(ElseStmt e) {
        // TODO

    }

    public void visit(EndIfStmt e) {
        // TODO

    }

    public void visit(SwitchStmt ss) {
        // TODO

    }

    public void visit(SwitchCase sc) {
        // TODO

    }

    public void visit(SwitchDef sd) {
        // TODO
    }

    public void visit(ReturnExpr r) {
        emitIndentation("return ");
        r.returnVal.accept(this);
        emit(";\n");
    }

    public void visit(Return r) {
        emitIndentation("return;\n");
    }

    public void visit(FunctionCallStmt fcs) {
        emitIndentation();
        fcs.functionCall.accept(this);
        emit(";\n");
    }

    public void visit(PlusExpr pe) {
        emit("(");
        pe.leftExpr.accept(this);
        emit("+");
        pe.rightExpr.accept(this);
        emit(")");
    }

    public void visit(MinusExpr me) {
        emit("(");
        me.leftExpr.accept(this);
        emit("-");
        me.rightExpr.accept(this);
        emit(")");
    }

    public void visit(MultExpr me) {
        emit("(");
        me.leftExpr.accept(this);
        emit("*");
        me.rightExpr.accept(this);
        emit(")");
    }

    public void visit(DivExpr de) {
        emit("(");
        de.leftExpr.accept(this);
        emit("/");
        de.rightExpr.accept(this);
        emit(")");
    }

    public void visit(ModExpr me) {
        emit("(");
        me.leftExpr.accept(this);
        emit("%");
        me.rightExpr.accept(this);
        emit(")");
    }

    public void visit(AndExpr ae) {
        emit("(");
        ae.leftExpr.accept(this);
        emit("&&");
        ae.rightExpr.accept(this);
        emit(")");
    }

    public void visit(OrExpr oe) {
        emit("(");
        oe.leftExpr.accept(this);
        emit("||");
        oe.rightExpr.accept(this);
        emit(")");
    }

    public void visit(LogicEqualsExpr le) {
        emit("(");
        le.leftExpr.accept(this);
        emit("==");
        le.rightExpr.accept(this);
        emit(")");
    }

    public void visit(LessThanExpr le) {
        emit("(");
        le.leftExpr.accept(this);
        emit("<");
        le.rightExpr.accept(this);
        emit(")");
    }

    public void visit(GreaterThanExpr ge) {
        emit("(");
        ge.leftExpr.accept(this);
        emit(">");
        ge.rightExpr.accept(this);
        emit(")");
    }

    public void visit(LessThanEqualsExpr le) {
        emit("(");
        le.leftExpr.accept(this);
        emit("<=");
        le.rightExpr.accept(this);
        emit(")");
    }

    public void visit(GreaterThanEqualsExpr ge) {
        emit("(");
        ge.leftExpr.accept(this);
        emit(">=");
        ge.rightExpr.accept(this);
        emit(")");
    }

    public void visit(NotExpr ne) {
        emit("(!");
        ne.expression.accept(this);
        emit(")");
    }

    public void visit(UnMinusExpr ue) {
        emit("(-");
        ue.expression.accept(this);
        emit(")");
    }

    public void visit(PlusPlusExpr pe) {
        emit("(");
        pe.expression.accept(this);
        emit("++)");
    }

    public void visit(MinusMinusExpr me) {
        emit("(");
        me.expression.accept(this);
        emit("--)");
    }

    public void visit(FunctionCallExpr fe) {
        fe.functionCall.accept(this);
    }

    public void visit(ObjectIdExpr ne) {
        ne.objectName.accept(this);
    }

    public void visit(StdLiteralExpr se) {
        se.stdLiteral.accept(this);
    }

    public void visit(CoordExpr ce) {
        // TODO

    }

    public void visit(EqualsOp eo) {
        emit("=");
    }

    public void visit(PlusEqualsOp po) {
        emit("+=");
    }

    public void visit(MinusEqualsOp mo) {
        emit("-=");
    }

    public void visit(ModEqualsOp mo) {
        emit("%=");
    }

    public void visit(MultEqualsOp mo) {
        emit("*=");
    }

    public void visit(DivEqualsOp deo) {
        emit("/=");
    }

    public void visit(FunctionCall f) {
        f.objectName.accept(this);
        emit("(");
        for (int i = 0; i < f.argumentList.size(); i++) {
            f.argumentList.elementAt(i).accept(this);
            if (i < f.argumentList.size() - 1) emit(", ");
        }
        emit(")");

    }

    public void visit(ToIterator ti) {
        // TODO

    }

    public void visit(DownToIterator di) {
        // TODO

    }

    public void visit(VariableObjectId vi) {
        vi.objectName.accept(this);
    }

    public void visit(VariableStdLiteral vs) {
        vs.stdLiteral.accept(this);
    }

    public void visit(DecimalLiteral dl) {
        emit(dl.value.toString());
    }

    public void visit(StringLiteral sl) {
        emit("\"" + sl.string + "\"");
    }

    public void visit(BooleanLiteral bl) {
        emit(bl.value.toString());
    }

    public void visit(IntegerLiteral il) {
        emit(il.value.toString());
    }

    public void visit(NullLiteral nl) {
        emit("null");
    }

    public void visit(Array1D a) {
        // TODO

    }

    public void visit(Array2D a) {
        // TODO

    }

    public void visit(ListOf l) {
        emit("ArrayList<");
        l.typeName.accept(this);
        emit(">");
    }

    public void visit(Decimal d) {
        emit("double");
    }

    public void visit(StringT s) {
        emit("String");
    }

    public void visit(BooleanT b) {
        emit("boolean");
    }

    public void visit(Group g) {
        // TODO

    }

    public void visit(Platoon p) {
        // TODO

    }

    public void visit(Force f) {
        // TODO

    }

    public void visit(Coord c) {
        // TODO

    }

    public void visit(Soldier s) {
        // TODO

    }

    public void visit(Barrier b) {
        // TODO

    }

    public void visit(VectorT v) {
        // TODO

    }

    public void visit(IntegerT i) {
        emit("int");
    }

    public void visit(VoidT v) {
        emit("void");

    }

    public void visit(Terrain t) {
        // TODO
    }

    public void visit(ObjectReferencing o) {

    }

    public void visit(Array1DReferencing a) {

    }

    public void visit(Array2DReferencing a) {

    }

    public void visit(Identifier id) {
        emit(id.name);
    }





    private void emitStdLib() {
        // Print
        emitIndentation();
        emit("public static void Print(String s) {\n");
        indentLevel++;
        emitIndentation();
        emit("System.out.print(s);\n");
        indentLevel--;
        emitIndentation();
        emit("}\n");

        //PrintLine
        emitIndentation("public static void PrintLine(String s) {\n");
        indentLevel++;
        emitIndentation("System.out.println(s);");
        indentLevel--;
        emitIndentation("}\n");

        //Input
        emitIndentation("public static String Input() {\n");
        indentLevel++;
        emitIndentation("Scanner sc = new Scanner(System.in);\n");
        emitIndentation("return sc.nextLine();\n");
        indentLevel--;
        emitIndentation("}\n");


        //ConvertToInteger
        emitIndentation("public static int ConvertToInteger(String s) {\n");
        indentLevel++;

        emitIndentation("if (!isIntegerParsable(s)) {\n");
        indentLevel++;
        emitIndentation("System.err.println(\"String \" + s + \" is not an Integer.\");\n");
        emitIndentation("return 0;\n");
        indentLevel--;
        emitIndentation("} else {\n");
        indentLevel++;

        emitIndentation("return Integer.parseInt(s);\n");

        indentLevel--;
        emitIndentation("}\n");

        indentLevel--;
        emitIndentation("}\n");

        // IsInteger?
        emitIndentation("public static boolean isIntegerParsable(String str) {\n");
        indentLevel++;
        emitIndentation("try {\n");
        indentLevel++;
        emitIndentation("Integer.parseInt(str);\n");
        emitIndentation("return true;\n");
        indentLevel--;
        emitIndentation("} catch (NumberFormatException nfe) {}\n");
        emitIndentation("return false;\n");
        indentLevel--;
        emitIndentation("}\n");
    }
}

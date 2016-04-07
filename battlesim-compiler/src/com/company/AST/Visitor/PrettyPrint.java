package com.company.AST.Visitor;

import com.company.AST.*;

/**
 * Created by joklost on 07-04-16.
 */
public class PrettyPrint extends Visitor implements VisitorInterface {
    private int indentLevel;

    public PrettyPrint() {
        indentLevel = 0;
    }

    private void printIndent() {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  ");
        }
    }

    private void println(String message) {
        printIndent();
        System.out.println(message);
    }

    private void print(String message) {
        System.out.print(message);
    }

    public void defaultVisit(Object o) {
        println("Error with object: " + o.getClass());
    }

    public void visit(Start s) {
        s.dclBlock.accept(this);
        println("");

        s.simBlock.accept(this);
        println("");

        for (int i = 0; i < s.functionDclList1.size(); i++) {
            s.functionDclList1.elementAt(i).accept(this);
            println("");
        }

        s.program.accept(this);
        println("");

        for (int i = 0; i < s.functionDclList2.size(); i++) {
            s.functionDclList2.elementAt(i).accept(this);
        }
    }

    public void visit(DclBlock db) {
        println("Begin Declarations");
        indentLevel++;

        for (int i = 0; i < db.stmtLists.size(); i++) {
            db.stmtLists.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Declarations");
    }

    ///// VENTER LIGE MED DEM
    public void visit(SimBlock s) {

    }

    public void visit(SimStep s) {

    }

    public void visit(Simulation s) {

    }

    public void visit(Interrupts is) {

    }

    ///// VENTER LIGE MED DEM

    
    public void visit(FunctionDcl fd) {
        printIndent();
        print("Function ");
        fd.typeIdentifier.accept(this);
        print(" ");
        fd.identifier.accept(this);
        print("(");

        for (int i = 0; i < fd.paramList.size(); i++) {
            fd.paramList.elementAt(i).accept(this);
            if (i > 0 && i != fd.paramList.size()) print(", ");
        }

        print(")\n");
        indentLevel++;


        for (int i = 0; i < fd.stmtList.size(); i++) {
            fd.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Function");
    }

    public void visit(Param p) {
        p.identifier.accept(this);
        print(" as ");
        p.typeIdentifier.accept(this);
    }

    public void visit(Program p) {
        println("Begin Program");
        indentLevel++;

        for (int i = 0; i < p.stmtList.size(); i++) {
            p.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Program");
    }

    public void visit(Dcl ds) {
        printIndent();
        print("Declare ");

        for (int i = 0; i < ds.dclIdList.size(); i++) {
            ds.dclIdList.elementAt(i).accept(this);
            if (i > 0 && i != ds.dclIdList.size()) print(", ");
        }

        print(" as ");

        ds.typeIdentifier.accept(this);

        print("\n");
    }

    public void visit(Assignment as) {
        printIndent();
        as.nestedIdentifier.accept(this);
        as.assignOp.accept(this);
        as.expression.accept(this);
        print("\n");
    }

    public void visit(WhileStmt ws) {
        printIndent();
        print("While ");
        ws.expression.accept(this);
        print(" Do\n");

        indentLevel++;

        for (int i = 0; i < ws.stmtList.size(); i++) {
            ws.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        println("End While");

    }

    public void visit(ForeachStmt fes) {
        printIndent();
        print("Foreach ");
        fes.typeIdentifier.accept(this);
        print(" ");
        fes.identifier.accept(this);
        print(" in ");
        fes.nestedIdentifier.accept(this);
        print(" Do\n");

        indentLevel++;

        for (int i = 0; i < fes.stmtList.size(); i++) {
            fes.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        println("End Foreach");
    }

    public void visit(ForStmt fs) {
        printIndent();
        print("For ");
        fs.expression1.accept(this);
        fs.forIterator.accept(this);
        fs.expression2.accept(this);
        print(" Do\n");

        indentLevel++;

        for (int i = 0; i < fs.stmtList.size(); i++) {
            fs.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        println("End For");
    }

    public void visit(IfStmt i) {
        printIndent();
        print("If ");
        i.expression.accept(this);
        print(" Then\n");

        indentLevel++;

        for (int k = 0; k < i.stmtList.size(); k++) {
            i.stmtList.elementAt(k).accept(this);
        }

        indentLevel--;

        i.elseStmt.accept(this);
    }

    public void visit(ElseIfStmt e) {
        printIndent();
        print("Else If ");
        e.expression.accept(this);
        print(" Then");

        indentLevel++;

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        e.elifStmt.accept(this);
    }

    public void visit(ElseStmt e) {
        println("Else");

        indentLevel++;

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        println("End If");
    }

    public void visit(EndIfStmt e) {
        println("End If");
    }

    public void visit(SwitchStmt ss) {
        printIndent();
        print("Switch ");
        ss.variable.accept(this);
        print("\n");

        indentLevel++;

        for (int i = 0; i < ss.switchCaseList.size(); i++) {
            ss.switchCaseList.elementAt(i).accept(this);
        }

        ss.switchDef.accept(this);

        indentLevel--;

        println("End Switch");
    }

    public void visit(SwitchCase sc) {
        printIndent();
        print("Case ");
        sc.expression.accept(this);
        print("\n");

        indentLevel++;

        for (int i = 0; i < sc.stmtList.size(); i++) {
            sc.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
    }

    public void visit(SwitchDef sd) {
        println("Default");

        indentLevel++;

        for (int i = 0; i < sd.stmtList.size(); i++) {
            sd.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
    }

    public void visit(ReturnExpr r) {
        printIndent();
        print("Return ");
        r.expression.accept(this);
        print("\n");
    }

    public void visit(Return r) {
        println("Return");
    }

    public void visit(FunctionCallStmt fcs) {
        printIndent();
        fcs.functionCall.accept(this);
        print("\n");
    }

    public void visit(PlusExpr pe) {
        pe.leftExpr.accept(this);
        print(" + ");
        pe.rightExpr.accept(this);
    }

    public void visit(MinusExpr me) {
        me.leftExpr.accept(this);
        print(" - ");
        me.rightExpr.accept(this);
    }

    public void visit(MultExpr me) {
        me.leftExpr.accept(this);
        print(" * ");
        me.rightExpr.accept(this);
    }

    public void visit(DivExpr de) {
        de.leftExpr.accept(this);
        print(" / ");
        de.rightExpr.accept(this);
    }

    public void visit(ModExpr me) {
        me.leftExpr.accept(this);
        print(" % ");
        me.rightExpr.accept(this);
    }

    public void visit(AndExpr ae) {
        ae.leftExpr.accept(this);
        print(" AND ");
        ae.rightExpr.accept(this);
    }

    public void visit(OrExpr oe) {
        oe.leftExpr.accept(this);
        print(" OR ");
        oe.rightExpr.accept(this);
    }

    public void visit(LogicEqualsExpr le) {
        le.leftExpr.accept(this);
        print(" == ");
        le.rightExpr.accept(this);
    }

    public void visit(LessThanExpr le) {
        le.leftExpr.accept(this);
        print(" < ");
        le.rightExpr.accept(this);
    }

    public void visit(GreaterThanExpr ge) {
        ge.leftExpr.accept(this);
        print(" > ");
        ge.rightExpr.accept(this);
    }

    public void visit(LessThanEqualsExpr le) {
        le.leftExpr.accept(this);
        print(" <= ");
        le.rightExpr.accept(this);
    }

    public void visit(GreaterThanEqualsExpr ge) {
        ge.leftExpr.accept(this);
        print(" >= ");
        ge.rightExpr.accept(this);
    }

    public void visit(NotExpr ne) {
        print("NOT ");
        ne.expression.accept(this);
    }

    public void visit(UnMinusExpr ue) {
        print("-");
        ue.expression.accept(this);
    }

    public void visit(PlusPlusExpr pe) {
        pe.expression.accept(this);
        print("++");
    }

    public void visit(MinusMinusExpr me) {
        me.expression.accept(this);
        print("--");
    }

    public void visit(FunctionCallExpr fe) {
        fe.functionCall.accept(this);
    }

    public void visit(NestedIdExpr ne) {
        ne.nestedIdentifier.accept(this);
    }

    public void visit(StdLiteralExpr se) {
        se.stdLiteral.accept(this);
    }

    public void visit(CoordExpr ce) {
        print("(");
        ce.expression1.accept(this);
        print(", ");
        ce.expression2.accept(this);
        print(")");
    }

    public void visit(EqualsOp eo) {
        print("=");
    }

    public void visit(PlusEqualsOp po) {
        print("+=");
    }

    public void visit(MinusEqualsOp mo) {
        print("-=");
    }

    public void visit(ModEqualsOp mo) {
        print("%=");
    }

    public void visit(MultEqualsOp mo) {
        print("*=");
    }

    public void visit(DivEqualsOp deo) {
        print("/=");
    }

    public void visit(FunctionCall f) {
        f.nestedIdentifier.accept(this);
        print("(");

        for (int i = 0; i < f.argumentList.size(); i++) {
            f.argumentList.elementAt(i).accept(this);
            if (i > 0 && i != f.argumentList.size()) print(", ");
        }

        print(")");
    }

    public void visit(ToIterator ti) {
        print(" To ");
    }

    public void visit(DownToIterator di) {
        print(" DownTo ");
    }

    public void visit(VariableNestedId vi) {
        vi.nestedIdentifier.accept(this);
    }

    public void visit(VariableStdLiteral vs) {
        vs.stdLiteral.accept(this);
    }

    public void visit(DecimalLiteral dl) {
        print(dl.decimal.toString());
    }

    public void visit(StringLiteral sl) {
        print(sl.string);
    }

    public void visit(BooleanLiteral bl) {
        print(bl.bool.toString());
    }

    public void visit(IntegerLiteral il) {
        print(il.integer.toString());
    }

    public void visit(NullLiteral nl) {
        print("NULL");
    }

    public void visit(Decimal d) {
        print("Decimal");
    }

    public void visit(StringT s) {
        print("String");
    }

    public void visit(BooleanT b) {
        print("Boolean");
    }

    public void visit(Group g) {
        print("Group");
    }

    public void visit(Platoon p) {
        print("Platoon");
    }

    public void visit(Force f) {
        print("Force");
    }

    public void visit(Coord c) {
        print("Coord");
    }

    public void visit(Soldier s) {
        print("Soldier");
    }

    public void visit(Barrier b) {
        print("Barrier");
    }

    public void visit(VectorT v) {
        print("Vector");
    }

    public void visit(IntegerT i) {
        print("Integer");
    }

    public void visit(VoidT v) {
        print("Void");
    }

    public void visit(Terrain t) {
        print("Terrain");
    }

    public void visit(Decimal1DArray d) {
        print("Decimal[]");
    }

    public void visit(String1DArray s) {
        print("String[]");
    }

    public void visit(Boolean1DArray b) {
        print("Boolean[]");
    }

    public void visit(Group1DArray g) {
        print("Group[]");
    }

    public void visit(Platoon1DArray p) {
        print("Platoon[]");
    }

    public void visit(Force1DArray f) {
        print("Force[]");
    }

    public void visit(Coord1DArray c) {
        print("Coord[]");
    }

    public void visit(Soldier1DArray s) {
        print("Solder[]");
    }

    public void visit(Barrier1DArray b) {
        print("Barrier[]");
    }

    public void visit(Vector1DArray v) {
        print("Vector[]");
    }

    public void visit(Integer1DArray i) {
        print("Integer[]");
    }

    public void visit(Terrain1DArray t) {
        print("Terrain[]");
    }

    public void visit(Decimal2DArray d) {
        print("Decimal[][]");
    }

    public void visit(String2DArray s) {
        print("String[][]");
    }

    public void visit(Boolean2DArray b) {
        print("Boolean[][]");
    }

    public void visit(Group2DArray g) {
        print("Group[][]");
    }

    public void visit(Platoon2DArray p) {
        print("Platoon[][]");
    }

    public void visit(Force2DArray f) {
        print("Force[][]");
    }

    public void visit(Coord2DArray c) {
        print("Coord[][]");
    }

    public void visit(Soldier2DArray s) {
        print("Soldier[][]");
    }

    public void visit(Barrier2DArray b) {
        print("Barrier[][]");
    }

    public void visit(Vector2DArray v) {
        print("Vector[][]");
    }

    public void visit(Integer2DArray i) {
        print("Integer[][]");
    }

    public void visit(Terrain2DArray t) {
        print("Terrain[][]");
    }

    public void visit(DecimalList d) {
        print("List of Decimal");
    }

    public void visit(StringList s) {
        print("List of String");
    }

    public void visit(BooleanList b) {
        print("List of Boolean");
    }

    public void visit(GroupList g) {
        print("List of Group");
    }

    public void visit(PlatoonList p) {
        print("List of Platoon");
    }

    public void visit(ForceList f) {
        print("List of Force");
    }

    public void visit(CoordList c) {
        print("List of Coord");
    }

    public void visit(SoldierList s) {
        print("List of Soldier");
    }

    public void visit(BarrierList b) {
        print("List of Barrier");
    }

    public void visit(VectorList v) {
        print("List of Vector");
    }

    public void visit(IntegerList i) {
        print("List of Integer");
    }

    public void visit(TerrainList t) {
        print("List of Terrain");
    }

    public void visit(NestedIdentifierMember n) {
        n.identifier.accept(this);
        print(".");
        n.nestedIdentifier.accept(this);
    }

    public void visit(NestedIdentifier1DArray n) {
        n.identifier.accept(this);
        print("[");
        n.expression.accept(this);
        print("]");
    }

    public void visit(NestedIdentifier1DArrayMember n) {
        n.identifier.accept(this);
        print("[");
        n.expression.accept(this);
        print("].");
        n.nestedIdentifier.accept(this);
    }

    public void visit(NestedIdentifier2DArray n) {
        n.identifier.accept(this);
        print("[");
        n.expression1.accept(this);
        print("][");
        n.expression2.accept(this);
        print("]");
    }

    public void visit(NestedIdentifier2DArrayMember n) {
        n.identifier.accept(this);
        print("[");
        n.expression1.accept(this);
        print("][");
        n.expression2.accept(this);
        print("].");
        n.nestedIdentifier.accept(this);
    }

    public void visit(NestedIdentifier n) {
        n.identifier.accept(this);
    }

    public void visit(Identifier id) {
        print(id.identifier);
    }
}

package com.company.AST.Visitor;

import com.company.AST.*;

/**
 * Created by joklost on 03-04-16.
 */
public class TreeVisitor extends Visitor implements IVisitor {
    private int indentLevel;

    public TreeVisitor() {
        this.indentLevel = 0;
    }

    private void println(String message) {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  ");
        }
        System.out.println(message);
    }

    public void visit(Start s) {
        println("Start:");
        indentLevel++;
        s.dclBlock.accept(this);

        s.simBlock.accept(this);

        FunctionDclList functionDcls = s.functionDclList1;
        for (int i = 0; i < functionDcls.size(); i++) {
            functionDcls.elementAt(i).accept(this);
        }

        s.program.accept(this);

        functionDcls = s.functionDclList2;
        for (int i = 0; i < functionDcls.size(); i++) {
            functionDcls.elementAt(i).accept(this);
        }
        indentLevel--;
    }

    public void visit(DclBlock db) {
        println("DclBlock:");
        indentLevel++;


        StmtList stmts = db.stmtLists;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }

        indentLevel--;
    }

    public void visit(SimBlock s) {
        println("Declare simulations:");
        indentLevel++;

        println("Simulations:");
        indentLevel++;
        SimulationList sl = s.simulationList;
        for (int i = 0; i < sl.size(); i++) {
            sl.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(SimStep s) {
        println("Simulation step " + s.stepNumber + ":");
        indentLevel++;

        println("Statements:");
        indentLevel++;
        StmtList sl = s.stmtList;
        for (int i = 0; i < sl.size(); i++) {
            sl.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(Simulation s) {
        println("Simulation " + s.identifier1.identifier + ":");
        indentLevel++;

        println("Simulation steps:");
        indentLevel++;

        SimStepList sl = s.simStepList;
        for (int i = 0; i < sl.size(); i++) {
            sl.elementAt(i).accept(this);
        }
        indentLevel--;

        println("Interrupts:");
        indentLevel++;
        s.interrupts.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(Interrupts is) {
        println("Interrupt statements:");
        indentLevel++;

        StmtList sl = is.stmtList;
        for (int i = 0; i < sl.size(); i++) {
            sl.elementAt(i).accept(this);
        }

        indentLevel--;
    }

    public void visit(FunctionDcl fd) {
        println("Function named " + fd.identifier.identifier + ":");
        indentLevel++;

        println("Return type:");
        indentLevel++;
        fd.typeIdentifier.accept(this);
        indentLevel--;

        println("Parameters:");
        indentLevel++;
        ParamList params = fd.paramList;
        for (int i = 0; i < params.size(); i++) {
            params.elementAt(i).accept(this);
        }
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmts = fd.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;
    }

    public void visit(Param p) {
        println("Formal parameter " + p.identifier.identifier + " of type:");
        indentLevel++;
        p.typeIdentifier.accept(this);
        indentLevel--;
    }

    public void visit(Program p) {
        println("Program: ");
        indentLevel++;

        println("Statements:");
        indentLevel++;
        StmtList stmts = p.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(Dcl ds) {
        println("Declare variables:");
        indentLevel++;

        println("Type:");
        indentLevel++;
        ds.typeIdentifier.accept(this);
        indentLevel--;

        println("Variable identifiers:");
        indentLevel++;
        DclIdList ids = ds.dclIdList;
        for (int i = 0; i < ids.size(); i++) {
            ids.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(Assignment as) {
        println("Assignment of expression to variable:");
        indentLevel++;

        println("Variable id:");
        indentLevel++;
        as.nestedIdentifier.accept(this);
        indentLevel--;

        println("Operator:");
        indentLevel++;
        as.assignOp.accept(this);
        indentLevel--;

        println("Expression:");
        indentLevel++;
        as.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(WhileStmt ws) {
        println("While:");
        indentLevel++;

        println("Expression:");
        indentLevel++;
        ws.expression.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmts = ws.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(ForeachStmt fes) {
        println("Foreach, iterates over " + fes.identifier.identifier + ":");
        indentLevel++;

        println("Local element " + fes.identifier.identifier + " of type:");
        indentLevel++;
        fes.typeIdentifier.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmts = fes.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(ForStmt fs) {
        println("For:");
        indentLevel++;

        println("First expression:");
        indentLevel++;
        fs.expression1.accept(this);
        indentLevel--;

        println("Iterator:");
        indentLevel++;
        fs.forIterator.accept(this);
        indentLevel--;

        println("Second expression:");
        indentLevel++;
        fs.expression2.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmts = fs.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(IfStmt i) {
        println("If statement:");
        indentLevel++;

        println("Boolean expression:");
        indentLevel++;
        i.expression.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmtList = i.stmtList;
        for(int k = 0; k < stmtList.size(); k++) {
            stmtList.elementAt(k).accept(this);
        }
        indentLevel--;

        println("Elif:");
        indentLevel++;
        i.elseStmt.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(ElseIfStmt e) {
        println("Else if:");
        indentLevel++;

        println("Boolean expression:");
        indentLevel++;
        e.expression.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmtList = e.stmtList;
        for(int k = 0; k < stmtList.size(); k++) {
            stmtList.elementAt(k).accept(this);
        }
        indentLevel--;

        println("Elif:");
        indentLevel++;
        e.elifStmt.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(ElseStmt e) {
        println("Else:");
        indentLevel++;

        println("Statements:");
        indentLevel++;
        StmtList stmtList = e.stmtList;
        for(int k = 0; k < stmtList.size(); k++) {
            stmtList.elementAt(k).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(EndIfStmt e) {
        println("End if");
    }

    public void visit(SwitchStmt ss) {
        println("Switch:");
        indentLevel++;

        println("Switching for variable:");
        indentLevel++;
        ss.variable.accept(this);
        indentLevel--;

        println("Switch cases:");
        indentLevel++;
        SwitchCaseList slist = ss.switchCaseList;
        for (int i = 0; i < slist.size(); i++) {
            slist.elementAt(i).accept(this);
        }
        indentLevel--;

        println("Switch default:");
        indentLevel++;
        ss.switchDef.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(SwitchCase sc) {
        println("Switch case:");
        indentLevel++;

        println("Case expression:");
        indentLevel++;
        sc.expression.accept(this);
        indentLevel--;

        println("Statements:");
        indentLevel++;
        StmtList stmts = sc.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(SwitchDef sd) {
        println("Switch default:");
        indentLevel++;

        println("Statements:");
        indentLevel++;
        StmtList stmts = sd.stmtList;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(ReturnExpr r) {
        println("Return with expression:");
        indentLevel++;

        r.expression.accept(this);

        indentLevel--;
    }

    public void visit(Return r) {
        println("Return");
    }

    public void visit(FunctionCallStmt fcs) {
        println("Function call statement:");
        indentLevel++;

        println("Function call:");
        indentLevel++;
        fcs.functionCall.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(PlusExpr pe) {
        println("Addition:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        pe.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        pe.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(MinusExpr me) {
        println("Subtraction:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        me.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        me.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(MultExpr me) {
        println("Multiplication:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        me.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        me.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(DivExpr de) {
        println("Division:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        de.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        de.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(ModExpr me) {
        println("Modulus:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        me.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        me.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(AndExpr ae) {
        println("Boolean and:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        ae.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        ae.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(OrExpr oe) {
        println("Boolean or:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        oe.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        oe.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(LogicEqualsExpr le) {
        println("Boolean equals:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        le.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        le.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(LessThanExpr le) {
        println("Less than:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        le.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        le.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(GreaterThanExpr ge) {
        println("Greater than:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        ge.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        ge.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(LessThanEqualsExpr le) {
        println("Less than or equals:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        le.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        le.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(GreaterThanEqualsExpr ge) {
        println("Greater than or equals:");
        indentLevel++;

        println("Left expression:");
        indentLevel++;
        ge.leftExpr.accept(this);
        indentLevel--;

        println("Right expression:");
        indentLevel++;
        ge.rightExpr.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NotExpr ne) {
        println("Boolean not:");
        indentLevel++;

        println("Expression");
        indentLevel++;
        ne.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(UnMinusExpr ue) {
        println("Unary minus:");
        indentLevel++;

        println("Expression");
        indentLevel++;
        ue.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(PlusPlusExpr pe) {
        println("Postfix plusplus:");
        indentLevel++;

        println("Expression");
        indentLevel++;
        pe.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(MinusMinusExpr me) {
        println("Postfix minusminus:");
        indentLevel++;

        println("Expression");
        indentLevel++;
        me.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(FunctionCallExpr fe) {
        println("Expression function call:");
        indentLevel++;

        println("Function call:");
        indentLevel++;
        fe.functionCall.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdExpr ne) {
        println("Expression nested id:");
        indentLevel++;

        println("Nested identifier:");
        indentLevel++;
        ne.nestedIdentifier.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(StdLiteralExpr se) {
        println("Standard literal:");
        indentLevel++;

        se.stdLiteral.accept(this);

        indentLevel--;
    }

    public void visit(CoordExpr ce) {
        println("Coordinate expression:");
        indentLevel++;

        println("X coordinate:");
        indentLevel++;
        ce.expression1.accept(this);
        indentLevel--;

        println("Y coordinate:");
        indentLevel++;
        ce.expression2.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(EqualsOp eo) {
        println("=");
    }

    public void visit(PlusEqualsOp po) {
        println("+=");
    }

    public void visit(MinusEqualsOp mo) {
        println("-=");
    }

    public void visit(ModEqualsOp mo) {
        println("%=");
    }

    public void visit(MultEqualsOp mo) {
        println("*=");
    }

    public void visit(DivEqualsOp deo) {
        println("/=");
    }

    public void visit(FunctionCall f) {
        println("Function call for function:");
        indentLevel++;

        println("Function id:");
        indentLevel++;
        f.nestedIdentifier.accept(this);
        indentLevel--;

        println("Argument list:");
        indentLevel++;
        ArgumentList args = f.argumentList;
        for (int i = 0; i < args.size(); i++) {
            args.elementAt(i).accept(this);
        }
        indentLevel--;

        indentLevel--;
    }

    public void visit(ToIterator ti) {
        println("To");
    }

    public void visit(DownToIterator di) {
        println("Down to");
    }

    public void visit(VariableNestedId vi) {
        println("Variable nested id:");
        indentLevel++;

        println("Nested identifier:");
        indentLevel++;
        vi.nestedIdentifier.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(VariableStdLiteral vs) {
        println("Variable standard literal:");
        indentLevel++;

        println("Standard literal:");
        indentLevel++;
        vs.stdLiteral.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(DecimalLiteral dl) {
        println("Decimal literal, value: " + dl.decimal);
    }

    public void visit(StringLiteral sl) {
        println("String literal, value: " + sl.string);
    }

    public void visit(BooleanLiteral bl) {
        println("Boolean literal, value: " + bl.bool);
    }

    public void visit(IntegerLiteral il) {
        println("Integer literal, value: " + il.integer);
    }

    public void visit(NullLiteral nl) {
        println("Null literal");
    }

    public void visit(Decimal d) {
        println("Decimal");
    }

    public void visit(StringT s) {
        println("String");
    }

    public void visit(BooleanT b) {
        println("Boolean");
    }

    public void visit(Group g) {
        println("Group");
    }

    public void visit(Platoon p) {
        println("Platoon");
    }

    public void visit(Force f) {
        println("Force");
    }

    public void visit(Coord c) {
        println("Coord");
    }

    public void visit(Soldier s) {
        println("Soldier");
    }

    public void visit(Barrier b) {
        println("Barrier");
    }

    public void visit(VectorT v) {
        println("Vector");
    }

    public void visit(IntegerT i) {
        println("Integer");
    }

    public void visit(VoidT v) {
        println("Void");
    }

    public void visit(Terrain t) {
        println("Terrain");
    }

    public void visit(Decimal1DArray d) {
        println("Decimal 1d array");
    }

    public void visit(String1DArray s) {
        println("String 1d array");
    }

    public void visit(Boolean1DArray b) {
        println("Boolean 1d array");
    }

    public void visit(Group1DArray g) {
        println("Group 1d array");
    }

    public void visit(Platoon1DArray p) {
        println("Platoon 1d array");
    }

    public void visit(Force1DArray f) {
        println("Force 1d array");
    }

    public void visit(Coord1DArray c) {
        println("Coord 1d array");
    }

    public void visit(Soldier1DArray s) {
        println("Soldier 1d array");
    }

    public void visit(Barrier1DArray b) {
        println("Barrier 1d array");
    }

    public void visit(Vector1DArray v) {
        println("Vector 1d array");
    }

    public void visit(Integer1DArray i) {
        println("Integer 1d array");
    }

    public void visit(Terrain1DArray t) {
        println("Terrain 1d array");
    }

    public void visit(Decimal2DArray d) {
        println("Decimal 2d array");
    }

    public void visit(String2DArray s) {
        println("String 2d array");
    }

    public void visit(Boolean2DArray b) {
        println("Boolean 2d array");
    }

    public void visit(Group2DArray g) {
        println("Group 2d array");
    }

    public void visit(Platoon2DArray p) {
        println("Platoon 2d array");
    }

    public void visit(Force2DArray f) {
        println("Force 2d array");
    }

    public void visit(Coord2DArray c) {
        println("Coord 2d array");
    }

    public void visit(Soldier2DArray s) {
        println("Soldier 2d array");
    }

    public void visit(Barrier2DArray b) {
        println("Barrier 2d array");
    }

    public void visit(Vector2DArray v) {
        println("Vector 2d array");
    }

    public void visit(Integer2DArray i) {
        println("Integer 2d array");
    }

    public void visit(Terrain2DArray t) {
        println("Terrain 2d array");
    }

    public void visit(DecimalList d) {
        println("List of Decimal");
    }

    public void visit(StringList s) {
        println("List of String");
    }

    public void visit(BooleanList b) {
        println("List of Boolean");
    }

    public void visit(GroupList g) {
        println("List of Group");
    }

    public void visit(PlatoonList p) {
        println("List of Platoon");
    }

    public void visit(ForceList f) {
        println("List of Force");
    }

    public void visit(CoordList c) {
        println("List of Coord");
    }

    public void visit(SoldierList s) {
        println("List of Solder");
    }

    public void visit(BarrierList b) {
        println("List of Barrier");
    }

    public void visit(VectorList v) {
        println("List of Vector");
    }

    public void visit(IntegerList i) {
        println("List of Integer");
    }

    public void visit(TerrainList t) {
        println("List of Terrain");
    }

    public void visit(NestedIdentifierMember n) {
        println("Nested id with member:");
        indentLevel++;

        println("Parent id:");
        indentLevel++;
        n.identifier.accept(this);
        indentLevel--;

        println("Child id:");
        indentLevel++;
        n.nestedIdentifier.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdentifier1DArray n) {
        println("Nested id with 1d array for " + n.identifier.identifier + ":");
        indentLevel++;

        println("Position expression:");
        indentLevel++;
        n.expression.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdentifier1DArrayMember n) {
        println("Nested id with 1d array and member for " + n.identifier.identifier + ":");
        indentLevel++;

        println("Position expression:");
        indentLevel++;
        n.expression.accept(this);
        indentLevel--;

        println("Member nested identifier:");
        indentLevel++;
        n.nestedIdentifier.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdentifier2DArray n) {
        println("Nested id with 2d array for " + n.identifier.identifier + ":");
        indentLevel++;

        println("First position expression:");
        indentLevel++;
        n.expression1.accept(this);
        indentLevel--;

        println("Second position expression:");
        indentLevel++;
        n.expression2.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdentifier2DArrayMember n) {
        println("Nested id with 2d array and member for " + n.identifier.identifier + ":");
        indentLevel++;

        println("First position expression:");
        indentLevel++;
        n.expression1.accept(this);
        indentLevel--;

        println("Second position expression:");
        indentLevel++;
        n.expression2.accept(this);
        indentLevel--;

        println("Member nested identifier:");
        indentLevel++;
        n.nestedIdentifier.accept(this);
        indentLevel--;

        indentLevel--;
    }

    public void visit(NestedIdentifier n) {
        println("'" + n.identifier.identifier + "'");
    }

    public void visit(Identifier id) {
        println("'" + id.identifier + "'");
    }


    public void defaultVisit(Object o) {
        println("This should not happen...");
        println(o.getClass().toString());
    }
}

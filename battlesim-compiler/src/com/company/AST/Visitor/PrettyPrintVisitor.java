package com.company.AST.Visitor;

import com.company.AST.Nodes.*;

/**
 * Created by r5hej on 25-04-16.
 */
public class PrettyPrintVisitor extends Visitor implements VisitorInterface {
    private int indentLevel;

    public PrettyPrintVisitor() {
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


    public void visit(Start s){
        System.out.println("Pretty Printing:");
        System.out.println("----------------\n");

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
    }

    public void visit(DclBlock db){
        println("Begin Declarations");
        indentLevel++;

        for (int i = 0; i < db.stmtLists.size(); i++){
            db.stmtLists.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Declarations");
    }

    public void visit(SimBlock s){
        println("Begin Simulations");
        indentLevel++;

        for (int i = 0; i < s.simulationList.size(); i++){
            s.simulationList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Simulations");
    }

    public void visit(SimStep s){
        printIndent();
        print("Step " + s.stepNumber.intValue() + "\n");

        indentLevel++;

        for (int i = 0; i < s.stmtList.size(); i++){
            s.stmtList.elementAt(i).accept(this);
        }
        indentLevel--;
    }

    public void visit(Simulation s){
        printIndent();
        print("Simulation ");
        s.identifier.accept(this);
        print(" ");
        s.objectIdentifier.accept(this);
        print("\n");

        indentLevel++;
        for (int i = 0; i < s.simStepList.size(); i++){
            s.simStepList.elementAt(i).accept(this);
        }

        s.interrupts.accept(this);

        indentLevel--;

        println("End Simulation");
    }

    public void visit(Interrupts is){
        println("Interrupts");

        indentLevel++;
        for (int i = 0; i < is.stmtList.size(); i++){
            is.stmtList.elementAt(i).accept(this);
        }
        indentLevel--;
    }

    public void visit(FunctionDcl fd){
        printIndent();
        print("Function ");
        fd.returnType.accept(this);
        print(" ");
        fd.functionName.accept(this);
        print("(");

        for (int i = 0; i < fd.paramList.size(); i++){
            fd.paramList.elementAt(i).accept(this);
        }

        print(")\n");
        indentLevel++;

        for (int i = 0; i < fd.stmtList.size(); i++){
            fd.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Function");
    }

    public void visit(Param p){
        p.identifier.accept(this);
        print(" as ");
        p.typeIdentifier.accept(this);
    }

    public void visit(Program p){
        println("Begin Program");
        indentLevel++;

        for (int i = 0; i < p.stmtList.size(); i++){
            p.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End Program");
    }

    public void visit(Dcl ds){
        printIndent();
        println("Declare ");

        for (int i = 0; i < ds.dclIdList.size(); i++){
            ds.dclIdList.elementAt(i).accept(this);
            if (i < ds.dclIdList.size() - 1)
                print(", ");

        }

        print(" as ");
        ds.typeName.accept(this);
        print("\n");
    }

    public void visit(Assignment as){
        printIndent();
        as.targetName.accept(this);
        as.assignOp.accept(this);
        as.expression.accept(this);
    }


    public void visit(WhileStmt ws){
        printIndent();
        print("While ");
        ws.condition.accept(this);
        print(" Do\n");

        indentLevel++;
        for (int i = 0; i < ws.stmtList.size(); i++){
            ws.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End While");
    }

    public void visit(ForeachStmt fes){
        printIndent();
        print("For ");
        fes.typeName.accept(this);
        print(" ");
        fes.localName.accept(this);
        print(" in ");
        fes.objectName.accept(this);
        print(" Do\n");

        indentLevel++;
        for (int i = 0; i < fes.stmtList.size(); i++){
            fes.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End For");
    }

    public void visit(ForStmt fs){
        printIndent();
        fs.initialExpr.accept(this);
        print("For ");
        fs.forIterator.accept(this);
        print(" To ");
        fs.toExpr.accept(this);
        print(" Do\n");

        indentLevel++;
        for (int i = 0; i < fs.stmtList.size(); i++){
            fs.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("End For"); /*TODO*/
    }

    public void visit(IfStmt i){
        printIndent();
        print("If ");
        i.condition.accept(this);
        print(" Then\n");

        indentLevel++;
        for (int k = 0; k < i.stmtList.size(); k++){
            i.stmtList.elementAt(k).accept(this);
        }
        indentLevel--;

        if(i.elseStmt != null) {
            println("Else");
            indentLevel++;
            i.elseStmt.accept(this);
            indentLevel--;
        }
        println("End If");
    }

    public void visit(ElseIfStmt e){
        printIndent();
        print("Else If ");
        e.condition.accept(this);
        print(" Then\n");

        indentLevel++;
        for (int i = 0; i < e.stmtList.size(); i++){
            e.stmtList.elementAt(i).accept(this);
        }
        indentLevel--;

        if (e.elifStmt != null){
            println("Else");
            indentLevel++;
            e.elifStmt.accept(this);
            indentLevel--;
        }

        println("End If");
    }

    public void visit(ElseStmt e){
        printIndent();
        println("Else");

        indentLevel++;
        for (int i = 0; i < e.stmtList.size(); i++){
            e.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;
        println("Else If");
    }

    public void visit(EndIfStmt e){
        //TODO
    }

    public void visit(SwitchStmt ss){
        //TODO
    }

    public void visit(SwitchCase sc){
        //TODO
    }

    public void visit(SwitchDef sd){
        //TODO
    }


    public void visit(ReturnExpr r){
        printIndent();
        print("Return ");
        r.returnVal.accept(this);
        print("\n");
    }

    public void visit(Return r){
        //TODO
    }

    public void visit(FunctionCallStmt fcs){
        printIndent();
        fcs.functionCall.accept(this);
        //TODO
    }


    public void visit(PlusPlusStmt s){
        printIndent();
        s.id.accept(this);
        print("++\n");
    }

    public void visit(MinusMinusStmt s){
        printIndent();
        s.id.accept(this);
        print("--\n ");
    }


    public void visit(PlusExpr pe){
        leftRightStmt(pe.leftExpr, pe.rightExpr, "=" );
    }

    public void visit(MinusExpr me){
        leftRightStmt(me.leftExpr, me.rightExpr, "=");
    }

    public void visit(MultExpr me){
        leftRightStmt(me.leftExpr, me.rightExpr, "=");
    }

    public void visit(DivExpr de){
        leftRightStmt(de.leftExpr, de.rightExpr, "=");
    }

    public void visit(ModExpr me){
        leftRightStmt(me.leftExpr, me.rightExpr, "=");
    }

    public void visit(AndExpr ae){
        leftRightStmt(ae.leftExpr, ae.rightExpr, "AND");
    }

    public void visit(OrExpr oe){
        leftRightStmt(oe.leftExpr, oe.rightExpr, "OR");
    }

    public void visit(LogicEqualsExpr le){
        leftRightStmt(le.leftExpr, le.rightExpr, "EQUALS");
    }

    public void visit(LessThanExpr le){
        leftRightStmt(le.leftExpr, le.rightExpr, "<");
    }

    public void visit(GreaterThanExpr ge){
        leftRightStmt(ge.leftExpr, ge.rightExpr, ">");
    }

    public void visit(LessThanEqualsExpr le){
        leftRightStmt(le.leftExpr, le.rightExpr, "<=");
    }

    public void visit(GreaterThanEqualsExpr ge){
        leftRightStmt(ge.leftExpr, ge.rightExpr, ">=");
    }

    public void visit(NotExpr ne){
        printIndent();
        print("NOT ");
        ne.expression.accept(this);
        print("\n");
    }

    public void visit(UnMinusExpr ue){
        //TODO
    }

    public void visit(PlusPlusExpr pe){
        //TODO
    }

    public void visit(MinusMinusExpr me){
        //TODO
    }

    public void visit(FunctionCallExpr fe){
        printIndent();
        fe.functionCall.accept(this);
        print("\n");
    }

    public void visit(ObjectIdExpr ne){
        printIndent();
        ne.objectName.accept(this);
        print("\n");
    }

    public void visit(StdLiteralExpr se){
        printIndent();
        se.stdLiteral.accept(this);
        print("\n");
    }

    public void visit(CoordExpr ce){
        printIndent();
        print("coord = (");
        ce.expression1.accept(this);
        print(", ");
        ce.expression2.accept(this);
        print(")\n");
    }

    public void visit(EqualsOp eo){
        //TODO
    }

    public void visit(PlusEqualsOp po){
        //TODO
    }

    public void visit(MinusEqualsOp mo){
        //TODO
    }

    public void visit(ModEqualsOp mo){
        //TODO
    }

    public void visit(MultEqualsOp mo){
        //TODO
    }

    public void visit(DivEqualsOp deo){
        //TODO
    }

    public void visit(FunctionCall f){
        printIndent();
        f.objectName.accept(this);
        print(" (");

        for (int i = 0; i < f.argumentList.size(); i++){
            f.argumentList.elementAt(i).accept(this);
            if (i < f.argumentList.size() - 1)
                print(", ");
        }
        print(")\n");
    }

    void visit(ToIterator ti){
    }
    void visit(DownToIterator di){
    }
    void visit(VariableObjectId vi){
    }
    void visit(VariableStdLiteral vs){
    }
    void visit(DecimalLiteral dl){
    }
    void visit(StringLiteral sl){
    }
    void visit(BooleanLiteral bl){
    }
    void visit(IntegerLiteral il){
    }
    void visit(NullLiteral nl){
    }
    void visit(Array1D a){
    }
    void visit(Array2D a){
    }
    void visit(ListOf l){
    }
    void visit(Decimal d){
    }
    void visit(StringT s){
    }
    void visit(BooleanT b){
    }
    void visit(Group g){
    }
    void visit(Platoon p){
    }
    void visit(Force f){
    }
    void visit(Coord c){
    }
    void visit(Soldier s){
    }
    void visit(Barrier b){
    }
    void visit(IntegerT i){
    }
    void visit(VoidT v){
    }
    void visit(Terrain t){
    }
    void visit(ObjectReferencing o){
    }
    void visit(Array1DReferencing a){
    }
    void visit(Array2DReferencing a){
    }
    void visit(Identifier id){
    }
    void visit(JavaString j){
    }

    public void leftRightStmt(Expression left, Expression right, String symbol){
        printIndent();
        left.accept(this);
        print(" " + symbol +" ");
        right.accept(this);
        print("\n");
    }

}

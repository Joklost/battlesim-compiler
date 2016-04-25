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

    public void visit(ForStmt fs){/*

    }
    /*
    void visit(IfStmt i){
    }
    void visit(ElseIfStmt e){
    }
    void visit(ElseStmt e){
    }
    void visit(EndIfStmt e){
    }
    void visit(SwitchStmt ss){
    }
    void visit(SwitchCase sc){
    }
    void visit(SwitchDef sd){
    }
    void visit(ReturnExpr r){
    }
    void visit(Return r){
    }
    void visit(FunctionCallStmt fcs){
    }
    void visit(PlusPlusStmt s){
    }
    void visit(MinusMinusStmt s){
    }
    void visit(PlusExpr pe){
    }
    void visit(MinusExpr me){
    }
    void visit(MultExpr me){
    }
    void visit(DivExpr de){
    }
    void visit(ModExpr me){
    }
    void visit(AndExpr ae){
    }
    void visit(OrExpr oe){
    }
    void visit(LogicEqualsExpr le){
    }
    void visit(LessThanExpr le){
    }
    void visit(GreaterThanExpr ge){
    }
    void visit(LessThanEqualsExpr le){
    }
    void visit(GreaterThanEqualsExpr ge){
    }
    void visit(NotExpr ne){
    }
    void visit(UnMinusExpr ue){
    }
    void visit(PlusPlusExpr pe){
    }
    void visit(MinusMinusExpr me){
    }
    void visit(FunctionCallExpr fe){
    }
    void visit(ObjectIdExpr ne){
    }
    void visit(StdLiteralExpr se){
    }
    void visit(CoordExpr ce){
    }
    void visit(EqualsOp eo){
    }
    void visit(PlusEqualsOp po){
    }
    void visit(MinusEqualsOp mo){
    }
    void visit(ModEqualsOp mo){
    }
    void visit(MultEqualsOp mo){
    }
    void visit(DivEqualsOp deo){
    }
    void visit(FunctionCall f){
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

    */
}

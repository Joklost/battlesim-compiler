package com.company.AST.Visitor;

import com.company.AST.*;

/**
 * Created by joklost on 03-04-16.
 */
public interface IVisitor {
    void visit(Start s);
    void visit(DclBlock db);
    void visit(FunctionDcl fd);
    void visit(Param p);
    void visit(Program p);
    // Simulation
    void visit(Dcl ds);
    void visit(Assignment as);
    void visit(WhileStmt ws);
    void visit(ForeachStmt fes);
    void visit(ForStmt fs);
    //If else if then do whatever
    void visit(SwitchStmt ss);
    void visit(SwitchCase sc);
    void visit(SwitchDef sd);
    void visit(PlusExpr pe);
    void visit(MinusExpr me);
    void visit(MultExpr me);
    void visit(DivExpr de);
    void visit(ModExpr me);
    void visit(AndExpr ae);
    void visit(OrExpr oe);
    void visit(LogicEqualsExpr le);
    void visit(LessThanExpr le);
    void visit(GreaterThanExpr ge);
    void visit(LessThanEqualsExpr le);
    void visit(GreaterThanEqualsExpr ge);
    void visit(NotExpr ne);
    void visit(UnMinusExpr ue);
    void visit(PlusPlusExpr pe);
    void visit(MinusMinusExpr me);
    void visit(FunctionCallExpr fe);
    void visit(NestedIdExpr ne);
    void visit(StdLiteralExpr se);
    void visit(CoordExpr ce);
    void visit(EqualsOp eo);
    void visit(PlusEqualsOp po);
    void visit(MinusEqualsOp mo);
    void visit(ModEqualsOp mo);
    void visit(MultEqualsOp mo);
    void visit(DivEqualsOp deo);
    void visit(FunctionCall f);
    void visit(ToIterator ti);
    void visit(DownToIterator di);
    void visit(VariableNestedId vi);
    void visit(VariableStdLiteral vs);
    void visit(DecimalLiteral dl);
    void visit(StringLiteral sl);
    void visit(BooleanLiteral bl);
    void visit(IntegerLiteral il);
    void visit(NullLiteral nl);

}

package com.company.AST.Visitor;

import com.company.AST.Nodes.*;

/**
 * Created by joklost on 03-04-16.
 */
public interface VisitorInterface {

    void visit(Start s);
    void visit(DclBlock db);
    void visit(SimBlock s);
    void visit(SimStep s);
    void visit(Simulation s);
    void visit(Interrupts is);
    void visit(TypeDeclaration t);
    void visit(JavaString j);
    void visit(FunctionDcl fd);
    void visit(Param p);
    void visit(Program p);
    void visit(Dcl ds);
    void visit(Assignment as);
    void visit(WhileStmt ws);
    void visit(ForeachStmt fes);
    void visit(ForStmt fs);
    void visit(IfStmt i);
    void visit(ElseIfStmt e);
    void visit(ElseStmt e);
    void visit(EndIfStmt e);
    void visit(SwitchStmt ss);
    void visit(SwitchCase sc);
    void visit(SwitchDef sd);
    void visit(ReturnExpr r);
    void visit(Return r);
    void visit(FunctionCallStmt fcs);
    void visit(PlusPlusStmt s);
    void visit(MinusMinusStmt s);
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
    void visit(ObjectIdExpr ne);
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
    void visit(VariableObjectId vi);
    void visit(VariableStdLiteral vs);
    void visit(DecimalLiteral dl);
    void visit(StringLiteral sl);
    void visit(BooleanLiteral bl);
    void visit(IntegerLiteral il);
    void visit(NullLiteral nl);
    void visit(Array1D a);
    void visit(Array2D a);
    void visit(ListOf l);
    void visit(Decimal d);
    void visit(StringT s);
    void visit(BooleanT b);
    void visit(IntegerT i);
    void visit(VoidT v);
    void visit(CustomTypeIdentifier o);
    void visit(ObjectReferencing o);
    void visit(Array1DReferencing a);
    void visit(Array2DReferencing a);
    void visit(Identifier id);
    void visit(JavaStringStmt j);
}

package com.company.AST.Visitor;

import com.company.AST.*;

/**
 * Created by joklost on 03-04-16.
 */
public interface IVisitor {

    void visit(Start s);
    void visit(DclBlock db);
    void visit(SimBlock s);
    void visit(SimStep s);
    void visit(Simulation s);
    void visit(Interrupts is);
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
    void visit(Decimal d);
    void visit(StringT s);
    void visit(BooleanT b);
    void visit(Group g);
    void visit(Platoon p);
    void visit(Force f);
    void visit(Coord c);
    void visit(Soldier s);
    void visit(Barrier b);
    void visit(VectorT v);
    void visit(IntegerT i);
    void visit(VoidT v);
    void visit(Terrain t);
    void visit(Decimal1DArray d);
    void visit(String1DArray s);
    void visit(Boolean1DArray b);
    void visit(Group1DArray g);
    void visit(Platoon1DArray p);
    void visit(Force1DArray f);
    void visit(Coord1DArray c);
    void visit(Soldier1DArray s);
    void visit(Barrier1DArray b);
    void visit(Vector1DArray v);
    void visit(Integer1DArray i);
    void visit(Terrain1DArray t);
    void visit(Decimal2DArray d);
    void visit(String2DArray s);
    void visit(Boolean2DArray b);
    void visit(Group2DArray g);
    void visit(Platoon2DArray p);
    void visit(Force2DArray f);
    void visit(Coord2DArray c);
    void visit(Soldier2DArray s);
    void visit(Barrier2DArray b);
    void visit(Vector2DArray v);
    void visit(Integer2DArray i);
    void visit(Terrain2DArray t);
    void visit(DecimalList d);
    void visit(StringList s);
    void visit(BooleanList b);
    void visit(GroupList g);
    void visit(PlatoonList p);
    void visit(ForceList f);
    void visit(CoordList c);
    void visit(SoldierList s);
    void visit(BarrierList b);
    void visit(VectorList v);
    void visit(IntegerList i);
    void visit(TerrainList t);
    void visit(NestedIdentifierMember n);
    void visit(NestedIdentifier1DArray n);
    void visit(NestedIdentifier1DArrayMember n);
    void visit(NestedIdentifier2DArray n);
    void visit(NestedIdentifier2DArrayMember n);
    void visit(NestedIdentifier n);
    void visit(Identifier id);
}

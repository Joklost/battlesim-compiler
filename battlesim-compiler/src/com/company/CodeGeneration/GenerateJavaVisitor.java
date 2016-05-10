package com.company.CodeGeneration;

import com.company.AST.Nodes.*;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.ContextualAnalysis.TypeConsts.*;



/**
 * Created by joklost on 18-04-16.
 */

public class GenerateJavaVisitor extends Visitor implements VisitorInterface {
    private Map<String, List<String>> codeMap;
    private List<String> mainCode;
    private List<String> declCode;
    private List<String> customTypeCode;
    private int indentLevel;

    private final String main = "Main";
    private final String decl = "Declarations";

    private String emitTarget = "";

    public GenerateJavaVisitor() {
        codeMap = new HashMap<>();
        mainCode = new ArrayList<>();
        declCode = new ArrayList<>();
        codeMap.put(main, mainCode);
        codeMap.put(decl, declCode);
    }

    private void setEmitTarget(String s) {
        emitTarget = s;
    }

    private void emit(String s) {
        if (emitTarget.equals(main)) {
            mainCode.add(s);
        } else if (emitTarget.equals(decl)) {
            declCode.add(s);
        } else {
            codeMap.get(emitTarget).add(s);
        }
    }

    private void emitIndentation() {
        String spaces = "";
        for (int i = 0; i < indentLevel; i++) {
            spaces += "    ";
        }
        emit(spaces);
    }

    private void emitIndentation(String s) {
        emitIndentation();
        emit(s);
    }

    private void emitComment(String s) {
        emitIndentation();
        emit("// " + s);
    }

    public Map<String, List<String>> getCode() {
        return codeMap;
    }

    public void defaultVisit(Object o) {

    }

    public void visit(Start s) {
        ///////////////////
        /// Declarations //
        ///////////////////
        setEmitTarget(decl);
        emitIndentation("package com.BattleSim;\n");
        emitIndentation("import java.io.*;\n");
        emitIndentation("import java.util.*;\n");
        emitIndentation("import java.util.Scanner;\n");
        emitComment("BattleSim automatically generated code file.\n");
        emitIndentation("public class Declarations {\n");
        indentLevel++;
        s.dclBlock.accept(this);
        indentLevel--;
        emitIndentation("}\n");


        ///////////////////
        //// Main /////////
        ///////////////////
        setEmitTarget(main);
        emitIndentation("package com.BattleSim;\n");
        emitIndentation("import java.io.*;\n");
        emitIndentation("import java.util.*;\n");
        emitIndentation("import java.util.Scanner;\n");
        emitIndentation("import static com.BattleSim.Declarations.*;\n");
        emitComment("BattleSim automatically generated code file.\n");
        emitIndentation("public class Main {\n");

        indentLevel++;

        for (int i = 0; i < s.typeDeclarationList.size(); i++) {
            s.typeDeclarationList.elementAt(i).accept(this);
        }

        for (int i = 0; i < s.functionDclList.size(); i++) {
            s.functionDclList.elementAt(i).accept(this);
        }

        s.simBlock.accept(this);

        s.program.accept(this);

        indentLevel--;
        emitIndentation("}\n");
    }

    public void visit(DclBlock db) {
        for (int i = 0; i < db.stmtLists.size(); i++) {
            if (db.stmtLists.elementAt(i) instanceof Dcl) {
                emitIndentation("public static ");
                db.stmtLists.elementAt(i).accept(this);
            }
        }

        emitIndentation("public Declarations() {\n");
        indentLevel++;

        for (int i = 0; i < db.stmtLists.size(); i++) {
            if (!(db.stmtLists.elementAt(i) instanceof Dcl)) {
                db.stmtLists.elementAt(i).accept(this);
            }
        }

        indentLevel--;
        emitIndentation("}\n");
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

    public void visit(TypeDeclaration t) {
        String oldEmitTarget = emitTarget;
        setEmitTarget(t.name.name);

        List<String> typeCode = new ArrayList<>();
        codeMap.put(t.name.name, typeCode);

        indentLevel--;

        emitIndentation("package com.BattleSim;\n");
        emitIndentation("import java.io.*;\n");
        emitIndentation("import java.util.*;\n");
        emitIndentation("import java.util.Scanner;\n");
        emitComment("BattleSim automatically generated code file.\n");
        emitIndentation("public class ");
        t.name.accept(this);
        emit(" {\n");    //Sørg for at tilføje SimObj

        indentLevel++;

        for (int i = 0; i < t.declarationList.size(); i++) {
            t.declarationList.elementAt(i).accept(this);
        }

        for (int i = 0; i <t.functionDclList.size(); i++) {
            t.functionDclList.elementAt(i).accept(this);
        }

        for (int i = 0; i < t.javaStringList.size(); i++) {
            t.javaStringList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("}\n");

        setEmitTarget(oldEmitTarget);

        indentLevel++;
    }

    public void visit(JavaString j) {
        emitIndentation(j.javaCode.substring(3));
    }

    public void visit(FunctionDcl fd) {
        emitIndentation("public ");
        if (emitTarget == main) {
            emit("static ");
        }
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
        emitIndentation("Declarations decl795030_declarationblock = new Declarations();\n");

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

        if (ds.typeName instanceof ListOf) {
            emit(" = new ArrayList<>()");
        } else if (ds.typeName instanceof Array1D) {
            //int[] navn = new int[10];
            emit(" = new ");
            ((Array1D) ds.typeName).typeName.accept(this);
            emit("[");
            ((Array1D) ds.typeName).index.accept(this);
            emit("]");
        } else if (ds.typeName instanceof Array2D) {
            emit(" = new ");
            ((Array2D) ds.typeName).typeName.accept(this);
            emit("[");
            ((Array2D) ds.typeName).index1.accept(this);
            emit("][");
            ((Array2D) ds.typeName).index2.accept(this);
            emit("]");
        } else if (ds.typeName instanceof BooleanT) {
            emit(" = false");
        } else if (ds.typeName instanceof IntegerT) {
            emit(" = 0");
        } else if (ds.typeName instanceof StringT) {
            emit(" = \"\"");
        } else if (ds.typeName instanceof Decimal) {
            emit(" = 0.0");
        } else if (ds.typeName instanceof CustomTypeIdentifier) {
            emit(" = new " + ((CustomTypeIdentifier) ds.typeName).name.name + "()");
        }

        /*else if (ds.typeName instanceof Barrier){
            emit(" = new Barrier()");
        } else if (ds.typeName instanceof Coord){
            emit(" = new Coord()");
        } else if (ds.typeName instanceof Force){
            emit(" = new Force()");
        } else if (ds.typeName instanceof Group){
            emit(" = new Group()");
        } else if (ds.typeName instanceof Terrain){
            emit(" = new Terrain()");
        } else if (ds.typeName instanceof Platoon){
            emit(" = new Platoon()");
        } else if (ds.typeName instanceof Soldier) {
            emit(" = new Soldier()");
        }*/
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
        emitIndentation("for (int i795030" + indentLevel + "_forloop=");
        fs.initialExpr.accept(this);
        emit("; ");
        if (fs.forIterator instanceof ToIterator) {
            emit("i795030" + indentLevel + "_forloop<");
            fs.toExpr.accept(this);
            emit("; i795030" + indentLevel + "_forloop++) {\n");
        } else {
            emit("i795030" + indentLevel + "_forloop>");
            fs.toExpr.accept(this);
            emit("; i795030" + indentLevel + "_forloop--) {\n");
        }

        indentLevel++;

        for (int i = 0; i < fs.stmtList.size(); i++) {
            fs.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("}\n");
    }

    public void visit(IfStmt i) {
        emitIndentation("if (");
        i.condition.accept(this);
        emit(") {\n");

        indentLevel++;

        for (int k = 0; k < i.stmtList.size(); k++) {
            i.stmtList.elementAt(k).accept(this);
        }

        indentLevel--;

        emitIndentation("} ");
        i.elseStmt.accept(this);
    }

    public void visit(ElseIfStmt e) {
        emit("else if (");
        e.condition.accept(this);
        emit(") {\n");

        indentLevel++;

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("} ");
        e.elifStmt.accept(this);
    }

    public void visit(ElseStmt e) {
        emit("else {\n");

        indentLevel++;

        for (int i = 0; i < e.stmtList.size(); i++) {
            e.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation("}\n");

    }

    public void visit(EndIfStmt e) {
        emit("\n");
    }

    public void visit(SwitchStmt ss) {
        emitIndentation("switch (");
        ss.variable.accept(this);
        emit(") {\n");

        indentLevel++;

        for (int i = 0; i < ss.switchCaseList.size(); i++) {
            ss.switchCaseList.elementAt(i).accept(this);
        }

        ss.switchDef.accept(this);

        indentLevel--;

        emitIndentation("}\n");

    }

    public void visit(SwitchCase sc) {
        emitIndentation("case ");
        sc.label.accept(this);
        emit(":\n");

        indentLevel++;

        for (int i = 0; i < sc.stmtList.size(); i++) {
            sc.stmtList.elementAt(i).accept(this);
        }

        emitIndentation("break;\n");

        indentLevel--;
    }

    public void visit(SwitchDef sd) {
        emitIndentation("default:\n");
        indentLevel++;

        for (int i = 0; i < sd.stmtList.size(); i++) {
            sd.stmtList.elementAt(i).accept(this);
        }


        emitIndentation("break;\n");

        indentLevel--;
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

    public void visit(PlusPlusStmt s) {
        emitIndentation();
        s.id.accept(this);
        emit("=");
        s.id.accept(this);
        emit("+1;\n");
    }

    public void visit(MinusMinusStmt s) {
        emitIndentation();
        s.id.accept(this);
        emit("=");
        s.id.accept(this);
        emit("-1;\n");
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
        emit("(");
        ce.expression1.accept(this);
        emit(", ");
        ce.expression2.accept(this);
        emit(")");
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

    public void visit(ToIterator ti) {}

    public void visit(DownToIterator di) {}

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
        a.typeName.accept(this);
        emit("[]");
        //int[] navn = new int[10];
    }

    public void visit(Array2D a) {
        a.typeName.accept(this);
        emit("[][]");
    }

    public void visit(ListOf l) {
        emit("ArrayList<");
        if (l.typeName.type == integerType) {
            emit("Integer");
        } else if (l.typeName.type == stringType) {
            emit("String");
        } else if (l.typeName.type == booleanType) {
            emit("Boolean");
        } else if (l.typeName.type == decimalType) {
            emit("Double");
        } else if (l.typeName instanceof CustomTypeIdentifier) {
            emit(((CustomTypeIdentifier) l.typeName).name.name);
        }
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
/*
    public void visit(Group g) {
        emit("Group");

    }

    public void visit(Platoon p) {
        emit("Platoon");

    }

    public void visit(Force f) {
        emit("Force");

    }

    public void visit(Coord c) {
        emit("Coord");

    }

    public void visit(Soldier s) {
        emit("Soldier");

    }

    public void visit(Barrier b) {
        emit("Barrier");

    }

    public void visit(VectorT v) {
        // TODO

    }
    public void visit(Terrain t) {
        emit("Terrain");
    }

*/
    public void visit(IntegerT i) {
        emit("int");
    }

    public void visit(VoidT v) {
        emit("void");

    }

    public void visit(CustomTypeIdentifier o) {
        emit(o.name.name);
    }


    public void visit(ObjectReferencing o) {
        o.objectName.accept(this);
        emit(".");
        o.fieldName.accept(this);
    }

    public void visit(Array1DReferencing a) {
        a.arrayName.accept(this);
        emit("[");
        a.indexExpr.accept(this);
        emit("]");
    }

    public void visit(Array2DReferencing a) {
        a.arrayName.accept(this);
        emit("[");
        a.firstIndexExpr.accept(this);
        emit("][");
        a.secondIndexExpr.accept(this);
        emit("]");
    }

    public void visit(Identifier id) {
        emit(id.name);
    }

    public void visit(JavaStringStmt j) {
        emitIndentation(j.javaCode.substring(3));
    }


}

package com.company.CodeGeneration;

import com.company.AST.Nodes.*;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;

import java.util.*;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.*;



/**
 * Created by joklost on 18-04-16.
 */

public class GenerateJavaVisitor extends Visitor implements VisitorInterface {
    private Map<String, List<String>> codeMap;
    private List<String> mainCode;
    private List<String> declCode;
    private List<String> customTypeCode;
    private int indentLevel;
    private int instructionNumber;

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

    public void emitAtIndex(int index, String s){
        if (emitTarget.equals(main)) {
            mainCode.add(index, s);
        } else if (emitTarget.equals(decl)) {
            declCode.add(index, s);
        } else {
            codeMap.get(emitTarget).add(index, s);
        }
    }

    private void emitIndentationAtIndex(int index) {
        String spaces = "";
        for (int i = 0; i < indentLevel; i++) {
            spaces += "    ";
        }
        emitAtIndex(index, spaces);
    }

    private void emitIndentationAtIndex(int index, String s) {
        emitIndentationAtIndex(index);
        emitAtIndex(index, s);
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
        emitIndentation("import java.util.HashMap;\n");
        emitComment("BattleSim automatically generated code file.\n");
        emitIndentation("public class Declarations {\n");
        indentLevel++;
        emitIndentation("public static HashMap<String, SimObj> SimObjMap = new HashMap<String, SimObj>();\n");
        emitIndentation("public static ArrayList<Barrier> barriers = new ArrayList<Barrier>();\n");
        s.dclBlock.accept(this);
        indentLevel--;
        emitIndentation("}\n");

        ///////////////////
        //// SimBlock//////
        ///////////////////
        s.simBlock.accept(this);

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

        emitIndentation("public static void main(String[] args) {\n");
        indentLevel++;
        emitIndentation("Declarations decl795030_declarationblock = new Declarations();\n");

        for(int i = 0; i < s.simBlock.simulationList.size(); i++){
            String simName = s.simBlock.simulationList.elementAt(i).identifier.name;
            emitIndentation("Simulation " + simName.toLowerCase() + " = new " + simName + "(SimObjMap);\n");
        }

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

        for(int i = 0; i < db.stmtLists.size(); i++){
            if(db.stmtLists.elementAt(i) instanceof Dcl) {
                for (int k = 0; k < ((Dcl) db.stmtLists.elementAt(i)).dclIdList.size(); k++) {
                    if(((Dcl) db.stmtLists.elementAt(i)).typeName instanceof CustomTypeIdentifier){
                        String typeName = ((CustomTypeIdentifier) ((Dcl) db.stmtLists.elementAt(i)).typeName).name.name;
                        String name = ((Dcl) db.stmtLists.elementAt(i)).dclIdList.elementAt(k).name;
                        if(typeName.equals("Barrier")){
                            emitIndentation("barriers.add(" + name + ");\n");
                        }else{
                            emitIndentation("SimObjMap.put(\"" + name + "\", " + name + ");\n");
                        }
                    }

                }
            }
        }
        indentLevel--;
        emitIndentation("}\n");
    }

    public void visit(SimBlock s) {
        for(int i = 0; i < s.simulationList.size(); i++){
            s.simulationList.elementAt(i).accept(this);
        }

    }

    public void visit(SimStep s) {
        // TODO

    }

    public void visit(Simulation s) {
        codeMap.put(s.identifier.name, new ArrayList<String>());
        setEmitTarget(s.identifier.name);
        emitIndentation("package com.BattleSim;\n");
        emitIndentation("import java.util.HashMap;\n");
        emitIndentation("import static com.BattleSim.Declarations.*;\n");
        emitIndentation("public class ");
        emit(s.identifier.name);
        emit(" extends Simulation {\n");

        indentLevel++;
        emitIndentation("public ");
        emit(s.identifier.name);
        emit("(HashMap<String, SimObj> simObjMap){\n");
        indentLevel++;
        emitIndentation("super(simObjMap);\n");
        //her skal der emittes til første del
        indentLevel--;

        codeMap.put(s.identifier.name + "1", new ArrayList<String>());
        setEmitTarget(s.identifier.name + "1");
        emitIndentation("public void Run(double deltaT){\n");
        indentLevel++;
        instructionNumber = 0;
        for(int i = 0; i < s.simStepList.size(); i++){
            for(int k = 0; k < s.simStepList.elementAt(i).stmtList.size(); k++){
                s.simStepList.elementAt(i).stmtList.elementAt(k).accept(this);
                //her skal der emittes til anden del
            }
        }

        indentLevel--;
        emitIndentation("}\n");

        s.interrupts.accept(this);

        setEmitTarget(s.identifier.name);
        emitIndentation("}\n");

        setEmitTarget(s.identifier.name + "1");
        indentLevel--;
        emitIndentation("}\n");

        codeMap.get(s.identifier.name).addAll(codeMap.get(s.identifier.name + "1")); //Sæt anden del i enden på første del
        codeMap.remove(s.identifier.name + "1"); //slet anden del
    }

    public void visit(Interrupts is) {
        emitIndentation("public void runInterrupts(double deltaT){\n");
        indentLevel++;
        for(int i = 0 ; i < is.stmtList.size(); i++){
            is.stmtList.elementAt(i).accept(this);
        }
        indentLevel --;
        emitIndentation("}\n");
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
        emit(" extends SimObj{\n");    //Sørg for at tilføje SimObj

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

        for (int i = 0; i < p.stmtList.size(); i++) {
            if(p.stmtList.elementAt(i) instanceof FunctionCallStmt){
                FunctionCall funcCall = ((FunctionCallStmt) p.stmtList.elementAt(i)).functionCall;

                //Handle functioncall RunSimulation
                if(funcCall.objectName.name.equals("RunSimulation")){
                    emitIndentation("BasicFrame ex = new BasicFrame(");
                    for (int k = 0; k < funcCall.argumentList.size(); k++) {
                        if(k == 3 || k == 4){
                            funcCall.argumentList.elementAt(k).accept(this);
                            String str = codeMap.get(main).get(codeMap.get(main).size() - 1);
                            codeMap.get(main).set(codeMap.get(main).size() - 1, str.replace('"', ' ').toLowerCase());
                        }else{
                            funcCall.argumentList.elementAt(k).accept(this);
                        }
                        if (k < funcCall.argumentList.size() - 1) emit(", ");
                    }
                    emit(", barriers);\n");
                    emitIndentation("ex.setVisible(true);");
                }
                //Handle rest in Program
                else{
                    p.stmtList.elementAt(i).accept(this);
                }
            }
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
            ((Array1D) ds.typeName).size.accept(this);
            emit("]");
        } else if (ds.typeName instanceof Array2D) {
            emit(" = new ");
            ((Array2D) ds.typeName).typeName.accept(this);
            emit("[");
            ((Array2D) ds.typeName).size1.accept(this);
            emit("][");
            ((Array2D) ds.typeName).size2.accept(this);
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
        ss.control.accept(this);
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
        emit(" new Coord(");
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
        if(f.objectName instanceof ObjectReferencing){
            String funcName = ((ObjectReferencing) f.objectName).fieldName.name;
            if(funcName.equals("MoveToXY") || funcName.equals("Wait") || funcName.equals("MoveToCoord")){
                visitInstruction(f, funcName);
            }
            else{
                visitFunctionCall(f);
            }
        }
        else{
            visitFunctionCall(f);
        }
    }

    public void visitInstruction(FunctionCall f, String instruction){
        emit("Steps.get(" + instructionNumber + ").RunIfCanStart(deltaT)");
        instructionNumber++;
        setEmitTarget(emitTarget.substring(0, emitTarget.length() - 1)); //set emitTarget from SimulationName1 to SimulationName
        if(instruction.equals("MoveToXY")) {
            emitIndentation("Steps.add(new MoveStep(SimObjMap.get(\"");
            if(f.objectName instanceof ObjectReferencing){
                ((ObjectReferencing) f.objectName).objectName.accept(this);
            }
            emit("\"), new Coord(");
            f.argumentList.elementAt(0).accept(this);       //x
            emit(", ");
            f.argumentList.elementAt(1).accept(this);       //y
            emit(")));\n");
        }
        if(instruction.equals("MoveToCoord")){
            emitIndentation("Steps.add(new MoveStep(SimObjMap.get(\"");
            if(f.objectName instanceof ObjectReferencing){
                ((ObjectReferencing) f.objectName).objectName.accept(this);
            }
            emit("\"), ");
            f.argumentList.elementAt(0).accept(this);       //Coord
            emit("));\n");
        }
        if(instruction.equals("Wait")){
            emitIndentation("Steps.add(new WaitStep(SimObjMap.get(\"");
            if(f.objectName instanceof ObjectReferencing){
                ((ObjectReferencing) f.objectName).objectName.accept(this);
            }
            emit("\"), ");
            f.argumentList.elementAt(0).accept(this);       //Seconds
            emit("));\n");
        }
        setEmitTarget(emitTarget + "1");                    //change emitTarget back to SimulationName1
    }

    public void visitFunctionCall(FunctionCall f){
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

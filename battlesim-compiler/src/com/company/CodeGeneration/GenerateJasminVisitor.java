package com.company.CodeGeneration;

import com.company.AST.Nodes.*;
import com.company.AST.Visitor.Visitor;
import com.company.AST.Visitor.VisitorInterface;
import com.company.CodeGeneration.RegisterEntry;
import com.company.ContextualAnalysis.HelperClasses.TypeConsts;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.*;

/**
 * Created by joklost on 18-04-16.
 * assembly copy by pgug on 13-05-16
 */

public class GenerateJasminVisitor extends Visitor implements VisitorInterface {
    private Map<String, List<String>> codeMap;
    private List<String> mainCode;
    private List<String> declCode;
    private List<String> customTypeCode;
    private int indentLevel;

    private List<RegisterEntry> registerEntries = new ArrayList<>();
    private int registerUsed = 0;
    private int lastRegisterUsed = 0;

    private final String main = "Main";
    private final String setup = "Setup";

    private String emitTarget = "";

    public GenerateJasminVisitor() {
        codeMap = new HashMap<>();
        mainCode = new ArrayList<>();
        declCode = new ArrayList<>();
        codeMap.put(main, mainCode);
        codeMap.put(setup, declCode);
    }

    private void setEmitTarget(String s) {
        emitTarget = s;
    }

    private void emit(String s) {
        if (emitTarget.equals(main)) {
            mainCode.add(s);
        } else if (emitTarget.equals(setup)) {
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
        emit("; " + s);
    }

    public Map<String, List<String>> getCode() {
        return codeMap;
    }

    public void defaultVisit(Object o) {

    }

    public void visit(Start s) {
        setEmitTarget(setup);

        emitIndentation(".class public Main\n");
        emitIndentation(".super java/lang/Object\n");

        emitIndentation(".method public <init>()V\n");
        indentLevel++;
        emitIndentation("aload_0\n");
        emitIndentation("invokespecial java/lang/Object/<init>()V\n");
        emitIndentation("return\n");
        indentLevel--;
        emitIndentation(".end method\n");

        s.simBlock.accept(this);

        setEmitTarget(main);

        for (int i = 0; i < s.typeDeclarationList.size(); i++) {
            s.typeDeclarationList.elementAt(i).accept(this);
        }

        for (int i = 0; i < s.functionDclList.size(); i++) {
            s.functionDclList.elementAt(i).accept(this);
        }

        s.program.accept(this);
    }

    public void visit(DclBlock db) {

        /**
         * Declaration block is not used, that is
         *   of course a bad idea but not
         *   "strictly" needed.
         */
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
        // TODO
    }

    public void visit(JavaString j) {
        emitIndentation(j.javaCode.substring(3));
    }

    public void visit(FunctionDcl fd) {
        emitIndentation(".method public ");
        if (emitTarget == main) {
            emit("static ");
        }
        fd.functionName.accept(this);
        emit("(");
        for (int i = 0; i < fd.paramList.size(); i++) {
            fd.paramList.elementAt(i).accept(this);
            if (i < fd.paramList.size() - 1) emit(", ");
        }
        switch (fd.returnType.type) {
            case 4:
                emit(")V\n");
                break;
            case 1:
                emit(")I\n");
                break;
            case 3:
                emit(")Z\n");
                break;
            case 6:
                emit(")D\n");
                break;
            default:
                emit(")\n");
        }

        indentLevel++;

        for (int i = 0; i < fd.stmtList.size(); i++) {
            fd.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emitIndentation(".end method\n");
    }

    public void visit(Param p) {
        p.typeIdentifier.accept(this);
    }

    public void visit(Program p) {
        emitIndentation(".method public static main([Ljava/lang/String;)V\n");
        indentLevel++;
        emitIndentation(".limit stack 256\n");
        emitIndentation(".limit locals 256\n");

        for (int i = 0; i < p.stmtList.size(); i++) {
            p.stmtList.elementAt(i).accept(this);
        }

        emitIndentation("return\n");
        indentLevel--;
        emitIndentation(".end method\n");
    }

    public void visit(Dcl ds) {
        /**
         * This is where things gets declared
         * it would be wise to make a note of
         *  what is declared, and where it is.
         */

        RegisterEntry regEnt = new RegisterEntry();

        if(ds.typeName instanceof Decimal) {
            regEnt.register = registerUsed + 1;
            regEnt.variableName = ds.dclIdList.elementAt(0).name;
            registerEntries.add(regEnt);
            lastRegisterUsed = regEnt.register;
            registerUsed = regEnt.register + 1; // Decimal (double) fills up two words
        }
        if(ds.typeName instanceof IntegerT) {
            regEnt.register = registerUsed + 1;
            regEnt.variableName = ds.dclIdList.elementAt(0).name;
            registerEntries.add(regEnt);
            lastRegisterUsed = regEnt.register;
            registerUsed = regEnt.register;
        }
    }

    public void visit(Assignment as) {

        for (RegisterEntry reg : registerEntries) {
            if(reg.variableName.equals(as.targetName.name)) {
                lastRegisterUsed = reg.register;
                switch (as.type) {
                    case TypeConsts.integerType:
                        if(as.expression instanceof PlusExpr || as.expression instanceof MinusExpr || as.expression instanceof DivExpr || as.expression instanceof ModExpr || as.expression instanceof PlusPlusExpr || as.expression instanceof MinusMinusExpr) {
                            as.expression.accept(this);
                        } else {
                            emitIndentation("ldc ");
                            as.expression.accept(this);
                            emit("\n");
                            emitIndentation("istore ");
                            emit(reg.register+"\n");
                        }
                        emit("\n");
                        break;
                    case TypeConsts.decimalType:
                        if(as.expression instanceof PlusExpr || as.expression instanceof MinusExpr || as.expression instanceof DivExpr || as.expression instanceof ModExpr  || as.expression instanceof PlusPlusExpr || as.expression instanceof MinusMinusExpr) {
                            as.expression.accept(this);
                            lastRegisterUsed = reg.register;
                        } else {
                            emitIndentation("ldc2_w ");
                            as.expression.accept(this);
                            emit("\n");
                            emitIndentation("dstore ");
                            emit(reg.register+"\n");
                        }
                        emit("\n");
                        break;
                }
            }
        }
    }

    public void visit(WhileStmt ws) {
        // TODO
    }

    public void visit(ForeachStmt fes) {
        // TODO
    }

    public void visit(ForStmt fs) {
        RegisterEntry regEntFROM = new RegisterEntry();
        regEntFROM.variableName = "fori795030" + indentLevel + "_forloopFROM";
        regEntFROM.register = registerUsed + 1;
        registerEntries.add(regEntFROM);
        registerUsed = registerUsed + 1;
        int registerFROM = registerUsed;

        RegisterEntry regEntTO = new RegisterEntry();
        regEntTO.variableName = "fori795030" + indentLevel + "_forloopTO";
        regEntTO.register = registerUsed + 1;
        registerEntries.add(regEntTO);
        registerUsed = registerUsed + 1;
        int registerTO = registerUsed;

        emit("bipush ");
        fs.initialExpr.accept(this);
        emit("\nistore ");
        emit(registerFROM+"\n");

        emit("bipush ");
        fs.toExpr.accept(this);
        emit("\nistore ");
        emit(registerTO+"\n");

        if (!(fs.forIterator instanceof ToIterator)) {
            emit("iload " + registerFROM + "\n");
            emit("iload " + registerTO + "\n");
            emit("istore " + registerFROM + "\n");
            emit("istore " + registerTO + "\n");
        }

        // From 1 To 10 (10 times)
        //emit("iload " + registerTO + "\n");
        //emit("bipush 1\n");
        //emit("iadd\n");
        //emit("istore " +registerTO + "\n");

        emit("iload " + registerFROM + "\n");
        emit("ineg\n");
        emit("istore " + registerFROM + "\n");

        emit("Loop" + registerFROM + ":\n");
        emit("iload " + registerTO + "\n");
        emit("bipush -1\n");
        emit("iadd\n");
        emit("istore " +registerTO + "\n");

        indentLevel++;

        for (int i = 0; i < fs.stmtList.size(); i++) {
            fs.stmtList.elementAt(i).accept(this);
        }

        indentLevel--;

        emit("iload " + registerTO + "\n");
        emit("iload " + registerFROM + "\n");
        emit("iadd\n");
        emit("ifne Loop" + registerFROM + "\n");
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
        emit("\n");
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
        emitIndentation("return\n");
    }

    public void visit(Return r) {
        emitIndentation("return\n");
    }

    public void visit(FunctionCallStmt fcs) {
        // TODO
    }

    public void visit(PlusPlusStmt s) {
        // TODO
    }

    public void visit(MinusMinusStmt s) {
        // TODO
    }

    public void visit(PlusExpr pe) {
        switch (pe.leftExpr.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                pe.leftExpr.accept(this);
                emit("\n");

                emit("ldc ");
                pe.rightExpr.accept(this);
                emit("\n");

                emit("iadd\n");

                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                pe.leftExpr.accept(this);
                emit("\n");

                emit("ldc2_w ");
                pe.rightExpr.accept(this);
                emit("\n");

                emit("dadd\n");

                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;
        }
    }

    public void visit(MinusExpr me) {
        switch (me.leftExpr.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("isub\n");

                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc2_w ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("dsub\n");
                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;
        }
    }

    public void visit(MultExpr me) {
        switch (me.leftExpr.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("imul\n");
                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc2_w ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("dmul\n");
                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;

            default:
                emit("; unknown type");
        }
    }

    public void visit(DivExpr de) {
        switch (de.leftExpr.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                de.leftExpr.accept(this);
                emit("\n");

                emit("ldc ");
                de.rightExpr.accept(this);
                emit("\n");

                emit("idiv\n");

                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                de.leftExpr.accept(this);
                emit("\n");

                emit("ldc2_w ");
                de.rightExpr.accept(this);
                emit("\n");

                emit("ddiv\n");

                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;
        }
    }

    /*
    Java Virtual Machine (Meyer & Downing) P.128
     Says irem = modulus of two integers
          drem = remainder of two doubles
     we decided it must both mean something
     like modulus...
    */
    public void visit(ModExpr me) {
        switch (me.leftExpr.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("irem\n");

                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                me.leftExpr.accept(this);
                emit("\n");

                emit("ldc2_w ");
                me.rightExpr.accept(this);
                emit("\n");

                emit("drem\n");

                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;

            default:
                emit("; unknown type");
        }
    }

    public void visit(AndExpr ae) {
        // TODO
        // Something like: (bitwise and)
        emit("ldc ");
        ae.leftExpr.accept(this);
        emit("\n");
        emit("ldc ");
        ae.rightExpr.accept(this);
        emit("\n");
        emit("iand\n");
    }

    public void visit(OrExpr oe) {
        // TODO
        // Something like: (bitwise or)
        emit("ldc ");
        oe.leftExpr.accept(this);
        emit("\n");
        emit("ldc ");
        oe.rightExpr.accept(this);
        emit("\n");
        emit("ior\n");
    }

    public void visit(LogicEqualsExpr le) {
        // TODO
    }

    public void visit(LessThanExpr le) {
        // TODO
    }

    public void visit(GreaterThanExpr ge) {
        // TODO
    }

    public void visit(LessThanEqualsExpr le) {
        // TODO
    }

    public void visit(GreaterThanEqualsExpr ge) {
        // TODO
    }

    public void visit(NotExpr ne) {
        // TODO
    }

    public void visit(UnMinusExpr ue) {
        // TODO
    }

    public void visit(PlusPlusExpr pe) {
        switch (pe.expression.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                pe.expression.accept(this);
                emit("\n");
                emit("ldc 1\n");
                emit("iadd\n");
                emit("istore ");
                emit(lastRegisterUsed+"\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                pe.expression.accept(this);
                emit("\n");
                emit("dconst_1\n");
                emit("dadd\n");
                emit("dstore ");
                emit(lastRegisterUsed+"");
                break;
        }
    }

    public void visit(MinusMinusExpr me) {
        switch (me.expression.type) {
            case TypeConsts.integerType:
                emit("ldc ");
                me.expression.accept(this);
                emit("\n");
                emit("ldc -1\n");
                emit("iadd\n");

                emit("istore ");
                emit(lastRegisterUsed + "\n");
                break;
            case TypeConsts.decimalType:
                emit("ldc2_w ");
                me.expression.accept(this);
                emit("\n");
                emit("ldc2_w -1.0\n");
                emit("dadd\n");

                emit("dstore ");
                emit(lastRegisterUsed + "\n");
                break;
        }
    }

    public void visit(FunctionCallExpr fe) {
        // TODO
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
        // TODO
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
    }

    public void visit(Array2D a) {
        // TODO
    }

    public void visit(ListOf l) {
        // TODO
    }

    public void visit(Decimal d) {
        emit("D");
    }

    public void visit(StringT s) {
        emit("Ljava/lang/String;");
    }

    public void visit(BooleanT b) {
        emit("Z");
    }

    public void visit(IntegerT i) {
        emit("I");
    }

    public void visit(VoidT v) { emit("V"); }

    public void visit(CustomTypeIdentifier o) {
        emit(o.name.name);
    }


    public void visit(ObjectReferencing o) {
        // TODO
    }

    public void visit(Array1DReferencing a) {
        // TODO
    }

    public void visit(Array2DReferencing a) {
        // TODO
    }

    public void visit(Identifier id) {
        emit(id.name);
    }

    public void visit(JavaStringStmt j) {
        emitIndentation(j.javaCode.substring(3));
    }


}

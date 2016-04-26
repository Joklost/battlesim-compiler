package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.Main;

import static com.company.AST.SymbolTable.SymbolTable.*;
import static com.company.ContextualAnalysis.TypeConsts.*;

/**
 * Created by joklost on 12-04-16.
 */
public class TopDeclVisitor extends SemanticsVisitor {

    private void errorDeclaredLocally(String id) {
        Main.errorFound = true;
        System.err.println(id + " has already been declared at level " + symbolTable.getLevel() + ".");
    }

    public TopDeclVisitor() {

    }

    // Tror den er crisp - jkj
    public void visit(Dcl d) {
        TypeVisitor typeVisitor = new TypeVisitor();
        d.typeName.accept(typeVisitor);     // sætter d.typename.type
        // s. 311 i bogen: ArrayDefining foregår i typeVisitor

        for (int i = 0; i < d.dclIdList.size(); i++) {
            String id = d.dclIdList.elementAt(i).name;
            if (symbolTable.declaredLocally(id)) {
                errorDeclaredLocally(id);
                d.dclIdList.elementAt(i).type = errorType;
                d.dclIdList.elementAt(i).def = null;
            } else {
                d.dclIdList.elementAt(i).type = d.typeName.type;
                TypeIdentifier def = d.typeName;
                def.type = d.typeName.type;
                d.dclIdList.elementAt(i).def = def;
                //System.out.println(id);
                symbolTable.enterSymbol(id, def);
            }
        }

    }


    // Tror den er crisp - jkj
    public void visit(FunctionDcl f) {
        TypeVisitor typeVisitor = new TypeVisitor();
        f.returnType.accept(typeVisitor);

        if (symbolTable.declaredLocally(f.functionName.name)) {
            errorDeclaredLocally(f.functionName.name);
            f.type = errorType;
        } else {
            symbolTable.enterSymbol(f.functionName.name, f);
            f.type = functionType;
        }

        symbolTable.openScope();

        FunctionDcl oldCurrentFunction = currentFunction;
        currentFunction = f;

        for (int i = 0; i < f.paramList.size(); i++) {
            f.paramList.elementAt(i).accept(this);
        }

        for (int i = 0; i < f.stmtList.size(); i++) {
            f.stmtList.elementAt(i).accept(this);
        }

        currentFunction = oldCurrentFunction;

        symbolTable.closeScope();

        boolean containsReturnExpr = false;
        if (f.returnType.type != voidType && f.returnType.type != errorType) {
            for (int i = 0; i < f.stmtList.size(); i++) {
                if (f.stmtList.elementAt(i) instanceof ReturnExpr) {
                    containsReturnExpr = true;
                }

            }

            if (!containsReturnExpr) {
                error(f.getLineNumber(), "Function " + f.functionName.name + " must return a value.");
                f.type = errorType;
            }
        }
    }

    public void visit(Param p) {
        TypeVisitor typeVisitor = new TypeVisitor();
        p.typeIdentifier.accept(typeVisitor);

        if (symbolTable.declaredLocally(p.identifier.name)) {
            errorDeclaredLocally(p.identifier.name);
            p.type = errorType;
        } else {
            symbolTable.enterSymbol(p.identifier.name, p.typeIdentifier);
            p.type = p.typeIdentifier.type;
        }
    }

    public void visit(Simulation s) {
        if (symbolTable.declaredLocally(s.identifier.name)) {
            errorDeclaredLocally(s.identifier.name);
            s.type = errorType;
        } else {
            symbolTable.enterSymbol(s.identifier.name, s);
            s.type = simulationType;
        }

        s.objectIdentifier.accept(this);

        symbolTable.openScope();

        for (int i = 0; i < s.simStepList.size(); i++) {
            s.simStepList.elementAt(i).accept(this);
        }

        s.interrupts.accept(this);

        symbolTable.closeScope();

    }

    public void visit(SimStep s) {
        if (symbolTable.declaredLocally("Step" + s.stepNumber)) {
            errorDeclaredLocally("Step" + s.stepNumber);
            s.type = errorType;
        } else {
            symbolTable.enterSymbol("Step" + s.stepNumber, s);
            s.type = simulationStepType;
        }

        symbolTable.openScope();

        for (int i = 0; i < s.stmtList.size(); i++) {
            s.stmtList.elementAt(i).accept(this);
        }

        symbolTable.closeScope();
    }
    
}

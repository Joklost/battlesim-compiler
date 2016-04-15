package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;

import static com.company.AST.SymbolTable.SymbolTable.*;
import static com.company.ContextualAnalysis.TypeConsts.*;

/**
 * Created by joklost on 12-04-16.
 */
public class TopDeclVisitor extends SemanticsVisitor {

    private boolean debug = false;

    private void errorDeclaredLocally(String id) {
        System.err.println(id + " has already been declared at level " + getLevel() + ".");
    }

    // Tror den er crisp - jkj
    public void visit(Dcl d) {
        TypeVisitor typeVisitor = new TypeVisitor();
        d.typeName.accept(typeVisitor);     // sætter d.typename.type
        // s. 311 i bogen: ArrayDefining foregår i typeVisitor

        for (int i = 0; i < d.dclIdList.size(); i++) {
            String id = d.dclIdList.elementAt(i).name;
            if (declaredLocally(id)) {
                errorDeclaredLocally(id);
                d.dclIdList.elementAt(i).type = errorType;
                d.dclIdList.elementAt(i).def = null;
            } else {
                d.dclIdList.elementAt(i).type = d.typeName.type;
                TypeIdentifier def = d.typeName;
                def.type = d.typeName.type;
                d.dclIdList.elementAt(i).def = def;
                //System.out.println(id);
                enterSymbol(id, def);
            }
        }

    }


    // Tror den er crisp - jkj
    public void visit(FunctionDcl f) {
        TypeVisitor typeVisitor = new TypeVisitor();
        f.returnType.accept(typeVisitor);

        if (declaredLocally(f.functionName.name)) {
            errorDeclaredLocally(f.functionName.name);
            f.type = errorType;
        } else {
            enterSymbol(f.functionName.name, f);
            f.type = functionType;
        }

        openScope();

        FunctionDcl oldCurrentFunction = currentFunction;
        currentFunction = f;

        for (int i = 0; i < f.paramList.size(); i++) {
            f.paramList.elementAt(i).accept(this);
        }

        for (int i = 0; i < f.stmtList.size(); i++) {
            f.stmtList.elementAt(i).accept(this);
        }

        currentFunction = oldCurrentFunction;

        closeScope();

    }

    public void visit(Param p) {
        TypeVisitor typeVisitor = new TypeVisitor();
        p.typeIdentifier.accept(typeVisitor);

        if (declaredLocally(p.identifier.name)) {
            errorDeclaredLocally(p.identifier.name);
            p.type = errorType;
        } else {
            enterSymbol(p.identifier.name, p.typeIdentifier);
            p.type = p.typeIdentifier.type;
        }
    }

    public void visit(Simulation s) {
        if (declaredLocally(s.identifier.name)) {
            errorDeclaredLocally(s.identifier.name);
            s.type = errorType;
        } else {
            enterSymbol(s.identifier.name, s);
            s.type = simulationType;
        }

        openScope();

        for (int i = 0; i < s.simStepList.size(); i++) {
            s.simStepList.elementAt(i).accept(this);
        }

        // er ikke sikker på den her
        s.interrupts.accept(this);

        closeScope();

    }

    public void visit(SimStep s) {
        if (declaredLocally("Step" + s.stepNumber)) {
            errorDeclaredLocally("Step" + s.stepNumber);
            s.type = errorType;
        } else {
            enterSymbol("Step" + s.stepNumber, s);
            s.type = simulationStepType;
        }

        openScope();

        for (int i = 0; i < s.stmtList.size(); i++) {
            s.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }
    
}

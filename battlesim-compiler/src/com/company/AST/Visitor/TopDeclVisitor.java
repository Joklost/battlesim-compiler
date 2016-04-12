package com.company.AST.Visitor;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;

import static com.company.AST.SymbolTable.SymbolTable.*;

/**
 * Created by joklost on 12-04-16.
 */
public class TopDeclVisitor extends SemanticsVisitor {

    private boolean debug = false;

    private void errorDeclaredLocally(String id) {
        System.err.println(id + " has already been declared at level " + getLevel() + ".");
    }

    public void visit(Dcl d) {
        TypeVisitor typeVisitor = new TypeVisitor();
        d.typeIdentifier.accept(typeVisitor);

        for (int i = 0; i < d.dclIdList.size(); i++) {
            String id = d.dclIdList.elementAt(i);
            if (declaredLocally(id)) {
                errorDeclaredLocally(id);
            } else {
                enterSymbol(id, d.typeIdentifier);
            }
        }

    }

    public void visit(FunctionDcl f) {
        TypeVisitor typeVisitor = new TypeVisitor();
        f.returnType.accept(typeVisitor);

        if (declaredLocally(f.identifier)) {
            errorDeclaredLocally(f.identifier);
        } else {
            enterSymbol(f.identifier, f);
        }

        openScope();

        for (int i = 0; i < f.paramList.size(); i++) {
            f.paramList.elementAt(i).accept(this);
        }

        for (int i = 0; i < f.stmtList.size(); i++) {
            f.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }

    public void visit(Param p) {
        TypeVisitor typeVisitor = new TypeVisitor();
        p.typeIdentifier.accept(typeVisitor);

        if (declaredLocally(p.identifier)) {
            errorDeclaredLocally(p.identifier);
        } else {
            enterSymbol(p.identifier, p.typeIdentifier);
        }
    }

    public void visit(Simulation s) {
        if (declaredLocally(s.identifier)) {
            errorDeclaredLocally(s.identifier);
        } else {
            enterSymbol(s.identifier, s);
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
        } else {
            enterSymbol("Step" + s.stepNumber, s);
        }

        openScope();

        for (int i = 0; i < s.stmtList.size(); i++) {
            s.stmtList.elementAt(i).accept(this);
        }

        closeScope();
    }
    
}

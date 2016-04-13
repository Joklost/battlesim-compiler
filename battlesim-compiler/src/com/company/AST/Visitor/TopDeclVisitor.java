package com.company.AST.Visitor;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;

import static com.company.AST.SymbolTable.SymbolTable.*;
import static com.company.AST.Visitor.Types.errorType;

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
        d.typeName.accept(typeVisitor);     // sætter d.typename.type

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
                enterSymbol(id, def);
            }
        }

    }


    // skal skrives lidt om
    public void visit(FunctionDcl f) {
        TypeVisitor typeVisitor = new TypeVisitor();
        f.returnType.accept(typeVisitor);

        if (declaredLocally(f.identifier.name)) {
            errorDeclaredLocally(f.identifier.name);
        } else {
            enterSymbol(f.identifier.name, f);
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

        if (declaredLocally(p.identifier.name)) {
            errorDeclaredLocally(p.identifier.name);
        } else {
            enterSymbol(p.identifier.name, p.typeIdentifier);
        }
    }

    public void visit(Simulation s) {
        if (declaredLocally(s.identifier.name)) {
            errorDeclaredLocally(s.identifier.name);
        } else {
            enterSymbol(s.identifier.name, s);
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

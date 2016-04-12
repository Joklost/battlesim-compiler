package com.company.AST.Visitor;

import com.company.AST.Nodes.Dcl;
import com.company.AST.Nodes.FunctionDcl;
import com.company.AST.Nodes.Param;
import com.company.AST.SymbolTable.SymbolTable;

/**
 * Created by joklost on 12-04-16.
 */
public class TopDeclVisitor extends SemanticsVisitor {

    private boolean debug = false;

    private void errorDeclaredLocally(String id) {
        System.err.println(id + " has already been declared at level " + SymbolTable.getLevel() + ".");
    }

    public void visit(Dcl d) {
        TypeVisitor typeVisitor = new TypeVisitor();
        d.typeIdentifier.accept(typeVisitor);

        for (int i = 0; i < d.dclIdList.size(); i++) {
            if (SymbolTable.declaredLocally(d.dclIdList.elementAt(i))) {
                errorDeclaredLocally(d.dclIdList.elementAt(i));
            } else {
                SymbolTable.enterSymbol(d.dclIdList.elementAt(i), d.typeIdentifier);
            }
        }

    }

    public void visit(FunctionDcl f) {
        TypeVisitor typeVisitor = new TypeVisitor();
        f.returnType.accept(typeVisitor);

        if (SymbolTable.declaredLocally(f.identifier)) {
            errorDeclaredLocally(f.identifier);
        } else {
            SymbolTable.enterSymbol(f.identifier, f);
        }

        SymbolTable.openScope();

        for (int i = 0; i < f.paramList.size(); i++) {
            f.paramList.elementAt(i).accept(this);
        }


        SymbolTable.closeScope();
    }

    public void visit(Param p) {
        TypeVisitor typeVisitor = new TypeVisitor();
        p.typeIdentifier.accept(typeVisitor);

        if (SymbolTable.declaredLocally(p.identifier)) {
            errorDeclaredLocally(p.identifier);
        } else {
            SymbolTable.enterSymbol(p.identifier, p.typeIdentifier);
        }
    }
}

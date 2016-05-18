package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.Main;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.errorType;
import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.getTypeName;

/**
 * Created by joklost on 12-04-16.
 */
public class LHSSemanticsVisitor extends SemanticsVisitor {


    private boolean isAssignable(ASTNode a) {
        if (a == null) return false;

        if (a instanceof Simulation) {
            return false;
        } else if (a instanceof FunctionDcl) {
            return false;
        } else if (a instanceof Param) {
            return false;
        } else if (a instanceof SimStep) {
            return false;
        } else if (a instanceof ForeachStmt) {
            return false;
        } else {
            return true;
        }
    }

    public void visit(Identifier id) {
        id.accept(new SemanticsVisitor());

        if (!isAssignable(id.def)) {
            error(id.getLineNumber(),
                    id.name + " is not assignable. Type: " + getTypeName(id.type));
            id.type = errorType;
            id.def = null;
        }
    }

    public void visit(Array1DReferencing a) {
        a.arrayName.accept(this);
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        a.accept(semanticsVisitor);
    }

    public void visit(Array2DReferencing a) {
        a.arrayName.accept(this);
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        a.accept(semanticsVisitor);
    }

    public void visit(ObjectReferencing o) {
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        o.accept(semanticsVisitor);

        if (o.type != errorType) {
            o.objectName.accept(this);
            ASTNode def = Main.currentSymbolTable.retrieveSymbol(o.objectName.name);
            if (def instanceof CustomTypeIdentifier) {
                ASTNode oDef = Main.currentSymbolTable.retrieveSymbol(((CustomTypeIdentifier) def).name.name);
                if (oDef instanceof TypeDeclaration) {
                    SymbolTable oldCurrentSymbolTable = Main.currentSymbolTable;
                    Main.currentSymbolTable = ((TypeDeclaration) oDef).typeDescriptor.fields;

                    ASTNode fDef = Main.currentSymbolTable.retrieveSymbol(o.fieldName.name);
                    if (!isAssignable(fDef)) {
                        error(o.getLineNumber(), o.fieldName.name + " is not an assignable field.");
                        o.type = errorType;
                    }

                    Main.currentSymbolTable = oldCurrentSymbolTable;
                } else {
                    error(o.getLineNumber(), o.fieldName.name + " is not a declared type.");
                    o.type = errorType;
                }

            } else {
                error(o.getLineNumber(), o.fieldName.name + " is not a declared type.");
                o.type = errorType;
            }

        }
    }

}

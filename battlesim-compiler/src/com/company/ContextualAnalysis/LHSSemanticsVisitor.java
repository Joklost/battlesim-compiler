package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;

import static com.company.ContextualAnalysis.TypeConsts.errorType;

/**
 * Created by joklost on 12-04-16.
 */
public class LHSSemanticsVisitor extends SemanticsVisitor {

    private void error(String s) {
        System.err.println(s);
    }

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
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        id.accept(semanticsVisitor);

        if (!isAssignable(id.def)) {
            error(id.name + " is not assignable.");
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
            ASTNode def = symbolTable.retrieveSymbol(o.fieldName.name);
            if (!isAssignable(def)) {
                error(o.fieldName.name + " is not an assignable field.");
            }
        }
    }

}

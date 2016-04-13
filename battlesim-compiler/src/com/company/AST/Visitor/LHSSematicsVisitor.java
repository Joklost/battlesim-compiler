package com.company.AST.Visitor;

import com.company.AST.Nodes.*;

import static com.company.AST.Visitor.Types.errorType;

/**
 * Created by joklost on 12-04-16.
 */
public class LHSSematicsVisitor extends SemanticsVisitor {

    private void errorIsNotAssignable(String var) {
        System.err.println(var + " is not assignable.");
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
        } else {
            return true;
        }
    }





    public void visit(Identifier id) {
        SemanticsVisitor semanticsVisitor = new SemanticsVisitor();
        id.accept(semanticsVisitor);

        if (!isAssignable(id.def)) {
            errorIsNotAssignable(id.name);
            id.type = errorType;
            id.def = null;
        }
    }

}

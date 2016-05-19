package com.company.ContextualAnalysis;

import com.company.AST.Nodes.*;
import com.company.AST.SymbolTable.SymbolTable;
import com.company.ContextualAnalysis.HelperClasses.Array1DTypeDescriptor;
import com.company.ContextualAnalysis.HelperClasses.Array2DTypeDescriptor;
import com.company.ContextualAnalysis.HelperClasses.ObjectTypeDescriptor;
import com.company.Main;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.*;

/**
 * Created by joklost on 12-04-16.
 */
public class TypeVisitor extends TopDeclVisitor {

    public void visit(TypeDeclaration t) {
        if (Main.currentSymbolTable.declaredLocally(t.name.name)) {
            errorDeclaredLocally(t.getLineNumber(), t.name.name);
            t.type = errorType;
        } else {
            t.typeDescriptor = new ObjectTypeDescriptor();
            t.typeDescriptor.fields = new SymbolTable();

            for (int i = 0; i < t.declarationList.size(); i++) {
                t.declarationList.elementAt(i).typeName.accept(this);
                TypeIdentifier def = t.declarationList.elementAt(i).typeName;

                for (int k = 0; k < t.declarationList.elementAt(i).dclIdList.size(); k++) {
                    Identifier id = t.declarationList.elementAt(i).dclIdList.elementAt(k);
                    if (t.typeDescriptor.fields.declaredLocally(id.name)) {
                        error(t.getLineNumber(), "Field " + id + " cannot be re-declared.");
                        id.type = errorType;
                        id.def = null;
                    } else {
                        id.type = def.type;
                        id.def = def;
                        t.typeDescriptor.fields.enterSymbol(id.name, def);
                    }
                }
            }

            SymbolTable oldCurrentSymbolTable = Main.currentSymbolTable;
            Main.currentSymbolTable = t.typeDescriptor.fields;

            for (int i = 0; i < t.functionDclList.size(); i++) {
                FunctionDcl curFuncDcl = t.functionDclList.elementAt(i);
                curFuncDcl.returnType.accept(this);
                if (t.typeDescriptor.fields.declaredLocally(curFuncDcl.functionName.name)) {
                    error(t.getLineNumber(), "Function " + curFuncDcl.functionName.name + " cannot be re-declared.");
                    curFuncDcl.functionName.type = errorType;
                    curFuncDcl.functionName.def = null;
                } else {
                    curFuncDcl.type = functionType;
                    curFuncDcl.functionName.type = curFuncDcl.type;
                    curFuncDcl.functionName.def = curFuncDcl;
                    t.typeDescriptor.fields.enterSymbol(curFuncDcl.functionName.name, curFuncDcl);
                }

                t.typeDescriptor.fields.openScope();

                FunctionDcl oldCurrentFunction = currentFunction;
                currentFunction = curFuncDcl;

                for (int k = 0; k < curFuncDcl.paramList.size(); k++) {
                    curFuncDcl.paramList.elementAt(k).accept(this);
                }
                for (int k = 0; k < curFuncDcl.stmtList.size(); k++) {
                    curFuncDcl.stmtList.elementAt(k).accept(this);
                }

                currentFunction = oldCurrentFunction;

                t.typeDescriptor.fields.closeScope();

                if (curFuncDcl.returnType.type != voidType && curFuncDcl.type != errorType) {
                    boolean containsReturnStmt = false;
                    // checks if function has at least a "reachable" return statement
                    for (int k = 0; k < curFuncDcl.stmtList.size(); k++) {
                        if (curFuncDcl.stmtList.elementAt(k) instanceof ReturnExpr) {
                            containsReturnStmt = true;
                        }
                    }
                    if (!containsReturnStmt) {
                        error(curFuncDcl.getLineNumber(), "Field function " + curFuncDcl.functionName.name + " must return a value.");
                        curFuncDcl.type = errorType;
                    }
                }
            }

            Main.currentSymbolTable = oldCurrentSymbolTable;
            Main.currentSymbolTable.enterSymbol(t.name.name, t);
        }
    }

    public void visit(Array1D a) {
        a.typeName.accept(this);
        a.size.accept(this);
        a.type = array1DType;

        a.typeDesc = new Array1DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize = a.size;
    }

    public void visit(Array2D a) {
        a.typeName.accept(this);
        a.size1.accept(this);
        a.size2.accept(this);
        a.type = array2DType;

        a.typeDesc = new Array2DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize1 = a.size1;
        a.typeDesc.arraySize2 = a.size2;
    }

    public void visit(ListOf l) {
        l.typeName.accept(this);
        l.type = listType;
    }

}


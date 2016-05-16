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
                        error(t.getLineNumber(), "Field " + id + " cannot be redeclared.");
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
                t.functionDclList.elementAt(i).returnType.accept(this);
                if (t.typeDescriptor.fields.declaredLocally(t.functionDclList.elementAt(i).functionName.name)) {
                    error(t.getLineNumber(), "Function " + t.functionDclList.elementAt(i).functionName.name + " cannot be redeclared.");
                    t.functionDclList.elementAt(i).functionName.type = errorType;
                    t.functionDclList.elementAt(i).functionName.def = null;
                } else {
                    t.typeDescriptor.fields.enterSymbol(t.functionDclList.elementAt(i).functionName.name, t.functionDclList.elementAt(i));
                    t.functionDclList.elementAt(i).type = functionType;
                }

                t.typeDescriptor.fields.openScope();

                FunctionDcl oldCurrentFunction = currentFunction;
                currentFunction = t.functionDclList.elementAt(i);


                for (int k = 0; k < t.functionDclList.elementAt(i).paramList.size(); k++) {
                    t.functionDclList.elementAt(i).paramList.elementAt(k).accept(this);
                }

                for (int k = 0; k < t.functionDclList.elementAt(i).stmtList.size(); k++) {
                    t.functionDclList.elementAt(i).stmtList.elementAt(k).accept(this);
                }

                currentFunction = oldCurrentFunction;

                t.typeDescriptor.fields.closeScope();

                boolean containsReturnStmt = false;

                if (t.functionDclList.elementAt(i).returnType.type != voidType && t.functionDclList.elementAt(i).type != errorType) {
                    for (int k = 0; k < t.functionDclList.elementAt(i).stmtList.size(); k++) {
                        if (t.functionDclList.elementAt(i).stmtList.elementAt(k) instanceof ReturnExpr) {
                            containsReturnStmt = true;
                        }
                    }

                    if (!containsReturnStmt) {
                        error(t.functionDclList.elementAt(i).getLineNumber(), "Field function " + t.functionDclList.elementAt(i).functionName.name + " must return a value.");
                        t.functionDclList.elementAt(i).type = errorType;
                    }
                }
            }

            Main.currentSymbolTable = oldCurrentSymbolTable;
            Main.currentSymbolTable.enterSymbol(t.name.name, t);
        }
    }

    public void visit(Array1D a) {
        a.typeName.accept(this);
        a.index.accept(this);
        a.type = array1DType;

        a.typeDesc = new Array1DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize = a.index;
    }

    public void visit(Array2D a) {
        a.typeName.accept(this);
        a.index1.accept(this);
        a.index2.accept(this);
        a.type = array2DType;

        a.typeDesc = new Array2DTypeDescriptor();
        a.typeDesc.elementType = a.typeName.type;
        a.typeDesc.arraySize1 = a.index1;
        a.typeDesc.arraySize2 = a.index2;
    }

    public void visit(ListOf l) {
        l.typeName.accept(this);
        l.type = listType;
    }

}


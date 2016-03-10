package com.company;

import javax.lang.model.element.VariableElement;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/**
 * Created by joklost on 3/10/16.
 */
public class Parser {
    private TokenStream ts;

    public Parser(Scanner scanner) {
        this.ts = new TokenStream(scanner);
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
///////CFG//////////////////////////////////////////////////////////////////////
    public void Start() {
        if (ts.peek() == Token.INCLUDE || ts.peek() == Token.DEFINE || ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            Includes();
            Defines();
            TypeDcls();
            FunctionDcls();
            Program();
            FunctionDcls();
            expect(Token.EOF);
        } else {
            error("Start: Expected Include, Define, Type, Function or Begin.");
        }
    }

    public void Includes() {
        if (ts.peek() == Token.INCLUDE) {
            Include();
            Includes();
        } else if(ts.peek() == Token.DEFINE || ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("Includes: Expected Include, Define, Type, Function or Begin.");
        }
    }

    public void Include() {
        if (ts.peek() == Token.INCLUDE) {
            expect(Token.INCLUDE);
            expect(Token.LPAREN);
            HeaderId();
            expect(Token.RPAREN);
        } else {
            error("Include: Expected Include.");
        }
    }

    public void Defines() {
        if (ts.peek() == Token.DEFINE) {
            Define();
            Defines();
        } else if (ts.peek() == Token.TYPE || ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("Defines: Expected Define, Type, Function or Begin.");
        }
    }

    public void Define() {
        if (ts.peek() == Token.DEFINE) {
            expect(Token.DEFINE);
        } else {
            error("Define: Expected Define.");
        }
    }

    public void DefineType() {
        if (ts.peek() == Token.NUMBER_LITERAL) {
            Number_Lit();
        } else if (ts.peek() == Token.STRING_LITERAL) {
            String_Lit();
        } else if (ts.peek() == Token.BOOLEAN_LITERAL) {
            Boolean_Lit();
        } else {
            error("DefineType: Expected Number_Literal, String_Literal or Boolean_Literal.");
        }
    }

    public void TypeDcls() {
        if (ts.peek() == Token.TYPE) {
            TypeDcl();
            TypeDcls();
        } else if (ts.peek() == Token.FUNCTION || ts.peek() == Token.BEGIN) {
            // Lambda
        } else {
            error("TypeDcls: Expected Type, Function or Begin.");
        }
    }

    public void TypeDcl() {
        if (ts.peek() == Token.TYPE) {
            expect(Token.TYPE);
            Id();
            MemberDcl();
            MemberDcls();
            FunctionDcls();
            expect(Token.END);
            expect(Token.TYPE);
        } else {
            error("TypeDcl: Expected Type.");
        }
    }

    public void MemberDcls() {
        if (ts.peek() == Token.IDENTIFIER) {
            MemberDcl();
            MemberDcls();
        } else if (ts.peek() == Token.FUNCTION || ts.peek() == Token.END) {
            // Lambda
        } else {
            error("MemberDcls: Expected Identifier, Function or End.");
        }
    }

    public void MemberDcl() {
        if (ts.peek() == Token.IDENTIFIER) {
            Id();
            expect(Token.AS);
            TypeId();
        } else {
            error("MemberDcl: Expected Identifier.");
        }
    }

    public void FunctionDcls() {
        if (ts.peek() == Token.FUNCTION) {
            FunctionDcl();
            FunctionDcls();
        } else if (ts.peek() == Token.BEGIN || ts.peek() == Token.EOF) {
            // Lambda
        } else {
            error("FunctionDcls: Expected Function, Begin or EOF.");
        }
    }

    public void FunctionDcl() {
        if (ts.peek() == Token.FUNCTION) {
            // CFG'en skal refactores
        } else {
            error("FunctionDcl: Expected Function.");
        }
    }

    public void Params() {
        if (ts.peek() == Token.IDENTIFIER) {
            // CFG'en skal refactores
        } else {
            error("Params: Expected Identifier.");
        }
    }

    public void Program() {
        if (ts.peek() == Token.BEGIN) {
            expect(Token.BEGIN);
            expect(Token.PROGRAM);
            Stmts();
            expect(Token.END);
            expect(Token.PROGRAM);
        } else {
            error("Program: Expected Begin.");
        }
    }

    public void Stmts() {
        if (ts.peek() == Token.DECLARE || ts.peek() == Token.IDENTIFIER || ts.peek() == Token.IF || ts.peek() == Token.WHILE || ts.peek() == Token.FOREACH || ts.peek() == Token.FOR || ts.peek() == Token.SWITCH || ts.peek() == Token.RETURN) {
            Stmt();
            Stmts();
        } else if (ts.peek() == Token.END || ts.peek() == Token.DEFAULT) {
            // Lambda
        } else {
            error("Stmts: Expected Declare, Identifier, If, While, Foreach, For, Switch, Return, End or Default.");
        }
    }

    public void Stmt() {
        if (ts.peek() == Token.DECLARE) {
            Dcl();
        } else if (ts.peek() == Token.IDENTIFIER) {
            Assignment();
        } else if (ts.peek() == Token.IF) {
            IfStmt();
        } else if (ts.peek() == Token.WHILE || ts.peek() == Token.FOR || ts.peek() == Token.FOREACH) {
            LoopStmt();
        } else if (ts.peek() == Token.SWITCH) {
            SwitchStmt();
        } else if (ts.peek() == Token.RETURN) {
            // CFG'en skal refactores
        } else {
            error("Stmt: Expected Declare, Identifier, If, While, Foreach, For, Switch or Return.");
        }
    }

    public void IfStmt() {
        if (ts.peek() == Token.IF) {
            expect(Token.IF);
            Arithmetic();
            expect(Token.THEN);
            Stmts();
            ElifStmt();
        } else {
            error("IfStmt: Expected If.");
        }
    }

    public void ElifStmt() {
        if (ts.peek() == Token.END) {
            expect(Token.END);
            expect(Token.IF);
        } else if (ts.peek() == Token.ELSE) {
            // CFG'en skal refactoreres
        } else {
            error("ElifStmt: Expected End or Else.");
        }
    }

    public void SwitchStmt() {
        if (ts.peek() == Token.SWITCH) {
            expect(Token.SWITCH);
            Variable();
            SwitchCases();
            SwitchDefault();
            expect(Token.END);
            expect(Token.SWITCH);
        } else {
            error("SwitchStmt: Expected Switch.");
        }
    }

    public void SwitchCases() {
        if (ts.peek() == Token.CASE) {
            SwitchCase();
            SwitchCases();
        } else if (ts.peek() == Token.END) {
            // Lambda
        } else {
            error("SwitchCases: Expected Case or End.");
        }
    }

    public void SwitchCase() {
        if (ts.peek() == Token.CASE) {
            expect(Token.CASE);
            Variable();
            Stmts();
        } else {
            error("SwitchCase: Expected Case.");
        }
    }

    public void SwitchDefault() {
        if (ts.peek() == Token.DEFAULT) {
            expect(Token.DEFAULT);
            Stmts();
        } else if (ts.peek() == Token.END) {
            // Lambda
        } else {
            error("SwitchDefault: Expected Default or End.");
        }
    }
    public void Arithmetic() {
        if (ts.peek() == Token.LPAREN) {
            Arithmetic();
        } else if (ts.peek() == Token.IDENTIFIER || ts.peek() == Token.NUMBER_LITERAL || ts.peek() == Token.STRING_LITERAL || ts.peek() == Token.BOOLEAN_LITERAL) {
            Variable();
            Operator();
        } else if (ts.peek() == Token.NOT || ts.peek() == Token.MINUS || ts.peek() == Token.PLUSPLUS || ts.peek() == Token.MINUSMINUS) {
            LeftUnOperator();
            Variable();
        } else {
            error("Arithmetic: Expected (, Identifier, Number_Literal, String_Literal, Boolean_Literal, NOT, -, ++ or --.");
        }
    }

    public void Operator() {
        if (ts.peek() == Token.MULT || ts.peek() == Token.PLUS || ts.peek() == Token.MINUS || ts.peek() == Token.MOD || ts.peek() == Token.DIV || ts.peek() == Token.AND || ts.peek() == Token.OR || ts.peek() == Token.EQUALS || ts.peek() == Token.GREATERTHAN || ts.peek() == Token.LESSTHAN || ts.peek() == Token.GREATERTHANEQUALS || ts.peek() == Token.LESSTHANEQUALS) {
            BinOperator();
            Variable();
        } else if (ts.peek() == Token.PLUSPLUS || ts.peek() == Token.MINUSMINUS) {
            UnOperator();
        } else {
            error("Operator: Expected *, +, /, -, %, ++, --, AND, OR, EQUALS, GREATERTHAN, LESSTHAN, GREATERTHANEQUALS, LESSTHANEQUALS.");
        }
    }

    public void Assignment() {
        if (ts.peek() == Token.IDENTIFIER) {
            // CFG'en skal refactoreres
        } else {
            error("Assignment: Expected Identifier");
        }
    }

    public void MultiAssign() {
        if (ts.peek() == Token.COMMA) {
            expect(Token.COMMA);
            Id();
            MultiAssign();
            Variable();
            expect(Token.COMMA);
        } else if (ts.peek() == Token.RPAREN) {
            expect(Token.RPAREN);
            expect(Token.EQ);
            expect(Token.LPAREN);
        } else {
            error("MultiAssign: Expected , or ).");
        }
    }

    public void AssignOperator() {
        if (ts.peek() == Token.EQ) {
            expect(Token.EQ);
        } else if (ts.peek() == Token.PLUSEQ) {
            expect(Token.PLUSEQ);
        } else if (ts.peek() == Token.MINUSEQ) {
            expect(Token.MINUSEQ);
        } else if (ts.peek() == Token.MODEQ) {
            expect(Token.MODEQ);
        } else if (ts.peek() == Token.MULTEQ) {
            expect(Token.MULTEQ);
        } else if (ts.peek() == Token.DIVEQ) {
            expect(Token.DIVEQ);
        } else {
            error("AssignOperator: Expected =, +=, -=, %=, *=, /=.");
        }
    }

    public void RightAssign() {
        // CFG bør refactoreres
    }

    public void FunctionCall() {
        // CFG bør refactoreres
    }

    public void Arguments() {
        // CFG bør refactoreres
    }

    public void LoopStmt() {
        if (ts.peek() == Token.WHILE) {
            WhileStmt();
        } else if (ts.peek() == Token.FOR) {
            ForStmt();
        } else if (ts.peek() == Token.FOREACH) {
            ForeachStmt();
        } else {
            error("LoopStmt: Expected While, For or Foreach.");
        }
    }

    public void WhileStmt() {
        if (ts.peek() == Token.WHILE) {
            expect(Token.WHILE);
            Arithmetic();
            expect(Token.DO);
            Stmts();
            expect(Token.END);
            expect(Token.WHILE);
        } else {
            error("WhileStmt: Expected While.");
        }
    }

    public void ForeachStmt() {
        if (ts.peek() == Token.FOREACH) {
            expect(Token.FOREACH);
            TypeId();
            Id();
            expect(Token.IN);
            NestedId();
            expect(Token.DO);
            Stmts();
            expect(Token.END);
            expect(Token.FOREACH);
        } else {
            error("ForeachStmt: Expected Foreach.");
        }
    }

    public void ForStmt() {
        if (ts.peek() == Token.FOR) {
            expect(Token.FOR);
            Integer_Lit();
            ForIterator();
            Integer_Lit();
            expect(Token.DO);
            Stmts();
            expect(Token.END);
            expect(Token.FOR);
        } else {
            error("ForStmt: Expected For.");
        }
    }

    public void ForIterator() {
        if (ts.peek() == Token.TO) {
            expect(Token.TO);
        } else if (ts.peek() == Token.DOWNTO) {
            expect(Token.DOWNTO);
        } else {
            error("ForIterator: Expected To or DownTo.");
        }
    }

    public void BinOperator() {
        if (ts.peek() == Token.MULT) {
            expect(Token.MULT);
        } else if (ts.peek() == Token.PLUS) {
            expect(Token.PLUS);
        } else if (ts.peek() == Token.DIV) {
            expect(Token.DIV);
        } else if (ts.peek() == Token.MINUS) {
            expect(Token.MINUS);
        } else if (ts.peek() == Token.MOD) {
            expect(Token.MOD);
        } else if (ts.peek() == Token.AND || ts.peek() == Token.OR) {
            LogicOperator();
        } else if (ts.peek() == Token.EQUALS || ts.peek() == Token.GREATERTHAN || ts.peek() == Token.LESSTHAN || ts.peek() == Token.GREATERTHANEQUALS || ts.peek() == Token.LESSTHANEQUALS) {
            RelaOperator();
        } else {
            error("BinOperator: Expected *, +, /, -, %, AND, OR, EQUALS, GREATERTHAN, GREATERTHANEQUALS, LESSTHAN, LESSTHANEQUALS.");
        }
    }

    public void LogicOperator() {
        if (ts.peek() == Token.AND) {
            expect(Token.AND);
        } else if (ts.peek() == Token.OR) {
            expect(Token.OR);
        } else {
            error("LogicOperator: Expected OR or AND.");
        }
    }

    public void RelaOperator() {
        if (ts.peek() == Token.EQUALS) {
            expect(Token.EQUALS);
        } else if (ts.peek() == Token.GREATERTHAN) {
            expect(Token.GREATERTHAN);
        } else if (ts.peek() == Token.GREATERTHANEQUALS) {
            expect(Token.GREATERTHANEQUALS);
        } else if (ts.peek() == Token.LESSTHAN) {
            expect(Token.LESSTHAN);
        } else if (ts.peek() == Token.LESSTHANEQUALS) {
            expect(Token.LESSTHANEQUALS);
        } else {
            error("RelaOperator: Expected EQUALS, GREATERTHAN, GREATERTHANEQUALS, LESSTHAN, LESSTHANEQUALS.");
        }
    }

    public void UnOperator() {
        if (ts.peek() == Token.MINUSMINUS) {
            expect(Token.MINUSMINUS);
        } else if (ts.peek() == Token.PLUSPLUS) {
            expect(Token.PLUSPLUS);
        } else {
            error("UnOperator: Expected -- or ++.");
        }
    }

    public void LeftUnOperator() {
        if (ts.peek() == Token.NOT) {
            expect(Token.NOT);
        } else if (ts.peek() == Token.MINUS) {
            expect(Token.MINUS);
        } else if (ts.peek() == Token.PLUSPLUS || ts.peek() == Token.MINUSMINUS) {
            UnOperator();
        } else {
            error("LeftUnOperator: Expected NOT, -, ++ or --.");
        }
    }

    public void Variable() {
        if (ts.peek() == Token.IDENTIFIER) {
            NestedId();
        } else if (ts.peek() == Token.NUMBER_LITERAL) {
            Number_Lit();
        } else if (ts.peek() == Token.STRING_LITERAL) {
            String_Lit();
        } else if (ts.peek() == Token.BOOLEAN_LITERAL) {
            Boolean_Lit();
        } else {
            error("Variable: Expected Identifier, Number_Literal, String_Literal, Boolean_Literal.");
        }
    }

    

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    private void expect(int type) {
        Token t = ts.advance();
        if (t.getType() != type) {
            error("Expected type " + Token.getTokenName(type) +
                    " but received type " + Token.getTokenName(t.getType()));
        }
    }

    private void error(String message) {
        throw new Error(message);
    }

}

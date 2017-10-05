package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspExprStmt extends AspSyntax {

    AspExpr expr = null;

    AspExprStmt(int n) {
        super(n);
    }

    public static AspExprStmt parse(Scanner s) {
        Main.log.enterParser("expr stmt");

        AspExprStmt exprStmt = new AspExprStmt(s.curLineNum());
        exprStmt.expr = AspExpr.parse(s);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("expr stmt");
        return exprStmt;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

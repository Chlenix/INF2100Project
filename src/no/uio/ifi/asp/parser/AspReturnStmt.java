package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspReturnStmt extends AspSyntax {

    AspExpr returnValue = null;

    AspReturnStmt(int n) {
        super(n);
    }

    public static AspReturnStmt parse(Scanner s) {
        Main.log.enterParser("return stmt");

        AspReturnStmt returnStmt = new AspReturnStmt(s.curLineNum());
        skip(s, TokenKind.returnToken);
        returnStmt.returnValue = AspExpr.parse(s);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("return stmt");
        return returnStmt;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("return ");
        returnValue.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

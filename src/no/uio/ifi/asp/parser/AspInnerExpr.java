package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspInnerExpr extends AspSyntax {

    AspExpr expr = null;

    AspInnerExpr(int n) {
        super(n);
    }

    public static AspInnerExpr parse(Scanner s) {
        Main.log.enterParser("inner expr");

        AspInnerExpr innerExpr = new AspInnerExpr(s.curLineNum());

        skip(s, TokenKind.leftParToken);

        innerExpr.expr = AspExpr.parse(s);

        skip(s, TokenKind.rightParToken);

        Main.log.leaveParser("inner expr");
        return innerExpr;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("(");
        expr.prettyPrint();
        Main.log.prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}

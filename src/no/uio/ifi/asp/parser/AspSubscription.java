package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSubscription extends AspSyntax {

    AspExpr expr = null;

    public static AspSubscription parse(Scanner s) {
        Main.log.enterParser("subscription");

        skip(s, TokenKind.leftBracketToken);

        AspSubscription subscription = new AspSubscription(s.curLineNum());
        subscription.expr = AspExpr.parse(s);

        skip(s, TokenKind.rightBracketToken);

        Main.log.leaveParser("subscription");
        return subscription;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("[");
        expr.prettyPrint();
        Main.log.prettyWrite("]");
    }

    AspSubscription(int curLineNum) {
        super(curLineNum);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}

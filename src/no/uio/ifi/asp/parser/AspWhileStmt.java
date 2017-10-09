package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspWhileStmt extends AspSyntax {

    AspExpr condition = null;
    AspSuite body = null;

    AspWhileStmt(int n) {
        super(n);
    }

    public static AspWhileStmt parse(Scanner s) {
        Main.log.enterParser("while stmt");

        AspWhileStmt whileStmt = new AspWhileStmt(s.curLineNum());
        skip(s, TokenKind.whileToken);
        whileStmt.condition = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        whileStmt.body = AspSuite.parse(s);

        Main.log.leaveParser("while stmt");
        return whileStmt;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("while ");
        condition.prettyPrint();
        Main.log.prettyWrite(":");
        body.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSuite extends AspSyntax {

    AspStmt scope = null;

    AspSuite(int n) {
        super(n);
    }

    public static AspSuite parse(Scanner s) {
        Main.log.enterParser("suite");

        AspSuite suite = new AspSuite(s.curLineNum());

        skip(s, TokenKind.newLineToken);
        skip(s, TokenKind.indentToken);

        suite.scope = AspStmt.parse(s);

        skip(s, TokenKind.dedentToken);

        Main.log.leaveParser("suite");
        return suite;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

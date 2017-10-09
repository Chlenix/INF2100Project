package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspPassStmt extends AspSyntax {
    AspPassStmt(int n) {
        super(n);
    }

    public static AspPassStmt parse(Scanner s) {
        Main.log.enterParser("pass stmt");

        AspPassStmt passStmt = new AspPassStmt(s.curLineNum());
        skip(s, TokenKind.passToken);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("pass stmt");
        return passStmt;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite("pass");
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

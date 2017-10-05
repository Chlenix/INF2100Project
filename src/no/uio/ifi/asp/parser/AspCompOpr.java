package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspCompOpr extends AspSyntax {

    Token value = null;

    AspCompOpr(int n) {
        super(n);
    }

    public static AspCompOpr parse(Scanner s) {
        Main.log.enterParser("comp opr");

        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());
        compOpr.value = s.curToken();

        Main.log.leaveParser("comp opr");
        return compOpr;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

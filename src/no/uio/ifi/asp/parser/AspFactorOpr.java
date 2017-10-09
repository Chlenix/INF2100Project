package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspFactorOpr extends AspSyntax {

    Token value = null;

    AspFactorOpr(int n) {
        super(n);
    }

    public static AspFactorOpr parse(Scanner s) {
        Main.log.enterParser("factor opr");

        AspFactorOpr factorOpr = new AspFactorOpr(s.curLineNum());
        factorOpr.value = s.curToken();

        Main.log.leaveParser("factor opr");
        return factorOpr;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite(" " + value.name + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

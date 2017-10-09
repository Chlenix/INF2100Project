package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspTermOpr extends AspSyntax {

    Token value = null;

    AspTermOpr(int n) {
        super(n);
    }

    public static AspTermOpr parse(Scanner s) {
        Main.log.enterParser("term opr");

        AspTermOpr termOpr = new AspTermOpr(s.curLineNum());
        termOpr.value = s.curToken();

        Main.log.leaveParser("term opr");
        return termOpr;
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

package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspFactorPrefix extends AspSyntax {

    Token value = null;

    public static AspFactorPrefix parse(Scanner s) {
        Main.log.enterParser("prefix");

        AspFactorPrefix factorPrefix = new AspFactorPrefix(s.curLineNum());
        factorPrefix.value = s.curToken();
        skip(s, s.curToken().kind);

        Main.log.leaveParser("prefix");
        return factorPrefix;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite(value.name);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspFactorPrefix(int n) {
        super(n);
    }

}

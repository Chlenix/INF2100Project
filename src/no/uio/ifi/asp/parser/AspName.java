package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspName extends AspSyntax {

    Token value = null;

    AspName(int curLineNum) {
        super(curLineNum);
    }

    @Override
    void prettyPrint() {

    }

    public static AspName parse(Scanner s) {
        Main.log.enterParser("name");

        AspName name = new AspName(s.curLineNum());
        name.value = s.curToken();
        skip(s, TokenKind.nameToken);

        Main.log.leaveParser("name");
        return name;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}

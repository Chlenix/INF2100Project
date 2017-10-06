package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax {
    ArrayList<AspPrimarySuffix> suffixes = new ArrayList<>();
    AspAtom atom = null;
    @Override
    void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix suffix : suffixes) {
            suffix.prettyPrint();
        }
    }

    public static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");

        AspPrimary primary = new AspPrimary(s.curLineNum());
        primary.atom = AspAtom.parse(s);
        while (s.curToken().kind == TokenKind.leftParToken || s.curToken().kind == TokenKind.leftBracketToken) {
            primary.suffixes.add(AspPrimarySuffix.parse(s));
        }

        Main.log.leaveParser("primary");
        return primary;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    AspPrimary(int n) {
        super(n);
    }

}

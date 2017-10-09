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
        RuntimeValue rv = atom.eval(curScope);
        return rv;
        // someArray for suffixes
        // for loop
            // eval someArray[5], store in builder
            // eval builder[3], store in builder (builder has someArray[5] so someArray[5][3])
            // eval builder(), store in builder (builder "someArray[5][3]" returned a callable)
    }

    AspPrimary(int n) {
        super(n);
    }

}

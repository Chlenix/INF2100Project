package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

import java.util.ArrayList;

public class AspFactor extends AspSyntax {

    ArrayList<AspPrimary> primaries = new ArrayList<>();
    AspFactorOpr factorOpr = null;
    AspFactorPrefix prefix = null;

    @Override
    void prettyPrint() {
        boolean printed = false;
        for (AspPrimary primary : primaries) {
            if (prefix != null && !printed) {
                prefix.prettyPrint();
            }

            if (printed) {
                factorOpr.prettyPrint();
            }

            primary.prettyPrint();
            printed = true;
        }
    }

    public static AspFactor parse(Scanner s) {
        Main.log.enterParser("factor");

        AspFactor factor = new AspFactor(s.curLineNum());
        if (s.isFactorPrefix()) {
            factor.prefix = AspFactorPrefix.parse(s);
        }
        while (true) {
            factor.primaries.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            factor.factorOpr = AspFactorOpr.parse(s);
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("factor");
        return factor;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return primaries.get(0).eval(curScope);
    }

    AspFactor(int n) {
        super(n);
    }

}

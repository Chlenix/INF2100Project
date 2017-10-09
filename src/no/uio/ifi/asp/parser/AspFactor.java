package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeFactorOpr;
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
        RuntimeValue cumulative = null;
        RuntimeValue leftPrimary = primaries.get(0).eval(curScope);
        for (AspPrimary primary : primaries.subList(1, primaries.size())) {
            RuntimeFactorOpr factorOpr = (RuntimeFactorOpr) this.factorOpr.eval(curScope);
            RuntimeValue nextPrimary = primary.eval(curScope);
            switch (factorOpr.getValue()) {
                case astToken:
                    cumulative = leftPrimary.evalMultiply(nextPrimary, this);
                    break;
                case slashToken:
                    // division
                    break;
                case percentToken:
                    // modulo
                    break;
                case doubleSlashToken:
                    // integer division
                    break;
            }
        }

        return cumulative != null ? cumulative : leftPrimary;
    }

    AspFactor(int n) {
        super(n);
    }

}

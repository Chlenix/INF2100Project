package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

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
        RuntimeValue result = null;
        RuntimeValue leftPrimary = primaries.get(0).eval(curScope);
        //if (prefix != null) {
        //    if (prefix.value.kind == TokenKind.minusToken) {
        //        leftPrimary = leftPrimary.evalNegate(this);
        //    }
        //}
        for (AspPrimary primary : primaries.subList(1, primaries.size())) {
            if (prefix != null) {
                if (prefix.value.kind == TokenKind.minusToken) {
                    leftPrimary = leftPrimary.evalNegate(this);
                }
            }

            RuntimeFactorOpr factorOpr = (RuntimeFactorOpr) this.factorOpr.eval(curScope);
            RuntimeValue nextPrimary = primary.eval(curScope);
            switch (factorOpr.getValue()) {
                case astToken:
                    result = leftPrimary.evalMultiply(nextPrimary, this);
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
            leftPrimary = result;
        }

        return result != null ? result : leftPrimary;
    }

    AspFactor(int n) {
        super(n);
    }

}

package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspFactor extends AspSyntax {

    ArrayList<AspPrimary> primaries = new ArrayList<>();
    ArrayList <AspFactorOpr> factorOpr = new ArrayList<>();
    AspFactorPrefix prefix = null;

    @Override
    void prettyPrint() {
        int size = primaries.size();
        boolean printed = false;
        for (int i = 0; i < size; i++) {
            if (!printed && prefix != null) {
                prefix.prettyPrint();
                printed = true;
            }
            primaries.get(i).prettyPrint();
            if (i + 1 < size)
                factorOpr.get(i).prettyPrint();
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
            factor.factorOpr.add(AspFactorOpr.parse(s));
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("factor");
        return factor;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue accumulator = primaries.get(0).eval(curScope);

        // if prefix -, negate accumulator
        if (prefix != null) {
            RuntimePrefixValue prefixValue = (RuntimePrefixValue) prefix.eval(curScope);

            if (prefixValue.getValue() == TokenKind.minusToken) {
                accumulator = accumulator.evalNegate(this);
            }
        }
        int i = 0;
        for (AspPrimary primary : primaries.subList(1, primaries.size())) {
            RuntimeFactorOpr factorOpr = (RuntimeFactorOpr) this.factorOpr.get(i++).eval(curScope);
            RuntimeValue nextPrimary = primary.eval(curScope);
            switch (factorOpr.getValue()) {
                case astToken:
                    accumulator = accumulator.evalMultiply(nextPrimary, this);
                    break;
                case slashToken:
                    accumulator = accumulator.evalDivide(nextPrimary, this);
                    break;
                case percentToken:
                    accumulator = accumulator.evalModulo(nextPrimary, this);
                    break;
                case doubleSlashToken:
                    accumulator = accumulator.evalIntDivide(nextPrimary, this);
                    break;
            }

        }

        return accumulator;
    }

    AspFactor(int n) {
        super(n);
    }

}

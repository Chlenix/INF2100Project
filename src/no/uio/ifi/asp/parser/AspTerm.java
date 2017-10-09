package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeTermOpr;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

public class AspTerm extends AspSyntax {

    ArrayList<AspFactor> factors = new ArrayList<>();
    AspTermOpr termOpr = null;

    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");

        AspTerm term = new AspTerm(s.curLineNum());
        while (true) {
            term.factors.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            term.termOpr = AspTermOpr.parse(s);
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("term");
        return term;
    }

    @Override
    void prettyPrint() {
        boolean printed = false;
        for (AspFactor f : factors) {
            if (printed) {
                termOpr.prettyPrint();
            }
            f.prettyPrint();
            printed = true;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue cumulative = factors.get(0).eval(curScope);
        for (AspFactor factor : factors.subList(1, factors.size())) {
            RuntimeTermOpr termOpr = (RuntimeTermOpr) this.termOpr.eval(curScope);
            RuntimeValue nextFactor = factor.eval(curScope);
            switch (termOpr.getValue()) {
                case plusToken:
                    cumulative = cumulative.evalAdd(nextFactor, this);
                    break;
                case minusToken:
                    cumulative = cumulative.evalSubtract(nextFactor, this);
                    break;
            }
        }
        return cumulative;
    }

    AspTerm(int n) {
        super(n);
    }
}

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
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();

    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");

        AspTerm term = new AspTerm(s.curLineNum());
        while (true) {
            term.factors.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            term.termOpr.add(AspTermOpr.parse(s));
            skip(s, s.curToken().kind);
        }

        Main.log.leaveParser("term");
        return term;
    }

    @Override
    void prettyPrint() {
//        boolean printed = false;
//        for (AspFactor f : factors) {
//            if (printed) {
//                termOpr.prettyPrint();
//            }
//            f.prettyPrint();
//            printed = true;
//        }
        int size = factors.size();
        for (int i = 0; i < size; i++) {
            factors.get(i).prettyPrint();

            if (i + 1 < size)
                termOpr.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue accumulator = factors.get(0).eval(curScope);

        int i = 0;
        for (AspFactor factor : factors.subList(1, factors.size())) {
            RuntimeTermOpr termOpr = (RuntimeTermOpr) this.termOpr.get(i++).eval(curScope);
            RuntimeValue nextFactor = factor.eval(curScope);
            switch (termOpr.getValue()) {
                case plusToken:
                    accumulator = accumulator.evalAdd(nextFactor, this);
                    break;
                case minusToken:
                    accumulator = accumulator.evalSubtract(nextFactor, this);
                    break;
            }
        }
        return accumulator;
    }

    AspTerm(int n) {
        super(n);
    }
}

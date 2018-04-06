package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

public class AspComparison extends AspSyntax {

    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOpr = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        Main.log.enterParser("comparison");

        AspComparison comparison = new AspComparison(s.curLineNum());

        while (true) {
            comparison.terms.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;

            comparison.compOpr.add(AspCompOpr.parse(s));
            skip(s, s.curToken().kind);
        }

        if (comparison.compOpr.size() + 1 != comparison.terms.size()) {
            parserError("Number of comparasions doesn't match number of operators.", s.curLineNum());
        }

        Main.log.leaveParser("comparison");
        return comparison;
    }

    @Override
    void prettyPrint() {
        int size = terms.size();
        for (int i = 0; i < size; i++) {
            terms.get(i).prettyPrint();

            if (i + 1 < size)
                compOpr.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue accumulator = terms.get(0).eval(curScope);

        RuntimeValue lastTerm = accumulator;
        int i = 0;
        for (AspTerm term : terms.subList(1, terms.size())) {
            RuntimeCompareOpr compOpr = (RuntimeCompareOpr) this.compOpr.get(i++).eval(curScope);
            RuntimeValue nextTerm = term.eval(curScope);

            /* Each case must be done for
            * AspStringLiteral
            * AspIntegerLiteral
            * AspFloatLiteral
            * AspBooleanLiteral
            * AspListDisplay
            * AspDictDisplay
            * */

            switch (compOpr.getValue()) {
                case lessToken:
                    accumulator = lastTerm.evalLess(nextTerm, this);
                    break;
                case greaterToken:
                    accumulator = lastTerm.evalGreater(nextTerm, this);
                    break;
                case doubleEqualToken:
                    accumulator = lastTerm.evalEqual(nextTerm, this);
                    break;
                case greaterEqualToken:
                    accumulator = lastTerm.evalGreaterEqual(nextTerm, this);
                    break;
                case lessEqualToken:
                    accumulator = lastTerm.evalLessEqual(nextTerm, this);
                    break;
                case notEqualToken:
                    accumulator = lastTerm.evalNotEqual(nextTerm, this);
                    break;
            }
            if (!accumulator.getBoolValue("comparison accumulator", this)) {
                // the entire comparison is false because 1 condition is false
                break;
            }
            lastTerm = nextTerm;
        }
        return accumulator;
    }

}

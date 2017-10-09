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
        RuntimeBoolValue cumulative = null;

        RuntimeValue leftTerm = terms.get(0).eval(curScope);
        int i = 0;
        for (AspTerm term : terms.subList(1, terms.size())) {
            RuntimeCompareOpr compOpr = (RuntimeCompareOpr) this.compOpr.get(i++).eval(curScope);
            RuntimeValue rightTerm = term.eval(curScope);

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
                    cumulative = (RuntimeBoolValue) leftTerm.evalLess(rightTerm, this);
                    break;
                case greaterToken:
                    cumulative = (RuntimeBoolValue) leftTerm.evalGreater(rightTerm, this);
                    break;
                case doubleEqualToken:
                    cumulative = (RuntimeBoolValue) leftTerm.evalEqual(rightTerm, this);
                    break;
                case greaterEqualToken:
                    cumulative = (RuntimeBoolValue) leftTerm.evalGreaterEqual(rightTerm, this);
                    break;
                case lessEqualToken:
                    cumulative = (RuntimeBoolValue) leftTerm.evalLessEqual(rightTerm, this);
                    break;
                case notEqualToken:
                    cumulative = (RuntimeBoolValue) leftTerm.evalNotEqual(rightTerm, this);
                    break;
            }
        }
        return cumulative != null ? cumulative : leftTerm;
    }

}

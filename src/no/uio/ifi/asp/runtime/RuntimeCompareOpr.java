package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class RuntimeCompareOpr extends RuntimeValue {

    Token operator;

    public RuntimeCompareOpr(Token value) {
        this.operator = value;
    }

    public TokenKind getValue() {
        return operator.kind;
    }

    @Override
    protected String typeName() {
        return "compare operator";
    }
}

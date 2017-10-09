package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class RuntimeFactorOpr extends RuntimeValue {

    Token operator;

    public RuntimeFactorOpr(Token value) {
        this.operator = value;
    }

    @Override
    protected String typeName() {
        return "factor operator";
    }

    public TokenKind getValue() {
        return operator.kind;
    }

    @Override
    public String toString() {
        return operator.kind.toString();
    }
}

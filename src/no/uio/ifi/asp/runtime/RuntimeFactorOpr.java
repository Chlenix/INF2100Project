package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.scanner.Token;

public class RuntimeFactorOpr extends RuntimeValue {

    Token operator;

    public RuntimeFactorOpr(Token value) {
        this.operator = value;
    }

    @Override
    protected String typeName() {
        return "factor operator";
    }
}

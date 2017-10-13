package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class RuntimePrefixValue extends RuntimeValue {
    Token value = null;

    public RuntimePrefixValue(Token value) {
        this.value = value;
    }

    public TokenKind getValue() {
        return value.kind;
    }

    public String toString() {
        return value.kind.toString();
    }

    @Override
    protected String typeName() {
        return "prefix";
    }
}

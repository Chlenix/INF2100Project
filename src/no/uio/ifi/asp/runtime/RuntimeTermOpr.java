package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class RuntimeTermOpr extends RuntimeValue {
    Token operator;

    public RuntimeTermOpr(Token value) {
        this.operator = value;
    }

    public TokenKind getValue() {
        return operator.kind;
    }

    @Override
    protected String typeName() {
        return "term operator";
    }
}

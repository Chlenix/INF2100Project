package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RuntimeDictValue extends RuntimeValue {

    LinkedHashMap<RuntimeValue, RuntimeValue> dict = new LinkedHashMap<>();

    public RuntimeDictValue(LinkedHashMap<RuntimeValue, RuntimeValue> dict) {
        this.dict = dict;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return dict.get(v);
        }

        return super.evalSubscription(v, where);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        String separator = "";
        for (HashMap.Entry<RuntimeValue, RuntimeValue> entry : dict.entrySet()) {
            sb.append(separator);
            separator = ", ";
            sb.append(entry.getKey().toString());
            sb.append(": ");
            sb.append(entry.getValue().toString());
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    protected String typeName() {
        return "dict";
    }
}

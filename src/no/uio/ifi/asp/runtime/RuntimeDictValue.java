package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RuntimeDictValue extends RuntimeValue {

    LinkedHashMap<String, RuntimeValue> dict = new LinkedHashMap<>();

    public RuntimeDictValue(LinkedHashMap<String, RuntimeValue> dict) {
        this.dict = dict;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeStringValue) {
            return dict.get(v.getStringValue(typeName(), where));
        }

        return super.evalSubscription(v, where);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        String separator = "";
        for (HashMap.Entry entry : dict.entrySet()) {
            // separator
            sb.append(separator);
            separator = ", ";

            // key
            sb.append("\'");
            sb.append(entry.getKey());
            sb.append("\'");

            // value
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

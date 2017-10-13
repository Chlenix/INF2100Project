package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> list = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        String separator = "";
        for (RuntimeValue rv : list) {
            sb.append(separator);
            separator = ", ";
            sb.append(rv.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return super.evalNegate(where);
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            int i = (int) v.getIntValue(typeName(), where);
            return list.get(i);
        }
        return super.evalSubscription(v, where);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {

        if (v instanceof RuntimeIntegerValue) {
            // reverse loop so that v.getIntValue is only evaluated once
            ArrayList<RuntimeValue> newList = new ArrayList<>();
            for (long i = v.getIntValue(typeName(), where) - 1; i >= 0; --i) {
                newList.addAll(list);
            }
            return new RuntimeListValue(newList);
        }

        return super.evalMultiply(v, where);
    }

    @Override
    protected String typeName() {
        return "list";
    }
}

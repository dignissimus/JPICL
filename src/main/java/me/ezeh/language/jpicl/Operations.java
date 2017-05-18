package me.ezeh.language.jpicl;

import static me.ezeh.language.jpicl.Element.*;

public class Operations {
    static Token operate(Token operator, Token a, Token b) {
        if (operator.getType() != OPERATOR) {
            throw new IllegalArgumentException("Argument 'operator' must be an operator");
        }
        String opType = operator.getValue();
        Element aType = a.getType();
        Element bType = b.getType();
        if (aType == NUMBER) {
            if (!bType.equals(NUMBER)) {
                throw new RuntimeException("Tried to perform an unsupported operation on a number");
            }
            int aint, bint;
            aint = Integer.parseInt(a.getValue());
            bint = Integer.parseInt(b.getValue());
            switch (opType) {
                case "+": {
                    return new Token(NUMBER, String.valueOf(aint + bint));
                }
                case "*": {
                    return new Token(NUMBER, String.valueOf(aint * bint));
                }
                case "/": {
                    return new Token(NUMBER, String.valueOf((float) aint / bint));
                }
                case "-": {
                    return new Token(NUMBER, String.valueOf(aint - bint));
                }
                default: {
                    throw new UnsupportedOperationException(String.format("As of now, you cannot use '%s' inside of a function call", opType));
                }

            }
        }
        if (aType == STRING) {
            switch (opType) {
                case "+": {
                    return new Token(STRING, a.getValue() + b.getValue());
                }
                default: {
                    throw new RuntimeException();
                }
            }
        }
        if (aType == VARIABLE) {
            throw new UnsupportedOperationException("As of now, you cannot perform operations on a variable inside a function call");
        }
        return null;

    }
}

class OperationInstruction implements Instruction {
    private String value;
    private Token a, b, operator;

    OperationInstruction(Token operator, Token a, Token b) {
        this.a = a;
        this.b = b;
        this.operator = operator;
        if (a.getType() != VARIABLE && b.getType() != VARIABLE) {
            value = Operations.operate(operator, a, b).getValue();
        } else {
            value = a.getValue() + operator.getValue() + b.getValue();
        }
    }

    @Override
    public String getJava() {
        if (!operator.getValue().equals("="))
            return String.valueOf(value);
        else {
            if (b.getType() == NUMBER || b.getType() == STRING) {
                String type = b.getType() == NUMBER ? "int" : "String";
                RuntimeEnvironment.lastType.put(a.getValue(), b.getType().toString());
                return type + " " + b.getType() + "_" + a.getValue() + " = " + b.getValue() + ";";
            } else {
                return "Unsupported operation";
            }
        }
    }

    @Override
    public void perform() {
        if (operator.getValue().equals("=")) {
            if (b.getType() == VARIABLE)
                RuntimeEnvironment.variables.put(a.getValue(), RuntimeEnvironment.variables.containsKey(b.getValue()) ? RuntimeEnvironment.variables.get(b.getValue()) : b.getValue());
            else {
                if (b.getType() == NUMBER) {
                    //TODO Create a setter method. I shouldn't use this method
                    RuntimeEnvironment.variables.put(a.getValue(), /*Check if double*/Double.parseDouble(b.getValue()));
                }
                if (b.getType() == STRING) {
                    RuntimeEnvironment.variables.put(a.getValue(), b.getValue());
                }
            }
        }
    }
}
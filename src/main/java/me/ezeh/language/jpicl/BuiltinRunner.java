package me.ezeh.language.jpicl;

public class BuiltinRunner {
    private BuiltinMethod method;
    private Token[] args;

    BuiltinRunner(String name, Token... args) {
        method = new BuiltinMethod(name);
        this.args = args;
    }

    BuiltinMethod getMethod() {
        return method;
    }

    Token[] getArgs() {
        return args;
    }

    String getJava() {
        return method.compile.run(args) + ";";
    }

    void run() {
        method.runtime.run(args);
    }
}

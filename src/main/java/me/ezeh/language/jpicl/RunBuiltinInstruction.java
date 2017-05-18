package me.ezeh.language.jpicl;

import java.util.ArrayList;
import java.util.List;

public class RunBuiltinInstruction implements Instruction {
    BuiltinRunner runner;
    private String name;
    private Token[] args;

    RunBuiltinInstruction(String name, Token[] args) {
        this.name = name;
        this.args = args;
        runner = new BuiltinRunner(name, args);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getJava() {
        return runner.getJava();
    }

    @Override
    public void perform() {
        runner.run();
    }

    @Override
    public String toString() {
        ArrayList<String> args = new ArrayList<>();
        for (Token tok : getArgs()) {
            args.add(tok.getValue());
        }
        return String.format("%s(%s)", getName(), String.join(", ", args));
    }

    public Token[] getArgs() {
        return args;
    }

    public List<String> getStringArgs() {
        ArrayList<String> args = new ArrayList<>();
        for (Token tok : getArgs()) {
            args.add(tok.getValue());
        }
        return args;
    }
}

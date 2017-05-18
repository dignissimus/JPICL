package me.ezeh.language.jpicl;


public interface Instruction {
    String getJava();

    void perform();

    enum InstructionType {
        RUN_BUILTIN
    }

}

package me.ezeh.language.jpicl;

import java.util.List;

public class JPICLRunner {
    private List<Instruction> instructions;
    JPICLRunner(String code){
        instructions = new Parser().parse(new Lexer().lex(code));
    }
    String getJavaCode(){
        TemplateBuilder tb = new TemplateBuilder(Main.readFile("javabase.java"));
        StringBuilder sb = new StringBuilder();
        for (Instruction instruction : instructions) {
            sb.append("\t\t").append(instruction.getJava()).append("\n");// make it look nice
        }
        tb.set("code", sb.toString());
        return tb.build();
    }
    void execute(){
        for(Instruction instruction:instructions){
            instruction.perform();
        }
    }
}

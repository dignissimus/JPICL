package me.ezeh.language.jpicl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.ezeh.language.jpicl.Element.*;
import static me.ezeh.language.jpicl.RuntimeEnvironment.*;

public class Parser {
    // If I'm making a Java backend web server use sparkjava, although it's not the best, and it is supposed to be used as a REST API. It has lambdas and is really simple to manipulate the responses and and requests. But java isn't made for the web
    static final Map<String, RuntimeMethod> RuntimeMethods = new HashMap<String, RuntimeMethod>() {{
        put("print", args -> {
            if (args.length == 0) {
                System.out.println();
            }
            if (args.length == 1) {
                System.out.println(String.valueOf(getValue(args[0])));
            }
            if (args.length > 1) {
                StringBuilder sb = new StringBuilder();
                for (Token t : args) {
                    sb.append(" ");
                    sb.append(getStringValue(t));
                }
                System.out.println(sb.substring(1));
            }
        });
    }};
    static final Map<String, CompiledMethod> CompiledMethods = new HashMap<String, CompiledMethod>() {{
        put("print", args -> {
            String[] rawStringArgs = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                rawStringArgs[i] = String.valueOf(getJavaString(args[i]));
            }
            /*
            ArrayList<String> stringArgs = new ArrayList<>();

            for (String string : rawStringArgs)
                stringArgs.addAll(Arrays.stream(string.split("\"")).filter(s -> !s.isEmpty() && !s.equals(" ")).collect(Collectors.toList()));
           */// I needed this for when I broke the Lexer, I may need it again
            String base = "System.out.println(%s)";
            if (rawStringArgs.length == 0) {
                return String.format(base, "");
            }
            if (rawStringArgs.length == 1) {
                return String.format(base, String.format("%s", rawStringArgs[0]));
            } else {
                // larger than 1
                StringBuilder sb = new StringBuilder();
                for (String s : rawStringArgs) {
                    sb.append(" + ");// length of this string is '3'
                    sb.append(s);
                }
                return String.format(base, String.format("%s", sb.substring(3)));
            }
        });
    }};
    List<String> builtins = new ArrayList<String>() {{
        add("print");
    }};

    private boolean isFunction(Token token) {
        return builtins.contains(token.getValue());
    }

    List<Instruction> parse(List<Token> tokens) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        for (int index = 0; index < tokens.size(); index++) {
            Token token = tokens.get(index);
            Element type = token.getType();
            if (type == VARIABLE) {
                if (isFunction(token)) {
                    ArrayList<Token> args = new ArrayList<>();
                    Token next;
                    while ((next = tokens.get(++index)).getType() != TERMINATOR) {
                        args.add(next);
                    }
                    args = simplifyJava(args);
                    instructions.add(new RunBuiltinInstruction(token.getValue(), args.toArray(new Token[]{})));
                    continue;
                }
                Token next = index + 1 < tokens.size() ? tokens.get(++index) : null;
                if (next != null && next.getType() == OPERATOR && next.getValue().equals("=")) {
                    Token other = tokens.get(++index);
                    instructions.add(new OperationInstruction(next, token, other));
                    //Operations.operate(next, token, other);
                }

            }

                /*if (type==Element.VARIABLE){
                if(tokens.get(index+1).getType()==OPERATOR){
                    Token operator = tokens.get(index+1);
                    Token other = tokens.get(index+2);
                }
                */
        }
        return instructions;
    }

    private ArrayList<Token> simplifyJava(ArrayList<Token> args1) {
        Token[] args = args1.toArray(new Token[0]);
        ArrayList<Token> newArgs = new ArrayList<>();
        for (int index = 0; index < args.length; index++) {
            Token token = args[index];

            /*
            if (token.getType() == VARIABLE) {
                if (next != null && next.getType() == OPERATOR) { //next is operator
                    Token other = args[++index];
                    continue;
                } else {
                    if (next != null) index--;
                    newArgs.add(token);
                    continue;
                }

            }
            */
            if (token.getType() == NEWOBJECT) {
                // System.out.println("token = " + token.getValue());
                if (token.getValue().matches("!.*!\\(.*\\)")) {
                    Matcher matcher = Pattern.compile("!(?<name>.*)!(?<params>\\(.*\\))").matcher(token.getValue());
                    matcher.find();
                    String name = matcher.group("name");
                    String params = matcher.group("params");
                    if (name.equals("String")) {
                        newArgs.add(new Token(STRING, params.substring(1, params.length() - 1)));
                    }
                } else {
                    if (getObjectName(token).equals("String")) {
                        newArgs.add(new Token(STRING, ""));
                    }
                }
                continue;
            }
            Token next = index + 1 < args.length ? args[++index] : null;
            if (next != null) {
                if (next.getType() == OPERATOR /*&& (token.getType() == NUMBER | token.getType() == STRING)*/) {
                    Token other = args[++index];
                    newArgs.add(Operations.operate(next, token, other));
                } else {
                    newArgs.add(token);
                    index--;
                }
                continue;
            }
            newArgs.add(token);
        }
        while (getTypes(newArgs).contains(OPERATOR) || getTypes(newArgs).contains(NEWOBJECT)) {
            newArgs = simplifyJava(newArgs);
        }
        return newArgs;
    }

    private String getObjectName(Token token) {
        return token.getValue().substring(1, token.getValue().length() - 1);
    }

    private Set<Element> getTypes(List<Token> tokens) {
        return tokens.stream().map(Token::getType).collect(Collectors.toSet());
    }
}
package me.ezeh.language.jpicl;

import java.util.HashMap;

public class RuntimeEnvironment {
    //TODO create getter method, don't use static
    static HashMap<String, Object> variables = new HashMap<>();
    static HashMap<String, String> lastType = new HashMap<>();

    static Object getValue(Token token) {
        return token.getType() == Element.VARIABLE ? variables.get(token.getValue()) : token.getValue();
    }

    static String getStringValue(Token token) {
        return (String) getValue(token);
    }

    static String getJavaString(Token token) {
        return token.getType() == Element.VARIABLE ? lastType.get(token.getValue()) + "_" + token.getValue() : token.getValue();
    }
}

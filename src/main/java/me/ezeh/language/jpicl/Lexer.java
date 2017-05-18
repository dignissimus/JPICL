package me.ezeh.language.jpicl;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.ezeh.language.jpicl.Element.*;

public class Lexer {
    String groupFormat = "|(?<{type}>{regex})";

    public List<Token> lex(String code) {
        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder patternsBuilder = new StringBuilder(); // Don't use concatenation, use this
        for (Element element : Element.values())
            patternsBuilder.append(new TemplateBuilder(groupFormat).set("type", element.name()).set("regex", element.getRegex()).build());
        Pattern tokenPatterns = Pattern.compile(patternsBuilder.substring(1)); // Remove the first pipe operator
        // System.out.println("pattern = " + patternsBuilder.toString());
        Matcher matcher = tokenPatterns.matcher(code);
        while (matcher.find()) {
            for (Element element : Element.values()) {
                if (matcher.group(element.name()) != null)
                    if (element != WHITESPACE && element != COMMENT && element != JAVAMETHOD)
                        tokens.add(new Token(element, matcher.group(element.name())));
                    else if(element==JAVAMETHOD){

                    }
            }
            /*if (matcher.group(NUMBER.name()) != null) {
                tokens.add(new Token(NUMBER, matcher.group(NUMBER.name())));
            } else if (matcher.group(OPERATOR.name()) != null) {
                tokens.add(new Token(OPERATOR, matcher.group(OPERATOR.name())));
            }
            */
            //else if (matcher.group(WHITESPACE.name()) != null)
        }
        List<Token> tokenList = tokens;

        if (tokenList.get(tokens.size() - 1).getType() == VARIABLE) tokenList = tokenList.subList(0, tokens.size() - 1);
        if (tokenList.get(tokenList.size() - 1).getType() != TERMINATOR) tokenList.add(new Token(TERMINATOR, ";"));
        return tokenList;
    }
}
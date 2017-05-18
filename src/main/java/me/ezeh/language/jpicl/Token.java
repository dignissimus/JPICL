package me.ezeh.language.jpicl;


import org.apache.commons.lang.StringEscapeUtils;

public class Token {
    private Element type;
    private String value;

    public Token(Element type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Element getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Token(%s) -> %s", getType(), StringEscapeUtils.escapeJava(getValue()));
    }
}

package me.ezeh.language.jpicl;


public enum Element {
    IMPORT("import .*"), OPERATOR("[+\\/\\*\\=]"), COMMENT("\\/\\*.*\\*\\/"), NEWOBJECT("\\!.*\\!(\\(.*\\))?"), JAVAMETHOD("\\$(?:.*\\.)+.*\\$"), NUMBER("-?\\d[l]?"), TERMINATOR("[\\n\\;]"), WHITESPACE("\\s+"), STRING("\\\".*?\\\""), VARIABLE("[A-Za-z]*");
    // TODO make comments work like they are supposed to
    private String regex;

    Element(String s) {
        this.regex = s;
    }

    @Override
    public String toString() {
        return name();
    }

    String getRegex() {
        return this.regex;
    }
}
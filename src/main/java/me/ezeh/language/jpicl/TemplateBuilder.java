package me.ezeh.language.jpicl;

public class TemplateBuilder {
    String template;

    TemplateBuilder(String template) {
        this.template = template;
    }

    TemplateBuilder() {
        this("");
    }

    TemplateBuilder set(String placeholder, String text) {
        template = template.replace(String.format("{%s}", placeholder), text);
        return this;
    }

    String build() {
        return template;
    }

    @Override
    public String toString() {
        return this.template;
    }
}

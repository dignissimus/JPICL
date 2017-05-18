package me.ezeh.language.jpicl;

/**
 * Created by sam on 16/05/17.
 */
public class BuiltinMethod {
    RuntimeMethod runtime;
    CompiledMethod compile;

    BuiltinMethod(String name) {
        runtime = Parser.RuntimeMethods.get(name);
        compile = Parser.CompiledMethods.get(name);
        // compile =
    }
}

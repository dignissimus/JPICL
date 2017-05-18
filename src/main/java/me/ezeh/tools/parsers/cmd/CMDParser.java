package me.ezeh.tools.parsers.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CMDParser {
    private List<String> args;
    private HashMap<String, String> aliases;
    private HashMap<String, Object> values;

    public CMDParser(String[] args) {
        this.args = Arrays.stream(args).collect(Collectors.toList());
    }


    public String getString(String name) {

        try {
            int index = args.indexOf(name) + 1;
            index = index == 0 ? args.indexOf(aliases.get(name)) : index;
            if (index == 0) return null;
            return args.get(index);
        }
        catch (IndexOutOfBoundsException exception){
            return null;
        }
    }

    public void addAlias(String arg, String alias) {
        aliases.put(arg, alias);
    }

    public boolean getBoolean(String name) {
        return args.contains("--"+name)||args.contains("-"+aliases.get(name));
    }

}

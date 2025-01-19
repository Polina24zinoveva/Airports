package com.example;

import java.util.HashMap;
import java.util.Map;

public class ProgramParamParser {
    private Map<String, String> args = new HashMap<>();


    public ProgramParamParser(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            this.args.put(args[i], args[i + 1]);
        }
    }

    public ProgramParams paramParsing(){
        return new ProgramParams(args.get("--data"), Integer.parseInt(args.get("--indexed-column-id")),
                args.get("--input-file"), args.get("--output-file"));
    }
}




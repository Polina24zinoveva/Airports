package com.example;

import java.util.HashMap;
import java.util.Map;

public class ProgramParamParser {

    public static ProgramParams paramParsing(String[] args) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            params.put(args[i], args[i + 1]);
        }
        if (params.containsKey("--data") && params.containsKey("--indexed-column-id") &&
                params.containsKey("--input-file") && params.containsKey("--output-file")){
            try {
                return new ProgramParams(
                        params.get("--data"),
                        Integer.parseInt(params.get("--indexed-column-id")),
                        params.get("--input-file"),
                        params.get("--output-file"));
            }
            catch (NumberFormatException e){
                throw new NumberFormatException("--indexed-column-id mast be integer");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid input data");
        }
    }

}




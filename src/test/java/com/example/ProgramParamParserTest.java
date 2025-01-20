package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProgramParamParserTest {
    @Test
    void allParam() throws Exception {
        String[] args = new String[] {"--data", "C:\\Users\\polin\\Desktop\\airports.csv", "--indexed-column-id", "2", "--input-file", "C:\\Users\\polin\\Desktop\\airportsText.txt", "--output-file", "C:\\Users\\polin\\Desktop\\output.json"};
        ProgramParams programParam = ProgramParamParser.paramParsing(args);
        ProgramParams programParamsExpected = new ProgramParams("C:\\Users\\polin\\Desktop\\airports.csv", 2, "C:\\Users\\polin\\Desktop\\airportsText.txt", "C:\\Users\\polin\\Desktop\\output.json");

        Assertions.assertEquals(programParamsExpected, programParam);
    }

    @Test
    void threeParam() throws Exception {
        String[] args = new String[] {"--data", "C:\\Users\\polin\\Desktop\\airports.csv", "--indexed-column-id", "2", "--input-file", "C:\\Users\\polin\\Desktop\\airportsText.txt"};

        Assertions.assertThrows(IllegalArgumentException.class, () -> ProgramParamParser.paramParsing(args));
    }

    @Test
    void twoParam() throws Exception {
        String[] args = new String[] {"--data", "C:\\Users\\polin\\Desktop\\airports.csv", "--indexed-column-id", "2"};

        Assertions.assertThrows(IllegalArgumentException.class, () -> ProgramParamParser.paramParsing(args));
    }

    @Test
    void oneParam() throws Exception {
        String[] args = new String[] {"--data", "C:\\Users\\polin\\Desktop\\airports.csv"};

        Assertions.assertThrows(IllegalArgumentException.class, () -> ProgramParamParser.paramParsing(args));
    }

    @Test
    void noneParam() throws Exception {
        String[] args = new String[] {};

        Assertions.assertThrows(IllegalArgumentException.class, () -> ProgramParamParser.paramParsing(args));
    }

    @Test
    void notIntParam() throws Exception {
        String[] args = new String[] {"--data", "C:\\Users\\polin\\Desktop\\airports.csv", "--indexed-column-id", "iiiiiii", "--input-file", "C:\\Users\\polin\\Desktop\\airportsText.txt", "--output-file", "C:\\Users\\polin\\Desktop\\output.json"};

        Assertions.assertThrows(NumberFormatException.class, () -> ProgramParamParser.paramParsing(args));
    }

}

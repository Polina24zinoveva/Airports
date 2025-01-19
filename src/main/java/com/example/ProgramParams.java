package com.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProgramParams {
    private String pathToCsv;
    private int indexedColumnId;
    private String inputPathToFile;
    private String outputPathToFile;

    public ProgramParams(String pathToCsv, int indexedColumnId, String inputPathToFile, String outputPathToFile) {
        this.pathToCsv = pathToCsv;
        this.indexedColumnId = indexedColumnId - 1;
        this.inputPathToFile = inputPathToFile;
        this.outputPathToFile = outputPathToFile;
    }
}

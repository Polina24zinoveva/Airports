package com.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramParams that = (ProgramParams) o;
        return indexedColumnId == that.indexedColumnId &&
                Objects.equals(pathToCsv, that.pathToCsv) &&
                Objects.equals(inputPathToFile, that.inputPathToFile) &&
                Objects.equals(outputPathToFile, that.outputPathToFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathToCsv, indexedColumnId, inputPathToFile, outputPathToFile);
    }
}

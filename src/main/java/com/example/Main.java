package com.example;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            ProgramParamParser programParamParser = new ProgramParamParser(args);
            ProgramParams programParams = programParamParser.paramParsing();

            long startTime = System.currentTimeMillis();
            List<String> stringsToSearch;
            try {
                // чтение слов для поиска
                stringsToSearch = getStringsToSearch(programParams);

                // подключение к csv файлу
                Trie<String, List<Integer>> lines = readFromCsv(programParams);

                // создание структуры для хранения номеров строк
                Map<String, Map<String, List<Integer>>> resultSearch = new HashMap<>();
                for (String str : stringsToSearch){
                    resultSearch.put(str, new TreeMap<>());
                }

                // создание структуры для хранения времени поиска
                Map<String, Long> times = new HashMap<>();
                for (String str : stringsToSearch){
                    times.put(str, 0L);
                }

                // вычисление времени инициализации от начала запуска программы до готовности к выполнению первого поиска.
                long endTime = System.currentTimeMillis();
                long initTime = endTime - startTime;

                // поиск
                long time;
                for (String str: stringsToSearch){
                    startTime = System.currentTimeMillis();
                    SortedMap<String, List<Integer>> newTrie = lines.prefixMap(str);
                    endTime = System.currentTimeMillis();
                    time = endTime - startTime;
                    resultSearch.put(str, new TreeMap<>(newTrie));
                    times.put(str, time);
                }

                // сохранение ответов в json файл
                Result result = new Result();
                result.writeToJsonFile(programParams.getOutputPathToFile(), result.toJson(initTime, resultSearch, times));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught");
        }
    }

    private static Trie<String, List<Integer>> readFromCsv(ProgramParams programParams) throws FileNotFoundException {
        Scanner scannerCsv = new Scanner(new File(programParams.getPathToCsv()));
        scannerCsv.useDelimiter(",");

        // сканирование csv файла
        Trie<String, List<Integer>> lines = new PatriciaTrie<>();
        while (scannerCsv.hasNext())
        {
            String[] values = scannerCsv.nextLine().split(",");
            if (programParams.getIndexedColumnId() < values.length)
            {
                String key = values[programParams.getIndexedColumnId()].trim().replaceAll("\"", "");
                List<Integer> value;

                if (lines.containsKey(key)) value = lines.get(key);
                else value = new ArrayList<>();

                value.add(Integer.valueOf(values[0]));
                lines.put(key, value);
            }
        }
        scannerCsv.close();
        return lines;
    }

    private static List<String> getStringsToSearch(ProgramParams programParams) throws IOException {
        return Files.lines(Path.of(programParams.getInputPathToFile())).collect(Collectors.toList());
    }
}


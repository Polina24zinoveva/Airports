package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
C:\Users\polin\Desktop\airports.csv
2
C:\Users\polin\Desktop\airportsText.txt
C:\Users\polin\Desktop\output.json

java -Xmx7m -jar target/Airports-1.0-SNAPSHOT.jar --data C:\Users\polin\Desktop\airports.csv --indexed-column-id 2 --input-file C:\Users\polin\Desktop\airportsText.txt --output-file C:\Users\polin\Desktop\output.json
*/


public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String pathToCsv = scanner.nextLine();
//        int indexedColumnId = scanner.nextInt();
//        indexedColumnId--;
//        scanner.nextLine();
//        String inputPathToFile = scanner.nextLine();
//        String outputPathToFile = scanner.nextLine();
//        scanner.close();


        try {
            String pathToCsv = args[1];
            int indexedColumnId = Integer.parseInt(args[3]);
            indexedColumnId--;
            String inputPathToFile = args[5];
            String outputPathToFile =  args[7];


            long startTime = System.currentTimeMillis();
            ArrayList<String> stringsToSearch = new ArrayList<>();
            try {
                // запись слов для поиска
                Scanner scannerFromFile = new Scanner(new File(inputPathToFile));
                while (scannerFromFile.hasNextLine()) {
                    stringsToSearch.add(scannerFromFile.nextLine());
                }
                scannerFromFile.close();

                // подключение к csv файлу
                Scanner scannerCsv = new Scanner(new File(pathToCsv));
                scannerCsv.useDelimiter(",");

                // сканирование csv файла
                Trie<String, Integer> lines = new PatriciaTrie<>();
                while (scannerCsv.hasNext())
                {
                    String[] values = scannerCsv.nextLine().split(",");
                    lines.put(values[indexedColumnId].replaceAll("\"", ""), Integer.valueOf(values[0]));
                }
                scannerCsv.close();

                // создание структуры для хранения номеров строк
                Map<String, Map<String, Integer>> resultSearch = new HashMap<>();
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

                long time;
                // поиск
                for (String str: stringsToSearch){
                    startTime = System.currentTimeMillis();
                    SortedMap<String, Integer> newTrie = lines.prefixMap(str);
                    endTime = System.currentTimeMillis();
                    time = endTime - startTime;
                    resultSearch.put(str, new TreeMap<>(newTrie));
                    times.put(str, time);
                }

                // сохранение ответов в json файл
                OutputJson outputJson = new OutputJson();
                ArrayList<OutputJson.ResultString> result = new ArrayList<>();

                for (Map.Entry<String, Map<String, Integer>> res : resultSearch.entrySet()){
                    ArrayList<Integer> valueList = new ArrayList<>(res.getValue().values());

                    result.add(outputJson.new ResultString(
                            res.getKey(),
                            valueList,
                            Math.toIntExact(times.get(res.getKey()))
                    ));
                }

                outputJson.setInitTime(Math.toIntExact(initTime));
                outputJson.setResult(result);


                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(new File(outputPathToFile), outputJson);
                System.out.println(mapper.writeValueAsString(outputJson));

            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught");
        }


    }
}


package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result {
    private int initTime;
    private ArrayList<Result.ResultString> result;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ResultString{
        private String search;
        private ArrayList<Integer> result;
        private int time;

    }

    public String toJson(Long initTime, Map<String, Map<String, List<Integer>>> resultSearch, Map<String, Long> times) throws IOException {

        ArrayList<Result.ResultString> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<Integer>>> res : resultSearch.entrySet()){
            ArrayList<Integer> valueList = res.getValue().values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toCollection(ArrayList::new));

            result.add(this.new ResultString(
                    res.getKey(),
                    valueList,
                    Math.toIntExact(times.get(res.getKey()))
            ));
        }

        this.setInitTime(Math.toIntExact(initTime));
        this.setResult(result);


        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(this));
        return mapper.writeValueAsString(this);
    }

    public void writeToJsonFile(String outputPathToFile, String outputJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(outputPathToFile), outputJson);
    }
}

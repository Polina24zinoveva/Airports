package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputJson {
    private int initTime;
    private ArrayList<ResultString> result;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ResultString{
        private String search;
        private ArrayList<Integer> result;
        private int time;

    }
}

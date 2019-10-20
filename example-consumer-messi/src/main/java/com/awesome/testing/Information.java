package com.awesome.testing;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Information {
    private Integer salary;
    private String name;
    private String nationality;
    private Map<String, String> contact = new HashMap<>();
}

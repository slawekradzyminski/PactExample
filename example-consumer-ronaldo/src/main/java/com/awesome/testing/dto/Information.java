package com.awesome.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {
    private String name;
    private String nationality;
    private Integer salary;
}
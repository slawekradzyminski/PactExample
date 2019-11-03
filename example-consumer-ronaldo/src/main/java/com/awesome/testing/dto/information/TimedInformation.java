package com.awesome.testing.dto.information;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TimedInformation {
    private long id;
    private String name;
    private String nationality;
    private int salary;
    private Instant timestamp;
}

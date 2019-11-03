package com.awesome.testing.dto.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNotAwareInformation {
    private String name;
    private String nationality;
    private int salary;

}

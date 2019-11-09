package com.awesome.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CustomMessage implements Serializable {

    private String text;
    private int priority;
    private boolean secret;

}

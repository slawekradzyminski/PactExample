package com.awesome.testing.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public final class CustomMessage implements Serializable {

    private final String text;
    private final int priority;
    private final boolean secret;

}

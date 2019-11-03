package com.awesome.testing.util;

import com.awesome.testing.dto.information.Information;
import org.json.JSONObject;

import java.time.Instant;

public class InformationTransformer {

    public String addTimestamp(Information information) {
        return new JSONObject()
                .put("id", information.getId())
                .put("name", information.getName())
                .put("salary", information.getSalary())
                .put("nationality", information.getNationality())
                .put("timestamp", Instant.now())
                .toString();
    }

}

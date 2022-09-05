package org.glebchanskiy.SensorRestAPI.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorErrorResponse {
    private String name;
    private Long timestamp;

    public SensorErrorResponse(String name, Long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }
}

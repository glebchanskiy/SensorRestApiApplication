package org.glebchanskiy.SensorRestAPI.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementErrorResponse {
    private String name;
    private Long timestamp;

    public MeasurementErrorResponse(String name, Long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }
}

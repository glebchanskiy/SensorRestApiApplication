package org.glebchanskiy.SensorRestAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.glebchanskiy.SensorRestAPI.models.Sensor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class MeasurementDTO {
    @Max(value = 100, message = "value greater than 100")
    @Min(value = -100, message = "value less than -100")
    private Integer value;
    private Boolean raining;
    private SensorDTO sensor;
}

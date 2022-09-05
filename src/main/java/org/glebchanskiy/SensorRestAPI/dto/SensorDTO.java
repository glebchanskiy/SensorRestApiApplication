package org.glebchanskiy.SensorRestAPI.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.glebchanskiy.SensorRestAPI.models.Measurement;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class SensorDTO {
    @Size(min = 3, max = 40, message = "sensor name should be between 3 and 40 characters")
    private String name;
}

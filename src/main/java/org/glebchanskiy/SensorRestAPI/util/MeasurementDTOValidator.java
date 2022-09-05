package org.glebchanskiy.SensorRestAPI.util;


import org.glebchanskiy.SensorRestAPI.dto.MeasurementDTO;
import org.glebchanskiy.SensorRestAPI.dto.SensorDTO;
import org.glebchanskiy.SensorRestAPI.models.Sensor;
import org.glebchanskiy.SensorRestAPI.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementDTOValidator implements Validator {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementDTOValidator(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        Optional<Sensor> sensor = sensorsRepository.findByName(measurementDTO.getSensor().getName());
        if (sensor.isEmpty()) {
            errors.rejectValue("sensor", "", "sensor not reg");
        }
//        } else {
//            measurementDTO.setSensor(sensor.get());
//        }
    }
}

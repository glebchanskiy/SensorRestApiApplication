package org.glebchanskiy.SensorRestAPI.util;

import org.glebchanskiy.SensorRestAPI.dto.SensorDTO;
import org.glebchanskiy.SensorRestAPI.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorDTOValidator implements Validator {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorDTOValidator(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTOValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        sensorsRepository.findByName(sensorDTO.getName())
                .ifPresent(sensor -> errors.rejectValue("name", "", "'" + sensorDTO.getName() + "' sensor already exist"));
    }
}

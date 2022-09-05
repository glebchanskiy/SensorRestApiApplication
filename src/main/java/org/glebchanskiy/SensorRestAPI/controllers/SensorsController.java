package org.glebchanskiy.SensorRestAPI.controllers;

import org.glebchanskiy.SensorRestAPI.Exceptions.SensorNotCreatedException;
import org.glebchanskiy.SensorRestAPI.Exceptions.SensorNotFoundedException;
import org.glebchanskiy.SensorRestAPI.dto.SensorDTO;
import org.glebchanskiy.SensorRestAPI.models.Sensor;
import org.glebchanskiy.SensorRestAPI.services.SensorsService;
import org.glebchanskiy.SensorRestAPI.util.SensorDTOValidator;
import org.glebchanskiy.SensorRestAPI.util.SensorErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorDTOValidator sensorDTOValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorDTOValidator sensorDTOValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorDTOValidator = sensorDTOValidator;
    }

    @GetMapping("/{id}")
    public SensorDTO findById(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorsService.findById(id));
    }

    @GetMapping
    public List<SensorDTO> findAll() {
        return sensorsService.findAll().stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorDTOValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError ->
                    errorMessage.append("Error in field: ")
                            .append(fieldError.getField())
                            .append(" - ")
                            .append(fieldError.getDefaultMessage())
                            .append(";")
            );
            throw new SensorNotCreatedException(errorMessage.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Сенсор не найден",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}

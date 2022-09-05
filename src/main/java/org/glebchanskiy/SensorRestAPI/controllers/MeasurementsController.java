package org.glebchanskiy.SensorRestAPI.controllers;

import org.glebchanskiy.SensorRestAPI.Exceptions.MeasurementNotCreatedException;
import org.glebchanskiy.SensorRestAPI.Exceptions.MeasurementNotFoundedException;
import org.glebchanskiy.SensorRestAPI.dto.MeasurementDTO;
import org.glebchanskiy.SensorRestAPI.models.Measurement;
import org.glebchanskiy.SensorRestAPI.models.Sensor;
import org.glebchanskiy.SensorRestAPI.services.MeasurementsService;
import org.glebchanskiy.SensorRestAPI.services.SensorsService;
import org.glebchanskiy.SensorRestAPI.util.MeasurementDTOValidator;
import org.glebchanskiy.SensorRestAPI.util.MeasurementErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    private final MeasurementDTOValidator measurementDTOValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService, ModelMapper modelMapper, MeasurementDTOValidator measurementDTOValidator) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.measurementDTOValidator = measurementDTOValidator;
    }

    @GetMapping
    public List<MeasurementDTO> findAll() {
        return measurementsService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public MeasurementDTO findById(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementsService.findById(id));
    }

    @GetMapping("/rainyDaysCount")
    public HashMap<String, Integer> getRainyDaysCount() {
        Integer count = measurementsService.getRainyDaysCount();
        HashMap<String, Integer> json = new HashMap<>();
        json.put("rainyDaysCount", count);
        return json;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        measurementDTOValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError ->
                    errorMessage.append("Error in field: ")
                            .append(fieldError.getField())
                            .append(" - ")
                            .append(fieldError.getDefaultMessage())
                            .append(";")
                    );

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        Sensor sensor = sensorsService.findByName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);

        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Запись не найдена",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}

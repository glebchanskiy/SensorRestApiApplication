package org.glebchanskiy.SensorRestAPI.services;

import org.glebchanskiy.SensorRestAPI.Exceptions.SensorNotFoundedException;
import org.glebchanskiy.SensorRestAPI.dto.SensorDTO;
import org.glebchanskiy.SensorRestAPI.models.Sensor;
import org.glebchanskiy.SensorRestAPI.repositories.MeasurementsRepository;
import org.glebchanskiy.SensorRestAPI.repositories.SensorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    public SensorsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public Sensor findById(int id) {
        return sensorsRepository.findById(id)
                .orElseThrow(SensorNotFoundedException::new);
    }

    public Sensor findByName(String name) {
        return sensorsRepository.findByName(name)
                .orElseThrow(SensorNotFoundedException::new);
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    @Transactional
    public void save(Sensor sensor) {
        enrichSensor(sensor);
        sensorsRepository.save(sensor);
    }

    public void enrichSensor(Sensor sensor) {}


}

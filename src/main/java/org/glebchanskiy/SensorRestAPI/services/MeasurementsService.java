package org.glebchanskiy.SensorRestAPI.services;

import org.glebchanskiy.SensorRestAPI.Exceptions.MeasurementNotFoundedException;
import org.glebchanskiy.SensorRestAPI.models.Measurement;
import org.glebchanskiy.SensorRestAPI.models.Sensor;
import org.glebchanskiy.SensorRestAPI.repositories.MeasurementsRepository;
import org.glebchanskiy.SensorRestAPI.repositories.SensorsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }
    
    public Measurement findById(int id) {
        return measurementsRepository.findById(id).orElseThrow(MeasurementNotFoundedException::new);
    }
    
    public List<Measurement> findAll() {
        List<Measurement> list = measurementsRepository.findAll();
//        list.forEach(Hibernate::initialize);
//        list.forEach(System.out::println);
        return list;
    }
    
    @Transactional
    public void save(Measurement measurement) {
        enrich(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrich(Measurement measurement) {

    }

    public Integer getRainyDaysCount() {
        return measurementsRepository.countAllByRainingTrue();
    }
}

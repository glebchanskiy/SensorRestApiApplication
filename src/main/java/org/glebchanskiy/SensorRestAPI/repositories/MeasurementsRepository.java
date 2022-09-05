package org.glebchanskiy.SensorRestAPI.repositories;

import org.glebchanskiy.SensorRestAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    Integer countAllByRainingTrue();
}

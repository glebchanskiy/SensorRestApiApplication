package org.glebchanskiy.SensorRestAPI.Exceptions;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}

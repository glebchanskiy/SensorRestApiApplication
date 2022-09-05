package org.glebchanskiy.SensorRestAPI.Exceptions;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}

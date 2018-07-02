package edu.neu.ccs.wellness.adcaregiverapp.network.people;

/**
 * Created by hermansaksono on 3/20/18.
 */

public class PersonDoesNotExistException extends Exception {

    public PersonDoesNotExistException(String message) {
        super(message);
    }
}

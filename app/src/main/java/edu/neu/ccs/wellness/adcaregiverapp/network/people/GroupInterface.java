package edu.neu.ccs.wellness.adcaregiverapp.network.people;

import java.util.List;

/**
 * Created by hermansaksono on 11/3/17.
 */

public interface GroupInterface {

    int getId();

    String getName();

    List<Person> getMembers();
}

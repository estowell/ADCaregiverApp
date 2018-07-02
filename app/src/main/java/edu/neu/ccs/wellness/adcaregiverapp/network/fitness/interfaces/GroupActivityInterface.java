package edu.neu.ccs.wellness.adcaregiverapp.network.fitness.interfaces;

import java.util.List;
import java.util.Map;

import edu.neu.ccs.wellness.adcaregiverapp.network.people.Person;
import edu.neu.ccs.wellness.adcaregiverapp.network.server.AuthUser;

/**
 * Created by hermansaksono on 6/14/17.
 */

public interface GroupActivityInterface {

    GroupActivityInterface create(AuthUser user, String startDate, String endDate);

    String getStartDate();

    String getEndDate();

    List<Person> getMembers();

    Map<Person, List<OneDayFitnessInterface>> getDailyActivities();

}

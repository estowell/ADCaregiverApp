package edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness;

import android.content.Context;

import org.json.JSONArray;

import java.util.Map;

import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces.GroupFitnessInterface;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces.MultiDayFitnessInterface;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.people.Person;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.people.PersonDoesNotExistException;

/**
 * Created by hermansaksono on 3/20/18.
 */

public class GroupFitness implements GroupFitnessInterface {

    //PRIVATE MEMBERS
    private Map<Person, MultiDayFitnessInterface> personMultiDayFitnessMap;
    private Context context;


    private GroupFitness(Context context, Map<Person, MultiDayFitnessInterface> personMultiDayFitnessMap ){
        this.context = context;
        this.personMultiDayFitnessMap = personMultiDayFitnessMap;
    }

    public static GroupFitness create(Context context, Map<Person, MultiDayFitnessInterface> personMultiDayFitnessMap){
        return new GroupFitness(context, personMultiDayFitnessMap);
    }


    @Override
    public MultiDayFitnessInterface getAPersonMultiDayFitness(Person person)
            throws PersonDoesNotExistException {
        if (personMultiDayFitnessMap.containsKey(person)) {
            return personMultiDayFitnessMap.get(person);
        } else {
            throw new PersonDoesNotExistException("Person does not exist.");
        }
    }

    @Override
    public Map<Person, MultiDayFitnessInterface> getGroupFitness() {
        return personMultiDayFitnessMap;
    }

    private void setPersonMultiDayFitnessMap(JSONArray jsonArray){

    }

    public Map<Person, MultiDayFitnessInterface> getPersonMultiDayFitnessMap(){
        return this.personMultiDayFitnessMap;
    }
}

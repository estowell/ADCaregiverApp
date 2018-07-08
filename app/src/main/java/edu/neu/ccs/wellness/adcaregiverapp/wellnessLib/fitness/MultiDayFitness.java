package edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness;

import android.content.Context;

import java.util.Date;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces.MultiDayFitnessInterface;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces.OneDayFitnessInterface;

/**
 * Created by hermansaksono on 3/20/18.
 */

public class MultiDayFitness implements MultiDayFitnessInterface {

    //PRIVATE MEMBERS
    private Date startDate;
    private Date endDate;
    private int numberOfDays;
    private int elapsedDays;
    private Context context;
    private List<OneDayFitnessInterface> oneDayFitnessInterfaces;

    public MultiDayFitness(Context context, Date startDate, Date endDate,
                           int numberOfDays, int elapsedDays,
                           List<OneDayFitnessInterface> oneDayFitnessInterfaces){
        this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfDays = numberOfDays;
        this.elapsedDays = elapsedDays;
        this.oneDayFitnessInterfaces = oneDayFitnessInterfaces;
    }

    public static MultiDayFitness create(Context context, Date startDate, Date endDate,
                                         int numberOfDays, int elapsedDays,
                                         List<OneDayFitnessInterface> oneDayFitnessInterfaces ){
        return new MultiDayFitness(context, startDate, endDate,
                numberOfDays, elapsedDays, oneDayFitnessInterfaces);
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public int getNumDays() {
        return this.numberOfDays;
    }

    @Override
    public int getElapsedDays() {
        return this.elapsedDays;
    }

    @Override
    public List<OneDayFitnessInterface> getDailyFitness() {
        return this.oneDayFitnessInterfaces;
    }
}

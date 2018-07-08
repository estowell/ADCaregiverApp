package edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces;

import java.util.Date;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.challenges.ChallengeProgress;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.challenges.UnitChallenge;

/**
 * Created by RAJ on 2/19/2018.
 */

public interface RunningChallengeInterface {
    /**
     * Returns true all the time
     * @return
     */
    boolean isCurrentlyRunning();

    /**
     * Get the primary text that explains the challenge
     * @return
     */
    String getText();

    /**
     * Get the secondary text that explains the challenge
     * @return
     */
    String getSubText();

    /**
     * Get the duration of the challenge
     * @return String code of the duration
     */
    String getTotalDuration();

    /**
     * Get the start date of a running and unsynced challenge
     */
    Date getStartDate();

    /**
     * Get the end date of a running and unsynced challenge
     */
    Date getEndDate();

    UnitChallenge getUnitChallenge();

    int getLevelId();

    int getLevelOrder();

    List<ChallengeProgress> getChallengeProgress();
}

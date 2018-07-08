package edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.interfaces;

import org.json.JSONException;

import java.io.IOException;

import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.fitness.challenges.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.server.RestServer;

/**
 * Created by hermansaksono on 2/5/18.
 */

public interface ChallengeManagerInterface {

    /**
     * Return the user's current ChallengeStatus. If there is no saved challenge in internal storage
     * then return ChallengeStatus.UNITIALIZED
     * @return The status of user's UnitChallenge
     */
    ChallengeStatus getStatus() throws IOException, JSONException;

    /**
     * Get the a list of available challenges if the ChallengeStatus is either UNSTARTED or AVAILABLE.
     * @return Available challenges
     */
    AvailableChallengesInterface getAvailableChallenges() throws IOException, JSONException;

    /**
     * Set the running challenge if the ChallengeStatus is AVAILABLE. Then sets the status to
     * UNSYNCED_RUN. This function MUST save the given challenge to a persistent storage.
     * It should not sync the given challenge to server.
     * @param challenge
     */
    void setRunningChallenge(UnitChallenge challenge) throws IOException, JSONException;

    /**
     * Get the currently unsynced running UnitChallenge if the ChallengeStatus is UNSYNCED_RUN.
     * @return Currently running but unsynced unit challenge
     */
    UnitChallengeInterface getUnsyncedChallenge() throws IOException, JSONException;

    /**
     * Get the currently running UnitChallenge if the ChallengeStatus is RUNNING.
     * @return Currently running unit challenge
     */
    UnitChallengeInterface getRunningChallenge() throws IOException, JSONException;

    /**
     * Get the currently running UnitChallenge if the ChallengeStatus is UNSYNCED_RUN or RUNNING.
     * @return Currently running challenge
     */
    UnitChallengeInterface getUnsyncedOrRunningChallenge() throws Exception;


    /**
     * Sync the running challenge (that was saved in the persistent storage) with the server.
     * @return The status of the synchronization
     */
    RestServer.ResponseType syncRunningChallenge();

    /**
     *
     */

    void completeChallenge() throws IOException, JSONException;

    void syncCompletedChallenge() throws IOException, JSONException;

    void changeChallengeStatus(int state) throws Exception;
}

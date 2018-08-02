package edu.neu.ccs.wellness.adcaregiverapp.repository;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.JSONUtils;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.GetExercisesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;

public class ExerciseRepository {

    private Context context;

    @Inject
    public ExerciseRepository(Context context) {
        this.context = context;
    }

    public void getExercises(ExerciseType type, GetExercisesUseCase.GetExercisesUseCaseCallBack caseCallBack) {

        String regex = "";

        switch (type) {
            case FLEXIBILITY:
                regex = "flexibility";
                break;
            case ENDURANCE:
                regex = "endurance";
                break;
            case STRENGTH:
                regex = "strength";
                break;
            case BALANCE:
                regex = "balance";
                break;
        }

        String[] assests = null;

        try {
            assests = context.getAssets().list("");
        } catch (IOException e) {
            caseCallBack.onError();
            e.printStackTrace();
        }

        Map<String, JSONObject> jsonObjects = new HashMap<>();

        for (String asset : Objects.requireNonNull(assests)) {

            if (asset.contains(regex)) {
                String jsonString = JSONUtils.loadJSONFromAsset(context, asset);
                try {
                    jsonObjects.put(asset.replace(regex + "_", ""), new JSONObject(jsonString));

                } catch (JSONException e) {
                    caseCallBack.onError();
                    e.printStackTrace();

                }
            }

        }

        caseCallBack.onSuccess(jsonObjects);
    }
}

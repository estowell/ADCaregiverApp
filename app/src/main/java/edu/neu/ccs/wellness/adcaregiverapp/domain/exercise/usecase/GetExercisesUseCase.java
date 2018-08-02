package edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.model.Exercise;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ExerciseRepository;

public class GetExercisesUseCase extends UseCase<GetExercisesUseCase.Request, GetExercisesUseCase.Response> {

    private ExerciseRepository repository;


    public GetExercisesUseCase(UseCaseCallback callback, ExerciseRepository repository) {
        super(callback);
        this.repository = repository;
    }

    @Override
    public void run() {
        new Call().execute();
    }

    public static class Request implements UseCase.RequestValues {
        private ExerciseType type;

        public Request(ExerciseType type) {
            this.type = type;
        }

        public ExerciseType getType() {
            return type;
        }
    }

    public class Response implements UseCase.ResponseValue {

        @Nullable
        private String message;

        @Nullable
        private List<Exercise> exercises;

        public Response(String message) {
            this.message = message;
        }

        public Response(List<Exercise> exercises) {
            this.exercises = exercises;
        }


        @Nullable
        public String getMessage() {
            return message;
        }

        @Nullable
        public List<Exercise> getExercises() {
            return exercises;
        }
    }

    private class Call {

        public void execute() {
            repository.getExercises(getRequestValues().getType(), new GetExercisesUseCaseCallBack() {
                @Override
                public void onSuccess(Map<String, JSONObject> tutorials) {
                    List<Exercise> exercises = new ArrayList<>();

                    for (Map.Entry<String, JSONObject> tutorial : tutorials.entrySet()) {
                        String head;
                        String url;
                        List<String> steps = new ArrayList<>();
                        try {
                            JSONObject value = tutorial.getValue();
                            head = value.getString("heading");
                            url = value.getString("url");
                            if (!value.isNull("steps")) {
                                Iterator keys = value.getJSONObject("steps").keys();
                                while (keys.hasNext()) {
                                    String currentKey = keys.next().toString();
                                    steps.add(value.getJSONObject("steps").getString(currentKey));
                                }
                            }
                            String exerciseName = tutorial.getKey().replace("_", " ");
                            exerciseName = exerciseName.replace(".json", "");
                            exercises.add(new Exercise(url, steps, head, exerciseName));


                        } catch (JSONException e) {
                            getUseCaseCallback().onError(new Response("Error Mapping Response"));
                            e.printStackTrace();
                        }
                    }
                    getUseCaseCallback().onSuccess(new Response(exercises));
                }

                @Override
                public void onError() {
                    getUseCaseCallback().onError(new Response("Error Feteching Data"));
                }
            });
        }

    }

    public interface GetExercisesUseCaseCallBack {
        void onSuccess(Map<String, JSONObject> tutorials);

        void onError();

    }
}

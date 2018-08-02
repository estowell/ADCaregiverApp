package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.tutorialFragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.GetExercisesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.model.Exercise;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ExerciseRepository;

public class TutorialListViewModel extends ViewModel {

    private ExerciseRepository repository;

    @Inject
    public TutorialListViewModel(ExerciseRepository repository) {
        this.repository = repository;
    }

    private MutableLiveData<List<Exercise>> exerciseObserver;

    private MutableLiveData<String> errorLiverData;

    public MutableLiveData<List<Exercise>> getGetExerciseObserver() {

        if (exerciseObserver == null) {
            exerciseObserver = new MutableLiveData<>();
        }

        return exerciseObserver;
    }

    public MutableLiveData<String> getErrorLiverData() {
        if (errorLiverData == null) {
            errorLiverData = new MutableLiveData<>();
        }
        return errorLiverData;
    }

    public void getTutorials(ExerciseType type) {
        GetExercisesUseCase useCase = new GetExercisesUseCase(new UseCase.UseCaseCallback<GetExercisesUseCase.Response>() {

            @Override
            public void onSuccess(GetExercisesUseCase.Response response) {
                getGetExerciseObserver().setValue(response.getExercises());
            }

            @Override
            public void onError(GetExercisesUseCase.Response response) {
                getErrorLiverData().setValue(response.getMessage());
            }

            @Override
            public void onFailure() {
                //Not Implemented : DO Nothing
            }

        }, repository);
        useCase.setRequestValues(new GetExercisesUseCase.Request(type));
        useCase.run();
    }


}

package roomdemo.wiseass.com.roomdemo.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gianmarco Moretti on 31/12/2017.
 */

public class ExerciseRepository {

    private final ExerciseDao exerciseDao;

    @Inject
    public ExerciseRepository(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    public LiveData<Exercise> getExerciseById(int id){
        return this.exerciseDao.getExerciseById(id);
    }

    public LiveData<List<Exercise>> getListExercises(){
         return this.exerciseDao.getAllExercises();
    }

    public Long createNewExercise(Exercise exercise){
        return this.exerciseDao.insertAllExercises(exercise);
    }

    public void deleteExercises(Exercise exercise){
        this.exerciseDao.deleteExercise(exercise);
    }

}

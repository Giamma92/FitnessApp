package roomdemo.wiseass.com.roomdemo.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Gianmarco Moretti on 31/12/2017.
 */

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercises WHERE id = :id")
    LiveData<Exercise> getExerciseById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertAllExercises(Exercise exercises);

    @Delete
    void deleteExercise(Exercise exercise);
}

package roomdemo.wiseass.com.roomdemo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Gianmarco Moretti on 31/12/2017.
 */

@Entity(tableName = "exercises")
public class Exercise {

    public Exercise(String name, String muscleArea, int numberSeries, int numberReps) {
        this.name = name;
        this.muscleArea = muscleArea;
        this.numberSeries = numberSeries;
        this.numberReps = numberReps;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "muscle_area")
    private String muscleArea;
    @ColumnInfo(name = "number_series")
    private int numberSeries;
    @ColumnInfo(name = "number_reps")
    private int numberReps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleArea() {
        return muscleArea;
    }

    public void setMuscleArea(String muscleArea) {
        this.muscleArea = muscleArea;
    }

    public int getNumberSeries() {
        return numberSeries;
    }

    public void setNumberSeries(int numberSeries) {
        this.numberSeries = numberSeries;
    }

    public int getNumberReps() {
        return numberReps;
    }

    public void setNumberReps(int numberReps) {
        this.numberReps = numberReps;
    }
}

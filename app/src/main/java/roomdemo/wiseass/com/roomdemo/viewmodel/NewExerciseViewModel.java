/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package roomdemo.wiseass.com.roomdemo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import roomdemo.wiseass.com.roomdemo.data.Exercise;
import roomdemo.wiseass.com.roomdemo.data.ExerciseRepository;

/**
 * Created by R_KAY on 8/11/2017.
 */

public class NewExerciseViewModel extends ViewModel {

    private ExerciseRepository repository;

    NewExerciseViewModel(ExerciseRepository repository) {
        this.repository = repository;
    }

    /**
     * Attach our LiveData to the Database
     */
    public void addNewExerciseDatabase(Exercise exercise){
        new AddItemTask().execute(exercise);
    }

    private class AddItemTask extends AsyncTask<Exercise, Void, Void> {

        @Override
        protected Void doInBackground(Exercise... exercises) {
            repository.createNewExercise(exercises[0]);
            return null;
        }
    }
}

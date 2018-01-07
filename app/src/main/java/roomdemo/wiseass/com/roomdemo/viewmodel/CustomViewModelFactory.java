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
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import roomdemo.wiseass.com.roomdemo.data.ExerciseRepository;

/**
 * Created by R_KAY on 8/17/2017.
 */
@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ExerciseRepository repository;

    @Inject
    public CustomViewModelFactory(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ExerciseCollectionViewModel.class))
            return (T) new ExerciseCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ExerciseViewModel.class))
            return (T) new ExerciseViewModel(repository);

        else if (modelClass.isAssignableFrom(NewExerciseViewModel.class))
            return (T) new NewExerciseViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}

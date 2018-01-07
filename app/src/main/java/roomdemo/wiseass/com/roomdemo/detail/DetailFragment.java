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

package roomdemo.wiseass.com.roomdemo.detail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import roomdemo.wiseass.com.roomdemo.R;
import roomdemo.wiseass.com.roomdemo.FitnessApplication;
import roomdemo.wiseass.com.roomdemo.data.Exercise;
import roomdemo.wiseass.com.roomdemo.viewmodel.ExerciseViewModel;


public class DetailFragment extends LifecycleFragment {

    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    public TextView nameExercise;
    public TextView muscleArea;
    public TextView nrSeries;
    public TextView nrReps;

    private int exerciseId;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ExerciseViewModel exerciseViewModel;

    public DetailFragment() {
    }


    public static DetailFragment newInstance(int exerciseId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ITEM_ID, exerciseId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((FitnessApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        Bundle args = getArguments();

        this.exerciseId = args.getInt(EXTRA_ITEM_ID);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        exerciseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ExerciseViewModel.class);

        exerciseViewModel.getExerciseById(exerciseId).observe(this, new Observer<Exercise>() {
            @Override
            public void onChanged(@Nullable Exercise exercise) {
                if (exercise != null) {

                    exerciseId = exercise.getId();

                    nameExercise.setText(exercise.getName());
                    muscleArea.setText(exercise.getMuscleArea());
                    nrSeries.setText(""+exercise.getNumberSeries());
                    nrReps.setText(""+exercise.getNumberReps());              }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        nameExercise = v.findViewById(R.id.lbl_name);
        muscleArea = v.findViewById(R.id.lbl_muscle_area);
        nrSeries = v.findViewById(R.id.lbl_number_series);
        nrReps = v.findViewById(R.id.lbl_number_reps);


        //message = (TextView) v.findViewById(R.id.lbl_message_body);
        //coloredBackground = (ImageView) v.findViewById(R.id.imv_colored_background);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}




















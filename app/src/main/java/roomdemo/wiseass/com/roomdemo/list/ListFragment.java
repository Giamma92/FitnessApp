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

package roomdemo.wiseass.com.roomdemo.list;


import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import roomdemo.wiseass.com.roomdemo.R;
import roomdemo.wiseass.com.roomdemo.FitnessApplication;
import roomdemo.wiseass.com.roomdemo.create.CreateActivity;
import roomdemo.wiseass.com.roomdemo.data.Exercise;
import roomdemo.wiseass.com.roomdemo.detail.DetailActivity;
import roomdemo.wiseass.com.roomdemo.viewmodel.ExerciseCollectionViewModel;

/**
 * Please Note!!!
 * LifeCycle Fragment is a temporary class, and likely by the time you watch this, the standard
 * Fragment implementation will suffice. Architecture Components isn't finalized.
 * <p>
 * You have been told.
 */
public class ListFragment extends LifecycleFragment {

    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    private List<Exercise> listOfData;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Toolbar toolbar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ExerciseCollectionViewModel listItemCollectionViewModel;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

        /*------------------------------- Lifecycle -------------------------------*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((FitnessApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ExerciseCollectionViewModel.class);

        listItemCollectionViewModel.getListItems().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if (ListFragment.this.listOfData == null) {
                    setListData(exercises);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rec_list_activity);
        layoutInflater = getActivity().getLayoutInflater();
        toolbar = (Toolbar) v.findViewById(R.id.tlb_list_activity);

        toolbar.setTitle(R.string.title_toolbar);
        toolbar.setLogo(R.drawable.ic_view_list_white_24dp);
        toolbar.setTitleMarginStart(72);

        FloatingActionButton fabulous = (FloatingActionButton) v.findViewById(R.id.fab_create_new_item);

        fabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateActivity();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void startDetailActivity(int exerciseId, View viewRoot) {
        Activity container = getActivity();
        Intent i = new Intent(container, DetailActivity.class);
        i.putExtra(EXTRA_ITEM_ID, exerciseId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getWindow().setEnterTransition(new Fade(Fade.IN));
            container.getWindow().setEnterTransition(new Fade(Fade.OUT));

            /*ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(container,
                            new Pair<>(viewRoot.findViewById(R.id.lbl_name_exercise),
                                    getString(R.string.transition_drawable)),
                            new Pair<>(viewRoot.findViewById(R.id.lbl_muscle_area),
                                    getString(R.string.transition_message)),
                            new Pair<>(viewRoot.findViewById(R.id.lbl_number_reps),
                                    getString(R.string.transition_time_and_date)));*/



            startActivity(i);//, options.toBundle()

        } else {
            startActivity(i);
        }
    }

    public void startCreateActivity() {
        startActivity(new Intent(getActivity(), CreateActivity.class));
    }


    public void setListData(List<Exercise> listOfData) {
            this.listOfData = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        getActivity(),
                        R.drawable.divider_white
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    /*-------------------- RecyclerView Boilerplate ----------------------*/

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {//6

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_data, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
            //11. and now the ViewHolder data
            Exercise currentExercise = listOfData.get(position);


            holder.nameExercise.setText(currentExercise.getName());
            holder.muscleArea.setText(currentExercise.getMuscleArea());
            holder.nrSeries.setText(""+currentExercise.getNumberSeries());
            holder.nrReps.setText(""+currentExercise.getNumberReps());



            //holder.coloredCircle.setImageResource(currentItem.getColorResource());

            /*holder.message.setText(
                    currentItem.getMessage()
            );

            holder.dateAndTime.setText(
                    currentItem.getItemId()
            );*/

            holder.loading.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            // 12. Returning 0 here will tell our Adapter not to make any Items. Let's fix that.
            return listOfData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //10. now that we've made our layouts, let's bind them

            public TextView nameExercise;
            public TextView muscleArea;
            public TextView nrSeries;
            public TextView nrReps;

            private ViewGroup container;
            private ProgressBar loading;

            public CustomViewHolder(View itemView) {
                super(itemView);

                nameExercise = itemView.findViewById(R.id.name_exercise);
                muscleArea = itemView.findViewById(R.id.muscle_area);
                nrSeries = itemView.findViewById(R.id.number_series);
                nrReps = itemView.findViewById(R.id.number_reps);

                this.loading = (ProgressBar) itemView.findViewById(R.id.pro_item_data);
                this.container = (ViewGroup) itemView.findViewById(R.id.root_list_item);

                /*
                We can pass "this" as an Argument, because "this", which refers to the Current
                Instance of type CustomViewHolder currently conforms to (implements) the
                View.OnClickListener interface. I have a Video on my channel which goes into
                Interfaces with Detailed Examples.

                Search "Android WTF: Java Interfaces by Example"
                 */
                this.container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                Exercise exercise = listOfData.get(
                        this.getAdapterPosition()
                );

                startDetailActivity(exercise.getId(), v);

            }
        }

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listItemCollectionViewModel.deleteListItem(
                        listOfData.get(position)
                );

                //ensure View is consistent with underlying data
                listOfData.remove(position);
                adapter.notifyItemRemoved(position);


            }
        };
        return simpleItemTouchCallback;
    }
}
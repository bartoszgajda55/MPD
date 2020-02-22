package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.PlannedRoadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;

import java.util.ArrayList;
import java.util.List;

public class RoadworksListFragment extends Fragment {
  public static final int DATE_DAILOG_FRAGMENT = 1;
  public static final int FEED_FILTER_DIALOG_FRAGMENT = 2;

  private RoadworksListViewModel roadworksListViewModel;
  private ListView roadworkListView;
  private RoadworksListAdapter roadworksListAdapter;
  private EditText searchInput;
  private List<Roadwork> roadworks;
  private List<PlannedRoadwork> plannedRoadworks;
  private String feedFilterOption;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    roadworksListViewModel = ViewModelProviders.of(this).get(RoadworksListViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_roadworks_list, container, false);

    roadworkListView = root.findViewById(R.id.roadworks_list);
    searchInput = root.findViewById(R.id.roadworks_list_search);

    this.roadworks = new ArrayList<>();
    this.plannedRoadworks = new ArrayList<>();
    roadworksListAdapter = new RoadworksListAdapter(root.getContext(), new ArrayList<RoadworkModel>());
    roadworkListView.setAdapter(roadworksListAdapter);

    roadworksListViewModel.getRoadworks().observe(this, new Observer<List<Roadwork>>() {
      @Override
      public void onChanged(List<Roadwork> roadworks) {
        RoadworksListFragment.this.roadworks = roadworks;
        RoadworksListFragment.this.roadworksListAdapter.addAll(roadworks);
        RoadworksListFragment.this.roadworksListAdapter.notifyDataSetChanged();
      }
    });

    roadworksListViewModel.getPlannedRoadworks().observe(this, new Observer<List<PlannedRoadwork>>() {
      @Override
      public void onChanged(List<PlannedRoadwork> plannedRoadworks) {
        RoadworksListFragment.this.plannedRoadworks = plannedRoadworks;
        RoadworksListFragment.this.roadworksListAdapter.addAll(plannedRoadworks);
        RoadworksListFragment.this.roadworksListAdapter.notifyDataSetChanged();
      }
    });

    searchInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        RoadworksListFragment.this.roadworksListAdapter.getFilter().filter(charSequence);
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    setHasOptionsMenu(true);
    return root;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.roadworks_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getTitle().equals("Filter feed")) {
      this.showFeedFilterDialog();
    }
    if (item.getTitle().equals("Specify date")) {
      this.showDateDialog();
    }
    return super.onOptionsItemSelected(item);
  }

  private void showFeedFilterDialog() {
    RoadworksListFeedDialogFragment roadworksListFeedDialogFragment = new RoadworksListFeedDialogFragment();
    roadworksListFeedDialogFragment.setTargetFragment(RoadworksListFragment.this, FEED_FILTER_DIALOG_FRAGMENT);
    roadworksListFeedDialogFragment.show(getFragmentManager().beginTransaction(), "date-feed-filter");
  }

  private void showDateDialog() {
    RoadworksListDateDialogFragment roadworksListDateDialogFragment = new RoadworksListDateDialogFragment();
    roadworksListDateDialogFragment.setTargetFragment(RoadworksListFragment.this, DATE_DAILOG_FRAGMENT);
    roadworksListDateDialogFragment.show(getFragmentManager().beginTransaction(), "date-dialog");
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    switch (requestCode) {
      case DATE_DAILOG_FRAGMENT:
        if (resultCode == Activity.RESULT_OK) {
          Bundle bundle = data.getExtras();
          String resultDate = bundle.getString("selectedDate", "Both");
        }
        break;
      case FEED_FILTER_DIALOG_FRAGMENT:
        if (resultCode == Activity.RESULT_OK) {
          Bundle bundle = data.getExtras();
          String resultFeedFilter = bundle.getString("selectedFeedFilter", "Both");
          this.updateListAdapterWithFeedFilter(resultFeedFilter);
        }
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void updateListAdapterWithFeedFilter(String feedFilterOption) {
    RoadworksListFragment.this.roadworksListAdapter.clear();
    switch (feedFilterOption) {
      case "Current Roadworks":
        RoadworksListFragment.this.roadworksListAdapter.addAll(this.roadworks);
        break;
      case "Planned Roadworks":
        RoadworksListFragment.this.roadworksListAdapter.addAll(this.plannedRoadworks);
        break;
      case "Both":
        RoadworksListFragment.this.roadworksListAdapter.addAll(this.roadworks);
        RoadworksListFragment.this.roadworksListAdapter.addAll(this.plannedRoadworks);
        break;
    }
    RoadworksListFragment.this.roadworksListAdapter.notifyDataSetChanged();
  }
}
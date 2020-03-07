package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.PlannedRoadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class RoadworksListFragment extends Fragment {
  private static final int DATE_DIALOG_FRAGMENT = 1;
  private static final int FEED_FILTER_DIALOG_FRAGMENT = 2;
  private static final int MAP_DIALOG_FRAGMENT = 3;

  private RoadworksListViewModel roadworksListViewModel;
  private ListView roadworkListView;
  private RoadworksListAdapter roadworksListAdapter;
  private EditText searchInput;
  private TextView roadworksCount;
  private Button showOnMap;
  private List<Roadwork> roadworks;
  private List<PlannedRoadwork> plannedRoadworks;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    roadworksListViewModel = ViewModelProviders.of(this).get(RoadworksListViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_roadworks_list, container, false);

    roadworkListView = root.findViewById(R.id.roadworks_list);
    searchInput = root.findViewById(R.id.roadworks_list_search);
    roadworksCount = root.findViewById(R.id.roadworks_count);
    showOnMap = root.findViewById(R.id.roadwork_show_on_map);

    this.roadworks = new ArrayList<>();
    this.plannedRoadworks = new ArrayList<>();
    roadworksListAdapter = new RoadworksListAdapter(root.getContext(), new ArrayList<>());
    roadworkListView.setAdapter(roadworksListAdapter);

    roadworksListViewModel.getRoadworks().observe(this, roadworks -> {
      RoadworksListFragment.this.roadworks = roadworks;
      RoadworksListFragment.this.roadworksListAdapter.addAll(roadworks);
      RoadworksListFragment.this.roadworksListAdapter.notifyDataSetChanged();
    });

    roadworksListViewModel.getPlannedRoadworks().observe(this, plannedRoadworks -> {
      RoadworksListFragment.this.plannedRoadworks = plannedRoadworks;
      RoadworksListFragment.this.roadworksListAdapter.addAll(plannedRoadworks);
      RoadworksListFragment.this.roadworksListAdapter.notifyDataSetChanged();
    });

    searchInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        RoadworksListFragment.this.roadworksListAdapter.getFilter().filter(charSequence);
      }
      @Override
      public void afterTextChanged(Editable editable) {
      }
    });

    this.roadworksListAdapter.registerDataSetObserver(new DataSetObserver() {
      @Override
      public void onChanged() {
        roadworksCount.setText("Roadworks count: " + RoadworksListFragment.this.roadworksListAdapter.getCount());
      }
    });

    showOnMap.setOnClickListener(view -> {
      List<RoadworkModel> filteredRoadworks = new ArrayList<>();
      for(int i = 0; i < this.roadworksListAdapter.getCount(); i++) {
        filteredRoadworks.add(this.roadworksListAdapter.getItem(i));
      }
      RoadworksListMapDialogFragment roadworksListMapDialogFragment = new RoadworksListMapDialogFragment(filteredRoadworks);
      roadworksListMapDialogFragment.setTargetFragment(RoadworksListFragment.this, MAP_DIALOG_FRAGMENT);
      roadworksListMapDialogFragment.show(getFragmentManager().beginTransaction(), "map");
    });

    setHasOptionsMenu(true);
    return root;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.roadworks_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.roadworks_filter) {
      this.showFeedFilterDialog();
    }
    if (item.getItemId() == R.id.roadworks_date) {
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
    roadworksListDateDialogFragment.setTargetFragment(RoadworksListFragment.this, DATE_DIALOG_FRAGMENT);
    roadworksListDateDialogFragment.show(getFragmentManager().beginTransaction(), "date-dialog");
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    switch (requestCode) {
      case DATE_DIALOG_FRAGMENT:
        if (resultCode == Activity.RESULT_OK) {
          Bundle bundle = data.getExtras();
          Calendar selectedDate = (Calendar) bundle.get("selectedDate");
          this.filterListAdapterElementsByPublicationDate(selectedDate);
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

  private void filterListAdapterElementsByPublicationDate(Calendar publicationDate) {
    for (int i = 0; i < roadworksListAdapter.getCount(); i++) {
      final Calendar roadworkCalendar = Calendar.getInstance();
      roadworkCalendar.setTime(roadworksListAdapter.getItem(i).getPublicationDate());
      if (publicationDate.before(roadworkCalendar)) {
        roadworksListAdapter.remove(roadworksListAdapter.getItem(i));
      }
    }
    roadworksListAdapter.notifyDataSetChanged();
    roadworksCount.setText("Roadworks count: " + this.roadworksListAdapter.getCount());
  }

  private void updateListAdapterWithFeedFilter(String feedFilterOption) {
    this.roadworksListAdapter.clear();
    switch (feedFilterOption) {
      case "Current Roadworks":
        this.roadworksListAdapter.addAll(this.roadworks);
        break;
      case "Planned Roadworks":
        this.roadworksListAdapter.addAll(this.plannedRoadworks);
        break;
      case "Both":
        this.roadworksListAdapter.addAll(this.roadworks);
        this.roadworksListAdapter.addAll(this.plannedRoadworks);
        break;
    }
    this.roadworksListAdapter.notifyDataSetChanged();
    roadworksCount.setText("Roadworks count: " + this.roadworksListAdapter.getCount());
  }
}
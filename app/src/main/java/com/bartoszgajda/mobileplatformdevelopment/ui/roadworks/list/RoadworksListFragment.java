package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;

import java.util.List;

public class RoadworksListFragment extends Fragment {

  private RoadworksListViewModel roadworksListViewModel;
  private ListView roadworkListView;
  private RoadworksListAdapter roadworksListAdapter;
  private EditText searchInput;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    roadworksListViewModel = ViewModelProviders.of(this).get(RoadworksListViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_roadworks_list, container, false);

    roadworkListView = root.findViewById(R.id.roadworks_list);
    searchInput = root.findViewById(R.id.roadworks_list_search);

    roadworksListViewModel.getRoadworks().observe(this, new Observer<List<Roadwork>>() {
      @Override
      public void onChanged(List<Roadwork> incidents) {
        roadworksListAdapter = new RoadworksListAdapter(root.getContext(), incidents);
        roadworkListView.setAdapter(roadworksListAdapter);
        roadworksListAdapter.notifyDataSetChanged();
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
    return super.onOptionsItemSelected(item);
  }
}
package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.list;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;

import java.util.List;

public class IncidentsListFragment extends Fragment {

  private IncidentsListViewModel incidentsListViewModel;
  private ListView incidentsListView;
  private IncidentsListAdapter incidentsListAdapter;
  private EditText searchInput;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    incidentsListViewModel = ViewModelProviders.of(this).get(IncidentsListViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_incidents_list, container, false);

    incidentsListView = root.findViewById(R.id.incidents_list);
    searchInput = root.findViewById(R.id.incidents_list_search);

    incidentsListViewModel.getIncidents().observe(this, new Observer<List<Incident>>() {
      @Override
      public void onChanged(List<Incident> incidents) {
        incidentsListAdapter = new IncidentsListAdapter(root.getContext(), incidents);
        incidentsListView.setAdapter(incidentsListAdapter);
        incidentsListAdapter.notifyDataSetChanged();
      }
    });

    searchInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        IncidentsListFragment.this.incidentsListAdapter.getFilter().filter(charSequence);
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
    return root;
  }
}
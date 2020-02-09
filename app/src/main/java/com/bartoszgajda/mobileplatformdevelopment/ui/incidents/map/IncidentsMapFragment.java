package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;

public class IncidentsMapFragment extends Fragment {

  private IncidentsMapViewModel incidentsMapViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    incidentsMapViewModel =
        ViewModelProviders.of(this).get(IncidentsMapViewModel.class);
    View root = inflater.inflate(R.layout.fragment_incidents_map, container, false);
    final TextView textView = root.findViewById(R.id.text_incidents_map);
    incidentsMapViewModel.getText().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });
    return root;
  }
}
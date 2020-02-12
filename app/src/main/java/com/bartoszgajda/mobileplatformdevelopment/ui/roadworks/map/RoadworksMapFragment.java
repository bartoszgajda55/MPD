package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.map;

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

public class RoadworksMapFragment extends Fragment {

    private RoadworksMapViewModel roadworksMapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        roadworksMapViewModel =
                ViewModelProviders.of(this).get(RoadworksMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_roadworks_map, container, false);
        final TextView textView = root.findViewById(R.id.roadworks_map);
        roadworksMapViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
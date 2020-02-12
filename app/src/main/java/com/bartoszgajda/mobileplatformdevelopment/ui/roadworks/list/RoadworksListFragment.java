package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

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

public class RoadworksListFragment extends Fragment {

    private RoadworksListViewModel roadworksListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        roadworksListViewModel =
                ViewModelProviders.of(this).get(RoadworksListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_roadworks_list, container, false);
        final TextView textView = root.findViewById(R.id.roadworks_list);
        roadworksListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
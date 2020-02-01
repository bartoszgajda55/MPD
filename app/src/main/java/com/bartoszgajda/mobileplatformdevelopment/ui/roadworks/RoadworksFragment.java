package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;

public class RoadworksFragment extends Fragment {

    private RoadworksViewModel roadworksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        roadworksViewModel =
                ViewModelProviders.of(this).get(RoadworksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_roadworks, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        roadworksViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
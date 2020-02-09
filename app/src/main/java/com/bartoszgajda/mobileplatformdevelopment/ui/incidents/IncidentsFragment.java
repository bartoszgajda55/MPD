package com.bartoszgajda.mobileplatformdevelopment.ui.incidents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IncidentsFragment extends Fragment {

  private IncidentsViewModel incidentsViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    incidentsViewModel = ViewModelProviders.of(this).get(IncidentsViewModel.class);
    View root = inflater.inflate(R.layout.fragment_incidents, container, false);

    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    BottomNavigationView navView = getView().findViewById(R.id.tab_view);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_list, R.id.navigation_map)
        .build();
    NavController navController = Navigation.findNavController(getActivity(), R.id.tab_host_fragment);
    NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);
  }
}
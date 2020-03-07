package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class RoadworksListMapDialogFragment extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View promptView = layoutInflater.inflate(R.layout.fragment_roadworks_map, null);

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder
        .setTitle("Roadworks on Map")
        .setView(promptView)
        .setNegativeButton("Close", (dialogInterface, i) -> {
          dialogInterface.dismiss();
        });

    AlertDialog alertDialog = builder.create();
    MapView mMapView = (MapView) promptView.findViewById(R.id.roadwork_map);
    MapsInitializer.initialize(getContext());
    mMapView.onCreate(alertDialog.onSaveInstanceState());
    mMapView.onResume();

    mMapView.getMapAsync(googleMap -> {
      LatLng glasgow = new LatLng(55.86515, -4.25763);
      googleMap.addMarker(new MarkerOptions().position(glasgow));
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
      googleMap.getUiSettings().setZoomControlsEnabled(true);
    });

    return alertDialog;
  }

}

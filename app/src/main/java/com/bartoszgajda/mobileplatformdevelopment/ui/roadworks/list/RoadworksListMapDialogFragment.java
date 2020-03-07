package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.map.IconConverter;
import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class RoadworksListMapDialogFragment extends DialogFragment {
  private List<RoadworkModel> roadworks;
  private IconConverter iconConverter = IconConverter.getInstance();

  public RoadworksListMapDialogFragment(List<RoadworkModel> roadworks) {
    this.roadworks = roadworks;
  }

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
      for (RoadworkModel roadwork: this.roadworks) {
        LatLng marker = new LatLng(Double.parseDouble(roadwork.getCoordinates()[0]), Double.parseDouble(roadwork.getCoordinates()[1]));
        Bitmap icon;
        if (roadwork.getType().equals("current")) {
          icon = iconConverter.getMarkerBitmapFromDrawable((getResources().getDrawable(R.drawable.square_foot_24px_red)));
        } else {
          icon = iconConverter.getMarkerBitmapFromDrawable((getResources().getDrawable(R.drawable.square_foot_24px_yellow)));
        }
        Bitmap largerIcon = Bitmap.createScaledBitmap(icon, 120, 120, false);
        googleMap.addMarker(new MarkerOptions().position(marker).icon(BitmapDescriptorFactory.fromBitmap(largerIcon))).setTitle(roadwork.getTitle());
      }

      LatLng glasgow = new LatLng(55.86515, -4.25763);
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
      googleMap.getUiSettings().setZoomControlsEnabled(true);
    });

    return alertDialog;
  }

}

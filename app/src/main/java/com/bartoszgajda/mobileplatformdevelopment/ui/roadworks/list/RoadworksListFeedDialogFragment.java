package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bartoszgajda.mobileplatformdevelopment.R;

public class RoadworksListFeedDialogFragment extends DialogFragment {
  private String option;

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final CharSequence[] charSequence = new CharSequence[]{"Current Roadworks", "Planned Roadworks", "Both"};
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle(R.string.roadworks_menu_filter_title)
        .setSingleChoiceItems(charSequence, 2, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            RoadworksListFeedDialogFragment.this.option = charSequence[which].toString();
            Log.d("dialog", RoadworksListFeedDialogFragment.this.option);
          }
        })
        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
          }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        });

    return builder.create();
  }
}

package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;

import java.text.SimpleDateFormat;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class PlannerMapDialogFragment extends DialogFragment {
  private RoadworkModel roadwork;

  public PlannerMapDialogFragment(RoadworkModel roadwork) {
    this.roadwork = roadwork;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
        .setTitle(roadwork.getTitle())
        .setMessage(Html.fromHtml(roadwork.getDescription()) + "\n\n" + "Published: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(roadwork.getPublicationDate()))
        .setNegativeButton("Close", (dialogInterface, i) -> {
        });
    return builder.create();
  }
}

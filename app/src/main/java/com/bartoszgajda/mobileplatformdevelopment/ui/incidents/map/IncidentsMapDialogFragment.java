package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;

import java.text.SimpleDateFormat;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class IncidentsMapDialogFragment extends DialogFragment {
  private Incident incident;

  public IncidentsMapDialogFragment(Incident incident) {
    this.incident = incident;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
        .setTitle(incident.getTitle())
        .setMessage(incident.getDescription() + "\n\n" + "Published: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(incident.getPublicationDate()))
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
          }
        });
    return builder.create();
  }
}

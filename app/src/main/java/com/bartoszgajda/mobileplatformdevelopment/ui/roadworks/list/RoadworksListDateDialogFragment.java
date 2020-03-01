package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class RoadworksListDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    Calendar c = Calendar.getInstance();
    c.set(year, month, day);
    Intent i = new Intent();
    i.putExtra("selectedDate", c);
    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
  }
}

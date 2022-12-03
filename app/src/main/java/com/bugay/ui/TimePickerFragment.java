package com.bugay.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment {

    SimpleDateFormat dt, day, time, date;
    Date formater;
    String currentDateTimeString, dtString, dayString, timeString, dateString;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /*//SimpleDateFormat
        dt = new SimpleDateFormat("MM/dd/yyyy KK:mm a");
        day = new SimpleDateFormat("a");
        time = new SimpleDateFormat("KK:mm");
        date = new SimpleDateFormat("MM/dd/yyyy");

        //Convert SimpleDateFormat to String
        formater = Calendar.getInstance().getTime();//get the current date and time of the phone
        dtString = dt.format(formater);
        dayString = day.format(formater);
        timeString = time.format(formater);
        dateString = date.format(formater);*/

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)
                getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}


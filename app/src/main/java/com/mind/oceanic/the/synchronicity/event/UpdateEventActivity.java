package com.mind.oceanic.the.synchronicity.event;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dave on 1/21/16.
 */
public class UpdateEventActivity extends Activity implements View.OnClickListener {

    SynchronicityDataSource datasource;
    long synchId = -1;
    long eventId = -1;
    String eventSummary = "";
    String eventDetails = "";
    String eventDate = "";

    private EditText etDate;
    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    boolean newItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText etSummary;
        final EditText etDetails;


        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_summary_detail);
        Log.i("dolphin", "toggle");
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
        }

        datasource = new SynchronicityDataSource(this);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Bundle b = getIntent().getExtras();
        synchId = b.getLong("SynchId");
        eventId = b.getLong("EventId");
        if (eventId == -1) {
            Log.i("dolphin","eeeeeeeeeeeeeeeeventid="+eventId);
            newItem = true;
        }
        setDateTimeField();
        eventSummary = b.getString("EventSummary");
        eventDetails = b.getString("EventDetails");
        eventDate = b.getString("EventDate");
        Log.i("dolphin","SynchId is "+synchId);
        Log.i("dolphin","Eventid is "+eventId);
        etSummary = (EditText) findViewById(R.id.txt_event_summary);
        etSummary.setText(eventSummary);

        etDetails = (EditText) findViewById(R.id.txt_event_details);
        etDetails.setText(eventDetails);

//        etDate = (EditText) findViewById(R.id.txt_event_date);
//        etDate.setText(eventDate);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Event event = new Event();
                                           if (!newItem) {
                                               event.setEventId(eventId);
                                               event.setEventSummary(eventSummary);
                                               event.setEventDetails(eventDetails);
                                               event.setEventDate(eventDate);
                                           }
                                           event.setEventSummary(etSummary.getText().toString());
                                           event.setEventDetails(etDetails.getText().toString());
                                           event.setEventDate(etDate.getText().toString());
                                           Log.i("dolphiny", "setdetail=" + eventDetails);

                                           if (newItem) {
                                               Log.i("dolphinv","saving new");
                                               SaveNew(event);
                                           } else {
                                               Log.i("dolphinv","saving existing");
                                               SaveExisting(event);
                                           }
                                           finish();
                                       }
                                   }
        );

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.i("dolphin","btnCancel");
                                             finish();

                                         }
                                     }
        );


        Log.i("dolphin", "togglest");
    }
    protected boolean SaveNew(Event event) {
        Log.i("dolphinv", "Item added");
        eventId = datasource.add(event);
        if (eventId != -1) {
            SynchItemEvent synchItemEvent = new SynchItemEvent();
            synchItemEvent.setSeSynchId(synchId);
            synchItemEvent.setSeEventId(eventId);
            Log.i("dolphinv", "Item crated synchId="+synchId+"  eventid="+eventId);
            datasource.create(synchItemEvent);
            return true;
        } else {
            Log.i("dolphinv", "ID not added");
            return false;
        }
    }

    protected boolean SaveExisting(Event event) {
        Log.i("dolphinu","saveexisting");
        if (datasource.update(event)) {
            return true;
        } else {
            return false;
        }
    }
    private void setDateTimeField() {
        etDate = (EditText) findViewById(R.id.txt_event_date);
//        eventDate = (EditText) findViewById(R.id.txt_event_date);
//        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        Log.i("dolphin", "setdatetimefiled");
        etDate.setOnClickListener(UpdateEventActivity.this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        entryDate = df.format(c.getTime());

        etDate.setText(entryDate);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    public void onClick(View view) {
        if (view == etDate) {

            datePickerDialog.show();

        }
    }
}

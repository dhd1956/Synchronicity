package com.mind.oceanic.the.synchronicity.event;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dave on 1/31/16.
 */



    public class EventListActivity extends Activity implements View.OnClickListener {
    Event event;
    SynchronicityDataSource datasource;

    protected long eventId;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate;
    private Button btnDefineEvent;
    private Button btnCancel;
    private Button btnSave;
    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_summary);
        Bundle b = getIntent().getExtras();
        event = b.getParcelable(".model.SynchItem");
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        Log.i("dolphin","top of eventlistactivity");
        eventId = b.getLong("Id");
        eventSummary = b.getString("EventSummary");
        eventDetails = b.getString("EventDetails");
        eventDate = b.getString("EventDate");
        datasource = new SynchronicityDataSource(this);
        btnDefineEvent = (Button) findViewById(R.id.btnDefineEvent);
        btnDefineEvent.setOnClickListener(this);
//        ET_DATE = (EditText) findViewById(R.id.txt_synch_date);
//        setDateTimeField();
//        setFilterList();

    }

//    private void setDateTimeField() {
//        ET_DATE = (EditText) findViewById(R.id.txt_event_date);
//        ET_DATE.setInputType(InputType.TYPE_NULL);
//        ET_DATE.requestFocus();
//        ET_DATE.setOnClickListener(this);
//        Log.i("dolphin", "setdatetimefiled");
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//
//        entryDate = df.format(c.getTime());
//
//        ET_DATE.setText(entryDate);
//        Calendar newCalendar = Calendar.getInstance();
//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//
//                ET_DATE.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//    }
    @Override
    public void onClick(View view) {
        if(view == btnDefineEvent) {

            Log.i("dolphinp", "after new show=");
            Intent intent2 = new Intent(EventListActivity.this,UpdateEventActivity.class);
            intent2.putExtra("Id",eventId);
            intent2.putExtra("EventSummary",eventSummary);
            intent2.putExtra("EventDetails",eventDetails);
            intent2.putExtra("EventDate",eventDate);

            startActivityForResult(intent2,2);



//        } else if(view == b) {
//            setShoppingList();
//        } else if(view == bCalc) {
//            calculateStoreToShop();
//                } else if(view == lvl1) {
//                    Log.i("dolphinp","show"+fromDateEtxt.getText().toString());
//                    fromDateEtxt.setText(lvl1.getItemAtPosition(lvl1.getSelectedItemPosition()).toString());
        }
    }

    protected void setFilterList() {


        ListView l = (ListView) findViewById(R.id.lst_event_filters);
        String[] values = new String[]{"Show Selected", "Show All", "Show Unselected"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventListActivity.this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);
    }
}
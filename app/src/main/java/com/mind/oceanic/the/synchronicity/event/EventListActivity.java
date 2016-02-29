package com.mind.oceanic.the.synchronicity.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mind.oceanic.the.synchronicity.MainActivity;
import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.SynchItem;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;
import com.mind.oceanic.the.synchronicity.synch.MaintainSynchronicityActivity;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by dave on 1/31/16.
 */



    public class EventListActivity extends Activity  implements View.OnClickListener  {
    Event event;
    SynchronicityDataSource datasource;
//    protected ListView lst1;
    String eventSource="";
    List<Event> events;
    protected long synchId=-1;
    protected long eventId;
    protected long verbId;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate;
    private Button btnReturn;
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
        Log.i("dolphin","top of eventlistactivity");

        Bundle b = getIntent().getExtras();
//        event = b.getParcelable(".model.SynchItem");
//        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        eventSource = b.getString("EventSource");
        synchId = b.getLong("SynchId");
//        eventSummary = b.getString("EventSummary");
//        eventDetails = b.getString("EventDetails");
//        eventDate = b.getString("EventDate");
        datasource = new SynchronicityDataSource(this);
        datasource.open();
        btnDefineEvent = (Button) findViewById(R.id.btn_define_event);
        btnDefineEvent.setOnClickListener(this);
        btnReturn = (Button) findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(this);

        setList();
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
            Intent intent2 = new Intent(EventListActivity.this, UpdateEventActivity.class);
            eventId = -1;
            eventSummary = null;
            eventDetails = null;
            eventDate = null;
            intent2.putExtra("EventId", eventId);
            intent2.putExtra("EventSummary", eventSummary);
            intent2.putExtra("EventDetails", eventDetails);
            intent2.putExtra("EventDate", eventDate);

            startActivityForResult(intent2, 2);
        } else if (view == btnReturn) {
            finish();
        }

//        } else if(view == b) {
//            setShoppingList();
//        } else if(view == bCalc) {
//            calculateStoreToShop();
//                } else if(view == lvl1) {
//                    Log.i("dolphinp","show"+fromDateEtxt.getText().toString());
//                    fromDateEtxt.setText(lvl1.getItemAtPosition(lvl1.getSelectedItemPosition()).toString());

    }

    protected void setList() {

        events = datasource.findAllEvents();

        final ListView lst1 = (ListView) findViewById(R.id.lst_events);
//        for (int i=1;i<events.size();i++) {
//            Log.i("dolhin","look at i="+i+"  id="+events.get(i).getEventId()+"  event="+events.get(i).getEventSummary());
//            if (events.get(i).getEventId() == 11) {
////                datasource.deleteEvent(11);
//            }
//        }

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(EventListActivity.this,
                android.R.layout.simple_list_item_1, events);
        lst1.setAdapter(adapter);

        Log.i("dolphin", "bad move");
        lst1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Log.i("dolphin", "there is a house in new " + eventId);
                        event = events.get(position);
                        eventId = event.getEventId();
                        eventDate = event.getEventDate();
                        Log.i("dolphin", "before summary");
                        if (event.getEventSummary() != null) {
                            eventSummary = event.getEventSummary().toString();
                        }
                        Log.i("dolphin", "after summary eventID=" + eventId + "evemtspirce=" + eventSource);
                        if (eventSource.equals("EventList")) {

                            Intent intent2 = new Intent(EventListActivity.this, UpdateEventActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());
                            intent2.putExtra("EventId", eventId);
                            intent2.putExtra("EventDate", eventDate);
                            intent2.putExtra("EventSummary", event.getEventSummary());
                            intent2.putExtra("EventDetails", event.getEventDetails());
                            long eventId = -1;
                            intent2.putExtra("Flag", "Reset");
                            intent2.putExtra("EventSoure", "EventList");
                            startActivityForResult(intent2, 2);
                        } else {
                            // link
                            Log.i("dolphin", "sink id=" + synchId + "  left=");

                            SynchItemEvent seEvent = new SynchItemEvent();
                            seEvent.setSeSynchId(synchId);
                            Log.i("dolphin", "in click of list=" + synchId + " eventid=" + eventId);
                            seEvent.setSeEventId(eventId);
                            try {
                                datasource.create(seEvent);
                                finish();
                            } catch (Exception e) {
                                Context context = getApplicationContext();
                                CharSequence text = "Already selected";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    }
                }
        );
        lst1.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = new Event();
                event = events.get(position);
                eventId = event.getEventId();
//                Log.i("dolphin", "deleting=" + synchId + synchItem.getVerbName());
                if (okCancelEventAlert()) {
                }
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setList();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin", "onactivityresulttop" + requestCode);
        if(requestCode==1)
        {
            verbId=data.getLongExtra("VerbId",2);
            Log.i("dolphin","verbid from above"+verbId);
        }
    }
    protected boolean okCancelEventAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventListActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                datasource.deleteEvent(eventId);
                datasource.deleteSynchEventEventOrphans(eventId);
                setList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
            }
        });
        myAlertDialog.show();
        return true;
    }
}
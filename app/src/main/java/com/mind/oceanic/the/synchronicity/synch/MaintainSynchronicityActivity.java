package com.mind.oceanic.the.synchronicity.synch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.event.EventListActivity;
import com.mind.oceanic.the.synchronicity.event.UpdateEventActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.SynchItem;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dave on 1/17/16.
 */
public class MaintainSynchronicityActivity extends Activity implements View.OnClickListener {

    SynchItem synchItem;
    SynchItem si;
    SynchronicityDataSource datasource;
    protected long synchId;
    protected String synchDate="";
    protected int synchRating;
    protected String synchSummary;
    protected String synchDetails;

    Event event;
    List<Event> events;
    protected long eventId = -1;
    protected long leftEventId = -1;
    protected long rightEventId = -1;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate=null;
    private EditText etDate;
    private TextView tvSummary;
    private TextView tvDetails;
    private ListView lstEvents;
    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_synch);
        Bundle b = getIntent().getExtras();
//        synchItem = b.getParcelable(".model.SynchItem");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        synchId = b.getLong("Id");
        synchDate = b.getString("Date");
        Log.i("dolphin","   the datesynch is "+synchDate);
        synchSummary = b.getString("Summary");
        synchDetails = b.getString("Details");
        String flag = b.getString("Flag");
        datasource = new SynchronicityDataSource(this);
        datasource.open();
        Log.i("dolphin", "eventId from start of mainain=" + flag);

        if (flag.equals("set")) {
            //check if synch events are already linked
            SynchItem synchItem = new SynchItem();
            synchItem = datasource.create(synchItem);
            synchId = synchItem.getSynchId();
            Log.i("dolphin", "maintina start=" + synchSummary + "  AND " + synchDetails + "  and " + synchId);
            leftEventId = b.getLong("leftEventId");
            event = datasource.findEvent(leftEventId);
            linkEvent(synchId,leftEventId);
            showEvent(synchId,leftEventId);
            rightEventId = b.getLong("rightEventId");
            event = datasource.findEvent(rightEventId);
            linkEvent(synchId,rightEventId);
            showEvent(synchId,rightEventId);
        }
        tvSummary = (TextView) findViewById(R.id.lbl_synch_summary);
        etDate = (EditText) findViewById(R.id.txt_synch_date);
        tvDetails = (TextView) findViewById(R.id.lbl_synch_details);
        lstEvents = (ListView) findViewById(R.id.lst_events);
        if (synchId != -1) {
            setEventList();
            Log.i("dolphin", "detail is coming into maintain=");//+ synchDetails);
            setSynchInfo();
        }
        setDateTimeField();
        tvSummary.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             getUpdate();
                                         }
                                     }
        );
        tvDetails.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             getUpdate();

                                         }
                                     }
        );
        Button btnLinkEvent = (Button) findViewById(R.id.btn_link_event);
        btnLinkEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                synchId = -1;
                eventId = -1;
                linkEvents();
            }
        });
        Button btnNewEvent = (Button) findViewById(R.id.btn_new_event);
        btnNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                synchId = -1;
                eventId = -1;
                getEvents();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchDate = etDate.getText().toString();
                Log.i("dolphin","  saving with this etdate "+synchDate);
                SynchItem synchItem = new SynchItem();
                synchItem.setSynchId(synchId);
                synchItem.setSynchDate(etDate.getText().toString());
                synchItem.setSynchSummary(tvSummary.getText().toString());
                synchItem.setSynchDetails(tvDetails.getText().toString());
                synchItem.setSynchRating(synchRating);
                if (synchId == -1) {
                    Log.i("dolphinv","saving new");
                    SaveNew(synchItem);
                } else {
                    Log.i("dolphinv","saving existing");
                    SaveExisting(synchItem);
                }
                finish();
            }
        });
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getUpdate() {
        Intent intent = new Intent(MaintainSynchronicityActivity.this,UpdateSynchDetailActivity.class);
        if (synchId == -1) {

            synchDate = "";
            synchSummary = "";
            synchDetails = "";

        }
            intent.putExtra("Id", synchId);
            Log.i("dolphin", "detailitis has " + synchId);
            intent.putExtra("Summary", synchSummary);
            intent.putExtra("Details", synchDetails);
            startActivityForResult(intent, 1);

    }
    private void getEvents() {
        if (eventId == -1) {
            eventDate = "";
            eventSummary = "";
            eventDetails = "";

        }
        Intent intent2 = new Intent(MaintainSynchronicityActivity.this,UpdateEventActivity.class);
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails",eventDetails);
        intent2.putExtra("EventDate", eventDate);
        startActivityForResult(intent2,2);

    }
    private void linkEvents() {
        if (eventId == -1) {
            eventDate = "";
            eventSummary = "";
            eventDetails = "";

        }
        Intent intent2 = new Intent(MaintainSynchronicityActivity.this,EventListActivity.class);
        intent2.putExtra("EventSource","Synch");
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails",eventDetails);
        intent2.putExtra("EventDate", eventDate);
        startActivityForResult(intent2,5);

    }
    private void setDateTimeField() {
        Log.i("dolphin", "setdatetimefiled");

        etDate = (EditText) findViewById(R.id.txt_synch_date);
        Log.i("dolphinoo","synchDate is "+synchDate);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        etDate.setOnClickListener(this);
        etDate.setText(synchDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        entryDate = df.format(c.getTime());
        Log.i("dolphin","entryDate is before in date setting as "+entryDate);
        Log.i("dolphin","s dt is before in date setting as "+synchDate);
        if (synchDate != null && synchDate.length()>3) {
            Log.i("dolphin","synchDate is in date setting as "+synchDate);
            etDate.setText(synchDate);
        } else {
            Log.i("dolphin","entryDate is in date setting as "+entryDate);
            etDate.setText(entryDate);
        }



        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                etDate.setText(dateFormatter.format(newDate.getTime()));
//                synchDate = etDate.getText().toString();dd
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    public void setSynchInfo() {
        Log.i("dolphin", "setting text id=" + synchId);

//        Log.i("dolphin", "setting next details=" + synchDetails);
//        tvSummary = (TextView) findViewById(R.id.lbl_synch_summary);
//        etDate = (EditText) findViewById(R.id.txt_synch_date);
//        tvDetails = (TextView) findViewById(R.id.lbl_synch_details);
        synchItem = datasource.find(synchId);
        etDate.setText(synchItem.getSynchDate());
        synchDate = etDate.getText().toString();
        Log.i("dolphin","in setsynchinfo synchDate = "+synchDate);

        tvSummary.setText(synchItem.getSynchSummary());
        tvDetails.setText(synchItem.getSynchDetails());
    }

    @Override
    public void onClick(View view) {
        if(view == etDate) {

            datePickerDialog.show();


        } else if (view == lstEvents) {
            Log.i("dolphin","top of lstevents click");
            Intent intent3 = new Intent(MaintainSynchronicityActivity.this,UpdateEventActivity.class);
            intent3.putExtra("Id",eventId);
            intent3.putExtra("EventSummary", eventSummary);
            intent3.putExtra("EventDetails",eventDetails);
            intent3.putExtra("EventDate",eventDate);
            startActivityForResult(intent3,3);
        }
        Log.i("dolphinp", "after new show=");
    }

    public void setEventList() {

        events = datasource.findAllLinkedEvents(synchId);
        final ListView lst1 = (ListView) findViewById(R.id.lst_events);
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(MaintainSynchronicityActivity.this,
                android.R.layout.simple_list_item_1, events);
        lst1.setAdapter(adapter);
        Log.i("dolphino", "seteventlist "+synchId);
        lst1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Log.i("dolphin", "after summary");

                        event = events.get(position);
                        eventId = event.getEventId();
                        Log.i("dolphin", "before summaryyyyyyy=" + eventId + "   " + position);
                        if (event.getEventSummary() != null) {
                            eventSummary = event.getEventSummary();
                        }
                        showEvent(synchId, eventId);
                    }
                }
        );
        Log.i("dolphin", "seteventlist bottom");
    }

    protected void linkEvent(long synchId, long eventId) {
        Log.i("dolphin", "showevent");
        SynchItemEvent synchItemEvent = new SynchItemEvent();
        synchItemEvent.setSeSynchId(synchId);
        synchItemEvent.setSeEventId(eventId);
        Log.i("dolphinv", "Item crated synchId=" + synchId + "  eventid=" + eventId);
        datasource.create(synchItemEvent);
        eventDetails = event.getEventDetails();
    }

    protected void showEvent(long synchId, long eventId) {
        Intent intent2 = new Intent(MaintainSynchronicityActivity.this, UpdateEventActivity.class);
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", event.getEventId());
        intent2.putExtra("EventSummary", event.getEventSummary());
        intent2.putExtra("EventDetails", event.getEventDetails());
        intent2.putExtra("EventDate",event.getEventDate());
        Log.i("dolphin", "in click of list=" + event.getEventDetails());
        startActivityForResult(intent2, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (synchId != -1) {
            Log.i("dolphin", "onresume in maintainsynch  "+synchId);
            setEventList();
            setSynchInfo();
        }
        setDateTimeField();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin", "onactivityresulttop" + requestCode);
        if(requestCode==1)
        {
            synchId=data.getLongExtra("SynchId",2);
            Log.i("dolphin","synchid from above"+synchId);
        } else if (requestCode == 5) {

        }
    }

    protected boolean SaveNew(SynchItem synchItem) {
        Log.i("dolphinv", "Item added");
        if (datasource.add(synchItem)) {
            synchId = synchItem.getSynchId();
            return true;
        } else {
            Log.i("dolphinv", "ID not added");
            return false;
        }

    }

    protected boolean SaveExisting(SynchItem synchItem) {
        Log.i("dolphinu","saveexisting");
        if (datasource.update(synchItem)) {
            return true;
        } else {
            return false;
        }

    }
}

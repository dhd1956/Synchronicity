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
    SynchronicityDataSource datasource;
    protected long synchId;
    protected String synchDate;
    protected String synchSummary;
    protected String synchDetails;
    Event event;
    List<Event> events;
    protected long eventId = -1;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate;
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
        synchItem = b.getParcelable(".model.SynchItem");
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        synchId = b.getLong("Id");
        synchSummary = b.getString("Summary");
        synchDetails = b.getString("Details");
        datasource = new SynchronicityDataSource(this);
        datasource.open();

        Log.i("dolphin", "maintina start=" + synchSummary + "  AND " + synchDetails+"  and "+synchId);

        Log.i("dolphin", "detail is coming into maintain="+synchDetails);
        setEventList();
//        ET_DATE = (EditText) findViewById(R.id.txt_synch_date);
        setDateTimeField();
//        setFilterList();
        setSynchInfo();
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
        intent2.putExtra("SynchId",synchId);
        intent2.putExtra("EventId",eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails",eventDetails);
        intent2.putExtra("EventDate",eventDate);
        startActivityForResult(intent2,2);

    }
    private void setDateTimeField() {
        lstEvents = (ListView) findViewById(R.id.lst_events);
        etDate = (EditText) findViewById(R.id.txt_synch_date);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        etDate.setOnClickListener(this);

        Log.i("dolphin", "setdatetimefiled");
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
//        } else if(view == bCalc) {
//            calculateStoreToShop();
//                } else if(view == lvl1) {
//                    Log.i("dolphinp","show"+fromDateEtxt.getText().toString());
//                    fromDateEtxt.setText(lvl1.getItemAtPosition(lvl1.getSelectedItemPosition()).toString());
        }
        Log.i("dolphinp","after new show=");
    }

    public void setSynchInfo() {
        Log.i("dolphin", "setting text id=" + synchId);

        synchItem = datasource.find(synchId);
        Log.i("dolphin", "setting next details=" + synchDetails);
        tvSummary = (TextView) findViewById(R.id.lbl_synch_summary);
        tvDetails = (TextView) findViewById(R.id.lbl_synch_details);
        tvSummary.setText(synchItem.getSynchSummary());
        tvDetails.setText(synchItem.getSynchDetails());
    }

    public void setEventList() {

        events = datasource.findAllLinkedEvents(synchId);
        final ListView lst1 = (ListView) findViewById(R.id.lst_events);
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(MaintainSynchronicityActivity.this,
                android.R.layout.simple_list_item_1, events);
        lst1.setAdapter(adapter);
        Log.i("dolphino", "seteventlist ");
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

                        eventDetails = event.getEventDetails();
                        Intent intent2 = new Intent(MaintainSynchronicityActivity.this, UpdateEventActivity.class);
                        intent2.putExtra("SynchId", synchId);
                        intent2.putExtra("EventId", event.getEventId());
                        intent2.putExtra("EventSummary", event.getEventSummary());
                        intent2.putExtra("EventDetails", event.getEventDetails());
                        Log.i("dolphin", "in click of list=" + event.getEventDetails());
                        startActivityForResult(intent2, 2);
                    }
                }
        );
    }


    protected void setFilterList() {


        ListView l = (ListView) findViewById(R.id.lst_event_filters);
        String[] values = new String[] { "Show Selected", "Show All", "Show Unselected"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MaintainSynchronicityActivity.this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);


//        final ListView lst1 = (ListView) findViewById(R.id.lst_synch_summary);
//        ArrayAdapter<SynchItem> adapter = new ArrayAdapter<SynchItem>(this,
//                android.R.layout.simple_list_item_1, synchItems);
//        lst1.setAdapter(adapter);

//
//        lstFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(item);
////                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
//            }

//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("dolphin", "onresume in maintainsynch");

        setSynchInfo();
        setEventList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin","onactivityresulttop"+requestCode);
        if(requestCode==1)
        {
            synchId=data.getLongExtra("SynchId",2);
            Log.i("dolphin","synchid from above"+synchId);
        }
    }

//    @Override
//    protected void onStop() {
//
//        Log.i("dolphin","save and stop");
//        if (datasource.addSynchItem(synchItem)) {
//        }
//
//        super.onStop();
//        finish();
//    }
}

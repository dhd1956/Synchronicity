package com.mind.oceanic.the.synchronicity.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.event.EventNotesActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Verb;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dave on 2/15/16.
 */


public class CalendarActivity extends Activity  {

    SynchronicityDataSource datasource;

    List<Verb> verbs;
    long eventId=-1;
    long noteId=-1;

    String placeName;
    String person;
    String noteInfo;
//    Button btnCancel;
//    Button btnAddToCalendar;
    EditText txtPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_calendar);

        datasource = new SynchronicityDataSource(this);
        datasource.open();

        Log.i("dolphin", "top of verbActivity");

        Bundle b = getIntent().getExtras();
        eventId = b.getLong("EventId");
        noteId = b.getLong("NoteId");
        person = b.getString("NotePerson");
        noteInfo = b.getString("NoteInfo");

        txtPlaceName = (EditText) findViewById(R.id.txt_place_name);
//        btnCancel = (Button) findViewById(R.id.btn_cancel);
//        btnAddToCalendar = (Button) findViewById(R.id.btn_add_to_calendar);

        txtPlaceName.setText(placeName);
Log.i("dolphin","noteinfo is ="+noteInfo);

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             finish();
//                                         }
//                                     }
//        );
//
//        btnAddToCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addToCalendar();
//            }
//        });
    }

    protected void addToCalendar(){
        Event event = new Event();
        event = datasource.findEvent(eventId);
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, event.getEventSummary());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, txtPlaceName.getText().toString());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, noteInfo);

        GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        startActivity(calIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

            case R.id.menu_cancel:
                finish();
                break;

            case R.id.menu_add_to_calendar:
                addToCalendar();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

}

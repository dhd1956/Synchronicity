package com.mind.oceanic.the.synchronicity.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Note;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;
import com.mind.oceanic.the.synchronicity.model.Thing;
import com.mind.oceanic.the.synchronicity.note.ThingActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by dave on 1/21/16.
 */
public class UpdateEventActivity extends Activity implements View.OnClickListener {

    SynchronicityDataSource datasource;
    List<Note> notes;
    long noteId = -1;
    String notePerson="";
    String noteInfo = "";
    long synchId = -1;
    long eventId = -1;
    String eventSummary = "";
    String eventDetails = "";
    String eventDate = "";

    boolean ok = false;

    private EditText etDate;

    private EditText etSummary;
    private EditText etDetails;
    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    boolean newItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_summary_detail);
        Log.i("dolphin", "toggle");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
        }

        datasource = new SynchronicityDataSource(this);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Log.i("dolphin", "eeeeeeeeeeeeeeeeventid=" + eventId);
        Bundle b = getIntent().getExtras();
        synchId = b.getLong("SynchId");
        eventId = b.getLong("EventId");
        if (eventId == -1) {
            newItem = true;
        }
        setDateTimeField();
        eventSummary = b.getString("EventSummary");
        eventDetails = b.getString("EventDetails");
        eventDate = b.getString("EventDate");
        Log.i("dolphin", "SynchId is " + synchId);
        Log.i("dolphin", "Eventid is " + eventId);
        Log.i("dolphin", "date is " + eventDate);

        etSummary = (EditText) findViewById(R.id.txt_event_summary);
        etDetails = (EditText) findViewById(R.id.txt_event_details);
        etDate = (EditText) findViewById(R.id.txt_event_date);
        if (!newItem) {
            etSummary.setText(eventSummary);

            etDetails.setText(eventDetails);

            etDate.setText(eventDate);
        }
//        Button btnSave = (Button) findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           save();
//                                       }
//                                   }
//        );
//
//        Button btnNotes = (Button) findViewById(R.id.btn_notes);
//        btnNotes.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            notes();
//
//                                        }
//                                    }
//        );
//
//        Button btnCancel = (Button) findViewById(R.id.btnCancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             Log.i("dolphin", "btnCancel");
//                                             finish();
//
//                                         }
//                                     }
//        );
    }

    public void setNotesList() {
        Log.i("dolphin", "in setNotelist eventID=" + eventId);
        notes = datasource.findAllLinkedNotes(eventId);
        final ListView lstNote = (ListView) findViewById(R.id.lst_notes);
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, notes);
        lstNote.setAdapter(adapter);

        lstNote.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        Note note = new Note();
                        note = notes.get(position);
                        noteId = note.getNoteId();

                        Log.i("dolphin", "before summary  noteId=" + noteId);

                        notePerson = note.getNotePerson();
                        if (note.getNoteInfo() != null) {
                            noteInfo = note.getNoteInfo().toString();
                            notePerson = note.getNotePerson();
                        }
                        Log.i("dolphin", "after summary");

                        Intent intent3 = new Intent(UpdateEventActivity.this, EventNotesActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());
                        intent3.putExtra("EventId", eventId);
                        intent3.putExtra("NoteId", noteId);
                        intent3.putExtra("NoteInfo", noteInfo);
                        intent3.putExtra("NotePerson", notePerson);
//                        intent2.putExtra("Summary", note.getNoteName());

//                        long eventId = -1;
                        intent3.putExtra("Flag", "Reset");
                        Log.i("dolphin", "in click of list=" + note.getNoteInfo());
                        startActivityForResult(intent3, 2);

                    }
                });
        lstNote.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = new Note();
                note = notes.get(position);
                noteId = note.getNoteId();
                Log.i("dolphin", "deleting=" + noteId + note.getNoteInfo());
                if (okCancelAlert()) {
                    datasource.deleteNote(noteId);
                    setNotesList();
                }
                return true;
            }
        });
    }


    protected boolean SaveNew(Event event) {
        Log.i("dolphinv", "Item added");
        eventId = datasource.add(event);
        if (eventId != -1) {
            SynchItemEvent synchItemEvent = new SynchItemEvent();
            synchItemEvent.setSeSynchId(synchId);
            synchItemEvent.setSeEventId(eventId);
            Log.i("dolphinv", "Item crated synchId=" + synchId + "  eventid=" + eventId);
            datasource.create(synchItemEvent);
            return true;
        } else {
            Log.i("dolphinv", "ID not added");
            return false;
        }
    }

    protected boolean SaveExisting(Event event) {
        Log.i("dolphinu", "saveexisting");
        if (datasource.update(event)) {
            return true;
        } else {
            return false;
        }
    }

            protected void save(){
                Event event = new Event();
                if (!newItem) {
                    event.setEventId(eventId);
                    event.setEventDate(eventDate);
                    event.setEventSummary(eventSummary);
                    event.setEventDetails(eventDetails);
                    event.setEventDate(eventDate);
                }
                event.setEventDate(etDate.getText().toString());
                event.setEventSummary(etSummary.getText().toString());
                event.setEventDetails(etDetails.getText().toString());
                event.setEventDate(etDate.getText().toString());
                Log.i("dolphiny", "setdetail=" + eventDetails);

                if (newItem) {
                    Log.i("dolphinv", "saving new");
                    SaveNew(event);
                } else {
                    Log.i("dolphinv", "saving existing");
                    SaveExisting(event);
                }
                finish();
            }
    protected void notes(){
        Intent intent2 = new Intent(UpdateEventActivity.this, EventNotesActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());

        intent2.putExtra("Flag", "Reset");
        Log.i("dolphin", "in click of note=");
        intent2.putExtra("EventId", eventId);
        noteId = -1;
        intent2.putExtra("NoteId", noteId);
        intent2.putExtra("NotePerson",notePerson);
        startActivityForResult(intent2, 2);
    }

    private void setDateTimeField() {
        etDate = (EditText) findViewById(R.id.txt_event_date);
//        eventDate = (EditText) findViewById(R.id.txt_event_date);
//        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        Log.i("dolphin", "setdatetimefiled");
        etDate.setOnClickListener(UpdateEventActivity.this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

    protected boolean okCancelAlert() {


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(UpdateEventActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                ok = true;
                datasource.deleteNote(noteId);
                setNotesList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
                ok = false;
            }
        });
        myAlertDialog.show();
        Log.i("dolphin", "ok is " + ok);
        return ok;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

//            case R.id.action_settings:
//                Intent intent = new Intent(this, HttpMainActivity.class);
//                startActivity(intent);
//                break;
            case R.id.menu_cancel:
                finish();
                break;

            case R.id.menu_reminder:
                notes();
                break;

            case R.id.menu_save:
                save();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }
}

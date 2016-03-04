package com.mind.oceanic.the.synchronicity.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Note;
import com.mind.oceanic.the.synchronicity.model.Person;
import com.mind.oceanic.the.synchronicity.model.Thing;
import com.mind.oceanic.the.synchronicity.note.CalendarActivity;
import com.mind.oceanic.the.synchronicity.note.ContactsActivity;
import com.mind.oceanic.the.synchronicity.note.ThingActivity;
import com.mind.oceanic.the.synchronicity.note.VerbActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Verb;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dave on 2/15/16.
 */
public class EventNotesActivity extends Activity {

    protected List<Verb> verbs;
    protected List<Thing> things;
    protected List<Note> notes;

    SynchronicityDataSource datasource;

    long eventId=-1;
    long noteId=-1;
    long verbId=-1;
    long thingId=-1;
    String person="";
    String noteInfo="";
    String verbName;
    String thingName;

    Button btnContacts;
    Button btnCancel;
    Button btnVerb;
    Button btnThing;
    Button btnSave;
    Button btnAddToCalendar;
    ListView lstVerb;
    ListView lstThing;
    EditText txtNote;
    EditText txtPerson;

    boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datasource = new SynchronicityDataSource(this);
        datasource.open();

        setContentView(R.layout.event_notes);
        final Note note = new Note();

        Bundle b = getIntent().getExtras();
        eventId = b.getLong("EventId");
        noteId = b.getLong("NoteId");
        person = b.getString("NotePerson");
        noteInfo = b.getString("NoteInfo");
        txtPerson = (EditText) findViewById(R.id.txt_person);
        txtNote = (EditText) findViewById(R.id.txt_note);
        btnContacts = (Button) findViewById(R.id.btn_contacts);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnVerb = (Button) findViewById(R.id.btn_verb);
        btnThing = (Button) findViewById(R.id.btn_thing);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnAddToCalendar = (Button) findViewById(R.id.btn_add_to_calendar);
        lstVerb = (ListView) findViewById(R.id.lst_verb);
        lstThing = (ListView) findViewById(R.id.lst_thing);
//        btnSave.setOnClickListener(this);
        txtPerson.setText(person);
        txtNote.setText(noteInfo);

        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finish();
                                         }
                                     }
        );

        Log.i("dolphin", "notes");

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(EventNotesActivity.this, ContactsActivity.class);
                verbId = -1;
                intent5.putExtra("VerbId", verbId);
                Log.i("dolphin", "btnContacts click");
                startActivityForResult(intent5, 5);
            }

        });

        btnAddToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setToSave();
                noteInfo = txtNote.getText().toString();
                Intent intent6 = new Intent(EventNotesActivity.this,CalendarActivity.class);
                intent6.putExtra("EventId",eventId);
                intent6.putExtra("NoteId",noteId);
                intent6.putExtra("NotePerson",txtPerson.getText().toString());
                intent6.putExtra("NoteInfo",noteInfo);
                startActivityForResult(intent6,6);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!person.equals("")) {
                    if (okCancelReminderAlert()) {
                    } else {
                        setToSave();
                    }
                }
            }
        });

        btnVerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EventNotesActivity.this, VerbActivity.class);
                verbId = -1;
                intent1.putExtra("VerbId", verbId);
                Log.i("dolphin","btnVerb click");
                startActivityForResult(intent1, 3);
            }

        });


        btnThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(EventNotesActivity.this, VerbActivity.class);
                thingId = -1;
                intent4.putExtra("VerbId", thingId);
                Log.i("dolphin","btnVerb click");
                startActivityForResult(intent4, 4);
            }

        });        btnThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EventNotesActivity.this, ThingActivity.class);
                thingId = -1;
                intent1.putExtra("ThingId", thingId);
                Log.i("dolphin","btnThing click");
                startActivityForResult(intent1, 3);
            }

        });
    }


    public void setToSave() {
        Note note = new Note();
        note.setNoteId(noteId);
        note.setFkEventId(eventId);
        note.setNotePerson(txtPerson.getText().toString());
        note.setNoteInfo(txtNote.getText().toString());
        if (noteId == -1) {
            Log.i("dolphin", "savenew eventId=" + eventId+"  and "+note.getFkEventId());
            saveNew(note);
        } else {
            Log.i("dolphin", "save Existing eventId=" + eventId+"  and "+note.getFkEventId());
            saveExisting(note);
        }
    }

    public void setVerbList() {
        Log.i("dolphin", "in setVerblist");
        verbs = datasource.findAllVerbs();
        final ListView lstVerb = (ListView) findViewById(R.id.lst_verb);
        ArrayAdapter<Verb> adapter = new ArrayAdapter<Verb>(this,
                android.R.layout.simple_list_item_1, verbs);
        lstVerb.setAdapter(adapter);

        lstVerb.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        Verb verb = new Verb();
                        verb = verbs.get(position);
                        verbId = verb.getVerbId();

                        Log.i("dolphin", "before summary verbid=" + verbId + "  name=" + verb.getVerbName().toString());
                        if (verb.getVerbName() != null) {
                            verbName = verb.getVerbName().toString();
                        }
                        Log.i("dolphin", "after summary");
                        setVerbText();
                    }
                });
                lstVerb.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Verb verb = new Verb();
                        verb = verbs.get(position);
                        verbId = verb.getVerbId();
                        Log.i("dolphin", "deleting=" + verbId + verb.getVerbName());
                        if (okCancelVerbAlert()) {
//                            datasource.deleteVerb(verbId);
                        }
                        return true;
                    }
                });
        }

    public void setThingList() {
        Log.i("dolphin", "in setThinglist");
        things = datasource.findAllThings();
        final ListView lstThing = (ListView) findViewById(R.id.lst_thing);
        ArrayAdapter<Thing> adapter = new ArrayAdapter<Thing>(this,
                android.R.layout.simple_list_item_1, things);
        lstThing.setAdapter(adapter);

        lstThing.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        Thing thing = new Thing();
                        thing = things.get(position);
                        thingId = thing.getThingId();

                        Log.i("dolphin", "before summary");
                        if (thing.getThingName() != null) {
                            thingName = thing.getThingName();
                        }
                        Log.i("dolphin", "after summary");
                        setThingText();
                    }
                });
        lstThing.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Thing thing = new Thing();
                thing = things.get(position);
                thingId = thing.getThingId();
                Log.i("dolphin","deleting="+thingId+thing.getThingName());
                if (okCancelThingAlert()) {
//                    datasource.deleteThing(thingId);
                }
                return true;
            }
        });
    }

    protected void setVerbText() {
        if (verbId > -1) {
            for (int i=0;i<verbs.size();i++) {
                if (verbs.get(i).getVerbId() == verbId) {
                    Log.i("dolphin","setting text verbs.get="+verbs.get(i).getVerbName()+"  i="+i+"  verbid="+verbId);
                    txtNote.setText(updateVerbText(txtNote.getText().toString(), verbs.get(i).getVerbName().toString()));
                }
            }
        }
        verbId=-1;
    }

    protected void setThingText() {
        if (thingId > -1) {
            for (int i=0;i<things.size();i++) {
                if (things.get(i).getThingId() == thingId) {
                    Log.i("dolphin","setting text things.get="+things.get(i).getThingName()+"  i="+i+"  thingnId="+thingId);
                    txtNote.setText(updateThingText(txtNote.getText().toString(), things.get(i).getThingName().toString()));
                }
            }
        }
        thingId=-1;
    }

    protected String updateVerbText(String str1,String str2){
        Log.i("dolphinpo","str1="+str1+"  str2"+str2);
        if (str1.length()>0) {
            str1 = str1 + "\n" + str2;
        } else {
            str1 = str2;
        }
        return str1;
    }

    protected String updateThingText(String str1,String str2){
        if (str1.length()>0) {
            str1 = str1 + " " + str2;
        } else {
            str1 = str2;
        }
        return str1;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        noteId = -1;
        if (verbId > -1) {
            setVerbText();
        }
        if (thingId > -1) {
            setThingText();
        }
        setVerbList();
        setThingList();
    }

    protected boolean okCancelVerbAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventNotesActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                ok = true;
                datasource.deleteVerb(verbId);
                setVerbList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
                ok = false;
            }
        });
        myAlertDialog.show();
        return ok;
    }

    protected boolean okCancelThingAlert(){


    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventNotesActivity.this);
    myAlertDialog.setTitle("Delete");
    myAlertDialog.setMessage("Press Ok to delete");
    myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1) {
            // do something when the OK button is clicked
            ok = true;
            datasource.deleteThing(thingId);
            setThingList();
        }
    });
    myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1) {
            // do something when the Cancel button is clicked
            ok = false;
        }
    });
    myAlertDialog.show();
    return ok;
}

    protected boolean okCancelReminderAlert(){

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventNotesActivity.this);
        myAlertDialog.setTitle("Reminders");
        myAlertDialog.setMessage("Press Ok to create any outstanding reminders");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                Log.i("dolphin","before check");
                checkForActionRequired(person);
                setToSave();
                Log.i("dolphin","after check");
                ok = true;
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
                setToSave();

                ok = false;
            }
        });
        myAlertDialog.show();
        return ok;
    }

    protected void saveNew(Note note) {
        Log.i("dolphinv", "Item added  saveNew fk=" + note.getFkEventId());
        note = datasource.create(note);
        noteId = note.getNoteId();
    }

    protected boolean saveExisting(Note note) {
        Log.i("dolphinu", "saveexisting");
        if (datasource.update(note)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin", "onactivityresulttop" + requestCode);
//        if(requestCode==3)
//        {
//            verbId=data.getLongExtra("VerbId",2);
//        } else if (requestCode==2) {
//            thingId=data.getLongExtra("ThingId",2);
        if (requestCode == 5) {
            person = data.getStringExtra("ContactName");
            Log.i("dolphin", "person here is " + person);

            txtPerson.setText(person);

        }
    }


    protected void checkForActionRequired(String person) {
        String thisNote = "";
        String thisItem = "";
        String item = "";
        int actionItemCount = 0;
        int altActionItemCount = 0;
        String action = "";
        String thisAction = "";
        String thisAltAction = "";
        String thisAppliedAction = "";

        List<Note> personNotes;
        List<Thing> things;
        List<Verb> actions;
        List<Verb> altActions;

        // circle through THING for master list of items
        // for Verb, use extra field to store resolving verb eg) Return resolves Borrow so prompt to store Borrow on that activity

        personNotes = datasource.findAllEventNotes(person);
        things = datasource.findAllThings();
        actions = datasource.findAllVerbs();
        altActions = datasource.findAllVerbs();

        for (int a = 0; a < actions.size(); a++) {
            Log.i("dolphinx", "topper of action item thisAction=" + actions.get(a).getVerbName()+"  a="+a);
            if (actions.get(a).getVerbAppliesTo().equals("")) {
                thisAction = actions.get(a).getVerbName();
                for (int t = 0; t < things.size(); t++) {
                    thisItem = things.get(t).getThingName();
                    for (int i = 0; i < personNotes.size() - 1; i++) {
                        // inner note count for multiples
                        thisNote = personNotes.get(i).getNoteInfo();
                        actionItemCount = actionItemCount + findAnActionItem(thisAction, thisItem, thisNote);

                        // on this note
                        for (int alt = 0; alt < altActions.size(); alt++) {
                            thisAppliedAction = altActions.get(alt).getVerbAppliesTo();
                            thisAltAction = altActions.get(alt).getVerbName();
                            if (thisAppliedAction.equals(thisAction)) {
                                altActionItemCount = altActionItemCount + findAnActionItem(thisAltAction, thisItem, thisNote);
                                Log.i("dolphinx","thisaltaction="+ thisAltAction+"  thisItem="+thisItem+" thisNote="+thisNote+"  act&alt"+actionItemCount+altActionItemCount);
                            }
                        }
                        Log.i("dolphinx", "top of action item thisAction=" + thisAction + " and item=" + thisItem + "  thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);
                    }
                    if (actionItemCount - altActionItemCount > 0) {
                        postActionItem(thisAltAction, thisItem);
                        Log.i("dolphinx","post action="+thisAltAction+"  item="+thisItem);
                    }
                    actionItemCount = 0;
                    altActionItemCount = 0;
                }
            }
        }
    }

    protected int findAnActionItem(String action,String item, String thisNote) {
        int i=-0;
        int pos=0;
        int itemCount=0;
        String sentence="";



        sentence = action + " " + item;
        if (inStr(i, thisNote, sentence) > -1) {
            itemCount++;
        }
//        pos = inStr(i, thisNote, sentence);
//        Log.i("dolphinay","call instr pos="+pos);
//        while (pos != -1) {
//            itemCount = itemCount + 1;
//            pos = inStr(pos+1, thisNote, sentence);
//            Log.i("dolphinay in","call instr pos="+pos);
//        }
        return itemCount;
    }


    protected int inStr(int startPos, String thisNote, String sentence) {
        int i=-1;
        int j=0;
        int matchPos=-1;
        int startNotePos=0;
        boolean found=false;

        i = startPos;

        while ((i < thisNote.length()-1) && !found) {
//            Log.i("dolphinx","instr note="+thisNote+"  sentence="+sentence+"  notechar1="+thisNote.charAt(i)+"  i="+i+"  j="+j+"  sentencech="+sentence.charAt(j));
            startNotePos = i;
            while ((thisNote.charAt(i) == sentence.charAt(j)) && !found) {

//                Log.i("dolphinaei","instr while thisnote =="+thisNote+"  aye="+i+" sentence="+sentence+"  and jay="+j+"  and sent len="+sentence.length());
                i++;
                j++;

                if (j >= sentence.length()-1) {
                    found = true;
                    matchPos = startNotePos;
//                    Log.i("dolphinx","instr found one while thisnote =="+matchPos+"  startnotepos="+startNotePos);
                }
            }
            if (!found) {
                i = startNotePos + 1;
                j = 0;
            }
//            Log.i("dolphinx","ifound="+found);
        }
//        Log.i("dolphinx","oofound="+found+"  matchPos="+matchPos);
        return matchPos;
    }



    protected void postActionItem(String action,String item){
        Log.i("dolphindo","action="+action+"  item="+item);
        txtNote.setText(updateVerbText(txtNote.getText().toString(), action));
        txtNote.setText(updateThingText(txtNote.getText().toString(), item));
    }



}





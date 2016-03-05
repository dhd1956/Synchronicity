package com.mind.oceanic.the.synchronicity.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.event.EventNotesActivity;
import com.mind.oceanic.the.synchronicity.event.UpdateEventActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Verb;

import java.util.List;

/**
 * Created by dave on 2/15/16.
 */


public class VerbActivity extends Activity  {

    SynchronicityDataSource datasource;

    List<Verb> verbs;
    long verbId=-1;
    String verbName;
    String verbAppliesTo="";

//    Button btnCancel;
//    Button btnVerb;
//    Button btnSave;
    EditText txtVerbName;
    EditText txtAppliesTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_verb);

        datasource = new SynchronicityDataSource(this);
        datasource.open();

        Log.i("dolphin", "top of verbActivity");
        Bundle b = getIntent().getExtras();
        verbId = b.getLong("VerbId");
        verbName = b.getString("VerbName");

        txtVerbName = (EditText) findViewById(R.id.txt_verb_name);
        txtAppliesTo = (EditText) findViewById(R.id.txt_applies_to);
//        btnCancel = (Button) findViewById(R.id.btn_cancel);
//        btnVerb = (Button) findViewById(R.id.btn_verb);
//        btnSave = (Button) findViewById(R.id.btn_save);

        Verb verb = new Verb();
        if (verbId != -1) {
            verb.setVerbId(verbId);
            verb.setVerbName(verbName);
            verb.setVerbAppliesTo(verbAppliesTo);
            Log.i("dolphin","set verb name to verbName="+verbName+"  "+verbId);
        }
        txtVerbName.setText(verbName);
        txtVerbName.setText(verbAppliesTo);


//        btnCancel.setOnClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             verbId = -1;
//                                             finish();
//                                         }
//                                     }
//        );
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//save();
//
//                                       }
//                                   }
//        );

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void prepareReturnValues() {
        Intent intent=new Intent();
        intent.putExtra("VerbId", verbId);
        Log.i("dolphin","vvvverbid="+verbId);
        setResult(1001, intent);
        finish();
    }

    protected void save() {
        Verb verb = new Verb();
        verb.setVerbId(verbId);
        verb.setVerbName(txtVerbName.getText().toString());
        verb.setVerbAppliesTo(txtAppliesTo.getText().toString());
        if (verbId == -1) {
            saveNew(verb);
        } else {
            saveExisting(verb);
        }
        prepareReturnValues();
        finish();
    }

    protected void saveNew(Verb verb) {
        Log.i("dolphinv", "Item adding="+verb.getVerbName());
        verb = datasource.create(verb);
        Log.i("dolphinv", "Item added");
        verbId = verb.getVerbId();
        finish();
    }

    protected boolean saveExisting(Verb verb) {
        Log.i("dolphinu", "saveexisting");
        if (datasource.update(verb)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verb, menu);
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

            case R.id.menu_save:
                save();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }
}

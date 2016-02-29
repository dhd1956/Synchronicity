package com.mind.oceanic.the.synchronicity.note;

import android.app.Activity;
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
    Button btnCancel;
    Button btnVerb;
    Button btnSave;
    EditText txtVerbName;

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
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnVerb = (Button) findViewById(R.id.btn_verb);
        btnSave = (Button) findViewById(R.id.btn_save);

        Verb verb = new Verb();
        if (verbId != -1) {
            verb.setVerbId(verbId);
            verb.setVerbName(verbName);
            Log.i("dolphin","set verb name to verbName="+verbName+"  "+verbId);
        }
        txtVerbName.setText(verbName);


        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             verbId = -1;
                                             finish();
                                         }
                                     }
        );

        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Verb verb = new Verb();
                                           verb.setVerbId(verbId);
                                           verb.setVerbName(txtVerbName.getText().toString());
                                           verb.setVerbAppliesTo("All");
                                           if (verbId == -1) {
                                               saveNew(verb);
                                           } else {
                                               saveExisting(verb);
                                           }
                                           prepareReturnValues();
                                           finish();

                                       }
                                   }
        );

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

}

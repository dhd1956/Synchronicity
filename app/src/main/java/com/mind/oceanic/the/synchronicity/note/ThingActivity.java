package com.mind.oceanic.the.synchronicity.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.Thing;

import java.util.List;

/**
 * Created by dave on 2/15/16.
 */


public class ThingActivity extends Activity  {

    SynchronicityDataSource datasource;

    List<Thing> things;
    long thingId=-1;
    String thingName;
    Button btnCancel;
    Button btnThing;
    Button btnSave;
    EditText txtThingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_thing);

        datasource = new SynchronicityDataSource(this);
        datasource.open();

        Log.i("dolphin", "top of thingActivity");
        Bundle b = getIntent().getExtras();
        thingId = b.getLong("ThingId");
        thingName = b.getString("ThingName");

        txtThingName = (EditText) findViewById(R.id.txt_thing_name);
//        btnCancel = (Button) findViewById(R.id.btn_cancel);
//        btnThing = (Button) findViewById(R.id.btn_thing);
//        btnSave = (Button) findViewById(R.id.btn_save);

        Thing thing = new Thing();
        if (thingId != -1) {
            thing.setThingId(thingId);
            thing.setThingName(thingName);
            Log.i("dolphin","set thing name to thingName="+thingName);
        }
        txtThingName.setText(thingName);


        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finish();
                                         }
                                     }
        );

        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                            save();

                                       }
                                   }
        );

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void save(){
        Thing thing = new Thing();
        thing.setThingId(thingId);
        thing.setThingName(txtThingName.getText().toString());
        thing.setThingUse("All");
        if (thingId == -1) {
            saveNew(thing);
        } else {
            saveExisting(thing);
        }
        prepareReturnValues();
        finish();
    }

    protected void prepareReturnValues() {
        Intent intent=new Intent();
        intent.putExtra("ThingId", thingId);
        setResult(3, intent);
        finish();
        }
    protected void saveNew(Thing thing) {
        Log.i("dolphinv", "Item adding="+thing.getThingName());
        thing = datasource.create(thing);
        Log.i("dolphinv", "Item added");
        thingId = thing.getThingId();
        finish();
    }

    protected boolean saveExisting(Thing thing) {
        Log.i("dolphinu", "saveexisting");
        if (datasource.update(thing)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thing, menu);
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

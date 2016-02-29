package com.mind.oceanic.the.synchronicity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.event.EventListActivity;
import com.mind.oceanic.the.synchronicity.event.UpdateEventActivity;
import com.mind.oceanic.the.synchronicity.match.MatchKeywordsActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.synch.MaintainSynchronicityActivity;
import com.mind.oceanic.the.synchronicity.synch.SynchListActivity;

import java.util.List;

/**
 * Created by dave on 2/6/16.
 */
public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

Log.i("dolphin","matching");
        setButtons();


    }
    private void setButtons() {

        Button btn_goto_synch = (Button) findViewById(R.id.btn_goto_synch);

        btn_goto_synch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent1 = new Intent(MainActivity.this, SynchListActivity.class);
                startActivityForResult(intent1, 1);

            }
        });

        Button btn_goto_match = (Button) findViewById(R.id.btn_goto_match);

        btn_goto_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, MatchKeywordsActivity.class);
                startActivityForResult(intent2, 1);
            }
        });

        Button btn_goto_event = (Button) findViewById(R.id.btn_goto_event);

        btn_goto_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, EventListActivity.class);
                intent3.putExtra("EventSource","EventList");
                startActivityForResult(intent3, 1);
            }
        });


    }


}


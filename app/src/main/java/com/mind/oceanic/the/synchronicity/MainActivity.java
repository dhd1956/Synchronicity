package com.mind.oceanic.the.synchronicity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.event.EventListActivity;
import com.mind.oceanic.the.synchronicity.event.UpdateEventActivity;
import com.mind.oceanic.the.synchronicity.match.MatchKeywordsActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.synch.MaintainSynchronicityActivity;
import com.mind.oceanic.the.synchronicity.synch.SynchListActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dave on 2/6/16.
 */
public class MainActivity extends Activity {

    TextView tvIntro;
    TextView tvContents1;
    TextView tvContents2;
    TextView tvContents3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvContents1 = (TextView) findViewById(R.id.tv_contents1);
        tvContents2 = (TextView) findViewById(R.id.tv_contents2);
        tvContents3 = (TextView) findViewById(R.id.tv_contents3);

        tvIntro.setText(Html.fromHtml("<h2><u>Synchronicity Tracker</u></h2><p><p><br>Welcome to the Synchronicity Tracker. The term synchronicity was coined by Carl Jung. He applied it to events that are considered 'meaningful coincidences'. Events where there is no apparent causal relationship but they seem to be meaningfully related.<br></p> <p>This app is intended to facilitate the tracking of events and assistance in discovering synchronicity.<br> It is a bit of a time machine in that you can set the date forward or backward and 'connect the dots' between events at any time"));

        tvContents1.setText(Html.fromHtml("<u>Maintain Synchronicity</> - used when you have a synchronistic event in mind and you want to record it and associated events."));
        tvContents2.setText(Html.fromHtml("<br><u>Maintain Events</> - used to log events that may or may not appear synchronistic. They can be linked at a later date if applicable."));
        tvContents3.setText(Html.fromHtml("<br><u>Match Synchronistic Events</> - used to compare events. Keywords are matched and if not significant can be marked to be ignored."));

    }
//    private void setButtons() {
//
//        Button btn_goto_synch = (Button) findViewById(R.id.btn_goto_synch);
//
//        btn_goto_synch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v2) {
//                Intent intent1 = new Intent(MainActivity.this, SynchListActivity.class);
//                startActivityForResult(intent1, 1);
//
//            }
//        });
//
//        Button btn_goto_match = (Button) findViewById(R.id.btn_goto_match);
//
//        btn_goto_match.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(MainActivity.this, MatchKeywordsActivity.class);
//                startActivityForResult(intent2, 1);
//            }
//        });
//
//        Button btn_goto_event = (Button) findViewById(R.id.btn_goto_event);
//
//        btn_goto_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent3 = new Intent(MainActivity.this, EventListActivity.class);
//                intent3.putExtra("EventSource", "EventList");
//                startActivityForResult(intent3, 1);
//            }
//        });
//
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            case R.id.menu_maintain_synchronicity:
                Intent intent4 = new Intent(MainActivity.this, SynchListActivity.class);
                startActivityForResult(intent4, 4);
                break;

            case R.id.menu_maintain_events:
                Intent intent5 = new Intent(MainActivity.this, EventListActivity.class);
                intent5.putExtra("EventSource", "EventList");
                startActivityForResult(intent5, 5);
                break;

            case R.id.menu_match_events:
                Intent intent6 = new Intent(MainActivity.this, MatchKeywordsActivity.class);
                startActivityForResult(intent6, 6);
                break;


            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

    /**
     * Created by dave on 8/13/15.
     */
    public static class SettingsActivity {
    }
}



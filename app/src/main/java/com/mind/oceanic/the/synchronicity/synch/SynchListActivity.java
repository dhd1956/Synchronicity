package com.mind.oceanic.the.synchronicity.synch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.SynchItem;

import java.util.List;

public class SynchListActivity extends Activity implements View.OnClickListener {

    List<SynchItem> synchItems;
    SynchronicityDataSource datasource;
    SynchItem synchItem;
    long synchId = -1;
    String synchDate = null;
    String synchSummary = null;
    String synchDetails = null;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_list);

        datasource = new SynchronicityDataSource(this);
        Log.i("dolphin", "before start");
        datasource.open();
        synchItems = datasource.findAllSynchItems();

        setList();

        Button btn_create_view = (Button) findViewById(R.id.btn_create_new);
        Button btnReturn = (Button) findViewById(R.id.btn_return);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v2) {
                finish();
            }
        });

        btn_create_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent = new Intent(SynchListActivity.this, MaintainSynchronicityActivity.class);
                synchDate = null;
                synchSummary = null;
                synchDetails = null;
                intent.putExtra("Id", synchId);
                intent.putExtra("Date",synchDate);
                intent.putExtra("Summary", synchSummary);
                intent.putExtra("Detail", synchDetails);
                intent.putExtra("Flag","Reset");
                Log.i("dolphin", "still alive");
                startActivityForResult(intent, 1);

            }
        });

        Log.i("dolphin", "start");




//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                        null, null, null, null);
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(
//                                cur.getColumnIndex(ContactsContract.Contacts._ID));
//                Log.i("dolphin", "mdarker  "+id);
//                String name = cur.getString(
//                                cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                Log.i("dolphin", "mdarker  "+name);
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                            //Query phone here.  Covered next
//                }
//            }
//        }
//
//        Intent calIntent = new Intent(Intent.ACTION_INSERT);
//        calIntent.setType("vnd.android.cursor.item/event");
//        calIntent.putExtra(CalendarContract.Events.TITLE, "My House Party");
//        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Beach House");
//        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "A Pig Roast on the Beach");
//
//        GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
//                calDate.getTimeInMillis());
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
//                calDate.getTimeInMillis());
//
//        startActivity(calIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
//        if (view == shoppingDate) {
//
//        }
    }

    public void setList() {

        synchItems = datasource.findAllSynchItems();
        final ListView lst1 = (ListView) findViewById(R.id.lst_synch_summary);
        ArrayAdapter<SynchItem> adapter = new ArrayAdapter<SynchItem>(this,
                android.R.layout.simple_list_item_1, synchItems);
        lst1.setAdapter(adapter);

        lst1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        synchItem = synchItems.get(position);
                        synchId = synchItem.getSynchId();
                        Log.i("dolphin", "there is a house in new " + synchId);
                        Log.i("dolphin", "before summary");
                        if (synchItem.getSynchSummary() != null) {
                            synchSummary = synchItem.getSynchSummary().toString();
                        }
                        Log.i("dolphin", "after summary");
                        synchDate = synchItem.getSynchDate();
                        synchDetails = synchItem.getSynchDetails().toString();
                        Intent intent2 = new Intent(SynchListActivity.this, MaintainSynchronicityActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());
                        intent2.putExtra("Id", synchId);
                        intent2.putExtra("Date", synchDate);
                        intent2.putExtra("Summary", synchItem.getSynchSummary());
                        intent2.putExtra("Details", synchItem.getSynchDetails());
                        long eventId = -1;
                        intent2.putExtra("Flag", "Reset");
                        Log.i("dolphin", "in click of list=" + synchItem.getSynchDetails());
                        startActivityForResult(intent2, 2);
                    }
                }
        );
        lst1.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SynchItem synchItem = new SynchItem();
                synchItem = synchItems.get(position);
                synchId = synchItem.getSynchId();
//                Log.i("dolphin", "deleting=" + synchId + synchItem.getVerbName());
                if (okCancelSynchAlert()) {
                }
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        synchId = -1;
        setList();
    }

    protected boolean okCancelSynchAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(SynchListActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                datasource.deleteSynchItem(synchId);
                datasource.deleteSynchEventSynchOrphans(synchId);
                setList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
            }
        });
        myAlertDialog.show();
        return true;
    }
}

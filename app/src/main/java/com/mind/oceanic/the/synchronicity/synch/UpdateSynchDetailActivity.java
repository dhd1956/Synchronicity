package com.mind.oceanic.the.synchronicity.synch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.model.SynchItem;

/**
 * Created by dave on 1/21/16.
 */
public class UpdateSynchDetailActivity extends Activity {

    SynchronicityDataSource datasource;
    long synchItemId = -1;
    String synchItemDate = "";
    String synchItemSummary = "";
    String synchItemDetails = "";
    boolean newItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText ET_SUMMARY;
        final EditText ET_DETAILS;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.synch_summary_detail);
        Log.i("dolphin", "toggle");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
        }

        datasource = new SynchronicityDataSource(this);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Bundle b = getIntent().getExtras();
        synchItemId = b.getLong("Id");
        Log.i("dolphin","id is "+synchItemId);
        if (synchItemId == -1) {
            newItem = true;
        }
        synchItemDate = b.getString("Date");
        synchItemSummary = b.getString("Summary");
        synchItemDetails = b.getString("Details");
        ET_SUMMARY = (EditText) findViewById(R.id.txt_synch_summary);
        ET_SUMMARY.setText(synchItemSummary);

        ET_DETAILS = (EditText) findViewById(R.id.txt_synch_details);
        ET_DETAILS.setText(synchItemDetails);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                          SynchItem synchItem = new SynchItem();
                                          if (!newItem) {
                                              synchItem.setSynchId(synchItemId);
                                              synchItem.setSynchDate(synchItemDate);
                                              synchItem.setSynchSummary(synchItemSummary);
                                              synchItem.setSynchDetails(synchItemDetails);
                                          }
                                          synchItem.setSynchSummary(ET_SUMMARY.getText().toString());
                                          synchItem.setSynchDetails(ET_DETAILS.getText().toString());
                                          Log.i("dolphiny", "setdetail=" + synchItem.getSynchDetails());

                                          if (newItem) {
                                              Log.i("dolphinv","saving new");
                                              SaveNew(synchItem);
                                          } else {
                                              Log.i("dolphinv","saving existing");
                                              SaveExisting(synchItem);
                                          }
                                           prepareReturnValues();
                                      }
                                  }
        );

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("dolphin","btnCancel");
                prepareReturnValues();
            }
        }
        );


        Log.i("dolphin", "togglest");
    }
    protected void prepareReturnValues() {
        Intent intent=new Intent();
        intent.putExtra("SynchId", synchItemId);
        setResult(2, intent);
        finish();
    }
    protected boolean SaveNew(SynchItem synchItem) {
        Log.i("dolphinv", "Item added");
        if (datasource.add(synchItem)) {
            synchItemId = synchItem.getSynchId();
            return true;
        } else {
            Log.i("dolphinv", "ID not added");
            return false;
        }

    }

    protected boolean SaveExisting(SynchItem synchItem) {
        Log.i("dolphinu","saveexisting");
        if (datasource.update(synchItem)) {
            return true;
        } else {
            return false;
        }
    }
}

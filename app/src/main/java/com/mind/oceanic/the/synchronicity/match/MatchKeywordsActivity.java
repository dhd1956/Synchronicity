package com.mind.oceanic.the.synchronicity.match;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mind.oceanic.the.synchronicity.R;
import com.mind.oceanic.the.synchronicity.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity.note.VerbActivity;
import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Ignore;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;
import com.mind.oceanic.the.synchronicity.synch.MaintainSynchronicityActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by dave on 2/5/16.
 */
public class MatchKeywordsActivity extends Activity {
    SynchronicityDataSource datasource;
    private TextView lbl_match_word;
    private TextView lbl_left_event_details;
    private TextView lbl_right_event_details;
    private List<Event> leftEvents;
    private List<Event> rightEvents;
    private List<Ignore> ignoreWords;
    private List<SynchItemEvent> links;
    protected int pos;
    protected int leftPos;
    protected int rightPos;
    protected int saveLeftPos=-1;
    protected int saveRightPos=-1;
    protected int saveI=-1;
    protected int saveJ=-1;
    boolean pauseCompare=false;
    protected long synchId=-1;
    protected String synchDate="";
    protected String synchSummary="";
    protected String synchDetails="";
    protected int i=0;
    protected int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        datasource = new SynchronicityDataSource(this);

        lbl_match_word = (TextView) findViewById(R.id.lbl_match_word);
        lbl_left_event_details = (TextView) findViewById(R.id.lbl_left_event_details);
        lbl_left_event_details.setMovementMethod(new ScrollingMovementMethod());

        lbl_right_event_details = (TextView) findViewById(R.id.lbl_right_event_details);
        lbl_right_event_details.setMovementMethod(new ScrollingMovementMethod());

        setCursors();
        compareDetails();
//        Button btn_Ignore = (Button) findViewById(R.id.btn_ignore);
//        btn_Ignore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ignore();
//            }
//        });
//
//        Button btn_Return = (Button) findViewById(R.id.btn_return);
//        btn_Return.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//        Button btn_Match = (Button) findViewById(R.id.btn_match);
//        btn_Match.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                match();
//
//
//            }
//        });
//        Button btn_Pass = (Button) findViewById(R.id.btn_pass);
//        btn_Pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                compareDetails();
//                Log.i("dolphin", "in Pass saveI=" + saveI + "  saveJ=" + saveJ + "   saveLeftPos=" + saveLeftPos + "  right=" + saveRightPos);
//            }
//        });


    }

//    private boolean foundDuplicate(long s,long le,long re) {
////        if (datasource.findLinkedEvent(s,le) && datasource.findLinkedEvent(s,re)) {
////            return true;
////        } else {
//            return false;
////        }
//    }

    private void setCursors() {
        // get findAll events and circle through them
        leftEvents = datasource.findAllEvents();
        rightEvents = datasource.findAllEvents();
        ignoreWords = datasource.findAllIgnores();

    }

    private void compareDetails() {

        String leftDetails;
        String rightDetails;
        String nextLeftWord = "";
        String nextRightWord = "";
//        int i=-1;
//        int j=-1;

        pauseCompare = false;
        i = saveI;
//        saveI = -1;
        while (i < leftEvents.size()-1 && !pauseCompare) {
            i++;
            leftDetails = leftEvents.get(i).getEventDetails();
            leftPos = saveLeftPos;
            saveLeftPos = -1;
            while (leftPos < leftDetails.length()-1 && !pauseCompare) {
//                leftPos++;
                nextLeftWord = getCheckedWord(leftEvents.get(i).getEventDetails());
//                Log.i("dolphin","doverette i="+i+"  j="+j+"  leftpos="+leftPos+"  leftDetLen="+ leftDetails.length());
                j = saveJ;
                saveJ = -1;
                while (j < rightEvents.size()-1 && !pauseCompare) {
                    j++;
//                    Log.i("dolphin","rover i="+i+"  j="+j+"  leftpos="+leftPos+"  rightpos="+rightPos+" nextLeftWord="+nextLeftWord+"  nextRightWord="+nextRightWord);
                    if (leftEvents.get(i).getEventId() != rightEvents.get(j).getEventId()) {
//                        boolean alreadyMatched = checkMatched(leftEvents.get(i).getEventId(), rightEvents.get(j).getEventId());
                        rightDetails = rightEvents.get(j).getEventDetails();
                        rightPos = saveRightPos;
                        saveRightPos = -1;
                        while (rightPos < rightDetails.length()-1 && !pauseCompare) {
//                            rightPos++;
                            nextRightWord = getNextRightWord(rightEvents.get(j).getEventDetails());
//                            Log.i("dolphin","dover i="+i+"  j="+j+" rightpos="+rightPos+"  rightDetLen = "+rightDetails.length()+"  leftpos="+leftPos+"  rightpos="+rightPos+" nextLeftWord="+nextLeftWord+"  nextRightWord="+nextRightWord);
                            if (nextLeftWord.equals(nextRightWord) && !pauseCompare) {

                                Log.i("dolphin","doverttt nextLeftWord="+nextLeftWord+"  nextRigtWord="+nextRightWord+" pause="+pauseCompare);
                                lbl_match_word.setText(nextLeftWord);
                                    lbl_left_event_details.setText(leftDetails);
                                    lbl_right_event_details.setText(rightDetails);
                                    pauseCompare = true;
                                    saveI = i;
                                    saveJ = j;
                                    saveLeftPos = leftPos;
                                    saveRightPos = rightPos;
                                }

                            }
//                            Log.i("dolphin","after in if .equals");
                        }

//                        Log.i("dolphin","while rightpos < rightdetails");
                    }
//                    Log.i("dolphin","if left j="+j+" rightsize"+rightEvents.size());

//                Log.i("dolphin","for j");

            }
//            Log.i("dolphin","while leftpos");
        }
//        Log.i("dolphin","FINISHED for i"+i+"  j"+j+"  savei="+saveI+"  savej="+saveJ);

    }

    private String getCheckedWord(String str) {
        String thisWord="";
        boolean ignoreThisWord=true;
        int i=0;

        while (leftPos < str.length()-1 && ignoreThisWord) {
            thisWord = getNextLeftWord(str);
//            Log.i("dolphino","in get checked="+thisWord+"  leftpos="+leftPos+"  strlen="+str.length());
            ignoreThisWord = false;
            i = -1;
            while (i < ignoreWords.size()-1 && !ignoreThisWord) {
                i++;
//                Log.i("dolphino","i before mid="+i+"  size="+ignoreWords.size());
                if (ignoreWords.size() > 0) {
//                    Log.i("dolphino","thisword="+thisWord+"  ig="+ignoreWords.get(i).getWord());
                    if (thisWord.equals(ignoreWords.get(i).getWord())) {
                        ignoreThisWord = true;
                    }
                }
            }
        }
//        Log.i("dolphino","out get checked="+thisWord+"  leftpos="+leftPos+"  strlen="+str.length());
//        Log.i("dolphino", "oot get checked=" + thisWord + "  leftpos=" + leftPos + "  strlen=" + str.length() + "  ignorewords=" + ignoreWords.get(i).getWord());
        return thisWord;
    }

    private String getNextLeftWord(String str) {
        String retStr;
        int i;
        char ch='0';
        if (leftPos==-1){leftPos = 0;}
        i = leftPos;

        while (ch != ' ' && i < str.length()-1) {
            i++;
            ch = str.charAt(i);
//            Log.i("dolphinn","lch = "+ch);
        };
//        Log.i("dolphinn","leftpos="+leftPos+"  i="+i);
        retStr = str.substring(leftPos,i);
//        Log.i("dolphinn","  left retstr="+retStr);
        leftPos=i;
        return retStr;
    }

    private String getNextRightWord(String str) {
        int i;
        char ch='0';
        String retStr;
        if (rightPos==-1){rightPos = 0;}
        i = rightPos;

        while (ch != ' ' && i < str.length()-1) {
            i++;
            ch = str.charAt(i);
//            Log.i("dolphin","rch = "+ch);
        };
//        Log.i("dolphin","rightpos="+rightPos+"  i="+i);
        retStr = str.substring(rightPos,i);
        rightPos=i;
//        Log.i("dolphin","  right retstr="+retStr);
        return retStr;
    }

    protected void ignore(){
        Ignore ignore = new Ignore();
        Log.i("dolphin", "mathword=" + lbl_match_word.getText());
        ignore.setWord(lbl_match_word.getText().toString());
        try {
            datasource.add(ignore);
        } catch (Exception e){
            Context context = getApplicationContext();
            CharSequence text = "Finished";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        ignoreWords = datasource.findAllIgnores();
        compareDetails();
    }

    protected void match(){
        if (datasource.findLinkedTogetherEvents(leftEvents.get(i).getEventId(),rightEvents.get(j).getEventId())) {
            Context context = getApplicationContext();
            CharSequence text = "Already matched";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Log.i("dolphin","sinked id=??");
            links = datasource.findAllSynchItemEvents();
            for (int ii=0;ii<links.size();ii++) {
                Log.i("dolphin","sinked id="+links.get(ii).getSeSynchId()+"  sinked e id=" +links.get(ii).getSeEventId());

            }
        } else {
            Intent intent = new Intent(MatchKeywordsActivity.this, MaintainSynchronicityActivity.class);
            synchSummary = null;
            synchDetails = null;
            intent.putExtra("Id", synchId);
            intent.putExtra("Summary", synchSummary);
            intent.putExtra("Detail", synchDetails);
            intent.putExtra("rightEventId", leftEvents.get(i).getEventId());
            Log.i("dolphin", "ww2  " + i);
            intent.putExtra("leftEventId", rightEvents.get(j).getEventId());
            intent.putExtra("Flag", "set");
            Log.i("dolphin", "still alive");
            startActivityForResult(intent, 1);
        }
    }

    protected void pass(){
        compareDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_match, menu);
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
            case R.id.menu_return:
                finish();
                break;

            case R.id.menu_ignore:
                ignore();
                break;

            case R.id.menu_match:
                match();
                break;

            case R.id.menu_pass:
                pass();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

}
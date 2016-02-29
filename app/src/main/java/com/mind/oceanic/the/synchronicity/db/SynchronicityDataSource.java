package com.mind.oceanic.the.synchronicity.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;

/**
 * Created by dave on 1/19/16.
 */

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

import com.mind.oceanic.the.synchronicity.model.Event;
import com.mind.oceanic.the.synchronicity.model.Ignore;
import com.mind.oceanic.the.synchronicity.model.Note;
import com.mind.oceanic.the.synchronicity.model.SynchItem;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;
import com.mind.oceanic.the.synchronicity.model.Thing;
import com.mind.oceanic.the.synchronicity.model.Verb;

import java.util.ArrayList;
    import java.util.List;

    /**
     * Created by dave on 8/14/15.
     */
    public class SynchronicityDataSource {
        SQLiteOpenHelper dbhelper;
        SQLiteDatabase database;

        private static final String[] allSynchItemColumns = {
                SynchronicityDBOpenHelper.COLUMN_SYNCH_ID,
                SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE,
                SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY,
                SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS};

        private static final String[] allEventColumns = {
                SynchronicityDBOpenHelper.COLUMN_EVENT_ID,
                SynchronicityDBOpenHelper.COLUMN_EVENT_DATE,
                SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY,
                SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS};

        private static final String[] allSynchItemEventColumns = {
                SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID,
                SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID};

        private static final String[] allIgnoresColumns = {
                SynchronicityDBOpenHelper.COLUMN_WORD};

        private static final String[] allPlacesColumns = {
                SynchronicityDBOpenHelper.COLUMN_PLACE_ID,
                SynchronicityDBOpenHelper.COLUMN_PLACE_NAME,
                SynchronicityDBOpenHelper.COLUMN_PLACE_MAIN_PHONE,
                SynchronicityDBOpenHelper.COLUMN_PLACE_EMAIL,
                SynchronicityDBOpenHelper.COLUMN_PLACE_ADDRESS,
                SynchronicityDBOpenHelper.COLUMN_PLACE_CITY,
                SynchronicityDBOpenHelper.COLUMN_PLACE_PROVINCE};

        private static final String[] allPersonsColumns = {
                SynchronicityDBOpenHelper.COLUMN_PERSON_ID,
                SynchronicityDBOpenHelper.COLUMN_PERSON_FIRST_NAME,
                SynchronicityDBOpenHelper.COLUMN_PERSON_LAST_NAME,
                SynchronicityDBOpenHelper.COLUMN_PERSON_MAIN_PHONE,
                SynchronicityDBOpenHelper.COLUMN_PERSON_EMAIL,
                SynchronicityDBOpenHelper.COLUMN_PERSON_ADDRESS,
                SynchronicityDBOpenHelper.COLUMN_PERSON_CITY,
                SynchronicityDBOpenHelper.COLUMN_PERSON_PROVINCE,
                SynchronicityDBOpenHelper.COLUMN_PERSON_COUNTRY};

        private static final String[] allThingsColumns = {
                SynchronicityDBOpenHelper.COLUMN_THING_ID,
                SynchronicityDBOpenHelper.COLUMN_THING_NAME,
                SynchronicityDBOpenHelper.COLUMN_THING_USE};

        private static final String[] allVerbCsolumns = {
                SynchronicityDBOpenHelper.COLUMN_VERB_ID,
                SynchronicityDBOpenHelper.COLUMN_VERB_NAME,
                SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO};

        private static final String[] allTNotesColumns = {
                SynchronicityDBOpenHelper.COLUMN_NOTE_ID,
                SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID,
                SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON,
                SynchronicityDBOpenHelper.COLUMN_NOTE_INFO};



        public SynchronicityDataSource(Context context) {
            dbhelper = new SynchronicityDBOpenHelper(context);
        }

        public void open() {
            database = dbhelper.getWritableDatabase();
            Log.i("dolphin", "Database opened");
        }

        public void close() {
            Log.i("dolphin", "Database closed");
            dbhelper.close();
        }

        public SynchItem create(SynchItem synchItem) {
            ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE, synchItem.getSynchDate());
                values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY, synchItem.getSynchSummary());
                values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS, synchItem.getSynchDetails());
            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS, null, values);
            synchItem.setSynchId(insertid);
            Log.i("dolphinv", "null synch item  " + synchItem.getSynchId());

            return synchItem;
        }

        public Ignore create(Ignore ignore) {
            Log.i("dolphinv", "TOP OF CREATE IG");
            if (ignore.getWord() == null) {
                Log.i("dolphinv", "null ig item");
            } else {
                String sqlCmd = "Insert into ignores values ('"+ignore.getWord()+"');";

                Log.i("dolphin", "sqlCmd=" + sqlCmd);
//                database.execSQL(sqlCmd);
                database.execSQL(sqlCmd);
                Log.i("dolphiny", "after execSQL");
            }
            return ignore;
        }

        public Event create(Event event) {
            Log.i("dolphinv", "TOP OF CREATE EVENT");
            if (event.getEventDetails() == null) {
                Log.i("dolphinv", "null synch item");
            } else {
                ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE, event.getEventDate());
                values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY, event.getEventSummary());
                values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS, event.getEventDetails());
                long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_EVENTS, null, values);
                event.setEventId(insertid);
            }
            return event;
        }

        public Verb create(Verb verb) {
            Log.i("dolphinv", "TOP OF CREATE VErB");
            if (verb.getVerbName() == null) {
                Log.i("dolphinv", "null verb item");
            } else {
                ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_VERB_NAME, verb.getVerbName());
                values.put(SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO, verb.getVerbAppliesTo());
                long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_VERBS, null, values);
                verb.setVerbId(insertid);
            }
            return verb;
        }


        public Thing create(Thing thing) {
            Log.i("dolphinv", "TOP OF CREATE VErB");
            if (thing.getThingName() == null) {
                Log.i("dolphinv", "null verb item");
            } else {
                ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_THING_NAME, thing.getThingName());
                values.put(SynchronicityDBOpenHelper.COLUMN_THING_USE, thing.getThingUse());
                long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_THINGS, null, values);
                thing.setThingId(insertid);
            }
            return thing;
        }

        public Note create(Note note) {
            Log.i("dolphinv", "TOP OF CREATE NOTE");
            if (note.getNoteInfo() == null) {
                Log.i("dolphinv", "null verb item");
            } else {
                ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID, note.getFkEventId());
                values.put(SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON, note.getNotePerson());
                values.put(SynchronicityDBOpenHelper.COLUMN_NOTE_INFO, note.getNoteInfo());
                long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_NOTES, null, values);
                note.setNoteId(insertid);
            }
            return note;
        }

        public boolean update(SynchItem synchItem) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " Set " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE + " = '" + synchItem.getSynchDate() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY + " = '" + synchItem.getSynchSummary() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS + " = '" + synchItem.getSynchDetails() + "' where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = " + synchItem.getSynchId() + ";";
            Log.i("dolphiny", "update sql=" + sqlCmd);
            open();
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return (synchItem.getSynchId() != -1);
        }

        public boolean update(Event event) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_EVENTS + " Set " + SynchronicityDBOpenHelper.COLUMN_EVENT_DATE + " = '" + event.getEventDate() + "', " + SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY + " = '" + event.getEventSummary() + "', " + SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS + " = '" + event.getEventDetails() + "' where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = " + event.getEventId() + ";";
            Log.i("dolphiny", "update sql=" + sqlCmd);
            open();
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return (event.getEventId() != -1);
        }

        public boolean update(Verb verb) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_VERBS + " Set " + SynchronicityDBOpenHelper.COLUMN_VERB_NAME + " = '" + verb.getVerbName()  + "' where " + SynchronicityDBOpenHelper.COLUMN_VERB_ID + " = " + verb.getVerbId() + ";";
            Log.i("dolphiny", "update sql=" + sqlCmd);
            open();
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return (verb.getVerbId() != -1);
        }

        public boolean update(Thing thing) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_THINGS + " Set " + SynchronicityDBOpenHelper.COLUMN_THING_NAME + " = '" + thing.getThingName()  + "' where " + SynchronicityDBOpenHelper.COLUMN_THING_ID + " = " + thing.getThingId() + ";";
            Log.i("dolphiny", "update sql=" + sqlCmd);
            open();
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return (thing.getThingId() != -1);
        }

        public boolean update(Note note) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_NOTES + " Set " + SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON + " = '" + note.getNotePerson()  + "', " + SynchronicityDBOpenHelper.COLUMN_NOTE_INFO + " = '" + note.getNoteInfo()  + "' where " + SynchronicityDBOpenHelper.COLUMN_NOTE_ID + " = " + note.getNoteId() + ";";
            Log.i("dolphiny", "update sql=" + sqlCmd);
            open();
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return (note.getNoteId() != -1);
        }


        public SynchItem find(long synchId) {
            Log.i("dolphin", "find sql=before");
            open();
//            String sqlCmd = "select synchDate, synchSummary, SynchDetail fromm synchItems where synchId = '" + synchItem.getSynchId() + "'";
            String sqlCmd = "select synchId, synchDate, synchSummary, SynchDetails from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchId + "';";
            Log.i("dolphin", "find sql=" + sqlCmd);
            Cursor cursor = database.rawQuery(sqlCmd, null);
            SynchItem synchItem = new SynchItem();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Log.i("dolphin", "id=");
                    synchItem.setSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID)));
                    Log.i("dolphin", "Date=");
                    synchItem.setSynchDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE)));
                    Log.i("dolphin", "summ=");
                    synchItem.setSynchSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
                    Log.i("dolphin", "Ddetail=");
                    synchItem.setSynchDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS)));
                }
            }
            Log.i("dolphin", "after Ddetail=");

            return synchItem;
        }

        public Event findEvent(long eventId) {
            Log.i("dolphin", "find event sql=before");
            open();
//            String sqlCmd = "select synchDate, synchSummary, SynchDetail fromm synchItems where synchId = '" + synchItem.getSynchId() + "'";
            String sqlCmd = "select eventId, eventDate, eventSummary, eventDetails from " + SynchronicityDBOpenHelper.TABLE_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = '" + eventId + "';";
            Log.i("dolphin", "find sql=" + sqlCmd);
            Cursor cursor = database.rawQuery(sqlCmd, null);
            Event event = new Event();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Log.i("dolphin", "id=");
                    event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
                    Log.i("dolphin", "Date=");
                    event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                    Log.i("dolphin", "summ=");
                    event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                    Log.i("dolphin", "Ddetail=");
                    event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
                }
            }
            Log.i("dolphin", "after Ddetail=");

            return event;
        }

        public List<SynchItem> delete(SynchItem synchItem) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchItem.getSynchId() + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<SynchItem> synchItems = findAllSynchItems();
            return synchItems;
        }

        public List<SynchItem> deleteSynchItem(long synchId) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchId + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<SynchItem> synchItems = findAllSynchItems();
            return synchItems;
        }

        public void deleteEvent(long i) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = '" + i + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<SynchItem> synchItems = findAllSynchItems();

        }

        public void deleteSynchEventEventOrphans(long eventId){
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID + " = '" + eventId + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
        }

        public void deleteSynchEventSynchOrphans(long synchId){
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID + " = '" + synchId + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
        }


        public void deleteVerb(long i) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_VERBS + " where " + SynchronicityDBOpenHelper.COLUMN_VERB_ID + " = '" + i + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<Verb> verbs = findAllVerbs();

        }

        public void deleteThing(long i) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_THINGS + " where " + SynchronicityDBOpenHelper.COLUMN_THING_ID + " = '" + i + "';";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<Thing> things = findAllThings();

        }

        public void deleteNote(long i) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_NOTES + " where " + SynchronicityDBOpenHelper.COLUMN_NOTE_ID + " = '" + i + "';";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<Thing> things = findAllThings();

        }


        public List<SynchItem> findAllSynchItems() {

            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS, allSynchItemColumns,
                    null, null, null, null, null);
            List<SynchItem> synchItems = synchItemCursorToList(cursor);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            return synchItems;
        }

        public List<SynchItemEvent> findAllSynchItemEvents() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
            String sqlCmd="select * from synchItemEvents;";
            Cursor cursor = database.rawQuery(sqlCmd, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " se rows");
            List<SynchItemEvent> synchItemEvents = synchItemEventCursorToList(cursor);
            return synchItemEvents;
        }

        public List<Note> findAllNotes() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
            String sqlCmd="select * from notes;";
            Log.i("dolphin", "Dolphi");
            open();
            Cursor cursor = database.rawQuery(sqlCmd,null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " note rows");
            List<Note> notes = noteCursorToList(cursor);
            return notes;
        }

        public List<Note> findAllLinkedNotes(long eventId) {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
            String sqlCmd="select * from notes where fkEventId = "+eventId+";";
            Log.i("dolphin", "Dolphiwnate");
            open();
            Cursor cursor = database.rawQuery(sqlCmd,null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " note rows");
            List<Note> notes = noteCursorToList(cursor);
            return notes;
        }

        public List<Verb> findAllVerbs() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
            String sqlCmd="select * from verbs;";
            Log.i("dolphin", "Dolphi");
            open();
            Cursor cursor = database.rawQuery(sqlCmd,null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " verb rows");
            List<Verb> verbs = verbCursorToList(cursor);
            return verbs;
        }

        public List<Thing> findAllThings() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
            String sqlCmd="select * from things;";
            Log.i("dolphin", "Dolphinoo");
            open();
            Cursor cursor = database.rawQuery(sqlCmd,null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " things rows");
            List<Thing> things = thingCursorToList(cursor);
            return things;
        }

        public List<Event> findAllEvents() {

            Log.i("dolphin", "Dolphin?? ");
            open();
//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_EVENTS, allEventColumns,
//                    null, null, null, null, null);

            String sqlCmd = "select * from events order by eventDate;";
            Cursor cursor = database.rawQuery(sqlCmd, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            List<Event> events = eventAllCursorToList(cursor);
            return events;
        }

        public List<Ignore> findAllIgnores() {

            Log.i("dolphin", "Dolphin?? ");
            open();
            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_IGNORES, allIgnoresColumns,
                    null, null, null, null, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            List<Ignore> ignores = ignoreCursorToList(cursor);
            return ignores;
        }



        public List<Event> findAllLinkedEvents(long synchId) {

            Log.i("dolphin", "Dolphin?? ");
            String sqlCmd = "select synchItemEvents.seEventId, events.eventDate, events.eventSummary, events.eventDetails "+
                    "from synchItemEvents LEFT JOIN events ON synchItemEvents.seEventId = events.eventId  "+
                    "WHERE synchItemEvents.seSynchId = "+ synchId +";";
            Log.i("dolphin", "Dolphin?? ");


            Log.i("dolphinu", "sqlCmd=" + sqlCmd);
            Cursor cursor = database.rawQuery(sqlCmd, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            List<Event> events = eventCursorToList(cursor);
            return events;
        }

        public boolean findLinkedTogetherEvents(long leftEventId,long rightEventId) {

            boolean found=false;

            String sqlCmd = "select synchItemEvents.sesynchId FROM synchItemEvents "+
                    "WHERE seEventId = " + leftEventId + ";";
            Log.i("dolphin","sqlCmd left="+sqlCmd);
            open();
            Cursor leftCursor = database.rawQuery(sqlCmd, null);
            if (leftCursor.getCount() > 0) {
                sqlCmd = "select synchItemEvents.sesynchId FROM synchItemEvents "+
                        "WHERE seEventId = " + rightEventId + ";";
                Log.i("dolphin","sqlCmd right="+sqlCmd);
                Cursor rightCursor = database.rawQuery(sqlCmd,null);
                if (rightCursor.getCount() > 0) {
                            while (leftCursor.moveToNext()) {
                                while (rightCursor.moveToNext()) {
                                    if (leftCursor.getLong(0) == rightCursor.getLong(0)) {
                                        Log.i("dolphin",(leftCursor.getLong(0)+"  =  "+rightCursor.getLong(0)));
                                        Log.i("dolphin","while left and right cursor equal="+leftCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)+"  =?  "+rightCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID));
                                        found = true;
                                    }
                                }
                            }
                        }
            }
            return found;
        }


        public boolean findLinkedEvent(long synchId,long eventId) {

            Log.i("dolphin", "Dolphin?? ");
            String sqlCmd = "select synchItemEvents.seEventId, events.eventDate, events.eventSummary, events.eventDetails "+
                    "WHERE synchItemEvents.seSynchId = "+ synchId +
                    " AND synchItemEvents.seEventId = "+ eventId +";";
            Log.i("dolphin", "Dolphin?? ");


            Log.i("dolphinu", "sqlCmd=" + sqlCmd);
            Cursor cursor = database.rawQuery(sqlCmd, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        }

        public SynchItemEvent create(SynchItemEvent seEvent) {
            Log.i("dolphinv", "TOP OF CREATE seEVENT");

            String sqlCmd = " insert into synchItemEvents "+
                    "(seSynchId, seEventId) " +
                    "values ('"+
                    seEvent.getSeSynchId()+"', '"+ seEvent.getSeEventId()+"');";
            Log.i("dolphin","sqlCmd="+sqlCmd);
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
            return seEvent;
        }

        private List<SynchItem> synchItemCursorToList(Cursor cursor) {
            List<SynchItem> synchItems = new ArrayList<SynchItem>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SynchItem synchItem = new SynchItem();
                    synchItem.setSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID)));
                    synchItem.setSynchSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
                    synchItem.setSynchDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS)));
                    synchItems.add(synchItem);
                    Log.i("dolphin", "cursorToList synch item=" + synchItem.getSynchId());
                }
            }
            return synchItems;
        }

        private List<SynchItemEvent> synchItemEventCursorToList(Cursor cursor) {
            List<SynchItemEvent> synchItemEvents = new ArrayList<SynchItemEvent>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SynchItemEvent synchItemEvent = new SynchItemEvent();
                    synchItemEvent.setSeSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)));
                    synchItemEvent.setSeEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID)));
                    synchItemEvents.add(synchItemEvent);
                    Log.i("dolphin", "cursorToList synch item event =" + synchItemEvent.getSeSynchId());
                }
            }
            return synchItemEvents;
        }

        private List<Verb> verbCursorToList(Cursor cursor) {
            List<Verb> verbs = new ArrayList<Verb>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Verb verb = new Verb();
                    verb.setVerbId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_ID)));
                    verb.setVerbName(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_NAME)));
                    verb.setVerbAppliesTo(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO)));
                    verbs.add(verb);
                }
            }
            return verbs;
        }

        private List<Thing> thingCursorToList(Cursor cursor) {
            List<Thing> things = new ArrayList<Thing>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Thing thing = new Thing();
                    thing.setThingId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_ID)));
                    thing.setThingName(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_NAME)));
                    thing.setThingUse(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_USE)));
                    things.add(thing);
                }
            }
            return things;
        }

        private List<Note> noteCursorToList(Cursor cursor) {
            List<Note> notes = new ArrayList<Note>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Note note = new Note();
                    note.setNoteId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_ID)));
                    note.setNotePerson(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON)));
                    note.setNoteInfo(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_INFO)));
                    notes.add(note);
                }
            }
            return notes;
        }

        private List<Event> eventCursorToList(Cursor cursor) {
            List<Event> events = new ArrayList<Event>();
            if (cursor.getCount() > 0) {
                Log.i("dolphin", "cursorToList synch item???=");
                while (cursor.moveToNext()) {
                    Event event = new Event();
                    event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID)));
                    event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                    event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                    event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
                    events.add(event);
                    Log.i("dolphin", "cursorToList synch item=" + event.getEventId());
                    Log.i("dolphin", "cursorToList synch item=" + event.getEventSummary());
                    Log.i("dolphin", "cursorToList synch item=" + event.getEventDetails());

                }
            }
            return events;
        }
        private List<Ignore> ignoreCursorToList(Cursor cursor) {
            List<Ignore> ignores = new ArrayList<Ignore>();
            if (cursor.getCount() > 0) {
                Log.i("dolphin", "cursorToList synch item???=");
                while (cursor.moveToNext()) {
                    Ignore ignore = new Ignore();
                    ignore.setWord(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_WORD)));
                    ignores.add(ignore);
                }
            }
            return ignores;
        }


        private List<Event> eventAllCursorToList(Cursor cursor) {
            List<Event> events = new ArrayList<Event>();
            if (cursor.getCount() > 0) {
                Log.i("dolphin", "cursorToList synch item???=");
                while (cursor.moveToNext()) {
                    Event event = new Event();
                    event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
                    event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                    event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                    event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
                    events.add(event);
                    Log.i("dolphin", "cursorToList synch item??????=");

                }
            }
            return events;
        }

        public boolean add(SynchItem synchItem) {
            Log.i("dolphino", "top of addProduct nameB= ");
            open();
            ContentValues values = new ContentValues();
//            values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, synchItem.getSynchId());
            open();
            synchItem = create(synchItem);
            String thisItem = synchItem.getSynchDetails();
            Log.i("dolphino", "top of addProduct name= "+thisItem);
//        long result = database.insert(SynchronicityDBOpenHelper.TABLE_PRODUCTS, null, values);
//        close();

            return (synchItem.getSynchId() != -1);
        }
        public long add(Event event) {
            Log.i("dolphino", "top of addProduct nameB= ");
            open();
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_ID, event.getEventId());
            open();
            event = create(event);
            long thisItem = event.getEventId();
            Log.i("dolphino", "top of addProduct id= " + thisItem);
            return (event.getEventId());
        }

        public long add(Verb verb) {
            Log.i("dolphino", "top of addProduct nameB= ");
            open();
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_ID, verb.getVerbId());
            open();
            verb = create(verb);
            long thisItem = verb.getVerbId();
            Log.i("dolphino", "top of addProduct id= "+thisItem);
            return (verb.getVerbId());
        }

        public String add(Ignore ignore) {
            Log.i("dolphino", "top of addProduct nameB= ");
            open();
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_WORD, ignore.getWord());
            open();
            ignore = create(ignore);
            String thisItem = ignore.getWord();
            close();
            open();
            Log.i("dolphino", "top of addProduct id= "+thisItem);
            return (ignore.getWord());
        }
    }

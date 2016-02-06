package com.mind.oceanic.the.synchronicity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.mind.oceanic.the.synchronicity.model.SynchItem;
import com.mind.oceanic.the.synchronicity.model.SynchItemEvent;

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
            if (synchItem.getSynchDetails() == null) {
                Log.i("dolphinv", "null synch item");
            } else {
                ContentValues values = new ContentValues();
                values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY, synchItem.getSynchSummary());
                values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS, synchItem.getSynchDetails());
                long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS, null, values);
                synchItem.setSynchId(insertid);
            }
            return synchItem;
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

        public boolean update(SynchItem synchItem) {
            String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " Set " + SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY + " = '" + synchItem.getSynchSummary() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DETAILS + " = '" + synchItem.getSynchDetails() + "' where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = " + synchItem.getSynchId() + ";";
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
            Log.i("dolphin","after Ddetail=");

            return synchItem;
        }

        public List<SynchItem> delete(SynchItem synchItem) {
            String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchItem.getSynchId() + "'";
            Log.i("dolphin", "delete sql=" + sqlCmd);
            database.execSQL(sqlCmd);
            List<SynchItem> synchItems = findAllSynchItems();
            return synchItems;
        }

        public List<SynchItem> findAllSynchItems() {

            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS, allSynchItemColumns,
                    null, null, null, null, null);
            List<SynchItem> synchItems = synchItemCursorToList(cursor);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            return synchItems;
        }

        public List<Event> findAllEvents() {

            Log.i("dolphin", "Dolphin?? ");
            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_EVENTS, allEventColumns,
                    null, null, null, null, null);
            Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
            List<Event> events = eventCursorToList(cursor);
            return events;
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

        public SynchItemEvent create(SynchItemEvent seEvent) {
            Log.i("dolphinv", "TOP OF CREATE seEVENT");

            String sqlCmd = " insert into synchItemEvents "+
                    "(seSynchId, seEventId) " +
                    "values ('"+
                    seEvent.getSeSynchId()+"', '"+ seEvent.getSeEventId()+"');";
            Log.i("dolphin","sqlCmd="+sqlCmd);
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
//            ContentValues values = new ContentValues();
////            values.put(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID, seEvent.getSeSynchId());
//            values.put(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID, seEvent.getSeEventId());
//            database.insert(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, null, values);
//            String sqlCmd = "Select * from SynchItemEvent";
//            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, null, values);
//            seEvent.setSeSynchId(insertid);
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

//        private List<Event> eventCursorToList(Cursor cursor) {
//            List<Event> events = new ArrayList<Event>();
//            if (cursor.getCount() > 0) {
//                Log.i("dolphin", "cursorToList synch item???=");
//                while (cursor.moveToNext()) {
//                    Event event = new Event();
//                    event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
//                    event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
//                    event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
//                    events.add(event);
//                    Log.i("dolphin", "cursorToList synch item=" + event.getEventId());
//                }
//            }
//            return events;
//        }


        public boolean add(SynchItem synchItem) {
            Log.i("dolphino", "top of addProduct nameB= ");
            open();
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, synchItem.getSynchId());
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
            Log.i("dolphino", "top of addProduct id= "+thisItem);
            return (event.getEventId());
        }
    }

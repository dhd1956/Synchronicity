package com.mind.oceanic.the.synchronicity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dave on 1/19/16.
 */
public class SynchronicityDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Synchronicity.db";
    public static final int DATABASE_VERSION = 16;

    public static final String TABLE_SYNCH_ITEMS = "synchItems";
    public static final String COLUMN_SYNCH_ID = "synchId";
    public static final String COLUMN_SYNCH_DATE = "synchDate";
    public static final String COLUMN_SYNCH_SUMMARY = "synchSummary";
    public static final String COLUMN_SYNCH_DETAILS = "synchDetails";

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_ID = "eventId";
    public static final String COLUMN_EVENT_DATE = "eventDate";
    public static final String COLUMN_EVENT_SUMMARY = "eventSummary";
    public static final String COLUMN_EVENT_DETAILS = "eventDetails";

    public static final String TABLE_SYNCH_ITEM_EVENTS = "synchItemEvents";
    public static final String COLUMN_SE_SYNCH_ID = "seSynchId";
    public static final String COLUMN_SE_EVENT_ID = "seEventId";

    public static final String TABLE_EVENT_FILTERS = "eventFilters";
    public static final String COLUMN_FILTER_ID = "eventFilterId";
    public static final String COLUMN_EVENT_FILTER_NAME = "eventFilterName";

    public static final String TABLE_PERSONS = "persons";
    public static final String COLUMN_PERSON_ID = "personId";
    public static final String COLUMN_PERSON_FIRST_NAME = "personFirstName";
    public static final String COLUMN_PERSON_LAST_NAME = "personLastName";
    public static final String COLUMN_PERSON_EMAIL = "personEmail";
    public static final String COLUMN_PERSON_MAIN_PHONE = "personMainPhone";
    public static final String COLUMN_PERSON_ADDRESS = "personAddress";
    public static final String COLUMN_PERSON_CITY = "personCity";
    public static final String COLUMN_PERSON_PROVINCE = "personProvince";
    public static final String COLUMN_PERSON_COUNTRY = "personCountry";

    public static final String TABLE_PLACES = "places";
    public static final String COLUMN_PLACE_ID = "placeId";
    public static final String COLUMN_PLACE_NAME = "placeName";
    public static final String COLUMN_PLACE_EMAIL = "placeEmail";
    public static final String COLUMN_PLACE_MAIN_PHONE = "placeMainPhone";
    public static final String COLUMN_PLACE_ADDRESS = "placeAddress";
    public static final String COLUMN_PLACE_CITY = "placeCity";
    public static final String COLUMN_PLACE_PROVINCE = "placeProvince";
    public static final String COLUMN_PLACE_COUNTRY = "placeCountry";

    public static final String TABLE_THINGS = "things";
    public static final String COLUMN_THING_ID = "thingId";
    public static final String COLUMN_THING_NAME = "thingName";
    public static final String COLUMN_THING_USE = "thingUse";

    public static final String TABLE_VERBS = "verbs";
    public static final String COLUMN_VERB_ID = "verbId";
    public static final String COLUMN_VERB_NAME = "verbName";
    public static final String COLUMN_VERB_APPLIES_TO = "verbAppliesTo";

    public static final String TABLE_IGNORES = "ignores";
    public static final String COLUMN_WORD = "word";

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_NOTE_ID = "noteId";
    public static final String COLUMN_FK_EVENT_ID = "fkEventId";
    public static final String COLUMN_NOTE_PERSON = "notePerson";
    public static final String COLUMN_NOTE_INFO = "noteInfo";

    private static final String TABLE_SYNCH_ITEMS_CREATE =
            "CREATE TABLE " + TABLE_SYNCH_ITEMS + " (" +
                    COLUMN_SYNCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SYNCH_DATE + " TEXT, " +
                    COLUMN_SYNCH_SUMMARY + " TEXT, " +
                    COLUMN_SYNCH_DETAILS + " TEXT " +
                    ")";

    private static final String TABLE_EVENTS_CREATE =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EVENT_DATE + " TEXT, " +
                    COLUMN_EVENT_SUMMARY + " TEXT, " +
                    COLUMN_EVENT_DETAILS + " TEXT " +
                    ")";

    private static final String TABLE_SYNCH_ITEM_EVENTS_CREATE =
            "CREATE TABLE " + TABLE_SYNCH_ITEM_EVENTS + " (" +
                    COLUMN_SE_SYNCH_ID + " INTEGER NOT NULL, " +
                    COLUMN_SE_EVENT_ID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY ("+COLUMN_SE_SYNCH_ID+", "+COLUMN_SE_EVENT_ID+")"+
                    ")";

    private static final String TABLE_VERBS_CREATE =
            "CREATE TABLE " + TABLE_VERBS + " (" +
                    COLUMN_VERB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VERB_NAME + " TEXT, " +
                    COLUMN_VERB_APPLIES_TO + " TEXT " +
                    ")";

    private static final String TABLE_PERSONS_CREATE =
            "CREATE TABLE " + TABLE_PERSONS + " (" +
                    COLUMN_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PERSON_FIRST_NAME + " TEXT, " +
                    COLUMN_PERSON_LAST_NAME + " TEXT, " +
                    COLUMN_PERSON_MAIN_PHONE + " TEXT, " +
                    COLUMN_PERSON_EMAIL + " TEXT, " +
                    COLUMN_PERSON_ADDRESS + " TEXT, " +
                    COLUMN_PERSON_CITY + " TEXT, " +
                    COLUMN_PERSON_PROVINCE + " TEXT, " +
                    COLUMN_PERSON_COUNTRY + " TEXT " +
                     ")";

    private static final String TABLE_PLACES_CREATE =
            "CREATE TABLE " + TABLE_PLACES + " (" +
                    COLUMN_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PLACE_NAME + " TEXT, " +
                    COLUMN_PLACE_EMAIL + " TEXT, " +
                    COLUMN_PLACE_MAIN_PHONE + " TEXT, " +
                    COLUMN_PLACE_ADDRESS + " TEXT, " +
                    COLUMN_PLACE_CITY + " TEXT, " +
                    COLUMN_PLACE_PROVINCE + " TEXT, " +
                    COLUMN_PLACE_COUNTRY + " TEXT " +
                    ")";

    private static final String TABLE_THINGS_CREATE =
            "CREATE TABLE " + TABLE_THINGS + " (" +
                    COLUMN_THING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_THING_NAME + " TEXT, " +
                    COLUMN_THING_USE + " TEXT " +
                    ")";

    private static final String TABLE_IGNORES_CREATE =
            "CREATE TABLE " + TABLE_IGNORES + " (" +
                    COLUMN_WORD + " STRING PRIMARY KEY)";

    private static final String TABLE_NOTES_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FK_EVENT_ID + " INTEGER, " +
                    COLUMN_NOTE_PERSON + " TEXT, " +
                    COLUMN_NOTE_INFO + " TEXT )";

    public SynchronicityDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("dolphinv", "prior created synchItem table");
//        db.execSQL(TABLE_SYNCH_ITEMS_CREATE);
//        db.execSQL(TABLE_EVENTS_CREATE);
////        db.execSQL(TABLE_SYNCH_ITEM_EVENTS_CREATE);
        db.execSQL(TABLE_IGNORES_CREATE);
        db.execSQL(TABLE_PERSONS_CREATE);
        db.execSQL(TABLE_NOTES_CREATE);
//        Log.i("dolphinv", "created synchItem table");
        db.execSQL(TABLE_PLACES_CREATE);
        db.execSQL(TABLE_THINGS_CREATE);
        db.execSQL(TABLE_VERBS_CREATE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNCH_ITEMS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNCH_ITEM_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IGNORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERBS);
        Log.i("dolphin","dropping");
        onCreate(db);

        Log.i("dolphinv", "Database has been upgraded from " +
        oldVersion + " to " + newVersion);
    }
}



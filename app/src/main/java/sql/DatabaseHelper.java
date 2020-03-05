package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

import models.Exercise;
import models.Set;
import models.User;
import models.Workout;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_WEIGHT = "user_weight";
    private static final String COLUMN_USER_HEIGHT = "user_height";
    private static final String COLUMN_USER_AGE = "user_age";

    // Workout table name
    private static final String TABLE_WORKOUT = "workout";

    // Workout Table Columns names
    private static final String COLUMN_WORKOUT_ID = "workout_id";
    private static final String COLUMN_WORKOUT_NAME = "workout_name";
    private static final String COLUMN_WORKOUT_INFO = "workout_info";
    private static final String COLUMN_WORKOUT_AUTHOR = "workout_author";

    // Exercise table name
    private static final String TABLE_EXERCISE = "exercise";

    // Exercise Table Columns names
    private static final String COLUMN_EXERCISE_ID = "exercise_id";
    private static final String COLUMN_EXERCISE_NAME = "exercise_name";
    private static final String COLUMN_EXERCISE_INFO = "exercise_info";
    private static final String COLUMN_EXERCISE_CATEGORY = "exercise_category";
    private static final String COLUMN_EXERCISE_AUTHOR = "exercise_author";
    private static final String COLUMN_EXERCISE_WORKOUT = "exercise_workout";

    // Exercise table name
    private static final String TABLE_SET = "set_table";

    // Exercise Table Columns names
    private static final String COLUMN_SET_ID = "set_id";
    private static final String COLUMN_SET_REPS = "set_reps";
    private static final String COLUMN_SET_WEIGHT = "set_weight";
    private static final String COLUMN_SET_LIFTER = "set_lifter";
    private static final String COLUMN_SET_EXERCISE = "set_exercise";

    // create User table SQL query
    private String CREATE_USER_TABLE = " CREATE TABLE " + TABLE_USER
            + " ( "
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT, "
            + COLUMN_USER_WEIGHT + " FLOAT, "
            + COLUMN_USER_HEIGHT + " FLOAT, "
            + COLUMN_USER_AGE + " INT"
            + " ) ";

    // create Workout table SQL query
    private String CREATE_WORKOUT_TABLE = " CREATE TABLE " + TABLE_WORKOUT
            + " ( "
            + COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_WORKOUT_NAME + " TEXT, "
            + COLUMN_WORKOUT_INFO + " TEXT, "
            + COLUMN_WORKOUT_AUTHOR + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_WORKOUT_AUTHOR +  ") REFERENCES " + TABLE_USER + "(" + COLUMN_WORKOUT_AUTHOR + ")"
            + " ) ";

    // create Exercise table SQL query
    private String CREATE_EXERCISE_TABLE = " CREATE TABLE " + TABLE_EXERCISE
            + " ( "
            + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXERCISE_NAME + " TEXT, "
            + COLUMN_EXERCISE_CATEGORY + " TEXT, "
            + COLUMN_EXERCISE_INFO + " TEXT, "
            + COLUMN_EXERCISE_AUTHOR + " INTEGER, "
            + COLUMN_EXERCISE_WORKOUT + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_EXERCISE_AUTHOR + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EXERCISE_AUTHOR + "), "
            + " FOREIGN KEY (" + COLUMN_EXERCISE_WORKOUT + ") REFERENCES " + TABLE_WORKOUT + "(" + COLUMN_EXERCISE_WORKOUT + ")"
            + " ) ";

    // create Set table SQL query
    private String CREATE_SET_TABLE = " CREATE TABLE " + TABLE_SET
            + " ( "
            + COLUMN_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SET_REPS + " INTEGER, "
            + COLUMN_SET_WEIGHT + " FLOAT, "
            + COLUMN_SET_LIFTER + " INTEGER, "
            + COLUMN_SET_EXERCISE + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_SET_LIFTER+ ") REFERENCES " + TABLE_USER + "(" + COLUMN_SET_LIFTER + "), "
            + " FOREIGN KEY (" + COLUMN_SET_EXERCISE + ") REFERENCES " + TABLE_EXERCISE + "(" + COLUMN_SET_EXERCISE + ")"
            + " ) ";

    // drop User table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // drop Workout table sql query
    private String DROP_WORKOUT_TABLE = "DROP TABLE IF EXISTS " + TABLE_WORKOUT;

    // drop Exercise table sql query
    private String DROP_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXERCISE;

    // drop Set table sql query
    private String DROP_SET_TABLE = "DROP TABLE IF EXISTS " + TABLE_SET;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_SET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop Tables if exists
        db.execSQL(DROP_SET_TABLE);
        db.execSQL(DROP_EXERCISE_TABLE);
        db.execSQL(DROP_WORKOUT_TABLE);
        db.execSQL(DROP_USER_TABLE);

        // Create table again
        onCreate(db);
    }

    // ******************************* User Table Methods ***********************************
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_WEIGHT, user.getWeight());
        values.put(COLUMN_USER_HEIGHT, user.getHeight());
        values.put(COLUMN_USER_AGE, user.getAge());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_WEIGHT,
                COLUMN_USER_HEIGHT,
                COLUMN_USER_AGE
        };

        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, // Table to query
                columns,        //columns to return
                null,   //columns for WHERE clause
                null,   // The values for the WHERE clause
                null,       // group the rows
                null,        // filter by row groups
                sortOrder);         // The sort order

        // Transversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_WEIGHT))));
                user.setHeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_HEIGHT))));
                user.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AGE))));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_WEIGHT, user.getWeight());
        values.put(COLUMN_USER_HEIGHT, user.getHeight());
        values.put(COLUMN_USER_AGE, user.getAge());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public User findUserByEmail(String email) {
        User user = new User();
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStatement = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'";

        cursor = db.rawQuery(sqlStatement, null);
        cursor.moveToFirst();
        user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
        user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        user.setWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_WEIGHT))));
        user.setHeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_HEIGHT))));
        user.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AGE))));

        return user;
    }

    public User findUserById(int id) {
        User user = new User();
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStatement = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'";

        cursor = db.rawQuery(sqlStatement, null);
        cursor.moveToFirst();
        user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
        user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        user.setWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_WEIGHT))));
        user.setHeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_USER_HEIGHT))));
        user.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AGE))));

        return user;
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user with condition
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns, // columns ro return
                selection,  // columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,   // group the rows
                null,   // filter by row groups
                null);  // The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        //selection arguments
        String[] selectionArgs = {email, password};

        //query user table with conditions
        Cursor cursor = db.query(TABLE_USER,    // Table to query
                columns, // columns to return
                selection,  // columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,   // group the rows
                null,   // filter by row groups
                null);  // The sort order
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        return cursorCount > 0;
    }


    // ******************************* Workout Table Methods ***********************************
    public void addWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_NAME, workout.getName());
        values.put(COLUMN_WORKOUT_INFO, workout.getInfo());
        values.put(COLUMN_WORKOUT_AUTHOR, workout.getAuthor());

        // Inserting Row
        db.insert(TABLE_WORKOUT, null, values);
        db.close();
    }

    public void updateWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_NAME, workout.getName());
        values.put(COLUMN_WORKOUT_INFO, workout.getInfo());
        values.put(COLUMN_WORKOUT_AUTHOR, workout.getAuthor());

        // updating row
        db.update(TABLE_WORKOUT, values, COLUMN_WORKOUT_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});
        db.close();
    }

    public void deleteWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete workout record by id
        db.delete(TABLE_WORKOUT, COLUMN_WORKOUT_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});

        //////// need to delete all exercises in this workout ????????????

        db.close();
    }

    // ******************************* Exercise Table Methods ***********************************
    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, exercise.getName());
        values.put(COLUMN_EXERCISE_INFO, exercise.getInfo());
        values.put(COLUMN_EXERCISE_CATEGORY, exercise.getCategory());
        values.put(COLUMN_EXERCISE_AUTHOR, exercise.getAuthor());
        values.put(COLUMN_EXERCISE_WORKOUT, exercise.getWorkout());

        // Inserting Row
        db.insert(TABLE_EXERCISE, null, values);
        db.close();
    }

    public void updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, exercise.getName());
        values.put(COLUMN_EXERCISE_INFO, exercise.getInfo());
        values.put(COLUMN_EXERCISE_CATEGORY, exercise.getCategory());
        values.put(COLUMN_EXERCISE_AUTHOR, exercise.getAuthor());
        values.put(COLUMN_EXERCISE_WORKOUT, exercise.getWorkout());

        // updating row
        db.update(TABLE_EXERCISE, values, COLUMN_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});
        db.close();
    }

    public void deleteExerciseFromWorkout(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete exercise record by id
        db.delete(TABLE_EXERCISE, COLUMN_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});

        // no need to delete logs (sets) because an exercise can be performed without being in a
        // workout

        db.close();
    }

    public void deleteExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete exercise record by id
        db.delete(TABLE_EXERCISE, COLUMN_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});

        // no need to delete logs (sets) because an exercise can be performed without being in a
        // workout

        db.close();
    }


    // ******************************* Set Table Methods ***********************************
    public void addSet(Set set) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SET_REPS, set.getReps());
        values.put(COLUMN_SET_WEIGHT, set.getWeight());
        values.put(COLUMN_SET_EXERCISE, set.getExercise());
        values.put(COLUMN_SET_LIFTER, set.getLifter());

        // Inserting Row
        db.insert(TABLE_SET, null, values);
        db.close();
    }

    public void updateSet(Set set) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SET_REPS, set.getReps());
        values.put(COLUMN_SET_WEIGHT, set.getWeight());
        values.put(COLUMN_SET_EXERCISE, set.getExercise());
        values.put(COLUMN_SET_LIFTER, set.getLifter());

        // updating row
        db.update(TABLE_SET, values, COLUMN_SET_ID + " = ?",
                new String[]{String.valueOf(set.getId())});
        db.close();
    }

    public void deleteSet(Set set) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete set record by id
        db.delete(TABLE_SET, COLUMN_SET_ID + " = ?",
                new String[]{String.valueOf(set.getId())});

        db.close();
    }
}



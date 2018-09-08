package example.com.mydbmv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 * </pre>
 */

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "crud.db";


    MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here,it will be invoked after Database is created
        String CREATE_TABLE_MOVIE = "CREATE TABLE " + MovieInfo.TABLE_NAME + "("
                + MovieInfo.KEY_ID + " INTEGER PRIMARY KEY ,"
                + MovieInfo.KEY_TITLE + " TEXT, "
                + MovieInfo.KEY_RATING + " TEXT, "
                + MovieInfo.KEY_YEAR + " TEXT, "
                + MovieInfo.KEY_IMAGE_URL + " TEXT, "
                + MovieInfo.KEY_VIDEO_URL + " TEXT, "
                + MovieInfo.KEY_CONTENT + " TEXT )";
        db.execSQL(CREATE_TABLE_MOVIE);
        //to create a table

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + MovieInfo.TABLE_NAME);
        // Create tables again
        onCreate(db);

    }
    //invoked when database file is updated
}




package example.com.mydbmv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
/**
 *<pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 *</pre>
 */
public class MovieRepo {
    private  MoviesDBHelper dbHelper;

    MovieRepo(Context context) {
        dbHelper = new MoviesDBHelper(context);
        Log.i("", "");
    }

    /**
     *
     * @param movieInfo the data to be inserted
     */
    public void insertData(MovieInfo movieInfo) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieInfo.KEY_TITLE, movieInfo.title);
        values.put(MovieInfo.KEY_CONTENT, movieInfo.content);
        values.put(MovieInfo.KEY_ID, movieInfo.id);
        values.put(MovieInfo.KEY_RATING, movieInfo.rating);
        values.put(MovieInfo.KEY_IMAGE_URL, movieInfo.image_url);
        values.put(MovieInfo.KEY_VIDEO_URL, movieInfo.video_url);
        values.put(MovieInfo.KEY_YEAR, movieInfo.year);
        // Inserting Row
        db.insert(MovieInfo.TABLE_NAME, null, values);
        // Closing database connection
        db.close();

    }

    /**
     *
     * @param movieInfo data to be updated in DetailActivity
     * @param id according to the id of the movie to update the row
     */
    public void update(MovieInfo movieInfo, String id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieInfo.KEY_CONTENT, movieInfo.content);
        db.update(MovieInfo.TABLE_NAME, values, MovieInfo.KEY_ID + "= ?", new String[]{id});
        // Closing database connection
        db.close();
    }


    public ArrayList<MovieInfo> getSaveInfoList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                MovieInfo.KEY_ID + "," +
                MovieInfo.KEY_CONTENT + "," +
                MovieInfo.KEY_IMAGE_URL + "," +
                MovieInfo.KEY_VIDEO_URL + "," +
                MovieInfo.KEY_YEAR + "," +
                MovieInfo.KEY_TITLE + "," +
                MovieInfo.KEY_RATING +
                " FROM " + MovieInfo.TABLE_NAME;
        ArrayList<MovieInfo> saveInfoList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MovieInfo saveInfo = new MovieInfo();
                saveInfo.setId(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_ID)));
                saveInfo.setContent(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_CONTENT)));
                saveInfo.setImage_url(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_IMAGE_URL)));
                saveInfo.setRating(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_RATING)));
                saveInfo.setTitle(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_TITLE)));
                saveInfo.setVideo_url(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_VIDEO_URL)));
                saveInfo.setYear(cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_YEAR)));
                saveInfoList.add(saveInfo);
                } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return saveInfoList;

    }

    public MovieInfo getSaveInfoById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                MovieInfo.KEY_ID + "," +
                MovieInfo.KEY_CONTENT + "," +
                MovieInfo.KEY_IMAGE_URL + "," +
                MovieInfo.KEY_VIDEO_URL + "," +
                MovieInfo.KEY_YEAR + "," +
                MovieInfo.KEY_TITLE + "," +
                MovieInfo.KEY_RATING +
                " FROM " + MovieInfo.TABLE_NAME
                + " WHERE " +
                MovieInfo.KEY_ID + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string
        MovieInfo saveInfo = new MovieInfo();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                saveInfo.id = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_ID));
                saveInfo.title = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_TITLE));
                saveInfo.rating = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_RATING));
                saveInfo.image_url = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_IMAGE_URL));
                saveInfo.video_url = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_VIDEO_URL));
                saveInfo.content = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_CONTENT));
                saveInfo.year = cursor.getString(cursor.getColumnIndex(MovieInfo.KEY_YEAR));

            } while (cursor.moveToNext());
        }

        assert cursor != null;
        cursor.close();
        db.close();
        return saveInfo;
    }
}
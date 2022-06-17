package com.example.imdb.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.Entity.Result;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private final static String databaseName = "MoviesDB";
    private final static int databaseVersion = 1;
    private String MOVIE_TABLE = "movies";
    private static DataBase dbInstance = null;

    public DataBase(Context context){
        super(context,databaseName,null,databaseVersion);
    }

    public synchronized static DataBase getInstance(Context context){

        if(dbInstance == null){
            dbInstance = new DataBase(context.getApplicationContext());
        }

        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String createQuery = "CREATE TABLE " + MOVIE_TABLE + " ("
                + " userID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " id TEXT,"
                + " resultType TEXT,"
                + " image TEXT,"
                + " title TEXT,"
                + " description TEXT"
                + " )";

        sqLiteDatabase.execSQL(createQuery);
    }

    public void addNewMovie(String id, String resultType, String image, String title, String description){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("id",id);
        contentValues.put("resultType",resultType);
        contentValues.put("image",image);
        contentValues.put("title",title);
        contentValues.put("description",description);
        sqLiteDatabase.insert(MOVIE_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public ArrayList<Result> getMovieList(){
        ArrayList<Result> movieList = new ArrayList<Result>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(MOVIE_TABLE, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                movieList.add(
                        new Result(
                                cursor.getString(cursor.getColumnIndex("id")),
                                cursor.getString(cursor.getColumnIndex("resultType")),
                                cursor.getString(cursor.getColumnIndex("image")),
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("description"))
                        )
                );
            }
            while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return movieList;
    }

    //update vs kısmı eklenecek

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        //sqLiteDatabase.execSQL("ALTER TABLE " + MOVIE_TABLE + " ADD COLUMN age Integer");
    }

    public class Movie implements Parcelable {
        private Integer id;
        private String resultType;
        private String image;
        private String title;
        private String description;

        public Movie(Integer id, String resultType, String image, String title, String description) {
            super();
            this.id = id;
            this.resultType = resultType;
            this.image = image;
            this.title = title;
            this.description = description;
        }

        protected Movie(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            resultType = in.readString();
            image = in.readString();
            title = in.readString();
            description = in.readString();
        }

        public final Creator<Movie> CREATOR = new Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (id == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(id);
            }
            parcel.writeString(resultType);
            parcel.writeString(image);
            parcel.writeString(title);
            parcel.writeString(description);
        }
    }

    @SuppressLint("Range")
    public Movie getMovie(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(MOVIE_TABLE,null,"userID= ? ",new String[]{
                String.valueOf(id) },null,null,null);

        Movie movie = null;

        if(cursor.moveToFirst()){
            new Movie(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("resultType")),
                    cursor.getString(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description"))

            );
        }

        return movie;
    }

    public void DeleteMovie(String name){
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();

        sqLiteDatabase.delete(
                MOVIE_TABLE,
                "name= ? ",
                new String[]{
                        String.valueOf(name)
                }
        );

        sqLiteDatabase.close();
    }


}

package com.contreras.certamenandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    protected Context contexto;

    public static final String DATABASE_NAME = "estudiante.db";
    public static final String TABLE_NAME = "tabla_estudiante";
    public static final String DB_TABLE_USERS = "usuarios";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOMBRE";
    public static final String COL_3 = "APELLIDO";
    public static final String COL_4 = "NOTAS";
    public static final String COL_5 = "ASIGNATURA";
    public static final String COL_6 = "USUARIO_ID";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 8);
        this.contexto = context;
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DB_TABLE_USERS+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombres TEXT NOT NULL,"+
                "apellidos TEXT NOT NULL,"+
                "email TEXT NOT NULL,"+
                "clave TEXT NOT NULL)");
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, APELLIDO TEXT, NOTAS INTEGER, ASIGNATURA TEXT, USUARIO_ID INTEGER, FOREIGN KEY(USUARIO_ID) REFERENCES "+DB_TABLE_USERS+"(id)) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE_USERS);
        onCreate(db);
    }

    public boolean insertarDatos(String nombre, String apellido, String notas, String asignatura, int usuario_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,nombre);
        contentValues.put(COL_3,apellido);
        contentValues.put(COL_4,notas);
        contentValues.put(COL_5,asignatura);
        contentValues.put(COL_6,usuario_id);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor verTodo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean actualizarDatos(String id,String nombre, String apellido, String notas, String asignatura){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,nombre);
        contentValues.put(COL_3,apellido);
        contentValues.put(COL_4,notas);
        contentValues.put(COL_5,asignatura);
        db.update(TABLE_NAME, contentValues,"ID = ?", new String[]{ id });
        return true;
    }

    public Integer borrarData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[] {id});
    }


}

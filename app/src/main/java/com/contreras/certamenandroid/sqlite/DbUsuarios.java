package com.contreras.certamenandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.contreras.certamenandroid.models.Usuario;

import java.util.ArrayList;

public class DbUsuarios extends DbHelper {

    DbHelper helper;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        helper = new DbHelper(context);
    }

    public Usuario login(String email, String clave){
        // obtener al usuario que tiene el email y clave dadas
        DbHelper helper = new DbHelper(contexto);
        SQLiteDatabase bd = helper.getReadableDatabase();
        Usuario objetoUsuario = null;
        Cursor cursor;
        cursor = bd.rawQuery("SELECT * FROM "+DB_TABLE_USERS+
                " WHERE email=? AND clave=?",new String[] {email,clave});
        // Dicha consulta tiene dos posibles resultados
        // - Nos devuelva una fila (registro) -> objeto de tipo usuario
        // - No devolverá nada
        if( cursor.moveToFirst() ){ // se puede mover al primer registro
            objetoUsuario = new Usuario();
            objetoUsuario.setId( cursor.getInt(0) );
            objetoUsuario.setNombres( cursor.getString(1) );
            objetoUsuario.setApellidos( cursor.getString(2) );
            objetoUsuario.setEmail( cursor.getString(3) );
            objetoUsuario.setClave( cursor.getString(4) );
        }
        return objetoUsuario;
    }

    public long insertar(Usuario user){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put("nombres", user.getNombres());
        datos.put("apellidos",user.getApellidos());
        datos.put("email",user.getEmail());
        datos.put("clave",user.getClave());

        long id = db.insert(DB_TABLE_USERS, null, datos);
        return id;
    }

    public ArrayList<Usuario> selectAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Usuario> arrayUsuarios = new ArrayList<>();
        Usuario user = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM "+DB_TABLE_USERS,
                null);

        if( cursor.moveToFirst() ){
            do{
                user = new Usuario();
                user.setId( cursor.getInt(0) );
                user.setNombres( cursor.getString(1) );
                user.setApellidos( cursor.getString(2) );
                user.setEmail( cursor.getString(3) );
                user.setClave( cursor.getString(4) );
                arrayUsuarios.add(user);
            }while ( cursor.moveToNext() );
        }
        return arrayUsuarios;

    }
}

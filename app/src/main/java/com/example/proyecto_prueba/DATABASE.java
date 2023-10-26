package com.example.proyecto_prueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DATABASE extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NOMBRE = "comandero.db";
    public static final String TABLE_COMANDA = "comanda";
    public static final String TABLE_COMANDA_PRODUCTO = "comandaProducto";
    public static final String TABLE_FAMILIA = "familia";
    public static final String TABLE_PRODUCTO = "producto";
    public static final String TABLE_USUARIO = "usuario";

    public DATABASE(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    public DATABASE(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COMANDA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idMesa INTEGER NOT NULL," +
                "idCamarero INTEGER NOT NULL, " +
                "Estado BOOLEAN NOT NULL)"
        );

        db.execSQL("CREATE TABLE " + TABLE_COMANDA_PRODUCTO + "(" +
                "idComanda INTEGER NOT NULL," +
                "idMesa INTEGER NOT NULL," +
                "idProducto INTEGER NOT NULL," +
                "nombreDelProducto TEXT NOT NULL," +
                "cantidadProducto INTEGER NOT NULL, " +
                "precio FLOAT NOT NULL  )");

        db.execSQL("CREATE TABLE " + TABLE_FAMILIA + "(" +
                "idFamilia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FamiliaNombre TEXT NOT NULL," +
                "Descripcion TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTO + "(" +
                "idProducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idFamlia INTEGER NOT NULL," +
                "ProductoNombre TEXT NOT NULL, " +
                "precioProducto FLOAT NOT NULL  )");

        db.execSQL("CREATE TABLE " + TABLE_USUARIO + "(" +
                "idCamarero INTEGER PRIMARY KEY AUTOINCREMENT," +
                "password TEXT NOT NULL," +
                "nombre TEXT NOT NULL, " +
                "apellidos TEXT NOT NULL," +
                "telefono INTEGER (9) NOT NULL )");

        db.execSQL("INSERT INTO " + TABLE_FAMILIA + " VALUES(NULL, \"bebida\",\"\"), (NULL, \"Entrante\",\"\"), (NULL, \"Carne\",\"\"), (NULL, \"Pescado\",\"\"), (NULL, \"Postre\",\"\")");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTO + " VALUES (NULL, 1, \"Coca cola\", 1.60), (NULL, 2, \"Pulpo\", 13), (NULL, 2, \"Nachos\", 9), (NULL, 3, \"Solomillo\", 18), (NULL, 4, \"Lubina\", 27), (NULL, 3, \"Cachopo\", 22 ) , (NULL, 5, \"Tiramisu\", 6);");
        db.execSQL("INSERT INTO " + TABLE_USUARIO + " VALUES (NULL, 1111, \"David\", \"Boo\", 111111111), (NULL, 2222, \"Anita\", \"Varela\", 222222222), (NULL, 1411, \"alicia\", \"Berasategui\", 333333333), (NULL, 4444, \"Adrian\", \"Garcia\", 444444444), (NULL, 5555, \"Toni\", \"Garcia\",555555555);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_FAMILIA);
        db.execSQL("DROP TABLE " + TABLE_PRODUCTO);
        db.execSQL("DROP TABLE " + TABLE_COMANDA);
        db.execSQL("DROP TABLE " + TABLE_COMANDA_PRODUCTO);
        onCreate(db);
    }
}

package com.example.proyecto_prueba;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QhacerActivity extends AppCompatActivity {
Integer idMesa, idCamarero, idComanda;
Button botonCrearComanda, botonAnadirComanda;
    SQLiteDatabase db;
    DATABASE conn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qhacer);
        conn = new DATABASE(getApplicationContext(),DATABASE.DATABASE_NOMBRE, null, 1);
        Bundle parametros = this.getIntent().getExtras();
        botonCrearComanda = (Button)findViewById(R.id.botonCrearComanda);
        botonAnadirComanda = (Button)findViewById(R.id.botonAnadirComanda);
        //Se recogen los argumentos de la mesa seleccionada y del camarero logueado
        idMesa = parametros.getInt("MESA");
        idCamarero = parametros.getInt("camarero");
        comandaAbierta();

    }

    public void tomarComanda(View v) {
        db = conn.getReadableDatabase();

        String[] parametros = {idMesa.toString()};
        Cursor cursor = db.rawQuery("SELECT * FROM comanda where Estado = true and idMesa = ?",parametros);
        if(cursor.getCount() == 0){
            crearComanda();
        }

        Intent i = new Intent(this, RecyclerViewActivity.class);
        i.putExtra("NumMESA", idMesa);
        i.putExtra("IdCAMARERO", idCamarero);
        i.putExtra("IdCOMANDA", idComanda);
        startActivity(i);
    }

    public void volver(View v){
        Intent i = new Intent(this, SeleccionMesaActivity.class);
        i.putExtra("IDCAMARERO", idCamarero);
        startActivity(i);
    }

    /**
     * Se realiza una consulta a la bbdd para saber si en esta mesa hay alguna comanda abierta.
     * Si la hay
     * Si no la hay
     */
    private void comandaAbierta(){
         db = conn.getReadableDatabase();

        String[] parametros = {idMesa.toString()};
        Cursor cursor = db.rawQuery("SELECT * FROM comanda where Estado = true and idMesa = ?",parametros);
        if(cursor.getCount() == 0){
            botonAnadirComanda.setVisibility(View.INVISIBLE);
            botonCrearComanda.setVisibility(View.VISIBLE);
        }else {
            cursor.moveToFirst();
            idComanda = cursor.getInt(0);
            botonCrearComanda.setVisibility(View.INVISIBLE);
            botonAnadirComanda.setVisibility(View.VISIBLE);
        }
    }

    private void crearComanda(){
        db = conn.getWritableDatabase();
        db.execSQL("INSERT INTO " + DATABASE.TABLE_COMANDA + " VALUES (null, " + idMesa + ", " + idCamarero + ",true)");
        String[] parametros = {idMesa.toString()};
        Cursor cursor = db.rawQuery("SELECT * FROM comanda where Estado = true and idMesa = ?",parametros);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }else {
            cursor.moveToFirst();
            idComanda = cursor.getInt(0);
        }
    }



}
package com.example.proyecto_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SeleccionMesaActivity extends AppCompatActivity {


Integer idCamarero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_mesa);
        Bundle parametros = this.getIntent().getExtras();
        idCamarero = parametros.getInt("IDCAMARERO");


    }

    /**
     * Recoge el valor de la mesa que se ha seleccionado, y pasa al QhacerActivity adjuntando los el id de la mesa y del camarero
     * @param v
     */
    public void siguiente(View v) {
        Integer mesa = null;
        switch(v.getId())
        {
            case R.id.m1:
                mesa = 1;
                break;
            case R.id.m2:
                mesa = 2;
                break;
            case R.id.m3:
                mesa = 3;
                break;
            case R.id.m4:
                mesa = 4;
                break;
            case R.id.m8:
                mesa = 5;
                break;
            case R.id.m7:
                mesa = 6;
                break;
            case R.id.m6:
                mesa = 7;
                break;
            case R.id.m5:
                mesa = 8;
                break;

        }

        Intent i = new Intent(this, QhacerActivity.class);
        i.putExtra("MESA", mesa);
        i.putExtra("camarero", idCamarero);

        startActivity(i);
    }
    public void Salir(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
package com.example.proyecto_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button botonIngresar;
EditText nombre, nombre2;
String nombreUsuario,nombrePass;
int password;
boolean nom = false;
Integer idCamarero;
DATABASE conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DATABASE dbHelper = new DATABASE(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {
            Toast.makeText(this, "base de datos creada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }
        botonIngresar = findViewById(R.id.BotonIngresar);
        conn = new DATABASE(getApplicationContext(), DATABASE.DATABASE_NOMBRE, null, 1);

        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        }) ;

    }

    private void login() {

        nombre= findViewById(R.id.NombreUsuario);
        nombre2 = findViewById(R.id.pass);
        nombreUsuario = nombre.getText().toString();
        nombrePass = nombre2.getText().toString();

        String pass = consultarPassword();
        consultarNombre();

        /**
         * Si Existe el nombre y la contraseña concuerda pasa a la siguiente pantalla
         */
        if (nom && nombrePass.equals(pass)){
            idCamarero = consultaId();
            Intent intent = new Intent(this, SeleccionMesaActivity.class);
            intent.putExtra("IDCAMARERO",idCamarero);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Consulta la contraseña del nombre introducido
     * @return el password del nombre introducido
     */
    private String consultarPassword(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {nombreUsuario};
        String[] campos = {"password"};
        String pass = null;
        Cursor cursor = db.query(DATABASE.TABLE_USUARIO, campos, "nombre=?", parametros,null, null, null);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "no existe el usuario", Toast.LENGTH_SHORT).show();
        }else {
            cursor.moveToFirst();
            pass = cursor.getString(0);
        }
    return pass;}

    /**
     * Consulta si existe el nombre en la base de datos
     */
    private void consultarNombre(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {nombreUsuario};
        String[] campos = {"nombre"};

        Cursor cursor = db.query(DATABASE.TABLE_USUARIO, campos, "nombre=?", parametros,null, null, null);

        if(cursor.getCount() == 0){
            Toast.makeText(this, "no existe el usuario", Toast.LENGTH_SHORT).show();
        }else{
            nom = true;
        }
        }

    /**
     * Realiza una consulta para saber el camarero que se ha logeado.
     * @return el id del camarero con el que se ha logueado
     */
    private int consultaId(){
        Integer idCamarero = null;
            String[] parametros = {nombreUsuario};
            String[] campos = {"idCamarero"};
            SQLiteDatabase db = conn.getReadableDatabase();


            Cursor cursor = db.rawQuery("SELECT idCamarero from usuario where nombre = ?", parametros);
            if(cursor.getCount() == 0){
                Toast.makeText(this, "no existe el usuario", Toast.LENGTH_SHORT).show();
            }else {
                cursor.moveToFirst();
                idCamarero = cursor.getInt(0 );

            }
        return idCamarero;}

}


package com.example.proyecto_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaProductosActivity extends AppCompatActivity {

    ListView listViewProductos;
    ArrayList<Producto> listaproductos;
    ArrayList<String>listaInformacion;

    DATABASE conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        conn = new DATABASE(getApplicationContext(),DATABASE.DATABASE_NOMBRE, null, 1);
        listViewProductos = (ListView) findViewById(R.id.ListaProduct);
        consultarListaProduct();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listViewProductos.setAdapter(adaptador);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion = "Nombre: " + listaproductos.get(position).getNombre();

                Toast.makeText(ListaProductosActivity.this, informacion, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void consultarListaProduct() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Producto producto = null;
        listaproductos = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT * FROM "  + DATABASE.TABLE_PRODUCTO, null);

        while(cursor.moveToNext()){
            producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setFamilia(cursor.getString(1));
            producto.setNombre(cursor.getString(2));
            producto.setPrecio(cursor.getFloat(3));

            listaproductos.add(producto);

        }
        obtenerLista();

    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i =0; i<listaproductos.size();i++){
            listaInformacion.add(listaproductos.get(i).getNombre());
        }
    }
}
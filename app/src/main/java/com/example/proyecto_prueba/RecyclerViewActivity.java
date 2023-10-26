package com.example.proyecto_prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    ArrayList<Producto> listaproductos;
    ArrayList <ProductoResumen> ProductosComandaResumen;
    Integer [] fotos={R.drawable.cocacola,R.drawable.pulpo, R.drawable.nachos, R.drawable.solomillo, R.drawable.lubina, R.drawable.cachopo, R.drawable.tiramisu};
    RecyclerView recyclerView;
    RecyclerView resumenRecyclerview;
    DATABASE conn;
    Integer opcion = 1;
    Cursor cursor;
    SQLiteDatabase db;
    AdaptadorResumen adaptadorResumen;
    Integer idMesa;
    Integer idComanda;
    Integer idCamarero;
    Button botonSalircomanda;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Bundle losparametros = this.getIntent().getExtras();
        if (idMesa == null) {
          idMesa = losparametros.getInt("NumMESA");
        }
        idCamarero = losparametros.getInt("IdCAMARERO");
        idComanda = losparametros.getInt("IdCOMANDA");
        conn = new DATABASE(getApplicationContext(),DATABASE.DATABASE_NOMBRE, null, 1);

        botonSalircomanda = (Button)findViewById(R.id.botonSalirComanda);
        botonSalircomanda.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ProductosComandaResumen.clear();
        Intent i = new Intent(getApplicationContext(), QhacerActivity.class);
        i.putExtra("MESA",idMesa);
        i.putExtra("IdComanda",idComanda);
        i.putExtra("camarero",idCamarero);
        startActivity(i);
    }
});
        comprobarComanda(idMesa, idComanda);
        construirRecycler();
    }


    private void comprobarComanda(int mesa, int comanda){

        db = conn.getWritableDatabase();
        ProductoResumen producto = null;
        ProductosComandaResumen = new ArrayList<ProductoResumen>();
        ProductosComandaResumen.clear();
        String[] parametros = {Integer.toString(mesa), Integer.toString(comanda)};


        cursor = db.rawQuery("SELECT * FROM "  + DATABASE.TABLE_COMANDA_PRODUCTO + " WHERE idMesa = ? AND idComanda = ? ",parametros);

        while(cursor.moveToNext()){
            producto = new ProductoResumen();
            producto.setIdComanda(cursor.getInt(0));
            producto.setIdMesa(cursor.getInt(1));
            producto.setIdProducto(cursor.getInt(2));
            producto.setNombreDelProducto(cursor.getString(3));
            producto.setCantidad(cursor.getInt(4));
            producto.setPrecio(cursor.getFloat(5));

            ProductosComandaResumen.add(producto);

        }
    }

    public void construirRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        resumenRecyclerview = (RecyclerView) findViewById(R.id.recyclerResumen);
        resumenRecyclerview.setLayoutManager(new GridLayoutManager(this,1));


        consultarListaProduct(opcion);
        comprobarComanda(idMesa, idComanda);

        AdapterDatos adaptador = new AdapterDatos(this, listaproductos, fotos, idMesa, idComanda, idCamarero);
        recyclerView.setAdapter(adaptador);

        adaptadorResumen = new AdaptadorResumen(ProductosComandaResumen, idMesa, idComanda, idCamarero);
        resumenRecyclerview.setAdapter(adaptadorResumen);
    }



    private void consultarListaProduct(int familia) {
        db = conn.getWritableDatabase();
        Producto producto = null;
        listaproductos = new ArrayList<Producto>();

        cursor = db.rawQuery("SELECT * FROM "  + DATABASE.TABLE_PRODUCTO + " WHERE idFamlia =" + familia,null);

        while(cursor.moveToNext()){
            producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setFamilia(cursor.getString(1));
            producto.setNombre(cursor.getString(2));
            producto.setPrecio(cursor.getFloat(3));

            listaproductos.add(producto);

        }
    }


    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.botonBebidas:opcion = 1;
            break;
            case R.id.botonEntrantes:opcion = 2;
                break;
            case R.id.botonCarne:opcion =3;
                break;
            case R.id.botonPescado:opcion =4;
                break;
            case R.id.botonPostre:opcion = 5;
                break;

        }

        construirRecycler();

    }





}
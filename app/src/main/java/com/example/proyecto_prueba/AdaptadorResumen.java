package com.example.proyecto_prueba;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

public class AdaptadorResumen extends RecyclerView.Adapter<AdaptadorResumen.ViewHolderDatos>{

    ArrayList<ProductoResumen> listaComanda;
    DATABASE conn;
    SQLiteDatabase db;
    Integer idMesa;
    Integer idComanda;
    Integer idCamarero;
    HashSet<String> contadorElementosDiferentes = new HashSet<>();

    Float precios;

    public AdaptadorResumen() {
    }

    public AdaptadorResumen(ArrayList<ProductoResumen> listaComanda, Integer idMesa, Integer idComanda, Integer idCamarero) {
        this.listaComanda = listaComanda;
        this.idMesa = idMesa;
        this.idComanda = idComanda;
        this.idCamarero = idCamarero;
    }

    @Override
    public AdaptadorResumen.ViewHolderDatos onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resumen,parent,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder( AdaptadorResumen.ViewHolderDatos holder, int position) {
        holder.asignarDatos(position);
    }

    @Override
    public int getItemCount() {

        contarResultados();
        return contadorElementosDiferentes.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre, precio, cantidad;
        Integer cant;
        String nombreProducto;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idProductoResumen);
            precio = (TextView) itemView.findViewById(R.id.precioProductoResumen);
            cantidad = (TextView) itemView.findViewById(R.id.cantidadProductoResumen);
            conn = new DATABASE(itemView.getContext(),DATABASE.DATABASE_NOMBRE, null, 1);
            db = conn.getWritableDatabase();
nombre.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(itemView.getContext(), "Ha eliminado " + nombre.getText().toString(), Toast.LENGTH_SHORT).show();
        db.execSQL("DELETE FROM " + DATABASE.TABLE_COMANDA_PRODUCTO + " WHERE nombreDelProducto = '" + nombre.getText().toString() + "'" );
        reiniciarRecycler(itemView);
        return true;
    }
});
        }

        public void asignarDatos(int position) {
            Cursor cursor = db.rawQuery("select nombreDelProducto, count(nombreDelProducto)as Cant,precio from comandaProducto " +
                    "where nombreDelProducto = '"+ listaComanda.get(position).nombreDelProducto + "' and idMesa = " + idMesa
                    + " group by nombreDelProducto", null);
            while(cursor.moveToNext()) {
                nombreProducto = cursor.getString(0);
                cant = cursor.getInt(1);
                precios = cursor.getFloat(2);

            }


                nombre.setText(nombreProducto.toString());
                precio.setText(precios.toString());
                cantidad.setText(cant.toString());
            }
        }

public void contarResultados (){

    for (int i = 0; i < listaComanda.size(); i++) {
        contadorElementosDiferentes.add(listaComanda.get(i).getNombreDelProducto());
    }

}
        public void reiniciarRecycler(View v) {
            Intent i = new Intent(v.getContext(), RecyclerViewActivity.class);
            i.putExtra("NumMESA", idMesa);
            i.putExtra("IdCOMANDA" , idComanda);
            i.putExtra("IdCAMARERO", idCamarero);
            v.getContext().startActivity(i);

        }
    }


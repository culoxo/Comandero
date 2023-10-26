package com.example.proyecto_prueba;




import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    DATABASE conn;
    ArrayList<Producto> listDatos;
    Integer [] fotos;
    Integer idMesa, idComanda, idCamarero;
    RecyclerViewActivity rva;
    SQLiteDatabase db;
    int cant;

Context act;

    public AdapterDatos(Context c, ArrayList<Producto> listDatos, Integer[] fotos, Integer idMesa, Integer idComanda, Integer idCamarero) {
        this.act = c;
        this.listDatos = listDatos;
        this.fotos = fotos;
        this.idMesa = idMesa;
        this.idComanda = idComanda;
        this.idCamarero = idCamarero;

    }

    @Override
    public AdapterDatos.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(AdapterDatos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(position);
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;
        ImageButton ib;

        public ViewHolderDatos(View itemView)    {
            super(itemView);

            dato = (TextView) itemView.findViewById(R.id.nombreProducto);

            ib = (ImageButton) itemView.findViewById(R.id.botonProducto);
        }

        public void asignarDatos(int posicion) {
            dato.setText(listDatos.get(posicion).nombre);


            if(listDatos.get(posicion).nombre.toString().equals("Coca cola")){
                ib.setImageResource(R.drawable.cocacola);
            } else if (listDatos.get(posicion).nombre.toString().equals("Pulpo")) {
                ib.setImageResource(R.drawable.pulpo);
            }else if (listDatos.get(posicion).nombre.toString().equals("Nachos")) {
                ib.setImageResource(R.drawable.nachos);
            }else if (listDatos.get(posicion).nombre.toString().equals("Solomillo")) {
                ib.setImageResource(R.drawable.solomillo);
            }else if (listDatos.get(posicion).nombre.toString().equals("Cachopo")) {
                ib.setImageResource(R.drawable.cachopo);
            }else if (listDatos.get(posicion).nombre.toString().equals("Lubina")) {
                ib.setImageResource(R.drawable.lubina);
            }else if (listDatos.get(posicion).nombre.toString().equals("Tiramisu")) {
                ib.setImageResource(R.drawable.tiramisu);
            }

            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conn = new DATABASE(v.getContext(),DATABASE.DATABASE_NOMBRE, null, 1);
                    Producto producto = new Producto();
                    producto.setNombre(listDatos.get(posicion).getNombre());
                    producto.setId(listDatos.get(posicion).getId());
                    producto.setFamilia(listDatos.get(posicion).getFamilia());
                    producto.setPrecio(listDatos.get(posicion).getPrecio());

                    SQLiteDatabase sqlb = conn.getWritableDatabase();


                    sqlb.execSQL("INSERT INTO comandaProducto select " + idComanda + ", " + idMesa + ", " + producto.id + ", '" + producto.nombre + "', 1," + producto.precio);

                    reiniciarRecycler(v);

                }


            });
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

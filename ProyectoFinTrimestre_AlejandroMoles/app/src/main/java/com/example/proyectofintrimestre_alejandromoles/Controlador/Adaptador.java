package com.example.proyectofintrimestre_alejandromoles.Controlador;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.proyectofintrimestre_alejandromoles.Modelo.Cartas;
import com.example.proyectofintrimestre_alejandromoles.R;
import com.example.proyectofintrimestre_alejandromoles.Vistas.VentanaDetalles;


import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderDatos>{

    //creo una Array list de tipo Cartas
    ArrayList<Cartas> listDatos;
    //creo el contructor de la clase Adaptador
    public Adaptador(ArrayList<Cartas> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflo la vista para asignar la vista que tengo con los componentes que llevara mi recyclerview al recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        //creo un objeto carta, que sera el objeto de la lista de Cartas, obteniendo la posicion
        Cartas carta =listDatos.get(position);
        //llamo al metodo de holder, pasandole el objeto que he creado
        holder.asignarDatos(carta);
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView titulo, descripcion;
        ImageView imagen;

        public RelativeLayout view_principal, view_fondo;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //asigno a la variable TxtView una id que es la de la vista donde se encuentran los componentes de mi RecyclerView
            titulo = itemView.findViewById(R.id.titulo);
            imagen = itemView.findViewById(R.id.imagen);
            descripcion = itemView.findViewById(R.id.descripcion);
            //este es el fondo que se mostrara al usuario
            view_principal = itemView.findViewById(R.id.rl_principal);
            //este es el fonfo que esta detras, que es de color rojo, el que se mostrara al hacer el slide
            view_fondo = itemView.findViewById(R.id.fondo_eliminar);


            ScrollingMovementMethod sr = new ScrollingMovementMethod();
            descripcion.setMovementMethod(sr);


        }
        //le meto al TextView el dato que quiero mostrar, en este caso el nombre de la carta
        public void asignarDatos(Cartas carta) {
            titulo.setText(carta.getNombre());
            Glide.with(itemView)
                    .load(carta.getURL())
                    .error(R.drawable.reverso)
                    .into(imagen);

            descripcion.setText(carta.getDescripcion());

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), VentanaDetalles.class);
                    i.putExtra("obj",carta);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }


    public void borrarCarta(int posicion){
        listDatos.remove(posicion);
        notifyItemRemoved(posicion);
    }
}

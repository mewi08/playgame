package com.example.playgame;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterVideojuegos extends RecyclerView.Adapter<AdapterVideojuegos.ViewHolderDatos> {
    ArrayList<Videojuego> lstVideojuegos;
    private OnAccionListener listener;

    public interface OnAccionListener {
        void onEliminar(Videojuego videojuego);
    }

    public AdapterVideojuegos(ArrayList<Videojuego> lstVideojuegos, OnAccionListener listener){
        this.lstVideojuegos = lstVideojuegos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterVideojuegos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_videojuegos), parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideojuegos.ViewHolderDatos holder, int position) {
        Videojuego videojuego = lstVideojuegos.get(position);
        holder.asignarDatos(videojuego);
        holder.btnEliminar.setOnClickListener( v -> {
            listener.onEliminar(videojuego);
        });
    }

    @Override
    public int getItemCount() {
        return lstVideojuegos.size();
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txtNombreVideojuego, txtGenero, txtDescripcion, txtDesarrollador, txtFechaLanzamiento, txtPrecio;
        Button btnEliminar;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtNombreVideojuego = itemView.findViewById(R.id.txtNombreVideojuego);
            txtGenero = itemView.findViewById(R.id.txtGenero);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtDesarrollador = itemView.findViewById(R.id.txtDesarrollador);
            txtFechaLanzamiento = itemView.findViewById(R.id.txtFechaLanzamiento);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);

            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        @SuppressLint("SimpleDateFormat")
        public void asignarDatos(Videojuego videojuego) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            String precio = videojuego.getPrecio() > 0
                    ? "S/ " + videojuego.getPrecio()
                    : "Gratis" ;

            txtNombreVideojuego.setText(videojuego.getTitulo());
            txtGenero.setText(videojuego.getGenero());
            txtDescripcion.setText(videojuego.getDescripcion());
            txtDesarrollador.setText(videojuego.getDesarrollador());
            txtFechaLanzamiento.setText(formato.format(videojuego.getFecha_lanz()));
            txtPrecio.setText(precio);
        }
    }
}

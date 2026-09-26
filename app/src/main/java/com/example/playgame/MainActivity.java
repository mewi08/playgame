package com.example.playgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterVideojuegos.OnAccionListener {

    ArrayList<Videojuego> lstVideojuegos= new ArrayList<>();
    AdapterVideojuegos adapterVideojuegos;
    RecyclerView recyclerVideojuegos;
    RequestQueue requestQueue;
    private final String URL = "http://192.168.1.12:3000/videojuegos";

    private void loadUI(){
        recyclerVideojuegos = findViewById(R.id.recyclerVideojuegos);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void renderizarVideojuegos(JSONObject jsonObject) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            lstVideojuegos.clear();
            JSONArray datos = jsonObject.getJSONArray("datos");
            for (int i = 0; i < datos.length(); i++) {

                JSONObject videojuegoJSON = datos.getJSONObject(i);
                String fecha = videojuegoJSON.getString("fecha_lanz");
                lstVideojuegos.add(new Videojuego(
                        videojuegoJSON.getInt("id"),
                        videojuegoJSON.getString("titulo"),
                        videojuegoJSON.getString("genero"),
                        videojuegoJSON.getString("desarrollador"),
                        videojuegoJSON.getDouble("precio"),
                        formato.parse(fecha),
                        videojuegoJSON.getString("descripcion")
                ));
            }

            adapterVideojuegos.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e("Error_JSON", e.toString());
        }
    }
    private void manejarError(VolleyError volleyError){
        Log.e("Error_WS", volleyError.toString());
    }

    private void obtenerVideojuegos(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                this::renderizarVideojuegos,
                this::manejarError
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void confirmarEliminacion(Videojuego videojuego){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea eliminar el registro?");

        builder.setPositiveButton("Sí", (dialog, which) -> {
            eliminarVideojuego(videojuego);
        });

        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void manejarEliminacion(JSONObject response) {
        obtenerVideojuegos();
    }
    private void eliminarVideojuego(Videojuego videojuego) {
        int id = videojuego.getId();
        String endPoint = URL + "/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                this::manejarEliminacion,
                this::manejarError
        );

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        this.loadUI();
        obtenerVideojuegos();
        adapterVideojuegos = new AdapterVideojuegos(lstVideojuegos, this);
        recyclerVideojuegos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideojuegos.setAdapter(adapterVideojuegos);
    }

    @Override
    public void onEliminar(Videojuego videojuego) {
        confirmarEliminacion(videojuego);
    }
}
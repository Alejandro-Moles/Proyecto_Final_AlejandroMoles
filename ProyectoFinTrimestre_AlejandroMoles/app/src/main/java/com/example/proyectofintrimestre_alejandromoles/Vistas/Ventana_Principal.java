package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.proyectofintrimestre_alejandromoles.Controlador.Adaptador;
import com.example.proyectofintrimestre_alejandromoles.Controlador.ConexionMagic;
import com.example.proyectofintrimestre_alejandromoles.Controlador.ItemCallBack;
import com.example.proyectofintrimestre_alejandromoles.Interfaces.ItemTouch;
import com.example.proyectofintrimestre_alejandromoles.Modelo.Cartas;
import com.example.proyectofintrimestre_alejandromoles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//implemento la interfaz que usare para hacer las animaciones de deslizar el item del racycler
public class Ventana_Principal extends AppCompatActivity implements ItemTouch {

    //me creo un array list donde se almacenaran los datos de las cartas
    private static ArrayList<Cartas> listaMagic = new ArrayList<Cartas>();

    //declaro el recyclerview
    RecyclerView lista;
    Adaptador adaptador;
    RelativeLayout relaPrincipal, relativeCarta;
    private String Idioma = "INGLES", ColorFondo = "Predeterminado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

        //instancio en recyclerview y creo un layoutmanager
        lista = findViewById(R.id.lista_re);
        lista.setLayoutManager(new LinearLayoutManager(this));
        relaPrincipal = findViewById(R.id.relative_Principal);
        relativeCarta = findViewById(R.id.rl_principal);
        //creo un nuevo objeto de la clase task y ejecuto el metodo execute





        //me creo un objeto callback al que le pasare el contexto  metere en otro objeto touch helper
        ItemTouchHelper.Callback callback = new ItemCallBack(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //lo conecto con el recycler view
        touchHelper.attachToRecyclerView(lista);




    }

    //creo un metodo el cual infla en menu para ponerlo en la barra superior de la aplicacion
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //creo un metodo para establecer las opciones que tendrá el menu creado anteriormente
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //si pulso el perfil ire a la pantalla de perfil
        if(id == R.id.Perfil){
            irPerfil();
        //en cambio si pulso cerrar sesion, ire a la pantalla de inicio
        }else if(id == R.id.Cerrar_Sesion){
            irPantallaInicio();
        }else if(id == R.id.Ajustes_btn){
            irAjustes();
        }
        return super.onOptionsItemSelected(item);
    }

    //metodo para volver a la pantalla de inicio
    public void irPantallaInicio(){
        Intent i = new Intent(this, Pantalla_Inicio.class);
        startActivity(i);
        finish();
    }

    public void irAjustes(){
        Intent i = new Intent(this,Preferencias.class);
        startActivity(i);
    }

    //metodo para ir a la pantalla de perfil pasandole como parametros el nombre del usuario y su correo
    public void irPerfil(){
        String nombre = getIntent().getStringExtra("nombre");
        String correo = getIntent().getStringExtra("correo");

        Intent i = new Intent(this,Perfil.class);
        i.putExtra("nombre", nombre);
        i.putExtra("correo", correo);

        startActivity(i);
    }

    //metodo que se ejecuta al hacer el swipe en el item del recycler
    //lo que hara sera borrar el item que se ha deslizado
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int posicion) {
        String nombre = listaMagic.get(viewHolder.getAdapterPosition()).getNombre();
        adaptador.borrarCarta(viewHolder.getAdapterPosition());
    }

    //creo esta clase para coger los datos de la api de una manera en la que no se sobrecargue la aplicacion
    private class taskConnections extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            //compruebo los el parametro 0 que le he pasado al llamar a este metodo
            if(strings[0] == "GET"){
                result = ConexionMagic.PeticionGet();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s ) {
            try {
                //si el string no es null, entonces no tendra problema en obtener los datos de la api
                //el string que cojo es un conjunto de datos que tiene la api, y ya a partir de ahi,
                //selecciono los que me interesen

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Ventana_Principal.this);
                Idioma = sharedPreferences.getString("idioma","");
                int idioma = 0;

                if (s != null) {
                    //hago un switch para sacar la variable int que corresponderá a mi idioma
                    switch (Idioma){
                        case "ESPAÑOL":
                            idioma = 1;
                            Log.d("D", String.valueOf(idioma)); //borro la lista para que me la rellene de nuevo
                            listaMagic.clear();
                            break;
                        case "ALEMAN":
                            idioma = 0;
                            Log.d("D", String.valueOf(idioma));
                            listaMagic.clear();
                            break;
                        case "FRANCES":
                            idioma = 2;
                            listaMagic.clear();
                            break;
                        case "ITALIANO":
                            idioma = 3;
                            listaMagic.clear();
                            break;
                        case "JAPONES":
                            idioma = 4;
                            listaMagic.clear();
                            break;
                        case "PORTUGUES":
                            idioma = 5;
                            listaMagic.clear();
                            break;
                        case "RUSSO":
                            idioma = 6;
                            listaMagic.clear();
                            break;
                        case "CHINO":
                            idioma = 7;
                            listaMagic.clear();
                            break;
                    }

                    //como el string no lo devuelve en JSON, hay que utilizar clases de android para obtener esos datos
                    //y poder luego meterlos en el recyclerView
                    JSONObject jsonObject = new JSONObject(s);

                    //pongo el cards, por que en la api los datos que quiero coger estan dentro de este apartado
                    //meto esos datos en un array
                    JSONArray jsonArray = jsonObject.getJSONArray("cards");

                    JSONArray jsonArray1 = null;

                    String name = "", Url = "", tipo, descripcion;
                    //recorro el array que he creado antes y asigno a un String nombre y a otro Url sus respectivos datos
                    //(al nombre el nombre de la carta y a la url la url de la imagen)


                    //recorro el array de json donde la key es cards
                    for(int i = 0;i<jsonArray.length();i++){

                        //compruebo que tiene valor en foreignNames
                        if(jsonArray.getJSONObject(i).has("foreignNames")){
                            //saco un array que sera un objeto que se encuentra en el array principal, el cual tiene como identificador foreignNames
                            jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("foreignNames");

                            //saco el nombre, url, descripcion, tipo  y descripcion de las cartas en su idioma,(dependiendo del que haya obtenido en el
                            //setting saca un idioma u otro, el idioma por defecto es el español
                            name = jsonArray1.getJSONObject(idioma).optString("name");
                            Url = jsonArray1.getJSONObject(idioma).optString("imageUrl").replace("http", "https");
                            tipo = jsonArray1.getJSONObject(idioma).optString("type");
                            descripcion = jsonArray1.getJSONObject(idioma).optString("text");
                            listaMagic.add(new Cartas(name, Url,tipo,descripcion));
                        }
                    }
                    //creo un objeto tipo adaptador al que le paso la lista de Cartas, para que me rellene el recycledView
                    adaptador = new Adaptador(listaMagic);
                    Log.d("AA", listaMagic.get(0).getNombre());
                    lista.setAdapter(adaptador);
                } else {
                    //si los datos que recibo del string estan vacios entonces no me carga nada de la api
                    Toast("Error a la hora de cargar datos");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskConnections tsk = new taskConnections();
        tsk.execute("GET");
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
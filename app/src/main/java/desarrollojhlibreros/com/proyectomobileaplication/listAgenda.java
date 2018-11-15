package desarrollojhlibreros.com.proyectomobileaplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listAgenda extends AppCompatActivity {

    private ArrayList<Contacto> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);
        findViewById(R.id.listAgenda);

        contactos=new ArrayList<>();
        obtenerContactos("https://monitorescomagenda.000webhostapp.com/conectorPHP/obtenerContactos_agenda.php");
    }


    private void obtenerContactos(String URL) {

        JsonArrayRequest peticionEnvio =new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        contactos.add(new Contacto(jsonObject.getString("nombre"), jsonObject.getString("telefono"), jsonObject.getString("imagen")));
                    } catch (JSONException e) {
                        Toast.makeText(getApplication(), "ERROR DE LECTURA: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                initRecyclerViewAgenda(R.id.listAgenda,contactos,R.layout.cardview_list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "ERROR DE CONEXION: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionEnvio);
    }



    private  void initRecyclerViewAgenda(int reciclerIdentificador, ArrayList<Contacto> contactos, int cardViewID ){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = findViewById(reciclerIdentificador);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AdapatadorList(contactos,cardViewID,getBaseContext()));
    }

}

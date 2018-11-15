package desarrollojhlibreros.com.proyectomobileaplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnListaAgenda;
    private Button btnAgregarUsuario;
    private Button btnEliminarUsuario;
    private Button btnModificarUsuario;
    private EditText nombreContacto;
    private EditText telefonoContacto;
    private Uri uri;

    private String nuevoNombre;
    private String nuevoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListaAgenda = (Button) findViewById(R.id.btnLista);
        btnAgregarUsuario=(Button) findViewById(R.id.btnAlta);
        btnEliminarUsuario = (Button) findViewById(R.id.btnBaja);
        btnModificarUsuario=(Button) findViewById(R.id.btnModificacion);

        nombreContacto=(EditText) findViewById(R.id.txtNombre);
        telefonoContacto=(EditText) findViewById(R.id.txtTelefono);

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("https://monitorescomagenda.000webhostapp.com/conectorPHP/insertar_agenda.php");
            }
        });


        btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("https://monitorescomagenda.000webhostapp.com/conectorPHP/eliminar_agenda.php");
            }
        });

        btnModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarAlertaUsuario().show();
            }
        });

        btnListaAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),listAgenda.class));
            }
        });
    }

    public AlertDialog modificarAlertaUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modificacion de contacto");
        builder.setMessage("Coloque los nuevos datos para el contacto");

        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_personalizado, null);
        builder.setView(v);
        final EditText nombreNuevo = (EditText) v.findViewById(R.id.nuevoNombre);
        final EditText telefonoNuevo = (EditText) v.findViewById(R.id.nuevoTelefono);


        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nuevoNombre=nombreNuevo.getText().toString();
                        nuevoTelefono=telefonoNuevo.getText().toString();
                        servicioModificarUsuario("https://monitorescomagenda.000webhostapp.com/conectorPHP/modificar_agenda.php");
                    }
                })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),"La operacion cancelada",Toast.LENGTH_LONG).show();
                            }
                        });
        return builder.create();
    }


    private void ejecutarServicio(String URL) {
        StringRequest peticionEnvio =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_LONG).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "PROBLEMA EN LA OPERACION:  "+error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros = new HashMap<>();
                parametros.put("nombre",nombreContacto.getText().toString());
                parametros.put("telefono",telefonoContacto.getText().toString());
                parametros.put("imagen","https://pixabay.com/photo-42914");
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionEnvio);
    }



    private void servicioModificarUsuario(String URL) {
        StringRequest peticionEnvio =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "OPERACION EXITOSA", Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "PROBLEMA EN LA OPERACION: "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros = new HashMap<>();
                parametros.put("nombre",nombreContacto.getText().toString());
                parametros.put("telefono",telefonoContacto.getText().toString());
                parametros.put("nombreNuevo",nuevoNombre);
                parametros.put("telefonoNuevo",nuevoTelefono);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionEnvio);
    }

}

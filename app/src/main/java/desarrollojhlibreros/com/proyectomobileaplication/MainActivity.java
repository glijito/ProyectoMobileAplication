package desarrollojhlibreros.com.proyectomobileaplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private Button btnListaAgenda;
    private Button btnAgregarUsuario;
    private Button btnEliminarUsuario;
    private Button btnModificarUsuario;
    private EditText nombreContacto;
    private EditText telefonoContacto;
    private Button btnImagen;
    private Uri uri;
    private Bitmap bitmap;

    private static final int RC_GET_IMG=0;
    private static final int RC_ACCESS=1;
    private ImageView imagen;

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
        btnImagen = (Button) findViewById(R.id.btnImagen);
        nombreContacto=(EditText) findViewById(R.id.txtNombre);
        telefonoContacto=(EditText) findViewById(R.id.txtTelefono);

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://bmhbdofn.lucusvirtual.es/conector/insertar_agenda.php");
            }
        });

        btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://bmhbdofn.lucusvirtual.es/conector/eliminar_agenda.php");
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

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
    }

    public void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(Intent.createChooser(intent,"Seleccina una imagen para tu perfil"),RC_GET_IMG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_GET_IMG && resultCode == RESULT_OK){
            uri=data.getData();

            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},RC_ACCESS);
            }else{
                InputStream inputStream=null;
                try {
                    inputStream=getContentResolver().openInputStream(uri);
                    bitmap=BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==RC_ACCESS){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                InputStream inputStream=null;
                try {
                    inputStream=getContentResolver().openInputStream(uri);
                    bitmap=BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
                        servicioModificarUsuario("http://bmhbdofn.lucusvirtual.es/conector/modificar_agenda.php");
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
                nombreContacto.setText("");
                telefonoContacto.setText("");
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "No se ha podida conectar  "+error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros = new HashMap<>();
                parametros.put("nombre",nombreContacto.getText().toString());
                parametros.put("telefono",telefonoContacto.getText().toString());
                parametros.put("imagen",convertirImagenString(bitmap));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionEnvio);
    }


    private String convertirImagenString(Bitmap bitmap){

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
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

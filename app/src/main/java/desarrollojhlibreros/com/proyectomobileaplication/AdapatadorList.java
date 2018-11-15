package desarrollojhlibreros.com.proyectomobileaplication;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapatadorList extends RecyclerView.Adapter<AdapatadorList.ViewHolder> {

    private ArrayList<Contacto> contactos;
    private int carviewerId;
    private Context instancia;

    public AdapatadorList(ArrayList<Contacto> images, int carviewerId,Context instancia) {
        this.contactos = images;
        this.carviewerId=carviewerId;
        this.instancia=instancia;
    }

    public AdapatadorList(){
        this(null,0,null);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(carviewerId,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        try{
            Glide.with(instancia).load(contactos.get(position).getImagen()).into(viewHolder.imagen);
            viewHolder.nombre.setText("Nombre: "+contactos.get(position).getNombre());
            viewHolder.telefono.setText("Telefono: "+contactos.get(position).getTelefono());
        }catch (Exception e){
            Toast.makeText(instancia,"List problema: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView nombre;
        TextView telefono;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen=itemView.findViewById(R.id.imageContacto);
            nombre=itemView.findViewById(R.id.lblNombre);
            telefono=itemView.findViewById(R.id.lbTelefono);
        }
    }
}

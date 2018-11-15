package desarrollojhlibreros.com.proyectomobileaplication;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapatadorList extends RecyclerView.Adapter<AdapatadorList.ViewHolder> {

    private ArrayList<Integer> images;
    private int carviewerId;

    public AdapatadorList(ArrayList<Integer> images, int carviewerId) {
        this.images = images;
        this.carviewerId=carviewerId;
    }

    public AdapatadorList(){
        this(null,0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(carviewerId,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return images.size();
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

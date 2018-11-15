package desarrollojhlibreros.com.proyectomobileaplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class listAgenda extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);
        findViewById(R.id.listAgenda);

        ArrayList<Integer> datos=new ArrayList<>();
        initRecyclerViewAgenda(R.id.listAgenda,datos,R.layout.cardview_list);
    }


    private  void initRecyclerViewAgenda(int reciclerIdentificador, ArrayList<Integer> productos, int cardViewID ){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = findViewById(reciclerIdentificador);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AdapatadorList(productos,cardViewID));
    }

}

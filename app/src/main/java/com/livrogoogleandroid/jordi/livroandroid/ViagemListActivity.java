package com.livrogoogleandroid.jordi.livroandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.livrogoogleandroid.jordi.livroandroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener {


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        Map<String, Object> map = viagens.get(position);
        String destino = (String)map.get("destino");
        String mensagem = "Viagem selecionada "+destino;
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, GastoListActivity.class));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String [] de = {"imagem", "destino", "data", "valor", "total"};

        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor};

        SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(), R.layout.activity_viagem_list, de, para);

        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);




    }

    private List<Map<String, Object>> viagens;

    private List<Map<String, Object>> listarViagens(){
          viagens = new ArrayList<Map<String, Object>>();

          Map<String, Object> item  = new HashMap<String, Object>();

          item.put("imagem", R.drawable.negocios);
          item.put("destino", "Sao Paulo");
          item.put("data", "02/02/2014 a 04/04/2014");
          item.put("total", "Gasto total de R$ 1000,00");
          viagens.add(item);

          item = new HashMap<String, Object>();
          item.put("imagem", R.drawable.lazer);
          item.put("destino", "Maceio");
          item.put("data", "14/01/2013 a 23/12/2013");
          item.put("total", "Gasto total de R$ 5000,00");
          viagens.add(item);

          return viagens;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viagem_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

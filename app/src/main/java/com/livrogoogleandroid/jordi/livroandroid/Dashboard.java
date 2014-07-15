package com.livrogoogleandroid.jordi.livroandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.livrogoogleandroid.jordi.livroandroid.R;

public class Dashboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }


    public void selecionarOpcao(View view){

        switch (view.getId()){
            case R.id.nova_viagem:
                startActivity(new Intent(this, NovaViagem.class));

            case R.id.novo_gasto:
                startActivity(new Intent(this, NovoGasto.class));

            case R.id.minhas_viagens:
                startActivity(new Intent(this, ViagemListActivity.class));

            case R.id.configuracoes:
                //teste

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        finish();
        return true;

    }
}

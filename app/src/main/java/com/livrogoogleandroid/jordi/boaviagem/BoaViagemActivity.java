package com.livrogoogleandroid.jordi.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



public class BoaViagemActivity extends Activity {

    private EditText usuario;
    private EditText senha;

    private CheckBox manterConectado;
    private static final String MANTER_CONECTADO = "manter_conectado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText)findViewById(R.id.edtUsuario);
        senha = (EditText)findViewById(R.id.edtSenha);
        manterConectado = (CheckBox)findViewById(R.id.CBManterConectado);

        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean conectado = preferencias.getBoolean(MANTER_CONECTADO, false);

        if(conectado){
            startActivity(new Intent(this, Dashboard.class));
        }

        //form = new LoginHelper(this);
    }


    public void entrarOnClick(View v){

        //Login login = form.getLogin();



        String usuarioInformado = usuario.getText().toString();
        String senhaInformada = senha.getText().toString();



        if("jordi" .equals(usuarioInformado) && "123123".equals(senhaInformada)){

            SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();

            editor.putBoolean(MANTER_CONECTADO, manterConectado.isChecked());
            editor.commit();

            startActivity(new Intent(BoaViagemActivity.this, Dashboard.class));
        }

        else{

            String mensagemErro = getString(R.string.erro_autenticacao);

            Toast toast = Toast.makeText(BoaViagemActivity.this, mensagemErro, Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

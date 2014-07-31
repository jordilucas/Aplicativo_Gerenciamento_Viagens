package com.livrogoogleandroid.jordi.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

public class GastoActivity extends Activity {

    private int ano, mes, dia;
    private Button dataGasto;
    private TextView destino;
    private Spinner categoria;
    private EditText valor;
    private EditText descricao;
    private EditText local;
    private Date data;
    private DataBaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataGasto = (Button)findViewById(R.id.data);
        dataGasto.setText(dia + "/" + (mes+1)+ "/" + ano);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categoria_gasto, android.R.layout.simple_spinner_item);

        categoria = (Spinner)findViewById(R.id.categoria);
        categoria.setAdapter(adapter);

        String viagemDestino = getIntent().getExtras().getString(Constantes.VIAGEM_DESTINO);

        destino = (TextView)findViewById(R.id.destino);
        destino.setText(viagemDestino);

        valor = (EditText)findViewById(R.id.valor);
        descricao = (EditText)findViewById(R.id.descricao);
        local = (EditText)findViewById(R.id.local);

        helper = new DataBaseHelper(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.novo_gasto, menu);
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

    public void selecionarData(View view){
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(R.id.data == id){

            return new DatePickerDialog(this, listener, ano, mes, dia);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;

            dataGasto.setText(dia + "/" + (mes+1) + "/" + ano);
            data = criarData(ano, mes, dia);
        }
    };

    public void registrarGasto(View view){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("categoria", categoria.getSelectedItem().toString());
        values.put("data", dataGasto.getText().toString());
        values.put("valor", valor.getText().toString());
        values.put("descricao", descricao.getText().toString());
        values.put("local", local.getText().toString());



        db.insert("gasto", null, values);

    }

    private Date criarData(int anoSelecionado, int mesSelecionado, int diaSelecionado){

        Calendar calendar = Calendar.getInstance();
        calendar.set(anoSelecionado, mesSelecionado, diaSelecionado);
        return calendar.getTime();

    }

}

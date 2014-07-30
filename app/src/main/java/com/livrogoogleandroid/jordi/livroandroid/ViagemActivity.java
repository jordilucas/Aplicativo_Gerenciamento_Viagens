package com.livrogoogleandroid.jordi.livroandroid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViagemActivity extends Activity {

    private String id;
    private DataBaseHelper helper;
    private EditText destino, qtdPessoas, orcamento;
    private RadioGroup radioGroup;
    private int dia, mes, ano;
    private Date dataChegada, dataSaida;
    private Button dataChegadaButton, dataSaidaButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);



        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes  = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataChegadaButton = (Button)findViewById(R.id.dataChegada);
        dataSaidaButton = (Button)findViewById(R.id.dataSaida);

        destino = (EditText)findViewById(R.id.destino);
        qtdPessoas = (EditText)findViewById(R.id.quantidadePessoas);
        orcamento = (EditText)findViewById(R.id.orcamento);
        radioGroup = (RadioGroup)findViewById(R.id.tipoViagem);

        helper = new DataBaseHelper(this);

        id = getIntent().getStringExtra(Constantes.VIAGEM_ID);

        if(id != null){
            prepararEdicao();
        }


    }


    public void criarViagem(View view){

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("destino", destino.getText().toString());

        int tipo = radioGroup.getCheckedRadioButtonId();

        if(tipo == R.id.lazer){
            values.put("tipo_viagem", Constantes.VIAGEM_LAZER);

        }else {
             values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);
        }

        values.put("data_chegada", dataChegada.getTime());
        values.put("data_saida", dataSaida.getTime());
        values.put("orcamento", orcamento.getText().toString());
        values.put("quantidade_pessoas", qtdPessoas.getText().toString());


        long resultado;


        if(id == null){


            Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_LONG).show();
            resultado = db.insert("viagem", null, values);


        }
        else{
            Toast.makeText(this, getString(R.string.erro_salvar), Toast.LENGTH_LONG).show();
            resultado = db.update("viagem", values, "_id = ?", new String[]{id});
        }



    }



    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }


    public void selecionarData(View view){
        showDialog(view.getId());
    }

    private void prepararEdicao(){

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT tipo_viagem, destino, data_chegada, data_saida, quantidade_pessoas, "+
                "orcamento FROM viagem  WHERE _id = ? ", new String[]{id});

        cursor.moveToFirst();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(cursor.getInt(0) == Constantes.VIAGEM_LAZER){
            radioGroup.check(R.id.lazer);
        }
        else{
            radioGroup.check(R.id.negocios);
        }

        destino.setText(cursor.getString(1));
        dataChegada = new Date(cursor.getLong(2));
        dataSaida = new Date(cursor.getLong(3));
        dataChegadaButton.setText(dateFormat.format(dataChegada));
        dataSaidaButton.setText(dateFormat.format(dataSaida));
        qtdPessoas.setText(cursor.getString(4));
        orcamento.setText(cursor.getString(5));
        cursor.close();
    }

    @Override
    protected Dialog onCreateDialog(int id){

        switch(id){

            case R.id.dataChegada:
                return new DatePickerDialog(this, dataChegadaListener, ano, mes, dia);

            case R.id.dataSaida:
                return new DatePickerDialog(this, dataSaidaListener, ano, mes, dia);
        }

        return null;

    }

    private DatePickerDialog.OnDateSetListener dataChegadaListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

            dataChegada = criarData(anoSelecionado, mesSelecionado, diaSelecionado);

            dataChegadaButton.setText(dia + "/" + (mes+1) + "/" + ano);

        }
    };

    private DatePickerDialog.OnDateSetListener dataSaidaListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

            dataSaida = criarData(anoSelecionado, mesSelecionado, diaSelecionado);
            dataSaidaButton.setText(dia + "/" + (mes+1) + "/" + ano);

        }
    };


    private Date criarData(int anoSelecionado, int mesSelecionado, int diaSelecionado){

        Calendar calendar = Calendar.getInstance();
        calendar.set(anoSelecionado, mesSelecionado, diaSelecionado);
        return calendar.getTime();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nova_viagem, menu);
        return true;

    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch(item.getItemId()){

            case R.id.novo_gasto:
                startActivity(new Intent(this, NovoGasto.class));

                return true;

            case R.id.remover:
                //remove viagem do banco de dados
                return true;

            default:

                return super.onMenuItemSelected(featureId, item);

        }

    }
}

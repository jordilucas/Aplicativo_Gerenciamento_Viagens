package com.livrogoogleandroid.jordi.boaviagem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.livrogoogleandroid.jordi.boaviagem.DataBaseHelper;
import com.livrogoogleandroid.jordi.boaviagem.domain.Gasto;
import com.livrogoogleandroid.jordi.boaviagem.domain.Viagem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jordi on 29/07/14.
 */
public class BoaViagemDao {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public BoaViagemDao(Context context){

        helper = new DataBaseHelper(context);

    }

    private SQLiteDatabase getDb(){

        if(db == null){

            db = helper.getWritableDatabase();

        }

        return db;

    }

    public void close(){
        helper.close();
    }

    public List<Viagem> listarViagens(){

        Cursor cursor = getDb().query(DataBaseHelper.Viagem.TABELA, DataBaseHelper.Viagem.COLUNAS, null, null, null, null, null);

        List<Viagem> viagens = new ArrayList<Viagem>();

        while(cursor.moveToNext()){
            Viagem viagem = criarViagem(cursor);
            viagens.add(viagem);
        }

        cursor.close();
        return viagens;

    }

    public Viagem buscarViagemId(Integer id){
        Cursor cursor = getDb().query(DataBaseHelper.Viagem.TABELA, DataBaseHelper.Viagem.COLUNAS,
                                 DataBaseHelper.Viagem._ID + " = ? ", new String[]{id.toString()},
                                 null, null, null);

        if(cursor.moveToNext()){
            Viagem viagem = criarViagem(cursor);
            cursor.close();
            return viagem;
        }

        return null;

    }

    public long inserirViagem(Viagem viagem){

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.Viagem.DESTINO, viagem.getDestino());
        values.put(DataBaseHelper.Viagem.TIPO_VIAGEM, viagem.getTipoViagem());
        values.put(DataBaseHelper.Viagem.DATA_CHEGADA, viagem.getDataChegada().getTime());
        values.put(DataBaseHelper.Viagem.DATA_SAIDA, viagem.getDataSaida().getTime());
        values.put(DataBaseHelper.Viagem.ORCAMENTO, viagem.getOrcamento());
        values.put(DataBaseHelper.Viagem.QUANTIDADE_PESSOAS, viagem.getQuantidadePessoas());

        return getDb().insert(DataBaseHelper.Viagem.TABELA, null, values);

    }

    public boolean removerViagem(Long id){

        String whereClause = DataBaseHelper.Viagem._ID + " = ? ";
        String[] whereArgs = new String[]{id.toString()};
        int removidos = getDb().delete(DataBaseHelper.Viagem.TABELA, whereClause, whereArgs);
        return removidos > 0;

    }

    public List<Gasto> listarGastos(Viagem viagem){

        String selection = DataBaseHelper.Gasto.VIAGEM_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(viagem.getId()).toString()};
        Cursor cursor = getDb().query(DataBaseHelper.Gasto.TABELA, DataBaseHelper.Gasto.COLUNAS,
                                      selection, selectionArgs, null, null, null);
        List<Gasto> gastos = new ArrayList<Gasto>();
        while(cursor.moveToNext()){
            Gasto gasto = criarGasto(cursor);
            gastos.add(gasto);
        }
        cursor.close();
        return gastos;

    }

    public Gasto buscarGastosPorId(Integer id){

        Cursor cursor = getDb().query(DataBaseHelper.Gasto.TABELA,
                DataBaseHelper.Gasto.COLUNAS, DataBaseHelper.Gasto._ID + " = ?",
                new String[]{id.toString()}, null, null, null);

        if(cursor.moveToNext()){
            Gasto gasto = criarGasto(cursor);
            cursor.close();
            return gasto;
        }
        return null;
    }

    public long inserir(Gasto gasto){

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.Gasto.CATEGORIA, gasto.getCategoria());
        values.put(DataBaseHelper.Gasto.DATA, gasto.getData().getTime());
        values.put(DataBaseHelper.Gasto.DESCRICAO, gasto.getDescricao());
        values.put(DataBaseHelper.Gasto.LOCAL, gasto.getLocal());
        values.put(DataBaseHelper.Gasto.VALOR, gasto.getValor());
        values.put(DataBaseHelper.Gasto.VIAGEM_ID, gasto.getViagemId());

        return getDb().insert(DataBaseHelper.Gasto.TABELA, null, values);

    }

    public boolean removerGasto(Long id){

        String whereClause = DataBaseHelper.Gasto._ID + " = ?";
        String[] whereArgs = new String[]{id.toString()};
        int removidos = getDb().delete(DataBaseHelper.Gasto.TABELA, whereClause, whereArgs);

        return removidos > 0;

    }

    public double calcularTotalGasto(Viagem viagem){

        Cursor cursor = getDb().rawQuery("SELECT SUM ("+DataBaseHelper.Gasto.VALOR+ ") FROM +" +
                        DataBaseHelper.Gasto.TABELA + " WHERE " + DataBaseHelper.Gasto.VIAGEM_ID + "=?",
                new String[]{String.valueOf(viagem.getId()).toString()});

        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;

    }

    private Viagem criarViagem(Cursor cursor){

        Viagem viagem = new Viagem(

                cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Viagem._ID)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Viagem.DESTINO)),
                cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Viagem.TIPO_VIAGEM)),
                new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Viagem.DATA_CHEGADA))),
                new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Viagem.DATA_SAIDA))),
                cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.Viagem.ORCAMENTO)),
                cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Viagem.QUANTIDADE_PESSOAS)));

        return viagem;

    }

    private Gasto criarGasto(Cursor cursor){

        Gasto gasto = new Gasto(

                cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Gasto._ID)),
                new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Gasto.DATA))),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Gasto.CATEGORIA)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Gasto.DESCRICAO)),
                cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.Gasto.VALOR)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Gasto.LOCAL)),
                cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Gasto.VIAGEM_ID))

        );

        return gasto;

    }





}


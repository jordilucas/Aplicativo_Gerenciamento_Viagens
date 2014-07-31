package com.livrogoogleandroid.jordi.boaviagem.domain;

import java.util.Date;

/**
 * Created by jordi on 29/07/14.
 */
public class Viagem {

    private Long id;
    private String destino;
    private Integer tipoViagem;
    private Date dataChegada;
    private Date dataSaida;
    private Double orcamento;
    private Integer quantidadePessoas;

    public Viagem(){

    }

    public Viagem(Long id, String destino, Integer tipoViagem, Date dataChegada, Date dataSaida,
                  Double orcamento, Integer quantidadePessoas){

        this.setId(id);
        this.setDestino(destino);
        this.setTipoViagem(tipoViagem);
        this.setDataChegada(dataChegada);
        this.setDataSaida(dataSaida);
        this.setOrcamento(orcamento);
        this.setQuantidadePessoas(quantidadePessoas);

    }


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(Integer tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }




}

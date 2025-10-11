package com.example.repository;

import com.example.model.Faixa;
import com.example.model.FaixaNormal;
import com.example.model.FaixaFavorita;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FaixaRepository {
    private List<Faixa> faixas;
    private long proximoId;

    public FaixaRepository() {
        this.faixas = new ArrayList<>();
        this.proximoId = 1;
    }

    public List<Faixa> listarTodas() {
        return faixas;
    }

    public Faixa buscarPorId(long id) {
        for (Faixa faixa : faixas) {
            if (faixa.getId() == id) {
                return faixa;
            }
        }
        return null;
    }

    public Faixa adicionarFaixaNormal(String nome, Duration duracao) {
        FaixaNormal novaFaixa = new FaixaNormal();
        novaFaixa.setId(proximoId++);
        novaFaixa.setNome(nome);
        novaFaixa.setDuracao(duracao);
        faixas.add(novaFaixa);
        return novaFaixa;
    }

    public Faixa adicionarFaixaFavorita(String nome, Duration duracao) {
        FaixaFavorita novaFaixa = new FaixaFavorita();
        novaFaixa.setId(proximoId++);
        novaFaixa.setNome(nome);
        novaFaixa.setDuracao(duracao);
        faixas.add(novaFaixa);
        return novaFaixa;
    }

    public void atualizarNome(long id, String novoNome) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixa.setNome(novoNome);
        }
    }

    public void atualizarDuracao(long id, Duration novaDuracao) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixa.setDuracao(novaDuracao);
        }
    }

    public void excluir(long id) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixas.remove(faixa);
        }
    }

    public int contar() {
        return faixas.size();
    }
}


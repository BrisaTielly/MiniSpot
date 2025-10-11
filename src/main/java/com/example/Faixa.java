package com.example;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Faixa {
    private long id;
    private String nome;
    private Duration duracao;

    public abstract void tocar();
    public abstract void exibirInfo();
}

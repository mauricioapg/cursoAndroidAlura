package com.example.aluraviagens.dao;

import com.example.aluraviagens.model.Pacote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacoteDAO {
    public List<Pacote> lista(){
        List<Pacote> pacotes = new ArrayList<>(Arrays.asList(
                new Pacote("São Paulo", "sao_paulo_sp", 4, new BigDecimal(249.90)),
                new Pacote("Belo Horizonte", "belo_horizonte_mg", 3, new BigDecimal(328.99)),
                new Pacote("Foz do Iguaçu", "foz_do_iguacu_pr", 5, new BigDecimal(379.90)),
                new Pacote("Recife", "recife_pe", 7, new BigDecimal(650.00)),
                new Pacote("Rio de Janeiro", "rio_de_janeiro_rj", 4, new BigDecimal(448.99)),
                new Pacote("Salvador", "salvador_ba", 6, new BigDecimal(249.90))
        ));
        return  pacotes;
    }
}

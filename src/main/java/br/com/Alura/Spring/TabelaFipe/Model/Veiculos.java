package br.com.Alura.Spring.TabelaFipe.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculos( 
    
 
@JsonAlias("Valor")String valor,
@JsonAlias("Marca") String Marca,
@JsonAlias("Modelo") String modelo,
@JsonAlias("Ano") Integer ano,
@JsonAlias("Combustivel")String Gasolina
) {
    
    
   

}

package br.com.Alura.Spring.TabelaFipe.Principal;

import java.util.Scanner;

import org.springframework.util.comparator.Comparators;

import com.fasterxml.jackson.core.Version;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;


import br.com.Alura.Spring.TabelaFipe.Model.Dados;
import br.com.Alura.Spring.TabelaFipe.Model.Modelos;
import br.com.Alura.Spring.TabelaFipe.Model.Veiculos;
import br.com.Alura.Spring.TabelaFipe.Service.ConsumoAPI;
import br.com.Alura.Spring.TabelaFipe.Service.ConverteDados;

public class Principal {
    
    
    private ConsumoAPI consumo = new ConsumoAPI();
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();
   private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
   
   
   
    public  void exibeMenu(){
        var menu = """
                OPÇÕES:

                *Carros
                *Moto
                *Caminhão

                Digite uma das opções para consulta:
                """;

                System.out.println(menu);

                var opcao = leitura.nextLine();

                String endereco;
                
                if (opcao.toLowerCase().contains("carr")) {
                    endereco = URL_BASE + "carros/marcas";

                } else if (opcao.toLowerCase().contains("mot")) {
                    endereco = URL_BASE + "motos/marcas";
                    
                }else {
                    endereco = URL_BASE + "caminhoes/marcas";
                }
                
                var json = consumo.obterDados(endereco);
                System.out.println(json);

                var marcas = conversor.obterLista(json, Dados.class);
                marcas.stream().sorted(Comparator.comparing(d -> d.codigo())).forEach(System.out::println);

                System.out.println("qual o código da marca:");
                var codigoMarca = leitura.nextLine();

                endereco =endereco + "/" + codigoMarca + "/modelos";
                json = consumo.obterDados(endereco);
                var modeloLista = conversor.obterDados(json, Modelos.class);
                System.out.println("\n Modelos dessa marca :");
                modeloLista.modelos().stream().sorted(Comparator.comparing(d -> d.codigo())).forEach(System.out::println);

                System.out.println("\n digite o nome do veiculo:");
                var nomeVeiculo = leitura.nextLine();
                List<Dados> modelosCarros = modeloLista.modelos().stream().filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase())).collect(Collectors.toList());
                System.out.println("\n modelos filtrados");
                modelosCarros.forEach(System.out::println);


                System.out.println("digite o código do modelo do carro");
                var codigoModelo = leitura.nextLine();
                endereco =endereco + "/" + codigoModelo + "/anos";
                json =consumo.obterDados(endereco);
                List<Dados> anos = conversor.obterLista(json, Dados.class);
                List<Veiculos> veiculos = new ArrayList<>();

                for (int i = 0; i < anos.size(); i++) {
                   var enderecoAnos = endereco + "/" + anos.get(i).codigo();
                   json = consumo.obterDados(enderecoAnos);
                   Veiculos veiculo = conversor.obterDados(json, Veiculos.class);  
                   veiculos.add(veiculo);
                }
                  System.out.println(" \n Todos os veiculos encontrados: ");
                  veiculos.forEach(System.out::println);
                
    }
}

package br.rmginner;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Broker {

    private final List<Map<String, Vendedor>> produtos = new ArrayList<>();
    private final List<Map<String, Vendedor>> lances = new ArrayList<>();

    @JmsListener(destination = "anuncio-item", containerFactory = "myFactory")
    public void receiveMessage(Vendedor vendedor) {
        System.out.println("Item: " + vendedor.getNomeItem());
        System.out.println("\n Anunciado por: " + vendedor.getNome());

        produtos.add(Map.of(vendedor.getNomeItem().trim().toLowerCase() + "-" + vendedor.getNome(), vendedor));
    }

    @JmsListener(destination = "compra-item", containerFactory = "myFactory")
    public void receiveMessage(Comprador comprador) {
        Map<String,Vendedor> produto = lances
                .stream()
                .filter(map -> map.containsKey(comprador.getItemDesejado().toLowerCase().trim() + "-"+ comprador.getNomeVendedor()))
        .findFirst().orElse(null);

        if(Objects.nonNull(produto)) {
            Vendedor vendedor = produto.get(comprador.getItemDesejado().toLowerCase().trim() + "-" + comprador.getNomeVendedor());

            System.out.printf("O comprador %s fez um lance de %.2f para o produto %s, que pertence ao vendedor %s.",
                    comprador.getNome(), comprador.getValor(), comprador.getItemDesejado(), vendedor.getNome());
        }else{
            System.out.println("Nenhum produto encontrado para este lance/produto/vendedor.");
        }


    }
}

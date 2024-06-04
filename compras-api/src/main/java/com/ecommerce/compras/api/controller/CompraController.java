package com.ecommerce.compras.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.compras.api.dto.CompraAuthDTO;
import com.ecommerce.compras.api.service.CompraService;
import com.ecommerce.compras.api.service.ItemService;
import com.ecommerce.compras.client.compra.CompraDTO;
import com.ecommerce.compras.client.compra.ItemDTO;
import com.ecommerce.compras.client.produto.ProdutoDTO;
import com.ecommerce.compras.client.usuario.ClienteDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/compras")
public class CompraController {

    @PostMapping
    public ResponseEntity<CompraDTO> cadastrarCompra(@RequestBody CompraAuthDTO dto) {
        ClienteDTO cliente = compraService.obterDetalhesDoCliente(dto.getCompra().getEmailCliente(), dto.getToken());

        List<ItemDTO> itens = dto.getCompra().getItens().stream().map(i -> {
            ProdutoDTO produto = itemService.obterDetalhesDoProduto(i.getCodigoProduto());

            return itemService.salvarItem(i, produto);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.salvarCompra(dto.getCompra(), cliente, itens));
    }

    @Autowired
    private CompraService compraService;

    @Autowired
    private ItemService itemService;

}

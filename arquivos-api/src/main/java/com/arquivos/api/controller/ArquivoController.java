package com.arquivos.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.arquivos.api.model.Arquivo;
import com.arquivos.api.service.ArquivoService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/arquivos")
public class ArquivoController {

    @PostMapping
    public ResponseEntity<Arquivo> uploadArquivo(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        String contentType = arquivo.getContentType();

        if (!contentType.equals("application/pdf")) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        if (arquivoService.arquivoExiste(arquivo.getOriginalFilename())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }

        return ResponseEntity.ok().body(arquivoService.uploadArquivo(arquivo));
    }

    @GetMapping
    public ResponseEntity<?> downloadArquivo(@RequestParam("nome") String nome) throws IOException {
        byte[] arquivo = arquivoService.downloadArquivo(nome);
        return ResponseEntity.ok().body(arquivo);
    }

    @Autowired
    private ArquivoService arquivoService;

}

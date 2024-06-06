package com.arquivos.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arquivos.api.model.Arquivo;
import com.arquivos.api.repository.ArquivoRepository;

@Service
public class ArquivoService {

    public boolean arquivoExiste(String nome) {
        Optional<Arquivo> arquivo = arquivoRepository.findByNome(nome);

        return arquivo.isPresent();
    }

    public Arquivo uploadArquivo(MultipartFile file) throws IOException {
        String filepath = ROOT_PATH + file.getOriginalFilename();

        Path path = Paths.get(filepath);

        Files.createDirectories(path.getParent());

        Files.write(path, file.getBytes());

        Arquivo arquivo = new Arquivo();

        arquivo.setNome(file.getOriginalFilename());
        arquivo.setDataHoraEnvio(LocalDateTime.now());
        arquivo.setPath(filepath);
        arquivo.setTipo(file.getContentType());

        return arquivoRepository.save(arquivo);
    }

    public ByteArrayResource downloadArquivo(String nome) throws IOException {
        Optional<Arquivo> arquivo = arquivoRepository.findByNome(nome);

        if (arquivo.isPresent()) {
            String path = arquivo.get().getPath();

            File file = new File(path);

            return new ByteArrayResource(Files.readAllBytes(file.toPath()));
        }

        return null;
    }

    public static final String ROOT_PATH = "../arquivos/";

    @Autowired
    private ArquivoRepository arquivoRepository;

}

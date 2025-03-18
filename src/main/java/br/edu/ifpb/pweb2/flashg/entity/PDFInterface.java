package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PDFInterface {
    public void gerarCabecalho();
    public void gerarCorpo();
    public void gerarRodape();
    public ResponseEntity<Resource> imprimir();
}

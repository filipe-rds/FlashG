package br.edu.ifpb.pweb2.flashg.entity;

public interface PDFInterface {
    public void gerarCabecalho();
    public void gerarCorpo();
    public void gerarRodape();
    public void imprimir();
}

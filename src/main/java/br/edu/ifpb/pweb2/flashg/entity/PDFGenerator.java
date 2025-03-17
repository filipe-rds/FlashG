package br.edu.ifpb.pweb2.flashg.entity;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerator implements PDFInterface {
    private CommentProjection comments;
    private Document documentPDF;
    private String path = "GeneratorPDF/Comments";

    public PDFGenerator(CommentProjection comments) throws FileNotFoundException {
        this.comments = comments;
        this.documentPDF = new Document();

        PdfWriter.getInstance(this.documentPDF, new FileOutputStream(path));
        documentPDF.open();
    }

    @Override
    public void gerarCabecalho() {
        System.out.println(this.comments);
    }

    @Override
    public void gerarCorpo() {

    }

    @Override
    public void gerarRodape() {

    }

    @Override
    public void imprimir() {

    }
}

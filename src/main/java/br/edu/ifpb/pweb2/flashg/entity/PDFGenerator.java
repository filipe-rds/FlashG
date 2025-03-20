package br.edu.ifpb.pweb2.flashg.entity;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator implements PDFInterface {
    private List<CommentProjection> comments;
    private Document documentPDF;
    private String path = "GeneratorPDF/Comments.pdf";

    public PDFGenerator(List<CommentProjection> comments) throws FileNotFoundException {
        this.comments = comments;
        this.documentPDF = new Document();

        PdfWriter.getInstance(this.documentPDF, new FileOutputStream(path));
        documentPDF.open();
    }

    @Override
    public void gerarCabecalho() {
        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.add(
                new Chunk(
                        "Comentários de Postagem",
                        new Font(Font.HELVETICA, 24)
                )
        );

        this.documentPDF.add(paragrafoTitulo);
        this.documentPDF.add(new Paragraph(" "));

        Paragraph quantcomments = new Paragraph();
        quantcomments.setAlignment(Element.ALIGN_CENTER);
        quantcomments.add(new Chunk("Quantidade de comentários: " + String.valueOf(this.comments.size())));
        this.documentPDF.add(quantcomments);

        Paragraph paragrafoSessao = new Paragraph("--------------------------------------------");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentPDF.add(paragrafoSessao);
        this.documentPDF.add(new Paragraph(" "));
    }

    @Override
    public void gerarCorpo() {
        for (CommentProjection comment: comments) {
            Paragraph commentText = new Paragraph();
            commentText.add(
                    new Chunk(
                            comment.getCommentText(),
                            new Font(Font.COURIER, 14)
                    )
            );

            Paragraph createdAt = new Paragraph();
            createdAt.add(
                    new Chunk(
                            String.valueOf(comment.getCreatedAt()),
                            new Font(Font.COURIER, 14)
                    )
            );

            this.documentPDF.add(commentText);
            this.documentPDF.add(createdAt);
            this.documentPDF.add(new Paragraph("--------------------------------------------"));
        }

        // Gerando tempo atual que o PDF foi gerado
        Paragraph time = new Paragraph();
        time.setAlignment(Element.ALIGN_RIGHT);
        time.add(
                new Chunk(
                        "PDF gerado em: " + dateFormat(),
                        new Font(Font.TIMES_ROMAN, 12)
                )
        );
        this.documentPDF.add(time);
    }

    @Override
    public void gerarRodape() {
        Paragraph paragrafoSessao = new Paragraph("--------------------------------------------");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentPDF.add(paragrafoSessao);
        this.documentPDF.add(new Paragraph(" "));

        Paragraph pRodape = new Paragraph();
        pRodape.setAlignment(Element.ALIGN_CENTER);
        pRodape.add(
                new Chunk(
                        "FlashG",
                        new Font(Font.TIMES_ROMAN, 14)
                )
        );

        this.documentPDF.add(pRodape);
    }

    @Override
    public ResponseEntity<Resource> imprimir() {
        if (this.documentPDF != null && this.documentPDF.isOpen()) {
            this.documentPDF.close();
        }

        return null;
    }

    public String dateFormat() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }
}

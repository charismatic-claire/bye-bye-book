package org.byebyebook.bbbserver.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import org.byebyebook.bbbserver.config.BbbConstants;
import org.byebyebook.bbbserver.config.PdfConfig;
import org.byebyebook.bbbserver.pdfcreation.PDFCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
public class GenerateByeByeBookController
{
    private final PDFCreator pdfCreator;

    @Autowired
    public GenerateByeByeBookController(final PDFCreator pdfCreator)
    {
        this.pdfCreator = pdfCreator;
    }

    @GetMapping(value = BbbConstants.GENERATE_ROUTE, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> generateByeByeBook() throws IOException
    {
        //generate and load ByeByeBook.pdf
        pdfCreator.generatePdfFile();
        final File byebyeBook = new File(PdfConfig.BYEBYEBOOK_PDF_PATH);

        //prevent caching so book is generated from scratch on each call
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        //support pdf delivery in browser and name resulting file
        headers.put("Content-Disposition", Collections.singletonList("attachment; filename=ByeByeBook.pdf"));

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentLength(byebyeBook.length())
            .contentType(MediaType.valueOf("application/pdf"))
            .body(new InputStreamResource(new FileInputStream(byebyeBook)));
    }
}

package org.byebyebook.bbbserver.pdfcreation;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.byebyebook.bbbserver.config.PdfConfig;
import org.byebyebook.bbbserver.model.Posting;
import org.byebyebook.bbbserver.model.Story;
import org.byebyebook.bbbserver.service.ImageStorageService;
import org.byebyebook.bbbserver.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PDFCreator
{
    private final ImageStorageService imageStorageService;
    private final PostingService postingService;

    @Autowired
    public PDFCreator(final ImageStorageService imageStorageService, final PostingService postingService)
    {
        this.imageStorageService = imageStorageService;
        this.postingService = postingService;
    }

    public void generatePdfFile()
    {
        final List<Posting> postings = postingService.getAll();
        try
        {
            final Document document = new Document();
            final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PdfConfig.BYEBYEBOOK_PDF_PATH));
            document.open();
            for (final Posting posting : postings)
            {
                //load images and theme
                final List<String> postingImages = resolveImagePaths(posting);
                final PDFTheme theme = PDFTheme.findByNumberOfImages(postingImages.size());

                //set background
                writer.setPageEvent(new PDFBackground(Image.getInstance(getClass().getClassLoader().getResource(theme.getBackgroundPath()))));

                //add from/to header
                final Paragraph fromTo = new Paragraph();
                fromTo.add(new Paragraph(String.format(PDFLabel.FROM_TO.getText(), posting.getFromName(), posting.getToName()),
                    theme.getHeaderFont()));
                document.add(fromTo);

                //add stories
                for (final Story story : posting.getStories())
                {
                    final Paragraph preface = new Paragraph();
                    addEmptyLine(preface, 1);
                    preface.add(new Paragraph(PDFLabel.findByTitle(story.getTitle()).getText() + "\n", theme.getBoldFont()));
                    preface.add(new Paragraph(story.getBody(), theme.getDefaultFont()));
                    document.add(preface);
                }

                //add email contact
                final Paragraph contact = new Paragraph();
                addEmptyLine(contact, 1);
                contact.add(new Paragraph(String.format(PDFLabel.CONTACT.getText(), posting.getFromEmail()), theme.getDefaultFont()));
                document.add(contact);

                //add images
                for (final String imagePath : postingImages)
                {
                    final Image img = Image.getInstance(imagePath);
                    img.scaleToFit(512f, 512f);
                    document.add(img);
                }

                // insert page break
                document.newPage();
            }
            document.close();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addEmptyLine(final Paragraph paragraph, final int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }

    private List<String> resolveImagePaths(final Posting posting)
    {
        return posting.getImageUris().stream()
            .map(uri -> imageStorageService.resolveImageStoragePath(uri.getUrl()))
            .collect(Collectors.toList());
    }
}

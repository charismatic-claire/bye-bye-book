package org.byebyebook.bbbserver.pdfcreation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFBackground extends PdfPageEventHelper
{
    private final Image backgroundImage;

    PDFBackground(final Image backgroundImage)
    {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public void onEndPage(final PdfWriter writer, final Document document)
    {
        try
        {
            // This scales the image to the page, use the image's width & height if you don't want to scale.
            final float width = document.getPageSize().getWidth();
            final float height = document.getPageSize().getHeight();
            writer.getDirectContentUnder().addImage(backgroundImage, width, 0, 0, height, 0, 0);
            //TODO styling with css, possibly use templates instead
        }
        catch (final DocumentException e)
        {
            e.printStackTrace();
        }
    }
}

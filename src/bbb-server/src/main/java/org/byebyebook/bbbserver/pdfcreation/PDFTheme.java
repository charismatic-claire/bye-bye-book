package org.byebyebook.bbbserver.pdfcreation;

import java.util.EnumSet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;


public enum PDFTheme
{
    //TODO style fonts and colors
    NONE("pdftemplateresources/polkadots.png",
        new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL),
        0),
    ONE("pdftemplateresources/stripes.png",
        new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL),
        1),
    TWO("pdftemplateresources/swirl.png",
        new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL),
        2),
    THREE("pdftemplateresources/trippy.png",
        new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.RED),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL),
        3),
    FOUR("pdftemplateresources/triangles.png",
        new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.GRAY),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD),
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL),
        4);

    private final String themeBackgroundPath;
    private final Font headerFont;
    private final Font boldFont;
    private final Font defaultFont;
    private final int numberOfImages;

    private PDFTheme(final String themeBackgroundPath, final Font headerFont, final Font boldFont, final Font defaultFont, final int numberOfImages)
    {
        this.themeBackgroundPath = themeBackgroundPath;
        this.headerFont = headerFont;
        this.boldFont = boldFont;
        this.defaultFont = defaultFont;
        this.numberOfImages = numberOfImages;
    }

    private static final EnumSet<PDFTheme> ALL = EnumSet.allOf(PDFTheme.class);

    public static PDFTheme findByNumberOfImages(final int numberOfImages)
    {
        return ALL.stream()
            .filter(theme -> theme.getNumberOfImages() == numberOfImages)
            .findFirst().orElseThrow(() ->
                new IllegalArgumentException("Illegal number of images, no theme found"));
    }

    public String getBackgroundPath()
    {
        return themeBackgroundPath;
    }

    public Font getHeaderFont()
    {
        return headerFont;
    }

    public Font getBoldFont()
    {
        return boldFont;
    }

    public Font getDefaultFont()
    {
        return defaultFont;
    }

    public int getNumberOfImages()
    {
        return numberOfImages;
    }
}

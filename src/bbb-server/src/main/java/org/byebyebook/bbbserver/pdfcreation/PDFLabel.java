package org.byebyebook.bbbserver.pdfcreation;

import java.util.EnumSet;


public enum PDFLabel
{
//    FROM_TO("Was %s über %s sagt"),
//    CONTACT("Ich würde mich freuen von eurem Abenteuer zu hören! Du erreichst mich unter: %s"),
//    WANNA_DO_NEVER_DID("Bisher verpasst - Etwas das wir schon immer hätten unternehmen sollen und hoffentlich noch tun werden:"),
//    SURPRISE("Überraschende Fakten - Ein spannender, noch unbekannter Fakt über mich:"),
//    LIKED("Das Beste - Was ich besonders an dir mag:"),
//    DISLIKED("Das Letzte - Es läuft nicht immer alles nach Plan, das sollten wir nicht wiederholen:"),
//    WILL_MISS("Vermissen - Am meisten an dir vermissen werde ich:"),
//    SECRET_TO_SHARE("Geheimnis - Was ich dir schon immer mal anvertrauen wollte:"),
//    GOODBYE("Letzte Worte - Das möchte ich dir auf deine Reise mitgeben:");

    FROM_TO("What %s says about %s"),
    CONTACT("I would like to stay in touch with you. You can get to me using the following address: %s"),
    WANNA_DO_NEVER_DID("Wanna do but never did:"),
    SURPRISE("Surprising facts:"),
    LIKED("The best - What I like the most about you:"),
    DISLIKED("The worst - Sometimes, things do not play out exactly as you wanted them to. What we do not have to do again:"),
    WILL_MISS("Missing the most:"),
    SECRET_TO_SHARE("Secret - A secret I always wanted to share with you but never found the time to do so:"),
    GOODBYE("Last words:");

    private final String text;

    private PDFLabel(final String text)
    {
        this.text = text;
    }

    private static final EnumSet<PDFLabel> ALL = EnumSet.allOf(PDFLabel.class);

    public static PDFLabel findByTitle(final String title)
    {
        return ALL.stream()
            .filter(theme -> theme.name().equals(title))
            .findFirst().orElseThrow(() ->
                new IllegalArgumentException("Title not known, no matching label found"));
    }

    public String getText()
    {
        return text;
    }
}

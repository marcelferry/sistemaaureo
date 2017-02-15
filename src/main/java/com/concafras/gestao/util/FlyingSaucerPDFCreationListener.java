package com.concafras.gestao.util;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFCreationListener;

public class FlyingSaucerPDFCreationListener implements PDFCreationListener {
    MyPDFPageEvents pdfPageEvents = null;

    public FlyingSaucerPDFCreationListener(MyPDFPageEvents mppe) {
        pdfPageEvents=mppe;
    }

    @Override
    public void preOpen(ITextRenderer iTextRenderer) {
        System.out.println("preOpen("+iTextRenderer+")");
        // set the PdfPageEvent instance to the PdfWriter
        iTextRenderer.getWriter().setPageEvent(pdfPageEvents);
    }

    @Override
    public void preWrite(ITextRenderer iTextRenderer, int pageCount) {
        System.out.println("preWrite("+iTextRenderer+", "+pageCount+")");
    }

    @Override
    public void onClose(ITextRenderer renderer) {
        System.out.println("onClose("+renderer+")");
    }

}
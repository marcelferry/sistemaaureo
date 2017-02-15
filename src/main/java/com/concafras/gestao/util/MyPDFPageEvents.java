package com.concafras.gestao.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class MyPDFPageEvents extends PdfPageEventHelper {
  
    public void onStartPage(PdfWriter writer, Document document) {
        System.out.println("onStartPage("+writer+","+document+")");
        document.setPageSize(PageSize.A4);
        document.setMargins(5f, 5f, 5f, 5f);
    }
}
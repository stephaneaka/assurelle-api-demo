package com.devolution.assurelle_api.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;


public class PdfRenderer {

	public static byte[] render(String html){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(html, output);
		return output.toByteArray();  
    }
}

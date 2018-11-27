package org.eclipse.birt.report.engine.emitter.epdf;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.RenderOptionBase;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterAdapter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfExtReportEmitter extends ContentEmitterAdapter { // PDFLayoutEmitter

  // ... constants

  private static final Font TITLE_FONT = new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLACK);
  private static final Font NORMAL_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
  private static final Font HIDDEN_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.WHITE);

  private static final String TEXT__LOREM_IPSUM_1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
  private static final String TEXT__LOREM_IPSUM_2 = "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis a";
  private static final String REPORT_FILE = null;

  // ... properties

  private OutputStream destOutputStream;

  // ... business methods

  @Override
  public void initialize(final IEmitterServices services) throws BirtException {

    super.initialize(services);

    final Object outFileOption = services.getOption(RenderOptionBase.OUTPUT_FILE_NAME);
    try {

      if (outFileOption != null) {

        final File outFile = new File(outFileOption.toString());

        final File outfileParentDir = outFile.getParentFile();
        if (outfileParentDir != null && !outfileParentDir.exists()) {

          outfileParentDir.mkdirs();
        }

        destOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
      }
    } catch (final Exception ex) {

      ex.printStackTrace();
    }

    if (destOutputStream == null) {

      final Object outStreamOption = services.getOption(RenderOptionBase.OUTPUT_STREAM);
      if (outStreamOption instanceof OutputStream) {

        destOutputStream = (OutputStream) outStreamOption;
      } else {

        try {

          final File outFile = new File(REPORT_FILE);
          destOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
        } catch (final FileNotFoundException ex) {
          ex.printStackTrace();
        }
      }
    }

    System.out.println("PdfExtReportEmitter # initialize(..)");
  }

  @Override
  public void start(final IReportContent report) throws BirtException {

    super.start(report);

    System.out.println("PdfExtReportEmitter # start(..)");

    try {

      example0_Create_Pdf_With_PlaceHolders_For_SignatureFields(destOutputStream, NORMAL_FONT);
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void end(final IReportContent report) throws BirtException {

    super.end(report);

    System.out.println("PdfExtReportEmitter # end(..)");

    if (destOutputStream != null) {

      try {
        destOutputStream.close();
      } catch (final Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  // ... helper methods

  public static void example0_Create_Pdf_With_PlaceHolders_For_SignatureFields( //
      final OutputStream destOutputStream, //
      final Font placeHolderFont //
      ) throws DocumentException {

    final Document document = new Document();
    final PdfWriter pdfWriter = PdfWriter.getInstance(document, destOutputStream);

    pdfWriter.setCompressionLevel(0);
    pdfWriter.setInitialLeading(16);

    document.open();

    document.add(new Chunk("Beratungsprotokoll", TITLE_FONT));

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    document.add(new Phrase(TEXT__LOREM_IPSUM_1));
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(new Phrase(TEXT__LOREM_IPSUM_2));

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    final PdfPTable pdfTable = new PdfPTable(5);

    pdfTable.setWidths(new int[] { 6, 2, 6, 2, 6 });
    pdfTable.setWidthPercentage(100);

    pdfTable.addCell(createSignaturePlaceHolderCell("Unterschrift 1", placeHolderFont));
    pdfTable.addCell(createSignatureDelimiterCell());
    pdfTable.addCell(createSignaturePlaceHolderCell("Unterschrift 2", placeHolderFont));
    pdfTable.addCell(createSignatureDelimiterCell());
    pdfTable.addCell(createSignaturePlaceHolderCell("Unterschrift 3", placeHolderFont));

    document.add(pdfTable);

    document.close();
  }

  private static PdfPCell createSignatureDelimiterCell() {

    final PdfPCell cell = new PdfPCell();

    cell.setBorder(Rectangle.NO_BORDER);
    cell.addElement(new Phrase(" "));

    return cell;
  }

  private static PdfPCell createSignaturePlaceHolderCell(final String underlineText, final Font placeHolderFont) {

    final PdfPCell cell = new PdfPCell();

    cell.setBorder(Rectangle.TOP);
    cell.setPadding(0f);
    cell.setPaddingLeft(5f);

    final Phrase signaturePlaceHolder = createSignaturePlaceHolderPhrase(underlineText, placeHolderFont);
    cell.addElement(signaturePlaceHolder);

    return cell;
  }

  private static Phrase createSignaturePlaceHolderPhrase(final String underlineText, final Font placeHolderFont) {

    final Phrase phrase = new Phrase(underlineText, placeHolderFont);
    return phrase;
  }

}

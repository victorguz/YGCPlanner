package archivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.DAOException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controlador.Controller;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Referencia;
import modelo.cliente.Medida;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class PDF {

    private Medida medida = new Medida();
    private Plan dieta = new Plan();
    private Plan rutina = new Plan();
    private File file;
    private Document document;

    public PDF() {
    }

    public PDF(Medida medida) throws DAOException, IOException, FileNotFoundException, DocumentException {
        setMedida(medida);
        setFile("");
    }

    public PDF(Medida medida, String url) throws DAOException, IOException {
        setMedida(medida);
        setFile(url);
    }

    public void setFile(String url) throws IOException {
        if (url.isEmpty()) {
            url = System.getProperty("user.home") + "\\Desktop\\" + getMedida().getCliente().getNombre() + "_" + getMedida().getCliente().getApellido() + ".pdf";
        }
        this.file = new File(url);
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
    }

    public File getFile() {
        return file;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public Plan getDieta() {
        return dieta;
    }

    public void setDieta(Plan dieta) {
        this.dieta = dieta;
    }

    public Plan getRutina() {
        return rutina;
    }

    public void setRutina(Plan rutina) {
        this.rutina = rutina;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    //YGC Fonts
    public static Font getFont(String nombre, int size, int red, int green, int blue) {
        try {
            File quantify;
            if (nombre.equalsIgnoreCase("regular")) {
                quantify = new File("src/fonts/Roboto-Regular.ttf");
            } else if (nombre.equalsIgnoreCase("thin")) {
                quantify = new File("src/fonts/Roboto-Thin.ttf");
            } else if (nombre.equalsIgnoreCase("thin-italic")) {
                quantify = new File("src/fonts/Roboto-ThinItalic.ttf");
            } else if (nombre.equalsIgnoreCase("medium")) {
                quantify = new File("src/fonts/Roboto-Medium.ttf");
            } else if (nombre.equalsIgnoreCase("medium-italic")) {
                quantify = new File("src/fonts/Roboto-MediumItalic.ttf");
            } else if (nombre.equalsIgnoreCase("light")) {
                quantify = new File("src/fonts/Roboto-Light.ttf");
            } else if (nombre.equalsIgnoreCase("light-italic")) {
                quantify = new File("src/fonts/Roboto-LightItalic.ttf");
            } else if (nombre.equalsIgnoreCase("italic")) {
                quantify = new File("src/fonts/Roboto-Italic.ttf");
            } else if (nombre.equalsIgnoreCase("bold")) {
                quantify = new File("src/fonts/Roboto-Bold.ttf");
            } else if (nombre.equalsIgnoreCase("bold-italic")) {
                quantify = new File("src/fonts/Roboto-BoldItalic.ttf");
            } else if (nombre.equalsIgnoreCase("black")) {
                quantify = new File("src/fonts/Roboto-Black.ttf");
            } else if (nombre.equalsIgnoreCase("black-italic")) {
                quantify = new File("src/fonts/Roboto-BlackItalic.ttf");
            } else {
                quantify = new File("src/fonts/Roboto-Regular.ttf");
            }
            BaseFont base = BaseFont.createFont(quantify.getAbsolutePath(), BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font font = new Font(base);
            font.setSize(size);
            font.setColor(red, green, blue);
            return font;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void createPDF() throws FileNotFoundException, DocumentException, IOException, DAOException {
        Referencia ins = Controller.getReferencias().obtener("insta1");
        Referencia fb = Controller.getReferencias().obtener("face1");
        Referencia tel = Controller.getReferencias().obtener("tel1");

        setDocument(new Document(PageSize.LETTER, 0, 0, 0, 0));
        PdfWriter.getInstance(getDocument(), new FileOutputStream(file));
        getDocument().open();
        getDocument().addTitle("Plan de entrenamiento y alimentacion");
        getDocument().addAuthor("Yezid Guzman Coach");
        getDocument().addCreator("Yezid Guzman Coach");
        //Edición del archivo:
        Chapter chapter = new Chapter(1);
        chapter.setNumberDepth(0);
        Image black;
        black = Image.getInstance(new File("src/imagen/black.jpg").toURL());
        black.scaleAbsolute(PageSize.LETTER);
        black.setAbsolutePosition(0, 0);
        chapter.add(black);
        PdfPTable table = new PdfPTable(3);
        Font fuente = getFont("bold", 15, 230, 230, 230);

        //Este ciclo nos ubica al final de la pagina
        for (int i = 0; i < 81; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            Paragraph p = new Paragraph(" ", fuente);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            table.addCell(cell);
        }

        Paragraph tIg = new Paragraph("Ig:", fuente);
        tIg.setAlignment(Element.ALIGN_CENTER);
        PdfPCell tIgCell = new PdfPCell();
        tIgCell.addElement(tIg);
        tIgCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(tIgCell);

        Paragraph tFb = new Paragraph("Fb:", fuente);
        tFb.setAlignment(Element.ALIGN_CENTER);
        PdfPCell tFbCell = new PdfPCell();
        tFbCell.addElement(tFb);
        tFbCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(tFbCell);

        Paragraph tTel = new Paragraph("Tel:", fuente);
        tTel.setAlignment(Element.ALIGN_CENTER);
        PdfPCell tTelCell = new PdfPCell();
        tTelCell.addElement(tTel);
        tTelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(tTelCell);

        Paragraph dIns = new Paragraph(ins.getDato(), fuente);
        dIns.setAlignment(Element.ALIGN_CENTER);
        PdfPCell dInsCell = new PdfPCell();
        dInsCell.addElement(dIns);
        dInsCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(dInsCell);

        Paragraph dFb = new Paragraph(fb.getDato(), fuente);
        dFb.setAlignment(Element.ALIGN_CENTER);
        PdfPCell dFbCell = new PdfPCell();
        dFbCell.addElement(dFb);
        dFbCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(dFbCell);

        Paragraph dTel = new Paragraph(tel.getDato(), fuente);
        dTel.setAlignment(Element.ALIGN_CENTER);
        PdfPCell dTelCell = new PdfPCell();
        dTelCell.addElement(dTel);
        dTelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(dTelCell);

        chapter.add(table);
        getDocument().add(chapter);
    }

    public void addBienvenida() throws DocumentException, MalformedURLException, BadElementException, IOException, DAOException {
        Referencia titulo = Controller.getReferencias().obtener("titulobienvenida");
        Referencia bienvenida = Controller.getReferencias().obtener("bienvenida");

        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/bienvenida.jpg").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        PdfPTable table = new PdfPTable(1);
        Font fuente = getFont("bold", 18, 230, 230, 230);

        //Celda en blanco añadida para ocupar espacio
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        Paragraph p = new Paragraph(" ", fuente);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);

        //Este ciclo nos ubica al final de la pagina
        for (int i = 0; i < 16; i++) {

            table.addCell(cell);
        }

        Paragraph pTitulo = new Paragraph(titulo.getDato().toUpperCase(), fuente);
        pTitulo.setAlignment(Element.ALIGN_CENTER);
        PdfPCell pTituloCell = new PdfPCell();
        pTituloCell.addElement(pTitulo);
        pTituloCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(pTituloCell);

        table.addCell(cell);
        table.addCell(cell);

        Paragraph pBienvenida = new Paragraph("\n"+bienvenida.getDato().toUpperCase(), getFont("regular", 14, 230, 230, 230));
        pBienvenida.setAlignment(Element.ALIGN_CENTER);
        PdfPCell pBienvenidaCell = new PdfPCell();
        pBienvenidaCell.addElement(pBienvenida);
        pBienvenidaCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(pBienvenidaCell);

        chapter.add(table);
        getDocument().add(chapter);
    }

    public void addRutina(Plan rutina) throws DocumentException, MalformedURLException, BadElementException, IOException {
        setRutina(rutina);
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/entrenamiento.jpg").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17, 0, 0, 0));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        document.add(chapter);
    }

    public void addDieta(Plan dieta) throws DocumentException, MalformedURLException, BadElementException, IOException {
        setRutina(dieta);
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/alimentacion.jpg").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17, 0, 0, 0));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        document.add(chapter);
    }

    public void addMedidas() throws DocumentException, MalformedURLException, BadElementException, IOException {
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/medidas.jpg").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17, 0, 0, 0));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        document.add(chapter);
    }

    public void close() throws IOException {
        document.close();
        Desktop.getDesktop().open(file);
    }
}

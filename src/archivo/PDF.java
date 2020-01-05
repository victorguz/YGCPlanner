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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.cliente.Cliente;
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

    public void setMedida(Medida medida){
        this.medida = medida;
    }

    public Plan getDieta() {
        return dieta;
    }

    public void setDieta(Plan dieta){
        this.dieta = dieta;
    }
public Plan getRutina() {
        return rutina;
    }

    public void setRutina(Plan rutina){
        this.rutina = rutina;
    }
    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    //YGC Fonts
    public static Font getFont(String nombre, int size) {
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
            return font;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void createPDF() throws FileNotFoundException, DocumentException, IOException {
        document = new Document(PageSize.LETTER, 50, 22, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        //Modificar metadatos del archivo:
        document.addTitle("Plan " + getMedida().getCliente().getNombre() + " " + getMedida().getCliente().getApellido());
        document.addAuthor("Yezid Guzman Coach");
        document.addCreator("Yezid Guzman Coach");
        //Edición del archivo:
        Chapter chapter = new Chapter(1);
        chapter.setNumberDepth(0);
        Image black;
        black = Image.getInstance(new File("src/imagen/black.png").toURL());
        black.scaleAbsolute(PageSize.LETTER);
        black.setAbsolutePosition(0, 0);
        chapter.add(black);

        Paragraph subInfo = new Paragraph("Información del cliente", getFont("bold", 14));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setIndentationLeft(345);
        subInfo.setSpacingBefore(65);
        subInfo.setSpacingAfter(10);
        chapter.add(subInfo);
        document.add(chapter);
        addBienvenida();
    }

    public void addBienvenida() throws DocumentException, MalformedURLException, BadElementException, IOException {
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/bienvenida.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        document.add(chapter);
    }

    public void addRutina(Plan rutina) throws DocumentException, MalformedURLException, BadElementException, IOException {
        setRutina(rutina);
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/entrenamiento.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17));
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
        page = Image.getInstance(new File("src/imagen/alimentacion.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17));
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        document.add(chapter);
    }

    public void addMedidas() throws DocumentException, MalformedURLException, BadElementException, IOException {
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/imagen/medidas.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", getFont("black", 17));
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

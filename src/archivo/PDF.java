package archivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.DAOException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import modelo.Referencia;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class PDF {

    private Cliente cliente = new Cliente();
    private Plan dieta = new Plan();
    private Plan rutina = new Plan();
    private File file;
    private Document document;

    public PDF() throws IOException {
        setFile("");
    }

    public PDF(Cliente cliente) throws IOException {
        setCliente(cliente);
        setFile("");
    }

    public PDF(Cliente cliente, String url) throws DAOException, IOException {
        setCliente(cliente);
        setFile(url);
    }

    public void setFile(String url) throws IOException {
        if (url.isEmpty()) {
            url = System.getProperty("user.home") + "\\Desktop\\" + getCliente().getNombre() + "_" + getCliente().getApellido() + ".pdf";
        }
        this.file = new File(url);
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
    }

    public File getFile() {
        return file;
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

        Paragraph white = new Paragraph(" ");
        white.setSpacingAfter(705);
        chapter.add(white);

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
        Font fuente = getFont("bold", 18, 230, 230, 230);

        Paragraph pTitulo = new Paragraph(((titulo.getDato().length() > 37)
                ? titulo.getDato().substring(0, 37)
                : titulo.getDato())
                .toUpperCase(), fuente);
        pTitulo.setSpacingBefore(485);
        pTitulo.setAlignment(Element.ALIGN_CENTER);

        chapter.add(pTitulo);

        Paragraph pBienvenida = new Paragraph("\n"
                + ((bienvenida.getDato().length() > 305)
                        ? bienvenida.getDato().substring(0, 305)
                        : bienvenida.getDato())
                        .toUpperCase(),
                getFont("thin", 14, 230, 230, 230));
        pBienvenida.setSpacingBefore(40);
        pBienvenida.setIndentationLeft(100);
        pBienvenida.setIndentationRight(100);
        pBienvenida.setAlignment(Element.ALIGN_CENTER);

        pBienvenida.setSpacingAfter(54);
        chapter.add(pBienvenida);
        chapter.add(new Paragraph(" "));

        getDocument().add(chapter);
    }

    public void addMedidas() throws DocumentException, MalformedURLException, BadElementException, IOException, DAOException {
        if (getCliente() != null) {
            ObservableList<Medida> medidas = Controller.getMedidas().obtenerTodos(getCliente().getClienteKey() + "");
            if (!medidas.isEmpty()) {
                Chapter chapter = new Chapter(2);
                chapter.setNumberDepth(0);
                Image page;
                page = Image.getInstance(new File("src/imagen/medidas.jpg").toURL());
                page.scaleAbsolute(PageSize.LETTER);
                page.setAbsolutePosition(0, 0);
                chapter.add(page);

                Font fuente = getFont("light", 10, 30, 30, 30);
                //Informacion del cliente
                //NOMBRE
                Paragraph p = new Paragraph(
                        (getCliente().getNombre()
                                + " "
                                + getCliente().getApellido())
                                .toUpperCase(), getFont("bold", 13, 30, 30, 30));
                p.setAlignment(Element.ALIGN_CENTER);
                p.setSpacingBefore(90);
                p.setSpacingAfter(20);
                chapter.add(p);

                //Tabla con información del cliente
                PdfPTable tablaCliente = new PdfPTable(4);
                //Titulos para datos del cliente
                PdfPCell cellComplexion = new PdfPCell();
                Paragraph pComplexion = new Paragraph("COMPLEXION FISICA", getFont("bold", 11, 230, 230, 230));
                cellComplexion.setBackgroundColor(new BaseColor(24, 22, 33));
                pComplexion.setAlignment(Element.ALIGN_CENTER);
                cellComplexion.addElement(pComplexion);
                tablaCliente.addCell(cellComplexion);

                PdfPCell cellEdad = new PdfPCell();
                Paragraph pEdad = new Paragraph("EDAD", getFont("bold", 11, 230, 230, 230));
                cellEdad.setBackgroundColor(new BaseColor(24, 22, 33));
                pEdad.setAlignment(Element.ALIGN_CENTER);
                cellEdad.addElement(pEdad);
                tablaCliente.addCell(cellEdad);

                PdfPCell cellAltura = new PdfPCell();
                Paragraph pAltura = new Paragraph("ALTURA", getFont("bold", 11, 230, 230, 230));
                cellAltura.setBackgroundColor(new BaseColor(24, 22, 33));
                pAltura.setAlignment(Element.ALIGN_CENTER);
                cellAltura.addElement(pAltura);
                tablaCliente.addCell(cellAltura);

                PdfPCell cellSexo = new PdfPCell();
                Paragraph pSexo = new Paragraph("SEXO", getFont("bold", 11, 230, 230, 230));
                cellSexo.setBackgroundColor(new BaseColor(24, 22, 33));
                pSexo.setAlignment(Element.ALIGN_CENTER);
                cellSexo.addElement(pSexo);
                tablaCliente.addCell(cellSexo);

                //Datos del cliente
                PdfPCell cellDComplexion = new PdfPCell();
                Paragraph pDComplexion = new Paragraph(medidas.get(medidas.size() - 1).getComplexionText().toUpperCase(), fuente);
                pDComplexion.setAlignment(Element.ALIGN_CENTER);
                cellDComplexion.addElement(pDComplexion);
                tablaCliente.addCell(cellDComplexion);

                PdfPCell cellDEdad = new PdfPCell();
                Paragraph pDEdad = new Paragraph(getCliente().getEdad() + "", fuente);
                pDEdad.setAlignment(Element.ALIGN_CENTER);
                cellDEdad.addElement(pDEdad);
                tablaCliente.addCell(cellDEdad);

                PdfPCell cellDAltura = new PdfPCell();
                Paragraph pDAltura = new Paragraph(medidas.get(medidas.size() - 1).getAltura() + "", fuente);
                pDAltura.setAlignment(Element.ALIGN_CENTER);
                cellDAltura.addElement(pDAltura);
                tablaCliente.addCell(cellDAltura);

                PdfPCell cellDSexo = new PdfPCell();
                Paragraph pDSexo = new Paragraph(getCliente().getSexo().toUpperCase(), fuente);
                pDSexo.setAlignment(Element.ALIGN_CENTER);
                cellDSexo.addElement(pDSexo);
                tablaCliente.addCell(cellDSexo);

                chapter.add(tablaCliente);

                //Informacion de medidas
                //Titulo
                Paragraph k = new Paragraph("MEDIDAS Y ESTADO FÍSICO",
                        getFont("bold", 12, 30, 30, 30));
                k.setAlignment(Element.ALIGN_CENTER);
                k.setSpacingBefore(15);
                k.setSpacingAfter(20);
                chapter.add(k);
                //Tabla de medidas
                PdfPTable tablaMedidas = null;
                //Titulos de medidas

                if (medidas.size() == 1) {
                    tablaMedidas = new PdfPTable(2);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("Detalle", getFont("bold", 11, 230, 230, 230));
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    cellMedida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("Medida 1", getFont("bold", 12, 230, 230, 230));
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (String[] toArray : medidas.get(0).toArray()) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(toArray[0], fuente);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Dato de medida
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(toArray[1], fuente);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);

                    }
                }
                if (medidas.size() == 2) {
                    tablaMedidas = new PdfPTable(3);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("Detalle", getFont("bold", 11, 230, 230, 230));
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("Medida 1", getFont("bold", 11, 230, 230, 230));
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("Medida actual", getFont("bold", 11, 230, 230, 230));
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (int i = 0; i < medidas.get(0).toArray().length; i++) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(medidas.get(0).toArray()[i][0], fuente);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Datos de medida medida 1
                        PdfPCell cell1 = new PdfPCell();
                        Paragraph l1 = new Paragraph(medidas.get(1).toArray()[i][1], fuente);
                        l1.setAlignment(Element.ALIGN_CENTER);
                        cell1.addElement(l1);
                        tablaMedidas.addCell(cell1);

                        //Datos de medida medida 2
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(medidas.get(0).toArray()[i][1], fuente);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);

                    }
                }
                if (medidas.size() > 2) {
                    int n = (int) Math.ceil(medidas.size() / 2 + 0.1);
                    tablaMedidas = new PdfPTable(4);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("Detalle", getFont("bold", 11, 230, 230, 230));
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("Medida 1", getFont("bold", 11, 230, 230, 230));
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaIntermedio = new PdfPCell();
                    Paragraph pMedidaIntermedio = new Paragraph("Medida " + n, getFont("bold", 11, 230, 230, 230));
                    cellMedidaIntermedio.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaIntermedio.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaIntermedio.addElement(pMedidaIntermedio);
                    tablaMedidas.addCell(cellMedidaIntermedio);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("Medida actual ("+medidas.size()+")", getFont("bold", 11, 230, 230, 230));
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (int i = 0; i < medidas.get(0).toArray().length; i++) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(medidas.get(0).toArray()[i][0], fuente);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Datos de medida medida 1
                        PdfPCell cell1 = new PdfPCell();
                        Paragraph l1 = new Paragraph(medidas.get(medidas.size()-1).toArray()[i][1], fuente);
                        l1.setAlignment(Element.ALIGN_CENTER);
                        cell1.addElement(l1);
                        tablaMedidas.addCell(cell1);

                        //Datos de medida medida 2
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(medidas.get(n-1).toArray()[i][1], fuente);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);
                        
                        //Datos de medida medida 3
                        PdfPCell cell3 = new PdfPCell();
                        Paragraph l3 = new Paragraph(medidas.get(0).toArray()[i][1], fuente);
                        l3.setAlignment(Element.ALIGN_CENTER);
                        cell3.addElement(l3);
                        tablaMedidas.addCell(cell3);
                    }

                }
                if (tablaMedidas != null) {
                    chapter.add(tablaMedidas);
                }
                document.add(chapter);
            } else {
                Controller.mensaje("Este cliente no tiene medidas", "aviso");
            }
        }
    }

    public void addRutina() throws DocumentException, MalformedURLException, BadElementException, IOException {
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

    public void addDieta() throws DocumentException, MalformedURLException, BadElementException, IOException {
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

    public void close() throws IOException {
        document.close();
        Desktop.getDesktop().open(file);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

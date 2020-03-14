package archivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.DAOException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controlador.Controller;
import controlador.Operacion;
import javafx.collections.ObservableList;
import modelo.Referencia;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.AlxDiet;
import modelo.plan.EjxRut;
import modelo.plan.Plan;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 201621279487
 */
public class PDF {

    private Cliente cliente = new Cliente();
    private File file;
    private Document document;
    private Chapter chapter;

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

    //YGC Fonts
    public static Font getFont(String nombre, int size, int red, int green, int blue) {
        try {
            File quantify;
            if (nombre.equalsIgnoreCase("regular")) {
                quantify = new File("src/fonts/Roboto-Regular.ttf");
            } else if (nombre.equalsIgnoreCase("thin")) {
                quantify = new File("src/fonts/Roboto-Thin.ttf");
            } else if (nombre.equalsIgnoreCase("medium")) {
                quantify = new File("src/fonts/Roboto-Medium.ttf");
            } else if (nombre.equalsIgnoreCase("light")) {
                quantify = new File("src/fonts/Roboto-Light.ttf");
            } else if (nombre.equalsIgnoreCase("bold")) {
                quantify = new File("src/fonts/Roboto-Bold.ttf");
            } else if (nombre.equalsIgnoreCase("black")) {
                quantify = new File("src/fonts/Roboto-Black.ttf");
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

    public File getFile() {
        return file;
    }

    public void setFile(String url) throws IOException {
        if (url.isEmpty()) {
            url = System.getProperty("user.home") + "\\Desktop";
        }
        this.file = new File(url + "\\" + getCliente().getNombre().toUpperCase() + "_" + getCliente().getApellido().toUpperCase() + ".pdf");
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void createPDF() throws DocumentException, IOException, DAOException {
        Referencia ins = Controller.getReferencias().select("insta1");
        Referencia fb = Controller.getReferencias().select("face1");
        Referencia tel = Controller.getReferencias().select("tel1");

        setDocument(new Document(PageSize.LETTER, 0, 0, 0, 0));
        PdfWriter.getInstance(getDocument(), new FileOutputStream(file));
        getDocument().open();
        getDocument().addTitle("Plan de entrenamiento y alimentacion");
        getDocument().addAuthor("Yezid Guzman Coach");
        getDocument().addCreator("Yezid Guzman Coach");
        //Edición del archivo:
        chapter = new Chapter(1);
        chapter.setNumberDepth(0);
        Image black;
        black = Image.getInstance("src/imagen/black.jpg");
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

    public void addBienvenida() throws DocumentException, IOException, DAOException {
        Referencia titulo = Controller.getReferencias().select("titulobienvenida");
        Referencia bienvenida = Controller.getReferencias().select("bienvenida");

        chapter = new Chapter(0);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance("src/imagen/bienvenida.jpg");
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

    public void addMedidas() throws DocumentException, IOException, DAOException {
        if (getCliente() != null) {
            ObservableList<Medida> medidas = Controller.getMedidas().where(getCliente().getClienteKey() + "");
            if (!medidas.isEmpty()) {
                chapter = new Chapter(0);
                chapter.setNumberDepth(0);
                Image page;
                page = Image.getInstance("src/imagen/medidas.jpg");
                page.scaleAbsolute(PageSize.LETTER);
                page.setAbsolutePosition(0, 0);
                chapter.add(page);

                Font fuente = getFont("light", 10, 30, 30, 30);
                Font bold = getFont("bold", 11, 230, 230, 230);
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
                Paragraph pComplexion = new Paragraph("COMPLEXION FISICA", bold);
                cellComplexion.setBackgroundColor(new BaseColor(24, 22, 33));
                pComplexion.setAlignment(Element.ALIGN_CENTER);
                cellComplexion.addElement(pComplexion);
                tablaCliente.addCell(cellComplexion);

                PdfPCell cellEdad = new PdfPCell();
                Paragraph pEdad = new Paragraph("EDAD", bold);
                cellEdad.setBackgroundColor(new BaseColor(24, 22, 33));
                pEdad.setAlignment(Element.ALIGN_CENTER);
                cellEdad.addElement(pEdad);
                tablaCliente.addCell(cellEdad);

                PdfPCell cellAltura = new PdfPCell();
                Paragraph pAltura = new Paragraph("ALTURA", bold);
                cellAltura.setBackgroundColor(new BaseColor(24, 22, 33));
                pAltura.setAlignment(Element.ALIGN_CENTER);
                cellAltura.addElement(pAltura);
                tablaCliente.addCell(cellAltura);

                PdfPCell cellSexo = new PdfPCell();
                Paragraph pSexo = new Paragraph("SEXO", bold);
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
                    Paragraph pMedida = new Paragraph("Detalle", bold);
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
                    Paragraph pMedida = new Paragraph("Detalle", bold);
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("Medida 1", bold);
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("Medida actual", bold);
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
                    Paragraph pMedida = new Paragraph("Detalle", bold);
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("Medida 1", bold);
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaIntermedio = new PdfPCell();
                    Paragraph pMedidaIntermedio = new Paragraph("Medida " + n, bold);
                    cellMedidaIntermedio.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaIntermedio.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaIntermedio.addElement(pMedidaIntermedio);
                    tablaMedidas.addCell(cellMedidaIntermedio);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("Medida actual (" + medidas.size() + ")", bold);
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
                        Paragraph l1 = new Paragraph(medidas.get(medidas.size() - 1).toArray()[i][1], fuente);
                        l1.setAlignment(Element.ALIGN_CENTER);
                        cell1.addElement(l1);
                        tablaMedidas.addCell(cell1);

                        //Datos de medida medida 2
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(medidas.get(n - 1).toArray()[i][1], fuente);
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

    public void addDieta() throws DocumentException, IOException, DAOException {
        chapter = new Chapter(0);
        Image page;
        page = Image.getInstance("src/imagen/alimentacionT.jpg");
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);

        Font titulo = getFont("bold", 14, 30, 30, 30);
        Font parrafoPeque = getFont("light", 11, 30, 30, 30);

        Plan dieta = Controller.getMedidas().where("" + getCliente().getClienteKey()).get(0).getDieta();

        chapter.setIndentationLeft(100);
        chapter.setIndentationRight(100);

        Paragraph t = new Paragraph(dieta.getNombre().toUpperCase(), titulo);
        t.setSpacingBefore(80);
        t.setAlignment(Element.ALIGN_CENTER);
        chapter.add(t);
        Referencia r = Controller.getReferencias().select("textodieta");
        Paragraph n = new Paragraph("Hola "
                + Operacion.camelCase(getCliente().getNombre()) + r.getDato().toUpperCase(), parrafoPeque);
        n.setSpacingBefore(20);
        n.setAlignment(Element.ALIGN_CENTER);

        chapter.add(n);

        if (!dieta.getDescripcion().isEmpty()) {
            Paragraph d = new Paragraph("DESCRIPCIÓN DEL PLAN: ", titulo);
            d.setSpacingBefore(40);
            d.setAlignment(Element.ALIGN_CENTER);
            Paragraph p = new Paragraph(dieta.getDescripcion().toUpperCase(), parrafoPeque);
            p.setSpacingBefore(20);
            p.setAlignment(Element.ALIGN_CENTER);
            chapter.add(d);

            chapter.add(p);
        }

        document.add(chapter);

        addAlimentos(dieta.getPlankey(), AlxDiet.LUNES);
        addAlimentos(dieta.getPlankey(), AlxDiet.MARTES);
        addAlimentos(dieta.getPlankey(), AlxDiet.MIERCOLES);
        addAlimentos(dieta.getPlankey(), AlxDiet.JUEVES);
        addAlimentos(dieta.getPlankey(), AlxDiet.VIERNES);
        addAlimentos(dieta.getPlankey(), AlxDiet.SABADO);
        addAlimentos(dieta.getPlankey(), AlxDiet.DOMINGO);
    }

    public void addAlimentos(int plankey, String dia) throws DAOException, IOException, DocumentException {
        ObservableList<AlxDiet> list = Controller.getAlxdiets().where(plankey, dia);
        if (!list.isEmpty()) {
            String DESAYUNO = "";
            String ALMUERZO = "";
            String CENA = "";
            String PREENTRENO = "";
            String POSTENTRENO = "";
            String SNACKAM = "";
            String SNACKPM = "";
            for (AlxDiet alx : list) {
                switch (alx.getMomento()) {
                    case AlxDiet.DESAYUNO:
                        DESAYUNO += alx.toString() + ", ";
                        break;
                    case AlxDiet.ALMUERZO:
                        ALMUERZO += alx.toString() + ", ";
                        break;
                    case AlxDiet.CENA:
                        CENA += alx.toString() + ", ";
                        break;
                    case AlxDiet.POSTENTRENO:
                        POSTENTRENO += alx.toString() + ", ";
                        break;
                    case AlxDiet.PREENTRENO:
                        PREENTRENO += alx.toString() + ", ";
                        break;
                    case AlxDiet.SNACKAM:
                        SNACKAM += alx.toString() + ", ";
                        break;
                    case AlxDiet.SNACKPM:
                        SNACKPM += alx.toString() + ", ";
                        break;
                }
            }

            Font tituloPeque = getFont("bold", 12, 30, 30, 30);
            Font parrafo = getFont("light", 10, 30, 30, 30);

            Paragraph t = new Paragraph();
            t.setFont(tituloPeque);
            t.add(dia);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingBefore(60);
            t.setSpacingAfter(10);
            Paragraph p;
            if (!DESAYUNO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.DESAYUNO, tituloPeque));
                p.add(new Paragraph(DESAYUNO + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!SNACKAM.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.SNACKAM, tituloPeque));
                p.add(new Paragraph(SNACKAM + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!ALMUERZO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.ALMUERZO, tituloPeque));
                p.add(new Paragraph(ALMUERZO + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!SNACKPM.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.SNACKPM, tituloPeque));
                p.add(new Paragraph(SNACKPM + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!CENA.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.CENA, tituloPeque));
                p.add(new Paragraph(CENA + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!PREENTRENO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.PREENTRENO, tituloPeque));
                p.add(new Paragraph(PREENTRENO + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!POSTENTRENO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(AlxDiet.POSTENTRENO, tituloPeque));
                p.add(new Paragraph(POSTENTRENO + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            chapter = new Chapter(0);
            Image page;
            page = Image.getInstance("src/imagen/alimentacion.jpg");
            page.scaleAbsolute(PageSize.LETTER);
            page.setAbsolutePosition(0, 0);
            chapter.add(page);

            chapter.setIndentationLeft(60);
            chapter.setIndentationRight(60);
            chapter.add(t);

            document.add(chapter);
        }
    }

    public void addRutina() throws DocumentException, IOException, DAOException {
        chapter = new Chapter(0);
        Image page;
        page = Image.getInstance("src/imagen/entrenamiento.jpg");
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);

        Font titulo = getFont("bold", 14, 30, 30, 30);
        Font parrafoPeque = getFont("light", 11, 30, 30, 30);

        Plan rutina = Controller.getMedidas().where("" + getCliente().getClienteKey()).get(0).getRutina();

        chapter.setIndentationLeft(100);
        chapter.setIndentationRight(100);

        Paragraph t = new Paragraph(rutina.getNombre().toUpperCase(), titulo);
        t.setSpacingBefore(80);
        t.setAlignment(Element.ALIGN_CENTER);
        chapter.add(t);
        Referencia r = Controller.getReferencias().select("textorutina");
        Paragraph n = new Paragraph("Hola " + Operacion.camelCase(getCliente().getNombre()) + r.getDato().toUpperCase(), parrafoPeque);
        n.setSpacingBefore(20);
        n.setAlignment(Element.ALIGN_CENTER);

        chapter.add(n);

        if (!rutina.getDescripcion().isEmpty()) {
            Paragraph d = new Paragraph("DESCRIPCIÓN DEL PLAN: ", titulo);
            d.setSpacingBefore(40);
            d.setAlignment(Element.ALIGN_CENTER);
            Paragraph p = new Paragraph(rutina.getDescripcion().toUpperCase(), parrafoPeque);
            p.setSpacingBefore(20);
            p.setAlignment(Element.ALIGN_CENTER);
            chapter.add(d);

            chapter.add(p);
        }

        document.add(chapter);

        addEjercicios(rutina.getPlankey(), EjxRut.LUNES);
        addEjercicios(rutina.getPlankey(), EjxRut.MARTES);
        addEjercicios(rutina.getPlankey(), EjxRut.MIERCOLES);
        addEjercicios(rutina.getPlankey(), EjxRut.JUEVES);
        addEjercicios(rutina.getPlankey(), EjxRut.VIERNES);
        addEjercicios(rutina.getPlankey(), EjxRut.SABADO);
        addEjercicios(rutina.getPlankey(), EjxRut.DOMINGO);
    }

    public void addEjercicios(int plankey, String dia) throws DAOException, IOException, DocumentException {
        ObservableList<EjxRut> list = Controller.getEjxruts().where(plankey, dia);
        if (!list.isEmpty()) {
            String BLOQUE1 = "";
            String BLOQUE2 = "";
            String BLOQUE3 = "";
            String BLOQUE4 = "";
            String BLOQUE5 = "";
            for (EjxRut ejx : list) {
                switch (ejx.getMomento()) {
                    case EjxRut.BLOQUE1:
                        BLOQUE1 += ejx.toString() + ", ";
                        break;
                    case EjxRut.BLOQUE2:
                        BLOQUE2 += ejx.toString() + ", ";
                        break;
                    case EjxRut.BLOQUE3:
                        BLOQUE3 += ejx.toString() + ", ";
                        break;
                    case EjxRut.BLOQUE4:
                        BLOQUE4 += ejx.toString() + ", ";
                        break;
                    case EjxRut.BLOQUE5:
                        BLOQUE5 += ejx.toString() + ", ";
                        break;
                }
            }

            Font tituloPeque = getFont("bold", 12, 30, 30, 30);
            Font parrafo = getFont("light", 10, 30, 30, 30);

            Paragraph t = new Paragraph();
            t.setFont(tituloPeque);
            t.add(dia);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingBefore(60);
            t.setSpacingAfter(10);
            Paragraph p;
            if (!BLOQUE1.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(EjxRut.BLOQUE1, tituloPeque));
                p.add(new Paragraph(BLOQUE1 + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE2.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(EjxRut.BLOQUE2, tituloPeque));
                p.add(new Paragraph(BLOQUE2 + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE3.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(EjxRut.BLOQUE3, tituloPeque));
                p.add(new Paragraph(BLOQUE3 + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE4.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(EjxRut.BLOQUE4, tituloPeque));
                p.add(new Paragraph(BLOQUE4 + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE5.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.add(new Paragraph(EjxRut.BLOQUE5, tituloPeque));
                p.add(new Paragraph(BLOQUE5 + ".", parrafo));
                p.setSpacingBefore(10);
                t.add(p);
            }

            chapter = new Chapter(0);
            Image page;
            page = Image.getInstance("src/imagen/entrenamiento.jpg");
            page.scaleAbsolute(PageSize.LETTER);
            page.setAbsolutePosition(0, 0);
            chapter.add(page);

            chapter.setIndentationLeft(60);
            chapter.setIndentationRight(60);
            chapter.add(t);

            document.add(chapter);
        }
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

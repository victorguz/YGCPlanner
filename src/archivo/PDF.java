package archivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.DAOException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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

    private final Font bold8Blanco = getFont("bold", 8, 230, 230, 230);
    private final Font bold8 = getFont("bold", 8, 30, 30, 30);
    private final Font bold12 = getFont("bold", 12, 30, 30, 30);
    private final Font bold14 = getFont("bold", 14, 30, 30, 30);
    private final Font regular8 = getFont("regular", 8, 30, 30, 30);
    private final Font regular10 = getFont("regular", 10, 30, 30, 30);


    private Cliente cliente = new Cliente();
    private File file;
    private Referencia plantilla;
    private Document document;
    private Chapter chapter;

    public PDF() throws IOException {
        setFile("");
    }

    public PDF(Cliente cliente, String url, Referencia plantilla) throws DAOException, IOException, DocumentException {
        setCliente(cliente);
        setFile(url);
        setPlantilla(plantilla);
        createPDF();
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
        this.file = new File(url + "\\" + Operacion.toCamelCase(getCliente().getNombre() + " " + getCliente().getApellido()) + " - YezidGuzmanCoach.pdf");
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
    }

    public Referencia getPlantilla() {
        return this.plantilla;
    }

    public void setPlantilla(Referencia ref) {
        if (ref == null) {
            Controller.mensaje("No hay ninguna plantilla, se generará el PDF sin imágenes.", "aviso");
        } else {
            this.plantilla = ref;
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
        getDocument().addTitle(Operacion.toCamelCase(getCliente().getNombre() + " " + getCliente().getApellido()) + " - YezidGuzmanCoach");
        getDocument().addAuthor("Yezid Guzman Coach");
        getDocument().addCreator("Yezid Guzman Coach");
    }


    public void addMedidas() throws DocumentException, IOException, DAOException {
        if (getCliente() != null) {
            ObservableList<Medida> medidas = Controller.getMedidas().where(getCliente().getClienteKey() + "");
            if (!medidas.isEmpty()) {
                chapter = new Chapter(0);
                chapter.setNumberDepth(0);
                if (getPlantilla() != null) {
                    Image black;
                    black = Image.getInstance(getPlantilla().getDato() + "medidas.jpg");
                    black.scaleAbsolute(PageSize.LETTER);
                    black.setAbsolutePosition(0, 0);
                    chapter.add(black);
                }


                //Informacion del cliente
                //NOMBRE
                Paragraph p = new Paragraph(
                        (getCliente().getNombre()
                                + " "
                                + getCliente().getApellido())
                                .toUpperCase(), bold12);
                p.setAlignment(Element.ALIGN_CENTER);
                p.setSpacingBefore(180);
                p.setSpacingAfter(15);
                p.setIndentationLeft(230);
                chapter.add(p);

                //Tabla con información del cliente
                PdfPTable tablaCliente = new PdfPTable(4);
                //Titulos para datos del cliente
                PdfPCell cellComplexion = new PdfPCell();
                Paragraph pComplexion = new Paragraph("COMPLEXIÓN", bold8Blanco);
                cellComplexion.setBackgroundColor(new BaseColor(24, 22, 33));
                pComplexion.setAlignment(Element.ALIGN_CENTER);
                cellComplexion.addElement(pComplexion);
                tablaCliente.addCell(cellComplexion);

                PdfPCell cellEdad = new PdfPCell();
                Paragraph pEdad = new Paragraph("EDAD", bold8Blanco);
                cellEdad.setBackgroundColor(new BaseColor(24, 22, 33));
                pEdad.setAlignment(Element.ALIGN_CENTER);
                cellEdad.addElement(pEdad);
                tablaCliente.addCell(cellEdad);

                PdfPCell cellAltura = new PdfPCell();
                Paragraph pAltura = new Paragraph("ALTURA", bold8Blanco);
                cellAltura.setBackgroundColor(new BaseColor(24, 22, 33));
                pAltura.setAlignment(Element.ALIGN_CENTER);
                cellAltura.addElement(pAltura);
                tablaCliente.addCell(cellAltura);

                PdfPCell cellSexo = new PdfPCell();
                Paragraph pSexo = new Paragraph("SEXO", bold8Blanco);
                cellSexo.setBackgroundColor(new BaseColor(24, 22, 33));
                pSexo.setAlignment(Element.ALIGN_CENTER);
                cellSexo.addElement(pSexo);
                tablaCliente.addCell(cellSexo);

                //Datos del cliente
                PdfPCell cellDComplexion = new PdfPCell();
                Paragraph pDComplexion = new Paragraph(medidas.get(medidas.size() - 1).getComplexionText().toUpperCase(), regular8);
                pDComplexion.setAlignment(Element.ALIGN_CENTER);
                cellDComplexion.addElement(pDComplexion);
                tablaCliente.addCell(cellDComplexion);

                PdfPCell cellDEdad = new PdfPCell();
                Paragraph pDEdad = new Paragraph(getCliente().getEdad() + "", regular8);
                pDEdad.setAlignment(Element.ALIGN_CENTER);
                cellDEdad.addElement(pDEdad);
                tablaCliente.addCell(cellDEdad);

                PdfPCell cellDAltura = new PdfPCell();
                Paragraph pDAltura = new Paragraph(medidas.get(medidas.size() - 1).getAltura() + "", regular8);
                pDAltura.setAlignment(Element.ALIGN_CENTER);
                cellDAltura.addElement(pDAltura);

                tablaCliente.addCell(cellDAltura);

                PdfPCell cellDSexo = new PdfPCell();
                Paragraph pDSexo = new Paragraph(getCliente().getSexo().toUpperCase(), regular8);
                pDSexo.setAlignment(Element.ALIGN_CENTER);
                cellDSexo.addElement(pDSexo);
                tablaCliente.addCell(cellDSexo);

                Paragraph ta = new Paragraph();
                ta.add(tablaCliente);
                ta.setIndentationLeft(230);

                chapter.add(ta);

                //Informacion de medidas
                //Titulo
                Paragraph k = new Paragraph("MEDIDAS Y ESTADO FÍSICO", bold12);
                k.setAlignment(Element.ALIGN_CENTER);
                k.setSpacingBefore(10);
                k.setSpacingAfter(15);
                k.setIndentationLeft(230);
                chapter.add(k);
                //Tabla de medidas
                PdfPTable tablaMedidas = null;
                //Titulos de medidas

                if (medidas.size() == 1) {
                    tablaMedidas = new PdfPTable(2);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("DETALLE", bold8Blanco);
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    cellMedida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("MEDIDA 1", getFont("bold", 12, 230, 230, 230));
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (String[] toArray : medidas.get(0).toArray()) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(toArray[0], regular8);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Dato de medida
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(toArray[1], regular8);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);

                    }
                }
                if (medidas.size() == 2) {
                    tablaMedidas = new PdfPTable(3);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("DETALLE", bold8Blanco);
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("MEDIDA 1", bold8Blanco);
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("MEDIDA 2", bold8Blanco);
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (int i = 0; i < medidas.get(0).toArray().length; i++) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(medidas.get(0).toArray()[i][0], bold8);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Datos de medida MEDIDA 1
                        PdfPCell cell1 = new PdfPCell();
                        Paragraph l1 = new Paragraph(medidas.get(1).toArray()[i][1], regular8);
                        l1.setAlignment(Element.ALIGN_CENTER);
                        cell1.addElement(l1);
                        tablaMedidas.addCell(cell1);

                        //Datos de medida MEDIDA 2
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(medidas.get(0).toArray()[i][1], regular8);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);

                    }
                }
                if (medidas.size() > 2) {
                    int n = (int) Math.ceil(medidas.size() / 2 + 0.1);
                    tablaMedidas = new PdfPTable(4);
                    PdfPCell cellMedida = new PdfPCell();
                    Paragraph pMedida = new Paragraph("DETALLE", bold8Blanco);
                    cellMedida.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida.setAlignment(Element.ALIGN_CENTER);
                    cellMedida.addElement(pMedida);
                    tablaMedidas.addCell(cellMedida);

                    PdfPCell cellMedida1 = new PdfPCell();
                    Paragraph pMedida1 = new Paragraph("MEDIDA 1", bold8Blanco);
                    cellMedida1.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedida1.setAlignment(Element.ALIGN_CENTER);
                    cellMedida1.addElement(pMedida1);
                    tablaMedidas.addCell(cellMedida1);

                    PdfPCell cellMedidaIntermedio = new PdfPCell();
                    Paragraph pMedidaIntermedio = new Paragraph("MEDIDA " + n, bold8Blanco);
                    cellMedidaIntermedio.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaIntermedio.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaIntermedio.addElement(pMedidaIntermedio);
                    tablaMedidas.addCell(cellMedidaIntermedio);

                    PdfPCell cellMedidaFin = new PdfPCell();
                    Paragraph pMedidaFin = new Paragraph("MEDIDA " + medidas.size(), bold8Blanco);
                    cellMedidaFin.setBackgroundColor(new BaseColor(24, 22, 33));
                    pMedidaFin.setAlignment(Element.ALIGN_CENTER);
                    cellMedidaFin.addElement(pMedidaFin);
                    tablaMedidas.addCell(cellMedidaFin);

                    //For que pone las medidas
                    for (int i = 0; i < medidas.get(0).toArray().length; i++) {

                        //Nombre de medida
                        PdfPCell cell = new PdfPCell();
                        Paragraph l = new Paragraph(medidas.get(0).toArray()[i][0], bold8);
                        l.setAlignment(Element.ALIGN_CENTER);
                        cell.addElement(l);
                        tablaMedidas.addCell(cell);

                        //Datos de medida inicial
                        PdfPCell cell1 = new PdfPCell();
                        Paragraph l1 = new Paragraph(medidas.get(medidas.size() - 1).toArray()[i][1], regular8);
                        l1.setAlignment(Element.ALIGN_CENTER);
                        cell1.addElement(l1);
                        tablaMedidas.addCell(cell1);

                        //Datos de medida intermedia
                        PdfPCell cell2 = new PdfPCell();
                        Paragraph l2 = new Paragraph(medidas.get(n - 1).toArray()[i][1], regular8);
                        l2.setAlignment(Element.ALIGN_CENTER);
                        cell2.addElement(l2);
                        tablaMedidas.addCell(cell2);

                        //Datos de ultima medida
                        PdfPCell cell3 = new PdfPCell();
                        Paragraph l3 = new Paragraph(medidas.get(0).toArray()[i][1], regular8);
                        l3.setAlignment(Element.ALIGN_CENTER);
                        cell3.addElement(l3);
                        tablaMedidas.addCell(cell3);
                    }

                }
                if (tablaMedidas != null) {
                    Paragraph tm = new Paragraph();
                    tm.add(tablaMedidas);
                    tm.setIndentationLeft(230);
                    chapter.add(tm);
                }
                document.add(chapter);
            } else {
                Controller.mensaje("Este cliente no tiene medidas", "aviso");
            }
        }
    }

    public void addDieta(Plan dieta) throws DocumentException, IOException, DAOException {
        chapter = new Chapter(0);
        if (getPlantilla() != null) {
            Image black;
            black = Image.getInstance(getPlantilla().getDato() + "alimentacion.jpg");
            black.scaleAbsolute(PageSize.LETTER);
            black.setAbsolutePosition(0, 0);
            chapter.add(black);
        }

        chapter.setIndentationLeft(100);
        chapter.setIndentationRight(100);

        if (dieta != null) {
            Referencia r = Controller.getReferencias().select("textodieta");
            Paragraph n = new Paragraph((getCliente().getNombre() + " " + ((r.getDato().length() > 600) ? r.getDato().substring(0, 590) : r.getDato())).toUpperCase(), regular8);
            n.setSpacingBefore(645);
            n.setAlignment(Element.ALIGN_CENTER);

            chapter.add(n);

            document.add(chapter);

            addAlimentos(dieta.getPlankey(), AlxDiet.LUNES);
            addAlimentos(dieta.getPlankey(), AlxDiet.MARTES);
            addAlimentos(dieta.getPlankey(), AlxDiet.MIERCOLES);
            addAlimentos(dieta.getPlankey(), AlxDiet.JUEVES);
            addAlimentos(dieta.getPlankey(), AlxDiet.VIERNES);
            addAlimentos(dieta.getPlankey(), AlxDiet.SABADO);
            addAlimentos(dieta.getPlankey(), AlxDiet.DOMINGO);
        }
    }

    public void addAlimentos(int plankey, String dia) throws DAOException, IOException, DocumentException {
        ObservableList<AlxDiet> list = Controller.getAlxdiets().where(plankey, dia);
        if (!list.isEmpty()) {
            String DESAYUNO = "";
            double DESAYUNOPROTEINAS = 0;
            double DESAYUNOCARBOHIDRATOS = 0;
            double DESAYUNOGRASAS = 0;
            double DESAYUNOCALORIAS = 0;

            String ALMUERZO = "";
            double ALMUERZOPROTEINAS = 0;
            double ALMUERZOCARBOHIDRATOS = 0;
            double ALMUERZOGRASAS = 0;
            double ALMUERZOCALORIAS = 0;

            String CENA = "";
            double CENAPROTEINAS = 0;
            double CENACARBOHIDRATOS = 0;
            double CENAGRASAS = 0;
            double CENACALORIAS = 0;

            String PREENTRENO = "";
            double PREENTRENOPROTEINAS = 0;
            double PREENTRENOCARBOHIDRATOS = 0;
            double PREENTRENOGRASAS = 0;
            double PREENTRENOCALORIAS = 0;

            String POSTENTRENO = "";
            double POSTENTRENOPROTEINAS = 0;
            double POSTENTRENOCARBOHIDRATOS = 0;
            double POSTENTRENOGRASAS = 0;
            double POSTENTRENOCALORIAS = 0;

            String SNACKAM = "";
            double SNACKAMPROTEINAS = 0;
            double SNACKAMCARBOHIDRATOS = 0;
            double SNACKAMGRASAS = 0;
            double SNACKAMCALORIAS = 0;

            String SNACKPM = "";
            double SNACKPMPROTEINAS = 0;
            double SNACKPMCARBOHIDRATOS = 0;
            double SNACKPMGRASAS = 0;
            double SNACKPMCALORIAS = 0;


            for (AlxDiet alx : list) {
                switch (alx.getMomento()) {
                    case AlxDiet.DESAYUNO:
                        DESAYUNO += alx.toString() + ", ";
                        DESAYUNOPROTEINAS += alx.getProteinasxpeso();
                        DESAYUNOCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        DESAYUNOGRASAS += alx.getGrasasxpeso();
                        DESAYUNOCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.ALMUERZO:
                        ALMUERZO += alx.toString() + ", ";
                        ALMUERZOPROTEINAS += alx.getProteinasxpeso();
                        ALMUERZOCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        ALMUERZOGRASAS += alx.getGrasasxpeso();
                        ALMUERZOCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.CENA:
                        CENA += alx.toString() + ", ";
                        CENAPROTEINAS += alx.getProteinasxpeso();
                        CENACARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        CENAGRASAS += alx.getGrasasxpeso();
                        CENACALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.POSTENTRENO:
                        POSTENTRENO += alx.toString() + ", ";
                        POSTENTRENOPROTEINAS += alx.getProteinasxpeso();
                        POSTENTRENOCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        POSTENTRENOGRASAS += alx.getGrasasxpeso();
                        POSTENTRENOCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.PREENTRENO:
                        PREENTRENO += alx.toString() + ", ";
                        PREENTRENOPROTEINAS += alx.getProteinasxpeso();
                        PREENTRENOCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        PREENTRENOGRASAS += alx.getGrasasxpeso();
                        PREENTRENOCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.SNACKAM:
                        SNACKAM += alx.toString() + ", ";
                        SNACKAMPROTEINAS += alx.getProteinasxpeso();
                        SNACKAMCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        SNACKAMGRASAS += alx.getGrasasxpeso();
                        SNACKAMCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                    case AlxDiet.SNACKPM:
                        SNACKPM += alx.toString() + ", ";
                        SNACKPMPROTEINAS += alx.getProteinasxpeso();
                        SNACKPMCARBOHIDRATOS += alx.getCarbohidratosxpeso();
                        SNACKPMGRASAS += alx.getGrasasxpeso();
                        SNACKPMCALORIAS += alx.getKilocaloriasxpeso();
                        break;
                }
            }

            //Titulo: dia
            Paragraph t = new Paragraph("MENÚ DEL " + dia.toUpperCase(), bold14);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingBefore(80);

            //Sumatoria del menú
            double CALORIAS = DESAYUNOCALORIAS + SNACKAMCALORIAS + ALMUERZOCALORIAS + SNACKPMCALORIAS + CENACALORIAS + PREENTRENOCALORIAS + POSTENTRENOCALORIAS;
            double PROTEINAS = DESAYUNOPROTEINAS + SNACKAMPROTEINAS + ALMUERZOPROTEINAS + SNACKPMPROTEINAS + CENAPROTEINAS + PREENTRENOPROTEINAS + POSTENTRENOPROTEINAS;
            double CARBOHIDRATOS = DESAYUNOCARBOHIDRATOS + SNACKAMCARBOHIDRATOS + ALMUERZOCARBOHIDRATOS + SNACKPMCARBOHIDRATOS + CENACARBOHIDRATOS + PREENTRENOCARBOHIDRATOS + POSTENTRENOCARBOHIDRATOS;
            double GRASAS = DESAYUNOGRASAS + SNACKAMGRASAS + ALMUERZOGRASAS + SNACKPMGRASAS + CENAGRASAS + PREENTRENOGRASAS + POSTENTRENOGRASAS;

            //Texto de introducción diario
            Paragraph p = new Paragraph(("Para el " + dia + " te he asignado una ingesta calórica de "
                    + Operacion.formatear(CALORIAS) + " kilocalorías." + " Teniendo en cuenta tus objetivos " +
                    "actuales, eso lo dividiremos en " + Operacion.formatear(PROTEINAS) + " gramos de proteínas, "
                    + Operacion.formatear(CARBOHIDRATOS) + " gramos de carbohidratos y " + Operacion.formatear(GRASAS)
                    + " gramos de grasas."), regular10);

            p.setAlignment(Element.ALIGN_JUSTIFIED);
            p.setSpacingBefore(5);
            t.add(p);

            if (!DESAYUNO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.DESAYUNO + ": ", bold12);
                Chunk pa = new Chunk(DESAYUNO.substring(0, DESAYUNO.length() - 2) + ".\n", regular10);

                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(15);
                t.add(p);
            }

            if (!SNACKAM.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.SNACKAM + ": ", bold12);
                Chunk pa = new Chunk(SNACKAM.substring(0, SNACKAM.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!ALMUERZO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.ALMUERZO + ": ", bold12);
                Chunk pa = new Chunk(ALMUERZO.substring(0, ALMUERZO.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!SNACKPM.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.SNACKPM + ": ", bold12);
                Chunk pa = new Chunk(SNACKPM.substring(0, SNACKPM.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!CENA.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.CENA + ": ", bold12);
                Chunk pa = new Chunk(CENA.substring(0, CENA.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!PREENTRENO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.PREENTRENO + ": ", bold12);
                Chunk pa = new Chunk(PREENTRENO.substring(0, PREENTRENO.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!POSTENTRENO.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(AlxDiet.POSTENTRENO + ": ", bold12);
                Chunk pa = new Chunk(POSTENTRENO.substring(0, POSTENTRENO.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            chapter = new Chapter(0);

            if (getPlantilla() != null) {
                Image black;
                black = Image.getInstance(getPlantilla().getDato() + "whitepage.jpg");
                black.scaleAbsolute(PageSize.LETTER);
                black.setAbsolutePosition(0, 0);
                chapter.add(black);
            }

            chapter.setIndentationLeft(60);
            chapter.setIndentationRight(60);
            chapter.add(t);

            document.add(chapter);
        }
    }

    public void addRutina(Plan rutina) throws DocumentException, IOException, DAOException {
        chapter = new Chapter(0);

        if (getPlantilla() != null) {
            Image black;
            black = Image.getInstance(getPlantilla().getDato() + "entrenamiento.jpg");
            black.scaleAbsolute(PageSize.LETTER);
            black.setAbsolutePosition(0, 0);
            chapter.add(black);
        }

        chapter.setIndentationLeft(380);
        chapter.setIndentationRight(13);

        Referencia r = Controller.getReferencias().select("textorutina");
        Paragraph n = new Paragraph((getCliente().getNombre() + " " + ((r.getDato().length() > 600) ? r.getDato().substring(0, 590) : r.getDato())).toUpperCase(), regular8);
        n.setSpacingBefore(570);
        n.setAlignment(Element.ALIGN_RIGHT);

        chapter.add(n);

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

            Paragraph t = new Paragraph("ENTRENO DEL " + dia.toUpperCase(), bold14);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingBefore(80);


            //Texto de introducción diario
            Paragraph p = null;
            try {
                p = new Paragraph(("Estos serán los ejercicios que realizarás el " + dia.toLowerCase() + ".")
                        , regular10);
            } catch (Exception e) {
                e.printStackTrace();
            }

            p.setAlignment(Element.ALIGN_JUSTIFIED);
            p.setSpacingBefore(5);
            t.add(p);


            if (!BLOQUE1.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(EjxRut.BLOQUE1 + ": ", bold12);
                Chunk pa = new Chunk(BLOQUE1.substring(0, BLOQUE1.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(15);
                t.add(p);
            }

            if (!BLOQUE2.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(EjxRut.BLOQUE2 + ": ", bold12);
                Chunk pa = new Chunk(BLOQUE2.substring(0, BLOQUE2.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE3.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(EjxRut.BLOQUE3 + ": ", bold12);
                Chunk pa = new Chunk(BLOQUE3.substring(0, BLOQUE3.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(15);
                t.add(p);
            }

            if (!BLOQUE4.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(EjxRut.BLOQUE4 + ": ", bold12);
                Chunk pa = new Chunk(BLOQUE4.substring(0, BLOQUE4.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            if (!BLOQUE5.isEmpty()) {
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_JUSTIFIED);
                Chunk ti = new Chunk(EjxRut.BLOQUE5 + ": ", bold12);
                Chunk pa = new Chunk(BLOQUE5.substring(0, BLOQUE5.length() - 2) + ".\n", regular10);
                p.add(ti);
                p.add(pa);
                p.setSpacingBefore(10);
                t.add(p);
            }

            chapter = new Chapter(0);

            if (getPlantilla() != null) {
                Image black;
                black = Image.getInstance(getPlantilla().getDato() + "whitepage.jpg");
                black.scaleAbsolute(PageSize.LETTER);
                black.setAbsolutePosition(0, 0);
                chapter.add(black);
            }

            chapter.setIndentationLeft(60);
            chapter.setIndentationRight(60);
            chapter.add(t);

            document.add(chapter);
        }
    }

    public void guardar() throws IOException {
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

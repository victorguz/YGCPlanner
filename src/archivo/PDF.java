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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import modelo.cliente.Medida;

/**
 *
 * @author 201621279487
 */
public class PDF {

    private Medida medida = new Medida();
    private File file;

    public PDF() {
    }

    public PDF(Medida medida) throws DAOException, IOException, FileNotFoundException, DocumentException {
        setMedida(medida);
        setFile("");
        createPDF();
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

    public void setMedida(Medida medida) throws DAOException {
        if (medida.isEmpty()) {
            throw new DAOException("A esta medida le faltan datos");
        }
        this.medida = medida;
    }

    //Fonts
    private static Font fontTitulo = FontFactory.getFont("Arial", 22, new BaseColor(80, 80, 80));
    private static Font fontSubtitulo = FontFactory.getFont("Arial", 16, new BaseColor(80, 80, 80));
    private static Font fontParrafoNegro = FontFactory.getFont("Arial", 10, Font.NORMAL, new BaseColor(40, 40, 40));
    private static Font fontParrafoNegroMenor = FontFactory.getFont("Arial", 8, Font.NORMAL, new BaseColor(40, 40, 40));
    private static Font fontParrafoGris = FontFactory.getFont("Arial", 10, Font.NORMAL, new BaseColor(125, 125, 125));
    private static Font fontParrafoGrisCursiva = FontFactory.getFont("Arial", 10, Font.ITALIC, new BaseColor(125, 125, 125));
    private static Font fontCategoria = FontFactory.getFont("Arial", 18, Font.BOLD);

    private static String userHome;
    Document document;

    public void createPDF() throws FileNotFoundException, DocumentException, IOException {
        document = new Document(PageSize.LETTER, 50, 22, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        //Modificar metadatos del archivo:
        document.addTitle("Plan " + getMedida().getCliente().getNombre()+" "+getMedida().getCliente().getApellido());
        document.addSubject("Plan nutricional y de rutina");
        document.addKeywords("Plan, Rutina, Dieta");
        document.addAuthor("Yezid Guzman Coach");
        document.addCreator("Yezid Guzman Coach");
        //Edición del archivo:
        Chapter chapter = new Chapter(1);
        chapter.setNumberDepth(0);
        Image black;
        black = Image.getInstance(new File("src/Images/black.png").toURL());
        black.scaleAbsolute(PageSize.LETTER);
        black.setAbsolutePosition(0, 0);
        chapter.add(black);

        Paragraph subInfo = new Paragraph("Información del cliente", fontSubtitulo);
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setIndentationLeft(345);
        subInfo.setSpacingBefore(65);
        subInfo.setSpacingAfter(10);
        chapter.add(subInfo);

        Chunk tPlan = new Chunk("Plan", fontParrafoNegro);
        Chunk tNombre = new Chunk("Nombre", fontParrafoNegro);
        Chunk tSexo = new Chunk("Sexo", fontParrafoNegro);
        Chunk tEdad = new Chunk("Edad", fontParrafoNegro);

        Paragraph pPlan = new Paragraph();
        pPlan.add(tPlan);
        pPlan.setFont(fontParrafoGris);
        pPlan.add("\n" + getMedida().getRutina().getNombre());
        pPlan.setAlignment(Element.ALIGN_CENTER);
        pPlan.setIndentationLeft(345);
        pPlan.setSpacingAfter(2);

        Paragraph pNombre = new Paragraph();
        pNombre.setFont(fontParrafoGris);
        pNombre.add(tNombre);
        pNombre.add("\n" + getMedida().getCliente().getNombre()+" "+getMedida().getCliente().getApellido());
        pNombre.setAlignment(Element.ALIGN_CENTER);
        pNombre.setIndentationLeft(345);
        pNombre.setSpacingAfter(2);

        Paragraph pSexo = new Paragraph();
        pSexo.setFont(fontParrafoGris);
        pSexo.add(tSexo);
        pSexo.add("\n" + getMedida().getCliente().getSexo());
        pSexo.setAlignment(Element.ALIGN_CENTER);
        pSexo.setIndentationLeft(345);
        pSexo.setSpacingAfter(2);

        Paragraph pEdad = new Paragraph();
        pEdad.setFont(fontParrafoGris);
        pEdad.add(tEdad);
        pEdad.add("\n" + getMedida().getCliente().getEdad());
        pEdad.setAlignment(Element.ALIGN_CENTER);
        pEdad.setIndentationLeft(345);

        chapter.add(pPlan);
        chapter.add(pNombre);
        chapter.add(pSexo);
        chapter.add(pEdad);

        Paragraph subComo = new Paragraph("Importante", fontSubtitulo);
        subComo.setAlignment(Element.ALIGN_CENTER);
        subComo.setIndentationLeft(345);
        subComo.setSpacingBefore(10);
        subComo.setSpacingAfter(10);
        chapter.add(subComo);
        Paragraph parrafoPlan = new Paragraph("El siguiente plan está "
                + "enfocado en tus objetivos y necesidades."
                + "\n"
                + "\nRecuerda que este es un trabajo de dos "
                + "y el objetivo numero uno debe ser adquirir buenos "
                + "hábitos, son esos buenos hábitos los que nos garantizan un "
                + "mejor estilo de vida y como consecuencia un mejor estado "
                + "físico, apariencia y salud. "
                + "\n"
                + "\nCon mi ayuda y tu disposición alcanzaremos tus objetivos.",
                fontParrafoGris);
        parrafoPlan.setAlignment(Element.ALIGN_CENTER);
        parrafoPlan.setIndentationLeft(345);
        parrafoPlan.setSpacingAfter(130);
        chapter.add(parrafoPlan);

        Paragraph parrafoNutricion = new Paragraph("Plan nutricional "
                + "diseñado para suplir tus necesidades y alcanzar "
                + "tus objetivos.", fontParrafoNegroMenor);
        parrafoNutricion.setAlignment(Element.ALIGN_CENTER);
        parrafoNutricion.setIndentationLeft(340);
        parrafoNutricion.setSpacingAfter(37);
        chapter.add(parrafoNutricion);

        Paragraph parrafoRutina = new Paragraph("Plan de entrenamiento "
                + "diseñado para trabajar tus cualidades y habilidades "
                + "físicas básicas y complejas de manera progresiva según "
                + "tus objetivos.", fontParrafoNegroMenor);
        parrafoRutina.setAlignment(Element.ALIGN_CENTER);
        parrafoRutina.setIndentationLeft(340);
        chapter.add(parrafoRutina);
        document.add(chapter);
    }
//Te guiaré en los dias de entrenamiento que escogiste.

    public void addPage() throws DocumentException, MalformedURLException, BadElementException, IOException {
        Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/Images/page.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", fontTitulo);
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        //Example:
        Paragraph parrafoNutricion = new Paragraph("Plan nutricional "
                + "diseñado para suplir tus necesidades y alcanzar "
                + "tus objetivos.", fontParrafoNegroMenor);
        parrafoNutricion.setAlignment(Element.ALIGN_CENTER);
        parrafoNutricion.setSpacingAfter(37);
        chapter.add(parrafoNutricion);
        //End example
        document.add(chapter);

    }

    public void close() throws IOException {
        document.close();
        Desktop.getDesktop().open(file);
    }
}

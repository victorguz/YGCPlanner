package archivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author 201621279487
 */
public class PDF {

    //Fonts
    private static Font fTitulo
            = FontFactory.getFont("Arial", 22, new BaseColor(80, 80, 80));
    private static Font fSubTitulo
            = FontFactory.getFont("Arial", 16, new BaseColor(80, 80, 80));
    private static Font fParrafoNegro
            = FontFactory.getFont("Arial", 10, Font.NORMAL, new BaseColor(40, 40, 40));
    private static Font fParrafoNegroMenor
            = FontFactory.getFont("Arial", 8, Font.NORMAL, new BaseColor(40, 40, 40));
    private static Font fParrafoGris = FontFactory.getFont("Arial", 10,
            Font.NORMAL, new BaseColor(125, 125, 125));
    private static Font fParrafoGrisCursiva = FontFactory.getFont("Arial", 10,
            Font.ITALIC, new BaseColor(125, 125, 125));
    private static Font fCategoria
            = FontFactory.getFont("Arial", 18, Font.BOLD);

    private static String userHome;
    Document document;
       private File file;

    public void createPDF(String plan, String nombreCliente, int edad, String sexo) throws FileNotFoundException, DocumentException, IOException {
        document = new Document(PageSize.LETTER, 50, 22, 50, 50);
        userHome = System.getProperty("user.home") + "\\Desktop\\"+nombreCliente+".pdf";
        file = new File(userHome);

        if (!file.exists()) {
            file.createNewFile();
        }
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        //Modificar metadatos del archivo:
        document.addTitle("Plan " + nombreCliente);
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

        Paragraph subInfo = new Paragraph("Información del cliente", fSubTitulo);
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setIndentationLeft(345);
        subInfo.setSpacingBefore(65);
        subInfo.setSpacingAfter(10);
        chapter.add(subInfo);

        Chunk tPlan = new Chunk("Plan", fParrafoNegro);
        Chunk tNombre = new Chunk("Nombre", fParrafoNegro);
        Chunk tSexo = new Chunk("Sexo", fParrafoNegro);
        Chunk tEdad = new Chunk("Edad", fParrafoNegro);

        Paragraph pPlan = new Paragraph();
        pPlan.add(tPlan);
        pPlan.setFont(fParrafoGris);
        pPlan.add("\n"+plan);
        pPlan.setAlignment(Element.ALIGN_CENTER);
        pPlan.setIndentationLeft(345);
        pPlan.setSpacingAfter(2);
        
        Paragraph pNombre = new Paragraph();
        pNombre.setFont(fParrafoGris);
        pNombre.add(tNombre);
        pNombre.add("\n"+nombreCliente);
        pNombre.setAlignment(Element.ALIGN_CENTER);
        pNombre.setIndentationLeft(345);
        pNombre.setSpacingAfter(2);
        
        Paragraph pSexo = new Paragraph();
        pSexo.setFont(fParrafoGris);
        pSexo.add(tSexo);
        pSexo.add("\n"+sexo);
        pSexo.setAlignment(Element.ALIGN_CENTER);
        pSexo.setIndentationLeft(345);
        pSexo.setSpacingAfter(2);
        
        Paragraph pEdad = new Paragraph();
        pEdad.setFont(fParrafoGris);
        pEdad.add(tEdad);
        pEdad.add("\n"+edad);
        pEdad.setAlignment(Element.ALIGN_CENTER);
        pEdad.setIndentationLeft(345);

        chapter.add(pPlan);
        chapter.add(pNombre);
        chapter.add(pSexo);
        chapter.add(pEdad);
        
        Paragraph subComo = new Paragraph("Importante", fSubTitulo);
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
                fParrafoGris);
        parrafoPlan.setAlignment(Element.ALIGN_CENTER);
        parrafoPlan.setIndentationLeft(345);
        parrafoPlan.setSpacingAfter(130);
        chapter.add(parrafoPlan);

        Paragraph parrafoNutricion = new Paragraph("Plan nutricional "
                + "diseñado para suplir tus necesidades y alcanzar "
                + "tus objetivos.", fParrafoNegroMenor);
        parrafoNutricion.setAlignment(Element.ALIGN_CENTER);
        parrafoNutricion.setIndentationLeft(340);
        parrafoNutricion.setSpacingAfter(37);
        chapter.add(parrafoNutricion);

        Paragraph parrafoRutina = new Paragraph("Plan de entrenamiento "
                + "diseñado para trabajar tus cualidades y habilidades "
                + "físicas básicas y complejas de manera progresiva según "
                + "tus objetivos.", fParrafoNegroMenor);
        parrafoRutina.setAlignment(Element.ALIGN_CENTER);
        parrafoRutina.setIndentationLeft(340);
        chapter.add(parrafoRutina);
        document.add(chapter);
    }
//Te guiaré en los dias de entrenamiento que escogiste.
public void addPage() throws DocumentException, MalformedURLException, BadElementException, IOException{
    Chapter chapter = new Chapter(2);
        chapter.setNumberDepth(0);
        Image page;
        page = Image.getInstance(new File("src/Images/page.png").toURL());
        page.scaleAbsolute(PageSize.LETTER);
        page.setAbsolutePosition(0, 0);
        chapter.add(page);
        Paragraph subInfo = new Paragraph("Lunes", fTitulo);
        subInfo.setAlignment(Element.ALIGN_CENTER);
        subInfo.setSpacingAfter(30);
        chapter.add(subInfo);
        //Example:
        Paragraph parrafoNutricion = new Paragraph("Plan nutricional "
                + "diseñado para suplir tus necesidades y alcanzar "
                + "tus objetivos.", fParrafoNegroMenor);
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

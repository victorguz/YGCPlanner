package Archivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 201621279487
 */
public final class Excel {

    private File file;
    private FileOutputStream out;
    private FileInputStream input;
    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private final String sheetName;
    private final ArrayList<Object> columns;

    public Excel(String name, String dir, ArrayList<Object> columns, String sheetName) throws IOException, FileNotFoundException, InvalidFormatException {
        this.sheetName = sheetName;
        this.columns = columns;
        createFile(name, dir);
    }

    public void createFile(String name, String dir) throws FileNotFoundException, IOException, InvalidFormatException {
        File folder = new File(dir);
        if (folder.exists()) {
            System.out.println("#Carpeta ya existe.");
        } else {
            folder.mkdirs();
        }
        file = new File(folder, name + ".xlsx");
        if (file.exists()) {
            System.out.println("#Archivo encontrado en " + file.getAbsolutePath());
        } else {
            System.out.println("#Archivo no existe. Creando...");
            file.createNewFile();
            System.out.println("#Archivo creado en " + file.getAbsolutePath());
            createBook();
        }
    }

    public void createBook() throws FileNotFoundException, IOException, InvalidFormatException {
        book = new XSSFWorkbook();
        sheet = book.getSheet(sheetName);
        if (sheet == null) {
            sheet = book.createSheet(sheetName);
        }
        out = new FileOutputStream(file);
        book.write(out);
        out.flush();
        out.close();
        book.close();
        System.out.println("#Libro creado con éxito. Añadiendo columnas...");
        replace(columns, 0);
    }

    public ArrayList<ArrayList<String>> read() throws IOException {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        if (!file.exists()) {
            throw new IOException("Aún no se ha creado un archivo.");
        }
        input = new FileInputStream(file);
        book = new XSSFWorkbook(input);
        sheet = book.getSheet(sheetName);
        if (sheet == null) {
            throw new IOException("No se encontró la hoja " + sheetName + ".");
        }
        Iterator<Row> rowIterator = sheet.iterator();
        Row row;
        while (rowIterator.hasNext()) {
            ArrayList<String> rowList = new ArrayList<>();
            row = rowIterator.next();
            Iterator< Cell> cellIterator = row.cellIterator();
            Cell cell;
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case NUMERIC:
                        rowList.add("" + cell.getNumericCellValue());
                        break;
                    case STRING:
                        rowList.add(cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        if (cell.getBooleanCellValue() == true) {
                            rowList.add("true");
                        } else {
                            rowList.add("false");
                        }
                        break;
                    case BLANK:
                        rowList.add("");
                        break;
                }
            }
            data.add(rowList);
        }
        input.close();
        book.close();
        return data;
    }

    public void add(ArrayList<Object> rowList) throws IOException {
        if (!file.exists()) {
            throw new IOException("Aún no se ha creado un archivo.");
        }
        input = new FileInputStream(file);
        book = new XSSFWorkbook(input);
        input.close();
        sheet = book.getSheet(sheetName);
        if (sheet == null) {
            sheet = book.createSheet(sheetName);
            System.out.println("Hoja " + sheetName + " creada con exito.");
        }
        Row row;
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        rowList.add(0, sheet.getLastRowNum());
        for (int i = 0; i < rowList.size(); i++) {
            row.createCell(i);
            if (rowList.get(i) instanceof String) {
                row.getCell(i).setCellValue((String) rowList.get(i));
            } else if (rowList.get(i) instanceof Double) {
                row.getCell(i).setCellValue((Double) rowList.get(i));
            } else if (rowList.get(i) instanceof Integer) {
                row.getCell(i).setCellValue((Integer) rowList.get(i));
            } else if (rowList.get(i) instanceof Boolean) {
                row.getCell(i).setCellValue((Boolean) rowList.get(i));
            }
        }
        // Escribimos en fichero
        out = new FileOutputStream(file);
        book.write(out);
        //cerramos el fichero y el libro
        out.close();
        book.close();
        System.out.println("Registro añadido.");
    }

    public void replace(ArrayList<Object> rowList, int numRow) throws IOException {
        if (!file.exists()) {
            throw new IOException("Aún no se ha creado un archivo.");
        }
        input = new FileInputStream(file);
        book = new XSSFWorkbook(input);
        input.close();
        sheet = book.getSheet(sheetName);
        Row row;
        row = sheet.createRow(numRow);
        for (int i = 0; i < rowList.size(); i++) {
            row.createCell(i);
            if (rowList.get(i) instanceof String) {
                row.getCell(i).setCellValue((String) rowList.get(i));
            } else if (rowList.get(i) instanceof Double) {
                row.getCell(i).setCellValue((Double) rowList.get(i));
            } else if (rowList.get(i) instanceof Integer) {
                row.getCell(i).setCellValue((Integer) rowList.get(i));
            } else if (rowList.get(i) instanceof Boolean) {
                row.getCell(i).setCellValue((Boolean) rowList.get(i));
            }
        }
        // Escribimos en fichero
        out = new FileOutputStream(file);
        book.write(out);
        //cerramos el fichero y el libro
        out.close();
        book.close();
        System.out.println("Columna añadido.");
    }
    
    public void delete(String column1, String column2) throws IOException {
        if (!file.exists()) {
            throw new IOException("Aún no se ha creado un archivo.");
        }
        input = new FileInputStream(file);
        book = new XSSFWorkbook(input);
        sheet = book.getSheet(sheetName);
        if (sheet == null) {
            throw new IOException("No se encontró la hoja " + sheetName + ".");
        }
        Iterator<Row> rowIterator = sheet.iterator();
        Row row;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Cell cell = row.getCell(1);
            Cell cell2 = row.getCell(2);
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (cell.getNumericCellValue() == Double.parseDouble(column1)
                            || cell2.getNumericCellValue() == Double.parseDouble(column1)) {
                        sheet.removeRow(row);
                    }
                    break;
                case STRING:
                    if (cell.getStringCellValue().equalsIgnoreCase(column1)
                            || cell.getStringCellValue().equalsIgnoreCase(column2)) {
                        sheet.removeRow(row);
                    }
                    break;
            }
        }
        input.close();
        book.close();
        System.out.println("Registro eliminado.");
    }
}

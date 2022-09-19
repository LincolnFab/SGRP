/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SalaDeAula;
import model.Servidor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Marilena Oshima
 */
public class ReadExcel {
    public static List<Servidor> servidorReadExcel(File fileImport) {
        // cria uma lista de servidores
        List<Servidor> servidores = new ArrayList<Servidor>();
        // pra ler o arquivo
        FileInputStream file = null;
        
        try {
            // transforma o arquivo em file input stream
            file = new FileInputStream(fileImport);
            Workbook workbook = null;

            if (fileImport.getName().toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(file);
            } else if (fileImport.getName().toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(file);
            }
            //planilha
            Sheet sheet = workbook.getSheetAt(0);
            // iterar sobre as linhas da planilha
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.hasNext();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                // instancia um servidor
                Servidor s = new Servidor();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 2:
                            s.setNome(cell.getStringCellValue());
                            break;
                        case 4:
                            s.setTipo(cell.getStringCellValue());
                            break;
                        case 5:
                            s.setEmail(cell.getStringCellValue());
                            break;
                        case 6:
                            s.setProntuario(cell.getStringCellValue());
                            s.setSenha(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }

                }
                //System.out.println("SERVIDORRR S....." + s);
                servidores.add(s);

            }
            file.close();
            return servidores;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<SalaDeAula> salaDeAulaReadExcel(File fileImport) {
 
        List<SalaDeAula> salas = new ArrayList<SalaDeAula>();
        
        FileInputStream file = null;
        
        try {
           
            file = new FileInputStream(fileImport);
            Workbook workbook = null;

            if (fileImport.getName().toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(file);
            } else if (fileImport.getName().toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(file);
            }
           
            Sheet sheet = workbook.getSheetAt(0);
    
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.hasNext();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                // instancia um servidor
                SalaDeAula s = new SalaDeAula();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 1:
                            s.setDescricao(cell.getStringCellValue());
                            break;
                        
                        default:
                            break;
                    }

                }
                
                salas.add(s);

            }
            file.close();
            return salas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

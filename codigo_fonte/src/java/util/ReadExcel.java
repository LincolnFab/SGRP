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
import model.Curso;
import model.Disciplina;
import model.DisciplinaPK;
import model.Estudante;
import model.SalaDeAula;
import model.Servidor;
import model.Turma;
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
            Row row = rowIterator.next();
            //row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
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
            Row row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                SalaDeAula s = new SalaDeAula();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            s.setIdSala(cell.getStringCellValue());
                            break;
                        case 1:
                            s.setTipo(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                salas.add(s);
            }
            file.close();
            //System.out.println("salas" + salas);
            return salas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<Estudante> estudanteExcelData(File fileImport, List<Turma> turmas) {
        List<Estudante> estudantes = new ArrayList<Estudante>();
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
            Row row = rowIterator.next();
            //row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Estudante e = new Estudante();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 1:
                            e.setProntuario(cell.getStringCellValue());
                            e.setSenha(cell.getStringCellValue());
                            break;
                        case 2:
                            e.setNome(cell.getStringCellValue());
                            break;
                        case 4:
                            e.setEmailAluno(cell.getStringCellValue());
                            break;
                        case 5:
                            e.setEmailPessoal(cell.getStringCellValue());
                            break;
                        case 6:
                            e.setEmailResponsavel(cell.getStringCellValue());
                            // se os e-mail forem - deixar em branco
                            if (e.getEmailResponsavel().equalsIgnoreCase("-"))
                                e.setEmailResponsavel("");
                            break;
                        case 7:
                            Turma turma = new Turma();
                            String codigoTurma = cell.getStringCellValue();
                            for (Turma t : turmas) {
                                if(t.getIdturma().equalsIgnoreCase(codigoTurma))
                                    e.setTurmaIdturma(t);
                            }
                            break;
                        default:
                            break;
                    }
                }
                System.out.println("ESTUDANTE....." + e);
                estudantes.add(e);
            }
            file.close();
            return estudantes;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<Turma> turmaExcelData(File fileImport, List<Curso> cursos) {
        List<Turma> turmas = new ArrayList<Turma>();
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
            Row row = rowIterator.next();
            //row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Turma t = new Turma();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            t.setIdturma(cell.getStringCellValue());
                            break;
                        case 1:
                            t.setDescricao(cell.getStringCellValue());
                            break;
                        case 10:
                            Curso curso = new Curso();
                            String codigoCurso = cell.getStringCellValue();
                            System.out.println("codigo do curso...." + codigoCurso);
                            for (Curso c : cursos) {
                                if(c.getId().equalsIgnoreCase(codigoCurso)) {
                                    t.setCursoId(c);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                System.out.println("turma" + t.getIdturma() + t.getDescricao() + t.getCursoId());
                turmas.add(t);
            }
            file.close();
            return turmas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<Disciplina> disciplinaExcelData(File fileImport, List<Curso> cursos) {
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
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
            Row row = rowIterator.next();
            //row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Disciplina d = new Disciplina();
                String siglaId = null;
                String cursoId = null;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 2:
                            siglaId = (cell.getStringCellValue());
                            break;
                        case 3:
                            d.setNome(cell.getStringCellValue());
                            break;
                        case 13:
                            Curso curso = new Curso();
                            cursoId = cell.getStringCellValue();
                            for (Curso c : cursos) {
                                if(c.getId().equalsIgnoreCase(cursoId)) {
                                    d.setCurso(c);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                d.setDisciplinaPK(new DisciplinaPK(siglaId, cursoId));
                disciplinas.add(d);
            }
            file.close();
            return disciplinas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(util.ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

package com.concafras.gestao.util;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Util {

  private static final String DATE_PATTERN = "dd/MM/yyyy";

  public static boolean isNullOrEmpty(String nome) {
    if(nome != null){
      if(nome.length() > 0){
        if(nome.trim().length() > 0){
          return false;
        }
      }
    }
    return true;
  }

  public static Date formatDataPadrao(String dataNascimento) {
    SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_PATTERN);
    try {
      return dataFormat.parse(dataNascimento);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public static String[] createIdArray(String id) {
    if (id.endsWith("" + '.')) {
      id = id.substring(0, id.length() - 1);
    }
    String[] aux = id.split("\\.");
    return aux;
  }

  public static boolean idArrayCompare(String[] menorArray, String[] maiorArray) {
    for (int i = 0; i < menorArray.length; i++) {
      if (!menorArray[i].equals(maiorArray[i])) {
        return false;
      }
    }
    return true;
  }

  public static String getRealPath(HttpServletRequest req) {
    ServletContext context = req.getSession().getServletContext();
    String path = context.getRealPath("/");

    if (path != null) {
      if (!path.endsWith(File.separator)) {
        path += File.separator;
      }
    }
    return path;
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null)
        emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  // then use Spring BeanUtils to copy and ignore null
  public static void myCopyProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }
  
  public static String getCellValue( FormulaEvaluator evaluator, CellReference celula, Sheet mySheet){
    Row linha = mySheet.getRow(celula.getRow());
    XSSFCell cell = (XSSFCell) linha.getCell(celula.getCol());
    String retorno = "";
    
    
    switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC :
          CellValue cValue = evaluator.evaluate(cell);
          
          double dv = cValue.getNumberValue();
          
          if (HSSFDateUtil.isCellDateFormatted(cell)) {
            
              Date date = HSSFDateUtil.getJavaDate(dv);
                    
              String dateFmt = cell.getCellStyle().getDataFormatString();
              /* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as
                     Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java
                     will always be 00 for date since "m" is minutes of the hour.*/
                    
              retorno = new CellDateFormatter(dateFmt).format(date);
              // takes care of idiosyncrasies of Excel
              
              retorno = new SimpleDateFormat("dd/MM/yyyy").format(date);
          } else {
            NumberFormat df = NumberFormat.getNumberInstance(new Locale("pt","BR"));
            df.setMaximumFractionDigits(0);
            retorno = String.valueOf( df.format( cell.getNumericCellValue() ) );
          }
                break;
            case XSSFCell.CELL_TYPE_STRING:   
                retorno = String.valueOf(  cell.getStringCellValue() );
                break;
            default:   
                retorno = String.valueOf(  cell.getRawValue() );
                break;   
      }
    if(retorno == null || retorno.trim().equals("null")){
      retorno = "";
    }
    return retorno;
  }
  
  public static String getCellValue( FormulaEvaluator evaluator, XSSFCell cell, Sheet mySheet){
    String retorno = "";
    
    
    switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC :
          CellValue cValue = evaluator.evaluate(cell);
          
          double dv = cValue.getNumberValue();
          
          if (HSSFDateUtil.isCellDateFormatted(cell)) {
            
              Date date = HSSFDateUtil.getJavaDate(dv);
                    
              String dateFmt = cell.getCellStyle().getDataFormatString();
              /* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as
                     Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java
                     will always be 00 for date since "m" is minutes of the hour.*/
                    
              retorno = new CellDateFormatter(dateFmt).format(date);
              // takes care of idiosyncrasies of Excel
              
              retorno = new SimpleDateFormat("dd/MM/yyyy").format(date);
          } else {
            NumberFormat df = NumberFormat.getNumberInstance(new Locale("pt","BR"));
            df.setMaximumFractionDigits(0);
            retorno = String.valueOf( df.format( cell.getNumericCellValue() ) );
          }
                break;
            case XSSFCell.CELL_TYPE_STRING:   
                retorno = String.valueOf(  cell.getStringCellValue() );
                break;
            default:   
                retorno = String.valueOf(  cell.getRawValue() );
                break;   
      }
    if(retorno == null || retorno.trim().equals("null")){
      retorno = "";
    }
    return retorno;
  }
  
  //            /** We now need something to iterate through the cells.**/
  //            Iterator<Row> rowIter = mySheet.rowIterator();
  //            while(rowIter.hasNext()){
  //
  //                XSSFRow myRow = (XSSFRow) rowIter.next();
  //                Iterator<Cell> cellIter = myRow.cellIterator();
  //                while(cellIter.hasNext()){
  //
  //                    XSSFCell myCell = (XSSFCell) cellIter.next();
  //                    //get cell index
  //                    System.out.println("Cell column index: " + myCell.getColumnIndex());
  //                    //get cell type
  //                    System.out.println("Cell Type: " + myCell.getCellType());
  //
  //                    //get cell value
  //                    switch (myCell.getCellType()) {
  //                    case XSSFCell.CELL_TYPE_NUMERIC :
  //                        System.out.println("Cell Value: " + myCell.getNumericCellValue());
  //                        break;
  //                    case XSSFCell.CELL_TYPE_STRING:
  //                        System.out.println("Cell Value: " + myCell.getStringCellValue());
  //                        break;
  //                    default:
  //                        System.out.println("Cell Value: " + myCell.getRawValue());
  //                        break;
  //                    }
  //                    System.out.println("---");
  //
  //                    if(myCell.getColumnIndex() == 0 &&
  //                            !myCell.getStringCellValue().trim().equals("") &&
  //                            !myCell.getStringCellValue().trim().equals("Item Number")){
  //                        count++;
  //                    }
  //
  //                }
  //
  //            }

}

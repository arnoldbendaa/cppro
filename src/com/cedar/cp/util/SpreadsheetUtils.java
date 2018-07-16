// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SpreadsheetUtils {

   private static String[] sCols;
   private static int sColSize = 256;


   public static List<Row> getRows(Sheet sheet) {
      ArrayList data = new ArrayList();
      Iterator it = sheet.rowIterator();

      while(it.hasNext()) {
         Row rowCell = (Row)it.next();
         data.add(rowCell);
      }

      return data;
   }

   public static List<Cell> getCells(Row row) {
      ArrayList data = new ArrayList();
      Iterator it = row.cellIterator();

      while(it.hasNext()) {
         Cell cellData = (Cell)it.next();
         data.add(cellData);
      }

      return data;
   }

   public static List<Cell> getOrderedCells(Row row) {
      ArrayList data = new ArrayList();

      for(short cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum() + 1; ++cellNum) {
         Cell cell = row.getCell(cellNum);
         if(cell != null) {
            data.add(cell);
         }
      }

      return data;
   }

   public static String getCellId(Cell cell) {
      StringBuffer sb = new StringBuffer();
      sb.append(getColId(cell.getColumnIndex()));
      sb.append(cell.getRowIndex() + 1);
      return sb.toString();
   }

   public static String getColumnLetter(int id) {
      return sCols[id];
   }

   private static String getColId(int id) {
      return sCols[id];
   }

   public static int getMaxRowNum(Sheet s) {
      int result = 0;
      Iterator rows = s.rowIterator();

      while(rows.hasNext()) {
         Row r = (Row)rows.next();
         if(r.getRowNum() > result) {
            result = r.getRowNum();
         }
      }

      return result;
   }

   public static Row getRow(Sheet s, int id) {
      Row result = s.getRow(id);
      if(result == null) {
         result = s.createRow(id);
      }

      return result;
   }

   public static Cell getCell(Row row, int id) {
      Cell result = row.getCell(id);
      if(result == null) {
         result = row.createCell(id);
      }

      return result;
   }

   public static void autoSizeSheet(Sheet sheet) {
      ArrayList colIds = new ArrayList();
      Iterator rowIter = sheet.rowIterator();

      while(rowIter.hasNext()) {
         Row i$ = (Row)rowIter.next();
         Iterator i = i$.cellIterator();

         while(i.hasNext()) {
            Cell cell = (Cell)i.next();
            Integer id = Integer.valueOf(cell.getColumnIndex());
            if(!colIds.contains(id)) {
               colIds.add(id);
            }
         }
      }

      Iterator i$1 = colIds.iterator();

      while(i$1.hasNext()) {
         Integer i1 = (Integer)i$1.next();
         sheet.autoSizeColumn(i1.shortValue());
      }

   }

   static {
      byte first = 65;
      byte second = 65;
      sCols = new String[sColSize];
      int counter = 0;

      for(int i = -1; i < 26; ++i) {
         int j;
         if(i == -1) {
            for(j = 0; j < 26; ++j) {
               sCols[counter++] = String.valueOf((char)(second + j));
            }
         } else {
            for(j = 0; j < 26 && counter < sColSize - 1; ++j) {
               sCols[counter++] = (char)(first + i) + String.valueOf((char)(second + j));
            }
         }

         if(counter >= sColSize - 1) {
            break;
         }
      }

   }
}

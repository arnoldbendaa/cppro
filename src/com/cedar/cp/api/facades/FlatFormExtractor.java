// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.CPFunctionsEvaluator;
import com.cedar.cp.api.facades.CPFunctionsEvaluatorImpl;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FlatFormExtractor {

   private CPFunctionsEvaluator mEvaluator;
   private Workbook mWorkbook;


   public FlatFormExtractor(CPFunctionsEvaluator internalEvaluator, Workbook workbook) {
      this.mWorkbook = workbook;
      this.mEvaluator = internalEvaluator;
   }

   public FlatFormExtractor(CPConnection connection, Workbook workbook) {
      this.mWorkbook = workbook;
      this.mEvaluator = new CPFunctionsEvaluatorImpl(connection);
   }

   public void extract(String costCentre, Map<String, String> params) throws Exception {
      long start = System.currentTimeMillis();
      this.mEvaluator.setCostCenter(costCentre);
      this.mEvaluator.setParameters(params);
      Iterator end = this.mWorkbook.getWorksheets().iterator();

      while(end.hasNext()) {
         Worksheet worksheet = (Worksheet)end.next();
         this.extract(worksheet);
      }

      long end1 = System.currentTimeMillis();
   }

   private String getHierarchyVisIdPropertyName(int dimIndex) {
      return WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", String.valueOf(dimIndex));
   }

   private void extract(Worksheet worksheet) throws Exception {
      worksheet.clearOriginlCellValues();
      String modelVisId = worksheet.getProperties() != null?(String)worksheet.getProperties().get(WorkbookProperties.MODEL_VISID.toString()):null;
      String financeCubeVisId = worksheet.getProperties() != null?(String)worksheet.getProperties().get(WorkbookProperties.FINANCE_CUBE_VISID.toString()):null;
      if(modelVisId != null && financeCubeVisId != null) {
         int iter = this.mEvaluator.setModelAndFinanceCube(modelVisId, financeCubeVisId);
         String[] results = new String[iter];

         for(int i$ = 0; i$ < iter; ++i$) {
            String entry = (String)worksheet.getProperties().get(this.getHierarchyVisIdPropertyName(i$));
            if(entry == null) {
               throw new ValidationException("Dimension " + i$ + " hierarchy vis id not defined");
            }

            results[i$] = entry;
         }

         this.mEvaluator.setHierarchies(results);
      }

      Iterator var10 = worksheet.iterator();

      while(var10.hasNext()) {
         CellLink var11 = (CellLink)var10.next();
         Cell var13 = (Cell)var11.getData();
         if(var13 != null) {
            boolean var14 = var13.isFormula();
            if(!var14 && var13.getInputMapping() != null) {
               var13.setText("");
               this.mEvaluator.addBatchExpression(var13.getInputMapping(), var13);
            } else if(var14 && var13.getOutputMapping() != null) {
               this.mEvaluator.addBatchExpression(this.convertOutputToInputMapping(var13.getOutputMapping()), var13);
            }
         }
      }

      Map var12 = this.mEvaluator.submitBatch();
      if(var12 != null) {
         Iterator var15 = var12.entrySet().iterator();

         while(var15.hasNext()) {
            Entry var16 = (Entry)var15.next();
            Cell cell = (Cell)var16.getKey();
            boolean isFormula = cell.isFormula();
            if(!isFormula && cell.getInputMapping() != null) {
               cell.setPostValue(String.valueOf(var16.getValue()));
            } else if(isFormula && cell.getOutputMapping() != null) {
               cell.getWorksheet().registerOriginalCellValue(cell, var16.getValue());
            }
         }
      }

   }

   private String convertOutputToInputMapping(String outputMapping) {
      return outputMapping.startsWith("cedar.cp.putCell(")?outputMapping.replaceFirst("cedar.cp.putCell\\(", "cedar.cp.getCell\\(type=\"M\","):outputMapping.replaceFirst("cedar.cp.post\\(", "cedar.cp.cell\\(M,");
   }
}

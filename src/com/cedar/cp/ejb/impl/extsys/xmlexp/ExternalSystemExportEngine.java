package com.cedar.cp.ejb.impl.extsys.xmlexp;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.extsys.xmlexp.ExternalSystemExportEngine$1;
import com.cedar.cp.ejb.impl.extsys.xmlexp.ExternalSystemExportEngine$2;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import com.cedar.cp.util.FileUtils;
import com.cedar.cp.util.XmlUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExternalSystemExportEngine extends AbstractDAO {

   private static final String SELECT_FROM_VFT_SQL_TEMPLATE = "select {dimCols} calendar_year_vis_id, value_type_vis_id, currency_vis_id, value from {viewName}";


   public void exportMappedModel(int mappedModelId, List<EntityRef> financeCubes, PrintWriter pw) throws ValidationException {
      String mappedModelDependents = "<0><1><2><3><4><5><6>";
      MappedModelEVO mappedModelEVO = (new MappedModelDAO()).getDetails(new MappedModelPK(mappedModelId), mappedModelDependents);
      if(mappedModelEVO == null) {
         throw new ValidationException("Unable to load mapped model with id:" + mappedModelId);
      } else {
         String externalSystemDependencies = "<0><1><2>";
         ExternalSystemEVO externalSystemEVO = (new ExternalSystemDAO()).getDetails(new ExternalSystemPK(mappedModelEVO.getExternalSystemId()), externalSystemDependencies);
         if(externalSystemEVO == null) {
            throw new ValidationException("Unable to load external system with id:" + mappedModelEVO.getExternalSystemId());
         } else {
            String modelDependents = "<9><0>";
            ModelDAO modelDAO = new ModelDAO();
            ModelEVO modelEVO = modelDAO.getDetails(new ModelPK(mappedModelEVO.getModelId()), modelDependents);
            if(modelEVO == null) {
               throw new ValidationException("Unable to load model width id:" + mappedModelEVO.getModelId());
            } else {
               ExtSysCompanyEVO extSysCompanyEVO = null;
               Iterator fileNameBuffer = externalSystemEVO.getExtSysCompanies().iterator();

               while(fileNameBuffer.hasNext()) {
                  ExtSysCompanyEVO writer = (ExtSysCompanyEVO)fileNameBuffer.next();
                  if(writer.getCompanyVisId().equals(mappedModelEVO.getCompanyVisId())) {
                     extSysCompanyEVO = writer;
                     break;
                  }
               }

               if(extSysCompanyEVO == null) {
                  throw new ValidationException("Failed to locate external system company evo:" + mappedModelEVO.getCompanyVisId());
               } else {
                  StringBuffer fileNameBuffer1 = new StringBuffer();
                  PrintWriter writer1 = null;

                  try {
                     writer1 = this.openWriter(mappedModelEVO, externalSystemEVO, modelEVO, fileNameBuffer1);
                     pw.print("Writing to output file: " + fileNameBuffer1.toString());
                     writer1.println("<?xml version=\"1.0\"?>");
                     writer1.print("<genericExport ");
                     XmlUtils.outputAttribute(writer1, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                     XmlUtils.outputAttribute(writer1, "xsi:noNamespaceSchemaLocation", "generic-export.xsd");
                     XmlUtils.outputAttribute(writer1, "externalSystemVisId", externalSystemEVO.getEntityRef().getNarrative());
                     XmlUtils.outputAttribute(writer1, "extractDateTime", this.getExtractDateTime());
                     writer1.println("> ");
                     writer1.println("<company ");
                     XmlUtils.outputAttribute(writer1, "companyVisId", mappedModelEVO.getCompanyVisId());
                     XmlUtils.outputAttribute(writer1, "companyDescription", extSysCompanyEVO.getDescription());
                     XmlUtils.outputAttribute(writer1, "modelVisId", modelEVO.getVisId());
                     XmlUtils.outputAttribute(writer1, "modelDescription", modelEVO.getDescription());
                     XmlUtils.outputAttribute(writer1, "dummy", Boolean.valueOf(extSysCompanyEVO.getDummy()));
                     XmlUtils.outputAttribute(writer1, "exportCalendarColumnIndex", Integer.valueOf(extSysCompanyEVO.getImportColumnCalendarIndex()));
                     writer1.println(">");
                     writer1.println("<ledger ");
                     ExtSysLedgerEVO e = null;
                     Iterator i$ = extSysCompanyEVO.getExtSysLedger().iterator();

                     while(i$.hasNext()) {
                        ExtSysLedgerEVO financeCubeRef = (ExtSysLedgerEVO)i$.next();
                        if(financeCubeRef.getLedgerVisId().equals(mappedModelEVO.getLedgerVisId())) {
                           e = financeCubeRef;
                           break;
                        }
                     }

                     if(e == null) {
                        throw new ValidationException("Failed to locate external system ledger evo:" + mappedModelEVO.getLedgerVisId());
                     }

                     XmlUtils.outputAttribute(writer1, "ledgerVisId", e.getLedgerVisId());
                     XmlUtils.outputAttribute(writer1, "description", e.getDescription());
                     XmlUtils.outputAttribute(writer1, "dummy", Boolean.valueOf(e.getDummy()));
                     writer1.println(">");
                     i$ = mappedModelEVO.getMappedDimensions().iterator();

                     while(i$.hasNext()) {
                        MappedDimensionEVO financeCubeRef2 = (MappedDimensionEVO)i$.next();
                        writer1.print("<dimension ");
                        ExtSysDimensionEVO financeCubeEVO = null;
                        Iterator i$1 = e.getExtSysDimensions().iterator();

                        while(true) {
                           if(i$1.hasNext()) {
                              ExtSysDimensionEVO evo = (ExtSysDimensionEVO)i$1.next();
                              if(!evo.getDimensionVisId().equals(financeCubeRef2.getPathVisId())) {
                                 continue;
                              }

                              financeCubeEVO = evo;
                           }

                           if(financeCubeEVO == null) {
                              throw new ValidationException("Unable to match mappedDimensionEVO:[" + financeCubeRef2.getPathVisId() + "] to ExtSysDimension.");
                           }

                           XmlUtils.outputAttribute(writer1, "dimensionVisId", financeCubeEVO.getDimensionVisId());
                           XmlUtils.outputAttribute(writer1, "description", financeCubeEVO.getDescription());
                           XmlUtils.outputAttribute(writer1, "exportColumnIndex", Integer.valueOf(financeCubeEVO.getImportColumnIndex()));
                           writer1.print("/>");
                           break;
                        }
                     }

                     i$ = financeCubes.iterator();

                     while(i$.hasNext()) {
                        EntityRef financeCubeRef1 = (EntityRef)i$.next();
                        writer1.println("<financeCube ");
                        FinanceCubeEVO financeCubeEVO1 = modelEVO.getFinanceCubesItem((FinanceCubePK)financeCubeRef1.getPrimaryKey());
                        if(financeCubeEVO1 == null) {
                           throw new ValidationException("Unable to locate finance cube with id:" + financeCubeRef1.getPrimaryKey());
                        }

                        XmlUtils.outputAttribute(writer1, "financeCubeVisId", financeCubeEVO1.getVisId());
                        XmlUtils.outputAttribute(writer1, "description", financeCubeEVO1.getDescription());
                        writer1.print(">");
                        this.writeFinanceValuesAsXML(financeCubeEVO1, writer1, e.getExtSysDimensions().size() + 1, pw);
                        writer1.print("</financeCube>");
                     }

                     writer1.println("</ledger>");
                     writer1.println("</company>");
                     writer1.println("</genericExport>");
                  } catch (SQLException e) {
                     e.printStackTrace();
                     throw new ValidationException("Failed reading from view:" + e.getMessage());
                  } catch (IOException e) {
                     e.printStackTrace();
                     throw new ValidationException("Failed writing output file:" + e.getMessage());
                  } finally {
                     if(writer1 != null) {
                        writer1.flush();
                        writer1.close();
                     }

                  }

               }
            }
         }
      }
   }

   private void writeFinanceValuesAsXML(FinanceCubeEVO financeCubeEVO, PrintWriter writer, int dimensionCount, PrintWriter log) throws IOException, SQLException {
      String viewName = this.queryExternalViewName(financeCubeEVO);
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ExternalSystemExportEngine$1 parser = new ExternalSystemExportEngine$1(this, "{", "}", "select {dimCols} calendar_year_vis_id, value_type_vis_id, currency_vis_id, value from {viewName}", dimensionCount, viewName);
         ps = this.getConnection().prepareStatement(parser.parse());
         rs = ps.executeQuery();

         int rowCount;
         for(rowCount = 0; rs.next(); ++rowCount) {
            int col = 1;
            writer.print("<value ");

            for(int i = 0; i < dimensionCount; ++i) {
               XmlUtils.outputAttribute(writer, "visId" + i, rs.getString(col++));
            }

            XmlUtils.outputAttribute(writer, "calendarYearVisId", rs.getString(col++));
            XmlUtils.outputAttribute(writer, "valueTypeVisId", rs.getString(col++));
            XmlUtils.outputAttribute(writer, "currencyVisId", rs.getString(col++));
            XmlUtils.outputAttribute(writer, "value", Long.valueOf(rs.getLong(col++)));
            writer.println("/>");
         }

         log.print("Wrote " + rowCount + " value rows for finance cube:" + financeCubeEVO.getVisId() + " - " + financeCubeEVO.getDescription());
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }
   }

   private String queryExternalViewName(FinanceCubeEVO financeCubeEVO) {
      return "XFT" + financeCubeEVO.getFinanceCubeId();
   }

   private PrintWriter openWriter(MappedModelEVO mappedModelEVO, ExternalSystemEVO externalSystemEVO, ModelEVO modelEVO, StringBuffer fileNameBuffer) throws ValidationException {
      try {
         String ve = externalSystemEVO.getEntityRef().getNarrative();
         if(externalSystemEVO.getExportTarget() != null && externalSystemEVO.getExportTarget().trim().length() != 0) {
            String exportTarget = externalSystemEVO.getExportTarget();
            if(this.isTemplateString(exportTarget)) {
               exportTarget = this.generateFileName(externalSystemEVO, modelEVO, exportTarget);
            }

            if(FileUtils.isFileURL(exportTarget)) {
               exportTarget = FileUtils.extractFileNameFromFileURL(exportTarget);
            }

            File f = new File(exportTarget);
            String fileName;
            String fullPathName;
            if(f.exists()) {
               if(f.isDirectory()) {
                  if(!f.canWrite()) {
                     throw new ValidationException("The export target defined for [" + ve + "] references a directory [" + f.getPath() + "] which is NOT writable by the CP server process.");
                  } else {
                     fileName = this.generateFileName(externalSystemEVO, modelEVO, "${extsys}-${model}-${timestamp}.xml");
                     fullPathName = this.makeFileName(exportTarget, fileName);
                     fileNameBuffer.append(fullPathName);
                     return new PrintWriter(new FileOutputStream(fullPathName));
                  }
               } else if(!f.canWrite()) {
                  throw new ValidationException("The export target defined for [" + ve + "] references an existing file [" + f.getPath() + "] which is NOT writable by the CP server process.");
               } else {
                  fileNameBuffer.append(f.getAbsolutePath());
                  return new PrintWriter(new FileOutputStream(f));
               }
            } else if(this.looksLikeAFileName(exportTarget)) {
               fileNameBuffer.append(exportTarget);
               return new PrintWriter(new FileOutputStream(exportTarget));
            } else if(!f.mkdirs()) {
               throw new ValidationException("Unable to create the export target defined for[" + ve + "] the export target used was [" + exportTarget + "]");
            } else {
               fileName = this.generateFileName(externalSystemEVO, modelEVO, "${extsys}-${model}-${timestamp}.xml");
               fullPathName = this.makeFileName(exportTarget, fileName);
               fileNameBuffer.append(fullPathName);
               return new PrintWriter(new FileOutputStream(fullPathName));
            }
         } else {
            throw new ValidationException("External system [" + ve + "] defines no export target.");
         }
      } catch (IOException e) {
         e.printStackTrace();
         throw new ValidationException("Failed to create export target:" + e.getMessage());
      }
   }

   private String generateFileName(ExternalSystemEVO externalSystemEVO, ModelEVO modelEVO, String formatString) {
      formatString = formatString.replaceAll("\\$\\{", "{");
      HashMap tokens = new HashMap();
      tokens.put("timestamp", this.createTimeStamp());
      tokens.put("extsys", externalSystemEVO.getEntityRef().getNarrative());
      tokens.put("model", modelEVO.getVisId());
      ExternalSystemExportEngine$2 parser = new ExternalSystemExportEngine$2(this, "{", "}", formatString, tokens);
      return parser.parse();
   }

   private String createTimeStamp() {
      return (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date());
   }

   private boolean isTemplateString(String outputName) {
      return outputName.indexOf("${") != -1;
   }

   private boolean looksLikeAFileName(String name) {
      return name.indexOf(46) != -1;
   }

   private String makeFileName(String path, String name) {
      StringBuffer sb = new StringBuffer();
      sb.append(path);
      if(path.length() > 0 && path.charAt(path.length() - 1) != File.separatorChar) {
         sb.append(File.separatorChar);
      }

      sb.append(name);
      return sb.toString();
   }

   private String getExtractDateTime() {
      return (new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss")).format(new Date());
   }

   private String replaceIllegalFileAndPathNameChars(String src) {
      StringBuffer ans = new StringBuffer();
      char[] arr$ = src.toCharArray();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         char c = arr$[i$];
         if(this.validFileOrPathNameCharacter(c)) {
            ans.append(c);
         }
      }

      return ans.toString();
   }

   private boolean validFileOrPathNameCharacter(char c) {
      return "`~.\\/*[]{}?^|\"&%:;\'<>=+".indexOf(c) == -1;
   }

   public String getEntityName() {
      return "ExternalSystemExportEngine";
   }

   // $FF: synthetic method
   static String accessMethod000(ExternalSystemExportEngine x0, String x1) {
      return x0.replaceIllegalFileAndPathNameChars(x1);
   }
}

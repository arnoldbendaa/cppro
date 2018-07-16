// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.virement.VirementFinanceCubeDTO;
import com.cedar.cp.utc.struts.virement.VirementQueryForm;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class VirementQuerySetupAction extends CPAction {

   private String[] mDateFormats = new String[]{"dd/MM/yyyy", "MM/dd/yyyy", "dd/MMMM/yyyy", "dd MM yyyy", "dd MMM yyyy"};
   private SimpleDateFormat mDateFormat;


   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      VirementQueryForm form = (VirementQueryForm)actionForm;
      if(this.getErrors(request) != null) {
         this.getErrors(request).clear();
      }

      if(form.getModelId() == 0) {
         this.queryModelAndFinanceCubes(request, form);
      } else {
         form.setCPContextId(request);
         form.getModelsAndFinanceCubes().clear();
         int financeCubeId = Integer.valueOf(form.getFinanceCubeId()).intValue();
         int modelId = Integer.valueOf(form.getModelId()).intValue();
         CPConnection connection = this.getCPContext(request).getCPConnection();
         EntityList dimensions = connection.getListHelper().getModelDimensions(modelId);
         form.setNoOfDims(dimensions.getNumRows());
         VirementQueryParams params = connection.getVirementRequestsProcess().getQueryParams(Integer.valueOf(financeCubeId));
         form.setOriginators(params.getOriginators());
         form.setAuthorisers(params.getAuthorisers());
         Integer requestStatus = null;
         if(form.getStatus() != null && form.getStatus().trim().length() > 0) {
            try {
               requestStatus = Integer.valueOf(Integer.parseInt(form.getStatus()));
               if(requestStatus != null && requestStatus.intValue() == -1) {
                  requestStatus = null;
               }
            } catch (NumberFormatException var30) {
               this.addFieldError(request, "status", var30.getMessage());
            }
         }

         Integer requestId = null;
         if(form.getRequestId() != null && form.getRequestId().trim().length() > 0) {
            try {
               requestId = Integer.valueOf(Integer.parseInt(form.getRequestId()));
            } catch (NumberFormatException var29) {
               this.addFieldError(request, "requestId", var29.getMessage());
            }
         }

         Date fromCreationDate = null;
         if(form.getFromCreationDate() != null && form.getFromCreationDate().trim().length() > 0) {
            try {
               fromCreationDate = this.parseDate(form.getFromCreationDate());
            } catch (ParseException var28) {
               this.addFieldError(request, "fromCreationDate", var28.getMessage());
            }
         }

         Date toCreationDate = null;
         if(form.getToCreationDate() != null && form.getToCreationDate().trim().length() > 0) {
            try {
               toCreationDate = this.parseDate(form.getToCreationDate());
            } catch (ParseException var27) {
               this.addFieldError(request, "toCreationDate", var27.getMessage());
            }
         }

         Double fromValue = null;
         if(form.getFromValue() != null && form.getFromValue().trim().length() > 0) {
            try {
               fromValue = Double.valueOf(Double.parseDouble(form.getFromValue()) * 10000.0D);
            } catch (NumberFormatException var26) {
               this.addFieldError(request, "fromValue", var26.getMessage());
            }
         }

         Double toValue = null;
         if(form.getToValue() != null && form.getToValue().trim().length() > 0) {
            try {
               toValue = Double.valueOf(Double.parseDouble(form.getToValue()) * 10000.0D);
            } catch (NumberFormatException var25) {
               this.addFieldError(request, "toValue", var25.getMessage());
            }
         }

         if(this.getErrors(request).isEmpty() && form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("executeQuery")) {
            List structureElements = form.getStructureElements();
            List virements = connection.getVirementRequestsProcess().queryVirementRequests(modelId, form.getNoOfDims(), form.getOwner(), form.getAuthoriser(), requestId, requestStatus, structureElements, fromValue, toValue, fromCreationDate, toCreationDate);
            if(!virements.isEmpty()) {
               StringBuilder errors1 = new StringBuilder();
               errors1.append("<Virements>");
               Iterator tFactory = virements.iterator();

               while(tFactory.hasNext()) {
                  String xslStream = (String)tFactory.next();
                  errors1.append(xslStream);
               }

               errors1.append("</Virements>");
               this.getLogger().debug("XML:" + errors1.toString());
               TransformerFactory tFactory1 = TransformerFactory.newInstance();
               InputStream xslStream1 = this.getClass().getResourceAsStream("VirementQuery.xsl");
               Transformer transformer = tFactory1.newTransformer(new StreamSource(xslStream1));
               StreamSource source = new StreamSource(new StringReader(errors1.toString()));
               StringWriter writer = new StringWriter();
               transformer.transform(source, new StreamResult(writer));
               form.setOutputText(writer.toString());
               writer.close();
               return mapping.findForward("virementQueryResults");
            }

            ActionErrors errors = new ActionErrors();
            errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", "No budget transfers found for this selection criteria."));
            this.addErrors(request, errors);
         }
      }

      return mapping.findForward("success");
   }

   private void addFieldError(HttpServletRequest request, String propertyName, String message) {
      ActionErrors errors = new ActionErrors();
      errors.add(propertyName, new ActionMessage("cp.virement.validation.error", message));
      this.addErrors(request, errors);
   }

   private void queryModelAndFinanceCubes(HttpServletRequest httpServletRequest, VirementQueryForm form) throws Exception {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      EntityList models = connection.getListHelper().getAllFinanceCubesWeb();

      for(int row = 0; row < models.getNumRows(); ++row) {
         form.getModelsAndFinanceCubes().add(new VirementFinanceCubeDTO(((Integer)models.getValueAt(row, "ModelId")).intValue(), ((ModelRef)models.getValueAt(row, "Model")).getNarrative(), ((Integer)models.getValueAt(row, "FinanceCubeId")).intValue(), ((FinanceCubeRef)models.getValueAt(row, "FinanceCube")).getNarrative() + " - " + models.getValueAt(row, "Description")));
      }

   }

   private synchronized Date parseDate(String dateStr) throws ParseException {
      if(this.mDateFormat == null) {
         this.mDateFormat = new SimpleDateFormat();
      }

      String[] arr$ = this.mDateFormats;
      int len$ = arr$.length;
      int i$ = 0;

      while(i$ < len$) {
         String fmt = arr$[i$];

         try {
            this.mDateFormat.applyPattern(fmt);
            return this.mDateFormat.parse(dateStr);
         } catch (ParseException var7) {
            ++i$;
         }
      }

      throw new ParseException("Invalid date format", 0);
   }
}

/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
/**
 * 
 */
package com.softproideas.app.admin.forms.flatforms.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormsDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.XMLFormService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.ZipServices;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.MetaDataXMLDigesterUtils;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata.MetaData;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.flatform.model.Workbook;
import com.softproideas.app.admin.financecubes.services.FinanceCubesServiceImpl;
import com.softproideas.app.admin.forms.flatforms.model.FlatFormsExportRequestDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.FileServices;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;

import de.schlichtherle.truezip.file.TFile;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("flatFormsExportService")
public class FlatFormsImportExportServiceImpl implements FlatFormsImportExportService {
    private static Logger logger = LoggerFactory.getLogger(FinanceCubesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.forms.flatforms.service.FlatFormsExportService#export(java.util.List) */
    @Override
    public File exportFlatForms(FlatFormsExportRequestDTO flatForms) throws ServiceException {
        File file, destFile = null;
        List<Integer> flatFormIds = flatForms.getFlatFormIds();
        List<String> financeCubeVisIds = flatForms.getFinanceCubeVisIds();
        int length = flatFormIds.size();
        HashMap<String, File> xmlFormDefTempFileList = new HashMap<String, File>();
        HashMap<String, File> xmlFormExcelTempFileList = new HashMap<String, File>();
        HashMap<String, File> xmlFormTempFile = new HashMap<String, File>();
        HashMap<String, File> metaDataTempFileList = new HashMap<String, File>();
        HashMap<String, File> fileList = new HashMap<String, File>();
        StringBuffer xmlForms = new StringBuffer();
        xmlForms.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlForms.append("<xmlForms>");
        try {
            int j = 0;
            for (int i = 0; i < length; i++) {
                XmlFormEditorSessionSSO sso = cpContextHolder.getXmlFormEditorSessionServer().getItemData(new XmlFormPK(flatFormIds.get(i)));
                XmlFormImpl xmlFormImpl = sso.getEditorData();

                j++;
                file = FileServices.createTemplateFile(j + ".xml");
                xmlFormDefTempFileList.put(j + ".xml", file);
                FileServices.writeDataToFile(file, xmlFormImpl.getDefinition());

                file = FileServices.createTemplateFile(j + ".xls");
                xmlFormExcelTempFileList.put(j + ".xls", file);
                FileServices.writeDataToFile(file, xmlFormImpl.getExcelFile());

                xmlForms.append("<xmlForm><visId>" + xmlFormImpl.getVisId() + "</visId><description>" + xmlFormImpl.getDescription() + "</description><type>" + xmlFormImpl.getType() + "</type><financeCubeVisId>" + financeCubeVisIds.get(i) + "</financeCubeVisId><defFileName>" + j + ".xml</defFileName><excelFileName>" + j + ".xls</excelFileName></xmlForm>");
            }
            xmlForms.append("</xmlForms>");

            file = FileServices.createTemplateFile("xmlforms.xml");
            xmlFormTempFile.put("xmlforms.xml", file);
            FileServices.writeDataToFile(file, xmlForms.toString());

            MetaData metaData = new MetaData();
            metaData.setXmlFormFileName("xmlforms.xml");
            metaData.setXmlFormDefFileNameList(xmlFormDefTempFileList.keySet());

            File e = FileServices.createTemplateFile("META-DATA.xml");
            metaDataTempFileList.put("META-DATA.xml", e);
            FileServices.writeDataToFile(e, metaData.toXML());

            fileList.putAll(xmlFormDefTempFileList);
            fileList.putAll(xmlFormExcelTempFileList);
            fileList.putAll(xmlFormTempFile);
            fileList.putAll(metaDataTempFileList);

            destFile = FileServices.createTemplateFile("export.zip");
            ZipServices.zipFiles(fileList, destFile.getPath());
        } catch (IOException e) {
            logger.error("Error during Flat Forms export operation!", e);
            throw new ServiceException("Error during Flat Forms export operation!", e);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }

        return destFile;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.forms.flatforms.service.FlatFormsImportExportService#importFlatForms(java.io.File) */
    @Override
    public ResponseMessage importFlatForms(File file) throws ServiceException {
        try {
            TFile zipFile = new TFile(file);
            ZipServices.verifyImportZipFile(zipFile);
            XMLFormsDigestRuleModel formsModel = MetaDataXMLDigesterUtils.parseXMLForm(zipFile);
            List<XMLFormDigestRuleModel> formList = formsModel.getXmlFormList();
            List<CommonImpExpItem> importList = makeImportList(formList, zipFile);

            importList = removeDuplicatedItems(importList);

            XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
            for (Iterator<CommonImpExpItem> iterator = importList.iterator(); iterator.hasNext();) {
                CommonImpExpItem element = iterator.next();
                XmlFormEditorSessionSSO xmlFormEditorSessionSSO = server.getNewItemData();
                XmlFormImpl xmlFormImpl = xmlFormEditorSessionSSO.getEditorData();
                fillXmlFormImpl(element, xmlFormImpl);
                server.insert(xmlFormImpl);
            }

            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (FileNotFoundException e) {
            logger.error("Error during Flat Forms import operation!", e);
            throw new ServiceException("Error during Flat Forms import operation!", e);
        } catch (Exception e) {
            logger.error("Error during Flat Forms import operation!", e);
            throw new ServiceException("Error during Flat Forms import operation!", e);
        }
    }

    /**
     * Make list suit to import operating.
     */
    private List<CommonImpExpItem> makeImportList(List<XMLFormDigestRuleModel> formList, File zipFile) throws ServiceException {
        try {
            List<CommonImpExpItem> importList = new ArrayList<CommonImpExpItem>();
            for (Iterator<XMLFormDigestRuleModel> iterator = formList.listIterator(); iterator.hasNext();) {
                XMLFormDigestRuleModel element = iterator.next();
                XMLFormModel userObj = new XMLFormModel();
                userObj.setItemName(element.getVisId());
                userObj.setTreeNodeType(2);
                userObj.setFormType(element.getType());
                userObj.setDescription(element.getDescription());
                String defFilePath = zipFile.getAbsolutePath() + "/" + element.getDefFileName();
                userObj.setDefFileName(defFilePath);
                userObj.setImportFileName(defFilePath);
                XMLFormService formService = new XMLFormService();
                String defFileContent = formService.readDefFile(defFilePath);
                userObj.setDefFileContent(defFileContent);

                Workbook workBook = formService.parseFlatFormWorkBook(defFileContent);
                userObj.setWorkBook(workBook);
                String excelFilePath = zipFile.getAbsolutePath() + "/" + element.getExcelFileName();
                byte[] excelFile = formService.readExcelFile(excelFilePath);
                userObj.setExcelFile(excelFile);

                userObj.setFinanceCubeVisId(element.getFinanceCubeVisId());

                importList.add(userObj);
            }
            return importList;
        } catch (Exception e) {
            logger.error("Error during construct Flat Forms list to import!", e);
            throw new ServiceException("Error during construct Flat Forms list to import!", e);
        }
    }

    private List<CommonImpExpItem> removeDuplicatedItems(List<CommonImpExpItem> items) {
        if (items != null && items.size() > 0) {
            List<CommonImpExpItem> formDBList = this.getListXMLForms();
            Iterator<CommonImpExpItem> i$ = items.iterator();

            while (i$.hasNext()) {
                CommonImpExpItem selectedItem = (CommonImpExpItem) i$.next();
                if (formDBList != null && formDBList.contains(selectedItem)) {
                    i$.remove();
                }
            }

            return items;
        } else {
            return null;
        }
    }

    private List<CommonImpExpItem> getListXMLForms() {
        ArrayList<CommonImpExpItem> exportItemList = new ArrayList<CommonImpExpItem>();
        EntityList listXmlForm = cpContextHolder.getListSessionServer().getAllXcellXmlForms();
        Object[] listVisID = listXmlForm.getValues("XmlForm");
        Object[] listDescription = listXmlForm.getValues("Description");
        if (listVisID == null) {
            return exportItemList;
        } else {
            CommonImpExpItem item = null;

            for (int i = 0; i < listVisID.length; ++i) {
                item = new CommonImpExpItem();
                item.setTreeNodeType(2);
                item.setItemName(listVisID[i].toString());
                item.setDescription((String) listDescription[i]);
                exportItemList.add(item);
            }

            return exportItemList;
        }
    }

    /**
     * Fill XmlFormImpl data during import operating.
     */
    private void fillXmlFormImpl(CommonImpExpItem element, XmlFormImpl xmlFormImpl) throws ServiceException {
        try {
            xmlFormImpl.setDescription(element.getDescription());
            xmlFormImpl.setType(((XMLFormModel) element).getFormType());
            Workbook valueMapping = ((XMLFormModel) element).getWorkBook();
            StringWriter financeCubeId = new StringWriter();
            valueMapping.writeXml(financeCubeId);
            xmlFormImpl.setDefinition(financeCubeId.toString());
            xmlFormImpl.setExcelFile(((XMLFormModel) element).getExcelFile());

            if (((XMLFormModel) element).getFinanceCubeVisId() != null && ((XMLFormModel) element).getFinanceCubeVisId().trim().length() > 0) {
                DefaultValueMapping valueMapping1 = buildFinanceCubeMapping();
                int financeCubeId1 = ((Integer) valueMapping1.getValue(((XMLFormModel) element).getFinanceCubeVisId())).intValue();
                xmlFormImpl.setFinanceCubeId(financeCubeId1);
            }
            xmlFormImpl.setVisId(element.getItemName());
            // save JSON
            String json = toJSON(((XMLFormModel) element).getExcelFile());
            xmlFormImpl.setJsonForm(json);
        } catch (IOException e) {
            logger.error("Error during build Flat Form to import!", e);
            throw new ServiceException("Error during build Flat Form to import!", e);
        }
    }
    
    private String toJSON(byte[] xls) throws ServiceException {
        try {
            String json = "";
            /* ExcelIO converter */
            // ExcelIOServiceImpl excelIO = new ExcelIOServiceImpl();
            // json = excelIO.toJSON(xmlFormModel.getExcelFile()).toString();

            /* Wijmo converter */
            json = cpContextHolder.getCPConnection().getExcelIOProcess().convertXlsToJson(xls, "");

            return json;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error during parse workbook to JSON! :" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Import file content financeCubeVisId, but not financeCubeId. This function create structure to get financeCubeId by financeCubeVisId.
     */
    private DefaultValueMapping buildFinanceCubeMapping() {
        EntityList fcubes = cpContextHolder.getListHelper().getAllSimpleFinanceCubes();
        int size = fcubes.getNumRows();
        String[] lits = new String[size];
        Object[] values = new Object[size];

        for (int i = 0; i < size; ++i) {
            lits[i] = fcubes.getValueAt(i, "FinanceCube").toString();
            values[i] = fcubes.getValueAt(i, "FinanceCubeId");
        }

        return new DefaultValueMapping(lits, values);
    }

}

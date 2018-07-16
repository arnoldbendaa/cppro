// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.LookupTableDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.LookupTablesDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormsDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.XMLFormService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.MetaDataXMLDigesterUtils;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.FormConfigFactory;
import de.schlichtherle.truezip.file.TFile;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeUtils {

   public static TreeNode buildXMLFormTreeFromZipFile(TFile zipFile) throws Exception {
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
      rootNode.setUserObject(new CommonImpExpItem());
      XMLFormService formService = new XMLFormService();
      LookupTablesDigestRuleModel lookupTables = MetaDataXMLDigesterUtils.parseLookupTable(zipFile);
      if(lookupTables != null && lookupTables.getLookupTableModelList() != null && lookupTables.getLookupTableModelList().size() > 0) {
         DefaultMutableTreeNode formsModel = new DefaultMutableTreeNode();
         CommonImpExpItem formsNode = new CommonImpExpItem();
         formsNode.setItemName("Lookup Tables");
         formsNode.setTreeNodeType(0);
         formsModel.setUserObject(formsNode);
         rootNode.add(formsModel);
         List itemNode = lookupTables.getLookupTableModelList();
         Iterator formList = itemNode.iterator();

         while(formList.hasNext()) {
            LookupTableDigestRuleModel iterator = (LookupTableDigestRuleModel)formList.next();
            DefaultMutableTreeNode formDigestRuleModel = new DefaultMutableTreeNode(iterator);
            CommonImpExpItem formNode = new CommonImpExpItem();
            formNode.setItemName(iterator.getVisId());
            formNode.setDescription(iterator.getDescription());
            formNode.setAutoSubmit(iterator.isAutoSubmit());
            formNode.setTreeNodeType(1);
            formNode.setImportFileName(zipFile.getAbsolutePath() + "/" + iterator.getExportExcelFile());
            formDigestRuleModel.setUserObject(formNode);
            formsModel.add(formDigestRuleModel);
         }
      }

      XMLFormsDigestRuleModel formsModel1 = MetaDataXMLDigesterUtils.parseXMLForm(zipFile);
      if(formsModel1 != null && formsModel1.getXmlFormList() != null && formsModel1.getXmlFormList().size() > 0) {
         DefaultMutableTreeNode formsNode1 = new DefaultMutableTreeNode();
         CommonImpExpItem itemNode1 = new CommonImpExpItem();
         itemNode1.setItemName("XML Forms");
         itemNode1.setTreeNodeType(0);
         formsNode1.setUserObject(itemNode1);
         rootNode.add(formsNode1);
         List formList1 = formsModel1.getXmlFormList();
         Iterator iterator1 = formList1.iterator();

         while(iterator1.hasNext()) {
            XMLFormDigestRuleModel formDigestRuleModel1 = (XMLFormDigestRuleModel)iterator1.next();
            DefaultMutableTreeNode formNode1 = new DefaultMutableTreeNode(formDigestRuleModel1.getVisId());
            XMLFormModel userObj = new XMLFormModel();
            userObj.setItemName(formDigestRuleModel1.getVisId());
            userObj.setTreeNodeType(2);
            userObj.setFormType(formDigestRuleModel1.getType());
            userObj.setDescription(formDigestRuleModel1.getDescription());
            String defFilePath = zipFile.getAbsolutePath() + "/" + formDigestRuleModel1.getDefFileName();
            userObj.setDefFileName(defFilePath);
            userObj.setImportFileName(defFilePath);
            String defFileContent = formService.readDefFile(defFilePath);
            userObj.setDefFileContent(defFileContent);
            Workbook workBook = null;
            switch(userObj.getFormType()) {
            case 4:
               workBook = formService.parseFlatFormWorkBook(defFileContent);
               userObj.setWorkBook(workBook);
            case 6:
            case 7:
                workBook = formService.parseFlatFormWorkBook(defFileContent);
                userObj.setWorkBook(workBook);
                String excelFilePath = zipFile.getAbsolutePath() + "/" + formDigestRuleModel1.getExcelFileName();
                byte[] excelFile = formService.readExcelFile(excelFilePath);
                userObj.setExcelFile(excelFile);
            default:
               FormConfig formConfig = FormConfigFactory.createStandardForm(defFileContent);
               userObj.setFormConfig(formConfig);
               userObj.setFinanceCubeVisId(formDigestRuleModel1.getFinanceCubeVisId());
               formNode1.setUserObject(userObj);
               formsNode1.add(formNode1);
            }
         }
      }

      return rootNode;
   }

   public static DefaultMutableTreeNode buildXMLFormNodeFromList(Collection<CommonImpExpItem> itemList) {
      if(itemList != null && itemList.size() > 0) {
         DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
         CommonImpExpItem folderItem = new CommonImpExpItem();
         folderItem.setItemName("XML Forms");
         folderItem.setTreeNodeType(0);
         parentNode.setUserObject(folderItem);

         CommonImpExpItem commonImpExpItem;
         for(Iterator i$ = itemList.iterator(); i$.hasNext(); commonImpExpItem.setOverwrite(true)) {
            commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == 2) {
               DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
               childNode.setUserObject(commonImpExpItem);
               parentNode.add(childNode);
            }
         }

         if(parentNode.children().hasMoreElements()) {
            return parentNode;
         }
      }

      return null;
   }

   public static TreeNode buildDuplicatedTreeItem(Collection<CommonImpExpItem> itemList) {
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
      rootNode.setUserObject(new CommonImpExpItem());
      DefaultMutableTreeNode lookupNode = buildLookupTableNodeFromList(itemList);
      if(lookupNode != null) {
         rootNode.add(lookupNode);
      }

      DefaultMutableTreeNode formNode = buildXMLFormNodeFromList(itemList);
      if(formNode != null) {
         rootNode.add(formNode);
      }

      return rootNode;
   }

   public static DefaultMutableTreeNode buildLookupTableNodeFromList(Collection<CommonImpExpItem> itemList) {
      if(itemList != null && itemList.size() > 0) {
         DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
         CommonImpExpItem folderItem = new CommonImpExpItem();
         folderItem.setItemName("Lookup Tables");
         folderItem.setTreeNodeType(0);
         parentNode.setUserObject(folderItem);

         CommonImpExpItem commonImpExpItem;
         for(Iterator i$ = itemList.iterator(); i$.hasNext(); commonImpExpItem.setOverwrite(true)) {
            commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == 1) {
               DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
               childNode.setUserObject(commonImpExpItem);
               parentNode.add(childNode);
            }
         }

         if(parentNode.children().hasMoreElements()) {
            return parentNode;
         }
      }

      return null;
   }

   public static DefaultMutableTreeNode buildFormsHaveCubeTree(Collection<CommonImpExpItem> itemList) {
      if(itemList != null && itemList.size() > 0) {
         DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();
         CommonImpExpItem folderItem = new CommonImpExpItem();
         folderItem.setItemName("XML Forms");
         folderItem.setTreeNodeType(0);
         parentNode.setUserObject(folderItem);
         Iterator i$ = itemList.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == 2 && commonImpExpItem instanceof XMLFormModel) {
               XMLFormModel xmlFormModel = (XMLFormModel)commonImpExpItem;
               if(xmlFormModel.getFormType() > 2) {
                  DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
                  childNode.setUserObject(commonImpExpItem);
                  parentNode.add(childNode);
               }
            }
         }

         return parentNode;
      } else {
         return null;
      }
   }
}

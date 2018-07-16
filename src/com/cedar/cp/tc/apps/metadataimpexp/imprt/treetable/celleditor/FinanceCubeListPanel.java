// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportSelectDimAndHierPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialogController;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.FinanceCubeService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreeNode;

public class FinanceCubeListPanel extends JPanel implements ActionListener, ItemListener {

   private JComboBox financeCubeCombo = new JComboBox();
   private JButton editButton = new JButton("Edit..");
   private CommonImpExpItem editObj = null;
   private FinanceCubeService cubeService = new FinanceCubeService();
   private int numberOfDim = 0;


   public FinanceCubeListPanel() {}

   public FinanceCubeListPanel(CommonImpExpItem editObj) {
      this.setLayout(new BoxLayout(this, 0));
      this.editObj = editObj;
      this.financeCubeCombo.addItem("-");
      this.add(this.financeCubeCombo);
      this.add(this.editButton);
      this.editButton.addActionListener(this);
      if(editObj != null && editObj instanceof XMLFormModel) {
         XMLFormModel xmlFormModel = (XMLFormModel)editObj;
         switch(xmlFormModel.getFormType()) {
         case 3:
            this.buildFinanceFormView();
            break;
         case 4:
            this.buildFlatFormView();
         }

         this.financeCubeCombo.addItemListener(this);
      }

   }

   private void buildFinanceFormView() {
      try {
         if(this.editObj != null && this.editObj instanceof XMLFormModel) {
            XMLFormModel ex = (XMLFormModel)this.editObj;
            if(ex.getFormType() == 3) {
               FormConfig formConfig = ex.getFormConfig();
               if(formConfig != null) {
                  Iterator inputs = formConfig.getInputs();

                  label47:
                  while(inputs.hasNext()) {
                     Object dimVisList = inputs.next();
                     if(dimVisList instanceof FinanceCubeInput) {
                        FinanceCubeInput i$ = (FinanceCubeInput)dimVisList;
                        int string = i$.getChildCount();
                        int i = 0;

                        while(true) {
                           if(i >= string) {
                              break label47;
                           }

                           TreeNode node = i$.getChildAt(i);
                           if(node instanceof StructureColumnValue) {
                              ++this.numberOfDim;
                           }

                           ++i;
                        }
                     }
                  }

                  List var10 = this.cubeService.filterFinaceCubeVisIdListByDim(this.numberOfDim);
                  if(var10 != null && var10.size() > 0) {
                     Iterator var11 = var10.iterator();

                     while(var11.hasNext()) {
                        String var12 = (String)var11.next();
                        this.financeCubeCombo.addItem(var12);
                     }
                  }
               }
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         JOptionPane.showMessageDialog(this, var9.getMessage());
      }

   }

   private void buildFlatFormView() {
      DefaultValueMapping cubeMapping = this.cubeService.buildFinanceCubeMapping();
      if(cubeMapping != null) {
         List cubeVisIdList = cubeMapping.getLiterals();
         Iterator i$ = cubeVisIdList.iterator();

         while(i$.hasNext()) {
            String visId = (String)i$.next();
            this.financeCubeCombo.addItem(visId);
         }
      }

   }

   public void actionPerformed(ActionEvent e) {
      String fcVis = this.financeCubeCombo.getSelectedItem().toString();
      if(e.getSource() == this.editButton) {
         try {
            JDialog ex = new JDialog();
            if(this.canEit()) {
               XMLFormModel formModel = (XMLFormModel)this.editObj;
               Hashtable dimHashtable = null;
               switch(formModel.getFormType()) {
               case 3:
                  dimHashtable = this.cubeService.getDimensionHashtableByCubeVisId(fcVis);
                  ImportSelectDimAndHierPanel panel = new ImportSelectDimAndHierPanel(fcVis, this.numberOfDim, dimHashtable, this.editObj, ex);
                  ex.getContentPane().add(panel);
                  ex.setSize(500, 400);
                  ex.setModal(true);
                  ex.setVisible(true);
                  break;
               case 4:
                  Workbook workbook = formModel.getWorkBook();
                  WorksheetEditDialogController wsDialogController = new WorksheetEditDialogController();
                  wsDialogController.setWorkbook(workbook);
                  wsDialogController.showWorksheetEditDialog();
               }
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   private boolean canEit() {
      String fcVis = this.financeCubeCombo.getSelectedItem().toString();
      if(fcVis != null && !fcVis.equals("-")) {
         return true;
      } else {
         JOptionPane.showMessageDialog(this, "Please select new Finance Cube", "Error", 0);
         return false;
      }
   }

   public void itemStateChanged(ItemEvent e) {
      String fcVis = this.financeCubeCombo.getSelectedItem().toString();
      XMLFormModel formModel = (XMLFormModel)this.editObj;
      formModel.setFinanceCubeVisId(fcVis);
   }
}

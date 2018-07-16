// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.OneColumnLayout;
import com.cedar.cp.util.awt.QuantumCheckBox;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ProtectionPanel extends JPanel {

   private QuantumCheckBox mLocked;
   private QuantumCheckBox mHidden;


   public ProtectionPanel() {
      super(new OneColumnLayout(5));
      this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      this.mLocked = new QuantumCheckBox("Locked");
      this.mHidden = new QuantumCheckBox("Hidden");
      this.add(this.mLocked);
      this.add(this.mHidden);
   }

   public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
      if(CellFormatEntry.hasMultipleFormats(activeFormats, "locked")) {
         this.mLocked.setAllowIntermediateState(true);
         this.mLocked.setQuantumState(2);
      } else {
         this.mLocked.setAllowIntermediateState(false);
         this.mLocked.setQuantumState(format.isLocked()?1:0);
      }

      if(CellFormatEntry.hasMultipleFormats(activeFormats, "hidden")) {
         this.mHidden.setAllowIntermediateState(true);
         this.mHidden.setQuantumState(2);
      } else {
         this.mHidden.setAllowIntermediateState(false);
         this.mHidden.setQuantumState(format.isHidden()?1:0);
      }

   }

   public void updateCell(CellFormat format) {
      if(this.mLocked.isOn()) {
         format.setLocked(true);
      } else if(this.mLocked.isOff()) {
         format.setLocked(false);
      }

      if(this.mHidden.isOn()) {
         format.setHidden(true);
      } else if(this.mHidden.isOff()) {
         format.setHidden(false);
      }

   }
}

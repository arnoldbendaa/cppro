// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.QuantumCheckBox;
import com.cedar.cp.util.awt.QuantumStateModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.text.View;

public class QuantumCheckBox$QuantumCheckBoxUI extends BasicCheckBoxUI {

   private static QuantumCheckBox$QuantumCheckBoxUI sQuantumCheckBoxUI = new QuantumCheckBox$QuantumCheckBoxUI();
   private static Dimension size = new Dimension();
   private static Rectangle viewRect = new Rectangle();
   private static Rectangle iconRect = new Rectangle();
   private static Rectangle textRect = new Rectangle();
   private Icon mBothIcon;
   private Icon mTickIcon;
   private Icon mNoneIcon;


   public static ComponentUI createUI(JComponent b) {
      return sQuantumCheckBoxUI;
   }

   public void installUI(JComponent c) {
      super.installUI(c);
      QuantumCheckBox qcb = (QuantumCheckBox)c;
      qcb.setIcon(this.getNoneIcon());
      qcb.setSelectedIcon(this.getTickIcon());
   }

   public Icon getTickIcon() {
      if(this.mTickIcon == null) {
         this.mTickIcon = new ImageIcon(this.getClass().getResource("quantum-tick.gif"));
      }

      return this.mTickIcon;
   }

   public Icon getBothIcon() {
      if(this.mBothIcon == null) {
         this.mBothIcon = new ImageIcon(this.getClass().getResource("quantum-both.gif"));
      }

      return this.mBothIcon;
   }

   public Icon getNoneIcon() {
      if(this.mNoneIcon == null) {
         this.mNoneIcon = new ImageIcon(this.getClass().getResource("quantum-none.gif"));
      }

      return this.mNoneIcon;
   }

   public synchronized void paint(Graphics g, JComponent c) {
      QuantumCheckBox b = (QuantumCheckBox)c;
      QuantumStateModel model = (QuantumStateModel)b.getModel();
      Font f = c.getFont();
      g.setFont(f);
      FontMetrics fm = c.getFontMetrics(f);
      Insets i = c.getInsets();
      size = b.getSize(size);
      viewRect.x = i.left;
      viewRect.y = i.top;
      viewRect.width = size.width - (i.right + viewRect.x);
      viewRect.height = size.height - (i.bottom + viewRect.y);
      iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
      textRect.x = textRect.y = textRect.width = textRect.height = 0;
      Icon altIcon = b.getIcon();
      Object selectedIcon = null;
      Object disabledIcon = null;
      String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), altIcon != null?altIcon:this.getDefaultIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null?0:b.getIconTextGap());
      if(c.isOpaque()) {
         g.setColor(b.getBackground());
         g.fillRect(0, 0, size.width, size.height);
      }

      if(!model.isEnabled()) {
         if(model.isSelected()) {
            altIcon = b.getDisabledSelectedIcon();
         } else {
            altIcon = b.getDisabledIcon();
         }
      } else if(model.isPressed() && model.isArmed()) {
         altIcon = b.getPressedIcon();
         if(altIcon == null) {
            altIcon = b.getSelectedIcon();
         }
      } else if(model.isOn()) {
         if(b.isRolloverEnabled() && model.isRollover()) {
            altIcon = b.getRolloverSelectedIcon();
            if(altIcon == null) {
               altIcon = b.getSelectedIcon();
            }
         } else {
            altIcon = b.getSelectedIcon();
         }
      } else if(model.isBoth()) {
         if(b.isRolloverEnabled() && model.isRollover()) {
            altIcon = b.getRolloverSelectedIcon();
            if(altIcon == null) {
               altIcon = this.getBothIcon();
            }
         } else {
            altIcon = this.getBothIcon();
         }
      } else if(model.isOff()) {
         altIcon = b.getIcon();
      } else if(b.isRolloverEnabled() && model.isRollover()) {
         altIcon = b.getRolloverIcon();
      }

      if(altIcon == null) {
         altIcon = b.getIcon();
      }

      altIcon.paintIcon(c, g, iconRect.x, iconRect.y);
      if(text != null) {
         View v = (View)c.getClientProperty("html");
         if(v != null) {
            v.paint(g, textRect);
         } else {
            this.paintText(g, b, textRect, text);
         }

         if(b.hasFocus() && b.isFocusPainted() && textRect.width > 0 && textRect.height > 0) {
            this.paintFocus(g, textRect, size);
         }
      }

   }

}

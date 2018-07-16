// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.skin.border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.MultipleGradientPaint.CycleMethod;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.painter.border.ClassicBorderPainter;
import org.jvnet.substance.utils.SubstanceArrowButton;
import org.jvnet.substance.utils.SubstanceColorUtilities;
import org.jvnet.substance.utils.SubstanceSizeUtils;
import org.jvnet.substance.utils.combo.SubstanceComboBoxButton;
import org.jvnet.substance.utils.scroll.SubstanceScrollButton;

public class COABorderPainter extends ClassicBorderPainter {

   public void paintBorder(Graphics var1, Component var2, int var3, int var4, Shape var5, Shape var6, SubstanceColorScheme var7, SubstanceColorScheme var8, float var9, boolean var10) {
      if(var5 != null) {
         Graphics2D var11 = (Graphics2D)var1.create();
         var11.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var11.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
         SubstanceColorScheme var13 = var10?var8:var7;
         double var14 = 1.0D - (double)var9;
         Color var17;
         Color var16;
         Color var18;
         if(!(var2 instanceof JTextComponent) && !(var2 instanceof JCheckBox) && !(var2 instanceof JRadioButton)) {
            if((var2 instanceof JButton || var2 instanceof JToggleButton) && !(var2 instanceof SubstanceComboBoxButton) && !(var2 instanceof SubstanceScrollButton) && var13.getDisplayName().equals("COA Border Default")) {
               var16 = SubstanceColorUtilities.getInterpolatedColor(var7.getLightColor(), var13.getLightColor(), var14);
               var17 = var16;
               var18 = var16;
            } else {
               var16 = this.getTopBorderColor(var7, var13, var14, var10);
               var17 = this.getMidBorderColor(var7, var13, var14, var10);
               var18 = this.getBottomBorderColor(var7, var13, var14, var10);
            }
         } else {
            if(var13.getDisplayName().equals("COA Border Default")) {
               var16 = SubstanceColorUtilities.getInterpolatedColor(var7.getUltraLightColor(), var13.getUltraLightColor(), var14);
            } else {
               var16 = SubstanceColorUtilities.getInterpolatedColor(var7.getMidColor(), var13.getMidColor(), var14);
            }

            var17 = var16;
            var18 = var16;
         }

         if(var16 != null && var17 != null && var18 != null) {
            float var19 = SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var2));
            boolean var20 = var2 == null?false:var2.getClass().isAnnotationPresent(SubstanceArrowButton.class);
            int var21 = var20?0:1;
            int var22 = var20?2:0;
            var11.setStroke(new BasicStroke(var19, var22, var21));
            LinearGradientPaint var23 = new LinearGradientPaint(0.0F, 0.0F, 0.0F, (float)var4, new float[]{0.0F, 0.5F, 1.0F}, new Color[]{var16, var17, var18}, CycleMethod.REPEAT);
            var11.setPaint(var23);
            var11.draw(var5);
         }

         var11.dispose();
      }
   }
}

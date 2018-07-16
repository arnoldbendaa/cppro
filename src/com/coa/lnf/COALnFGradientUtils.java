// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.GeneralPath;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame.JDesktopIcon;
import org.jvnet.lafwidget.animation.FadeKind;
import org.jvnet.lafwidget.animation.FadeState;
import org.jvnet.substance.api.ColorSchemeAssociationKind;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceConstants.Side;
import org.jvnet.substance.painter.gradient.StandardGradientPainter;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.jvnet.substance.shaper.SubstanceButtonShaper;
import org.jvnet.substance.utils.PulseTracker;
import org.jvnet.substance.utils.SubstanceColorSchemeUtilities;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceFadeUtilities;
import org.jvnet.substance.utils.SubstanceOutlineUtilities;
import org.jvnet.substance.utils.SubstanceSizeUtils;

public class COALnFGradientUtils {

   public static MultipleGradientPaint getButtonGradient(AbstractButton var0, SubstanceButtonShaper var1, StandardGradientPainter var2, int var3, int var4, ComponentState var5) {
      ButtonModel var6 = var0.getModel();
      ComponentState var7 = SubstanceCoreUtilities.getPrevComponentState(var0, (String)null);
      float var8 = var5.getCyclePosition();
      boolean var9 = false;
      if(var0 instanceof JButton) {
         JButton var10 = (JButton)var0;
         if(PulseTracker.isPulsating(var10) && var5 != ComponentState.PRESSED_SELECTED && var5 != ComponentState.PRESSED_UNSELECTED) {
            var9 = true;
            var8 = (float)((int)(PulseTracker.getCycles(var10) % 20L));
            if(var8 > 10.0F) {
               var8 = 19.0F - var8;
            }

            var8 /= 10.0F;
         }
      }

      SubstanceColorScheme var23 = SubstanceColorSchemeUtilities.getColorScheme(var0, var5);
      SubstanceColorScheme var11 = var23;
      boolean var12 = false;
      if(SubstanceCoreUtilities.isTitleCloseButton(var0)) {
         for(Object var13 = var0; var13 != null; var13 = ((Component)var13).getParent()) {
            if(var13 instanceof JInternalFrame) {
               JInternalFrame var29 = (JInternalFrame)var13;
               var12 = Boolean.TRUE.equals(var29.getClientProperty("windowModified"));
               break;
            }

            if(var13 instanceof JRootPane) {
               JRootPane var28 = (JRootPane)var13;
               var12 = Boolean.TRUE.equals(var28.getClientProperty("windowModified"));
               break;
            }

            if(var13 instanceof JDesktopIcon) {
               JDesktopIcon var14 = (JDesktopIcon)var13;
               JInternalFrame var15 = var14.getInternalFrame();
               var12 = Boolean.TRUE.equals(var15.getClientProperty("windowModified"));
               break;
            }
         }

         if(var12) {
            var11 = SubstanceColorSchemeUtilities.YELLOW;
            var23 = SubstanceColorSchemeUtilities.ORANGE;
         }
      }

      if(!var12 && !var9 && var6.isEnabled()) {
         FadeState var24 = SubstanceFadeUtilities.getFadeState(var0, new FadeKind[]{FadeKind.ROLLOVER, FadeKind.SELECTION, FadeKind.PRESS});
         if(var24 != null) {
            var23 = SubstanceColorSchemeUtilities.getColorScheme(var0, var5);
            var11 = SubstanceColorSchemeUtilities.getColorScheme(var0, var7);
            var8 = var24.getFadePosition();
            if(var24.isFadingIn()) {
               var8 = 1.0F - var8;
            }
         }
      }

      Set var25 = SubstanceCoreUtilities.getSides(var0, "substancelaf.buttonside");
      String var26 = "";

      Side var16;
      for(Iterator var27 = var25.iterator(); var27.hasNext(); var26 = var26 + var16.name() + "-") {
         var16 = (Side)var27.next();
      }

      Set var31 = SubstanceCoreUtilities.getSides(var0, "substancelaf.buttonopenSide");
      int var30 = (int)Math.ceil(3.0D * (double)SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var0)));
      int var17 = var31.contains(Side.LEFT)?var30:0;
      int var18 = var31.contains(Side.RIGHT)?var30:0;
      int var19 = var31.contains(Side.TOP)?var30:0;
      int var20 = var31.contains(Side.BOTTOM)?var30:0;
      int var21 = (int)Math.floor((double)SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var0)) / 2.0D);
      Shape var22 = var1.getButtonOutline(var0, new Insets(var21, var21, var21, var21), var3 + var17 + var18, var4 + var19 + var20, false);
      return getGradient(var2, var3 + var17 + var18, var4 + var19 + var20, var22, var23, var11, var8, true, var23 != var11);
   }

   public static MultipleGradientPaint getGradient(StandardGradientPainter var0, int var1, int var2, Shape var3, SubstanceColorScheme var4, SubstanceColorScheme var5, float var6, boolean var7, boolean var8) {
      SubstanceColorScheme var10 = var8?var5:var4;
      double var11 = 1.0D - (double)var6;
      Color var13 = var0.getTopFillColor(var4, var10, var11, var8);
      Color var14 = var0.getMidFillColorTop(var4, var10, var11, var8);
      Color var15 = var0.getMidFillColorBottom(var4, var10, var11, var8);
      Color var16 = var0.getBottomFillColor(var4, var10, var11, var8);
      LinearGradientPaint var17 = new LinearGradientPaint(0.0F, 0.0F, 0.0F, (float)var2, new float[]{0.0F, 0.4999999F, 0.5F, 1.0F}, new Color[]{var13, var14, var15, var16}, CycleMethod.REPEAT);
      return var17;
   }

   public static MultipleGradientPaint getTabGradient(JTabbedPane var0, int var1, int var2, ComponentState var3) {
      StandardGradientPainter var4 = (StandardGradientPainter)SubstanceCoreUtilities.getGradientPainter(var0);
      SubstanceButtonShaper var5 = SubstanceCoreUtilities.getButtonShaper(var0);
      int var6 = (int)Math.ceil(2.0D * (double)SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var0)));
      int var7 = (int)Math.floor((double)SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var0)) / 2.0D);
      int var8 = 2 + var6;
      EnumSet var9 = EnumSet.of(Side.BOTTOM);
      int var10 = var1 / 3;
      if(var5 instanceof ClassicButtonShaper) {
         var10 = (int)SubstanceSizeUtils.getClassicButtonCornerRadius(SubstanceSizeUtils.getComponentFontSize(var0));
      }

      GeneralPath var11 = SubstanceOutlineUtilities.getBaseOutline(var2, var1 + var8, (float)var10, var9, var7);
      boolean var12 = var3 == ComponentState.ROLLOVER_UNSELECTED;
      boolean var13 = var3 != ComponentState.DISABLED_UNSELECTED && var3 != ComponentState.DISABLED_SELECTED;
      float var14 = var12 && var13?0.5F:0.0F;
      SubstanceColorScheme var15 = SubstanceColorSchemeUtilities.getColorScheme(var0, ColorSchemeAssociationKind.TAB, var3);
      return getGradient(var4, var2, var1 + var8, var11, var15, var15, var14, true, false);
   }

   public static MultipleGradientPaint getDecorationAreaGradient(StandardGradientPainter var0, int var1, int var2, SubstanceColorScheme var3) {
      return getGradient(var0, var1, var2, new Rectangle(var1 + 6, var2 + 6), var3, var3, 0.0F, false, false);
   }
}

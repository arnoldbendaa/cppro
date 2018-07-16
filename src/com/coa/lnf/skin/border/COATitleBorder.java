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
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Float;
import org.jvnet.lafwidget.utils.RenderingUtils;
import org.jvnet.substance.api.ColorSchemeAssociationKind;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.utils.SubstanceColorSchemeUtilities;
import org.jvnet.substance.utils.SubstanceSizeUtils;
import org.jvnet.substance.utils.border.SubstanceEtchedBorder;

public class COATitleBorder extends SubstanceEtchedBorder {

   public Color getShadowColor(Component var1) {
      SubstanceColorScheme var2 = SubstanceColorSchemeUtilities.getColorScheme(var1, ColorSchemeAssociationKind.BORDER, ComponentState.DEFAULT);
      return var2.isDark()?var2.getDarkColor():var2.getExtraLightColor();
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Graphics2D var9 = (Graphics2D)var2.create();
      float var10 = SubstanceSizeUtils.getBorderStrokeWidth(SubstanceSizeUtils.getComponentFontSize(var1));
      var9.setStroke(new BasicStroke(var10, 0, 1));
      var9.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var9.translate(var3, var4);
      var9.setColor(this.getShadowColor(var1));
      int var11 = (int)Math.floor((double)var10 / 2.0D);
      var9.draw(new Float((float)var11, (float)var11, (float)(var5 - var11) - 2.0F * var10, (float)(var6 - var11) - 2.0F * var10));
      var9.dispose();
      RenderingUtils.installDesktopHints((Graphics2D)var2, var1);
   }
}

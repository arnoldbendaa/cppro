// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.skin;

import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceColorSchemeBundle;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.colorscheme.LightGrayColorScheme;
import org.jvnet.substance.colorscheme.MetallicColorScheme;
import org.jvnet.substance.colorscheme.OrangeColorScheme;
import org.jvnet.substance.colorscheme.PurpleColorScheme;
import org.jvnet.substance.painter.border.ClassicBorderPainter;
import org.jvnet.substance.painter.decoration.ClassicDecorationPainter;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import org.jvnet.substance.painter.gradient.ClassicGradientPainter;
import org.jvnet.substance.painter.highlight.ClassicHighlightPainter;
import org.jvnet.substance.shaper.StandardButtonShaper;
import org.jvnet.substance.skin.SaharaSkin;
import org.jvnet.substance.utils.SubstanceColorSchemeUtilities;

public class COASkin extends SubstanceSkin {

   public static final String NAME = "COA";


   public COASkin() {
      OrangeColorScheme var1 = new OrangeColorScheme();
      MetallicColorScheme var2 = new MetallicColorScheme();
      LightGrayColorScheme var3 = new LightGrayColorScheme();
      SubstanceColorSchemeBundle var4 = new SubstanceColorSchemeBundle(var1, var2, var3);
      var4.registerHighlightColorScheme((new PurpleColorScheme()).tint(0.2D).named("COA Highlight"), new ComponentState[0]);
      this.registerDecorationAreaSchemeBundle(var4, new DecorationAreaType[]{DecorationAreaType.NONE});
      this.registerAsDecorationArea(var1, new DecorationAreaType[]{DecorationAreaType.PRIMARY_TITLE_PANE, DecorationAreaType.SECONDARY_TITLE_PANE});
      SubstanceColorScheme var5 = SubstanceColorSchemeUtilities.getColorScheme(SaharaSkin.class.getClassLoader().getResource("org/jvnet/substance/skin/lightgray-general-watermark.colorscheme"));
      this.registerAsDecorationArea(var5, new DecorationAreaType[]{DecorationAreaType.GENERAL, DecorationAreaType.HEADER});
      this.buttonShaper = new StandardButtonShaper();
      this.gradientPainter = new ClassicGradientPainter();
      this.borderPainter = new ClassicBorderPainter();
      this.decorationPainter = new ClassicDecorationPainter();
      this.highlightPainter = new ClassicHighlightPainter();
   }

   public String getDisplayName() {
      return "COA";
   }
}

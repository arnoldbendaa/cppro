// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.skin;

import java.net.URL;

import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.jvnet.lafwidget.utils.LafConstants.AnimationKind;
import org.jvnet.substance.api.ColorSchemeAssociationKind;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceColorSchemeBundle;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.painter.decoration.ClassicDecorationPainter;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import org.jvnet.substance.painter.gradient.ClassicGradientPainter;
import org.jvnet.substance.painter.highlight.ClassicHighlightPainter;
import org.jvnet.substance.shaper.StandardButtonShaper;
import org.jvnet.substance.utils.SubstanceColorSchemeUtilities;

import com.coa.lnf.skin.border.COABorderPainter;
import com.coa.lnf.skin.border.COATitleBorder;

public class CustomizableCOASkin extends SubstanceSkin {

   private static final String NAME = "Customizable COA";
   private static final String DEFAULT_PATH = "com/coa/lnf/skin/coa-default.colorschemes";
   private static final String RESOURCE_NAME = "coalnf.colorschemes";
   private ColorSchemes colorSchemes;


   public CustomizableCOASkin() {
      this((String)null);
   }

   public CustomizableCOASkin(String var1) {
      URL var2 = null;
      if(null != var1) {
         var2 = CustomizableCOASkin.class.getClassLoader().getResource(var1);
      } else {
         var2 = CustomizableCOASkin.class.getClassLoader().getResource("coalnf.colorschemes");
      }

      this.load(var2);
   }

   public CustomizableCOASkin(URL var1) {
      this.load(var1);
   }

   private void load(URL var1) {
      this.colorSchemes = SubstanceColorSchemeUtilities.getColorSchemes(CustomizableCOASkin.class.getClassLoader().getResource("com/coa/lnf/skin/coa-default.colorschemes"));
      if(null != var1) {
         ColorSchemes var2 = SubstanceColorSchemeUtilities.getColorSchemes(var1);

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            this.colorSchemes.add(var2.get(var3));
         }
      }

      this.setup();
      UIManager.put("substancelaf.colorizationFactor", new Double(1.0D));
      UIManager.put("lafwidgets.animationKind", AnimationKind.NONE);
   }

   private void setup() {
      SubstanceColorScheme var1 = this.colorSchemes.get("COA Active");
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Default");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Disable");
      SubstanceColorSchemeBundle var4 = new SubstanceColorSchemeBundle(var1, var2, var3);
      this.registerDisabled(var4);
      this.registerRolloverAndPressed(var4);
      this.registerHighlightColorScheme(var4);
      this.registerColorSchemeAssociationKinds(var4);
      this.registerTabAndTabBorder(var4);
      SubstanceColorScheme var5 = this.colorSchemes.get("COA Separator");
      var4.registerColorScheme(var5, ColorSchemeAssociationKind.SEPARATOR, new ComponentState[0]);
      SubstanceColorScheme var6 = this.colorSchemes.get("COA Taskpane Title Background");
      if(null != var6) {
         var4.registerColorScheme(var6, ColorSchemeAssociationKind.HIGHLIGHT, new ComponentState[0]);
      }

      this.registerDecorationArea(var4);
      this.registerDecorationAreaSchemeBundle(var4, new DecorationAreaType[]{DecorationAreaType.NONE});
      this.watermarkScheme = this.colorSchemes.get("COA Watermark");
      this.buttonShaper = new StandardButtonShaper();
      this.gradientPainter = new ClassicGradientPainter();
      this.borderPainter = new COABorderPainter();
      this.decorationPainter = new ClassicDecorationPainter();
      this.highlightPainter = new ClassicHighlightPainter();
   }

   private void registerDecorationArea(SubstanceColorSchemeBundle var1) {
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Primary Title Pane");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Secondary Title Pane");
      if(null != var2) {
         this.registerAsDecorationArea(var2, new DecorationAreaType[]{DecorationAreaType.PRIMARY_TITLE_PANE});
      } else {
         this.registerAsDecorationArea(this.colorSchemes.get("COA Active"), new DecorationAreaType[]{DecorationAreaType.PRIMARY_TITLE_PANE});
      }

      if(null != var3) {
         this.registerAsDecorationArea(var3, new DecorationAreaType[]{DecorationAreaType.SECONDARY_TITLE_PANE});
      } else {
         this.registerAsDecorationArea(this.colorSchemes.get("COA Active"), new DecorationAreaType[]{DecorationAreaType.SECONDARY_TITLE_PANE});
      }

      SubstanceColorScheme var4 = this.colorSchemes.get("COA Header");
      this.registerAsDecorationArea(var4, new DecorationAreaType[]{DecorationAreaType.HEADER});
      SubstanceColorScheme var5 = this.colorSchemes.get("COA General");
      if(null != var5) {
         this.registerAsDecorationArea(var5, new DecorationAreaType[]{DecorationAreaType.GENERAL});
      } else {
         this.registerAsDecorationArea(var4, new DecorationAreaType[]{DecorationAreaType.GENERAL});
      }

      SubstanceColorScheme var6 = this.colorSchemes.get("COA Footer");
      if(null != var6) {
         this.registerAsDecorationArea(var6, new DecorationAreaType[]{DecorationAreaType.FOOTER});
      } else {
         this.registerAsDecorationArea(var4, new DecorationAreaType[]{DecorationAreaType.FOOTER});
      }

      SubstanceColorScheme var7 = this.colorSchemes.get("COA Toolbar");
      if(null != var7) {
         this.registerAsDecorationArea(var7, new DecorationAreaType[]{DecorationAreaType.TOOLBAR});
      } else {
         this.registerAsDecorationArea(var4, new DecorationAreaType[]{DecorationAreaType.TOOLBAR});
      }

   }

   private void registerDisabled(SubstanceColorSchemeBundle var1) {
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Disabled Selected");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Disabled Unselected");
      if(null != var2) {
         var1.registerColorScheme(var2, new ComponentState[]{ComponentState.DISABLED_SELECTED});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Disable"), new ComponentState[]{ComponentState.DISABLED_SELECTED});
      }

      if(null != var3) {
         var1.registerColorScheme(var3, new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Disable"), new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      }

   }

   private void registerRolloverAndPressed(SubstanceColorSchemeBundle var1) {
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Rollover");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Rollover Selected");
      SubstanceColorScheme var4 = this.colorSchemes.get("COA Selected");
      SubstanceColorScheme var5 = this.colorSchemes.get("COA Pressed");
      SubstanceColorScheme var6 = this.colorSchemes.get("COA Pressed Selected");
      var1.registerColorScheme(var2, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      var1.registerColorScheme(var3, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      var1.registerColorScheme(var4, new ComponentState[]{ComponentState.SELECTED});
      var1.registerColorScheme(var5, new ComponentState[]{ComponentState.PRESSED_UNSELECTED});
      var1.registerColorScheme(var6, new ComponentState[]{ComponentState.PRESSED_SELECTED});
   }

   private void registerHighlightColorScheme(SubstanceColorSchemeBundle var1) {
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Highlight Rollover");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Highlight Selected");
      SubstanceColorScheme var4 = this.colorSchemes.get("COA Highlight Rollover Selected");
      SubstanceColorScheme var5 = this.colorSchemes.get("COA Highlight Armed");
      SubstanceColorScheme var6 = this.colorSchemes.get("COA Highlight Rollover Armed");
      SubstanceColorScheme var7 = this.colorSchemes.get("COA Highlight Default");
      SubstanceColorScheme var8 = this.colorSchemes.get("COA Highlight Active");
      if(null != var2) {
         var1.registerHighlightColorScheme(var2, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      } else {
         var1.registerHighlightColorScheme(this.colorSchemes.get("COA Rollover"), 0.8F, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      }

      if(null != var3) {
         var1.registerHighlightColorScheme(var3, 1.0F, new ComponentState[]{ComponentState.SELECTED});
      } else {
         var1.registerHighlightColorScheme(this.colorSchemes.get("COA Selected"), 0.8F, new ComponentState[]{ComponentState.SELECTED});
      }

      if(null != var4) {
         var1.registerHighlightColorScheme(var4, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      } else {
         var1.registerHighlightColorScheme(this.colorSchemes.get("COA Rollover Selected"), 0.8F, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      }

      if(null != var5) {
         var1.registerHighlightColorScheme(var5, new ComponentState[]{ComponentState.ARMED});
      } else {
         var1.registerHighlightColorScheme(this.colorSchemes.get("COA Selected"), 0.8F, new ComponentState[]{ComponentState.ARMED});
      }

      if(null != var5) {
         var1.registerHighlightColorScheme(var6, new ComponentState[]{ComponentState.ROLLOVER_ARMED});
      } else {
         var1.registerHighlightColorScheme(this.colorSchemes.get("COA Selected"), 0.8F, new ComponentState[]{ComponentState.ROLLOVER_ARMED});
      }

      if(null != var7) {
         var1.registerHighlightColorScheme(var7, new ComponentState[]{ComponentState.DEFAULT});
      }

      if(null != var8) {
         var1.registerHighlightColorScheme(var8, new ComponentState[]{ComponentState.ACTIVE});
      }

   }

   private void registerColorSchemeAssociationKinds(SubstanceColorSchemeBundle var1) {
      this.unsetPaintingDropShadows(DecorationAreaType.PRIMARY_TITLE_PANE);
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Border Default");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Border Active");
      SubstanceColorScheme var4 = this.colorSchemes.get("COA Border Rollover");
      SubstanceColorScheme var5 = this.colorSchemes.get("COA Border Rollover Selected");
      SubstanceColorScheme var6 = this.colorSchemes.get("COA Border Selected");
      SubstanceColorScheme var7 = this.colorSchemes.get("COA Border Pressed");
      SubstanceColorScheme var8 = this.colorSchemes.get("COA Border Disabled Selected");
      SubstanceColorScheme var9 = this.colorSchemes.get("COA Border Disabled Unselected");
      SubstanceColorScheme var10 = this.colorSchemes.get("COA Border Armed");
      SubstanceColorScheme var11 = this.colorSchemes.get("COA Border Rollover Armed");
      SubstanceColorScheme var12 = this.colorSchemes.get("COA Border Pressed Unselected");
      SubstanceColorScheme var13 = this.colorSchemes.get("COA Border Disabled Active");
      var1.registerColorScheme(var2, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DEFAULT});
      if(null != var8) {
         var1.registerColorScheme(var8, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_SELECTED});
      } else {
         var1.registerColorScheme(var2, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_SELECTED});
      }

      if(null != var8) {
         var1.registerColorScheme(var9, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      } else {
         var1.registerColorScheme(var2, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      }

      if(null != var13) {
         var1.registerColorScheme(var13, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_ACTIVE});
      } else {
         var1.registerColorScheme(var9, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.DISABLED_ACTIVE});
      }

      var1.registerColorScheme(var3, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ACTIVE});
      var1.registerColorScheme(var4, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      var1.registerColorScheme(var5, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      if(null != var10) {
         var1.registerColorScheme(var10, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ARMED});
      } else {
         var1.registerColorScheme(var5, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ARMED});
      }

      if(null != var11) {
         var1.registerColorScheme(var11, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ROLLOVER_ARMED});
      } else {
         var1.registerColorScheme(var5, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.ROLLOVER_ARMED});
      }

      var1.registerColorScheme(var6, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.SELECTED});
      var1.registerColorScheme(var7, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.PRESSED_SELECTED});
      if(null != var12) {
         var1.registerColorScheme(var12, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.PRESSED_UNSELECTED});
      } else {
         var1.registerColorScheme(var7, ColorSchemeAssociationKind.BORDER, new ComponentState[]{ComponentState.PRESSED_UNSELECTED});
      }

      SubstanceColorScheme var14 = this.colorSchemes.get("COA Border Mark Default");
      SubstanceColorScheme var15 = this.colorSchemes.get("COA Border Mark Disabled Selected");
      SubstanceColorScheme var16 = this.colorSchemes.get("COA Border Mark Disabled Unselected");
      if(null != var14) {
         var1.registerColorScheme(var14, ColorSchemeAssociationKind.MARK, new ComponentState[]{ComponentState.DEFAULT});
      }

      if(null != var15) {
         var1.registerColorScheme(var15, ColorSchemeAssociationKind.MARK, new ComponentState[]{ComponentState.DISABLED_SELECTED});
      } else if(null != var14) {
         var1.registerColorScheme(var14, ColorSchemeAssociationKind.MARK, new ComponentState[]{ComponentState.DISABLED_SELECTED});
      }

      if(null != var16) {
         var1.registerColorScheme(var16, ColorSchemeAssociationKind.MARK, new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      } else if(null != var14) {
         var1.registerColorScheme(var14, ColorSchemeAssociationKind.MARK, new ComponentState[]{ComponentState.DISABLED_UNSELECTED});
      }

   }

   private void registerTabAndTabBorder(SubstanceColorSchemeBundle var1) {
      SubstanceColorScheme var2 = this.colorSchemes.get("COA Tab Selected");
      SubstanceColorScheme var3 = this.colorSchemes.get("COA Tab Default");
      SubstanceColorScheme var4 = this.colorSchemes.get("COA Tab Rollover");
      SubstanceColorScheme var5 = this.colorSchemes.get("COA Tab Rollover Selected");
      SubstanceColorScheme var6 = this.colorSchemes.get("COA Tab Pressed Selected");
      SubstanceColorScheme var7 = this.colorSchemes.get("COA Tab Pressed Unselected");
      var1.registerColorScheme(var2, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.SELECTED});
      var1.registerColorScheme(var3, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.DEFAULT});
      if(null != var5) {
         var1.registerColorScheme(var5, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      } else {
         var1.registerColorScheme(var2, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      }

      if(null != var6) {
         var1.registerColorScheme(var6, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.PRESSED_SELECTED});
      } else {
         var1.registerColorScheme(var2, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.PRESSED_SELECTED});
      }

      if(null != var7) {
         var1.registerColorScheme(var7, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.PRESSED_UNSELECTED});
      } else {
         var1.registerColorScheme(var2, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.PRESSED_UNSELECTED});
      }

      var1.registerColorScheme(var4, ColorSchemeAssociationKind.TAB, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      SubstanceColorScheme var8 = this.colorSchemes.get("COA Tabborder Selected");
      SubstanceColorScheme var9 = this.colorSchemes.get("COA Tabborder Default");
      SubstanceColorScheme var10 = this.colorSchemes.get("COA Tabborder Rollover Unselected");
      SubstanceColorScheme var11 = this.colorSchemes.get("COA Tabborder Rollover Selected");
      if(null != var8) {
         var1.registerColorScheme(var8, ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.SELECTED});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Border Default"), ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.SELECTED});
      }

      if(null != var9) {
         var1.registerColorScheme(var9, ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.DEFAULT});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Border Default"), ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.SELECTED});
      }

      if(null != var10) {
         var1.registerColorScheme(var10, ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Border Default"), ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.ROLLOVER_UNSELECTED});
      }

      if(null != var11) {
         var1.registerColorScheme(var11, ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      } else {
         var1.registerColorScheme(this.colorSchemes.get("COA Rollover Selected"), ColorSchemeAssociationKind.TAB_BORDER, new ComponentState[]{ComponentState.ROLLOVER_SELECTED});
      }

      this.setSelectedTabFadeStart(1.0D);
      this.setSelectedTabFadeEnd(1.0D);
   }

   public String getDisplayName() {
      return "Customizable COA";
   }

   public void addCustomEntriesToTable(UIDefaults var1) {
      super.addCustomEntriesToTable(var1);
      Object[] var2 = new Object[]{"TitledBorder.border", new COATitleBorder(), "Tree.leafIcon", LookAndFeel.makeIcon(CustomizableCOASkin.class, "/com/coa/lnf/icons/tree_leaf.gif"), "Tree.openIcon", LookAndFeel.makeIcon(CustomizableCOASkin.class, "/com/coa/lnf/icons/tree_open.gif"), "Tree.closedIcon", LookAndFeel.makeIcon(CustomizableCOASkin.class, "/com/coa/lnf/icons/tree_closed.gif")};
      var1.putDefaults(var2);
   }

   public ColorSchemes getColorSchemes() {
      return this.colorSchemes;
   }
}

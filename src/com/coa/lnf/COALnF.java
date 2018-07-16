// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MultipleGradientPaint;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicBorders.FieldBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.api.SubstanceSkin.ColorSchemes;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import org.jvnet.substance.painter.gradient.StandardGradientPainter;
import org.jvnet.substance.shaper.SubstanceButtonShaper;

import com.coa.lnf.fonts.COAFontPolicy;
import com.coa.lnf.skin.COALookAndFeel;
import com.coa.lnf.skin.CustomizableCOASkin;

public class COALnF {
	private static enum ColorTint {
		ULTRA_LIGHT, EXTRA_LIGHT, LIGHT, MID, DARK, ULTRA_DARK, FOREGROUND;
	}
   private static Map<String, Object> map;


   public static void initialise(String var0, String var1) {
      String var2 = System.getProperty("com.coa.lnf.substance");
      if("0".equals(var2)) {
         setPropertiesForMetalLnF(var0);
      } else {
         if(!SwingUtilities.isEventDispatchThread()) {
            try {
               SwingUtilities.invokeAndWait(new COALnF$1(var0, var1));
            } catch (InterruptedException var4) {
               var4.printStackTrace();
            } catch (InvocationTargetException var5) {
               var5.printStackTrace();
            }
         } else {
            setLookAndFeel(var0);
            initialiseFont(var1);
         }

      }
   }

   private static Map<String, Object> loadMetalProperties(URL var0) {
      Properties var1 = new Properties();
      HashMap var2 = null;
      URL var4 = COALnF.class.getClassLoader().getResource("com/coa/lnf/coalnf-metal-defaults.properties");

      try {
         var1.load(var4.openStream());
         if(null != var0) {
            Properties var5 = new Properties();
            var5.load(var0.openStream());
            var1.putAll(var5);
         }

         var2 = new HashMap();
         Set var14 = var1.entrySet();
         Iterator var6 = var14.iterator();

         while(var6.hasNext()) {
            Entry var7 = (Entry)var6.next();
            String var8 = (String)var7.getKey();
            String var9 = (String)var7.getValue();
            if(var8.startsWith("color.")) {
               var9 = var9.trim();
               if(!var9.matches("^#([A-Fa-f0-9]{6})$")) {
                  throw new IllegalArgumentException("Invalid color value at: " + var8);
               }

               var2.put(var8.trim(), new Color(Integer.decode(var9).intValue()));
            } else if(var8.startsWith("font")) {
               String[] var11 = var9.split(",");
               var11[0] = var11[0].trim();
               var11[1] = var11[1].trim();
               var11[2] = var11[2].trim();
               if(var11.length != 3) {
                  throw new IllegalArgumentException("Invalid definition at: " + var8);
               }

               if(!var11[0].matches("[\\w+ ]+")) {
                  throw new IllegalArgumentException("Invalid definition at the first value at: " + var8);
               }

               if(!var11[1].equalsIgnoreCase("plain") && !var11[1].equalsIgnoreCase("bold") && !var11[1].equalsIgnoreCase("italic") && !var11[1].equalsIgnoreCase("bold italic") && !var11[1].equalsIgnoreCase("italic bold")) {
                  throw new IllegalArgumentException("Invalid definition at the second value at: " + var8);
               }

               if(!var11[2].matches("\\d+")) {
                  throw new IllegalArgumentException("Invalid definition at the third value at: " + var8);
               }

               byte var10;
               if(var11[1].equalsIgnoreCase("italic")) {
                  var10 = 2;
               } else if(var11[1].equalsIgnoreCase("bold")) {
                  var10 = 1;
               } else if(!var11[1].equalsIgnoreCase("bold italic") && !var11[1].equalsIgnoreCase("italic bold")) {
                  var10 = 0;
               } else {
                  var10 = 3;
               }

               FontUIResource var12 = new FontUIResource(var11[0], var10, Integer.parseInt(var11[2]));
               var2.put(var8, var12);
            }
         }
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      return var2;
   }

   private static void setPropertiesForMetalLnF(String var0) {
      URL var2 = null;
      if(null != var0) {
         var2 = COALnF.class.getClassLoader().getResource(var0);
      } else {
         var2 = COALnF.class.getClassLoader().getResource("coalnf-metal.properties");
      }

      map = loadMetalProperties(var2);
      Set var3 = map.entrySet();
      MetalLookAndFeel.setCurrentTheme(new COATheme(map));

      try {
         UIManager.setLookAndFeel(new MetalLookAndFeel());
      } catch (UnsupportedLookAndFeelException var8) {
         var8.printStackTrace();
      }

      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         String var4 = (String)var7.getKey();
         Object var5 = var7.getValue();
         if(var4.startsWith("color.")) {
            var4 = var4.replaceFirst("color.", "");
            if(var4.startsWith("button.")) {
               var4 = var4.replaceFirst("button.", "");
               if(!var4.equals("border")) {
                  UIManager.put("Button." + var4, var5);
                  UIManager.put("ToggleButton." + var4, var5);
                  if(!var4.equals("background")) {
                     UIManager.put("CheckBox." + var4, var5);
                     UIManager.put("RadioButton." + var4, var5);
                  }

                  if(var4.equals("disabledText")) {
                     UIManager.put("MenuItem.disabledForeground", var5);
                  }
               }
            } else if(var4.startsWith("field.")) {
               var4 = var4.replaceFirst("field.", "");
               if(var4.equals("border")) {
                  var5 = new FieldBorder((Color)var5, (Color)var5, (Color)var5, (Color)var5);
               }

               UIManager.put("TextField." + var4, var5);
               UIManager.put("TextArea." + var4, var5);
               UIManager.put("TextPane." + var4, var5);
               UIManager.put("TextPane." + var4, var5);
               UIManager.put("EditorPane." + var4, var5);
               UIManager.put("PasswordField." + var4, var5);
               UIManager.put("FormattedTextField." + var4, var5);
            } else if(var4.startsWith("tab.")) {
               var4 = var4.replaceFirst("tab.", "");
               UIManager.put("TabbedPane." + var4, var5);
            } else if(var4.startsWith("label.")) {
               var4 = var4.replaceFirst("l", "L");
               UIManager.put(var4, var5);
            } else if(var4.startsWith("toolBar.")) {
               var4 = var4.replaceFirst("t", "T");
               UIManager.put(var4, var5);
            } else if(var4.startsWith("menuBar.")) {
               var4 = var4.replaceFirst("menuBar.", "");
               UIManager.put("MenuBar." + var4, var5);
               if(var4.equals("background")) {
                  UIManager.put("Menu." + var4, var5);
               }
            } else if(var4.startsWith("separator.")) {
               var4 = var4.replaceFirst("s", "S");
               UIManager.put(var4, var5);
            } else if(var4.startsWith("list.")) {
               var4 = var4.replaceFirst("l", "L");
               UIManager.put(var4, var5);
            } else if(!var4.startsWith("tree.") && !var4.startsWith("table.")) {
               if(var4.startsWith("panel.")) {
                  var4 = var4.replaceFirst("panel.", "");
                  UIManager.put("Panel." + var4, var5);
                  if(var4.equals("background")) {
                     UIManager.put("CheckBox." + var4, var5);
                     UIManager.put("RadioButton." + var4, var5);
                     UIManager.put("TaskPaneContainer." + var4, var5);
                  }
               } else if(var4.startsWith("taskPane.")) {
                  var4 = var4.replaceFirst("taskPane.", "");
                  UIManager.put("TaskPane." + var4, var5);
                  if(var4.equals("borderColor")) {
                     UIManager.put("TaskPane.titleBackgroundGradientStart", var5);
                     UIManager.put("TaskPane.titleBackgroundGradientEnd", var5);
                  }
               } else if(var4.startsWith("highlight.")) {
                  var4 = var4.replaceFirst("highlight.", "");
                  UIManager.put("Tree." + var4, var5);
                  UIManager.put("List." + var4, var5);
                  UIManager.put("Table." + var4, var5);
                  UIManager.put("ComboBox." + var4, var5);
                  UIManager.put("MenuItem." + var4, var5);
                  UIManager.put("Menu." + var4, var5);
                  UIManager.put("CheckBoxMenuItem." + var4, var5);
                  UIManager.put("RadioButtonMenuItem." + var4, var5);
               }
            } else {
               var4 = var4.replaceFirst("t", "T");
               UIManager.put(var4, var5);
            }
         }
      }

      UIManager.put("Tree.leafIcon", LookAndFeel.makeIcon(COALnF.class, "/com/coa/lnf/icons/tree_leaf.gif"));
      UIManager.put("Tree.openIcon", LookAndFeel.makeIcon(COALnF.class, "/com/coa/lnf/icons/tree_open.gif"));
      UIManager.put("Tree.closedIcon", LookAndFeel.makeIcon(COALnF.class, "/com/coa/lnf/icons/tree_closed.gif"));
      UIManager.put("ToolBar.isRollover", Boolean.TRUE);
      UIManager.put("ToolBar.separatorSize", new DimensionUIResource(0, 0));
   }

   public static void initialise() {
      initialise((String)null);
   }

   public static void initialise(String var0) {
      String var1 = System.getProperty("com.coa.lnf.substance");
      if("0".equals(var1)) {
         setPropertiesForMetalLnF(var0);
      } else {
         if(!SwingUtilities.isEventDispatchThread()) {
            try {
               SwingUtilities.invokeAndWait(new COALnF$2(var0));
            } catch (InterruptedException var3) {
               var3.printStackTrace();
            } catch (InvocationTargetException var4) {
               var4.printStackTrace();
            }
         } else {
            setLookAndFeel(var0);
            initialiseFont((String)null);
         }

      }
   }

   public static void initialiseFont(String var0) {
      COAFontPolicy var1 = COAFontPolicy.createPolicy(var0);
      SubstanceLookAndFeel.setFontPolicy(var1);
   }

   public static int getFontSize() {
      String var0 = System.getProperty("com.coa.lnf.substance");
      if("0".equals(var0)) {
         FontUIResource var1 = (FontUIResource)map.get("font");
         return var1.getSize();
      } else {
         return SubstanceLookAndFeel.getFontPolicy().getFontSet("COA", (UIDefaults)null).getControlFont().getSize();
      }
   }

   public static String getFontFamilyName() {
      String var0 = System.getProperty("com.coa.lnf.substance");
      if("0".equals(var0)) {
         FontUIResource var1 = (FontUIResource)map.get("font");
         return var1 == null?"Verdana":var1.getFontName();
      } else {
         return SubstanceLookAndFeel.getFontPolicy().getFontSet("COA", (UIDefaults)null).getControlFont().getFontName();
      }
   }

   public static Color getColor(COALnFColors var0) {
      String var1 = System.getProperty("com.coa.lnf.substance");
      if("0".equals(var1)) {
         return getMetalColor(var0);
      } else {
         SubstanceSkin var2 = SubstanceLookAndFeel.getCurrentSkin();
         if(!(var2 instanceof CustomizableCOASkin)) {
            Color var4 = getDefaultColor(var0);
            return var4 != null?var4:Color.black;
         } else {
            CustomizableCOASkin var3 = (CustomizableCOASkin)var2;
            switch(COALnF$3.$SwitchMap$com$coa$lnf$COALnFColors[var0.ordinal()]) {
            case 1:
               return extractColorFromScheme(var3, "COA Disable", ColorTint.ULTRA_LIGHT, 16511205);
            case 2:
               return extractColorFromScheme(var3, "COA Default", ColorTint.LIGHT, 15987447);
            case 3:
               return extractColorFromScheme(var3, "COA Rollover", ColorTint.LIGHT, 16438661);
            case 4:
               return extractColorFromScheme(var3, "COA Pressed", ColorTint.ULTRA_LIGHT, 16642498);
            case 5:
               return extractColorFromScheme(var3, "COA General", ColorTint.ULTRA_LIGHT, 15265015);
            case 6:
               return extractColorFromScheme(var3, "COA Header", ColorTint.ULTRA_LIGHT, 15331063);
            case 7:
               return extractColorFromScheme(var3, "COA Tab Default", ColorTint.LIGHT, 16381947);
            case 8:
               return extractColorFromScheme(var3, "COA Tab Rollover", ColorTint.LIGHT, 14802893);
            case 9:
               return extractColorFromScheme(var3, "COA Tab Selected", ColorTint.LIGHT, 14802893);
            case 10:
               return extractColorFromScheme(var3, "COA Primary Title Pane", ColorTint.MID, 13290976);
            case 11:
               return extractColorFromScheme(var3, "COA Primary Title Pane", ColorTint.MID, 13290976);
            case 12:
               return extractColorFromScheme(var3, "COA Border Default", ColorTint.DARK, 16119025);
            case 13:
               return new Color(3355494);
            case 14:
               return extractColorFromScheme(var3, "COA Header", ColorTint.MID, 15331063);
            case 15:
               return new Color(3355494);
            case 16:
               return new Color(3355494);
            case 17:
               return extractColorFromScheme(var3, "COA Default", ColorTint.EXTRA_LIGHT, 16381947);
            case 18:
               return new Color(3355494);
            case 19:
               return new Color(3355494);
            case 20:
               return extractColorFromScheme(var3, "COA Border Default", ColorTint.LIGHT, 9999523);
            case 21:
               return extractColorFromScheme(var3, "COA Disabled Unselected", ColorTint.DARK, 13157838);
            case 22:
               return new Color(10066329);
            case 23:
               return new Color(3355494);
            case 24:
               return extractColorFromScheme(var3, "COA Border Pressed", ColorTint.MID, 16750088);
            case 25:
               return new Color(3355494);
            case 26:
               return extractColorFromScheme(var3, "COA Border Rollover", ColorTint.DARK, 16438661);
            case 27:
               return extractColorFromScheme(var3, "COA Default", ColorTint.ULTRA_DARK, 16184819);
            case 28:
               return new Color(3355494);
            case 29:
               return extractColorFromScheme(var3, "COA Border Default", ColorTint.ULTRA_LIGHT, 10121303);
            case 30:
               return extractColorFromScheme(var3, "COA Disable", ColorTint.ULTRA_DARK, 16053220);
            case 31:
               return extractColorFromScheme(var3, "COA Border Disabled Unselected", ColorTint.ULTRA_LIGHT, 13290976);
            case 32:
               return new Color(9802925);
            case 33:
               return new Color(16640927);
            case 34:
               return new Color(8083242);
            case 35:
               return extractColorFromScheme(var3, "COA Border Selected", ColorTint.MID, 10065060);
            case 36:
               return new Color(3355494);
            case 37:
               return new Color(3355494);
            case 38:
               return extractColorFromScheme(var3, "COA Tab Default", ColorTint.DARK, 13290976);
            case 39:
               return new Color(6684825);
            case 40:
               return extractColorFromScheme(var3, "COA Tabborder Selected", ColorTint.DARK, 10065060);
            case 41:
               return new Color(13209);
            case 42:
               return extractColorFromScheme(var3, "COA Tabborder Rollover Unselected", ColorTint.DARK, 16438661);
            case 43:
               return extractColorFromScheme(var3, "COA Separator", ColorTint.LIGHT, 8599707);
            case 44:
               return extractColorFromScheme(var3, "COA Default", ColorTint.DARK, 13947079);
            case 45:
               return extractColorFromScheme(var3, "COA Highlight Active", ColorTint.ULTRA_LIGHT, 16627545);
            case 46:
               return extractColorFromScheme(var3, "COA Highlight Default", ColorTint.ULTRA_LIGHT, 16777215);
            case 47:
               return extractColorFromScheme(var3, "COA Highlight Default", ColorTint.LIGHT, 16184819);
            case 48:
               return extractColorFromScheme(var3, "COA Highlight Selected", ColorTint.LIGHT, 16640927);
            default:
               return Color.black;
            }
         }
      }
   }

   private static Color getMetalColor(COALnFColors var0) {
      switch(COALnF$3.$SwitchMap$com$coa$lnf$COALnFColors[var0.ordinal()]) {
      case 1:
         return (Color)map.get("color.button.background");
      case 2:
         return (Color)map.get("color.button.background");
      case 3:
         return getDefaultColor(COALnFColors.BUTTON_ROLLOVER_BACKGROUND);
      case 4:
         return (Color)map.get("color.button.select");
      case 5:
         return (Color)map.get("color.taskPane.background");
      case 6:
         return (Color)map.get("color.menuBar.background");
      case 7:
         return (Color)map.get("color.tab.contentAreaColor");
      case 8:
         return getDefaultColor(COALnFColors.TAB_ROLLOVER_BACKGROUND);
      case 9:
         return (Color)map.get("color.tab.selected");
      case 10:
         return (Color)map.get("color.titleBar.background");
      case 11:
         return (Color)map.get("color.container.border");
      case 12:
         return (Color)map.get("color.button.border");
      case 13:
         return new Color(3355494);
      case 14:
         return (Color)map.get("color.toolBar.background");
      case 15:
         return new Color(3355494);
      case 16:
         return new Color(3355494);
      case 17:
         return (Color)map.get("color.panel.background");
      case 18:
         return new Color(3355494);
      case 19:
         return (Color)map.get("color.button.foreground");
      case 20:
         return (Color)map.get("color.button.border");
      case 21:
         return (Color)map.get("color.button.disabledBorder");
      case 22:
         return (Color)map.get("color.button.disabledText");
      case 23:
         return new Color(3355494);
      case 24:
         return (Color)map.get("color.button.disabledText");
      case 25:
         return new Color(3355494);
      case 26:
         return getDefaultColor(COALnFColors.BUTTON_ROLLOVER_BORDER);
      case 27:
         return (Color)map.get("color.field.background");
      case 28:
         return (Color)map.get("color.field.foreground");
      case 29:
         return (Color)map.get("color.field.border");
      case 30:
         return (Color)map.get("color.field.border");
      case 31:
         return (Color)map.get("color.button.border");
      case 32:
         return (Color)map.get("color.field.inactiveForeground");
      case 33:
         return (Color)map.get("color.field.electionBackground");
      case 34:
         return (Color)map.get("color.field.selectionForeground");
      case 35:
         return (Color)map.get("color.field.border");
      case 36:
         return (Color)map.get("color.label.foreground");
      case 37:
         return (Color)map.get("color.tab.foreground");
      case 38:
         return (Color)map.get("color.button.border");
      case 39:
         return new Color(6684825);
      case 40:
         return (Color)map.get("color.tab.selectHighlight");
      case 41:
         return new Color(13209);
      case 42:
         return new Color(10065060);
      case 43:
         return (Color)map.get("color.separator.background");
      case 44:
         return (Color)map.get("color.taskPane.border");
      case 45:
         return getDefaultColor(COALnFColors.COLUMN_HEADER);
      case 46:
         return new Color(16184819);
      case 47:
         return new Color(16777215);
      case 48:
         return new Color(16640927);
      default:
         return Color.black;
      }
   }

   private static Color getDefaultColor(COALnFColors var0) {
      switch(COALnF$3.$SwitchMap$com$coa$lnf$COALnFColors[var0.ordinal()]) {
      case 1:
         return UIManager.getColor("Button.disabledbackground");
      case 2:
         return UIManager.getColor("Button.enabledbackground");
      case 3:
         return UIManager.getColor("Button.enabledbackground");
      case 4:
         return UIManager.getColor("Button.selectedbackground");
      case 5:
         return UIManager.getColor("Separator.background");
      case 6:
         return UIManager.getColor("MenuBar.background");
      case 7:
         return UIManager.getColor("TabbedPane.background");
      case 8:
         return UIManager.getColor("TabbedPane.background");
      case 9:
         return UIManager.getColor("TabbedPane.background");
      case 10:
         return UIManager.getColor("TableHeader.background");
      case 11:
      case 12:
      default:
         return Color.black;
      case 13:
         return UIManager.getColor("TableHeader.foreground");
      case 14:
         return UIManager.getColor("TableHeader.background");
      case 15:
         return UIManager.getColor("TableHeader.foreground");
      case 16:
         return UIManager.getColor("MenuBar.foreground");
      case 17:
         return UIManager.getColor("Panel.background");
      case 18:
         return UIManager.getColor("Panel.foreground");
      case 19:
         return UIManager.getColor("Button.enabledbackground");
      case 20:
         return UIManager.getColor("Button.enabledborder");
      case 21:
         return UIManager.getColor("Button.disabledborder");
      case 22:
         return UIManager.getColor("Button.disabledbackground");
      case 23:
         return UIManager.getColor("Button.selectedforeground");
      case 24:
         return UIManager.getColor("Button.selectedborder");
      case 25:
         return UIManager.getColor("Button.enabledforeground");
      case 26:
         return UIManager.getColor("Button.enabledborder");
      case 27:
         return UIManager.getColor("TextField.background");
      case 28:
         return UIManager.getColor("TextField.foreground");
      case 29:
         return UIManager.getColor("TextField.border");
      case 30:
         return UIManager.getColor("TextField.disabledbackground");
      case 31:
         return UIManager.getColor("TextField.diabledborder");
      case 32:
         return UIManager.getColor("TextField.disabledforeground");
      case 33:
         return UIManager.getColor("TextField.focusbackground");
      case 34:
         return UIManager.getColor("TextField.focusforeground");
      case 35:
         return UIManager.getColor("TextField.focusborder");
      case 36:
         return UIManager.getColor("Label.foreground");
      case 37:
         return UIManager.getColor("TabbedPane.foreground");
      case 38:
         return UIManager.getColor("TabbedPane.border");
      case 39:
         return UIManager.getColor("TabbedPane.foreground");
      case 40:
         return UIManager.getColor("TabbedPane.border");
      case 41:
         return UIManager.getColor("TabbedPane.foreground");
      case 42:
         return UIManager.getColor("TabbedPane.border");
      case 43:
         return UIManager.getColor("Separator.background");
      case 44:
         return UIManager.getColor("Separator.foreground");
      case 45:
         return UIManager.getColor("TableHeader.background");
      case 46:
         return UIManager.getColor("Table.background");
      case 47:
         return UIManager.getColor("Table.foreground");
      case 48:
         return UIManager.getColor("Table.selectionBackground");
      }
   }

   private static Color extractColorFromScheme(CustomizableCOASkin var0, String var1, ColorTint var2, int var3) {
      ColorSchemes var4 = var0.getColorSchemes();
      SubstanceColorScheme var5 = var4.get(var1);
      if(null != var5) {
         switch(COALnF$3.$SwitchMap$com$coa$lnf$ColorTint[var2.ordinal()]) {
         case 1:
            return var5.getUltraLightColor();
         case 2:
            return var5.getExtraLightColor();
         case 3:
            return var5.getLightColor();
         case 4:
            return var5.getMidColor();
         case 5:
            return var5.getDarkColor();
         case 6:
            return var5.getUltraDarkColor();
         }
      }

      return new Color(var3);
   }

   public static void changeLookAndFeel(JFrame var0, String var1) throws ClassNotFoundException {
      boolean var2 = !var0.isUndecorated();

      try {
         UIManager.setLookAndFeel(var1);
         Window[] var3 = Window.getWindows();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Window var6 = var3[var5];
            SwingUtilities.updateComponentTreeUI(var6);
         }

         boolean var8 = UIManager.getLookAndFeel().getSupportsWindowDecorations();
         if(var8 == var2) {
            boolean var9 = var0.isVisible();
            var0.setVisible(false);
            var0.dispose();
            if(var8 && !var2) {
               var0.setUndecorated(true);
               var0.getRootPane().setWindowDecorationStyle(1);
            } else {
               var0.setUndecorated(false);
               var0.getRootPane().setWindowDecorationStyle(0);
            }

            var0.setVisible(var9);
         }
      } catch (Exception var7) {
         System.out.println("Unable to change the Look and Feel");
         var7.printStackTrace();
      }

   }

   public static MultipleGradientPaint getGradient(COALnFColors var0) {
      return getGradient(var0, 0, 0);
   }

   public static MultipleGradientPaint getGradient(COALnFColors var0, int var1, int var2) {
      SubstanceSkin var3 = SubstanceLookAndFeel.getCurrentSkin();
      StandardGradientPainter var4 = (StandardGradientPainter)var3.getGradientPainter();
      SubstanceButtonShaper var5 = var3.getButtonShaper();
      JButton var6;
      SubstanceColorScheme var7;
      JTabbedPane var8;
      switch(COALnF$3.$SwitchMap$com$coa$lnf$COALnFColors[var0.ordinal()]) {
      case 1:
         var6 = new JButton();
         return COALnFGradientUtils.getButtonGradient(var6, var5, var4, var1 == 0?73:var1, var2 == 0?22:var2, ComponentState.DISABLED_UNSELECTED);
      case 2:
         var6 = new JButton();
         return COALnFGradientUtils.getButtonGradient(var6, var5, var4, var1 == 0?73:var1, var2 == 0?22:var2, ComponentState.DEFAULT);
      case 3:
         var6 = new JButton();
         return COALnFGradientUtils.getButtonGradient(var6, var5, var4, var1 == 0?73:var1, var2 == 0?22:var2, ComponentState.ROLLOVER_UNSELECTED);
      case 4:
         var6 = new JButton();
         return COALnFGradientUtils.getButtonGradient(var6, var5, var4, var1 == 0?73:var1, var2 == 0?22:var2, ComponentState.PRESSED_SELECTED);
      case 5:
         var7 = var3.getBackgroundColorScheme(DecorationAreaType.GENERAL);
         return COALnFGradientUtils.getDecorationAreaGradient(var4, var1 == 0?200:var1, var2 == 0?250:var2, var7);
      case 6:
         var7 = var3.getBackgroundColorScheme(DecorationAreaType.HEADER);
         return COALnFGradientUtils.getDecorationAreaGradient(var4, var1 == 0?36:var1, var2 == 0?108:var2, var7);
      case 7:
         var8 = new JTabbedPane();
         return COALnFGradientUtils.getTabGradient(var8, var1 == 0?17:var1, var2 == 0?45:var2, ComponentState.DEFAULT);
      case 8:
         var8 = new JTabbedPane();
         return COALnFGradientUtils.getTabGradient(var8, var1 == 0?17:var1, var2 == 0?45:var2, ComponentState.ROLLOVER_UNSELECTED);
      case 9:
         var8 = new JTabbedPane();
         return COALnFGradientUtils.getTabGradient(var8, var1 == 0?17:var1, var2 == 0?45:var2, ComponentState.SELECTED);
      case 10:
         var7 = var3.getBackgroundColorScheme(DecorationAreaType.PRIMARY_TITLE_PANE);
         Dimension var9 = Toolkit.getDefaultToolkit().getScreenSize();
         return COALnFGradientUtils.getDecorationAreaGradient(var4, var1 == 0?(int)var9.getWidth():var1, var2 == 0?27:var2, var7);
      case 11:
      case 12:
      case 13:
      default:
         return null;
      case 14:
         var7 = var3.getBackgroundColorScheme(DecorationAreaType.TOOLBAR);
         return COALnFGradientUtils.getDecorationAreaGradient(var4, var1 == 0?36:var1, var2 == 0?108:var2, var7);
      }
   }

   private static void setLookAndFeel(String var0) {
      try {
         UIManager.setLookAndFeel(new COALookAndFeel(var0));
      } catch (Exception var2) {
         System.out.println("Unable to initialise COA Look and Feel");
         var2.printStackTrace();
      }

   }

   // $FF: synthetic method
   static void accessMethod000(String var0) {
      setLookAndFeel(var0);
   }
   

	// $FF: synthetic class
	static class COALnF$3 {

		// $FF: synthetic field
		static final int[] $SwitchMap$com$coa$lnf$COALnFColors;
		// $FF: synthetic field
		static final int[] $SwitchMap$com$coa$lnf$ColorTint = new int[ColorTint
				.values().length];

		static {
			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.ULTRA_LIGHT
						.ordinal()] = 1;
			} catch (NoSuchFieldError var54) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.EXTRA_LIGHT
						.ordinal()] = 2;
			} catch (NoSuchFieldError var53) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.LIGHT.ordinal()] = 3;
			} catch (NoSuchFieldError var52) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.MID.ordinal()] = 4;
			} catch (NoSuchFieldError var51) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.DARK.ordinal()] = 5;
			} catch (NoSuchFieldError var50) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$ColorTint[ColorTint.ULTRA_DARK.ordinal()] = 6;
			} catch (NoSuchFieldError var49) {
				;
			}

			$SwitchMap$com$coa$lnf$COALnFColors = new int[COALnFColors.values().length];

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_DISABLED_BACKGROUND
						.ordinal()] = 1;
			} catch (NoSuchFieldError var48) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ENABLED_BACKGROUND
						.ordinal()] = 2;
			} catch (NoSuchFieldError var47) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ROLLOVER_BACKGROUND
						.ordinal()] = 3;
			} catch (NoSuchFieldError var46) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_SELECTED_BACKGROUND
						.ordinal()] = 4;
			} catch (NoSuchFieldError var45) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.DIFFERENTIATED_BACKGROUND
						.ordinal()] = 5;
			} catch (NoSuchFieldError var44) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.MENU_BAR_BACKGROUND
						.ordinal()] = 6;
			} catch (NoSuchFieldError var43) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_BACKGROUND
						.ordinal()] = 7;
			} catch (NoSuchFieldError var42) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_ROLLOVER_BACKGROUND
						.ordinal()] = 8;
			} catch (NoSuchFieldError var41) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_SELECTED_BACKGROUND
						.ordinal()] = 9;
			} catch (NoSuchFieldError var40) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TITLE_BAR_BACKGROUND
						.ordinal()] = 10;
			} catch (NoSuchFieldError var39) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.CONTAINER_OUTER_BORDER
						.ordinal()] = 11;
			} catch (NoSuchFieldError var38) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.CONTAINER_INNER_BORDER
						.ordinal()] = 12;
			} catch (NoSuchFieldError var37) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TITLE_BAR_TEXT
						.ordinal()] = 13;
			} catch (NoSuchFieldError var36) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.HEADER_BACKGROUND
						.ordinal()] = 14;
			} catch (NoSuchFieldError var35) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.HEADER_TEXT
						.ordinal()] = 15;
			} catch (NoSuchFieldError var34) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.MENU_BAR_TEXT
						.ordinal()] = 16;
			} catch (NoSuchFieldError var33) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.MAIN_BACKGROUND
						.ordinal()] = 17;
			} catch (NoSuchFieldError var32) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.MAIN_TEXT
						.ordinal()] = 18;
			} catch (NoSuchFieldError var31) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ENABLED_TEXT
						.ordinal()] = 19;
			} catch (NoSuchFieldError var30) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ENABLED_BORDER
						.ordinal()] = 20;
			} catch (NoSuchFieldError var29) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_DISABLED_BORDER
						.ordinal()] = 21;
			} catch (NoSuchFieldError var28) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_DISABLED_TEXT
						.ordinal()] = 22;
			} catch (NoSuchFieldError var27) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_SELECTED_TEXT
						.ordinal()] = 23;
			} catch (NoSuchFieldError var26) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_SELECTED_BORDER
						.ordinal()] = 24;
			} catch (NoSuchFieldError var25) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ROLLOVER_TEXT
						.ordinal()] = 25;
			} catch (NoSuchFieldError var24) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.BUTTON_ROLLOVER_BORDER
						.ordinal()] = 26;
			} catch (NoSuchFieldError var23) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_ENABLED_BACKGROUND
						.ordinal()] = 27;
			} catch (NoSuchFieldError var22) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_ENABLED_TEXT
						.ordinal()] = 28;
			} catch (NoSuchFieldError var21) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_ENABLED_BORDER
						.ordinal()] = 29;
			} catch (NoSuchFieldError var20) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_DISABLED_BACKGROUND
						.ordinal()] = 30;
			} catch (NoSuchFieldError var19) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_DISABLED_BORDER
						.ordinal()] = 31;
			} catch (NoSuchFieldError var18) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_DISABLED_TEXT
						.ordinal()] = 32;
			} catch (NoSuchFieldError var17) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_SELECTED_BACKGROUND
						.ordinal()] = 33;
			} catch (NoSuchFieldError var16) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_SELECTED_TEXT
						.ordinal()] = 34;
			} catch (NoSuchFieldError var15) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.FIELD_SELECTED_BORDER
						.ordinal()] = 35;
			} catch (NoSuchFieldError var14) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.LABEL_TEXT
						.ordinal()] = 36;
			} catch (NoSuchFieldError var13) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_TEXT
						.ordinal()] = 37;
			} catch (NoSuchFieldError var12) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_BORDER
						.ordinal()] = 38;
			} catch (NoSuchFieldError var11) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_SELECTED_TEXT
						.ordinal()] = 39;
			} catch (NoSuchFieldError var10) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_SELECTED_BORDER
						.ordinal()] = 40;
			} catch (NoSuchFieldError var9) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_ROLLOVER_TEXT
						.ordinal()] = 41;
			} catch (NoSuchFieldError var8) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.TAB_ROLLOVER_BORDER
						.ordinal()] = 42;
			} catch (NoSuchFieldError var7) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.SEPARATOR
						.ordinal()] = 43;
			} catch (NoSuchFieldError var6) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.DIFFERENTIATED_BORDER
						.ordinal()] = 44;
			} catch (NoSuchFieldError var5) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.COLUMN_HEADER
						.ordinal()] = 45;
			} catch (NoSuchFieldError var4) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.ROW_HIGHLIGHT_1
						.ordinal()] = 46;
			} catch (NoSuchFieldError var3) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.ROW_HIGHLIGHT_2
						.ordinal()] = 47;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				$SwitchMap$com$coa$lnf$COALnFColors[COALnFColors.ROW_HIGHLIGHT_SELECTED
						.ordinal()] = 48;
			} catch (NoSuchFieldError var1) {
				;
			}

		}
	}

}



// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class ImageLoader {

   private static Map<String, ImageIcon> sImages = new HashMap();
   private static final ImageLoader sImageLoader = new ImageLoader();
   public static final String DEFAULT_ICON = "Error16.png";
   public static final String FF_BOLD_OFF = "bold-off.png";
   public static final String FF_ITALIC_OFF = "italic-off.png";
   public static final String FF_UNDERLINE_OFF = "underline-off.png";
   public static final String FF_BOLD_ON = "bold-on.png";
   public static final String FF_ITALIC_ON = "italic-on.png";
   public static final String FF_UNDERLINE_ON = "underline-on.png";
   public static final String FF_BACKGROUND = "background-16.png";
   public static final String FF_TEXT = "label-foreground.png";
   public static final String FF_MERGE_CELL = "cell-merge.png";
   public static final String FF_SPLIT_CELL = "cell-split.png";
   public static final String FF_BORDER = "border.png";
   public static final String FF_GRID_ON = "grid-on.png";
   public static final String FF_GRID_OFF = "grid-off.png";
   public static final String FF_ALIGN_LEFT = "FormatAlignLeft16.png";
   public static final String FF_ALIGN_CENTRE = "FormatAlignCenter16.png";
   public static final String FF_ALIGN_RIGHT = "FormatAlignRight16.png";
   public static final String FF_CHART = "Chart16.png";
   public static final String FF_IMAGE = "image-16.png";
   public static final String FF_TEST = "test.png";
   public static final String FF_TEST_PARAM = "element-properties-16.png";
   public static final String FF_WORKSHEET_PROPS = "properties.png";
   public static final String XMLF_NEW = "new.gif";
   public static final String XMLF_DELETE = "delete.gif";
   public static final String STATUS_NETWORK = "network-activity.png";
   public static final String STATUS_NO_NETWORK = "network-activity-busy.png";
   public static final String STATUS_SECURE = "SecurityLock16.png";
   public static final String STATUS_NO_SECURE = "SecurityUnlock16.png";
   public static final String IMP_EXP_WIZARD = "32Application_Icon.png";
   public static final String ACTION_HELP = "help.png";
   public static final String INFORMATION = "about24.png";
   public static final String ATTACHMENT = "attachment.png";
   public static final String BUDGET = "NoteCheck16.png";
   public static final String NEW = "new.png";
   public static final String BLANK = "blank16.gif";
   public static final String CANCEL = "cancel.png";
   public static final String CLOSE = "close.gif";
   public static final String CP_ICON = "cp.gif";
   public static final String TREE_TICK = "tick.gif";
   public static final String TREE_CROSS = "cross.gif";
   public static final String TREE_NO_TICK = "notick.gif";
   public static final String HELP32 = "help-context-cursor.png";
   public static final String BACKGROUND = "watermark.png";
   public static final String CEDAR_ICON = "32Application_Icon.png";
   public static final String CHECK = "check.gif";
   public static final String ARROW_DOWN = "darrow.gif";
   public static final String PROCESSING = "processing.png";
   public static final String NOTE = "note-cp.png";
   public static final String UNREAD_NOTE = "note-unread.png";
   public static final String CALCULATOR = "calculator.png";
   public static final String RED_CALCULATOR = "calculator-red.png";
   public static final String YES = "yes.png";
   public static final String NO = "no-data.png";
   public static final String OPEN = "open.png";
   public static final String PRINT = "print.png";
   public static final String COPY = "copy.png";
   public static final String PASTE = "paste.png";
   public static final String SAVE = "save.png";
   public static final String ADD_ACCOUNT = "addAccount.png";
   public static final String CRITERIA = "criteria.png";
   public static final String EXCEL_EXPORT = "ico_file_excel2.png";
   public static final String PROFILE = "Maintain-Profiles.png";
   public static final String NOTE_LARGE = "note-cp.png";
   public static final String WARNING = "warnings.png";
   public static final String PRINT_PREVIEW = "preview.png";
   public static final String SUBMIT = "submit-16.png";
   public static final String MAXIMISE = "Maximise16.png";
   public static final String RESTORE = "restore-16.png";
   public static final String REPORT_SETTINGS = "report-configuration.png";
   public static final String SUPPRESS_GROUPINGS = "suppress-groupings-16.png";
   public static final String SUPPRESS_IDENTATION = "suppress-indentation-16.png";
   public static final String SUPPRESS_ROW_ZEROS = "suppress-row-zeros-16.png";
   public static final String SUPPRESS_COL_ZEROS = "suppress-column-zeros-16.png";
   public static final String ZOOM_IN = "zoomIn.png";
   public static final String ZOOM_OUT = "zoomOut.png";
   public static final String TO_COLUMN = "report-to-column.png";
   public static final String TO_ROW = "report-to-row.png";
   public static final String TO_FILTER = "report-to-filter.png";
   public static final String SWAP_COL_ROW = "report-swap-row-and-column.png";
   private static Map<String, String> mActionIcons = new HashMap();


   public static synchronized ImageIcon getImageIcon(String name) {
      if(name == null) {
         return null;
      } else {
         try {
            ImageIcon imageIcon = (ImageIcon)sImages.get(name);
            if(imageIcon == null) {
               String t;
               try {
                  t = "common/16/" + name;
                  InputStream in = ImageLoader.class.getClassLoader().getResourceAsStream(t);
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  byte[] buffer = new byte[1024];

                  int length;
                  while((length = in.read(buffer)) >= 0) {
                     if(length > 0) {
                        baos.write(buffer, 0, length);
                     }
                  }

                  byte[] result = baos.toByteArray();
                  imageIcon = new ImageIcon(result);
               } catch (Throwable var9) {
                  try {
                     t = "images/" + name;
                     imageIcon = new ImageIcon(sImageLoader.getClass().getResource(t));
                  } catch (Throwable var8) {
                     t = "images/Error16.png";
                     imageIcon = new ImageIcon(sImageLoader.getClass().getResource(t));
                  }
               }

               sImages.put(name, imageIcon);
            }

            return imageIcon;
         } catch (Throwable var10) {
            throw new RuntimeException("Unable to load image " + name);
         }
      }
   }
   
   /**
    * Returns the path to the file with given a name
    * 
    * @param name	File name, which is in the folder 'images'
    * @return
    */
	public static synchronized String getImagePath(String name) {
		if (name == null) {
			return null;
		} else {
			try {
				return sImageLoader.getClass().getResource("images/" + name).getPath();
			} catch (Throwable var10) {
				throw new RuntimeException("Unable to load image " + name);
			}
		}
	}

   public static synchronized ImageIcon getActionImageIcon(String name) {
      if(name == null) {
         return null;
      } else {
         String value = "";

         try {
            if(mActionIcons.isEmpty()) {
               putActions();
            }

            value = (String)mActionIcons.get(name);
            return getImageIcon(value);
         } catch (Throwable var3) {
            throw new RuntimeException("Unable to load image " + value);
         }
      }
   }

   private static void putActions() {
      mActionIcons.put("ACTION_UNDO", "undo.png");
      mActionIcons.put("ACTION_REDO", "redo.png");
      mActionIcons.put("ACTION_OPEN", "open.png");
      mActionIcons.put("ACTION_SAVE", "save.png");
      mActionIcons.put("ACTION_COPY", "copy.png");
      mActionIcons.put("ACTION_PRINT", "print.png");
      mActionIcons.put("ACTION_SAVEAS", "saveAs.png");
      mActionIcons.put("ACTION_PASTE", "paste.png");
      mActionIcons.put("ACTION_CUT", "cut.png");
      mActionIcons.put("ACTION_HELP", "help.png");
      mActionIcons.put("ACTION_NEW", "new.png");
      mActionIcons.put("ACTION_IMPORT", "import.png");
      mActionIcons.put("ACTION_EXPORT", "export.png");
      mActionIcons.put("ACTION_EXCEL_IMPORT", "importexcel16.png");
      mActionIcons.put("ACTION_BROWSER", "Browser16.png");
      mActionIcons.put("ACTION_BLANK", "blank16.gif");
      mActionIcons.put("ACTION_MENU_HELP", "Help-Context.png");
      mActionIcons.put("ACTION_EDIT_BASIC", "EditBasicAlloc16.gif");
   }

}

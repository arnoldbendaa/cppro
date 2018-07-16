// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.ErrorNotifiable;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class ErrorSettingAdapter {

   private static boolean sIconsLoaded;
   private static ImageIcon sInvalidImage;
   private static ImageIcon sValidImage;
   public static final String id = "@(#) $Archive: /fc/ui/base/ErrorSettingAdapter.java $ $Revision: 1.4 $";


   static void setValid(boolean isValid, Component component, String message, Object info) {
      if(isValid) {
         message = null;
      }

      if(component instanceof ErrorNotifiable) {
         ((ErrorNotifiable)component).setValid(isValid, message, info);
         component.invalidate();
      } else if(component instanceof JLabel) {
         JLabel tp = (JLabel)component;
         tp.setToolTipText(message);
         tp.setIcon(getImage(isValid));
      } else if(component instanceof JCheckBox) {
         JCheckBox tp1 = (JCheckBox)component;
         tp1.setToolTipText(message);
         tp1.setIcon(getImage(isValid));
      } else if(component.getParent() instanceof JTabbedPane) {
         JTabbedPane tp2 = (JTabbedPane)component.getParent();
         int index = tp2.indexOfComponent(component);
         tp2.setIconAt(index, getImage(isValid));
         tp2.setToolTipTextAt(index, message != null?"Errors exist on this tab":null);
      }

   }

   public static ImageIcon getImage(boolean isValid) {
      if(!sIconsLoaded) {
         URL imageURL = ErrorSettingAdapter.class.getResource("redcross.gif");
         Image img = Toolkit.getDefaultToolkit().getImage(imageURL);
         MediaTracker tracker = new MediaTracker(new JLabel());
         tracker.addImage(img, 0);

         try {
            tracker.waitForAll();
         } catch (InterruptedException var5) {
            var5.printStackTrace();
            System.err.println(var5.getMessage());
         }

         sInvalidImage = new ImageIcon(img);
         sIconsLoaded = true;
         sValidImage = null;
      }

      return isValid?sValidImage:sInvalidImage;
   }
}

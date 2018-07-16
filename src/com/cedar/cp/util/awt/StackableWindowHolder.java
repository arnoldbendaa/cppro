// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Container;
import java.util.Stack;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class StackableWindowHolder {

   private JFrame mFrame;
   private JApplet mApplet;
   private Stack<JComponent> mPanels = new Stack();
   private JComponent mCurrentComponent = null;
   private static StackableWindowHolder sInstance;


   private StackableWindowHolder(JFrame frame) {
      this.mFrame = frame;
   }

   private StackableWindowHolder(JApplet applet) {
      this.mApplet = applet;
   }

   public static void createInstance(JFrame frame) {
      sInstance = new StackableWindowHolder(frame);
   }

   public static void createInstance(JApplet applet) {
      sInstance = new StackableWindowHolder(applet);
   }

   public static synchronized void push(JComponent panel, JMenuBar menuBar) {
      if(sInstance == null) {
         throw new IllegalStateException("No StackableWindowHolder exists");
      } else {
         sInstance._push(panel, menuBar);
      }
   }

   public void _push(JComponent panel, JMenuBar menuBar) {
      if(this.mCurrentComponent != null) {
         this.mPanels.push(this.mCurrentComponent);
      }

      this.setCurrentJComponent(panel);
   }

   public static synchronized void pop() {
      if(sInstance == null) {
         throw new IllegalStateException("No StackableWindowHolder exists");
      } else {
         sInstance._pop();
      }
   }

   public void _pop() {
      if(!this.mPanels.isEmpty()) {
         this.setCurrentJComponent((JComponent)this.mPanels.pop());
      }

   }

   private void setCurrentJComponent(JComponent panel) {
      Container container = null;
      if(this.mFrame != null) {
         container = this.mFrame.getContentPane();
         if(container != null) {
            if(this.mCurrentComponent != null) {
               container.remove(this.mCurrentComponent);
            }

            container.add(panel, "Center");
            this.mCurrentComponent = panel;
            panel.invalidate();
            container.validate();
            panel.repaint();
         }
      } else if(this.mApplet != null) {
         container = this.mApplet.getContentPane();
         if(container != null) {
            if(this.mCurrentComponent != null) {
               container.remove(this.mCurrentComponent);
            }

            container.add(panel);
            this.mCurrentComponent = panel;
            panel.invalidate();
            container.validate();
            panel.repaint();
         }
      }

   }
}

// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TreeDepthPicker$1;
import com.cedar.cp.util.awt.TreeDepthPicker$2;
import com.cedar.cp.util.awt.TreeDepthSelectedEvent;
import com.cedar.cp.util.awt.TreeDepthSelectedListener;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class TreeDepthPicker extends JLabel {

   private static Color sMiniColor = new Color(15663103);
   public static final int BUTTON_SIZE = 12;
   public static final int BUTTON_MARGIN = 2;
   public static final int DEFAULT_MARGIN = 4;
   private String[] mLabels;
   private String mAllLabel;
   private int sLabelXSmallMargin;
   private int sLabelAXMargin;
   private int sLabelXMargin;
   private int sLabelYMargin;
   private int mMaxDepth;
   private int mCurrentMouseDepth;
   private int mMargin;
   private int mSelectedDepth;


   public TreeDepthPicker(int maxDepth) {
      this(maxDepth, 4);
   }

   public TreeDepthPicker(int maxDepth, int margin) {
      super("");
      this.mLabels = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
      this.mAllLabel = "A";
      this.sLabelXSmallMargin = 1;
      this.sLabelAXMargin = 3;
      this.sLabelXMargin = 4;
      this.sLabelYMargin = 2;
      this.mCurrentMouseDepth = -1;
      this.mMargin = 4;
      this.mSelectedDepth = 1000;
      this.mMaxDepth = maxDepth;
      this.mMargin = margin;
      this.setFont(new Font("Dialog", 0, 9));
      this.addMouseMotionListener(new TreeDepthPicker$1(this));
      this.addMouseListener(new TreeDepthPicker$2(this));
   }

   public Dimension getPreferredSize() {
      int width = 2 + this.mMaxDepth * 12;
      int height = this.mMargin * 2 + 12;
      return new Dimension(width, height);
   }

   public synchronized void addTreeDepthSelectedListener(TreeDepthSelectedListener l) {
      this.listenerList.add(TreeDepthSelectedListener.class, l);
   }

   public synchronized void removeTreeDepthSelectedListener(TreeDepthSelectedListener l) {
      this.listenerList.remove(TreeDepthSelectedListener.class, l);
   }

   protected void fireTreeDepthSelected(int depth) {
      Object[] listeners = this.listenerList.getListenerList();
      int modifiers = 0;
      AWTEvent currentEvent = EventQueue.getCurrentEvent();
      if(currentEvent instanceof InputEvent) {
         modifiers = ((InputEvent)currentEvent).getModifiers();
      } else if(currentEvent instanceof ActionEvent) {
         modifiers = ((ActionEvent)currentEvent).getModifiers();
      }

      TreeDepthSelectedEvent e = new TreeDepthSelectedEvent(this, 1001, depth, EventQueue.getMostRecentEventTime(), modifiers);

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeDepthSelectedListener.class) {
            ((TreeDepthSelectedListener)listeners[i + 1]).treeDepthSelected(e);
         }
      }

   }

   private int getMouseDepth(MouseEvent e) {
      int depth = -1;
      if(e.getY() > this.getSize().height - 12 - this.mMargin && e.getY() < this.getSize().height - this.mMargin && e.getX() > 2) {
         depth = (e.getX() - 2) / 12;
      }

      return depth;
   }

   public void unhilightedDepth() {
      this.mSelectedDepth = 1000;
      this.repaint();
   }

   public void setSelectedDepth(int selectedDepth) {
      this.mSelectedDepth = selectedDepth > this.mMaxDepth?this.mMaxDepth:selectedDepth;
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if(this.mMaxDepth > 1) {
         Dimension size = this.getSize();
         int x = 2;
         int y = size.height - this.mMargin - 12;

         for(int i = 0; i <= this.mMaxDepth; ++i) {
            int bottom = y + 12;
            int right = x + 12 - 1;
            if(i != this.mCurrentMouseDepth && i != this.mSelectedDepth) {
               g.setColor(sMiniColor);
            } else {
               g.setColor(UIManager.getColor("Table.selectionBackground"));
            }

            g.fillRect(x, y, 12, 12);
            g.setColor(Color.white);
            g.drawLine(x, y + 1, right, y + 1);
            g.drawLine(x + 1, y, x + 1, bottom);
            g.setColor(Color.black);
            g.drawRect(x, y, 12, 12);
            if(i == this.mCurrentMouseDepth || i == this.mSelectedDepth) {
               g.setColor(UIManager.getColor("Table.selectionForeground"));
            }

            int middle = y + 12 - this.sLabelYMargin;
            if(i == this.mMaxDepth) {
               g.drawString(this.mAllLabel, x + this.sLabelAXMargin, middle);
               g.setColor(UIManager.getColor("Table.selectionForeground"));
            } else if(i < 9) {
               g.drawString(this.mLabels[i], x + this.sLabelXMargin, middle);
            } else {
               g.drawString(this.mLabels[i], x + this.sLabelXSmallMargin, middle);
            }

            x += 12;
         }
      }

   }

   // $FF: synthetic method
   static int accessMethod000(TreeDepthPicker x0, MouseEvent x1) {
      return x0.getMouseDepth(x1);
   }

   // $FF: synthetic method
   static int accessMethod100(TreeDepthPicker x0) {
      return x0.mCurrentMouseDepth;
   }

   // $FF: synthetic method
   static int accessMethod102(TreeDepthPicker x0, int x1) {
      return x0.mCurrentMouseDepth = x1;
   }

   // $FF: synthetic method
   static int accessMethod200(TreeDepthPicker x0) {
      return x0.mMaxDepth;
   }

   // $FF: synthetic method
   static int accessMethod302(TreeDepthPicker x0, int x1) {
      return x0.mSelectedDepth = x1;
   }

}

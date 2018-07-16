// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class QTree extends JTree implements KeyListener {

   private String searchString;
   private long lastKeyPressTime = 0L;


   public QTree(TreeModel model) {
      super(model);
      this.addKeyListener(this);
   }

   public QTree() {
      this.addKeyListener(this);
   }

   public void keyPressed(KeyEvent e) {
      if(!e.isConsumed() && !e.isControlDown() && !e.isAltDown() && !e.isActionKey() && !e.isMetaDown() && !e.isAltGraphDown() && Character.isLetterOrDigit(e.getKeyChar())) {
         Date timeNow = new Date();
         long elapsedTime = timeNow.getTime() - this.lastKeyPressTime;
         if(elapsedTime > 1000L) {
            this.searchString = "" + e.getKeyChar();
         } else {
            this.searchString = this.searchString + e.getKeyChar();
         }

         this.lastKeyPressTime = timeNow.getTime();
         this.searchTreeContents();
      }

   }

   private void searchTreeContents() {
      int displayedRows = super.getRowCount();
      TreePath[] allDisplayedPaths = this.getPathBetweenRows(0, displayedRows);
      String[] nodes = this.createNodes(allDisplayedPaths);
      int found = -1;
      String entry = null;

      for(int i = 0; i < nodes.length; ++i) {
         entry = nodes[i].toString();
         if(entry.toLowerCase().startsWith(this.searchString.toLowerCase())) {
            found = i;
            break;
         }
      }

      if(found != -1) {
         this.setSelectionRow(found);
         this.scrollRowToVisible(found);
      }

   }

   private String[] createNodes(TreePath[] paths) {
      int entries = paths.length - 1;
      String[] nodes = new String[entries];

      for(int i = 0; i < entries; ++i) {
         String node = paths[i].toString();
         node = node.replace('[', ' ');
         node = node.replace(']', ' ');

         for(StringTokenizer st = new StringTokenizer(node); st.hasMoreTokens(); node = st.nextToken(",")) {
            ;
         }

         node = node.trim();
         nodes[i] = new String(node);
      }

      return nodes;
   }

   public void keyReleased(KeyEvent e) {}

   public void keyTyped(KeyEvent e) {}
}

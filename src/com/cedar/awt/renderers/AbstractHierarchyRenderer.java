// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.renderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

public abstract class AbstractHierarchyRenderer extends JPanel implements TreeCellRenderer {

   protected Icon mDefaultLeaf;
   protected Icon mDefaultOpen;
   protected Icon mDefaultClosed;
   protected JPanel mPrefixPanel;
   protected JLabel mImage1;
   protected Component mPrefixComponent;
   protected Component mPostfixComponent;
   protected JLabel mText;
   protected Color mSelectionBorderColor;
   protected Color mSelectionForegroundColour;
   protected Color mSelectionBackgroundColour;
   protected Color mTextForegroundColour;
   protected Color mTextBackgroundColour;


   public AbstractHierarchyRenderer() {
      this.setLayout(new BorderLayout(2, 0));
      this.mDefaultLeaf = UIManager.getIcon("Tree.leafIcon");
      this.mDefaultOpen = UIManager.getIcon("Tree.openIcon");
      this.mDefaultClosed = UIManager.getIcon("Tree.closedIcon");
      this.mSelectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
      this.mSelectionForegroundColour = UIManager.getColor("Tree.selectionForeground");
      this.mSelectionBackgroundColour = UIManager.getColor("Tree.selectionBackground");
      this.mTextForegroundColour = UIManager.getColor("Tree.textForeground");
      this.mTextBackgroundColour = UIManager.getColor("Tree.textBackground");
      this.mImage1 = new JLabel(this.mDefaultClosed);
      this.mImage1.setVerticalAlignment(0);
      this.mImage1.setHorizontalAlignment(2);
      this.mImage1.setOpaque(false);
      this.mPrefixPanel = new JPanel(new BorderLayout());
      this.mPrefixPanel.add(this.mImage1, "West");
      this.mPrefixPanel.setOpaque(false);
      this.mText = new JLabel("uninitialised", 2);
      this.mText.setVerticalAlignment(0);
      this.mText.setHorizontalAlignment(2);
      this.mText.setHorizontalTextPosition(2);
      this.mText.setOpaque(true);
      this.add(this.mPrefixPanel, "West");
      this.add(this.mText, "Center");
      this.setOpaque(true);
   }

   public void setFont(Font newFont) {
      super.setFont(newFont);
      if(this.mImage1 != null) {
         this.mImage1.setFont(newFont);
      }

      if(this.mPrefixPanel != null) {
         this.mPrefixPanel.setFont(newFont);
      }

      if(this.mText != null) {
         this.mText.setFont(newFont);
      }

      if(this.mPostfixComponent != null) {
         this.mPostfixComponent.setFont(newFont);
      }

   }

   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      if(leaf) {
         this.mImage1.setIcon(this.mDefaultLeaf);
      } else if(expanded) {
         this.mImage1.setIcon(this.mDefaultOpen);
      } else {
         this.mImage1.setIcon(this.mDefaultClosed);
      }

      TreeNode node = null;
      if(value instanceof TreeNode) {
         node = (TreeNode)value;
      }

      if(node != null) {
         int txt = this.mPrefixPanel.getComponentCount();
         if(txt == 2) {
            this.mPrefixPanel.remove(this.mPrefixComponent);
         }

         this.mPrefixComponent = this.getRenderPrefixComponent(tree, node, sel);
         if(this.mPrefixComponent != null) {
            this.mPrefixPanel.add(this.mPrefixComponent, "Center");
         }

         if(this.mPostfixComponent != null) {
            this.remove(this.mPostfixComponent);
            this.mPostfixComponent = null;
         }

         Component c = this.getRenderPostfixComponent(tree, node, sel);
         if(c != null) {
            this.mPostfixComponent = c;
            this.add(this.mPostfixComponent, "East");
         }

         String txt1 = node.toString();
         this.mText.setText(txt1);
         this.setToolTipText(txt1);
         this.mText.setEnabled(this.isNodeEnabled(node));
      } else {
         String txt2 = " " + value.toString();
         this.mText.setText(txt2);
         this.setToolTipText(txt2);
      }

      if(sel) {
         this.mText.setForeground(this.mSelectionForegroundColour);
         this.mText.setBackground(this.mSelectionBackgroundColour);
      } else {
         this.mText.setForeground(this.mTextForegroundColour);
         this.mText.setBackground(this.mTextBackgroundColour);
      }

      return this;
   }

   public abstract Component getRenderPrefixComponent(JTree var1, TreeNode var2, boolean var3);

   public abstract Component getRenderPostfixComponent(JTree var1, TreeNode var2, boolean var3);

   public boolean isNodeEnabled(TreeNode node) {
      return true;
   }

   public Dimension getPreferredSize() {
      Dimension retDimension = super.getPreferredSize();
      int extraWidth = 2;
      if(this.mPrefixPanel.getComponentCount() == 1) {
         extraWidth += this.mImage1.getPreferredSize().width;
      }

      if(retDimension != null) {
         retDimension = new Dimension(retDimension.width + extraWidth, retDimension.height);
      }

      return retDimension;
   }

   public JLabel getText() {
      return this.mText;
   }
}

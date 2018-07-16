package com.cedar.cp.util.flatform.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cedar.cp.util.flatform.model.OutlineHead;
import com.cedar.cp.util.flatform.model.OutlineHead.OutlineState;
import com.cedar.cp.util.flatform.model.OutlineLevelModel;

/**
*
* @author Jaroslaw Kaczmarski
* @company Softpro.pl Sp. z o.o.
*
* Class used to draw groups. Based on model OutlineLevel
*/
public class OutlineLevelPanel extends JPanel {
	private static final long serialVersionUID = -649866031537848721L;
	
	/**
    * Column width in pixels
    */
   private int columnWidth = 13;
   /**
    * Cell height in pixels
    */
   private int cellHeight = 17;
   /**
    * Data model
    */
   private OutlineLevelModel model;
   
   /**
    * Default constructor - sets data model
    * @return
    */
   public OutlineLevelPanel(OutlineLevelModel model) {
       this.model = model;
       setLayout(null);
       setPreferredSize();
       buildComponents();
   }

   /**
    * Sets the size of the panel
    * @return
    */
   public void setPreferredSize() {
      int panelWidth = 0;
      int panelHeight = 0;      
      if (model.getOutlineLevels().size() > 0) {
         // Panel width = (amount of the outline level + 1 additional level) * column width
    	 panelWidth = (model.getOutlineLevels().size() + 1) * columnWidth;
         // Panel height = height of the top button panel + (amount of the cells * cell height)
         panelHeight = model.getFirstLevel().get(model.getFirstLevel().size() - 1).getRange().getLast() * cellHeight;
      }
      setPreferredSize(new Dimension(panelWidth, panelHeight));
   }

   /**
    * Builds swing components on outline panel
    * @param model
    * @return
    */
   public void buildComponents() {
	  removeAll();
	  if (model.getOutlineLevels().size() > 0) {
		  buildGroups(model.getFirstLevel());
	  }
   }
   
   /**
    * Builds all heads in reference
    * @param heads
    * @return
    */
   public void buildGroups(List<OutlineHead> heads){
      for(int i=0; i < heads.size(); i++){
         buildGroup(heads.get(i)); // put head on panel
	     if (heads.get(i).getOutlineState() == OutlineState.EXPANDED) { // if extended, check children
	       	buildGroups(heads.get(i).getChildren());
	     }
      }
   }
   
   /**
    * Builds one head and put on outline panel
    * @param heads
    * @return
    */
   public void buildGroup(OutlineHead head){
	   Icon headIcon;
       
       if (head.getOutlineState() == OutlineState.COLLAPSED) {
           headIcon = getCollapsedIcon(); // [+]
       }
       else {
           headIcon = getExpandedIcon(); // [-]
       }
       
       // Coordinates
       int x = (head.getOulineLevel() * columnWidth) + ((columnWidth - headIcon.getIconWidth()) / 2);
       int y = (head.getRange().getFirst()-1) * cellHeight;
       
       // Build head
       JButton headButton = new JButton(headIcon);
       headButton.setName(head.getUUID());
       headButton.setOpaque(false);
       headButton.setContentAreaFilled(false);
       headButton.setBorderPainted(false);
       headButton.setFocusPainted(false);
       if (head.getOutlineState() == OutlineState.EXPANDED) {
    	   headButton.setToolTipText("Collapse this group");
       }
       else {
    	   headButton.setToolTipText("Expand this group");
       }
       headButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
       headButton.addActionListener(new java.awt.event.ActionListener() {
    	   @Override
           public void actionPerformed(ActionEvent e) {
    		   headButtonActionPerformed(e);
           }
       });
       add(headButton);
       headButton.setBounds(x, y, headIcon.getIconWidth()+1, headIcon.getIconHeight());
   }
   
   /**
    * Paint graphics components on outline panel
    * @param g
    * @return
    */
   @Override
   protected void paintComponent(Graphics g) {
	  // Paint background
      this.setBackground(new Color(235,235,235));
	  super.paintComponent(g);
   
      // Paint group ranges
      paintGroups(g, model.getFirstLevel());
   }
   
   /**
    * Paint all ranges in reference
    * @param g
    * @param heads
    * @return
    */
   public void paintGroups(Graphics g, List<OutlineHead> heads){
      for(int i=0; i < heads.size(); i++){
         if (heads.get(i).getOutlineState() == OutlineState.EXPANDED) { // if extended, check children
        	paintGroup(g, heads.get(i), heads.get(i).getRange().getCurrentLenght()); // paint range only for expanded head
            paintGroups(g, heads.get(i).getChildren());
         }
      }
   }
   
   /**
    * Paint one range on panel
    * @param g
    * @param head
    * @param currentLengh
    * @return
    */
   public void paintGroup(Graphics g, OutlineHead head, Integer currentLengh){
      Icon headIcon = getExpandedIcon(); // [-]
      
      // Coordinates
      int x = (head.getOulineLevel() * columnWidth) + ((columnWidth - headIcon.getIconWidth()) / 2);
      int yTop = ((head.getRange().getFirst()-1) * cellHeight) + headIcon.getIconHeight();
      int yBottom = yTop + (currentLengh * cellHeight);
      
      // Draw vertical line
      g.drawLine( x + (headIcon.getIconWidth() / 2), yTop , x + (headIcon.getIconWidth() / 2), yBottom );
      // Draw horizontal line
      g.drawLine( x + (headIcon.getIconWidth() / 2), yBottom, (x + headIcon.getIconWidth()) - 1, yBottom );
   }
   
   /*------------ Actions ------------*/
   
   /**
    * Action that is performed by pressing the head. Change the state of the head and repaint panel.
    * @param e
    * @return
    */
   private void headButtonActionPerformed(ActionEvent e) {
       for (int i = 0; i < model.getOutlineLevels().size(); i++) {
           for (int j = 0; j < model.getHeadsFromOutlineLevel(i).size(); j++){
        	   if (model.getHeadsFromOutlineLevel(i).get(j).getUUID().equals((String)((Component)e.getSource()).getName())) {
        		   model.getHeadsFromOutlineLevel(i).get(j).switchOutlineState();
        		   buildComponents();
        		   repaint();
        		   return;
        	   }
           }
       }
   }
   
   /*------------ Getters and Setters ------------*/
   
   public void setColumnWidth(int columnWidth) {
       this.columnWidth = columnWidth;
   }

   public void setCellHeight(int cellHeight) {
       this.cellHeight = cellHeight;
   }

   public void setModel(OutlineLevelModel model) {
       this.model = model;
   }
   
   /*------------ Collapsed / Expanded Icon ------------*/
   
    /**
    * Get collapsed icon
    * @return icon [+]
    */
   public Icon getCollapsedIcon() {
       return new NodeIcon('+');
   }

   /**
    * Get expanded icon
    * @return icon [-]
    */
   public Icon getExpandedIcon() {
       return new NodeIcon('-');
   }
   
   /**
    * Class to draw node icons ([+] or [-])
    */
   public class NodeIcon implements Icon {

       private static final int SIZE = 11;
       private char type;

       public NodeIcon(char type) {
           this.type = type;
       }

       @Override
       public void paintIcon(Component c, Graphics g, int x, int y) {
           g.setColor(Color.WHITE);
           g.fillRect(x, y, SIZE - 1, SIZE - 1);

           g.setColor(Color.BLACK);
           g.drawRect(x, y, SIZE - 1, SIZE - 1);

           g.drawLine(x + 3, y + SIZE / 2, x + SIZE - 4, y + SIZE / 2);
           if (type == '+') {
               g.drawLine(x + SIZE / 2, y + 3, x + SIZE / 2, y + SIZE - 4);
           }
       }

       @Override
       public int getIconWidth() {
           return SIZE;
       }

       @Override
       public int getIconHeight() {
           return SIZE;
       }
   }
}

package com.cedar.cp.util.flatform.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
* Class used to draw group buttons. Based on model OutlineLevel
*/
public class TopLeftPanel extends JPanel {
	private static final long serialVersionUID = 8105643681426784496L;
	
	/**
    * Column width in pixels
    */
   private int columnWidth = 13;
   /**
    * Buttons
    */
   private JButton topButtons[];
   /**
    * Data model
    */
   private OutlineLevelModel model;
   
   /**
    * Default constructor - sets data model
    *
    * @return
    */
   public TopLeftPanel(OutlineLevelModel model) {
       this.model = model;
       setLayout(null);
       buildComponents();
   }


   /**
    * Builds buttons and repaints the panel
    * @param model
    */
	public void buildComponents() {
		topButtons = new JButton[model.getOutlineLevels().size() + 1];
	       for (int i = 0; i < topButtons.length; i++) {
	           topButtons[i] = new JButton();
	           topButtons[i].setFont(new java.awt.Font("DejaVu Sans", 0, 8));
	           topButtons[i].setMargin(new Insets(0,0,0,0)); 
	           topButtons[i].setText(Integer.toString(i+1));
	           topButtons[i].setFocusPainted(false);
	           topButtons[i].setToolTipText("Expand all groups to level "+Integer.toString(i+1));
	           topButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	           topButtons[i].addActionListener(new java.awt.event.ActionListener() {
	        	   @Override
	               public void actionPerformed(ActionEvent e) {
	                   topButtonActionPerformed(e);
	               }
	           });
	           add(topButtons[i]);
	           topButtons[i].setBounds(columnWidth*i, 2, 13, 13);
	       }
	}
   
   /*------------ Actions ------------*/

   private void topButtonActionPerformed(ActionEvent e) {
       System.out.println("Clicked button "+e.getActionCommand());
       
       for (int j = 0; j < (Integer.parseInt(e.getActionCommand())-1); j++){
           for (int i = 0; i < model.getHeadsFromOutlineLevel(j).size(); i++){
               model.getHeadsFromOutlineLevel(j).get(i).setOutlineState(OutlineState.EXPANDED);
           }
       }
       if ( Integer.parseInt(e.getActionCommand()) <= model.getOutlineLevels().size()) {
           for (int i = 0; i < model.getHeadsFromOutlineLevel(Integer.parseInt(e.getActionCommand())-1).size(); i++){
               model.getHeadsFromOutlineLevel(Integer.parseInt(e.getActionCommand())-1).get(i).setOutlineState(OutlineState.COLLAPSED);
           }
       }
   }
   
   /*------------ Getters and Setters ------------*/
   
   public void setColumnWidth(int columnWidth) {
       this.columnWidth = columnWidth;
   }

   public void setModel(OutlineLevelModel model) {
       this.model = model;
   }

}

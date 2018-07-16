// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.Log;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.View;
import javax.swing.text.html.HTMLDocument;

public class DocumentRenderer implements Printable {

   protected int currentPage = -1;
   protected JEditorPane jeditorPane;
   protected double pageEndY = 0.0D;
   protected double pageStartY = 0.0D;
   protected boolean scaleWidthToFit = true;
   protected PageFormat pFormat = new PageFormat();
   protected PrinterJob pJob = PrinterJob.getPrinterJob();
   private transient Log mLog = new Log(this.getClass());


   public Document getDocument() {
      return this.jeditorPane != null?this.jeditorPane.getDocument():null;
   }

   public boolean getScaleWidthToFit() {
      return this.scaleWidthToFit;
   }

   public void pageDialog() {
      this.pFormat = this.pJob.pageDialog(this.pFormat);
   }

   public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
      double scale = 1.0D;
      Graphics2D graphics2D = (Graphics2D)graphics;
      this.jeditorPane.setSize((int)pageFormat.getImageableWidth(), Integer.MAX_VALUE);
      this.jeditorPane.validate();
      View rootView = this.jeditorPane.getUI().getRootView(this.jeditorPane);
      if(this.scaleWidthToFit && this.jeditorPane.getMinimumSize().getWidth() > pageFormat.getImageableWidth()) {
         scale = pageFormat.getImageableWidth() / this.jeditorPane.getMinimumSize().getWidth();
         graphics2D.scale(scale, scale);
      }

      graphics2D.setClip((int)(pageFormat.getImageableX() / scale), (int)(pageFormat.getImageableY() / scale), (int)(pageFormat.getImageableWidth() / scale), (int)(pageFormat.getImageableHeight() / scale));
      if(pageIndex > this.currentPage) {
         this.currentPage = pageIndex;
         this.pageStartY += this.pageEndY;
         this.pageEndY = graphics2D.getClipBounds().getHeight();
      }

      graphics2D.translate(graphics2D.getClipBounds().getX(), graphics2D.getClipBounds().getY());
      Rectangle allocation = new Rectangle(0, (int)(-this.pageStartY), (int)this.jeditorPane.getMinimumSize().getWidth(), (int)this.jeditorPane.getPreferredSize().getHeight());
      if(this.printView(graphics2D, allocation, rootView)) {
         return 0;
      } else {
         this.pageStartY = 0.0D;
         this.pageEndY = 0.0D;
         this.currentPage = -1;
         return 1;
      }
   }

   public void print(HTMLDocument htmlDocument) {
      this.setDocument(htmlDocument);
      this.printDialog();
   }

   public void print(JEditorPane jedPane) {
      this.setDocument(jedPane);
      this.printDialog();
   }

   public void print(PlainDocument plainDocument) {
      this.setDocument(plainDocument);
      this.printDialog();
   }

   protected void printDialog() {
      if(this.pJob.printDialog()) {
         this.pJob.setPrintable(this, this.pFormat);

         try {
            this.pJob.print();
         } catch (PrinterException var2) {
            this.pageStartY = 0.0D;
            this.pageEndY = 0.0D;
            this.currentPage = -1;
            this.mLog.debug("Error Printing Document");
         }
      }

   }

   protected boolean printView(Graphics2D graphics2D, Shape allocation, View view) {
      boolean pageExists = false;
      Rectangle clipRectangle = graphics2D.getClipBounds();
      if(view.getViewCount() > 0 && !view.getElement().getName().equalsIgnoreCase("td")) {
         for(int i = 0; i < view.getViewCount(); ++i) {
            Shape childAllocation = view.getChildAllocation(i, allocation);
            if(childAllocation != null) {
               View childView = view.getView(i);
               if(this.printView(graphics2D, childAllocation, childView)) {
                  pageExists = true;
               }
            }
         }
      } else if(allocation.getBounds().getMaxY() >= clipRectangle.getY()) {
         pageExists = true;
         if(allocation.getBounds().getHeight() > clipRectangle.getHeight() && allocation.intersects(clipRectangle)) {
            view.paint(graphics2D, allocation);
         } else if(allocation.getBounds().getY() >= clipRectangle.getY()) {
            if(allocation.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
               view.paint(graphics2D, allocation);
            } else if(allocation.getBounds().getY() < this.pageEndY) {
               this.pageEndY = allocation.getBounds().getY();
            }
         }
      }

      return pageExists;
   }

   protected void setContentType(String type) {
      this.jeditorPane.setContentType(type);
   }

   public void setDocument(HTMLDocument htmlDocument) {
      this.jeditorPane = new JEditorPane();
      this.setDocument("text/html", htmlDocument);
   }

   public void setDocument(JEditorPane jedPane) {
      this.jeditorPane = new JEditorPane();
      this.setDocument(jedPane.getContentType(), jedPane.getDocument());
   }

   public void setDocument(PlainDocument plainDocument) {
      this.jeditorPane = new JEditorPane();
      this.setDocument("text/plain", plainDocument);
   }

   protected void setDocument(String type, Document document) {
      this.setContentType(type);
      this.jeditorPane.setDocument(document);
   }

   public void setScaleWidthToFit(boolean scaleWidth) {
      this.scaleWidthToFit = scaleWidth;
   }
}

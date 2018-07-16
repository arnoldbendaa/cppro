// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.util.XmlUtils;

public abstract class XMLReportUtils {

   public abstract void add(String var1);

   public void addReport() {
      this.add("<report>");
   }

   public void addEndReport() {
      this.add("</report>");
   }

   public void addParamSection(String name) {
      this.add("<paramsection name=\"");
      this.add(XmlUtils.escapeStringForXML(name));
      this.add("\">\n");
   }

   public void addEndParamSection() {
      this.add("</paramsection>\n");
   }

   public void addParam(String name, String value) {
      this.add("<param name=\"");
      this.add(XmlUtils.escapeStringForXML(name));
      this.add("\" value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>\n");
   }

   public void addParam(String name) {
      this.add("<param name=\"");
      this.add(name);
      this.add("\">\n");
   }

   public void addParamIndent(int indent) {
      this.add("<paramindent indent=\"");
      this.add(String.valueOf(indent));
      this.add("\">\n");
   }

   public void addEndParamIndent() {
      this.add("</paramindent>\n");
   }

   public void addEndParam() {
      this.add("</param>\n");
   }

   public void addMatrixSection(String name) {
      this.add("<matrixsection name=\"");
      this.add(XmlUtils.escapeStringForXML(name));
      this.add("\">\n");
   }

   public void addEndMatrixSection() {
      this.add("</matrixsection>\n");
   }

   public void addRow() {
      this.add("<row>");
   }

   public void addEndRow() {
      this.add("</row>\n");
   }

   public void addCellHeading(String value) {
      this.add("<cellheading value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>\n");
   }

   public void addCellHeading(String value, String align) {
      this.add("<cellheading align=\"");
      this.add(align);
      this.add("\" value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>\n");
   }

   public void addMulti() {
      this.add("<multi>");
   }

   public void addMultiPart(int indent, String value) {
      this.add("<multipart indent=\"");
      this.add(String.valueOf(indent));
      this.add("\"");
      this.add(" value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>");
   }

   public void addEndMulti() {
      this.add("</multi>");
   }

   public void addMultiHeading() {
      this.add("<multiheading>\n");
   }

   public void addEndMultiHeading() {
      this.add("</multiheading>\n");
   }

   public void addCellText(String value) {
      this.add("<celltext value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>");
   }

   public void addCellText(String value, String align) {
      this.add("<celltext align=\"" + align + "\" value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>");
   }

   public void addCellText(String value, int rowSpan) {
      this.add("<celltext rowspan=\"");
      this.add(String.valueOf(rowSpan));
      this.add("\" value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>");
   }

   public void addCellNumber(String value) {
      this.add("<cellnumber value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>");
   }

   public void addTaskSection(String name) {
      this.add("<tasksection name=\"");
      this.add(XmlUtils.escapeStringForXML(name));
      this.add("\">\n");
   }

   public void addTaskMessage(String msg) {
      this.add("<taskmessage value=\"");
      this.add(XmlUtils.escapeStringForXML(msg));
      this.add("\"/>\n");
   }

   public void addEndTaskSection() {
      this.add("</tasksection>\n");
   }

   public void addPlain(String value) {
      this.add("<plain value=\"");
      this.add(XmlUtils.escapeStringForXML(value));
      this.add("\"/>\n");
   }
}

// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlUtils {

   private static Schema sFinanceFormSchema;


   public static void validateFinanceForm(String xmlText) throws Exception {
      DocumentBuilder db = getBuilder();
      InputSource input = new InputSource(new StringReader(xmlText));
      Document doc = db.parse(input);
      DOMSource domSource = new DOMSource(doc);
      Validator v = sFinanceFormSchema.newValidator();
      v.validate(domSource);
   }

   public static String prettyPrint(String xmlText) throws Exception {
      return prettyPrint(xmlText, true);
   }

   public static String normaliseDoc(String xmlText) throws Exception {
      String transform = prettyPrint(xmlText, false);
      return transform.replaceAll("\n|\r", "");
   }

   private static String prettyPrint(String xmlText, boolean newLines) throws Exception {
      StringWriter writer = new StringWriter();
      StreamResult result = new StreamResult(writer);
      TransformerFactory tf = TransformerFactory.newInstance();
      InputStream is;
      if(newLines) {
         is = XmlUtils.class.getResourceAsStream("format_form.xslt");
      } else {
         is = XmlUtils.class.getResourceAsStream("simplyfy.xslt");
      }

      StreamSource format = new StreamSource(is);
      Transformer transformer = tf.newTransformer(format);
      transformer.setOutputProperty("method", "xml");
      transformer.setOutputProperty("media-type", "text/xml");
      StreamSource input = new StreamSource(new StringReader(xmlText));
      transformer.transform(input, result);
      return writer.toString();
   }

   private static DocumentBuilder getBuilder() throws Exception {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilderFactory.setValidating(false);
      docBuilderFactory.setNamespaceAware(true);
      return docBuilderFactory.newDocumentBuilder();
   }

   public static String escapeStringForXML(Object o) {
      if(o == null) {
         return "&nbsp;";
      } else {
         String s = o.toString();
         return s.trim().length() == 0?"&nbsp;":escapeStringForXML(s, false);
      }
   }

   public static String escapeStringForXML(String s) {
      return escapeStringForXML(s, false);
   }

   public static String escapeStringForXML(String s, boolean allowHtmlElements) {
      if(s == null) {
         return null;
      } else {
         StringCharacterIterator i = new StringCharacterIterator(s);
         StringBuffer sb = new StringBuffer();

         for(char c = i.first(); c != '\uffff'; c = i.next()) {
            switch(c) {
            case 34:
               sb.append("&quot;");
               break;
            case 36:
               sb.append("&#36;");
               break;
            case 38:
               sb.append("&amp;");
               break;
            case 39:
               sb.append("&apos;");
               break;
            case 60:
               if(allowHtmlElements) {
                  sb.append('<');
               } else {
                  sb.append("&lt;");
               }
               break;
            case 62:
               if(allowHtmlElements) {
                  sb.append('>');
               } else {
                  sb.append("&gt;");
               }
               break;
            case 163:
               sb.append("&#163;");
               break;
            case 8364:
               sb.append("&#8364;");
               break;
            default:
               if(c != 9 && c != 10 && c != 13 && (c < 32 || c > '\ud7ff') && (c < '\ue000' || c > '\ufffd') && (c < 65536 || c > 1114111)) {
                  boolean c1 = true;
               } else {
                  sb.append(c);
               }
            }
         }

         return sb.toString();
      }
   }

   public static String escapeXMLStringForHTML(String s) {
      if(s == null) {
         return null;
      } else {
         s = s.replaceAll("&lt;", "<");
         s = s.replaceAll("&quot;", "\"");
         s = s.replaceAll("&gt;", ">");
         return s;
      }
   }

   public static void outputAttribute(Writer w, String attributeName, Object value) throws IOException {
      w.write(" ");
      w.write(attributeName);
      w.write("=\"");
      if(value != null) {
         w.write(escapeStringForXML(String.valueOf(value)));
      }

      w.write("\" ");
   }

   public static void outputElement(Writer w, String elementName, Collection<XMLWritable> values) throws IOException {
      w.write(" <");
      w.write(elementName);
      w.write(">\n");
      Iterator i$ = values.iterator();

      while(i$.hasNext()) {
         XMLWritable value = (XMLWritable)i$.next();
         value.writeXml(w);
      }

      w.write(" </");
      w.write(elementName);
      w.write(">\n");
   }

   static {
      try {
         SchemaFactory e = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         InputStream is = XMLWritable.class.getResourceAsStream("/com/cedar/cp/util/XMLForm.xsd");
         StreamSource ss = new StreamSource(is);
         sFinanceFormSchema = e.newSchema(ss);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}

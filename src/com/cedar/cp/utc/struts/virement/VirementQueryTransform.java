// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class VirementQueryTransform {

   public String transform(String virementXML) throws Exception {
      StringBuilder sb = new StringBuilder();
      sb.append("<Virements>");
      sb.append(virementXML);
      sb.append("</Virements>");
      TransformerFactory tFactory = TransformerFactory.newInstance();
      InputStream xslStream = this.getClass().getResourceAsStream("VirementQuery.xsl");
      Transformer transformer = tFactory.newTransformer(new StreamSource(xslStream));
      StreamSource source = new StreamSource(new StringReader(sb.toString()));
      StringWriter writer = new StringWriter();
      transformer.transform(source, new StreamResult(writer));
      StringBuffer result = new StringBuffer(writer.toString());
      writer.close();
      return result.toString();
   }
}

package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedCalendarElement;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;
import java.util.List;

public interface MappedCalendarYear extends Serializable, XMLWritable {

   int getYear();

   List<MappedCalendarElement> getMappedCalendarElements();

   boolean hasMappedElements();

   boolean hasUnmappedElements();
   
   String getCompanies();
   
   void setCompanies(String companies);
}

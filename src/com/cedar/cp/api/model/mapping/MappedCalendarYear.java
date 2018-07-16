// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarElement;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;
import java.util.List;

public interface MappedCalendarYear extends Serializable, XMLWritable {

   int getYear();

   List<MappedCalendarElement> getMappedCalendarElements();

   boolean hasMappedElements();

   boolean hasUnmappedElements();
}
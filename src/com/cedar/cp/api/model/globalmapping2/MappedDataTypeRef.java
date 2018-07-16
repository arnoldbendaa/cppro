package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;

public interface MappedDataTypeRef extends EntityRef {

   int IMPORT_ONLY = 0;
   int EXPORT_ONLY = 1;
   int IMPORT_EXPORT = 2;

}

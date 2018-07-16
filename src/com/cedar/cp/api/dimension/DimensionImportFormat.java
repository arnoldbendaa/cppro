// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;


public interface DimensionImportFormat {

   String LOAD = "L";
   String DELTA = "D";
   String ACCOUNT = "A";
   String BUSINESS = "B";
   String CALENDAR = "C";
   String INSERT = "I";
   String EDIT = "E";
   String MOVE = "M";
   String RENAME = "R";
   String DELETE = "D";
   String LOAD_TEXT = "Load";
   String DELTA_TEXT = "Delta";
   String[] MESSAGES = new String[]{"File has no records", "File type [{0}]  is not valid", "There were no detail records on the file", "Operation [{0}] is not valid for this file type", "No Root Element found", "Validation errors found - process abandoned", "Dimension Name must be supplied for {0}", "No valid Dimension Type supplied for {0}", "A Dimension of this Name and Type already exists", "Dimension header record was not valid for {0}", "A Dimension of this Name and Type does not exist", "Sequence number not unique within file", "Element not supplied for this Load operation", "More than one Root Element found", "No valid Parent Sequence number supplied for this Load operation", "More than one Parent in this Dimension with the same Name", "No valid Parent Element Name supplied for this Load operation", "Invalid Sibling position format supplied for this Load operation", "", "", "", "", ""};
   int NO_RECORDS = 0;
   int TYPE_INVALID = 1;
   int NO_DETAILS = 2;
   int INVALID_OPERATION = 3;
   int NO_ROOT = 4;
   int VALIDATION_ERRORS = 5;
   int NO_DIMENSION_NAME = 6;
   int NO_VALID_DIMENSION_TYPE = 7;
   int DUPLICATE_DIMENSION = 8;
   int INVALID_HEADER = 9;
   int DIMENSION_DOESNT_EXIST = 10;
   int SEQUENCE_NOT_UNIQUE = 11;
   int NO_ELEMENT_FOR_LOAD = 12;
   int DUPLICATE_ROOT = 13;
   int NO_PARENT_SEQUENCE = 14;
   int DUPLICATE_PARENT = 15;
   int NO_PARENT_FOR_LOAD = 16;
   int INVALID_SIBLING_FOR_LOAD = 17;
   int X1 = 18;
   int X2 = 19;
   int X3 = 20;


}

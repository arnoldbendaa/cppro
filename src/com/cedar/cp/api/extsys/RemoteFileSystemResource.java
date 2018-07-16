// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import java.io.Serializable;
import java.util.List;
import javax.swing.tree.TreeNode;

public interface RemoteFileSystemResource extends TreeNode, Serializable {

   int RESOURCE_TYPE_FILE = 1;
   int RESOURCE_TYPE_DIRECTORY = 2;
   int RESOURCE_TYPE_DRIVE = 3;


   String getURL();

   RemoteFileSystemResource getParent();

   String getName();

   int getResourceType();

   List<RemoteFileSystemResource> getChildren();
}

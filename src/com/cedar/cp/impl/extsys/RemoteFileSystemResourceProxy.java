// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import com.cedar.cp.dto.extsys.RemoteFileSystemResourceImpl;
import com.cedar.cp.impl.extsys.ExternalSystemEditorImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoteFileSystemResourceProxy implements InvocationHandler {

   private ExternalSystemEditorImpl mExternalSystemEditor;
   private RemoteFileSystemResourceImpl mRemoteFileSystemResource;


   public static RemoteFileSystemResource newInstance(ExternalSystemEditorImpl editor, RemoteFileSystemResourceImpl obj) {
      return (RemoteFileSystemResource)Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new RemoteFileSystemResourceProxy(editor, obj));
   }

   protected RemoteFileSystemResourceProxy(ExternalSystemEditorImpl editor, RemoteFileSystemResourceImpl rfsr) {
      this.mExternalSystemEditor = editor;
      this.mRemoteFileSystemResource = rfsr;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if((method.getName().equals("getChildren") || method.getName().equals("children") || method.getName().equals("getChildCount") || method.getName().equals("getIndex")) && this.mRemoteFileSystemResource.getResourceType() == 2 && this.mRemoteFileSystemResource.getChildren() == null) {
         List children = this.mExternalSystemEditor.queryRemoteFileSystem(this.mRemoteFileSystemResource.getPath());
         Iterator proxiedChildren = children.iterator();

         while(proxiedChildren.hasNext()) {
            RemoteFileSystemResource i$ = (RemoteFileSystemResource)proxiedChildren.next();
            ((RemoteFileSystemResourceImpl)i$).setParent(this.mRemoteFileSystemResource);
         }

         ArrayList proxiedChildren1 = new ArrayList();
         Iterator i$1 = children.iterator();

         while(i$1.hasNext()) {
            RemoteFileSystemResource child = (RemoteFileSystemResource)i$1.next();
            proxiedChildren1.add(newInstance(this.mExternalSystemEditor, (RemoteFileSystemResourceImpl)child));
         }

         this.mRemoteFileSystemResource.setChildren(proxiedChildren1);
      }

      return method.invoke(this.mRemoteFileSystemResource, args);
   }
}

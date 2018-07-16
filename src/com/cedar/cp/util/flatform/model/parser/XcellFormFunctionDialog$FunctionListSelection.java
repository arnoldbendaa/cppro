// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$1;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class XcellFormFunctionDialog$FunctionListSelection implements ListSelectionListener {

   // $FF: synthetic field
   final XcellFormFunctionDialog this$0;


   private XcellFormFunctionDialog$FunctionListSelection(XcellFormFunctionDialog var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent event) {
      this.this$0.mCurrentFunction = (FlatformFunctionDetail)((JList)event.getSource()).getSelectedValue();
      if(this.this$0.mCurrentFunction != null) {
         StringBuffer buffer = new StringBuffer();
         buffer.append("<html>");
         buffer.append(this.this$0.mCurrentFunction.getName() == null?"":this.this$0.mCurrentFunction.getName());
         buffer.append("(");
         buffer.append(this.this$0.mCurrentFunction.getParameter() == null?"":this.this$0.mCurrentFunction.getParameter());
         buffer.append(")");
         buffer.append("</html>");
         this.this$0.mFunctionNameLabel.setText(buffer.toString());
         this.this$0.mFunctionDescLabel.setText("<html>" + this.this$0.mCurrentFunction.getDetailedDesc() + "</html>");
      }

   }

   // $FF: synthetic method
   XcellFormFunctionDialog$FunctionListSelection(XcellFormFunctionDialog x0, XcellFormFunctionDialog$1 x1) {
      this(x0);
   }
}

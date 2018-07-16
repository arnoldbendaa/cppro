package com.cedar.cp.impl.xmlform.convert;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.convert.ExcelIOEditor;
import com.cedar.cp.api.xmlform.convert.ExcelIOEditorSession;
import com.cedar.cp.ejb.api.xmlform.convert.ExcelIOEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class ExcelIOEditorSessionImpl extends BusinessSessionImpl implements ExcelIOEditorSession {

   protected ExcelIOEditorImpl mClientEditor;


   public ExcelIOEditorSessionImpl(ExcelIOProcessImpl process, Object key) throws ValidationException {
      super(process);
   }

   protected ExcelIOEditorSessionServer getSessionServer() throws CPException {
      return new ExcelIOEditorSessionServer(this.getConnection());
   }

   public void terminate() {}
   
//   public EntityList getAvailableModels() {
//		try {
//			return this.getSessionServer().getAvailableModels();
//		} catch (Exception e) {
//			throw new RuntimeException("unexpected exception", e);
//		}
//	}

	@Override
	public Object getPrimaryKey() {
		return null;
	}

	@Override
	public ExcelIOEditor getExcelIOEditor() {
		return null;
	}

	@Override
	protected Object persistModifications(boolean var1) throws ValidationException, CPException {
		return null;
	}
}

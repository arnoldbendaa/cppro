package com.cedar.cp.ejb.base.async.xcellform;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.api.xmlform.convert.ExcelIOProcess;
import com.cedar.cp.dto.xmlform.convert.ConvertXlsToJsonTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.util.Log;
import java.util.Arrays;
import java.util.List;
import javax.naming.InitialContext;

public class ConvertXlsToJsonTask extends AbstractTask {

	private static final long serialVersionUID = 5158019142247601071L;
	private transient InitialContext mInitialContext;
	private transient Log mLog;

	public ConvertXlsToJsonTask() {
		mLog = new Log(getClass());
	}

	public int getReportType() {
		return 8;
	}

	public ConvertCheckpoint getCheckpoint() {
		return (ConvertCheckpoint) super.getCheckpoint();
	}

	public String getEntityName() {
		return "ConvertXlsToJsonTask";
	}

	public void runUnitOfWork(InitialContext initialContext) throws Exception {
		this.mInitialContext = initialContext;

		if (!(this.getRequest() instanceof ConvertXlsToJsonTaskRequest)) {
			this.logInfo("Wrong type of request");
			return;
		}

		mLog.debug("runUnitOfWork", "Starting ConvertXlsToJsonTask");
		setCheckpoint(new ConvertCheckpoint());
		
		// Initialize
		XmlFormsProcess xmlFormProcess = getCPConnection().getXmlFormsProcess();
		EntityList xcellForms = getCPConnection().getXmlFormsProcess().getAllXcellXmlForms();
		ExcelIOProcess converter = getCPConnection().getExcelIOProcess();
		int j = 0;
		
		// Xcell Forms
		for (Object form : xcellForms.getValues("XmlForm")) {
		    j++;
            this.log("----- " + j + "/" + xcellForms.getNumRows() + " (" + (j * 100)/xcellForms.getNumRows() + "%) ----- Xcell Form: " + ((XmlFormRef) form).getNarrative());
            System.out.println("----- " + j + "/" + xcellForms.getNumRows() + " (" + (j * 100)/xcellForms.getNumRows() + "%) ----- Xcell Form: " + ((XmlFormRef) form).getNarrative());
            
		    if (((XmlFormRef) form).getNarrative().equals("5 - Report 7.21") || ((XmlFormRef) form).getNarrative().equals("5 - Report 7.25")) {
		        System.out.println("PRZESKOCZ formularz "+ ((XmlFormRef) form).getNarrative());
                continue;
            }
			// download data for xcellFrom
			Object[] excelFile = xmlFormProcess.getExcelFile(((XmlFormRef) form).getPrimaryKey());
			// convert & save
			boolean saved = xmlFormProcess.saveJsonForm(((XmlFormRef) form).getPrimaryKey(), converter.convertXlsToJson((byte[]) excelFile[0], ""), (Integer) excelFile[1], getCPConnection().getUserContext().getUserId());
            if (!saved) {
                throw new Exception("Could not save JSON form.");
            }
			
		}

		mLog.debug("runUnitOfWork", "Ending  task");
		setCheckpoint(null);
	}
	
	public InitialContext getInitialContext() {
		return this.mInitialContext;
	}

	public boolean mustComplete() {
		return false;
	}

	static class ConvertCheckpoint extends AbstractTaskCheckpoint {
		public List toDisplay() {
			return Arrays.asList(new String[] { "ConvertXlsToJsonTask Checkpoint number:" + getCheckpointNumber() });
		}
	}
}
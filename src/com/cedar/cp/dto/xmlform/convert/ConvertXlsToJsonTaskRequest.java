package com.cedar.cp.dto.xmlform.convert;


import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ConvertXlsToJsonTaskRequest extends AbstractTaskRequest implements TaskRequest {

	private static final long serialVersionUID = 2380417003699059029L;
	private List<String> mMessages;


	public ConvertXlsToJsonTaskRequest() {
		this.mMessages = new ArrayList<String>();
		this.mMessages.add("Processing ConvertXlsToJsonTask...");
	}

	public List<String> toDisplay() {
		return this.mMessages;
	}

	public String getService() {
		return "com.cedar.cp.ejb.base.async.xcellform.ConvertXlsToJsonTask";
	}
}

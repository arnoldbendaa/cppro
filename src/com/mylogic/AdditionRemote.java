package com.mylogic;

import javax.ejb.Remote;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;

public interface AdditionRemote {
	public int add(int a , int b);
	public ModelEVO getDetails(String dependants) throws ValidationException;
	public ModelDAO getDAO();
}

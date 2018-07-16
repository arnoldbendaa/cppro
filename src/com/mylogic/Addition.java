package com.mylogic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Timer;

/**
 * Session Bean implementation class Addition
 */
public class Addition implements AdditionRemote {

    /**
     * Default constructor. 
     */
	   public ModelDAO mModelDAO;

    public Addition() {
        // TODO Auto-generated constructor stub
    }
    public int add(int a,int b){
    	int r=a+b;
    	return r;
    }
    public ModelEVO getDetails(String dependants) throws ValidationException {

        ModelEVO var3;
        try {
           var3 = this.getDAO().getDetails(dependants);
        } finally {

        }
        return var3;
     }
    public ModelDAO getDAO() {
        if(this.mModelDAO == null) {
           this.mModelDAO = new ModelDAO();
        }

        return this.mModelDAO;
     }

}

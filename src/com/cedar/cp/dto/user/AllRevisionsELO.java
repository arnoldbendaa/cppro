package com.cedar.cp.dto.user;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.dto.base.AbstractELO;

public class AllRevisionsELO extends AbstractELO implements Serializable {
    
    public AllRevisionsELO() {
        super(new String[]{"id", "revision", "version_date", "description"});
     }
    
    public void add(Integer id, Integer revision, Date versionDate, String description) {
        ArrayList l = new ArrayList();
        l.add(id);
        l.add(revision);
        l.add(versionDate);
        l.add(description);
        this.mCollection.add(l);
     }
    
    public void next() {
        if(this.mIterator == null) {
           this.reset();
        }

        ++this.mCurrRowIndex;
        List l = (List)this.mIterator.next();
        byte index = 0;
        int var4 = index + 1;
     }

}

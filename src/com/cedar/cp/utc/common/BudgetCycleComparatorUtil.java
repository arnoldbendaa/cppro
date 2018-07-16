package com.cedar.cp.utc.common;

import java.util.Comparator;

import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;

public class BudgetCycleComparatorUtil {
    
    public static Comparator<BudgetCycleDTO> BudgetCategoryComparator = new Comparator<BudgetCycleDTO>() {

        public int compare(BudgetCycleDTO b1, BudgetCycleDTO b2) {
           String bcc1 = b1.getCategory().toUpperCase();
           String bcc2 = b2.getCategory().toUpperCase();
           //ascending order
           return bcc1.compareTo(bcc2);
           //descending order
           //return bbc2.compareTo(bcc1);
        }};
}

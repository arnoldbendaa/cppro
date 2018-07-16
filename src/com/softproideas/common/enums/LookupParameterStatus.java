package com.softproideas.common.enums;

public enum LookupParameterStatus {
    //DELETED(2), ACTIVE(1), SUSPENDED(0);
    DELETED(10), SUSPENDED(20), ACTIVE(30);
    
    
    private int value;

    LookupParameterStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LookupParameterStatus valueOf(int value) {
        LookupParameterStatus foundedStatus = LookupParameterStatus.SUSPENDED;
        for (LookupParameterStatus status: LookupParameterStatus.values()) {
            if (status.getValue() == value) {
                foundedStatus = status;
                break;
            }
        }
        return foundedStatus;
    }

}

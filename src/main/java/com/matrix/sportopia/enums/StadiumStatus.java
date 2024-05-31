package com.matrix.sportopia.enums;

public enum StadiumStatus {
    OPEN(1L),
    TEMPORARILY_CLOSED(0L),
    CLOSED(-1L);

    private final Long value;

    StadiumStatus(Long value){
        this.value = value;
    }

    public Long getValue(){
        return value;
    }

    public static StadiumStatus fromValue(int value){
        for (StadiumStatus status:StadiumStatus.values()){
            if (status.value == value){
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

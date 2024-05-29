package com.matrix.sportopia.enums;

import com.matrix.sportopia.entities.User;
import lombok.Data;

public enum UserStatus {
    ACTIVE(1L),
    INACTIVE(0L),
    DELETED(-1L);

    private final Long value;

    UserStatus(Long value){
        this.value=value;
    }

    public Long getValue(){
        return value;
    }

    public static UserStatus fromValue(int value){
        for (UserStatus status:UserStatus.values()){
            if (status.value==value){
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

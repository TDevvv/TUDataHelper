package com.thundergod.data_helper.file_system;

import com.thundergod.data_helper.file_system.annotations.dtype.DTypeField;
import com.thundergod.data_helper.file_system.annotations.dtype.DTypeHandler;

public abstract class TU {
    public DTypeField handle(Class C){
       DTypeField FIELD =  DTypeHandler.handle(C);
       return FIELD;
    }
}

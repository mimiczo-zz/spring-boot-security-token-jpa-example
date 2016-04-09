package com.mimiczo.support.utils;

import com.mimiczo.configuration.exception.GlobalError;
import com.mimiczo.configuration.exception.GlobalException;

/**
 * Created by mimiczo on 15. 12. 13..
 */
public abstract class GlobalAssert {

    public static void Null(Object object) {
        notNull(object, GlobalError.DEF_E900);
    }
    public static void Null(Object object, Enum<?> code) {
        if (object != null) {
            throw new GlobalException(code);
        }
    }
    public static void notNull(Object object) {
        notNull(object, GlobalError.DEF_E900);
    }
    public static void notNull(Object object, Enum<?> code) {
        if (object == null) {
            throw new GlobalException(code);
        }
    }
}

package cn.v1.kanglewanjia.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * gson 序列化相关
 */
public class ExclusionField implements ExclusionStrategy{

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(NotSerialized.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}

package com.backend.ecommerce.utility;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class Utility {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> propertyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(descriptor.getName()) == null) {
                propertyNames.add(descriptor.getName());
            }
        }
        return propertyNames.toArray(new String[propertyNames.size()]);
    }
}

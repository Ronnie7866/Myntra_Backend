package com.backend.ecommerce.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "productId";
    public static final String SORT_ORDER = "asc";
    public static final String[] EXCEPTION_APIS = {"/api/auth/**","/api/users/**","/api/products/**","/api/inventory/**","/forgotPassword/**","/v2/api-docs/","/swagger-resources/**","/swagger-ui/**","/webjars/**","/api/category"};

}

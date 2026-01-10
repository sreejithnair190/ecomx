package me.sreejithnair.ecomx_api.common.constant;

import java.util.Set;

public final class SortableFields {

    private SortableFields() {}

    public static final Set<String> BRAND = Set.of("id", "name", "slug", "isActive", "createdAt");
    public static final Set<String> PRODUCT = Set.of("id", "name", "slug", "price", "isActive", "createdAt");
    public static final Set<String> CATEGORY = Set.of("id", "name", "slug", "isActive", "createdAt");
    public static final Set<String> USER = Set.of("id", "firstName", "lastName", "email", "status", "createdAt");
    public static final Set<String> ORDER = Set.of("id", "status", "totalAmount", "createdAt");
}

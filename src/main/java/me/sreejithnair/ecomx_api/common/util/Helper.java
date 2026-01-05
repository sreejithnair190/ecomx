package me.sreejithnair.ecomx_api.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Helper {

    /**
     * Creates a Pageable with validated sort fields for JPQL queries (uses entity field names).
     */
    public static Pageable createPageable(int page, int perPage, String sortBy, String sortDir, Set<String> allowedFields) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        String validatedSortBy = (sortBy != null && allowedFields.contains(sortBy)) ? sortBy : "createdAt";

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(direction, validatedSortBy));

        if (!"createdAt".equals(validatedSortBy)) {
            orders.add(new Sort.Order(Sort.Direction.DESC, "createdAt"));
        }

        return PageRequest.of(page, perPage, Sort.by(orders));
    }
}

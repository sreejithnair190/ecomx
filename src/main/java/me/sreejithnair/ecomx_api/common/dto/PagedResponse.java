package me.sreejithnair.ecomx_api.common.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PagedResponse<T> from(Page<T> page) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setItems(page.getContent());
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }
}

package com.kevin.emazon_cart.application.util;

import com.kevin.emazon_cart.domain.model.ItemCartResponse;
import org.springframework.data.domain.*;

import java.util.Comparator;
import java.util.List;
public class ListToPageConverter {

    public static final String SORT_BY = "itemName";
    public static final String SORTING_METHOD = "desc";
    private ListToPageConverter(){}
    public static Page<ItemCartResponse> convertListIntoPage(List<ItemCartResponse> domainModelList, Integer pageNumber, Integer pageSize, String orderingMethod) {
        sortPage(domainModelList, orderingMethod);
        return new PageImpl<>(domainModelList, createPageable(orderingMethod,pageNumber,pageSize), domainModelList.size());
    }

    public static Pageable createPageable(String order, Integer pageNumber, Integer pageSize){
        Sort sort = Sort.by(SORT_BY).ascending();
        if (SORTING_METHOD.equalsIgnoreCase(order)) {
            sort = sort.descending();
        }
        return PageRequest.of(pageNumber, pageSize, sort);
        }


    private static Comparator<ItemCartResponse> getComparator(String order) {
        if (SORTING_METHOD.equalsIgnoreCase(order)) {
            return Comparator.comparing(ItemCartResponse::getItemName).reversed();
        } else {
            return Comparator.comparing(ItemCartResponse::getItemName);
        }
    }

    private static void sortPage(List<ItemCartResponse> domainModelList, String orderingMethod) {
        domainModelList.sort(getComparator(orderingMethod));
    }
}
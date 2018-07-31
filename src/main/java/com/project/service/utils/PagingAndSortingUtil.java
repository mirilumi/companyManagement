package com.project.service.utils;

import com.project.config.ApplicationProperties;
import com.project.dtos.StandardQueryParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagingAndSortingUtil {


    @Autowired
    public void setAppProps(ApplicationProperties appProps) {
        this.appProps = appProps;
    }

    private ApplicationProperties appProps;


    public PageRequest getPageRequest(StandardQueryParams queryParams) {
        Optional<Sort> sort = getOrderList(queryParams);
        int pageNumber = getPageNumber(queryParams);
        int pageSize = getPageSize(queryParams);
        if (sort.isPresent()) {
            return new PageRequest(pageNumber, pageSize, sort.get());
        } else {
            return new PageRequest(pageNumber, pageSize);
        }
    }

    private int getPageSize(StandardQueryParams queryParams) {
        return queryParams.getPageSize();
    }

    private int getPageNumber(StandardQueryParams queryParams) {
//        return (queryParams.getFromRecords() - 1) > -1 ? queryParams.getFromRecords() - 1 : 0;
        return queryParams.getFromRecords()/queryParams.getPageSize();
    }

    private Optional<Sort> getOrderList(StandardQueryParams queryParams) {
        if (queryParams != null && queryParams.getSort() != null && queryParams.getSort().trim().length() != 0) {
            String raw = queryParams.getSort().trim();
            List<Sort.Order> orders = Arrays.asList(raw.split(",")).stream()
                    .map(s -> getOrder(s))
                    .flatMap(o -> flatten(o)).collect(Collectors.toList());
            if (orders.size() > 0) {
                return Optional.of(new Sort(orders));
            }
        }
        return Optional.empty();
    }

    private static Stream<Sort.Order> flatten(Optional<Sort.Order> o) {
        return o.isPresent() ? Stream.of(o.get()) : Stream.empty();
    }

    private static Optional<Sort.Order> getOrder(String property) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (property.startsWith("-")) {
            property = property.substring(1);
            direction = Sort.Direction.DESC;
        }
        if (property == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Sort.Order(direction, property));
        }
    }

    public String getSortAsString(PageRequest pageRequest) {
        return pageRequest.getSort() != null ? pageRequest.getSort().toString() : "NONE";
    }
}

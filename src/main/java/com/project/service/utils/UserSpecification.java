package com.project.service.utils;

import com.project.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class UserSpecification implements Specification<User> {
    private SearchCriteria criteria;

    public UserSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) return builder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString());
        else if (criteria.getOperation().equalsIgnoreCase("<")) return builder.lessThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString());
        else if (criteria.getOperation().equalsIgnoreCase(":"))
            if (root.get(criteria.getKey()).getJavaType() == String.class) return builder.like(
                    root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        return null;
    }
}

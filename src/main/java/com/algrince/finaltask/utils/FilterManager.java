package com.algrince.finaltask.utils;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterManager {

    private final EntityManager entityManager;

    public void enableDeletedFilter(String filterToEnable) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(filterToEnable);
        filter.setParameter("isDeleted", false);
    }

    public void disableFilter(String filterToDisable) {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter(filterToDisable);
    }
}

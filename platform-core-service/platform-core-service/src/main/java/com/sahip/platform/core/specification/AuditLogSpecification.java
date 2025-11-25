package com.sahip.platform.core.specification;

import com.sahip.platform.core.entity.AuditLog;
import org.springframework.data.jpa.domain.Specification;

public class AuditLogSpecification {

    public static Specification<AuditLog> usernameContains(String username) {
        return (root, query, cb) ->
                (username == null || username.trim().isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<AuditLog> actionEquals(String action) {
        return (root, query, cb) ->
                (action == null || action.trim().isEmpty())
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get("action")), action.toLowerCase());
    }

    public static Specification<AuditLog> resourceContains(String resource) {
        return (root, query, cb) ->
                (resource == null || resource.trim().isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("resource")), "%" + resource.toLowerCase() + "%");
    }
}


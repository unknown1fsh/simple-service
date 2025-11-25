package com.sahip.platform.core.service.impl;

import com.sahip.platform.core.dto.AuditLogDTO;
import com.sahip.platform.core.entity.AuditLog;
import com.sahip.platform.core.mapper.AuditLogMapper;
import com.sahip.platform.core.repository.AuditLogRepository;
import com.sahip.platform.core.service.AuditLogService;
import com.sahip.platform.core.specification.AuditLogSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> list(String username, String action, String resource, Pageable pageable) {
        Specification<AuditLog> spec = Specification
                .where(AuditLogSpecification.usernameContains(username))
                .and(AuditLogSpecification.actionEquals(action))
                .and(AuditLogSpecification.resourceContains(resource));

        return auditLogRepository.findAll(spec, pageable)
                .map(auditLogMapper::toDto);
    }

    @Override
    @Transactional
    public void log(String username, String action, String resource, String details, String ipAddress) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setResource(resource);
        log.setDetails(details);
        log.setIpAddress(ipAddress);
        auditLogRepository.save(log);
    }
}



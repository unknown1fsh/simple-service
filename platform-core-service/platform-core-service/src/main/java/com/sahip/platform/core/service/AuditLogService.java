package com.sahip.platform.core.service;

import com.sahip.platform.core.dto.AuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {

    Page<AuditLogDTO> list(String username, String action, String resource, Pageable pageable);

    void log(String username, String action, String resource, String details, String ipAddress);
}



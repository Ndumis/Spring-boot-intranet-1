package com.intranet.springboot.repository;

import com.intranet.springboot.security.domain.ActionLock;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActionLockRepository  extends PagingAndSortingRepository<ActionLock, String> {

    List<ActionLock> findByIdNumberAndType(String idNumber, ActionLock.Type type);
}

package com.intranet.springboot.repository;

import com.intranet.springboot.security.domain.LoginAttempt;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LoginAttemptRepository extends PagingAndSortingRepository<LoginAttempt, String> {
    Optional<LoginAttempt> findFirstByUsernameOrderByCreatedDesc(String username);
}

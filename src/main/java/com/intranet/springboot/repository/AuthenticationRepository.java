package com.intranet.springboot.repository;

import com.intranet.springboot.security.AuthToken;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AuthenticationRepository  extends PagingAndSortingRepository<AuthToken, String> {
    @Query("{\"originIp\": {$regex : ?0, $options: 'i'}}")
    Page<AuthToken> search(String keyword, Pageable pageable);
    @Query("{'$and':[{\"originIp\": {$regex : ?0, $options: 'i'}},{\"user.$id\": ?1 }]}")
    Page<AuthToken> search(String keyword, ObjectId userId, Pageable pageable);
    @Query("{\"user.$id\": ?0 }")
    List<AuthToken> findByUserId(ObjectId objectId);
}

package com.bripay.accountservice.repository;

import com.bripay.commonsservice.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByNumberAccount(String numberAccount);
    List<AccountEntity> findAllByUsername(String username);
    boolean existsByNumberAccountAndIdNot(String numberAccount, Long id);
}

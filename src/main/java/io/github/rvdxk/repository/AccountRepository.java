package io.github.rvdxk.repository;

import io.github.rvdxk.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

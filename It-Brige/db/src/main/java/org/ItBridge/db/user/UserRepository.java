package org.ItBridge.db.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findFirstByIdOrderByIdDesc(Long id);

    Optional<UserEntity> findFirstByEmailAndPasswordOrderByIdDesc(
            String email,
            String password);
}



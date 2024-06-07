package com.example.demo.registration.token;

import com.example.demo.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
//    Optional<ConfirmationToken> findAllByAppUser(AppUser appUser);
//    @Query(value="select c from ConfirmationToken c where ConfirmationToken.userId.id=?1")
//    Optional<ConfirmationToken> findByUserIdd(Long id);

    @Query(value="select c from ConfirmationToken c where c.isValid = true and c.appUser.id=?1")
    Optional<ConfirmationToken> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.isValid = ?2 WHERE c.token = ?1")
    int updateIsValid(String token, Boolean isValid);
}

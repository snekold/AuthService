package org.example.authservice.repo;

import org.example.authservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("""
           SELECT t from Token t 
           inner join User u on t.user_id = u.id 
           where u.id = :user_id and (t.expired == false or t.revoked == false)
            """)
    List<Token> findAllValidTokensByUserId(Long user_id);
}

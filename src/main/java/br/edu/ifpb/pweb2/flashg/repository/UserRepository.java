package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}

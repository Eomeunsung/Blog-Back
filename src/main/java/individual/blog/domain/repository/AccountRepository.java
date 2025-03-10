package individual.blog.domain.repository;

import individual.blog.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    List<Account> findByEmailContainingOrNameContaining(String search, String search2);

    List<Account> findAllBy();


}

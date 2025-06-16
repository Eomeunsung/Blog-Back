package individual.blog.domain.repository;

import individual.blog.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Set<Role> findByRoleNameIn(List<String> role);

    List<Role> findAllBy();

    Role findByRoleName(String role);
}

package individual.blog.domain.repository;

import individual.blog.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Set<Role> findByRoleNameIn(List<String> role);
}

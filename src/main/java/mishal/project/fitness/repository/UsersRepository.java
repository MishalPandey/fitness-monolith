package mishal.project.fitness.repository;

import mishal.project.fitness.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
                                            // JpaRepository<T, ID> where T is the entity type and
                                            // ID is the type of the entity's identifier(TYPE OF PRIMARY KEY)
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByEmail(String email);
}

package mishal.project.fitness.repository;

import mishal.project.fitness.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByUsersId(String usersId); //find means select query, By means where clause
                                                // samajh jaayega jpa/hibernate aur U capital I capital format matlb Userid
                                                // query generate kardeta toh specific format hai yeh bhi likhne ka
}

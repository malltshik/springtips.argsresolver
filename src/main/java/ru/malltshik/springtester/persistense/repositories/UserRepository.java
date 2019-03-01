package ru.malltshik.springtester.persistense.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malltshik.springtester.persistense.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

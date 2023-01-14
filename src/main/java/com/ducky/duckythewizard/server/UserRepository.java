package com.ducky.duckythewizard.server;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
    boolean existsUserByName(String name);
    boolean existsById(int id);
    User getUserByName(String name);
}

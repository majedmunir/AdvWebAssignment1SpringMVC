package com.demo.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstnameAndPassword(String accountname, String password);


    @Query("SELECT u FROM User u WHERE u.firstname=?1 and u.password=?2")
    List<User> checkUserInput(String accountname, String Password);

    @Query("SELECT u FROM User u WHERE u.firstname LIKE %?1% and u.lastname LIKE %?2%")
    List<User>searchUsers(String firstname, String lastname);
}

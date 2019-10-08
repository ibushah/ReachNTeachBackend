package com.example.hubProject.Repository;

import com.example.hubProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String> {



    User findByEmail(String username);



    @Query(value="select * from User where email=:email",nativeQuery = true)
    User findUserByActive(@Param("email") String email);


    @Query(value = "Select * from User where email != :email and active=true",nativeQuery = true)
    List<User> findAllExceptCurrentUser(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "delete from shared_project where user_id = :email",nativeQuery = true)
    void deleteSharedProjectByEmail(@Param("email") String email);


    @Modifying
    @Transactional
    @Query(value = "delete from user_dataset where user_id = :email",nativeQuery = true)
    void deleteSharedDataSetByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "delete from dataSet where creator = :email",nativeQuery = true)
    void deleteDataSetByEmail(@Param("email") String email);


    void deleteByEmail(String email);
}

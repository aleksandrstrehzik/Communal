package org.example.dao;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String UserName);

    @Modifying
    @Query(value = "DELETE FROM user_role where user_id =:id", nativeQuery = true)
    void deleteUserRole(Integer id);

    void deleteUserByUserName(String name);
}

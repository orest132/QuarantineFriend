package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;

@Repository
@Transactional
@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByUsername(String username);
    UserEntity findUserByEmail(String email);
    void deleteById(Long id);

    @Modifying
    @Query(value = "delete from team_members t where t.user_id=:id", nativeQuery = true)
    void deleteUserFromTeamMembers(Long id);

    @Modifying
    @Query(value = "delete from user_board b where b.user_id=:id", nativeQuery = true)
    void deleteUserFromBoard(Long id);
}

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
    @Query(value = "delete from user_hobbies uh where uh.user_entity_id=:id", nativeQuery = true)
    void deleteUserAssociatedHobbies(Long id);

    @Modifying
    @Query(value = "delete from request r where r.from_user_id=:id or r.to_user_id=:id", nativeQuery = true)
    void deleteUserAssociatedRequests(Long id);

    @Modifying
    @Query(value = "delete from chat c where c.user1_id=:id or c.user2_id=:id", nativeQuery = true)
    void deleteUserAssociatedChats(Long id);

    @Modifying
    @Query(value = "delete from message m where m.user_id=:id", nativeQuery = true)
    void deleteUserAssociatedMessages(Long id);

    @Modifying
    @Query(value = "delete from report r where r.reportee_id=:id", nativeQuery = true)
    void deleteAsocciatedReports(Long id);

    @Modifying
    @Query(value = "delete from chat_messages m where m.messages_id=any(select id from message mm where mm.user_id=:id)", nativeQuery = true)
    void deleteFromMessageChat(Long id);



    @Modifying
    @Query(value = "update chat c set c.user1_id=null where c.user1_id=:id", nativeQuery = true)
    void deleteUserFromChats(Long id);

    @Modifying
    @Query(value = "update chat c set c.user2_id=null where c.user2_id=:id", nativeQuery = true)
    void deleteUserFromChats1(Long id);

    @Modifying
    @Query(value = "update message m set m.user_id=null where m.user_id=:id", nativeQuery = true)
    void deleteUserFromMessages(Long id);

    @Modifying
    @Query(value = "delete from user_board b where b.user_id=:id", nativeQuery = true)
    void deleteUserFromBoard(Long id);
}

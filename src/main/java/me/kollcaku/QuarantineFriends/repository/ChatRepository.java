package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.ChatEntity;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@CrossOrigin("http://localhost:4200")
public interface ChatRepository extends JpaRepository<ChatEntity,Long> {

    @Query(value = "select * from chat c where c.user1_id=:id and c.user2_id=:idd", nativeQuery = true)
    ChatEntity getChatByUsersPresent(Long id, Long idd);

    @Query(value = "select * from chat c where c.user1_id=:id or c.user2_id=:id", nativeQuery = true)
    List<ChatEntity> getChatByUserId(Long id);
}

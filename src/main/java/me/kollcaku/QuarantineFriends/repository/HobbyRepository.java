package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.HobbyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HobbyRepository extends JpaRepository<HobbyEntity,Long> {
    @Query(value = "select * from hobbie h where h.hobby=:s", nativeQuery = true)
    HobbyEntity getHobbieByString(String s);
}

package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity,Long> {
}

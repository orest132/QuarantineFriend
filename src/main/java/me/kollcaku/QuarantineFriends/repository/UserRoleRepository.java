package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}

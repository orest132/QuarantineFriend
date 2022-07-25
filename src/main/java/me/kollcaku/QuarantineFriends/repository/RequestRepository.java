package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;

@Repository
@Transactional
@CrossOrigin("http://localhost:4200")
public interface RequestRepository extends JpaRepository<RequestEntity,Long> {

}

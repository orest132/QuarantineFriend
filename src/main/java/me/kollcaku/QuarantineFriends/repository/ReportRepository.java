package me.kollcaku.QuarantineFriends.repository;

import me.kollcaku.QuarantineFriends.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity,Long> {
}

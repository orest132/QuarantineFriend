package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.ReportDTO;
import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.entity.ReportEntity;
import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import me.kollcaku.QuarantineFriends.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void addReport(ReportDTO report) {
        ReportEntity reportEntity = mapToEntity(report);
        this.reportRepository.save(reportEntity);
    }

    public static ReportDTO mapToDto(ReportEntity reportEntity){

        ReportDTO reportDTO = new ReportDTO();

        if (reportEntity != null){
            reportDTO.setId(reportEntity.getId());
            reportDTO.setReporter(UserService.mapToDto(reportEntity.getReporter()));
            reportDTO.setReportee(UserService.mapToDto(reportEntity.getReportee()));
            reportDTO.setReport(reportEntity.getReport());
        }
        return reportDTO;
    }

    public static ReportEntity mapToEntity(ReportDTO reportDTO){
        ReportEntity reportEntity = new ReportEntity();
        if (reportDTO != null){
            reportEntity.setId(reportDTO.getId());
            reportEntity.setReporter(UserService.mapToEntity(reportDTO.getReporter()));
            reportEntity.setReportee(UserService.mapToEntity(reportDTO.getReportee()));
            reportEntity.setReport(reportDTO.getReport());
        }
        return reportEntity;
    }

    public List<ReportDTO> getReports() {
        return this.reportRepository.findAll().stream().map(ReportService::mapToDto).collect(Collectors.toList());
    }
}

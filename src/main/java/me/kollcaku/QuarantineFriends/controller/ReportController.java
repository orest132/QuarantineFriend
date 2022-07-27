package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.dto.ReportDTO;
import me.kollcaku.QuarantineFriends.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(value = {REQUEST_MAPPING})
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class ReportController {
    ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/report")
    public void addReport(@RequestBody ReportDTO report){
        this.reportService.addReport(report);
    }

    @GetMapping("/reports")
    public List<ReportDTO> getReports(){
        return this.reportService.getReports();
    }

    @DeleteMapping("/report/delete/{reportId}")
    public void deleteReport(@PathVariable("reportId") Long id){
        this.reportService.deleteReport(id);
    }
}

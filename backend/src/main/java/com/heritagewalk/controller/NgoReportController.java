package com.heritagewalk.controller;

import com.heritagewalk.dto.NgoReportSummaryDTO;
import com.heritagewalk.service.NgoReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/reports")
public class NgoReportController {

    private final NgoReportService reportService;

    @Autowired
    public NgoReportController(NgoReportService reportService) {
        this.reportService = reportService;
    }

    // 1. JSON Report Summary Preview
    @GetMapping("/summary")
    public ResponseEntity<NgoReportSummaryDTO> getReportSummary(
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String organizationName) {
        NgoReportSummaryDTO summary = reportService.getReportSummary(siteId, startDate, endDate, category, minRating, organizationName);
        return ResponseEntity.ok(summary);
    }

    // 2. Downloadable PDF Report
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdfReport(
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String organizationName,
            @RequestParam(defaultValue = "true") boolean includeReviews) {

        byte[] pdfBytes = reportService.generatePdfReport(siteId, startDate, endDate, category, minRating, organizationName, includeReviews);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "heritage_ngo_report_" + LocalDate.now() + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    // 3. Downloadable Excel Report
    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcelReport(
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String organizationName) {

        byte[] excelBytes = reportService.generateExcelReport(siteId, startDate, endDate, category, minRating, organizationName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "heritage_ngo_report_" + LocalDate.now() + ".xlsx");
        headers.setContentLength(excelBytes.length);

        return ResponseEntity.ok().headers(headers).body(excelBytes);
    }
}

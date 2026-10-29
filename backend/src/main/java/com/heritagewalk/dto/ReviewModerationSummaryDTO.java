package com.heritagewalk.dto;

public class ReviewModerationSummaryDTO {
    private long pendingCount;
    private long approvedCount;
    private long rejectedCount;
    private long totalCount;

    public ReviewModerationSummaryDTO() {
    }

    public ReviewModerationSummaryDTO(long pendingCount, long approvedCount, long rejectedCount, long totalCount) {
        this.pendingCount = pendingCount;
        this.approvedCount = approvedCount;
        this.rejectedCount = rejectedCount;
        this.totalCount = totalCount;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public long getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(long approvedCount) {
        this.approvedCount = approvedCount;
    }

    public long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}

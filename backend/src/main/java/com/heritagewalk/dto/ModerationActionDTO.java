package com.heritagewalk.dto;

public class ModerationActionDTO {
    private String reason;
    private String moderator;

    public ModerationActionDTO() {
    }

    public ModerationActionDTO(String reason, String moderator) {
        this.reason = reason;
        this.moderator = moderator;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }
}

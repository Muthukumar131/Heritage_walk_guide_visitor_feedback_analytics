package com.heritagewalk.dto;

import jakarta.validation.constraints.NotNull;

public class RouteStopDTO {

    @NotNull(message = "Heritage Place ID is required")
    private Long heritagePlaceId;

    @NotNull(message = "Sequence order is required")
    private Integer sequenceOrder;

    private String stopNotes;

    public RouteStopDTO() {
    }

    public RouteStopDTO(Long heritagePlaceId, Integer sequenceOrder, String stopNotes) {
        this.heritagePlaceId = heritagePlaceId;
        this.sequenceOrder = sequenceOrder;
        this.stopNotes = stopNotes;
    }

    public Long getHeritagePlaceId() {
        return heritagePlaceId;
    }

    public void setHeritagePlaceId(Long heritagePlaceId) {
        this.heritagePlaceId = heritagePlaceId;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public String getStopNotes() {
        return stopNotes;
    }

    public void setStopNotes(String stopNotes) {
        this.stopNotes = stopNotes;
    }
}

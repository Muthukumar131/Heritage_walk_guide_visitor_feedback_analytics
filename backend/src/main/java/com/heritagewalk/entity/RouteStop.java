package com.heritagewalk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "route_stops", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"route_id", "sequence_order"})
})
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonBackReference
    private WalkingRoute route;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heritage_place_id", nullable = false)
    private HeritagePlace heritagePlace;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;

    @Column(name = "stop_notes")
    private String stopNotes;

    public RouteStop() {
    }

    public RouteStop(Long id, WalkingRoute route, HeritagePlace heritagePlace, Integer sequenceOrder, String stopNotes) {
        this.id = id;
        this.route = route;
        this.heritagePlace = heritagePlace;
        this.sequenceOrder = sequenceOrder;
        this.stopNotes = stopNotes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalkingRoute getRoute() {
        return route;
    }

    public void setRoute(WalkingRoute route) {
        this.route = route;
    }

    public HeritagePlace getHeritagePlace() {
        return heritagePlace;
    }

    public void setHeritagePlace(HeritagePlace heritagePlace) {
        this.heritagePlace = heritagePlace;
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

package com.rural.sports.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    private String name;
    
    private String description;
    
    @JsonProperty("rule")
    private String rules;
    
    @JsonProperty("time")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;
    
    private Date endTime;
    private String location;
    private String status; // UPCOMING, ONGOING, FINISHED
    private String theme;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
    
    @JsonProperty("organizerName")
    public String getOrganizerName() {
        return organizer != null ? organizer.getRealName() : null;
    }
    
    @JsonProperty("organizerId")
    public Long getOrganizerId() {
        return organizer != null ? organizer.getId() : null;
    }

    @JsonProperty("organizerId")
    public void setOrganizerId(Long organizerId) {
        if (organizerId != null) {
            User u = new User();
            u.setId(organizerId);
            this.organizer = u;
        }
    }

    @JsonProperty("imgUrl")
    private String imgUrl;
}

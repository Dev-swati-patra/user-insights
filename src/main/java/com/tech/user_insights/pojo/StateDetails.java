package com.tech.user_insights.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "state_details")
public class StateDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateId;

    @Column(name = "state_name")
    private String stateName;  
    
    @Column(name = "state_code")
    private Integer stateCode;  

    @Column(name = "state_short_name")
    private String stateShortName;

}

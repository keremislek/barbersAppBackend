package com.example.barbersApp.entities;


import java.util.List;

import com.example.barbersApp.entities.AdressesInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="barbers")
@EqualsAndHashCode(callSuper = false)
public class Barber extends BaseUserEntity { //Hareket
	 
	private String address;
	private String barberName;
    private String photoUrl;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "barber_services",
               joinColumns = @JoinColumn(name = "barber_id",referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
	List<Services> services;

	@OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
	private List<ServicesInfo> servicesInfo;

	@OneToOne(mappedBy = "barber", cascade = CascadeType.ALL)
    private AdressesInfo addressesInfo;
}	

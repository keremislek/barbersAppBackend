package com.example.barbersApp.entities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@Table(name="services")
@AllArgsConstructor
@NoArgsConstructor
public class Services {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "barber_services",
               joinColumns = @JoinColumn(name = "service_id",referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "barber_id", referencedColumnName = "id"))
    private List<Barber> barber;

    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
	private List<ServicesInfo> servicesInfo;
}

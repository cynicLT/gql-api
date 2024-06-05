package org.cynic.gql_api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "\"ORDER\"")
@DynamicInsert
@DynamicUpdate
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order")
    @SequenceGenerator(name = "order", sequenceName = "ORDER_SEQ", allocationSize = 0)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE_TIME")
    private OffsetDateTime dateTime;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ORDER_ITEM",
        joinColumns = @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID")
    )
    private Set<Item> items;
}

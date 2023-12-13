package com.study.financial.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity(

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "icon")
    var icon: String? = null,

    ) {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID()
}

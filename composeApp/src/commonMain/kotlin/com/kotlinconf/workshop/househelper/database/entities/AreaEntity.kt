package com.kotlinconf.workshop.househelper.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlinconf.workshop.househelper.Area


@Entity(tableName = "areas")
data class AreaEntity(
    @PrimaryKey
    val id: String,
    val name: String
) {
    fun toArea(): Area = Area(
        id = id,
        name = name
    )

    companion object Companion {
        fun fromArea(area: Area): AreaEntity = AreaEntity(
            id = area.id,
            name = area.name
        )
    }
}

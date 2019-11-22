package com.davidmag.bitcointracker.data.source.common

abstract class EntityDtoMapper<Entity, Dto> {
    abstract val toDtoMapper : (Entity) -> Dto
    abstract val toEntityMapper : (Dto) -> Entity

    fun toEntity(data : Dto) : Entity {
        return toEntityMapper(data)
    }

    fun toDto(data : Entity) : Dto {
        return toDtoMapper(data)
    }

    fun toEntity(data : List<Dto>) : List<Entity> {
        return data.map { toEntityMapper(it) }
    }

    fun toDto(data : List<Entity>) : List<Dto> {
        return data.map { toDtoMapper(it) }
    }
}
package com.claraberriel.data.mapper

interface BaseMapperRepository<E, D> {

    fun transform(type: E): D

}
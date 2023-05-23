package com.davidvelez.petday.Model

data class Service(
    var id: String? = null,
    var urlImage: String? = null,
    var isCatSelected: Boolean = false,
    var isDogSelected: Boolean = false,
    var isPaseoSelected: Boolean = false,
    var isCuidarSelected: Boolean = false,
    var isBanharSelected: Boolean = false,
    var description: String? = null,
    var cost: Float? = null
)

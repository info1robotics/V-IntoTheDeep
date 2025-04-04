package org.firstinspires.ftc.teamcode.enums

enum class AutoStartPos {
    UNKNOWN,
    BASKET,
    CHAMBER;

    fun isBasket() = this == BASKET

    fun isChamber() = this == CHAMBER
}
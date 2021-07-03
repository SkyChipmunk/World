package org.cubit.world.util

import org.bukkit.entity.Player

object WorldTime {

    private val timeMap = mutableMapOf<Player, Long>()

    fun Player.updateTime() {
        timeMap[this] = System.currentTimeMillis()
    }

    fun Player.getTime() : Long {
        return System.currentTimeMillis() - (timeMap[this] ?: System.currentTimeMillis())
    }

}
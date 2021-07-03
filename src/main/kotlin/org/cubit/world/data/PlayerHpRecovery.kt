package org.cubit.world.data

import org.bukkit.entity.Player
import java.util.*

object PlayerHpRecovery {

    private val hpMap = mutableMapOf<UUID, Double>()

    fun Player.setHp(double : Double) {
        hpMap[this.uniqueId] = double
    }

    fun Player.getHp() : Double{
        return hpMap[this.uniqueId] ?: 0.0
    }
}
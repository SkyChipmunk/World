package org.cubit.world.data

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.cubit.world.abstraction.IPlayerRecoveryCheck

object PlayerDamageImmuned : IPlayerRecoveryCheck {
    override fun check(player: Player) : Boolean {
        return false
    }

}
package org.cubit.world.data

import org.bukkit.entity.Player
import org.cubit.world.abstraction.IPlayerRecoveryCheck

object PlayerHpRecovery : IPlayerRecoveryCheck {
    override fun check(player: Player): Boolean {
        return true
    }

}
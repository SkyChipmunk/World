package org.cubit.world.data

import org.bukkit.entity.Player
import org.cubit.world.abstraction.IPlayerRecoveryCheck
import java.util.*

object PlayerHpRecovery : IPlayerRecoveryCheck {
    override fun check(player: Player): Boolean {
        return true
    }

}
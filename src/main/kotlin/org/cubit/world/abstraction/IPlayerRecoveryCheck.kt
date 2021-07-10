package org.cubit.world.abstraction

import org.bukkit.entity.Player

interface IPlayerRecoveryCheck {

    fun check(player: Player): Boolean
}
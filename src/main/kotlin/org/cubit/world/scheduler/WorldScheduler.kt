package org.cubit.world.scheduler

import org.bukkit.Bukkit
import org.cubit.world.util.ImmuneUtil.getRecovery
import org.cubit.world.util.ImmuneUtil.isImmune
import org.cubit.world.util.ImmuneUtil.isImmuneSwapped
import org.cubit.world.util.ImmuneUtil.swapImmuneState

class WorldScheduler : Runnable {

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            player.swapImmuneState()
            if (player.isImmuneSwapped() && player.isImmune()) {
                player.health = (player.health + player.getRecovery()).coerceAtMost(player.maxHealth)
            }
        }
    }
}
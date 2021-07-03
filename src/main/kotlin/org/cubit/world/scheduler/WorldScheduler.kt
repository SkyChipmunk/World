package org.cubit.world.scheduler

import org.bukkit.Bukkit
import org.bukkit.metadata.FixedMetadataValue
import org.cubit.world.World
import org.cubit.world.data.PlayerCancel
import org.cubit.world.util.WorldTime.getTime

class WorldScheduler : Runnable{

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.getTime() > 10000) {
                player.setMetadata("Cancel" , FixedMetadataValue(World.inst , PlayerCancel()))
            }
        }
    }
}
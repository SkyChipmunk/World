package org.cubit.world.listener

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.cubit.world.data.PlayerDamageImmuned
import org.cubit.world.util.ImmuneUtil.adjustRecovery
import org.cubit.world.util.ImmuneUtil.check
import org.cubit.world.util.ImmuneUtil.isImmune

class WorldListener : Listener {

    @EventHandler
    fun EntityDamageByEntityEvent.onEntityDamageByEntity() {
        if (entity is Player) {
            val player = entity as Player
            player.check<PlayerDamageImmuned> {
                player.adjustRecovery(damage.toInt())
                damage = 0.0
            }
        }
    }


    @EventHandler
    fun EntityExplodeEvent.onEntityExplodeEvent() {
        isCancelled = true
        for (block in blockList()) {
            block.location(Material.STONE)
        }
    }

    operator fun Location.invoke(material: Material) {
        this.block.type = material
    }


}


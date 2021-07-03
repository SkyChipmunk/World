package org.cubit.world.listener

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.metadata.FixedMetadataValue
import org.cubit.world.World
import org.cubit.world.data.PlayerCancel
import org.cubit.world.data.PlayerHpRecovery
import org.cubit.world.data.PlayerHpRecovery.getHp
import org.cubit.world.util.WorldTime.updateTime
import org.cubit.world.util.WorldUtil.cancelIf

class WorldListener : Listener {

    @EventHandler
    fun EntityDamageByEntityEvent.onEntityDamageByEntity() {
        if (entity is Player) {
            val player = entity as Player
            if (player.hasMetadata("Cancel")) player.cancelIf<PlayerCancel> {
                player.health += player.getHp()
                player.setMetadata("Cancel", FixedMetadataValue(World.inst, PlayerHpRecovery))
                player.sendMessage("§a공격 회피!")
                player.updateTime()
                isCancelled = true
                return
            }
            player.cancelIf<PlayerHpRecovery> {
                player.setHp(player.maxHealth - player.health)
            }
        }
    }

    @EventHandler
    fun PlayerJoinEvent.onPlayerJoinEvent() {
        player.updateTime()
    }

    @EventHandler
    fun EntityExplodeEvent.onEntityExplodeEvent() {
        isCancelled = true
        for (block in blockList()) {
            block.location(Material.STONE)
        }
    }

    operator fun Location.invoke(material: Material){
        this.block.type = material
    }




}


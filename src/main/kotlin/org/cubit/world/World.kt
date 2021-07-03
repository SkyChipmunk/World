package org.cubit.world

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.cubit.world.listener.WorldListener
import org.cubit.world.scheduler.WorldScheduler

class World : JavaPlugin(){

    companion object {
        lateinit var inst: JavaPlugin
    }

    override fun onEnable() {
        inst = this
        Bukkit.getPluginManager().registerEvents(WorldListener() , this)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, WorldScheduler(), 1L, 1L)
    }
}
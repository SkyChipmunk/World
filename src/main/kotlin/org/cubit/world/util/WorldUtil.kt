package org.cubit.world.util

import org.bukkit.entity.Player

object WorldUtil {

    inline fun <reified T : Any> Player.cancelIf(unit: T.(Player) -> Unit) {
        if (hasMetadata("Cancel")) {
            val next = getMetadata("Cancel")[0].value()!!
            if (T::class.java.isAssignableFrom(next.javaClass)) {
                unit(next as T, this)

            }
        }
    }
}
package org.cubit.world.util

import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.cubit.world.World
import org.cubit.world.abstraction.IPlayerRecoveryCheck
import org.cubit.world.data.PlayerDamageImmuned
import org.cubit.world.data.PlayerHpRecovery

object ImmuneUtil {
    const val RECOVERY = "DamageRecovery"
    const val IMMUNE = "DamageImmune"
    const val IMMUNE_STATE = "DamageImmuneState"

    inline fun <reified T : IPlayerRecoveryCheck> Player.check(lambda: (IPlayerRecoveryCheck) -> Unit) {
        if (getMetadata(IMMUNE_STATE)[0].value() is T) {
            lambda(getMetadata(IMMUNE_STATE)[0].value() as T)
        }
    }

    fun Player.isImmune(): Boolean {
        val diff = System.currentTimeMillis() - getLastImmune()
        return diff < 10000
    }

    fun Player.swapImmuneState() {
        check<PlayerDamageImmuned> {
            if (isImmune())
                setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerHpRecovery))
        }
        check<PlayerHpRecovery> {
            if (!isImmune())
                setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerDamageImmuned))
        }
    }

    fun Player.isImmuneSwapped(): Boolean {
        val diff = System.currentTimeMillis() - getLastImmune()
        if (diff >= 20000) {
            updateImmune()
            return true
        }
        return false
    }

    fun Player.getLastImmune(): Long {
        if (!hasMetadata(IMMUNE))
            updateImmune()
        return getMetadata(IMMUNE)[0].asLong()
    }

    fun Player.updateImmune() {
        setMetadata(IMMUNE, FixedMetadataValue(World.inst, System.currentTimeMillis()))
        setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerHpRecovery))
    }

    fun Player.getRecovery(): Int {
        if (!hasMetadata(RECOVERY))
            setMetadata(RECOVERY, FixedMetadataValue(World.inst, 0))
        return getMetadata(RECOVERY)[0].asInt()
    }

    fun Player.adjustRecovery(amount: Int) {
        setRecovery(getRecovery() + amount)
    }

    fun Player.setRecovery(amount: Int) {
        setMetadata(RECOVERY, FixedMetadataValue(World.inst, amount))
    }

}
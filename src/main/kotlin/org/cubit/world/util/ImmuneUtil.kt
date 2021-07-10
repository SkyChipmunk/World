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

    //인라인 힘수로 호출된 메서드를 치환해준뒤 IMMUNE_STATE 상수 변수가 동일할떄 DSL 형식 실행
    inline fun <reified T : IPlayerRecoveryCheck> Player.check(lambda: (IPlayerRecoveryCheck) -> Unit) {
        if (getMetadata(IMMUNE_STATE)[0].value() is T) {
            lambda(getMetadata(IMMUNE_STATE)[0].value() as T)
        }
    }

    //무적 상태가 끝났는지 체크 해주는 메서드
    fun Player.isImmune(): Boolean {
        val diff = System.currentTimeMillis() - getLastImmune()
        return diff < 10000
    }

    // 플레이어 상태에 따라 무적이랑 , 대미지를 받는 상태를 설정 해주는 메서드
    fun Player.swapImmuneState() {
        if (!hasMetadata(IMMUNE_STATE)) setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerHpRecovery))
        check<PlayerDamageImmuned> {
            if (isImmune())
                setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerHpRecovery))
        }
        check<PlayerHpRecovery> {
            if (!isImmune())
                setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerDamageImmuned))
        }
    }

    //무적 상태가 시작되고 끝났을떄  초기화 해주는 메서드
    fun Player.isImmuneSwapped(): Boolean {
        val diff = System.currentTimeMillis() - getLastImmune()
        if (diff >= 20000) { // 조건: 회복은 무적시간이 대미지를 받고 무적시간이 끝날을떄 회복이다 (조건 틀림)
            updateImmune()
            return true
        }
        return false
    }

    //================================================================================

    //회복될 가져 오기
    private fun Player.getLastImmune(): Long {
        if (!hasMetadata(IMMUNE))
            updateImmune()
        return getMetadata(IMMUNE)[0].asLong()
    }

    //무적 상태 설정 , 회복될 시간 설정
    private fun Player.updateImmune() {
        setMetadata(IMMUNE, FixedMetadataValue(World.inst, System.currentTimeMillis()))
        setMetadata(IMMUNE_STATE, FixedMetadataValue(World.inst, PlayerHpRecovery))
    }
    //회복될 량 가져오는 메서드
    fun Player.getRecovery(): Int {
        if (!hasMetadata(RECOVERY))
            setMetadata(RECOVERY, FixedMetadataValue(World.inst, 0))
        return getMetadata(RECOVERY)[0].asInt()
    }

    //회복될 량 추가하는 메서드
    fun Player.adjustRecovery(amount: Int) {
        setRecovery(getRecovery() + amount)
    }

    //회복될 량 설정 메서드
    private fun Player.setRecovery(amount: Int) {
        setMetadata(RECOVERY, FixedMetadataValue(World.inst, amount))
    }

}
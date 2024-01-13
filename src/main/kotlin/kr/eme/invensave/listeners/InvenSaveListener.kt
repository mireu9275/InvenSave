package kr.eme.invensave.listeners

import kr.eme.invensave.managers.InvenSaveBookManager
import kr.eme.invensave.managers.InvenSaverManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object InvenSaveListener: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val uuid = player.uniqueId
        val saver = InvenSaverManager.getSaver(uuid)
        if (saver == null || saver.invenSaveAmount < 1) return
        event.keepInventory = true
        event.keepLevel = true
        event.drops.clear()
        event.droppedExp = 0
        InvenSaverManager.saverAmountSubtract(uuid, 1)
        val amount = InvenSaverManager.getSaverAmount(saver)
        player.sendMessage("남은 인벤세이브 횟수는 $amount 회 입니다.")
    }

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        // 우클릭 이벤트만 처리
        val action = event.action
        if (action != org.bukkit.event.block.Action.RIGHT_CLICK_AIR &&
            action != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
            return
        }
        val player = event.player
        val uuid = player.uniqueId

        // 아이템 확인
        val item = event.item ?: return
        if (item.type != Material.BOOK) return
        if (!InvenSaveBookManager.isCorrectItem(item)) return
        val amount = InvenSaveBookManager.getBookAmount(item)
        if (amount <= 0) {
            player.sendMessage("잘못된 아이템입니다. 어드민에게 문의해주세요.")
            return
        }

        val saver = InvenSaverManager.getSaver(uuid)
        if (saver == null) {
            player.sendMessage("플레이어가 등록되지 않았습니다. 어드민에게 문의해주세요.")
            return
        }

        saver.addAmount(amount)
        val saverAmount = saver.invenSaveAmount
        player.sendMessage("정상적으로 사용되었습니다. 현재 인벤세이브 가능 횟수 : $saverAmount 회")
        if (item.amount > 1) item.amount -= 1
        else player.inventory.removeItem(item)

    }


    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId
        InvenSaverManager.loadSaver(uuid)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId
        InvenSaverManager.unloadSaver(uuid)
    }
}
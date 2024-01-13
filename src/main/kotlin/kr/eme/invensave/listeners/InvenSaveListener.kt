package kr.eme.invensave.listeners

import kr.eme.invensave.managers.InvenSaverManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
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
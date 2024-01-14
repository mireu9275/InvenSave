package kr.eme.invensave

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

internal fun getUUIDFromPlayerName(playerName: String): UUID {
    val onlinePlayer = Bukkit.getPlayer(playerName)
    return if (onlinePlayer != null) {
        onlinePlayer.uniqueId
    } else {
        val offlinePlayer = Bukkit.getOfflinePlayer(playerName)
        return offlinePlayer.uniqueId
    }
}

fun hasEmptySlot(player: Player): Boolean {
    for (i in 0..35) {
        player.inventory.getItem(i) ?: return true
    }
    return false
}
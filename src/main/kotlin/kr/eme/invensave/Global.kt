package kr.eme.invensave

import org.bukkit.Bukkit
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
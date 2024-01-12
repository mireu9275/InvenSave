package kr.eme.invensave

import org.bukkit.Bukkit
import java.util.*

//추후 라이브러리에 넣을 예정

fun getUUIDFromPlayerName(playerName: String): UUID? {
    val onlinePlayer = Bukkit.getPlayer(playerName)
    return if (onlinePlayer != null) {
        onlinePlayer.uniqueId
    } else {
        val offlinePlayer = Bukkit.getOfflinePlayer(playerName)
        return offlinePlayer.uniqueId
    }
}


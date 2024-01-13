package kr.eme.invensave

import kr.eme.invensave.commands.InvenSaveCommand
import kr.eme.invensave.listeners.InvenSaveListener
import kr.eme.invensave.managers.FileManager
import kr.eme.invensave.managers.InvenSaverManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class InvenSave: JavaPlugin() {
    companion object {
        lateinit var main: InvenSave
            private set
    }

    override fun onEnable() {
        main = this
        main.saveDefaultConfig()
        FileManager.init()
        logger.info("InvenSave Plugin Enable")
        getCommand("인벤세이브")?.let {
            it.setExecutor(InvenSaveCommand)
            it.setAliases(listOf("invensave"))
        }
        server.pluginManager.registerEvents(InvenSaveListener, this)
    }

    override fun onDisable() {
        InvenSaverManager.saveAllSavers()
        logger.info("InvenSave Plguin Disable")
    }

    //추후 라이브러리에 넣을 예정.
    fun log(block: (Logger) -> Unit) { block(logger) }
    fun warn(message: String) { logger.warning(message) }
    fun info(message: String) { logger.info(message) }
}
package kr.eme.invensave

import kr.eme.invensave.listeners.InvenSaveListener
import kr.eme.invensave.managers.FileManager
import kr.eme.invensave.managers.InvenSaverManager
import org.bukkit.plugin.java.JavaPlugin

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
        server.pluginManager.registerEvents(InvenSaveListener, this)
    }

    override fun onDisable() {
        InvenSaverManager.saveAllSavers()
        logger.info("InvenSave Plguin Disable")
    }
}
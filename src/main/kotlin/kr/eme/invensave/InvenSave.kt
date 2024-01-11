package kr.eme.invensave

import kr.eme.invensave.listeners.InvenSaveListener
import org.bukkit.plugin.java.JavaPlugin

class InvenSave: JavaPlugin() {
    companion object {
        lateinit var main: InvenSave
            private set
    }

    override fun onEnable() {
        main = this
        main.saveDefaultConfig()
        logger.info("InvenSave Plugin Enable")
        server.pluginManager.registerEvents(InvenSaveListener, this)
    }

    override fun onDisable() {
        logger.info("InvenSave Plguin Disable")
    }
}
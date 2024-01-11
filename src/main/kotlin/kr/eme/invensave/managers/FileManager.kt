package kr.eme.invensave.managers

import kr.eme.invensave.InvenSave.Companion.main
import kr.eme.invensave.objects.InvenSaver
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

object FileManager {
    private val saverFolder = File(main.dataFolder.path + File.separator + "UUIDS")

    fun init() {
        createDir()
    }

    private fun createDir() {
        val uuidDir = File(main.dataFolder, "UUIDS")
        if (!uuidDir.exists()) uuidDir.mkdirs()
    }

    fun getPlayerFile(pUUID: UUID): FileConfiguration {
        val file = File(saverFolder,"$pUUID.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        if (!file.exists()) {
            val defaultAmount = main.config.getInt("defaultInvenSaveAmount", 5)
            file.createNewFile()
            // 초기 데이터 설정
            config.set("invenSaveAmount", defaultAmount)
            config.save(file)
        }
        return config
    }

    /**
     * Save player file
     *
     * 인벤세이브 횟수를 저장합니다.
     * @param invenSaver
     */
    fun savePlayerFile(invenSaver: InvenSaver) {
        val file = File(saverFolder, "${invenSaver.pUUID}")
        val config = YamlConfiguration.loadConfiguration(file)
        config.set("invenSaveAmount", invenSaver.invenSaveAmount)
        config.save(file)
    }
}
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


    fun createPlayerFile(uuid: UUID): FileConfiguration {
        val file = File(saverFolder,"$uuid.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        if (!file.exists()) {
            val defaultAmount = main.config.getInt("defaultInvenSaveAmount", 0)
            file.createNewFile()
            // 초기 데이터 설정
            config.set("invenSaveAmount", defaultAmount)
            config.save(file)
        }
        return config
    }

    fun getPlayerFile(uuid: UUID): FileConfiguration {
        val file = File(saverFolder, "$uuid.yml")
        return YamlConfiguration.loadConfiguration(file)
    }

    /**
     * Save player file
     *
     * 인벤세이브 횟수를 저장합니다.
     * @param saver
     */
    fun savePlayerFile(saver: InvenSaver) {
        val file = File(saverFolder, "${saver.uuid}.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        config.set("invenSaveAmount", saver.invenSaveAmount)
        config.save(file)
    }
}
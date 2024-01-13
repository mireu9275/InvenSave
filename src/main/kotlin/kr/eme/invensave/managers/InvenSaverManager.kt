package kr.eme.invensave.managers

import kr.eme.invensave.InvenSave.Companion.main
import kr.eme.invensave.objects.InvenSaver
import java.io.File
import java.util.*
import kotlin.collections.HashMap

object InvenSaverManager {
    private val saverMap = HashMap<UUID, InvenSaver>()
    /**
     * Get saver
     *
     * @param uuid
     * @return
     */
    fun getSaver(uuid: UUID): InvenSaver? = saverMap[uuid]

    fun getSaverAmount(saver: InvenSaver): Int = saver.invenSaveAmount

    fun getOfflineSaverAmount(uuid: UUID): Int {
        return try {
            val config = FileManager.getPlayerFile(uuid)
            config.getInt("invenSaveAmount")
        } catch (e: Exception) {
            main.warn("InvenSaveManager - getOfflineSaverAmount : $e")
            0
        }
    }

    /**
     * Load saver
     *
     * 플레이어가 접속 하였을 때 Config 파일에서 인벤세이브 횟수를 불러옴
     * @param uuid
     */
    fun loadSaver(uuid: UUID) {
        try {
            val config = FileManager.createPlayerFile(uuid)
            val amount = config.getInt("invenSaveAmount")
            saverMap[uuid] = InvenSaver(uuid, amount)
        } catch (e: Exception) {
            main.warn("loadSaver Error : ${e.message}")
        }
    }

    /**
     * Unload saver
     *
     * 플레이어가 접속을 종료하였을 때 Config 파일에 인벤세이브 횟수를 저장한 후 메모리에서 삭제함.
     * @param uuid
     */
    fun unloadSaver(uuid: UUID) {
        try {
            saverMap[uuid]?.let {
                val config = FileManager.getPlayerFile(uuid)
                config.set("invenSaveAmount", it.invenSaveAmount)
                config.save(File(main.dataFolder.path + File.separator + "UUIDS", "$uuid.yml"))
            }
            saverMap.remove(uuid)
        }
        catch (e: Exception) {
            main.warn("unLoadSaver Error : ${e.message}")
        }
    }

    /**
     * Save all savers
     *
     * 모든 유저를 저장합니다.
     */
    fun saveAllSavers() {
        for (saver in saverMap.values) {
            saver.save()
        }
    }

    /**
     * Saver amount add
     *
     * 플레이어의 인벤세이브 횟수를 amount 만큼 추가합니다.
     * @param uuid
     * @param amount
     */
    fun saverAmountAdd(uuid: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(uuid) ?: return
        saver.addAmount(amount)
    }

    /**
     * Saver amount subtract
     *
     * 플레이어의 인벤세이브 횟수를 amount 만큼 차감합니다.
     * @param uuid
     * @param amount
     */
    fun saverAmountSubtract(uuid: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(uuid) ?: return
        saver.subtractAmount(amount)
    }

    /**
     * Saver amount set
     * 플레이어의 인벤세이브 횟수를 amount 로 설정합니다.
     * @param uuid
     * @param amount
     */
    fun saverAmountSet(uuid: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(uuid) ?: return
        saver.setAmount(amount)
    }
}
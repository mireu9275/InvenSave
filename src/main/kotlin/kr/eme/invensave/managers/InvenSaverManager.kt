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
     * @param pUUID
     * @return
     */
    fun getSaver(pUUID: UUID): InvenSaver? = saverMap[pUUID]

    /**
     * Load saver
     *
     * 플레이어가 접속 하였을 때 Config 파일에서 인벤세이브 횟수를 불러옴
     * @param pUUID
     */
    fun loadSaver(pUUID: UUID) {
        try {
            val config = FileManager.getPlayerFile(pUUID)
            val amount = config.getInt("invenSaveAmount")
            saverMap[pUUID] = InvenSaver(pUUID, amount)
        } catch (e: Exception) {
            println("loadSaver Error : ${e.message}")
        }
    }

    /**
     * Unload saver
     *
     * 플레이어가 접속을 종료하였을 때 Config 파일에 인벤세이브 횟수를 저장한 후 메모리에서 삭제함.
     * @param pUUID
     */
    fun unloadSaver(pUUID: UUID) {
        try {
            saverMap[pUUID]?.let {
                val config = FileManager.getPlayerFile(pUUID)
                config.set("invenSaveAmount", it.invenSaveAmount)
                config.save(File(main.dataFolder.path + File.separator + "UUIDS", "$pUUID.yml"))
            }
            saverMap.remove(pUUID)
        }
        catch (e: Exception) {
            println("unLoadSaver Error : ${e.message}")
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
     * @param pUUID
     * @param amount
     */
    fun saverAmountAdd(pUUID: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(pUUID) ?: return
        saver.addAmount(amount)
    }

    /**
     * Saver amount substract
     *
     * 플레이어의 인벤세이브 횟수를 amount 만큼 차감합니다.
     * @param pUUID
     * @param amount
     */
    fun saverAmountSubstract(pUUID: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(pUUID) ?: return
        saver.substractAmount(amount)
    }

    /**
     * Saver amount set
     * 플레이어의 인벤세이브 횟수를 amount 로 설정합니다.
     * @param pUUID
     * @param amount
     */
    fun saverAmountSet(pUUID: UUID, amount: Int) {
        val saver: InvenSaver = getSaver(pUUID) ?: return
        saver.setAmount(amount)
    }
}
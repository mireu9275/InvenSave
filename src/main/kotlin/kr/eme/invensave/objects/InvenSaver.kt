package kr.eme.invensave.objects

import kr.eme.invensave.managers.FileManager
import java.util.*

class InvenSaver(
    val uuid: UUID,
    var invenSaveAmount: Int
) {
    fun addAmount(amount: Int) {
        invenSaveAmount += amount
    }

    fun subtractAmount(amount: Int) {
        invenSaveAmount -= amount
    }

    fun setAmount(amount: Int) {
        invenSaveAmount = amount
    }

    fun save() {
        try {
            FileManager.savePlayerFile(this)
        } catch (e: Exception) {
            println("objects.InvenSave Error : ${e.message}")
        }
    }
}
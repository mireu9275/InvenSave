package kr.eme.invensave.managers

import kr.eme.invensave.InvenSave.Companion.main
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object InvenSaveBookManager {
    private fun createInvenSaveBook(amount: Int): ItemStack {
        val item = ItemStack(Material.BOOK)
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("§f[인벤세이브권]")
        val lore = mutableListOf<String>()
        lore.add("§f추가 횟수: $amount")
        meta.lore = lore
        val nameKey = NamespacedKey(main, "InvenSaveBook")
        val amountKey = NamespacedKey(main, "InvenSaveAmount")
        meta.persistentDataContainer.set(nameKey, PersistentDataType.STRING, "true")
        meta.persistentDataContainer.set(amountKey, PersistentDataType.INTEGER, amount)
        item.itemMeta = meta
        return item
    }

    fun giveInvenSaveBook(player: Player, amount: Int) {
        val item = createInvenSaveBook(amount)
        val playerInventory = player.inventory
        val emptySlots = playerInventory.contents.count { it == null }
        if (emptySlots < item.amount) {
            player.sendMessage("인벤토리 공간이 부족합니다. 인벤토리를 비운 후 다시 시도해주세요.")
            return
        }
        player.inventory.addItem(item)
    }

    fun isCorrectItem(itemStack: ItemStack?): Boolean {
        if (itemStack == null) return false
        val meta = itemStack.itemMeta ?: return false
        val key = NamespacedKey(main, "InvenSaveBook")
        return meta.persistentDataContainer.has(key, PersistentDataType.STRING)
    }

    fun getBookAmount(itemStack: ItemStack?): Int {
        if (itemStack == null) return 0
        val meta = itemStack.itemMeta ?: return 0
        val amountKey = NamespacedKey(main, "InvenSaveAmount")
        return meta.persistentDataContainer.get(amountKey, PersistentDataType.INTEGER) ?: 0
    }
}
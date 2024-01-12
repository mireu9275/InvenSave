package kr.eme.invensave.commands

import kr.eme.invensave.getUUIDFromPlayerName
import kr.eme.invensave.managers.InvenSaverManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object InvenSaveCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                println("이 명령어는 플레이어만 사용 가능합니다.")
                return false
            }

            if (args.isEmpty()) {
                usage(sender); return true; }

            when (args[0]) {
                "보기" -> showSaverAmount(sender, args)
                "추가" -> TODO()
                "차감" -> TODO()
                "설정" -> TODO()
            }
            return true
        } catch (e: Exception) {
            print("InvenSaveCommand - onCommand : $e")
            return false
        }
    }

    /**
     * Usage
     *
     * 기본적인 명령어 정보
     * @param sender
     */
    private fun usage(sender: CommandSender) {
        if (sender.isOp) {
            sender.sendMessage(
                "=== 명령어 사용법 ===\n" +
                "/인벤세이브 보기 [닉네임] : [닉네임]의 인벤세이브 횟수를 확인합니다.\n" +
                "/인벤세이브 추가 [수량] [닉네임] : [닉네임]의 인벤세이브 횟수를 [수량] 만큼 추가합니다. \n" +
                "/인벤세이브 차감 [수량] [닉네임] : [닉네임]의 인벤세이브 횟수를 [수량] 만큼 차감합니다. \n" +
                "/인벤세이브 설정 [수량] [닉네임] : [닉네임]의 인벤세이브 횟수를 [수량] 으로 설정합니다."
            )
        } else {
            sender.sendMessage(
                "=== 명령어 사용법 ===\n" +
                "/인벤세이브 보기: 자신의 인벤세이브 횟수를 확인합니다.\n"
            )
        }
    }

    private fun showSaverAmount(sender: CommandSender, args: Array<out String>) {
        try {
            if (sender !is Player) return
            val senderUUID = sender.uniqueId
            val senderSaver = InvenSaverManager.getSaver(senderUUID) ?: return
            if (args.size == 2) {
                val targetUUID = getUUIDFromPlayerName(args[1]) ?: return
                val targetSaver = InvenSaverManager.getSaver(targetUUID)
                if (targetSaver == null) {
                    val amount = InvenSaverManager.getOfflineSaverAmount(targetUUID)
                    sender.sendMessage("남은 인벤세이브 횟수는 $amount 회 입니다.")
                }
            } else {
                val amount = InvenSaverManager.getSaverAmount(senderSaver)
                sender.sendMessage("남은 인벤세이브 횟수는 $amount 회 입니다.")
            }
        } catch (e: Exception) {
            println("$e")
        }
    }
}
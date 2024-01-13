package kr.eme.invensave.commands

import kr.eme.invensave.getUUIDFromPlayerName
import kr.eme.invensave.managers.InvenSaverManager
import kr.eme.invensave.objects.InvenSaver
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
                "추가" -> addSaverAmount(sender, args)
                "차감" -> subtractSaverAmount(sender, args)
                "설정" -> setSaverAmount(sender, args)
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
            if (args.size == 2) {
                val targetUUID = getUUIDFromPlayerName(args[1]) ?: return
                val targetSaver = InvenSaverManager.getSaver(targetUUID)
                if (targetSaver == null) {
                    val amount = InvenSaverManager.getOfflineSaverAmount(targetUUID)
                    sender.sendMessage("남은 인벤세이브 횟수는 $amount 회 입니다.")
                }
            } else {
                val senderSaver = getSaver(sender, args[1]) ?: return
                val amount = InvenSaverManager.getSaverAmount(senderSaver)
                sender.sendMessage("남은 인벤세이브 횟수는 $amount 회 입니다.")
            }
        } catch (e: Exception) {
            println("showSaverAmount : $e")
        }
    }

    /**
     * Get saver
     *
     * @param sender
     * @param playerName
     * @return
     */
    private fun getSaver(sender: CommandSender, playerName: String): InvenSaver? {
        if (sender !is Player) return null
        val uuid = getUUIDFromPlayerName(playerName) ?: return null
        val saver = InvenSaverManager.getSaver(uuid)
        if (saver == null) {
            sender.sendMessage("접속하지 않은 플레이어는 설정이 불가능합니다.")
            return null
        }
        return saver
    }

    /**
     * Add saver amount
     *
     * @param sender
     * @param args
     */
    private fun addSaverAmount(sender: CommandSender, args: Array<out String>) {
        try {
            if (args.size != 3) return
            val saver = getSaver(sender, args[2]) ?: return
            val amount = args[1].toInt()
            saver.addAmount(amount)
            sender.sendMessage("${args[2]} 님의 인벤세이브 횟수를 $amount 회 추가했습니다.")
        } catch (e: Exception) {
            println("addSaverAmount : $e")
        }
    }

    /**
     * Subtract saver amount
     *
     * @param sender
     * @param args
     */
    private fun subtractSaverAmount(sender: CommandSender, args: Array<out String>) {
        try {
            if (args.size != 3) return
            val saver = getSaver(sender, args[2]) ?: return
            val amount = args[1].toInt()
            saver.subtractAmount(amount)
            sender.sendMessage("${args[2]} 님의 인벤세이브 횟수를 $amount 회 차감했습니다.")
        } catch (e: Exception) {
            println("subtractSaverAmount : $e")
        }
    }

    /**
     * Set saver amount
     *
     * @param sender
     * @param args
     */
    private fun setSaverAmount(sender: CommandSender, args: Array<out String>) {
        try {
            if (args.size != 3) return
            val saver = getSaver(sender, args[2]) ?: return
            val amount = args[1].toInt()
            saver.setAmount(amount)
            sender.sendMessage("${args[2]} 님의 인벤세이브 횟수를 $amount 회로 설정했습니다.")
        } catch (e: Exception) {
            println("setSaverAmount : $e")
        }
    }

}
package kr.eme.invensave.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object InvenSaveCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) { usage(sender); return true; }
        return true
    }

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
}
package CodeGaming

import java.util
import java.util.Random

import CodeGaming.CardClazz.{Ground, Skill}


object test {


  def main(args: Array[String]): Unit = {

    //设置战锤
    val battleField = new BattleField
    //设置玩家
    val player = battleField.new_character("The Elder One", "", 10)
    //设置敌人
    val enermy = battleField.new_character("The Mortal", "", 2)
    //测试打印信息
    println(player.toString)
    println(enermy.toString)
    //准备使用火球术
    player.plan_used = new Skill
    player.plan_used.name = "火球术"
    player.plan_target = enermy
    player.plan_cost = new util.ArrayList[Card]()
    player.planning()
    //加入一张牌，作为额外消耗
    val extra_card = new Card
    player.plan_cost.add(extra_card)
    player.planning()
    //开始释放
    player.excuting()
    println(enermy.toString)
  }
}

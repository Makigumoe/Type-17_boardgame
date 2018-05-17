package CodeGaming

import java.util
import java.util.Random

import CodeGaming.CardClazz.{Ground, Skill}


object test {


  def main(args: Array[String]): Unit = {

    //设置玩家
    val player = new Character
    player.name = "The Elder One"
    player.info = "Testing methods"
    player.position = 0.5
    player.hp = 8
    player.hp_current = 7
    //设置敌人
    val enermy = new Character
    enermy.name = "The Mortal"
    enermy.info = "weak"
    enermy.hp = 10
    enermy.hp_current = 10
    enermy.position = 0.5
    enermy.current_ground = new Ground
    enermy.current_ground.name = "岩石"
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

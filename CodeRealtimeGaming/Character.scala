package CodeRealtimeGaming

class Character(time_span: Int) {
  var action_list = Map[String, Int]()
  var position = (0, 0) //坐标
  var target_position = (0, 0)
  var moved = false

  def command_attack(): Unit = {
    println("准备砍。耗时2s")
    action_list += ("action_attack" -> 2000)
  }

  def action_attack(): Unit = {
    println("砍一刀")
  }

  def command_move(new_position: (Int, Int)): Unit = {
    action_list += ("action_move" -> 2000)
    target_position = new_position
  }

  def action_move(): Unit = {
    //x上移动
    if (position._1 < target_position._1)
      position = (position._1 + 1, position._2)
    else if (position._1 > target_position._1)
      position = (position._1 - 1, position._2)
    //y上移动
    if (position._2 < target_position._2)
      position = (position._1, position._2 + 1)
    else if (position._2 > target_position._2)
      position = (position._1, position._2 - 1)
    //如果还没到，那就继续动
    if (position != target_position)
      action_list += ("action_move" -> 2000)
    moved = true
  }

  /**
    * 减少行动计数。为0时，执行动作
    */
  def down_count(): Unit = {
    val tmp = action_list
    action_list = Map[String, Int]()
    for (act <- tmp) {
      if (act._2 - time_span > 0)
        action_list += (act._1 -> (act._2 - time_span))
      else {
        val clazz = this.getClass
        clazz.getDeclaredMethod(act._1)
          .invoke(this)
          .asInstanceOf[Unit]
      }
    }
  }
}

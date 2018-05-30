package CodeRealtimeGaming

object Battlefield {
  var players = Array[Character]()
  val time_span = 100
  var last_tick: Long = 0

  def init(): Unit = {
    players = players :+ new Character(time_span)
    players(0).command_move((3, 6))
    last_tick = System.currentTimeMillis()
  }

  def tick(): Unit = {
    while (true) {
      for (player <- players)
        player.down_count()
      map()
      Thread.sleep(last_tick + time_span - System.currentTimeMillis())
      last_tick = System.currentTimeMillis()
    }

  }

  def map(): Unit = {
    //先检查是不是有人动了
    var is_moved = false
    for (player <- players)
      if (player.moved) {
        player.moved = false
        is_moved = true
      }
    if (is_moved) {
      var positions = Array[(Int, Int)]()
      for (player <- players)
        positions = positions :+ player.position
      val blocks = for (i <- 0 until 10) yield {
        for (j <- 0 until 20) yield if (positions.contains((i, j))) "人" else "。"
      }
      println((for (i <- 0 until 40) yield "-").reduce(_ + _))
      for (line <- blocks)
        println(line.reduce(_ + _))
    }
  }

  def main(args: Array[String]): Unit = {
    init()
    tick()
  }

}

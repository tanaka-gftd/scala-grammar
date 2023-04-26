/* 最短経路問題 */
/*
  単一始点最短経路問題を、ベルマンフォード法で解いてみる
  (総当たり法で解こうとすると、ノード数が増えると尋常じゃないぐらい経路が増えるので現実的ではない)
*/



case class Edge(from: Char, to: Char, distance: Int)
object ShortestPath {

  //単一始点最短経路問題で使用するグラフを作成、まずは視点と終点を指定
  //toメソッドを利用して、AからGまでの文字を表すRangeオブジェクトを作成する
  //toメソッド...Rangeオブジェクトを作成する
  val vertexes = 'A' to 'G'

  //単一始点最短経路問題で使用するグラフの中身を作成
  //存在する各辺と、その辺を移動するためのコストをSeqで保持
  val edges = Seq(
    Edge('A', 'B', 1),
    Edge('A', 'C', 8),
    Edge('B', 'A', 1),
    Edge('B', 'C', 6),
    Edge('B', 'D', 6),
    Edge('B', 'E', 6),
    Edge('C', 'A', 8),
    Edge('C', 'B', 6),
    Edge('C', 'D', 7),
    Edge('D', 'B', 6),
    Edge('D', 'C', 7),
    Edge('D', 'F', 2),
    Edge('E', 'B', 6),
    Edge('E', 'F', 6),
    Edge('E', 'G', 8),
    Edge('F', 'D', 2),
    Edge('F', 'E', 6),
    Edge('F', 'G', 5),
    Edge('G', 'E', 8),
    Edge('G', 'F', 5)
  )


  //ベルマンフォード法による、単一始点最短経路問題の解法アルゴリズム
  def solveByBellmanFord(start: Char, goal: Char): Unit = {
    var distances = vertexes.map((v) => (v -> Int.MaxValue)).toMap
    distances = distances + (start -> 0)

    var isUpdated = true
    while(isUpdated){
      isUpdated = false
      edges.foreach{(e) =>
        if((distances(e.from) != Int.MaxValue) && (distances(e.to) > distances(e.from) + e.distance)) {
          distances = distances + (e.to -> (distances(e.from) + e.distance))
          isUpdated = true
        }
      }
    }
    //ベルマンフォード法で解いた結果
    println(distances)  //第1引数として渡したノードから、それぞれのノードへの最短経路
    println(distances(goal))  //第1引数として渡したノードを始点、第2引数として渡したノードをを終点としたときの、最小移動コスト
  }
}

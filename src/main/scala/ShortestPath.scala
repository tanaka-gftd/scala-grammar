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
  def solveByBellmanFord(start: Char, goal: Char): Unit = {  //引数として始点と終点となるノードを渡す

    /*
      まず、vertexesで保持してあるRangeオブジェクトの各ノードを示す値(ここではAからG)を取り出し、「ノード->♾️」というエントリーを持つMapを作成する。
      (map関数で返されるコレクションの型はVector(Listの一種)なので、toMapでMapに変換しておく)
      また、♾️の表現方法については、Int.MaxValue(Int型の最大値)を使って、擬似的に行う。
      なお、始点として設定したノード(ここではA)だけは、+メソッドで「始点のノード->0」と更新しておく。
      +メソッド...keyがなければエントリーを追加したコレクション、keyがあればvalueを更新したコレクションを、それぞれ新規に作成して返す(非破壊的)
      このMapで設定した値(数値)の更新を繰り返し、最終的に「ノード->最短移動コスト」とするのがベルマンフォード法。
    */
    var distances = vertexes.map((v) => (v -> Int.MaxValue)).toMap
    //var distances = vertexes.map(v => v -> Int.MaxValue).toMap  //引数と返り値を括っている()は省略できる
    //var distances = vertexes.map(_ -> Int.MaxValue).toMap  //アンダーバーで無名関数を省略することもできる(ただし、分かりにくくなる)
    distances = distances + (start -> 0)

    /*
      辺のコレクションに存在している辺を全て、一度も更新がなくなるまでループし続ける。

      ループの中の処理において、
        •「辺の始点までの最短距離」が無限大ではない場合
        •「辺の終点までの最短距離」が「辺の始点までの最短距離」+「辺の距離」より大きい
      の両方を満たした場合に「辺の終点までの最短距離」を更新する。

      以上の要件をwhile式で記述する。
    */
    var isUpdated = true  //whileループ内で更新が行われたかどうかを示すフラグとして使う(初期値はtrue)

    //isUpdatedがtrueの場合、whileループを繰り返す(isUpdatedがtrueだと、最短距離更新中としている)
    while(isUpdated){

      //一旦、isUpdatedをfalseにする
      //最短距離を示す値の更新時に再度trueにしてwhileループし、更新されなければfalseのままにしてwhileループを抜けるようにしている
      isUpdated = false

      //edges(Seq型)内で保持している各辺について、foreachで処理を行っていく
      //なお edges.foreach { e => ... } は edges.foreach(e => ...) と同じ意味
      //Scalaでは、メソッドの引数が1つだけの場合は () を {} に置き換えることができる
      edges.foreach{(e) =>
        /*
          「distances(e.from) != Int.MaxValue」 ...「辺の始点までの最短距離」が無限大ではない
          「distances(e.to) > distances(e.from) + e.distance」 ...「辺の終点までの最短距離」が「辺の始点までの最短距離」+「辺の距離」より大きい
           以上2点の条件を満たした場合、Map(ここではdistances)で保持している「辺の終点までの最短距離」を、「辺の始点までの最短距離」+「辺の距離」の値に更新する。
        */
        if((distances(e.from) != Int.MaxValue) && (distances(e.to) > distances(e.from) + e.distance)) {
          distances = distances + (e.to -> (distances(e.from) + e.distance))

          //値が更新されたので、isUpdatedをtrueにして、whileループのフラグを再度オンにする
          isUpdated = true
        }
      }
    }
    //ベルマンフォード法で解いた結果
    println(distances)  //第1引数として渡したノードから、それぞれのノードへの最短経路
    println(distances(goal))  //第1引数として渡したノードを始点、第2引数として渡したノードをを終点としたときの、最小移動コスト
  }
}

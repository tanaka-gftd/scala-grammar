package jp.co.dwango.marubatsu.game

///状態を表すケースクラスをimport
import jp.co.dwango.marubatsu.board.{Board, Empty, Maru => MaruState, Batsu => BatsuState}

//Gameクラスはフィールドとして、winnerとboardを持つ
class Game(private[this] val winner: Winner, private[this] val board: Board) {

  //ゲームを遊ぶ時に使用するメソッド
  /*
    すでにBoardクラスに実装していたcanPutメソッドを呼び出して、
    そのマスに印が置けるなら、印を置く(置けない場合は、thisキーワードで今の自分自身を返す)
    その後、Gameの勝者を判定するjudgeWinnerメソッドを呼び出し、
    次の状態のGameクラスのインスタンスを作成する。
  */
  def play(row: Int, column: Int): Game = {
    if(board.canPut(row,column)){
      val nextBoard = board.put(row, column)
      new Game(judgeWinner(nextBoard), nextBoard)
    } else {
      this
    }
  }

  //勝ち負けを判定するメソッド
  /*
    あらかじめ、勝利パターンである印の並びを作成しておき、
    ボードから現在のセルの状態を取得して、勝利パターンの並びと一致するかどうかを判定する
  */
  private[this] def judgeWinner(board: Board): Winner = {

    //勝利パターンをSeqで保存
    val winPattern =
      Seq(((0,0),(0,1),(0,2)),((1,0),(1,1),(1,2)),((2,0),(2,1),(2,2)),((0,0),(1,0),(2,0)),
        ((0,1),(1,1),(2,1)),((0,2),(1,2),(2,2)), ((0,0),(1,1),(2,2)),((2,0),(1,1),(0,2))
      )

    //ボードの状態を取得
    //val cells: Map[(Int, Int), CellState]
    val cells = board.cells

    //勝利パターンとマッチングさせる
    //c1,c2,c3は任意のマスを示す
    val winners = winPattern.map{

      //任意のマス(c1,c2,c3)において、c1,c2,c3のCellStateが同じであり、かつ、勝利パターンとマッチする場合、その印を使うプレイヤーを勝者とする
      //同じパッケージの別ファイルで定義したtoWinnerメソッドで、CellState型をWinner型に変換している
      case (c1,c2,c3) if (cells(c1) == cells(c2)) && (cells(c2) == cells(c3)) => toWinner(cells(c1))

      //マッチしない場合(勝者なし)
      case _ => NoWinner
    }

    //勝者(Winner型)を返す
    if(winners.contains(Maru)){
      Maru
    } else if(winners.contains(Batsu)){
      Batsu
    } else {
      NoWinner
    }

    /*
      例：
        ⭕️が斜めに揃う位置 (0, 0), (1, 1), (2, 2) の場所に置かれていれば、
        winnersはSeq(NoWinner, NoWinner, NoWinner, NoWinner, NoWinner, NoWinner, Maru, NoWinner)のようなMaruを含むSeqとなり、
        結果このjudgeWinnerメソッド全体の戻り値もMaru(Winner型)となる。
    */
  }

  //セルの状態(CellState型)に応じて、表示する絵柄を設定
  private[this] def sign(row: Int, column: Int) = board.cells((row, column)) match {
    case Empty => "  "
    case MaruState => "⭕️"
    case BatsuState => "❌"
  }

  //ボードの状態を表示させるために、デフォルトで用意されているtoStringをオーバーライド
  //マス目の一つ一つに上記で設定した絵柄をはめ込んでいく
  override def toString =
    s"""Winner: ${winner}
       ||${sign(0,0)}|${sign(0,1)}|${sign(0,2)}|
       ||${sign(1,0)}|${sign(1,1)}|${sign(1,2)}|
       ||${sign(2,0)}|${sign(2,1)}|${sign(2,2)}|
    """.stripMargin
}


//Gameクラスのファクトリメソッド
object Game {
  def apply(): Game = new Game(NoWinner,  Board())
}

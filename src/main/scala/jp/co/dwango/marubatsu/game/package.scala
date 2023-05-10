//パッケージオブジェクト
/*
  パッケージオブジェクト：
    パッケージ自体と同じ名前を持つオブジェクト。
    パッケージオブジェクトのメソッドを実装した場合、同じパッケージ内ではimport文なしで、定義したメソッドを呼び出せる。
*/

package jp.co.dwango.marubatsu

//boardパッケージで定義したものを、importする
//import jp.co.dwango.marubatsu.board.CellState
//import jp.co.dwango.marubatsu.board.Empty
//import jp.co.dwango.marubatsu.board.Maru
//import jp.co.dwango.marubatsu.board.Batsu
//上記4つのインポートを1行で表記
//import jp.co.dwango.marubatsu.board.{CellState, Empty, Maru, Batsu}
//インポート時に別名をつける(1行のimport文に、別名をつけたものと、つけなかったものを混在できる)
import jp.co.dwango.marubatsu.board.{CellState, Empty, Maru => MaruState, Batsu => BatsuState}

//別パッケージのクラスを、本パッケージのクラスと対応付ける
package object game {

  //同一パッケージからしかアクセスできないよう、アクセス修飾子を付与
  private[game] def toWinner(cellState: CellState): Winner = cellState match {
    /*
    case board.Maru => game.Maru
    case board.Batsu => game.Batsu
    case board.Empty => game.NoWinner
    */
    //import時に別名をつけたので、対応付ける時の表記も変更
    case MaruState => Maru
    case BatsuState => Batsu
    case Empty => NoWinner
  }
}

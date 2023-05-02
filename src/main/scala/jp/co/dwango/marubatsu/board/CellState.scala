package jp.co.dwango.marubatsu.board

//セルの状態を表すために、まず抽象クラスを宣言し、その具体的なクラスとしてケースオブジェクトを実装する
sealed abstract class CellState
case object Empty extends CellState  //空を表す
case object Maru extends CellState  //⭕️を表す
case object Batsu extends CellState  //❌を表す

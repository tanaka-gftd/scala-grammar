package jp.co.dwango.marubatsu.board

//セルの状態を表すために、まず抽象クラスを宣言し、その具体的なクラスとしてケースオブジェクトを実装する
/*
  board パッケージにあるインタフェースは、
  marubatsuパッケージ内からしかアクセスできないよう、
  アクセス修飾子として、private[marubatsu]を付与しておく
*/
private[marubatsu] sealed abstract class CellState
private[marubatsu] case object Empty extends CellState  //空を表す
private[marubatsu] case object Maru extends CellState  //⭕️を表す
private[marubatsu] case object Batsu extends CellState  //❌を表す

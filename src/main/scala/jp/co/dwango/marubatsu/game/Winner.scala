package jp.co.dwango.marubatsu.game

//勝者が誰かを表すクラスを定義
/*
  パッケージが異なれば、お案じ名前の型を、別な意味として使うことも可能。
  なので、MaruとBatsuは同名のものがBoardパッケージにも存在しているが、
  パッケージが異なるので、コンパイルエラーにならない。
*/
sealed abstract class Winner
case object NoWinner extends Winner  //勝者なし
case object Maru extends Winner  //⭕️プレイヤー側の勝ち
case object Batsu extends Winner  //❌プレイヤー側の勝ち
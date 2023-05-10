package jp.co.dwango.marubatsu.board

//ボードを表す
/*
  Boardクラスはフィールドとして、
  cellsという(Int,Int)で行と列を指定してセルの状態を受け取れるMapと、
  次に置くべきCellStateのnextを所有している。
*/
/*
  board パッケージにあるインタフェースは、
  marubatsuパッケージ内からしかアクセスできないよう、
  アクセス修飾子として、private[marubatsu]を付与しておく
*/
private[marubatsu] class Board(private[marubatsu] val cells: Map[(Int, Int), CellState],
                               private[marubatsu] val next: CellState) {

  /*
    ボードの状態を更新(正確には、内容を変えた新しいボードを作成する)メソッド
    cellsの状態とnextを更新した、新しいBoardを作成＆取得する。
    ここで利用されているgetNextメソッドは、下で定義されている。
  */
  private[marubatsu] def put(row: Int, column: Int): Board = {
    new Board(cells + ((row, column) -> next), getNext(next))
  }

  /*
    次のターンに置く図柄を取得するメソッド
    CellStateは別ファイルで定義してある。
    (同名パッケージからは特になにもしなくても、クラスを呼び出せるようだ)
    パターンマッチで記述
      •何も置いていない(ゲーム開始前)なら、何もしない
      •⭕️の次は❌
      •❌の次は⭕️

    private[...]
      private修飾子よりも厳しい条件。指定した場所からしかアクセスできない。
      例えば private[this]とすると、本クラスのインスタンスからしかアクセスできない。
  */
  private[this] def getNext(current: CellState): CellState = {
    current match {
      case Empty => Empty
      case Maru => Batsu
      case Batsu => Maru
    }
  }

  /*
    指定された位置がEmpty(=⭕️や❌が書けるセル)であるかどうかを確認するメソッド。
    Gameクラスから利用する。
  */
  def canPut(row: Int, column: Int): Boolean = cells((row, column)) == Empty

  /*
    コンソール表示用
    元々定義されているtoStringメソッドをオーバーライド
    •変数cellsには、各セルの状態を保存したHashMap
    •変数nextには、次に置ける図柄
    が、それぞれ格納されている
  */
  override def toString = s"Board($cells, $next)"
}


//Boardクラスのコンパニオンオブジェクト
/*
  0から2までの添字の行と列が全てEmpty状態のセルで埋められ、
  最初に置ける図柄をMaruとしたBoardクラスのインスタンスを作成する、というファクトリメソッド(apply)を用意しておく

  ゲーム開始時にボードを作成するのは、こちらのファクトリメソッドを使用している。
*/
object  Board {

  //ファクトリメソッド
  def apply(): Board = {

    /*
      for式とyieldを利用し、ループから((Int,Int)->CellState)型が含まれるシーケンスを作り、
      それをtoMapメソッドでMap型に変換している。
    */
    val keyValues = for(row <- 0 to 2; column <- 0 to 2) yield (row, column) -> Empty

    //作成した値を元に、Boardクラスのインスタンスを作成
    //(最初に置ける図柄は、Maruにしておく)
    new Board(keyValues.toMap, Maru)
  }
}

/*
  Scalaのアクセス修飾子についてのメモ (Javaとは仕様が若干異なるので注意)
    •アクセス修飾子なし.........どこからでもアクセス可能(Javaのpublicと同様)
    •protected...............自分(自クラス)と、自分を継承した子クラスからのみアクセス可能
    •private.................自分(自クラス)からのみアクセス可能
    •protected[パッケージ名]...指定したパッケージと、自分を継承した子クラスからアクセう可能(*1)
    •private[パッケージ名].....指定したパッケージからのみアクセス可能(*1)
    •protected[クラス名]......指定したクラスと、自分を継承した子クラスからアクセス可能(*2)
    •private[クラス名]........指定したクラスからアクセス可能(*2)
    •protected[this].........自分(自インスタンス)と、自分を継承したクラスかアクセス可能(*3)
    •private[this]...........自分(自インスタンス)からのみアクセス可能(*3)

  *1...パッケージ名には、自分が所属しているパッケージだけ指定可能
  *2...自分を内包しているクラス(自分の外側のクラス)のみ指定可能
  *3...単なるprotected•privateは、自分と同じクラスの別のインスタンスにアクセス可能らしい？(サイトによって解説が異なる)が、
       thisを付けると、同一クラスであっても、別インスタンスからにはアクセスできなくなる
*/

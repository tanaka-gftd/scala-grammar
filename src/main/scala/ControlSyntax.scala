/* 基礎的なScala構文の学習 */


object ControlSyntax  {

  /*
    Scalaは関数型プログラミングなので、"式"による構文が多い。
    "式"なので、実行時は必ず評価されてその結果を返す。(何もない値も含む)
    また、"式"は変数や定数に格納できる
  */


  //{}式(brace式)
  //式として評価された場合、評価結果として最後の値が返される(ここだと、"foo"は利用されない不要な式となる)
  def bar(): String = {
    "foo"
    "bar"
  }


  //if式(if文ではない)
  //if式は、JavaScriptの三項式みたいなイメージ
  def printOver18(age: Int): Unit = {
    val message = if (age < 18) {
      "18歳未満です"
    } else {
      "18歳以上です"
    }
    println(message)
  }


  //while式の返り値を表示してみる
  //while式自体は何も返さないので、本関数の返り値は()となる(この値をUnitという)
  //Unitは、他言語でのvoidのようなもの
  def printWhiteResult(): Unit = {
    var i = 0
    val result = while(i < 10) i = i + 1
    println(result)
  }


  //Scalaでは多重ループの一つのfor式で記載できる
  //forの()の中にif句を追加することで、ループの実行条件を指定できる(今回は、xとyの値が異なる時だけループ処理する)
  //to num ... numを含む(いわゆる=<)
  //until num ... numを含まない(いわゆる<)
  def doubleLoop(): Unit = {
    for(x <- 1 to 5; y<-1 until 5 if x != y ){
      println("x = " + x + " y  = " + y)
    }
  }


  //Scalaでは処理が少ない時に{}を省略して記述できる
  //本関数の実装は他言語で言うところのforEach文
  //コレクションの各要素を表示していく
  def collectionLoop(): Unit =
    for(e <- Seq("A","B","C","D","E")) println(e)


  //こちらの関数でも{}を省略して記述している
  //yield...与えられたコレクションの各要素に対して処理を加えた、新しいコレクションを返す (元のコレクションは変更されず保持される 非破壊的)
  def generateCollection(): Seq[String] =
    for(e <- Seq("A","B","C","D","E")) yield "pre" + e
}

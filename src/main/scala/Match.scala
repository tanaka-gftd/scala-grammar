/* scalaでのパターンマッチの学習 */


object Match {

  //match式(他言語で言うところのswitch文)
  //1行ずつ判定され、一致した時点で自動で処理を終える(いわゆるbreak句は不要)
  //for式の時のように、caseにif式(いわゆるガード句)を追加することで条件をフィルタリングできる
  def intToName(num: Int): String = {
    num match {
      case 1 => "one"
      case 2 => "two"
      case _ => "other"  //ここでの_(アンダーバー)はワイルドカード(switch文で言うところのdefaultとして使用)
    }
  }


  //match式で、複数のパターンで同じ結果を返したい場合は、|(パイプ)でまとめられる
  def message(message: String): String = {
    message match {
      //case "good" => "game"
      //case "bad" => "game"

      //上記の2つのパターンを|(パイプ)で1行にまとめる
      case "good" | "bad" => "game"
    }
  }


  //match式では、パターンマッチにより特定の値を取り出すこともできる
  def patternMatch(): Unit = {

    //Seqを作成
    val seqA = Seq("A", "B", "C", "D", "E")

    /*
       seqAの要素がcaseで提示されたSeqの要素とマッチするか判定する。
       今回は先頭の要素で一致したので、
       残りのb, c, d, e の変数にはそれぞれ seqAの2番目以降の要素が束縛されて、
       => 以降の右辺の式が評価される。
    */
    seqA match {
      case Seq("A", b, c, d, e)  =>  //b, c, d, eは変数
        println("b = " + b)  //以降の変数b, c, d, eには、seqAの各要素が束縛されて評価される
        println("c = " + c)
        println("d = " + d)
        println("e = " + e)
      case _ =>
        println("nothing")  //一致しなかった場合
    }
  }


  //入れ子のパターンマッチ
  def patternMatchNest(): Unit = {

    //入れ子のSeqを作成(いわゆる２重配列)
    val seqB = Seq(Seq("A"), Seq("B", "C", "D", "E"))

    /*
      match式で、先頭がSeq("A")であるという入れ子になったシーケンスのパターンを記述する。
      また、パターンの前に @ がついているのは asパターンと呼ばれ、
      @ の後を続くパターンにマッチする式を @ の前の変数に束縛します。
      as パターンは、パターンが複雑な場合に便利で、
      これを用いると、パターンの一部だけを切り取ることができる。

      ここでは@の前に記述されている変数aに、seqBとcaseで提示されたSeq("A")がマッチした要素が格納され、
      変数xにseqBの残りの要素が格納される
    */
    seqB match {
      case Seq(a@Seq("A"), x) =>
        println(a)
        println(x)
      case _ => println("nothing")
    }
  }


  //型によるパターンマッチ
  /*
    特定の型に所属する値に関してもその型を利用してパターンマッチができる。
    その場合には、 名前: マッチする型 の形で case 句を記述する。
  */
  def patternMatchType(x: Any): Unit = {  //Any型は、全ての派生元となる型
    /*
      型の派生元
       Any型...scalaにおいて、全ての型•クラスの派生元となるクラス(JavaでいうObject型)
        AnyVal型...Any型の直下のクラスで、IntやDouble、UnitやBoolean、Charなどの値の元となるクラス(Javaでいうプリミティブ型)
        AnyRef型...Any型の直下のクラスで、AnyVal型以外の全ての型•クラス(ユーザー定義型含む)の元となるクラス(Javaでいう参照型)

      例えば、String型はAnyRef型から派生するので、文字列はAnyRef型としても宣言できる
    */
    x match {
      //変数nに引数で渡したものが入り、どんな型とマッチするか評価される
      //マッチした場合は、=> 以降の右辺の式が評価される
      //マッチした場合、引数で渡したものが自動でキャストされる(ここでは、Any型からInteger型、もしくはString型へと変換される)
      case n: Integer => println("数値です！")  //こちらでマッチすると、Integer型へと自動でキャスト
      case n: String => println(s"文字列です！ 文字長は${n.length}")  //こちらでマッチすると、String型へと自動でキャスト
    }
  }


  //matchを使った型判定では、ジェネリクスの型までは判定できない
  //Scalaが動いているJVMの仕様で、こういった利用のときにはジェネリクスの型の情報が消えてしまうため
  //なので、ジェネリクスがあるコレクションに対してのパターンマッチは行わず、ワイルドカードでお茶を濁そう
  //ジェネリクス...Seq[Int] や Seq[String] のようなコレクションの型において [] で指定している、中に入る要素の型の指定
  def patternMatchGenerics(): Unit = {
    val obj: AnyRef = Seq("a","b","c")
    obj match {
    /*
      //objのジェネリクスの型(String)の情報が抜け落ちてしまうため、case v: Seq[Int]の行でマッチしてしまう
      case v: Seq[Int] => println("Seq[Int]")  //この行での判定でマッチしてしまう
      case v: Seq[String] => println("Seq[String]")  //こっちでマッチして欲しい
    */

      //JVMの使用上、match式でジェネリクスの型の判定はできないので、_(アンダーバー)をワイルドカードとして使用してお茶を濁す
      case v: Seq[_] => println("Seq[_]")
      case _ => println("nothing")
    }
  }
}

/* ケースクラス(case class)の学習 */



class Point(val x: Int, val y: Int){

  override def toString = s"Point($x, $y)"
}

object Point {
  def apply(x: Int, y: Int): Point = new Point(x, y)
}



//ケースクラス
/*
  ケースクラスの宣言方法...caseと付ける

  ケースクラスの特徴：
   •メンバーフィールドの公開(valなので更新不可)
   •toStringの実装
   •newの代わりのファクトリメソッド
     ..以上を自動的に用意してくれるクラス

   ケースクラスは自動的にvalとしてフィールドを持ってしまうため、
   基本的にはvarで宣言するような更新可能フィールドを持たない。
   そのため、ケースクラスの値を更新するときは、copyメソッドを使って値を更新した新しいケースクラスを作成する。

   ケースクラスから値を取り出すには、ケースクラス.フィールド名 でアクセスできる他、
   タプルと同じように分割代入のような値の取り出し方もできる。
   例えば、以下の実装の場合だと、
     val cp = CPoint(4, 7)
     val CPoint(a, b) = cp
   とすることで、aとbという変数に値を代入できる。

   同値性について：
     同じクラスから作成したインスタンスは、保持しているフィールドとその値が同じであっても、それぞれ別のものとして扱われる。
     それに対し、同じケースクラスから作成したインスタンスの場合は、保持しているフィールドとその値が同じだと、同じものとして扱われる。(ハッシュは違う)
     このような性質があるため、ケースクラスはオブジェクトでありながら、より値のようにプログラムの中で扱うことができる。
*/
case class CPoint(x: Int, y: Int)



//通常のクラスにおける同値性について
/*
  以下の実装はxとyの値を利用してhashCodeを生成し、
  同値性もその2つを比較して同じであれば、同じとするという内容の実装。
  (ケースクラスの同値性を、通常のクラスでも再現した実装)

  元から実装されているhashCodeメソッドとequalsメソッドを、オーバーライドしている。
  hashCodeメソッド...識別用のハッシュコードを返す
  equalsメソッド...2つが等しいかどうかを判定する
  isInstanceOf[type]...あるインスタンスがtype型かどうかを判定する

  N予備校と同じように実装しても、上手くいかない。
  ScalaやIDEAの仕様が変更になったのだろうか？(N予備校のScalaコースは、6年前に作成されたもののようだ)
*/
class Point_2(val x: Int, val y: Int){

  override def toString = s"Point_2($x, $y)"

  private def canEqual(other: Any): Boolean = other.isInstanceOf[Point_2]

  override def equals(other: Any): Boolean = other match {
    case that: Point_2 =>
      that.canEqual(this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

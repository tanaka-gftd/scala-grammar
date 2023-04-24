//sealed...宣言したファイル内でしか利用できないクラス
sealed abstract class DayOfWeek
case object Sunday extends DayOfWeek
case object Monday extends DayOfWeek
case object Tuesday extends DayOfWeek
case object Wednesday extends DayOfWeek
case object Thursday extends DayOfWeek
case object Friday extends DayOfWeek
case object Saturday extends DayOfWeek
/*
  N予備校と同じように実装しても、上手くいかない。
  ScalaやIDEAの仕様が変更になったのだろうか？(N予備校のscalaコースは、6年前に作成されたもののようだ)
*/
object DayOfWeek {
  def toNum(day: DayOfWeek): Int = {
    day match {
      case Sunday => 1
      case Monday => 2
      case Tuesday => 3
      case Wednesday => 4
      case Thursday => 5
      case Friday => 6
      case Saturday => 7
    }
  }
}




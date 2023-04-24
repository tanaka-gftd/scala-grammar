/*
  ケースクラスとパターンマッチを使うとデータが持てるという性質を使って、
  四則演算を表す構文木を作ることもできる。

  以下の実装について：
  構文木の全てのノードはExpを継承し、
  二項演算を表すノードはそれぞれの子としてlhs(左辺),rhs(右辺)を持つようなデータ構造を考える。
  葉ノードとして、Addを加算、Subを減算、Mulを乗算、Divを除算とし、整数リテラルをLitで表現する。
  パターンマッチと再起的処理で構文木のように計算する。
*/
sealed abstract class Exp
case class Add(lhs: Exp, rhs: Exp) extends Exp
case class Sub(lhs: Exp, rhs: Exp) extends Exp

case class Mul(lhs: Exp, rhs: Exp) extends Exp

case class Div(lhs: Exp, rhs: Exp) extends Exp

case class Lit(value: Int) extends Exp

object Exp {
  def calc(exp: Exp): Int = exp match {
    case Add(l,r) => calc(l) + calc(r)
    case Sub(l,r) => calc(l) - calc(r)
    case Mul(l,r) => calc(l) * calc(r)
    case Div(l,r) => calc(l) / calc(r)
    case Lit(v) => v
  }
}
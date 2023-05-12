/*
  継承と異なり、自分型アノテーションは型の循環参照を行える。
  以下の例のように、トレイトの相互参照ができる。
*/
trait Robot3 {
  self: Greeter3 =>

  def name: String

  def start(): Unit = greet()
}

//コンパイルできる
trait Greeter3 {
  self: Robot3 =>

  def greet(): Unit = println(s"My name is $name")
}


//継承では相互参照できない
/*
trait Robot4 extends Greeter4 {
  def name: String
  def start(): Unit = greet()
}

trait Greeter4 extends Robot4 {
  def greet(): Unit = println(s"My name is $name")
}
*/


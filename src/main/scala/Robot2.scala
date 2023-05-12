/*
  Robot.scalaのコードを、自分型アノテーションを使わずに書いてみる。

  この場合、普通に継承していく形になるので、
  trait HelloGreeter2 extends Greeter2 {
    def greet(): Unit = println("Hello!")
  }
  ↓
  val r2: Robot2 = new Robot2 with HelloGreeter2
  ↓
  r2.start()
  とする事で、Hello! という文字列が表示されるのはもちろんの事、
  r2.greet()
  とする事でも、Hello! という文字列が表示される。

  Robot2が利用するためにあるGreeter2のメソッドを外から呼び出せてしまう事は、必ずしもいい事とは限らないので、
  この点で自分型アノテーションを使うメリットがあると言える。
  逆に、単に依存性を注入できればよいという場合には自分型アノテーションは必要がないとも言える。
*/

trait Greeter2 {
  def greet(): Unit
}

trait Robot2 extends Greeter2 {
  def start(): Unit = greet()
  override final def toString = "Robot2"
}
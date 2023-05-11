/* トレイトの学習 */
/*
  トレイトの特徴：

  クラスやトレイトに、振る舞いを追加する。

  クラスに似ているが、以下の点に違いがある。
    •トレイトを継承することを「ミックスイン」と呼ぶ(逆に、クラスの継承はミックスインとは呼ばない)
    •複数のトレイトを、一つのクラスやトレイトにミックスインできる(1個目はextends、2個目以降はwithでミックスインする)
    •直接インスタンス化できない
    •クラスパラメータ(コンストラクタ引数)を撮ることができない

  抽象クラス(abstract class)にも似ているが、以下の点に違いがある。
    •一つのクラスに複数ミックスインできる
    •abstract classはis-aの関係で継承して使用するが、トレイトはあくまでも振る舞いを追加するという意味合いで使用する

  なお、トレイトは抽象クラス(abstract class)と同様に、必ずしもメソッドを実装する必要はない。
*/

//トレイト作成
trait Fillable {
  def fill(): Unit = println("Fill!")
}

//トレイト作成
trait Disposable {
  def dispose(): Unit = println("Dispose!")
}

//クラス宣言
class Paper

//トレイトをミックスイン
//Paperクラスを継承したPaperCupクラスを作成し、Fillableトレイト,Disposableトレイトをミックスインする
//1つ目の継承またはミックスインにはextendsキーワードを使い、2つめ以降のミックスインにはwithキーワードを使う
class PaperCup extends Paper with Fillable with Disposable



//トレイトを継承した際、メソッドが衝突した場合の挙動について(いわゆる菱型継承問題)
/*
  以下の例では、
  TraitBとTraitCが、共にTraitAをミックスインしており、
  TraitAで定義されたgreetメソッドが、TraitBとTraitCにおいてそれぞれ異なった形で実装されている。
  ↓
  この場合、TraitB,TraitCをミックインしたクラスでは、greetメソッドの実装が衝突してエラーとなる。
  ↓
  エラーの回避方法
  その1...クラス内でgreetメソッドをオーバーライドする → sampleClass1
  その2...片方のトレイトで実装されているgreetメソッドを使いたい場合 → superでトレイトを指定しオーバーライド → sampleClass2
  その3...いずれのトレイトのgreetメソッドを使いたい場合 → greetメソッド呼び出し時にトレイトを明示する → sampleClass3
*/
trait TraitA {
  def greet(): Unit
}

trait TraitB extends TraitA {
  def greet(): Unit = println("Good morning!")
}

trait TraitC extends TraitA {
  def greet(): Unit = println("Good evening!")
}

//菱型継承問題におけるメソッド衝突の回避方法その1
//クラス内でメソッドをオーバーライドして、再定義する
class sampleClass1 extends TraitB with TraitC {
  override def greet(): Unit = println("How are you?")
}

//菱型継承問題におけるメソッド衝突の回避方法その2：いずれか一つのトレイト内で実装されたものだけ使いたい場合
//使用したい方のトレイトのメソッドをsuperで呼び出し、オーバーライドする
class sampleClass2 extends TraitB with TraitC {
  override def greet(): Unit = super[TraitB].greet()  //この場合、トレイトBで実装されたgreetメソッドが使用できる
}

//菱型継承問題におけるメソッド衝突の回避方法その3：ミックスインされた複数,あるいは全てのトレイト内で実装されたメソッドを使いたい場合
//メソッドを呼び出し時に、どのトレイトのメソッドを使用するかを明示するようにする
//便利そうに見えるが、継承関係が複雑な場合は、明示的に呼ぶのが面倒くさくなる
class sampleClass3 extends TraitB with TraitC {
  override def greet(): Unit = {
    super[TraitB].greet()
    super[TraitC].greet()
  }
}


//Scalaの菱形継承問題への対応方法として、「線形化」も挙げれる
//トレイトの線型化機能...トレイトがミックスインされた順番を、トレイトの継承順番と見なすこと
/*
  以下の例を考えてみる。
  以下の例では、上記で解説した菱型継承問題の例との違い、
  トレイトを継承したトレイト内でのgreetメソッド実装時に override修飾子が付いている点。
  こうする事でトレイトの継承順番が線形化されて、後からミックスインしたトレイトが優先されるようになる。
      → 要するに、後に記述したトレイトの方のメソッドが優先されて呼び出される
*/
trait TraitD {
  def greet(): Unit
}

trait TraitE extends TraitD {
  override def greet(): Unit = println("Good morning!")
}

trait TraitF extends TraitD {
  override def greet(): Unit = println("Good evening!")
}

class Class1 extends TraitE with TraitF  //(new Class1).greet() で、TraitFのgreetメソッド呼び出し
class Class2 extends TraitF with TraitE  //(new Class2).greet() で、TraitEのgreetメソッド呼び出し


//superを使うことで線形化された親トレイトを使うこともできる
trait TraitG {
  def greet(): Unit = println("Hello!")
}

trait TraitH extends TraitG {
  override def greet(): Unit = {
    super.greet()
    println("My name is Terebi-chan.")
  }
}

trait TraitI extends TraitG {
  override def greet(): Unit = {
    super.greet()
    println("I like niconico.")
  }
}

class Class3 extends TraitH with TraitI
class Class4 extends TraitI with TraitH


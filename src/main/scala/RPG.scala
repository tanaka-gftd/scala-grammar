/* クラスの学習のために、RPG的な展開を作成 */


//乱数生成のためのライブラリをインポート
import java.util.Random

//ゲーム部分
object RPG extends App {
  val random = new Random  //乱数作成インスタンス作成 乱数は逃走判定と、モンスターの能力設定に利用する
  val monsterCount = 5  //出現モンスター数
  val hero = new Hero(400, 50)  //ヒーロー作成
  var monsters = for (i <- 1 to monsterCount)
    yield new Monster(random.nextInt(120), random.nextInt(120), false)  //モンスター作成

  //本コード起動時に表示されるメッセージ
  println(
    //ダブルクオーテーション3個で囲むと、複数行の文字列が入力可能になる
    //stripMargin...文字列を整形してくれる
    // | ...stripMarginメソッド使用、ここが左端になるよう整形してくれる
    s"""あなたは冒険中の${hero}であり、
       |${monsterCount}匹のモンスターが潜んでいる洞窟を抜けなければならない。
       |【ルール】:
       |1を入力してEnterキーを押すと攻撃、それ以外を入力すると逃走となる。
       |逃走成功確率は50%。逃走に失敗した場合はダメージをうける。
       |一度でもダメージを受けるとモンスターの体力と攻撃力が判明する。
       |またモンスターを倒した場合、武器を奪いその攻撃力を得ることができる。
       |--------------------------------------------------
       |未知のモンスターが現れた。""".stripMargin
  )

  //デバッグ用
  //println(hero)
  //println(monsterCount)
  //println(monsters)
  //println(monsters(0))
  //println(monsters(1))
  //println(monsters(2))
  //println(monsters(3))
  //println(monsters(4))


  //モンスターの残り数が一匹以上いた場合、while内の処理を繰り返す
  //各モンスターのデータはVectorで保持されている
  //Vector...Seqの一種でランダムアクセスと更新が得意(immutable) JavaにおけるArrayList風味  継承ルート:Seq > IndexSeq > Vector
  while(!monsters.isEmpty){

    //戦う相手として、monsters(Vector)の先頭のデータを抽出
    val monster = monsters.head

    //scala.io.StdIn.readLine...Scalaで1行読み込む標準入力
    //readLine()に引数として文字列を渡すと、その文字列がコンソールに表示される(この場合入力されるのは、表示された文字列以降の文字)
    val input = scala.io.StdIn.readLine("【選択】: 攻撃[1] or 逃走[0] >")

    //標準入力で入力された内容で、分岐する
    //攻撃or逃走処理の結果をコンソールに表示する
    if(input == "1"){
      //攻撃
      hero.attack(monster)  //heroクラスのattackメソッド呼び出し
      println(s"あなたは${hero.attackDamage}のダメージを与え、${monster.attackDamage}のダメージを受けた。")
    } else {
      //逃走
      if(hero.escape(monster)){  //heroクラスのescapeメソッド呼び出し
        println("あなたは、モンスターから逃走に成功した。")
      } else {
        println(s"あなたは、モンスターから逃走に失敗し、${monster.attackDamage}のダメージを受けた。")
      }
    }

    //攻撃後or逃走後、現在のヒーローやモンスターの状態を表示
    println(s"【現在の状態】: ${hero}, ${monster}")

    //ヒーローの体力に応じて分岐
    if(!hero.isAlive){
      //ヒーローの体力が尽きたなら、ゲームオーバー
      println(
        """--------------------------------------------------
          |【ゲームオーバー】: あなたは残念ながら負けてしまった。"""".stripMargin)

      //ゲーム終了(0は正常終了を示すステータスコード)
      System.exit(0)
    } else if (!monster.isAlive || monster.isAwayFromHero) {
      //モンスターを倒した or モンスターから逃走成功した場合の処理

      //モンスターを倒せたら、ヒーローの攻撃力をモンスターの攻撃力で上書きする(より強い武器をモンスターから奪う、みたいな感じ)
      //(モンスターの攻撃力の方が低い場合は、上書きしない 弱い武器は不要という感じ)
      if(!monster.isAwayFromHero) {
        println("モンスターは倒れた。そしてあなたは、モンスターの武器を奪った。")
        if(monster.attackDamage > hero.attackDamage) hero.attackDamage = monster.attackDamage
      }

      //モンスターのデータを保存しているVectorから、倒したモンスターのデータを除外する
      //tail...先頭要素を除いた新しいシーケンスを生成する.
      monsters = monsters.tail

      println(s"残りのモンスターは${monsters.length}匹となった。")

      //残りモンスターが1匹以上いた場合、次のメッセージを表示する(そして、while句の最初に戻る)
      if(monsters.length > 0){
        println(
          """----------------------------------------------
            |新たな未知のモンスターがあらわれた。""".stripMargin)
      }
    }
  }

  //全ての敵との戦いを終えた後(倒すだけでなく、逃走でもOK)
  println(
    s"""
       |【ゲームクリア】: あなたは困難を乗り越えた。新たな冒険に祝福を。
       |【結果】: ${hero}""".stripMargin
  )
  System.exit(0)  //ゲーム終了
}


//生き物を表すクラス
//コンストラクタ引数のhitPointは体力、attackDamageは攻撃力を示す(varがついているので、外部からアクセスできる)
//abstractを使って宣言することで、抽象クラスとして定義
//抽象クラス...「継承」をして使うことが求められるクラス。抽象クラス自体はインスタンス化できない(newするとエラーになる)
abstract class Creature(var hitPoint: Int, var attackDamage: Int){

  //活動できているかどうかを判断する変数
  //this...そのクラスのインスタンス自体を表す
  def isAlive(): Boolean = this.hitPoint > 0
}



//Creatureクラスを継承してヒーローを表すクラスを定義
//継承時は継承元のクラス名だけでなく、継承元のコンストラクタ引数も記載する必要がある
//継承しているので、Creatureクラスで宣言したフィールドやメソッドをそのまま利用できる
//各コンストラクタの引数名に_を付けているのは、Creatureクラスで宣言された変数名との衝突を防ぐため
class Hero(_hitPoint: Int, _attackDamage: Int) extends Creature(_hitPoint, _attackDamage) {

  //ヒーローが攻撃した時と、モンスターから攻撃を受けた時のダメージ処理
  //モンスターの体力とヒーローの体力に、お互いの攻撃力分減らしたものを再代入している
  def attack(monster: Monster): Unit = {
    monster.hitPoint = monster.hitPoint - this.attackDamage
    this.hitPoint = this.hitPoint - monster.attackDamage
  }

  //逃げようとした際の処理
  def escape(monster: Monster): Boolean = {

    //逃走が成功したかどうかを乱数で判定 逃走成功率は2/3
    //nextInt()...0から引数に指定した値未満の整数を返します。
    //contains(値)...引数として渡した値が、コレクション(Seq,Set,Map)内に要素として存在するかどうかを判定(存在すればtrue)
    val isEscape = Seq(0,1).contains(RPG.random.nextInt(3))

    if(!isEscape){
      //逃走失敗時の処理：ヒーローはダメージを受ける
      this.hitPoint = this.hitPoint - monster.attackDamage
    } else {
      //逃走成功時の処理：Monsterクラスで定義されている isAwayFromHero をtrueにする
      monster.isAwayFromHero = true
    }

    //逃走フラグ失敗を返す
    isEscape  // 逃走成功時は、!escapeFailed
  }

  //オーバーライド...親クラスで定義されたメソッドを子クラスで再定義することで、子クラス上で親クラスのメソッドを上書きすること
  /*オーバーライドとオーバーロードの混同に注意(オーバーロード...引数の数や型が異なる、同じ名前のメソッドを複数用意すること)*/
  /*
    Scalaにおける全てのclassはAnyRefクラスを暗黙的に継承している。(AnyRef型は全ての参照型の親元)
    また、ScalaのAnyRefクラスの実体はJavaのObjectクラスに該当するので、JavaのObjectクラスのメソッドを呼び出せる。
    以上をより、ここでオーバーライドしている toStringメソッドは、元々はJavaのObjectクラスで定義されたメソッドである。

    JavaのObjectクラスのtoStringメソッド：
      println関数などでコンソールに出力した際、文字列として表示するための関数で、

    println(クラス名)で実行すると、デフォルトでは {クラス名}@{ハッシュで示されるID} が表示される
    {クラス名}@{ハッシュで示されるID}と表示される部分を、Hero(体力:${hitPoint}, 攻撃力${attackDamage}) と表示されるようオーバーライド
  */
  override def toString = s"Hero(体力:${hitPoint}, 攻撃力:${attackDamage})"  //toStringの定義元は遠い親クラス
}



//Creatureクラスを継承してモンスターを表すクラスを定義
//継承時に、子クラス独自の変数をコンストラクタで宣言できる(ここでは isAwayFromHero: Boolean が該当、varを付けたので、外部から参照可能)
//isAwayFromHero...モンスターから逃げるのに成功した時にtrueとなる変数
class Monster(_hitPoint: Int, _attackDamage: Int, var isAwayFromHero: Boolean)
  extends Creature(_hitPoint, _attackDamage){
  override def toString = s"Monster(体力:${hitPoint}, 攻撃力:${attackDamage}, ヒーローから離れている:${isAwayFromHero})"
}


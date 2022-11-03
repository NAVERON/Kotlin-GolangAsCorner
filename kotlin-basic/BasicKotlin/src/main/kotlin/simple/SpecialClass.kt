package simple


// 被继承的类需要使用 open关键字
class SpecialClass : NormalClass {  // 一个类必须要有一个主构造器

    constructor(id : Int) : super(id)
    constructor(id : Int, name : String) : super(id, name) {
        println("继承类 继承构造器")
    }

    final override fun test() {  // 加上 final 禁止 子类重写
        // 调用父类 可以使用super前缀
        println("被覆盖的  父类方法, 需要使用open修饰")
    }
}
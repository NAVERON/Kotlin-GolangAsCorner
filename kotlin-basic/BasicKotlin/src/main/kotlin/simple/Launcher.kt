package simple

import java.io.File
import java.lang.IllegalArgumentException


/**
 * 案例教程  https://play.kotlinlang.org/byExample/overview
 */

fun main(args : Array<String>) {
    println("hello simple kotlin")
    println(args.contentToString())

    testList()
    testMap()
    abstractUsage()
    exchangeValue()

    // 延迟计算 第一次使用时计算
    val p : String by lazy { "lazy String --> p" }
    println("lazy string = $p")

    // 单例对象测试
    println("单例对象 --> ${SingletonObject.name}")

    // 调用java file 不为null 输出size
    val file = File("test").listFiles()
    println("输出文件路径 ---> ${file?.size ?: "empty"}")

    // 使用颜色转换 when
    val colorTransForm = transFormColor("YELLOW")
    println("颜色转换 --> $colorTransForm")  // 字符串变量替换
    println("使用lamda表达式 函数 --> ${transFormColor0("BLUE")}")

    val ifValue = if(colorTransForm == 0){
        9
    }else if(colorTransForm == 1){
        8
    }else if(colorTransForm == 2){
        7
    }else {
        -1
    }
    println("使用if直接赋值 --> $ifValue")

    // 调用一个对象
    val myTurtle = Turtle()
    with(myTurtle){
        penDown()
        for(i in 0.. 3){
            forward(2)
            turn(20)
        }
        drawPicture()
        penUp()
    }
    val normalClass = NormalClass()
    println("normal class --> $normalClass")
    val normalClass2 = NormalClass(3, "ded")
    println("使用次 构造器初始化 --> $normalClass2")
    val classApply = NormalClass(1, "test").apply {  // 再次修改class属性值
        id = 12
        name = "apply"
        status = true
    }
    println("class apply 生成类对象 --> ${classApply.toString()}")


}

fun testList() {
    // list
    val friuts = listOf<String>("banana", "avocado", "apple", "kiwifruit")
    friuts.filter { it.startsWith("a") }
        .map { it.uppercase() }
        .sortedBy { it }
        .forEach { println(it) }
}

fun testMap() {
    // map
    val map = mapOf<Int, String>(1 to "first", 2 to "second", 3 to "third")
    println(map.entries.size)
    println(map[2] + ", " + map.toList()[0])
}

fun abstractUsage() {
    // 虚类实现
    val serverImpl = object : AbstractClassServer() {
        override fun doSomeThing() {
            println("here is do sth implement")
        }

        override fun sleep() {
            println("I'm sleeping")
        }
    }
    println("实现类调用 --> ${serverImpl.doSomeThing()}")
}

fun transFormColor(color : String) : Int {
    return when(color){
        "RED" -> 0
        "BLUE" -> 1
        "YELLOW" -> 2
        else -> throw IllegalArgumentException("illegal color string")
    }
}
// lamda 表达式写法
fun transFormColor0(color : String) : Int = when(color) {
    "RED" -> 0
    "BLUE" -> 1
    "YELLOW" -> 2
    else -> throw IllegalArgumentException("illegal color")
}

fun exchangeValue() {
    var a = 10
    var b = 20
    println("输出 a = $a b = $b 值")
    a = b.also { b = a }

    println("转换后 === 输出 a = $a b = $b 值")
}

// 类似一个创建接口实现类的工厂
fun interfaceFactory() : NormalInterface {
    return InterfaceImpl()
}





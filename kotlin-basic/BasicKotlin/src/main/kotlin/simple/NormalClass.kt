package simple

open class NormalClass {
    constructor()  // 主构造器 一般写在类上
    constructor(id : Int) {
        println("构造器输出=====================")
    }
    constructor(id : Int, name : String) : this(id) {
        this.id = id
        this.name = name
        if(this.name.isBlank()){
            status = false
        }
        status = true
    }

    var id = -1
    var name = ""
    var status : Boolean = false

    init {  // 实例化类 会初始化执行
        println("Normal Class init--------------------")
    }

    override fun toString(): String {
        return "NormalClass(id=$id, name='$name', status=$status)"
    }

    open fun test() {
        println("normal function in class")
    }
}
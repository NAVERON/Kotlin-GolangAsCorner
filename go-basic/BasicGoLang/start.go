
package main 

import (
	"fmt"
	"math"
	"math/cmplx"
	"math/rand"
	"runtime"
	"time"
	"strings"
)

const PI = 3.14

func main() {
	fmt.Println("Hello, 世界")
	fmt.Println("hello ", math.Pi)
	fmt.Println("hello", add(1, 3))

	// 变量定义 := 或者 var xx = ... 
	a, b := swap("test1", "test2")
	fmt.Println(a, b)

	// 变量 
	var x, status bool
	var i int = 7
	var e, f, g int = 9, 8, 5
	fmt.Println("hello : ", i, x, status, "\nothers : ", e, f, g)
	fmt.Println("hello random int : ", rand.Intn(10))

	// 一次可以定义多个变量
	var (
		ToBe   bool       = false
		MaxInt uint64     = 1<<64 - 1
		z      complex128 = cmplx.Sqrt(-5 + 12i)
	)
	
	// Println() Printf() 两种标准输出函数  %T %v 类型和值 
	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)

	fmt.Println("conse define --> ", PI)

	basic(10)
	types()

	// 使用函数 
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}
	fmt.Println(hypot(5, 12))  // 需要什么功能, 就传入什么函数 
	fmt.Println(compute(hypot))
	fmt.Println(compute(math.Pow))

	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			pos(i),
			neg(-2*i),
		)
	}

	// 各种方法的定义和使用
	

}

// 闭包函数 
func adder() func(int) int {  // 调用会保存当前的计算结果, 下一次调用继续 
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}


// 自定义方法函数
// 数字相加 
func add(x int, y int) (int) {
	return x + y
}

// 交换字符串 
func swap(a, b string) (string, string) {
	return b, a
}

// 基础逻辑控制的使用 
func basic(limit int) int32 {
	sum := 0
	for i := 0; i < limit; i++ {
		sum += i
	}
	fmt.Printf("输出 sum : %v", sum)

	// for 可以去除前后 
	for ; sum < 20; {
		sum += sum
	}

	// go中 for 替代了while 语句 
	for sum > 30 && sum < 100 {
		sum -= 10
		fmt.Println("输出当前sum和值", sum)
	}

	// 死循环 
	for {
		fmt.Println("进入死循环")
		if sum < 100 {
			sum = 100
			break
		}
	}
	fmt.Println("跳出死循环, 当前sun --> ", sum)

	if sum < 50 {
		fmt.Println("if使用, sum < 50")
	}

	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.\n", os)
	}

	t := time.Now()
	switch {
		case t.Hour() < 12 : 
			fmt.Println("good morning")
		case t.Hour() < 17 : 
			fmt.Println("good afternoon")
		default : 
			fmt.Println("default")
	}

	return 0;
}

// 各种类型的使用 
func types() {
	// 使用指针 类似于c语言中的指针 
	i, j := 20, 30
	var p *int = &i  // 或者  p := &i  定义个赋值 
	fmt.Println("输出当前指针的值 --> ", *p)
	// 修改指针值 相当于直接修改 i 
	*p = 23
	fmt.Println("输出i的值 --> ", i)
	p = &j  // 修改指针  赋值 
	fmt.Printf("标准输出 %T, %v", p, *p)

	// 自定义类型 struct , 类似于 c中的typedef struct
	// 可以替代 java中的 class 
	type Point struct {
		x, y int
	}
	point := Point{2, 3}
	point.x = 12
	fmt.Println("输出自定义结构 数值 --> ", point)
	px := &point
	px.x = 7
	fmt.Println("输出自定义结构 数值 --> ", point)
	point2 := Point{x : 1, y : 2}
	fmt.Println("初始化 Point --> ", point2)

	// 使用数组 
	var arr [3]string 
	arr[0] = "hello"
	arr[1] = "kity"
	arr[2] = "tick"
	arr2 := [3]int {2, 4, 6}
	fmt.Println("输出数组 --> ", arr, arr2, len(arr), cap(arr2))
	var s []string = arr[1:]  // 切分 左闭右开 
	fmt.Println("输出切分后的数组 --> ", s)

	name := []string {
		"Jhon",
		"paul",
		"hello",
	}
	fmt.Println("输出名字 --> ", name)

	// make 函数的使用 
	x := make([]int, 3, 3)
	fmt.Println("数组make初始化 --> ", x)
	// Create a tic-tac-toe board.   多维数组 
	board := [][]string{
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
	}
	// The players take turns.
	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"
	for i := 0; i < len(board); i++ {
		fmt.Printf("%s\n", strings.Join(board[i], " "))
	}

	// map 结构的定义和使用 
	var mapper map[string]Point = make(map[string]Point)
	mapper["test"] = Point{2, 5}
	fmt.Println("输出map --> ", mapper)
	next := map[int]string {
		2 : "hello",
		3 : "test",
	}
	fmt.Println("next map : ", next)
}

// 函数定义的使用 
func compute(fn func(float64, float64) float64) float64 {  // 参数设置为一个函数 
	return fn(3, 4)
}






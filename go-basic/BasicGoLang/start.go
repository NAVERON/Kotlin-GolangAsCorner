
package main 

import (
	"fmt"
	"math"
	"math/cmplx"
	"math/rand"
	"runtime"
	"time"
)

const PI = 3.14

func main() {
	fmt.Println("Hello, 世界")
	fmt.Println("hello ", math.Pi)
	fmt.Println("hello", add(1, 3))

	a, b := swap("test1", "test2")
	fmt.Println(a, b)

	// 变量 
	var x, status bool
	var i int = 7
	var e, f, g int = 9, 8, 5
	fmt.Println("hello : ", i, x, status, "\nothers : ", e, f, g)
	fmt.Println("hello random int : ", rand.Intn(10))

	var (
		ToBe   bool       = false
		MaxInt uint64     = 1<<64 - 1
		z      complex128 = cmplx.Sqrt(-5 + 12i)
	)

	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)

	fmt.Println("conse define --> ", PI)

	basic(10)
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


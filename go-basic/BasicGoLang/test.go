package main

import (
	"fmt"
)

type Adder interface {  // 定义接口和方法 
	Test() string
}

// 实现接口直接赋值即可, 使用方法名同接口的实现 
type Ship struct {
	id int32
	name string
}
func (s *Ship) Test() string {
	fmt.Println("Ship default implement")
	return "Ship default implement"
}

type MyInt int32
func (my MyInt) Test() string {  // 不使用指针 
	fmt.Println("MyInt default implement")
	return "MyInt default implement"
}

func main() {

	fmt.Println("init...")

	// 对于接口的实现, 定义一个接口, 可以指向任意实现其方法的对象--> 方法名唯一 
	// 不需要像java 显式指明实现了哪些接口 
	var a Adder
	s := Ship{0, "ship"}
	a = &s  // 对于自定义对象 需要地址  普通类型直接 = 相当于地址
	a.Test()

	my := MyInt(3)
	a = my
	a.Test()

}


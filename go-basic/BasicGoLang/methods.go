package main

import (
	"fmt"
	"strconv"
	"image"
	"strings"
	"io"
)

type Adder interface {  // 定义接口和方法 
	Test() string
}

// 实现接口直接赋值即可, 使用方法名同接口的实现 
// 不需要 显示的表示 implements 
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

type Person struct {
	Name string
	Age  int
}
func (p Person) String() string {  // fmt 中默认有一个 Stringer接口 实现String 方法相当于实现了 toString 
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}
// Error 接口 
func (p *Person) Error() string {
	return fmt.Sprintf("Error --> name : %v, age : %v", p.Name, p.Age)
}
func runError() error {
	return &Person{"hello", 55}
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

	p := Person{"Arthur Dent", 42}
	z := Person{"Zaphod Beeblebrox", 9001}
	fmt.Println(p, "\n", z)

	// Error 接口的使用 
	if err := runError(); err != nil {
		fmt.Println(err)
	}
	i, err := strconv.Atoi("42")
	if err != nil {
		fmt.Printf("couldn't convert number: %v\n", err)
		return
	}
	fmt.Println("Converted integer:", i)

	// io 包的基础使用
	// 使用 io.Reader 实现了接口, 可以使用read方法读取byte数据 
	r := strings.NewReader("Hello, Reader!")
	b := make([]byte, 8)  // 设定每次读取的大小 8byte 
	for {
		n, err := r.Read(b)
		fmt.Printf("n = %v err = %v b = %v\n", n, err, b)
		fmt.Printf("b[:n] = %q\n", b[:n])
		if err == io.EOF {
			break
		}
	}

	// Image 包使用
	m := image.NewRGBA(image.Rect(0, 0, 100, 100))
	fmt.Println(m.Bounds())
	fmt.Println(m.At(0, 0).RGBA())
	
}



package main 


import (
	"fmt"
)

type Node[T any] struct {
	val T 
	next *Node[T]
}
func NewLinkedList[T any](val T) *Node[T] {
	node := Node[T]{val, nil}
	return &node
}
func AddNode[T any](val T, root *Node[T]) {
	// fmt.Println("添加新的节点 --> ", val)
	cur := root
	for {
		if cur.next == nil {
			node := Node[T]{val, nil}
			cur.next = &node
			break
		}
		// fmt.Println("遍历节点输出值 --> ", cur.val)
		cur = cur.next
	}
}
func TravelList[T any](head *Node[T]) {
	s := ""
	cur := head
	for cur != nil {
		s = fmt.Sprintf("%s->%d", s, cur.val)
		cur = cur.next
	}
	fmt.Println("遍历结束\n", s)
}

func main() {
	
	// 类似于 泛型的使用 
	si := []int{10, 20, 15, -10}
	fmt.Println(Index(si, 15))
	ss := []string{"foo", "bar", "baz"}
	fmt.Println(Index(ss, "hello"))

	// 使用泛型函数 add 
	r1 := Add(1, 4)
	r2 := Add("test1", "test2")
	fmt.Println("输出add计算结果 --", r1, r2)  // 输出之间使用空格隔开 -> 默认

	// 实现链表
	var head *Node[int] = NewLinkedList(2)
	TravelList(head) // 遍历输出当前的节点
	AddNode(3, head)
	TravelList(head)
	AddNode(12, head)
	TravelList(head)

}

// 定义方法  泛型方法的定义  返回值相等的索引
func Index[T comparable](s []T, x T) int {  // 中括号表示泛型传入参数类型 
	// 比较数组 s 中是否有存在和x相同的值 
	for i, v := range s {
		if v == x {
			return i
		}
	}

	return -1
}

func Add[T int | string](a T, b T) T {
	return a + b
}








package main 

import (
	"fmt"
	"time"
	"sync"
)

// 普通方法 测试lightweight thread 
func say(s string) {
	for i := 0; i < 10; i++ {
		time.Sleep(100 * time.Millisecond)  // 单位 需要乘法, 不是默认单位 
		fmt.Println(s)
	}
}

func SubSum(s []int, c chan int) {
	// 计算数组的和 保存在channel中 
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum
}

// 数列 输入管道 最后停止 close 函数封存管道 --> 不知道是否有open操作 ? 
func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	close(c)
}
// 并行 通信方式的数列 
func fibonacci2(c, quit chan int) {
	x, y := 0, 1
	for {
		select {  // 2种 case 选择可以执行的, 起初 quit 没有数据, 直到循环结束 
		case c <- x:
			x, y = y, x+y
		case <-quit:
			fmt.Println("quit")
			return
		}
		// default  如果没有其他执行, 则默认执行这个 
	}
}

// 并发编程 基础技能 
func main() {

	go say("hello")  // 轻量级线程 
	say("world")

	// channel 的概念, 类似于一个管道, 可以保存队列形势的数据 
	// 自己的理解 可以当作一个简单的队列使用 
	s := []int{7, 2, 8, -9, 4, 0} 
	c := make(chan int)  // 制作一个int类型的管道 
	go SubSum(s[:len(s)/2], c)  // 启动并行计算 
	go SubSum(s[len(s)/2:], c) 
	x, y := <- c, <- c 
	fmt.Println("输出计算结果 --> ", x, y)
	x, y = y, x  // 简单的数据交换写法 
	fmt.Println("尝试交换数据 --> ", x, y)

	ch := make(chan int, 10)  // 可以缓存额长度 
	ch <- 1
	ch <- 2
	ch <- 3  // 如果缓存设置太小, 会导致死锁, 原因后续再看 
	fmt.Println("输出...", <- ch, <- ch, <- ch)

	// 测试close函数 结束channel 插入值, 只能由 sender 关闭, 一般没有必要不需要关闭 not like files
	go fibonacci(cap(ch), ch)
	// for {
	// 	v, ok := <- ch
	// 	if !ok {
	// 		break
	// 	}
	// 	fmt.Println(v, ok)
	// }
	// 官方写法 
	for i := range ch {
		fmt.Println(i)
	}

	// select 使用, 选择一个田间下可以运行的执行, 用来协调多个任务选择一个 
	channel := make(chan int)
	quit := make(chan int)
	go func() {
		for i := 0; i < 10; i++ {
			fmt.Println(<-channel)  // 这里阻塞, 等待队列中有数据 
		}
		quit <- 0
	}()
	fibonacci2(channel, quit)

	// 测试 mutex 的使用 相当于锁 
	testMutex() 
	StartWebCrawler()
}


// 原子性操作 官方案例 ++ -- 原子性 
// SafeCounter is safe to use concurrently.
type SafeCounter struct {
	mu sync.Mutex
	v  map[string]int
}

// Inc increments the counter for the given key.
func (c *SafeCounter) Inc(key string) {
	c.mu.Lock()
	// Lock so only one goroutine at a time can access the map c.v.
	c.v[key]++
	c.mu.Unlock()
}

// Value returns the current value of the counter for the given key.
func (c *SafeCounter) Value(key string) int {
	c.mu.Lock()
	// Lock so only one goroutine at a time can access the map c.v.
	defer c.mu.Unlock()
	return c.v[key]
}

func testMutex() {
	c := SafeCounter{v: make(map[string]int)}
	for i := 0; i < 1000; i++ {
		go c.Inc("somekey")  // map 中数值增加1000 次 
	}

	time.Sleep(time.Second)
	fmt.Println(c.Value("somekey"))
}

// 官方 爬虫模拟的案例 

type Fetcher interface {  // 执行爬取某一个网页 返回内容和错误, 下级urls 
	// Fetch returns the body of URL and
	// a slice of URLs found on that page.
	Fetch(url string) (body string, urls []string, err error)
}

// Crawl uses fetcher to recursively crawl
// pages starting with url, to a maximum of depth.
func Crawl(url string, depth int, fetcher Fetcher) {  // 主逻辑, 递归实现多层执行 fetch 
	// TODO: Fetch URLs in parallel.
	// TODO: Don't fetch the same URL twice.
	// This implementation doesn't do either:
	if depth <= 0 {
		return
	}
	body, urls, err := fetcher.Fetch(url)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("found: %s %q\n", url, body)
	for _, u := range urls {
		Crawl(u, depth-1, fetcher)
	}
	return
}

func StartWebCrawler() {  // 启动爬虫的入口 
	Crawl("https://golang.org/", 4, fetcher)
}

// fakeFetcher is Fetcher that returns canned results.
type fakeFetcher map[string]*fakeResult

type fakeResult struct {  // 结果保存 数据结构 
	body string
	urls []string
}

func (f fakeFetcher) Fetch(url string) (string, []string, error) {
	if res, ok := f[url]; ok {
		return res.body, res.urls, nil
	}
	return "", nil, fmt.Errorf("not found: %s", url)
}

// fetcher is a populated fakeFetcher.
var fetcher = fakeFetcher{  // 生成一些模拟结果 
	"https://golang.org/": &fakeResult{
		"The Go Programming Language",
		[]string{
			"https://golang.org/pkg/",
			"https://golang.org/cmd/",
		},
	},
	"https://golang.org/pkg/": &fakeResult{
		"Packages",
		[]string{
			"https://golang.org/",
			"https://golang.org/cmd/",
			"https://golang.org/pkg/fmt/",
			"https://golang.org/pkg/os/",
		},
	},
	"https://golang.org/pkg/fmt/": &fakeResult{
		"Package fmt",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
	"https://golang.org/pkg/os/": &fakeResult{
		"Package os",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
}





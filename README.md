# Test assignment

Below you will find a coding/API design assignment. You have one week to complete the exercise. Feel free to use whatever statically typed language you're most comfortable with. Your solution is expected to be correctly working, performant, addressing corner cases if any, properly documented and accompanied by automated tests as if you were writing production ready code. Successful submissions will be discussed during the phone interview which is the next step.

Just attach the text file(s) with the solution. Archives like .zip will be removed by our mail servers.

Implement a generic List data structure supporting at least the following operations: reverse, filter\[1], map\[2], foldLeft\[3].

We are interested in your data structure design choices, so create your list from your language primitives, do not wrap an already existing data structure or container such as ArrayList.

The expected behavior:

```scala
MyList(1,2,3,4).reverse
> MyList(4,3,2,1)

MyList(1,2,3,4).filter((x) => x % 2 == 0)
> MyList(2,4)

MyList("foo","bar","baz","boom").map((x) => x.length)
> MyList(3,3,3,4)

MyList("foo","bar","baz").map((x) => x.toUpperCase)
> MyList("FOO","BAR","BAZ")

MyList("foo","bar","baz","boom").foldLeft(0)((a,x) => a + x.length)
> 13

MyList("foo","bar","baz").foldLeft("")((a,x) => a + x)
> "foobarbaz"
```

Note: the examples above are using Scala syntax, don't try to emulate it - use the most idiomatic syntax for your language of choice.

## BONUS QUESTIONS

1. What are the computational complexities for the operations that you have implemented</p>

2. What guarantees your implementation provide regarding concurrent access and modification

3. For a very large list how could you decompose the problem over many nodes

\[1]: https://en.wikipedia.org/wiki/Filter_(higher-order_function)

\[2]: https://en.wikipedia.org/wiki/Map_(higher-order_function)

\[3]: https://en.wikipedia.org/wiki/Fold_(higher-order_function)

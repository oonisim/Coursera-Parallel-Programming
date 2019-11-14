val width = 579
val numTasks = 5
val size = (width - (width % numTasks)) / (numTasks - 1)

//val l = (0 to width by size).toList :+ width
val l = (0 to width by size).toList :: width :: Nil

l zip l.tail
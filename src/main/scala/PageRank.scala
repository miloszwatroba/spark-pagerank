import org.apache.spark.sql.SparkSession

object PageRank {
  def main(args: Array[String]) {
    val inputPath = args(0)
    val iterations = args(1).toInt
    val saveTopNumber = args(2).toInt
    val outputPath = args(3)

    val spark = SparkSession
      .builder
      .appName("PageRank")
      .getOrCreate()

    val lines = spark.read.textFile(inputPath).rdd

    val links = lines
      .filter(!_.startsWith("#"))
      .filter(_.split("\\s").length == 2)
      .map(line => line.split("\\s") match {
        case Array(fromNodeId, toNodeId) => (fromNodeId, toNodeId)
      })
      .distinct()
      .groupByKey()
      .cache()

    var ranks = links.mapValues(_ => 1.0)

    for (_ <- 1 to iterations) {
      val contributions = links
        .join(ranks)
        .flatMap { case (_, (neighbors, rank)) =>
          neighbors.map(neighbor => (neighbor, rank / neighbors.size))
        }

      ranks = contributions
        .reduceByKey(_ + _)
        .mapValues(0.15 + 0.85 * _)
    }

    val output = ranks.takeOrdered(saveTopNumber)(Ordering[Double].reverse.on(_._2))
    spark.sparkContext.parallelize(output).saveAsTextFile(outputPath)
    output.foreach(t => println(s"Page: ${t._1}, Rank: ${t._2}"))

    spark.stop()
  }
}
package edu.wzm.ml.sparkapi

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2015/9/27.
 */
object KMeansDemo {

  def main(args: Array[String]): Unit ={

    if(args.length != 3){
      System.err.println("Usage: <input file> <iteration number> <cluster numberr>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val data = sc.textFile(args(0))
    //Iteration number
    val iteration = args(1).toInt
    //cluster number
    val cluster = args(2).toInt

    val parseData = data.map(line => Vectors.dense(line.split(" ").map(_.toDouble)))
    val model = KMeans.train(parseData, cluster, iteration)
    val centers = model.clusterCenters

    println(centers)

    sc.stop()
  }
}

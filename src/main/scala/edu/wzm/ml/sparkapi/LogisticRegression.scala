package edu.wzm.ml.sparkapi

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

/**
 * Created by Administrator on 2015/9/26.
 */
object LogisticRegression {

  def main (args: Array[String]): Unit = {

    if(args.length != 2){
      System.err.println("Usage: <input file> <iteration number>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val data = sc.textFile(args(0))
    //Iteration number
    val iteration = args(1).toInt
    val parseData = data.map{line =>
      val elem = line.split(":")
      LabeledPoint(elem(0).toDouble, Vectors.dense(elem(1).split(" ").map(x => x.toDouble)))
    }

    val model = LogisticRegressionWithSGD.train(parseData, iteration)
    val weight = model.weights

    println(weight)

    sc.stop()
  }
}

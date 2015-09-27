package edu.wzm.ml.sparkapi

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2015/9/27.
 */
object SVD {

  def main (args: Array[String]): Unit ={

    if(args.length != 1){
      System.err.println("Usage: <input file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val data = sc.textFile(args(0))
    val vectors = data.map{line =>
      val values = line.split(" ").map(_.toDouble)
      Vectors.dense(values)
    }

    val mat = new RowMatrix(vectors)
    val svd = mat.computeSVD(mat.numCols().toInt)

    println(svd)

    sc.stop()
  }
}

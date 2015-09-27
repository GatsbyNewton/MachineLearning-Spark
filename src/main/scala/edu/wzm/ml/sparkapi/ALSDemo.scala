package edu.wzm.ml.sparkapi

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.mllib.recommendation.ALS
/**
 * Created by Administrator on 2015/9/27.
 */
object ALSDemo {

  def main(args: Array[String]): Unit ={

    val length = args.length
    if(length != 5 && length != 6){
      System.err.println("Usage: <input file> <iteration number> <feature numberr>  " +
        "<user id> <number of recommending product> <regularization factor(optional)>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val data = sc.textFile(args(0))
    //Iteration number
    val iteration = args(1).toInt
    //number of features to use
    val feature = args(2).toInt
    //the user to recommend products to
    val userId =args(3).toInt
    //how many products to return. The number returned may be less than this
    val numProduct = args(4).toInt
    //regularization factor, and recommended 0.01
    val lambda = if(length != 6) 0.01 else args(5).toDouble

    val ratings = data.map{line =>
      line.split(" ") match {
        case (user, product, rate) => Rating(user, product, rate)
      }
    }
    val model = ALS.train(ratings, feature, iteration, lambda)

    val usersProducts = ratings.map{
      case (user, product, rate) => (user,product)
    }
    val predictRating = model.predict(usersProducts.asInstanceOf[RDD[(Int, Int)]]).map{
      case Rating(user, product, rate) => ((user, product), rate)
    }
    val recommenProducts = model.recommendProducts(userId, numProduct)

    println("Predict Rating: " + predictRating)
    println("User: " + userId + " is recommended " + recommenProducts)

    sc.stop()
  }
}

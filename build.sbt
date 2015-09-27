name := "MechineLearning"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  val sparkVersion = "1.2.1"
  Seq(
    "org.apache.hadoop" % "hadoop-client" % "2.4.1",
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-mllib" % sparkVersion,
    "org.scalanlp" %% "breeze" % "0.11.1",
    "org.scalanlp" %% "breeze-natives" % "0.11.1"
  )
}
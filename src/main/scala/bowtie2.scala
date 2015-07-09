package ohnosequencesBundles.statika

import ohnosequences.statika._, bundles._, instructions._


abstract class bowtie2(val version: String) extends Bundle() {

	import ammonite.ops._

	val usrbin = root/"usr"/"bin"

  def install: Results = {
    Seq("aws", "s3", "cp", s"s3://resources.ohnosequences.com/bowtie2/${version}/bowtie2-${version}-linux-x86_64.zip", "-O", "bowtie2-${version}-linux-x86_64.zip") ->-
    Seq("unzip", s"bowtie2-${version}-linux-x86_64.zip") ->-
    Seq("cd", s"bowtie2-${version}") ->-
    Seq("cp", "bowtie2", "bowtie2-*", "/usr/bin/") ->-
    success(s"${bundleName} is installed")
  }

}

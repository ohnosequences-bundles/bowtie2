package ohnosequencesBundles.statika

import ohnosequences.statika._, bundles._, instructions._
import java.io.File

abstract class Bowtie2(val version: String, val samtools: Samtools) extends Bundle(samtools) {


	val usrbin = "/usr/bin/"
	val bowtie2Dir = s"bowtie2-${version}"
	val bowtie2Distribution = bowtie2Dir+"-linux-x86_64"

	val commands: Set[String] = Set(
		"bowtie2",
		"bowtie2-build",
		"bowtie2-inspect"
	)


	def linkCommand(cmd: String): Results =
		Seq("ln", "-s", new File(s"${bowtie2Dir}/${cmd}").getAbsolutePath, s"${usrbin}/${cmd}")

  def install: Results = {

    Seq("wget", s"http://s3-eu-west-1.amazonaws.com/resources.ohnosequences.com/bowtie2/${version}/${bowtie2Distribution}.zip", "-O", s"${bowtie2Distribution}.zip") ->-
    Seq("unzip", s"${bowtie2Distribution}.zip") ->-
		commands.foldLeft[Results](
      Seq("echo", "linking tophat binaries")
    ){ (acc, cmd) => acc ->- linkCommand(cmd) } ->-
    success(s"${bundleName} is installed")
  }
}

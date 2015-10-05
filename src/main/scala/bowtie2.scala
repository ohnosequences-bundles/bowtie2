package ohnosequencesBundles.statika

import ohnosequences.statika._, bundles._, instructions._
import java.io.File

abstract class Bowtie2(val version: String, val samtools: Samtools)
	extends Bundle(samtools) { bowtie2 =>

	val name = "bowtie2-" + version
	val zip = name + "-linux-x86_64.zip"

	val binaries: Set[String] = Set(
		"bowtie2",
		"bowtie2-build",
		"bowtie2-inspect"
	)

	lazy val download: CmdInstructions = cmd("wget")(
		s"http://s3-eu-west-1.amazonaws.com/resources.ohnosequences.com/bowtie2/${version}/${bowtie2.zip}"
	)

	lazy val unzip: CmdInstructions = cmd("unzip")(bowtie2.zip)

	def linkCommand(binary: String): CmdInstructions = cmd("ln")("-s",
		new File(bowtie2.name, binary).getCanonicalPath,
		s"/usr/bin/${binary}"
	)

  def instructions: AnyInstructions =
		download -&-
		unzip -&-
		binaries.foldLeft[AnyInstructions]( Seq("echo", "linking binaries") ){ _ -&- linkCommand(_) }
}

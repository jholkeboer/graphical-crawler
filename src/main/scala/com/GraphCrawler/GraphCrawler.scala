package com.GraphCrawler

// tagsoup setup based on http://blog.dub.podval.org/2010/08/scala-and-tag-soup.html
import scala.xml.{Elem, XML}
import scala.xml.factory.XMLLoader
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

object GraphCrawler {
  def main(args: Array[String]): Unit = {

    val startingURL = "https://pure-chamber-37511.herokuapp.com"

    println("Crawling from " + startingURL)

    val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
    val parser = parserFactory.newSAXParser()
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter

    def getLinks(url : String): scala.collection.immutable.Seq[String] = {
      val source = new org.xml.sax.InputSource(url)
      val tree = adapter.loadXML(source, parser)
      if tree.size = 0 
        tree
      else
        val tree = adapter.loadXML(source, parser)
        tree \ "body" \\ "@href" map {l => println(l); l.text}
    }

    val startingLinks = getLinks(startingURL)

    val links = startingLinks.foldLeft(startingLinks)((a,b) => a ++ getLinks(b))

    links map println

  }
 }

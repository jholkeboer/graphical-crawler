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
    val source = new org.xml.sax.InputSource(startingURL)
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
    val tree = adapter.loadXML(source, parser)

    val title = (tree \ "head" \ "title")(0).text
    println("Page title: " + title)

    val links = tree \ "body" \\ "@href"
  
    links map println

  }
 }

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
      try {
        val source = new org.xml.sax.InputSource(url)
        val tree = adapter.loadXML(source, parser)
        tree \ "body" \\ "@href" filter {l => l.text.startsWith("http")} map {l2 => println(l2); l2.text}
      } catch {
        case e: javax.net.ssl.SSLHandshakeException => scala.collection.immutable.Seq[String]()
      }
    }

    val startingLinks = getLinks(startingURL)

    val links = startingLinks.foldLeft(startingLinks)((a,b) => a ++ getLinks(b))

  }
 }

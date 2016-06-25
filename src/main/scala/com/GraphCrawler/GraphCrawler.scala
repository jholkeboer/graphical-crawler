package com.GraphCrawler

// tagsoup setup based on http://blog.dub.podval.org/2010/08/scala-and-tag-soup.html
import scala.xml.{Elem, XML}
import scala.xml.factory.XMLLoader
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

object GraphCrawler {
  def main(args: Array[String]): Unit = {

    val recursionLimit = 25

    // definition of data structures

    // this structure stores a link as tuple of (url, title, links_on_page)
    type Link = (String, String, scala.collection.immutable.Seq[String])

    // this structure stores a list of all links and the current recursion level
    type CrawlerResult = (List[Link], Int)


    val startingURL = "https://pure-chamber-37511.herokuapp.com"

    println("Crawling from " + startingURL)

    val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
    val parser = parserFactory.newSAXParser()
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter

    def breadthFirstCrawl(next : String, level : Int): CrawlerResult = {
      try {
        println("Level " + level + " URL " + next)
        val source = new org.xml.sax.InputSource(next)
        val tree = adapter.loadXML(source, parser)
        val title = (tree \\ "title")(0).text
        val urls = tree \ "body" \\ "@href" filter {l => l.text.startsWith("http")} map {l2 => println(l2); l2.text}
        // val result_set = urls map {l => breadthFirstCrawl(l, level + 1)}
        // val result : CrawlerResult = urls.foldLeft((List[Link](), level))((a,b) => (a._1 ++ breadthFirstCrawl(b, level)._1, level))
        if (level > 25)  
          new CrawlerResult(List[Link](), level)
        else {
          val result_set = urls map {l => breadthFirstCrawl(l, level + 1)}
          result_set.foldLeft(new CrawlerResult(List[Link](), level))((a,b) => (a._1 ++ b._1, level + 1))
        }
      } catch {
        case e: javax.net.ssl.SSLHandshakeException => new CrawlerResult(List[Link](), 0)
      }
    }

    // val startingLinks = getLinks(startingURL)

    // val links = startingLinks.foldLeft(startingLinks)((a,b) => a ++ getLinks(b))
    val source = new org.xml.sax.InputSource(startingURL)
    val tree = adapter.loadXML(source, parser)
    val startingTitle = (tree \\ "title")(0).text
    val startingHREF = tree \ "body" \\ "@href" filter {l => l.text.startsWith("http")} map {l2 => println(l2); l2.text}
    val startingLink : Link = (startingURL, startingTitle, startingHREF)

    val results = startingHREF map {l => breadthFirstCrawl(l, 0)}

    //val results : CrawlerResult = breadthFirstCrawl(startingURL, 0)

    println(results)

  }
 }

package cz.mzk.editor.server.resolver
import scala.xml.XML._
import scala.xml._
import dispatch._, Defaults._

class Resolver {

  def fst(nSeq: Seq[Node]) = nSeq match {
    case Seq(el) if !el.text.isEmpty => Some(el.text)
    case _ => None
  }

	def resolve(resolverUrl: String, dc: String, model: String): String = {
    import scala.util.{Success, Failure}

    // create the xml
    val data = prepareXmlForRequest(dc, model).toString
    // async request
    Http(url(resolverUrl) << data OK as.String).value.getOrElse("error: none") match {
      case Success(xml) => fst(loadString(xml.toString) \\ "value").getOrElse("error: not-found")
      case Failure(f) => "error:" + f.toString
    }
	}
	
  def createModelElement(model: String, name: String, isxn: String = "") = {
    val content = model.toLowerCase match {
      case "monograph" => <envelope><titleInfo><title>{name}</title></titleInfo>{if (!isxn.isEmpty) <isbn>{isxn}</isbn> else ""}</envelope>
      case "monographvolume" => <envelope><titleInfo><monographTitle>{name}</monographTitle><volumeTitle>{name}</volumeTitle></titleInfo>{if (!isxn.isEmpty) <isbn>{isxn}</isbn> else ""}</envelope>
      case "periodical" => <envelope><titleInfo><title>{name}</title></titleInfo>{if (!isxn.isEmpty) <issn>{isxn}</issn> else ""}</envelope>
      case "periodicalvolume" => <envelope><titleInfo><periodicalTitle>{name}</periodicalTitle><volumeTitle>{name}</volumeTitle></titleInfo>{if (!isxn.isEmpty) <issn>{isxn}</issn> else ""}</envelope>
      case "periodicalissue" => <envelope><titleInfo><periodicalTitle>{name}</periodicalTitle><volumeTitle>{name}</volumeTitle><issueTitle>{name}</issueTitle></titleInfo>{if (!isxn.isEmpty) <issn>{isxn}</issn> else ""}</envelope>
      case "analytical" | "thesis" => <envelope><titleInfo><title>{name}</title></titleInfo></envelope>
      case _ => <envelope><titleInfo><title>{name}</title></titleInfo></envelope>
    }
    content.copy(label=model)
  }

  def prepareXmlForRequest(dc: String, model: String) = {
    val dcXml = loadString(dc)
    /*val dcXml = 
<oai_dc:dc xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
  <dc:title>Duha</dc:title>
  <dc:publisher>Moravská zemská knihovna v Brn</dc:publisher>
  <dc:date>1987-</dc:date>
  <dc:type>model:periodical</dc:type>
  <dc:identifier>uuid:13f650ad-6447-11e0-8ad7-0050569d679d</dc:identifier>
  <dc:identifier>issn:0862-1985</dc:identifier>
  <dc:language>cze</dc:language>
  <dc:rights>policy:public</dc:rights>
</oai_dc:dc>
*/
    val name = fst(dcXml \\ "title").get
    val isxn =(dcXml \\ "identifier") find (x => (x.text.toLowerCase startsWith "is") && x.text.length > 5 && x.text.toLowerCase()(3) == 'n')
    val modelEl = createModelElement(model, name, isxn.map(_.text) getOrElse "")
    <r:import xmlns="http://resolver.nkp.cz/v3/">{modelEl}</r:import>
  }
}

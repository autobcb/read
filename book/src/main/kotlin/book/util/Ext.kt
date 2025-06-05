package book.util


import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

fun getFileExtetion(url: String, defaultExt: String=""): String {
    try {
        var seqs = url.split("?", ignoreCase = true, limit = 2)
        var file = seqs[0].split("/").last()
        val dotPos = file.lastIndexOf('.')
        return if (0 <= dotPos) {
            file.substring(dotPos + 1)
        } else {
            defaultExt
        }
    } catch (e: Exception) {
        return defaultExt
    }
}


fun xml2map(source: Any): MutableMap<String, Any> {
    //1.创建DocumentBuilderFactory对象
    val factory = DocumentBuilderFactory.newInstance()
    //2.创建DocumentBuilder对象
    var doc = mutableMapOf<String, Any>()
    try {
        val builder = factory.newDocumentBuilder()
        // val document = builder.parse(filePath)
        when {
            source is String -> {
                val document = builder.parse(source as String)
                return parseNode(document.getChildNodes())
            }
            source is InputStream -> {
                val document = builder.parse(source as InputStream)
                return parseNode(document.getChildNodes())
            }
            source is InputSource -> {
                val document = builder.parse(source as InputSource)
                return parseNode(document.getChildNodes())
            }
            else -> {
                return doc
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return doc
    }
}


fun parseNode(list: NodeList): MutableMap<String, Any> {
    var doc = mutableMapOf<String, Any>()
    for (i in 0 until list.getLength()) {
        val node = list.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            val childNodes = node.getChildNodes()
            // <Element><Text></Text><Element><Text></Text></Element></Element>
            // logger.info("index: {} node: {} type: {} childNodesLength: {}", i, node, node.getNodeType(), childNodes.getLength())
            if (childNodes.getLength() == 1 && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                doc.put(node.getNodeName(), node.getFirstChild().getNodeValue())
            } else if(childNodes.getLength() > 1) {
                doc.put(node.getNodeName(), parseNode(childNodes))
            }
        }
    }
    return doc
}



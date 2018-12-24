import io.javalin.{Context, Handler, Javalin}
import logging.{LogBlock, MyLogger, NetLogBlock, NetworkLogger}
import readfile.Readfile
import schedulling.abstractions.Freezer
import java.util

import se.roland.Extractor
object SVisor {
  val pathBinaryLog = (new Readfile("sqlset")).binaryLogPath()
  val fr: Freezer = new Freezer
  var mylogger: MyLogger = new MyLogger(pathBinaryLog)
  mylogger.setFreezer(fr)
  mylogger.setExtractor(new Extractor)
  var nl : NetworkLogger = new NetworkLogger(pathBinaryLog)
  nl.start()
  val sv_ = new SVisor_
  val rootHandler: Handler = new Handler {override def handle(context: Context) ={context.render("fake.html")      }}
  val logHandler: Handler = new Handler {override def handle(context: Context) ={
    val log = mylogger.readLog(pathBinaryLog)
    val size = log.logblock.size
    val output = new StringBuffer
    output.append("<!DOCTYPE html>")
    output.append("<head>\n    <meta charset=\"UTF-8\">\n</head>")
    output.append("  <table style=\"width:100%\">\n<tr>" + "    <th>Дата</th>\n" + "    <th>Описание</th>\n" + "    <th>GUID</th>\n" + "    <th>Сервис</th>\n" + "  </tr>\n")
    for (i <- (size-1) to 0 by -1)
      output.append("  <tr style=\"color:" + sv_.ColorMap.get(log.logblock.get(i).status) + "\">\n" + "    <td>" + log.logblock.get(i).date + "</td>\n" + "    <td>" + log.logblock.get(i).description + "</td>\n" + "    <td>" + log.logblock.get(i).GUID + "</td>\n" + "    <td>" + log.logblock.get(i).pseudo + "</td>\n" + "  </tr>\n")
    output.append("</table>")
    context.html(output.toString)}}

  val activityHandler: Handler = new Handler {override def handle(context: Context) ={
    val output = new StringBuffer
    output.append("<!DOCTYPE html>")
    output.append("<head>\n    <meta charset=\"UTF-8\">\n</head>")
    output.append("  <table style=\"width:100%\">\n<tr>" + "    <th>сервис</th>\n" + "    <th>последнее обращение</th>\n" + "  </tr>\n")
    output.append("  <tr style=\"color:" + sv_.ColorMap.get(0) + "\">\n" + "    <td>" + mylogger.readAcrivityEvent.pseudo + "</td>\n" + "    <td>" + mylogger.readAcrivityEvent.date + "  </tr>\n")
    output.append("</table>")
    context.html(output.toString)}}

  val networkHandler: Handler = new Handler {override def handle(context: Context) ={
    val log = new NetLogBlock
    log.load(pathBinaryLog)
    val size = log.logblock.size
    System.out.println(size)
    val output = new StringBuffer
    output.append("<!DOCTYPE html>")
    output.append("<head>\n    <meta charset=\"UTF-8\">\n</head>")
    output.append("  <table style=\"width:100%\">\n<tr>" + "    <th>Дата</th>\n" + "    <th>имя интерфейса</th>\n" + "    <th>isUP</th>\n" + //     "    <th>IsUP</th>\n" +
      "    <th>статус</th>\n" + "  </tr>\n")
    for (i <- (size-1) to 0 by -1)
      output.append("  <tr style=\"color:" + sv_.Netmap.get(log.logblock.get(i).status) + "\">\n" + "    <td>" + log.logblock.get(i).date + "</td>\n" + "    <td>" + log.logblock.get(i).Name + "</td>\n" + //      "    <td>"+log.logblock.get(i).IP.toString()+"</td>\n" +
        "    <td>" + log.logblock.get(i).isUp + "</td>\n" + "  </tr>\n")
    output.append("</table>")
    context.html(output.toString)}}
  def main(args: Array[String]): Unit = {
    var app: Javalin = Javalin.create.start(7777)
    app.get("/", rootHandler)
    app.get("/log", logHandler)
    app.get("/lastact", activityHandler)
    app.get("/netst", networkHandler)
  }
}
class SVisor_{
  def getbinaryPath: String = (new Readfile("sqlset")).binaryLogPath()
  def ColorMap: util.Map[Integer, String] = {
    var result = new util.HashMap[Integer, String]
    result.put(0, "#047900")
    result.put(1, "#b8b000")
    result.put(2, "#700009")
    result.put(3, "#7d0ef0")
    return result
  }
  def Netmap: util.Map[Integer, String] = {
    var result = new util.HashMap[Integer, String]
    result.put(0, "#047900")
    result.put(1, "#ffb000")
    result.put(2, "#700009")
    return result
  }
}


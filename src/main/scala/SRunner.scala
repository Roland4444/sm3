import readfile.Readfile
import schedulling.Scheduller
import schedulling.abstractions.DependencyContainer
import util.SignerXML
import util.crypto.Sign2018
object SRunner extends App{
  val r = new Readfile("sqlset")
  var deps: DependencyContainer = new DependencyContainer(r.addressSAAJ, new SignerXML(new Sign2018(), new Sign2018()))
  var sch: Scheduller= new Scheduller(deps, true)
  val delay = r.delay.toInt
  deps.gis.SupressConsole = false
  sch.processor.setExecutor(deps.executor)
  sch.processor.enableBinaryLogging = true
  sch.processor.setIdentifier(deps.Idgen)
  deps.dataImporter.enableBinaryLogging = true
  deps.dataImporter.setLogger(deps.logger)
  deps.dataImporter.setSQLWrapper(sch.sqlWrapper)
  sch.processor.setSQLWrapper(sch.sqlWrapper)
  sch.processor.AckEnabled = true
  while (true){
        deps.dataImporter.loadDataToInputFlowiniversal(false, true)
        sch.processor.sendAllUniversal()
        sch.processor.getResponcesAnyUniversal()
        Thread.sleep(delay)
  }
}


package schedulling.abstractions;
import DB.Executor;
import essent.Client;
import essent.J8Client;
import logging.MyLogger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import schedulling.abstractions.OutDataPerfomImpl.PerfomReceivedData;
import schedulling.abstractions.OutDataPerform.tableResultProcessors;
import schedulling.gettingDataImplem.DataSource;
import schedulling.gettingDataImplem.getData;
import se.roland.Extractor;
import standart.*;
import standart.ESIA.createsia;
import standart.ESIA.esia;
import standart.ESIA.findesia;
import standart.ESIA.upgradesia;
import transport.SAAJ;
import transport.Transport;
import util.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;

import schedulling.ResultsProcessors.*;

public class DependencyContainer implements Serializable {
    public Readfile r;
    public String ftpAddr;
    public String pathtoLog="";
    public HashMap<String, String> ignored ;
    public TempDataContainer temp;
    public String IdBuffer="";
    public MapProcessor tableProcessor;
    public Sign sign;
    public SignerXML xmlsign;
    public Sign personalSign ;
    public OutputStream os;
    public StreamResult sr;
    public Extractor ext;
    public Executor executor;
    public Injector inj;
    public Transport transport;
    public timeBasedUUID uuidgen;

    public gis gis;
    public egr egr;
    public pass pass;
    public inn inn;
    public standart.ESIA.esia esia;
    public ebs ebs;
    public standart.ESIA.findesia findesia;
    public standart.ESIA.upgradesia upgradesia;
    public standart.ESIA.createsia createsia;

    public InfoAllRequests dbReqs;
    public InputDataContainer inputDataFlow;
    public MyLogger logger;
    public final String sucesfullqueed = "queued";
    public final String errorqueed = "error";
    public final String stopperGetResponce = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:GetResponseResponse xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\"/></soap:Body></soap:Envelope>";

    public final String gis_  ="gis";
    public final String egr_  ="egr";
    public final String pass_ ="pass";
    public final String inn_ ="psinn";
    public final String inn2_ ="inn";
    public final String bank_ = "bankost";
    public final String gisreg_ = "gisreg";
    public final String esia_ = "esia";
    public final String ebs_ = "ebs";
    public final String findesia_ = "findesia";
    public final String upgradesia_="upgradesia";
    public final String createsia_ = "createsia";

    public boolean SupressConsole;
    public Identifier Idgen;
    public DataSource datasource;
    public getData dataImporter;
    public tableResultProcessors tableResultProcessors;
    public gisResult gisResult;
    public egrResult egrResult;
    public ebsResult ebsResult;
    public passResult passResult;
    public findesiaResult findesiaResult;
    public PerfomReceivedData performReceiveddata;
    public Freezer freezer;
    private void initTableProcesor(){
        this.tableProcessor=new MapProcessor();
        tableProcessor.OperatorMap.put(gis_, this.gis);
        tableProcessor.OperatorMap.put(egr_, this.egr);
        tableProcessor.OperatorMap.put(pass_, this.pass);
        tableProcessor.OperatorMap.put(inn_, this.inn);
        tableProcessor.OperatorMap.put(inn2_, this.inn);
        tableProcessor.OperatorMap.put(bank_, this.inn);
        tableProcessor.OperatorMap.put(gisreg_, this.egr);
        tableProcessor.OperatorMap.put(esia_, esia);
        tableProcessor.OperatorMap.put(ebs_, ebs);
        tableProcessor.OperatorMap.put(findesia_, findesia);
        tableProcessor.OperatorMap.put(upgradesia_, upgradesia);
        tableProcessor.OperatorMap.put(createsia_, createsia);

    };

    private void initDataSource(){
        this.datasource = new DataSource();
        this.datasource.Source.put(gis_, "gis_files");
        this.datasource.Source.put(egr_, "fns_files");
        this.datasource.Source.put(pass_, "fms_zap");


    }

    private void generateOutputResultProcessors(){
        this.tableResultProcessors = new tableResultProcessors();
        this.tableResultProcessors.TableResultProcessors.put(gis_, this.gisResult);
        this.tableResultProcessors.TableResultProcessors.put(egr_, this.egrResult);
        this.tableResultProcessors.TableResultProcessors.put(pass_, this.passResult);
        this.tableResultProcessors.TableResultProcessors.put(ebs_, this.ebsResult);
        this.tableResultProcessors.TableResultProcessors.put(findesia_, this.findesiaResult);

    }



    private void init() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        initDataSource();
        this.r = new Readfile("sqlset");
        pathtoLog = r.binaryLogPath();
        this.ignored= new HashMap<>();
        this.temp= new TempDataContainer();
        this.freezer =  new Freezer();
        this.Idgen = new Identifier();
        this.SupressConsole=false;
        this.logger =  new MyLogger(pathtoLog);
        this.logger.setFreezer(this.freezer);
        this.dbReqs = new InfoAllRequests();
        this.inputDataFlow = new InputDataContainer();



        this.os = new ByteArrayOutputStream();
        this.sr = new StreamResult(os);
        this.ext=new Extractor();
        this.executor=new Executor(r.read(), true);
        this.uuidgen=new timeBasedUUID();
        this.inj = new Injector();

        this.gis = new gis(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.egr = new egr(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.pass = new pass(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.inn =  new inn(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.esia = new esia(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.ebs = new ebs(this.sr, this.xmlsign, this.inj,this.transport, this.temp);
        ebs.ftpAddr = this.ftpAddr;
        this.findesia = new findesia(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.upgradesia = new upgradesia(this.sr, this.xmlsign, this.inj, this.transport, this.temp);
        this.createsia = new createsia(this.sr, this.xmlsign, this.inj, this.transport, this.temp);

        initTableProcesor();

        this.dataImporter = new getData(this.executor, this.datasource, this.Idgen, this.inputDataFlow, this.tableProcessor);
        this.dataImporter.setInjector(this.inj);
        this.dataImporter.setIgnored(this.ignored);


        this.gisResult = new gisResult(this.executor, this.Idgen);
        this.gisResult.setExtractor(this.ext);
        this.egrResult = new egrResult(this.executor, this.Idgen);
        this.egrResult.setExtractor(this.ext);
        this.passResult = new passResult(this.executor, this.Idgen);
        this.passResult.setExtractor(this.ext);
        this.ebsResult = new ebsResult();
        this.findesiaResult = new findesiaResult();
        this.findesiaResult.setExtractor(this.ext);




        generateOutputResultProcessors();
        this.performReceiveddata = new PerfomReceivedData(this.tableResultProcessors, this.dbReqs, this.inputDataFlow);
    }



    public DependencyContainer(SignerXML signer, String addres) throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        this.transport = new SAAJ(addres);
        this.xmlsign = signer;
        init();
    }

    public DependencyContainer(String addres, SignerXML signer ) throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        this.transport = new SAAJ(addres);
        this.xmlsign = signer;
        this.ftpAddr="smev3-n0.test.gosuslugi.ru";
        init();
    }

    public DependencyContainer(SignerXML signer) throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        this.transport = new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl");
        this.xmlsign = signer;
        this.ftpAddr="smev3-n0.test.gosuslugi.ru";
        init();
    }


    public DependencyContainer(SignerXML signer, boolean isolate){
        this.xmlsign = signer;
        System.out.println("MUST specify::\n1)SOAP Address(Transport)\n2) FTP address");
    };

    public void ignite() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException {
        init();
    }



}

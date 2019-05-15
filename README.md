# candy_smev
Resolverinoutdata определдяет источник информации и куда класть результаты плюс чем обрабатывать (оператор)

constrioller на основе dataconveer мапы отвечает за получение данных и передачу их в резолвер для возвращения  результата
метод process()
метод recharge() заполняет dataconveer

гис гмп sendrequestrequest дает statusrequestIsQueued
getresponcerequest дает messageid originalmessageid накоторый надо дать ack(messageid)

информация о всех запросах, телах запросов и ответов, статусах содержится в infoAllRequests


Adding new Operator:
Create new pseudo for operator
in DepencdencyContainer
     =>> public final String egr_ ="egr";
register new class in this.datasource in Depencdency container
                          =>>      this.datasource.Source.put(gis_, "gis_files");

                          Registrate new operator in ResultProcessors
                          ==>  egrResult;

In standart registrate new Class for signing && transfer

in => generateOutputResultProcessors(){
        this.tableResultProcessors = new tableResultProcessors();
        this.tableResultProcessors.TableResultProcessors.put(gis_, this.gisResult);
  =>    this.tableResultProcessors.TableResultProcessors.put(egr_, this.egrResult);


Not forgot to initialize it
for example
this.pass = new pass(this.sr, this.xmlsign, this.personalSign, this.sign);
        this.egr.setLink(this.temp);
        gis.setSAAJ(this.saaj);

egrResult
<ns1:FNSVipIPResponse>**********</ns1:FNSVipIPResponse>     >>  String( f_body_a ) @ fns_egr
ФИО extracted @  FNSVipIPResponse  >>  f_namep  @ fns_egr


Running https://stackoverflow.com/questions/361975/setting-the-default-java-character-encoding
java -Xms1024m -Dfile.encoding=UTF-8 … com.x.Main

EBS  клиент и сервер стоят на одной машине
создание SOAP  уникальные данные вставлять в
<ns2:MessagePrimaryContent>


По фильтру ==> Namespace URI	>>urn://ru/mvd/sovm/p002/1.0.0  <<example @ https://smev3.gosuslugi.ru/portal/inquirytype_one.jsp?id=66003&zone=fed&page=1&dTest=true


Программа работает в гибком (настраиваем бесконечном цикле)

Очередь сообщений скаплтивается в inputFLOW

результаты запросов идут в InfoAllRequests

Операторы лдля кажого запроса свои они берут инфу с inputflow патчат ее подписывают и шлют
Операторы обоаботчики для каждого сервиса берут инфу с InfoAllRequests и обрабатывают ее согласно внутренней логике

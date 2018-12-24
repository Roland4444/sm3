package standart;

import Message.abstractions.Gender;
import Message.toSMEV.ESIACreate.ESIACreateInit;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class createsiaTest {

    @Test
    public void signedSoap() {
        ESIACreateInit msg = new ESIACreateInit();
        msg.ID="0000";
        msg.Birthdate=new Date("11.11.1988");
        msg.SNILSOper="135-419-238 52";
        msg.RA="1000321282";
        msg.SNILS="229-785-346 20";
        msg.Name="Тест";
        msg.Surname="Тестов";
        msg.MiddleName="Тестович";
        msg.Gender= Gender.M;
        msg.PassSeria="1111";
        msg.PassNumber="111111";
        msg.IssuedPassID="111111";
        msg.IssuedBy="выдан";
        msg.IssuedDatePass=new Date("01.10.2017");
        msg.Mobile="+7(920)4021351";
        msg.Region="23";
        msg.FIAS="720b25da-f43e-4204-9013-3cb06be3e9e4";
        msg.AddressStr="Кемеровская Область, Таштагольский Район, Шерегеш Поселок городского типа";
        msg.ZIP="394000";
        msg.Street="Советская Улица";
        msg.House="86/1";
        msg.Flat="пом.419";
        msg.Frame="204у";
        msg.Building="e";
        msg.BirthPlace="воронеж";


    }
}
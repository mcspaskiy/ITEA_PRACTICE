package com.mcspaskiy;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 * Максим Ковальчук, [30.11.20 19:37]
 * Создать обьекты под помник где мы подключались к бд, и розпарсить помник на обьекты при помощи SAXParser.
 * <p>
 * <p>
 * Максим Ковальчук, [30.11.20 19:37]
 * Несколько котов с аннотациями. В зависимости от аннотаций пускать котов на фарш или оставлять жить.
 * Если кот помечен аннотацией @Blochable - на фарш его ! Иначе - живи.
 * Если @CatColor("black")  - 50% идет на фарш (мало ли у него блохи, шерсть то темная, не понятно)
 * Итого - есть масив котов. Разобрать через рефлексию по аннотациям и решать его судьбу
 * <p>
 * <p>
 * Максим Ковальчук, [30.11.20 19:53]
 * [Forwarded from AngryDev]
 * Создать коллекцию типа Class с классами Cat,BlackCat,FatCat,ThinCat,AglyCat
 * и аннотации,которые могут быть разнесены как угодно .Color(catcolor),Blohable, CatYears(old).
 * нужно создать фабрику котячого фарша.если есть аннотация блохобл,то
 * коли на фарш не подходит,если цвет черный,то шанс не попасть в фарш 50%.
 * Коты старше 3 лет тоже не подходят.сделать логгировние ,что произошло с котами в коллекции.
 */
public class App {
    public static void main(String[] args) {
        List<Class> classes = new ArrayList<>();
        classes.add()


        /**
         * Start document parsing segment
         */
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        PomParser pomParser = new PomParser();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File("pom.xml"), pomParser);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * End document parsing segment
         */


    }
}

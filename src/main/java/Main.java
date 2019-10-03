import entity.*;
import serializer.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws Exception{

        Car c1 = new Car();
        c1.setMark("LADA");
        c1.setModel("2172");
        c1.setYear(2011);

        Person p1 = new Person();
        p1.setName("Pavel");
        p1.setSurname("Petrov");
        p1.setAge(21);
        p1.setMedicalCondition(new BigDecimal(92349.533));
        p1.setCar(c1);

        Node n1 = new Node();
        n1.setDate(Instant.now());
        n1.setText("Hello World!");
        n1.setPriority(3);
        n1.setPosted(true);

        System.out.println(
                "Before:\n" +
                p1.toString() + "\n" +
                n1.toString()
        );

        //Two types of serializers
        BuildInSerializer bis = new BuildInSerializer();
        ReflectiveSerializer rs = new ReflectiveSerializer();

        System.out.println("\nAfter:");

        executeAndPrintResult(p1,bis);
        executeAndPrintResult(p1, rs);


        executeAndPrintResult(n1, bis);
        executeAndPrintResult(n1, rs);

        System.out.println("\n\n----------In detail about one object----------");

        executeAndDetailedPrintResult(p1,rs);


    }

    private static <T> void executeAndPrintResult(T bean, SuperEncoder serializer) throws IOException, ReflectiveOperationException {
        //Serializing object
        byte[] byteStream = serializer.serialize(bean);

        //Deserialize object
        T obj = (T) serializer.deserialize(byteStream);

        //Print info about: Serialization tools and result object
        System.out.println(serializer.getClass().getName() + " : " + obj);
    }

    private static <T> void executeAndDetailedPrintResult(T bean, SuperEncoder serializer) throws IOException, ReflectiveOperationException {
        System.out.println("Source object:\n" + bean.toString()+"\n");
        //Serializing object
        byte[] byteStream = serializer.serialize(bean);

        System.out.println("ByteStream:");
        for (byte b : byteStream) System.out.print(b);
        System.out.println("\n\nRestored object");

        //Deserialize object
        T obj = (T) serializer.deserialize(byteStream);

        //Print info about: Serialization tools and result object
        System.out.println(serializer.getClass().getName() + " : " + obj);
    }

}
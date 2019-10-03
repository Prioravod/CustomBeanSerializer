import entity.*;
import serializer.*;

import java.io.IOException;
import java.math.BigDecimal;
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



        BuildInSerializer bis = new BuildInSerializer();
        ReflectiveSerializer rs = new ReflectiveSerializer();

        printResult(p1,bis);
        printResult(p1, rs);

        printResult(n1, bis);
        printResult(n1, rs);
    }

    private static <T> T printResult(T bean, SuperEncoder serializer) throws IOException, ReflectiveOperationException {
        T obj = (T) serializer.deserialize(serializer.serialize(bean));
        System.out.println(obj + "\n");
        return obj;
    }

}
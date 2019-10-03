import entity.*;
import serializer.*;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws Exception{

        Person p1 = new Person();
        p1.setName("Pavel");
        p1.setSurname("Petrov");
        p1.setAge(21);

        Node n1 = new Node();
        n1.setDate(Instant.now());
        n1.setText("Hello World!");
        n1.setPriority(3);


        BuildInSerializer bis = new BuildInSerializer();
        ReflectiveSerializer rs = new ReflectiveSerializer();

        printResult(p1, bis);
        printResult(p1, rs);

        printResult(n1, bis);
        printResult(n1, rs);
    }

    private static <T> T printResult(T bean, SuperEncoder serializer) throws IOException, ReflectiveOperationException {
        T obj = (T) serializer.deserialize(serializer.serialize(bean));
        System.out.println(obj);
        return obj;
    }

}
package serializer;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static util.ArrayUtil.*;

public class ReflectiveSerializer implements SuperEncoder {

    private static final byte[] COMMON_SEPARATOR = new byte[]{-128};// String.valueOf("-").getBytes(StandardCharsets.UTF_8);
    private static final byte[] FIELD_SEPARATOR = new byte[]{-1};//String.valueOf("000").getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] serialize(Object anyBean) throws IllegalAccessException {

        if (anyBean == null) return null;

        byte[] result = new byte[0];

        Class type = anyBean.getClass();

        result = concatByteArrays(result,
                type.getTypeName().getBytes(StandardCharsets.UTF_8),
                COMMON_SEPARATOR);

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            result = concatByteArrays(result,
                    field.getName().getBytes(StandardCharsets.UTF_8),
                    FIELD_SEPARATOR,
                    field.get(anyBean).toString().getBytes(StandardCharsets.UTF_8),
                    COMMON_SEPARATOR);
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] data) throws ReflectiveOperationException {

        int i = 0;
        List<Byte> buffer = new ArrayList<>();
        Field buffField = null;


        while (data[i] != -128) {
            buffer.add(data[i]);
            i++;
        }


        String className = byteArrParseToString(buffer);

        buffer.clear();

        Class<?> type = Class.forName(className);

        Object obj = type.newInstance();

        for(; i < data.length; i++){
            if ((data[i] != -1) && (data[i] != -128)) buffer.add(data[i]);
            else {
                if (data[i] == -1) {
                    buffField  = obj.getClass().getDeclaredField(byteArrParseToString(buffer));
                    buffer.clear();
                    buffField.setAccessible(true);
                }
                if ((data[i] == -128) && (buffField != null)) {
                    String value = byteArrParseToString(buffer);
                    buffField.set(obj, dynamicParse(buffField.getType(), value));
                    buffField = null;
                    buffer.clear();
                }
            }
        }

        return obj;
    }


}
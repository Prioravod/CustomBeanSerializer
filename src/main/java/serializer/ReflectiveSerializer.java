package serializer;

import entity.Beanable;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static util.ArrayUtil.*;

public class ReflectiveSerializer implements SuperEncoder {

    private static final byte[] COMMON_SEPARATOR = new byte[]{-2};// String.valueOf("-").getBytes(StandardCharsets.UTF_8);
    private static final byte[] FIELD_SEPARATOR = new byte[]{-1};//String.valueOf("000").getBytes(StandardCharsets.UTF_8);
    private static final byte[] SPECIAL_SEPARATOR = new byte[]{-9};

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

            if (field.get(anyBean) instanceof Beanable) {
                result = concatByteArrays(result,
                        field.getName().getBytes(StandardCharsets.UTF_8),
                        FIELD_SEPARATOR,
                        SPECIAL_SEPARATOR,
                        serialize(field.get(anyBean)),
                        SPECIAL_SEPARATOR,
                        COMMON_SEPARATOR);
            }
            else {
                result = concatByteArrays(result,
                        field.getName().getBytes(StandardCharsets.UTF_8),
                        FIELD_SEPARATOR,
                        field.get(anyBean).toString().getBytes(StandardCharsets.UTF_8),
                        COMMON_SEPARATOR);
            }
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] data) throws ReflectiveOperationException {

        int i = 0;
        boolean composite = false;

        List<Byte> buffer = new ArrayList<>();
        Field buffField = null;


        while (data[i] != COMMON_SEPARATOR[0]) {
            buffer.add(data[i]);
            i++;
        }


        String className = byteArrParseToString(buffer);

        buffer.clear();

        Class<?> type = Class.forName(className);

        Object obj = type.newInstance();

        for(; i < data.length; i++){
            if (data[i] != SPECIAL_SEPARATOR[0] && !composite) {
                if ((data[i] != FIELD_SEPARATOR[0]) && (data[i] != COMMON_SEPARATOR[0])) buffer.add(data[i]);
                else {
                    if (data[i] == FIELD_SEPARATOR[0]) {
                        buffField  = obj.getClass().getDeclaredField(byteArrParseToString(buffer));
                        buffer.clear();
                        buffField.setAccessible(true);
                    }
                    if ((data[i] == COMMON_SEPARATOR[0]) && (buffField != null)) {
                        String value = byteArrParseToString(buffer);
                        buffField.set(obj, dynamicParse(buffField.getType(), value));
                        buffField = null;
                        buffer.clear();
                    }
                }
            }
            else {
                if (data[i] == SPECIAL_SEPARATOR[0] && !composite) composite = true;
                else {
                    if (data[i] != SPECIAL_SEPARATOR[0] && composite) buffer.add(data[i]);
                    else if (data[i] == SPECIAL_SEPARATOR[0] && composite && data[i+1] == COMMON_SEPARATOR[0]) {

                        byte[] compositeObject = listToByteArray(buffer);
                        composite = false;
                        buffField.set(obj, deserialize(compositeObject));
                        buffer.clear();
                        buffField = null;
                        i += 1;

                    }
                }

            }

        }

        return obj;
    }


}
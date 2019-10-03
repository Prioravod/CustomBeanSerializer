package util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

public class ArrayUtil {

    public static byte[] listToByteArray(List<Byte> list){

        byte[] arr = new byte[list.size()];
        Iterator<Byte> iterator = list.iterator();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = iterator.next();
        }
        return arr;
    }

    public static byte[] concatByteArrays(byte[] ... arrays){

        int size = 0;

        for (byte[] arr : arrays) size += arr.length;

        byte[] resultArr = new byte[size];

        int destPosition = 0;

        for (int i = 0; i < arrays.length; i++){
            if (i > 0) destPosition += arrays[i-1].length;
            int length = arrays[i].length;
            System.arraycopy(arrays[i], 0, resultArr, destPosition, length);
        }

        return resultArr;
    }

    public static String byteArrParseToString(List<Byte> list){
        return byteArrParseToString(listToByteArray(list));
    }

    public static String byteArrParseToString(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static <T> T dynamicParse(Class<T> type, String value) throws ReflectiveOperationException{
        T result = null;

        if (ClassUtils.isPrimitiveOrWrapper(type)) {
            Class<?> wrapper;
            if (type.isPrimitive()) {
                wrapper = ClassUtils.primitiveToWrapper(type);
            }
            else {
                wrapper = type;
            }
            Method valueOf = wrapper.getDeclaredMethod("valueOf",String.class);
            result = (T) valueOf.invoke(null, value);
        }
        else if (type == String.class){
            result = (T) value;
        }
        else if (type == Instant.class) {
            result = (T) Instant.parse(value);
        }
        return result;


    }

}
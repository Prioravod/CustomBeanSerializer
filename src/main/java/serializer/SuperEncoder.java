package serializer;

import java.io.IOException;

public interface SuperEncoder {
    byte[] serialize(Object anyBean) throws IOException, IllegalAccessException;
    Object deserialize(byte[] data) throws IOException, ReflectiveOperationException;
}
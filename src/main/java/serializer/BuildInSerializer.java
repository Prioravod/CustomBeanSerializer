package serializer;

import java.io.*;

public class BuildInSerializer implements SuperEncoder {
    @Override
    public byte[] serialize(Object anyBean) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream os = new ObjectOutputStream(bos)){
            os.writeObject(anyBean);
        }
        return bos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }
}

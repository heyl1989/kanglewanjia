package cn.v1.kanglewanjia.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

/**
 *
 * 康乐万家接口都使用TypeByteArray来传输数据，所以convert方法都会将流转化为TypeByteArray
 */
public class KangLeConverter implements Converter{

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            InputStream is = body.in();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read())!= -1){
                baos.write(i);
            }
            return new TypedString(new String(baos.toByteArray()));
        } catch (IOException e) {
            throw new IllegalArgumentException("expect TypeByteArray but cannot get TypeByteArray");
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new TypedString(new String(baos.toByteArray()));
    }
}

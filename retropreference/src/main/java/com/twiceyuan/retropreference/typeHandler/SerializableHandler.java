package com.twiceyuan.retropreference.typeHandler;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by twiceYuan on 09/02/2017.
 * <p>
 * Serializable 对象存储。实际为保存对象到文件，并非使用 preference
 */
public class SerializableHandler extends BaseTypeHandler<Object> {

    private Context mContext;
    private String  mPreferencesName;

    public SerializableHandler(SharedPreferences preferences, String preferencesName, Context context) {
        super(preferences);
        mPreferencesName = preferencesName;
        mContext = context;
    }

    /**
     * 写入对象到文件
     */
    public void setValue(String key, Object value) {
        File objectFile = getObjectFile(key);
        if (objectFile == null) return;
        try {
            FileOutputStream fileOutput = new FileOutputStream(objectFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的对象
     */
    public Object get(String key, Object defaultValue) {
        File objectFile = getObjectFile(key);
        if (objectFile == null) return defaultValue;
        try {
            FileInputStream fileInput = new FileInputStream(objectFile);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            return objectInput.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    private File getObjectFile(String key) {
        File dir = mContext.getDir(mPreferencesName, Context.MODE_PRIVATE);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        return new File(dir, key);
    }
}

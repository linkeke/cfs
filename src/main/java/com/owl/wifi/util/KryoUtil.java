package com.owl.wifi.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;

public class KryoUtil {
	public static Kryo buildMapKryo(Class key , Class value){
		Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(key, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(value, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

        kryo.register(key, new JavaSerializer());
        kryo.register(value , new JavaSerializer());
        kryo.register(HashMap.class, serializer);
        return kryo ;
	}
	
	public static Kryo buildListKryo(Class klass){
		Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(klass , new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(klass, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);
        return kryo ;
	}
}

package com.shag.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GSDatabase extends GSBase{
	
	public static final byte[] HEADER="GSDB".getBytes();
	public static final short VERSION = 0x0001;		// 01   //2 bytes 1byte for 0 and 1 byte for 1
	public static final byte CONTAINER_TYPE=ContainerType.DATABASE;       //Data Storage Type (field (primtive data type), Array, Object)
	
	private short objectCount;
	public List<GSObject> objects=new ArrayList<GSObject>();
	
	
	private GSDatabase() {
		
	}
	
	public GSDatabase(String name) {
		size+=HEADER.length+2+1+2;
		setName(name);
	}
	
	
	public void addObject(GSObject object) {
		objects.add(object);
		size+=object.getSize();
		objectCount=(short)objects.size();
		
	}

public int getSize() {
		
		return size;
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, HEADER);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, VERSION);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, nameLength);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, name);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, size);
		
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, objectCount);
		
		for (GSObject gsObject : objects) {
			pointer=gsObject.getBytes(dest, pointer);
		}
		
		
		return pointer;
		
	}
	
	public static GSDatabase Deserialize(byte[] data) {
		int pointer=0;
		String header=SeriaalizationUtils.readString(data, pointer, HEADER.length);
		assert(header.equals(HEADER));
		pointer+=HEADER.length;
		
		short version=SeriaalizationUtils.readShort(data, pointer);
		if(version!=VERSION) {
			System.err.println("Invalid GSDB version");
			return null;
		}
		pointer+=2;
		
		byte containerType=SeriaalizationUtils.readByte(data,pointer);
		assert(containerType==CONTAINER_TYPE);
		pointer++;
		
		GSDatabase result=new GSDatabase();
		
		result.nameLength= SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		result.name=SeriaalizationUtils.readString(data, pointer, result.nameLength).getBytes();
		pointer+=result.nameLength;
		
		result.size=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		result.objectCount=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		
		for (int i = 0; i < result.objectCount; i++) {
			GSObject object=GSObject.Deserialize(data, pointer);
			result.objects.add(object);
			pointer+=object.getSize();
		}
		
		System.out.println(header);
		return result;
	}
	
	public GSObject findObject(String name) {
		for (GSObject object : objects) {
			if(object.getName().equals(name)) {
				return object;
			}
		}
		
		return null;
	}

	public static GSDatabase DeserializeFromFile(String path) {
		byte[] buffer=null;
		try {
			BufferedInputStream stream=new BufferedInputStream(new FileInputStream(path));
			buffer=new byte[stream.available()];
			stream.read(buffer);
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Deserialize(buffer);
	}
	
	public void serializeToFile(String path) {
		byte[] data=new byte[getSize()];
		getBytes(data, 0);
		try {
			BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.shag.serialization;

import java.util.ArrayList;
import java.util.List;

public class GSObject extends GSBase{

	public static final byte CONTAINER_TYPE=ContainerType.OBJECT;       //Data Storage Type (field (primtive data type), Array, Object)
//	public short nameLength;
	//public byte[] name;
	private short fieldCount;
	public List<GSField> fields=new ArrayList<GSField>();
	
	private short stringCount;
	public List<GSString> strings=new ArrayList<GSString>();
	
	private short arrayCount;
	public List<GSArray> arrays=new ArrayList<GSArray>();
	
	private GSObject() {
		
	}
	
	public GSObject(String name) {
		size+=1+2+2+2;
		setName(name);
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE );
		if(this.name!=null) {
			size-=this.name.length;
		}
		nameLength=(short)name.length();
		this.name=name.getBytes();
		size+=nameLength;
		
	}
	
	public String getName() {
		return new String(name,0, nameLength);
	}

	public void addArray(GSArray array) {
		arrays.add(array);
		size+=array.getSize();
		arrayCount=(short)arrays.size();
		
	}

	public void addField(GSField field) {
		fields.add(field);
		size+=field.getSize();
		fieldCount=(short)fields.size();
		
	}
	
	public void addString(GSString string) {
		strings.add(string);
		size+=string.getSize();
		stringCount=(short)strings.size();
		
	}


	public int getSize() {
		
		return size;
	}
	
	public GSField findField(String name) {
		for (GSField field : fields) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		
		return null;
	}
	
	public GSString findString(String name) {
		for (GSString string : strings) {
			if(string.getName().equals(name)) {
				return string;
			}
		}
		
		return null;
	}
	
	
	public GSArray findArray(String name) {
		for (GSArray array : arrays) {
			if(array.getName().equals(name)) {
				return array;
			}
		}
		
		return null;
	}
	

	public int getBytes(byte[] dest, int pointer) {
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, nameLength);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, name);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, size);
		
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, fieldCount);
		
		for (GSField gsField : fields) {
			pointer=gsField.getBytes(dest, pointer);
		}
		
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, stringCount);
		
		for (GSString gsStrings : strings) {
			pointer=gsStrings.getBytes(dest, pointer);
		}
		
		
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, arrayCount);
		
		for (GSArray gsArray : arrays) {
			pointer=gsArray.getBytes(dest, pointer);
		}
		
		return pointer;
		
	}
	
	public static GSObject Deserialize(byte[] data,int pointer) {
		byte containerType=data[pointer++];
		assert(containerType==CONTAINER_TYPE);
		
		GSObject result=new GSObject();
		result.nameLength= SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		result.name=SeriaalizationUtils.readString(data, pointer, result.nameLength).getBytes();
		pointer+=result.nameLength;
		
		result.size=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		result.fieldCount=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		for (int i = 0; i < result.fieldCount; i++) {
			GSField field=GSField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer+=field.getSize();
		}
		
		
		result.stringCount=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		for (int i = 0; i < result.stringCount; i++) {
			GSString string=GSString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer+=string.getSize();
		}
		
		
		result.arrayCount=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		for (int i = 0; i < result.arrayCount; i++) {
			GSArray array=GSArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer+=array.getSize();
		}
		
		return result;
	}
}

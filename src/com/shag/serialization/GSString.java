package com.shag.serialization;

public class GSString extends GSBase{
	public static final byte CONTAINER_TYPE=ContainerType.STRING;       //Data Storage Type (field (primtive data type), Array, Object)

	public int count;
	private char[] characters;

	
	private GSString() {
		size+=1+4;
	}
	
	
	
	public String getString() {
		return new String(characters);
	}


	
	public int getBytes(byte[] dest , int pointer) {
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, nameLength);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, name);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, size);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, count);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, characters);
		
		return pointer;
	}
	
	private void updateSize() {
		size+=getDataSize();
	}
	
	
	public int getSize() {
		return size;
	}
	
	public int getDataSize() {
		return characters.length*Type.getSize(Type.CHAR);
		
	}
	
	
	
	public static GSString Create(String name, String data) {
		GSString string=new GSString();
		string.setName(name);
		string.count=data.length();
		string.characters=data.toCharArray();
		string.updateSize();
		return string;
	}
	
	public static GSString Deserialize(byte[] data,int pointer) {
		byte containerType=data[pointer++];
		assert(containerType==CONTAINER_TYPE);
		
		GSString result=new GSString();
		result.nameLength=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		result.name=SeriaalizationUtils.readString(data, pointer,result.nameLength).getBytes();
		pointer+=result.nameLength;
		
		result.size=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		result.count=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		
		result.characters=new char[result.count];
		SeriaalizationUtils.readChars(data, pointer, result.characters);
		pointer+=result.count* Type.getSize(Type.CHAR);
		return result;
	}
	
}

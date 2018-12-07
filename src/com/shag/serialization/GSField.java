package com.shag.serialization;

public class GSField extends GSBase{

	public static final byte CONTAINER_TYPE=ContainerType.FIELD;       //Data Storage Type (field (primtive data type), Array, Object)
	public byte type;
	public byte[] data;
	
	private GSField() {
		// TODO Auto-generated constructor stub
	}
	
	public byte  getByte() {
		return SeriaalizationUtils.readByte(data, 0);
	}
	
	public short  getShort() {
		return SeriaalizationUtils.readShort(data, 0);
	}
	
	public char  getChar() {
		return SeriaalizationUtils.readChar(data, 0);
	}
	
	public int  getInt() {
		return SeriaalizationUtils.readInt(data, 0);
	}
	
	public long  getLong() {
		return SeriaalizationUtils.readLong(data, 0);
	}
	
	public float  getFloat() {
		return SeriaalizationUtils.readFloat(data, 0);
	}
	
	public double  getDouble() {
		return SeriaalizationUtils.readDouble(data, 0);
	}
	
	public boolean  getBoolean() {
		return SeriaalizationUtils.readBoolean(data, 0);
	}
	
	public int getBytes(byte[] dest , int pointer) {
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, nameLength);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, name);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, type);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, data);
		return pointer;
	}
	
	public int getSize() {
		assert(data.length==Type.getSize(type));
		return 1+2+name.length+1+data.length;
	}
	
	public static GSField Byte(String name, byte value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.BYTE;
		field.data=new byte[Type.getSize(Type.BYTE)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static GSField Character(String name, char value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.CHAR;
		field.data=new byte[Type.getSize(Type.CHAR)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Short(String name, short value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.SHORT;
		field.data=new byte[Type.getSize(Type.SHORT)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Integer(String name, int value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.INTEGER;
		field.data=new byte[Type.getSize(Type.INTEGER)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Long(String name, long value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.LONG;
		field.data=new byte[Type.getSize(Type.LONG)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Float(String name, float value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.FLOAT;
		field.data=new byte[Type.getSize(Type.FLOAT)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Double(String name, double value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.DOUBLE;
		field.data=new byte[Type.getSize(Type.DOUBLE)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Boolean(String name, boolean value) {
		GSField field=new GSField();
		field.setName(name);
		field.type=Type.BOOLEAN;
		field.data=new byte[Type.getSize(Type.BOOLEAN)];
		SeriaalizationUtils.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static GSField Deserialize(byte[] data,int pointer) {
		byte containerType=data[pointer++];
		assert(containerType==CONTAINER_TYPE);
		
		GSField result=new GSField();
		result.nameLength=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		result.name=SeriaalizationUtils.readString(data, pointer,result.nameLength).getBytes();
		pointer+=result.nameLength;
		
		result.type=data[pointer++];
		
		result.data=new byte[Type.getSize(result.type)];
		SeriaalizationUtils.readBytes(data, pointer, result.data);
		pointer+=Type.getSize(result.type);
		return result;
	}
}

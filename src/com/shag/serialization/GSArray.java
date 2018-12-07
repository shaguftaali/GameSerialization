package com.shag.serialization;

public class GSArray extends GSBase{
	public static final byte CONTAINER_TYPE=ContainerType.ARRAY;       //Data Storage Type (field (primtive data type), Array, Object)
	
	public byte type;
	public int count;
	
	public byte[] byteData;
	private short[] shortData;
	private char[] charData;
	private int[] intData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;
	
	private GSArray() {
		size+=1+1+4;
	}
	
	
	
	public int getBytes(byte[] dest , int pointer) {
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, nameLength);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, name);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, type);
		pointer=SeriaalizationUtils.writeBytes(dest, pointer, count);
		switch (type) {
		case Type.BYTE:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, byteData);			
			break;
			
		case Type.CHAR:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, charData);			
			break;
			
		case Type.SHORT:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, shortData);			
			break;
			
		case Type.INTEGER:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, intData);			
			break;
			
		case Type.LONG:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, longData);			
			break;
			
		case Type.FLOAT:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, floatData);			
			break;
			
		case Type.DOUBLE:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, doubleData);			
			break;
			
		case Type.BOOLEAN:
			pointer=SeriaalizationUtils.writeBytes(dest, pointer, booleanData);			
			break;

		default:
			break;
		}
		return pointer;
	}
	
	private void updateSize() {
		size+=getDataSize();
	}
	
	
	public int getSize() {
		return size;
	}
	
	public int getDataSize() {
		switch (type) {
			case Type.BYTE:		
				return byteData.length*Type.getSize(Type.BYTE);
				
			case Type.CHAR:
				return charData.length*Type.getSize(Type.CHAR);
				
			case Type.SHORT:
				return shortData.length*Type.getSize(Type.SHORT);
				
			case Type.INTEGER:
				return intData.length*Type.getSize(Type.INTEGER);
				
			case Type.LONG:
				return longData.length*Type.getSize(Type.LONG);
				
			case Type.FLOAT:
				return floatData.length*Type.getSize(Type.FLOAT);
				
			case Type.DOUBLE:
				return doubleData.length*Type.getSize(Type.DOUBLE);
				
			case Type.BOOLEAN:
				return booleanData.length*Type.getSize(Type.BOOLEAN);
		
		}
		return 0;
	}
	
	public static GSArray Byte(String name, byte[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.BYTE;
		array.count=data.length;
		array.byteData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Char(String name, char[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.CHAR;
		array.count=data.length;
		array.charData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Short(String name, short[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.SHORT;
		array.count=data.length;
		array.shortData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Integer(String name, int[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.INTEGER;
		array.count=data.length;
		array.intData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Long(String name, long[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.LONG;
		array.count=data.length;
		array.longData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Float(String name, float[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.FLOAT;
		array.count=data.length;
		array.floatData=data;
		array.updateSize();
		return array;
	}
	
	
	public static GSArray Double(String name, double[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.DOUBLE;
		array.count=data.length;
		array.doubleData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Boolean(String name, boolean[] data) {
		GSArray array=new GSArray();
		array.setName(name);
		array.type=Type.BOOLEAN;
		array.count=data.length;
		array.booleanData=data;
		array.updateSize();
		return array;
	}
	
	public static GSArray Deserialize(byte[] data,int pointer) {
		byte containerType=data[pointer++];
		assert(containerType==CONTAINER_TYPE);
		
		GSArray result=new GSArray();
		result.nameLength=SeriaalizationUtils.readShort(data, pointer);
		pointer+=2;
		
		result.name=SeriaalizationUtils.readString(data, pointer,result.nameLength).getBytes();
		pointer+=result.nameLength;
		
		result.size=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		result.type=data[pointer++];
		
		result.count=SeriaalizationUtils.readInt(data, pointer);
		pointer+=4;
		
		
		switch (result.type) {
		case Type.BYTE:
			result.byteData=new byte[result.count];
			SeriaalizationUtils.readBytes(data, pointer, result.byteData);			
			break;
			
		case Type.CHAR:
			result.charData=new char[result.count];
			SeriaalizationUtils.readChars(data, pointer, result.charData);				
			break;
			
		case Type.SHORT:
			result.shortData=new short[result.count];
			SeriaalizationUtils.readShorts(data, pointer, result.shortData);				
			break;
			
		case Type.INTEGER:
			result.intData=new int[result.count];
			SeriaalizationUtils.readInts(data, pointer, result.intData);				
			break;
			
		case Type.LONG:
			result.longData=new long[result.count];
			SeriaalizationUtils.readLongs(data, pointer, result.longData);				
			break;
			
		case Type.FLOAT:
			result.floatData=new float[result.count];
			SeriaalizationUtils.readFloats(data, pointer, result.floatData);				
			break;
			
		case Type.DOUBLE:
			result.doubleData=new double[result.count];
			SeriaalizationUtils.readDoubles(data, pointer, result.doubleData);				
			break;
			
		case Type.BOOLEAN:
			result.booleanData=new boolean[result.count];
			SeriaalizationUtils.readBooleans(data, pointer, result.booleanData);				
			break;

		default:
			break;
		}
		pointer+=result.count*Type.getSize(result.type);
		return result;
	}
	
}

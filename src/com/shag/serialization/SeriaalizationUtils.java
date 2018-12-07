package com.shag.serialization;

import java.nio.ByteBuffer;

public class SeriaalizationUtils {
	
	public static int writeBytes(byte[] dest, int pointer,byte[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			dest[pointer++]=src[i];
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,char[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,short[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,int[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,long[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,float[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,double[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,boolean[] src) {
		assert(dest.length>pointer+src.length);
		for (int i = 0; i < src.length; i++) {
			pointer=writeBytes(dest, pointer, src[i]);
		}
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,byte value) {
		assert(dest.length>pointer+Type.getSize(Type.BYTE));
		dest[pointer++]=value;
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,short value) {
		//shot is 2 bytes each array element stores one byte 
		//so short will occupy two array elements 
		assert(dest.length>pointer+Type.getSize(Type.SHORT));
		dest[pointer++]=(byte)((value >> 8) & 0xff);
		dest[pointer++]=(byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,char value) {
		//char is 2 bytes each array element stores one byte 
		//so short will occupy two array elements 
		assert(dest.length>pointer+Type.getSize(Type.CHAR));
		dest[pointer++]=(byte)((value >> 8) & 0xff);
		dest[pointer++]=(byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,int value) {
		//int is 4 bytes each array element stores one byte 
		//so int will occupy 4 array elements 
		assert(dest.length>pointer+Type.getSize(Type.INTEGER));
		
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		
		
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,long value) {
		//long is 8 bytes each array element stores one byte 
		//so long will occupy 8 array elements 
		assert(dest.length>pointer+Type.getSize(Type.LONG));
		dest[pointer++]=(byte)((value >> 56) & 0xff);
		dest[pointer++]=(byte)((value >> 48) & 0xff);
		dest[pointer++]=(byte)((value >> 40) & 0xff);
		dest[pointer++]=(byte)((value >> 32) & 0xff);
		dest[pointer++]=(byte)((value >> 24) & 0xff);
		dest[pointer++]=(byte)((value >> 16) & 0xff);
		dest[pointer++]=(byte)((value >> 8) & 0xff);
		dest[pointer++]=(byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,float value) {
		assert(dest.length>pointer+Type.getSize(Type.FLOAT));
		int data= Float.floatToIntBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer,double value) {
		assert(dest.length>pointer+Type.getSize(Type.DOUBLE));
		long data= Double.doubleToLongBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer,boolean value) {
		//boolean is 1 bytes each array element stores one byte 
		//so boolean will occupy 1 array elements 
		assert(dest.length>pointer+Type.getSize(Type.BOOLEAN));
		dest[pointer++]=(byte)(value?1:0);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer,String value) {
		pointer=writeBytes(dest, pointer, (short)value.length());
		return writeBytes(dest, pointer, value.getBytes());
	}
	
	public static byte readByte(byte[] src, int pointer) {
		
		return (src[pointer]);
	}
	
	public static void readBytes(byte[] src, int pointer,byte[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=src[pointer++];
			
		}
	}
	
	public static void readChars(byte[] src, int pointer,char[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readChar(src, pointer);
			pointer+=Type.getSize(Type.CHAR);
		}
	}
	
	public static void readShorts(byte[] src, int pointer,short[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readShort(src, pointer);
			pointer+=Type.getSize(Type.SHORT);
		}
	}
	
	public static void readInts(byte[] src, int pointer,int[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readInt(src, pointer);
			pointer+=Type.getSize(Type.INTEGER);
		}
	}
	
	public static void readLongs(byte[] src, int pointer,long[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readLong(src, pointer);
			pointer+=Type.getSize(Type.LONG);
		}
	}
	
	public static void readFloats(byte[] src, int pointer,float[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readFloat(src, pointer);
			pointer+=Type.getSize(Type.FLOAT);
		}
	}
	
	public static void readDoubles(byte[] src, int pointer,double[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readDouble(src, pointer);
			pointer+=Type.getSize(Type.DOUBLE);
		}
	}
	
	public static void readBooleans(byte[] src, int pointer,boolean[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i]=readBoolean(src, pointer);
			pointer+=Type.getSize(Type.BOOLEAN);
		}
	}
		
	public static short readShort(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 2).getShort();
//		return (short)((src[pointer]) << 8 | (src[pointer+1]));
	}
	
	public static char readChar(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 2).getChar();
//		return (char)((src[pointer]) << 8 | (src[pointer+1]));
	}
	
	
	public static int readInt(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 4).getInt();
		//return (int)((src[pointer]) << 24 | (src[pointer+1]) << 16 | (src[pointer+2]) << 8 | (src[pointer+3]));
	}
	
	public static long readLong(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 8).getLong();
//		return (int)((src[pointer]) << 56 | (src[pointer+1]) << 48 | (src[pointer+2]) << 40 | (src[pointer+3]) <<32 |
//						(src[pointer+4]) << 24 | (src[pointer+5]) << 16 | (src[pointer+6]) << 8 | (src[pointer+7]));
	}
	

	public static float readFloat(byte[] src, int pointer) {
		return Float.intBitsToFloat(readInt(src, pointer));
	}
	
	public static double readDouble(byte[] src, int pointer) {
		return Double.longBitsToDouble(readInt(src, pointer));
	}
	
	
	public static boolean readBoolean(byte[] src, int pointer) {
		assert(src[pointer] ==0 || src[pointer]==1);
		return src[pointer]==1;
	}
	
	public static String readString(byte[] src, int pointer, int length) {
		return new String(src,pointer,length);
	}
	
}

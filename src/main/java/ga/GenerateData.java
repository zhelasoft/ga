package ga;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import com.github.javafaker.Faker;

public class GenerateData {
	
	Faker faker;
	Random r;
	
	GenerateData() {
		faker = new Faker();
		r = new Random();
	}
	
	public Object getData(String dataType) {
		
		
		if(dataType.equals("boolean")) {
			return this.getRandomBoolean();
		}
		
		if(dataType.equals("byte")) {
			return this.getRandomByte();
		}
		
		if(dataType.equals("char")) {
			return this.getRandomChar();
		}
		
		if(dataType.equals("short")) {
			return this.getRandomShort();
		}
		
		if(dataType.equals("int")) {
			return this.getRandomInt();
		}
		
		if(dataType.equals("long")) {
			return this.getRandomLong();
		}
		
		if(dataType.equals("float")) {
			return this.getRandomFloat();
		}
		
		if(dataType.equals("double")) {
			return this.getRandomDouble();
		}
		
		return null;
	}
	
	public boolean getRandomBoolean() {
		return this.r.nextBoolean();
	}
	
	public char getRandomChar() {
		return this.faker.name().firstName().charAt(0);
	}
	
	public byte[] getRandomByte() {
		return RandomUtils.nextBytes(this.r.nextInt());
	}
	
	public short getRandomShort() {
		return (short) this.r.nextInt(1 << 16);
	}
	
	public int getRandomInt() {
//		return this.faker.number().randomDigit();
		return this.r.nextInt();
	}
	
	public long getRandomLong() {
		return this.faker.number().randomNumber();
	}
	
	public float getRandomFloat() {
		return this.r.nextFloat();
	}
	
	public double getRandomDouble() {
		return this.r.nextDouble();
	}
}
package com.jt.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;





public class TestSerilizeable implements Serializable {

	private String a="";
	private transient int b=0;
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public  int getB() {
		return b;
	}
	public  void setB(int b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "TestSerilizeable [a=" + a + +b+"]";
	}

}
class TestResult{
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		TestSerilizeable t=new TestSerilizeable();
	      t.setA("你好啊");
	      t.setB(10);
	   
	     File file = new File("D:\\1-JT","f1.txt");
	     try {
	    	 file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
	   objectOutputStream.writeObject(t);
	   objectOutputStream.close();
	   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	
	    	 TestSerilizeable readObject = (TestSerilizeable)ois.readObject();
	
	     System.out.println(readObject.toString());
	 	String [] a=new String[10];
	 	for (String string : a) {
			
		}
	}


}
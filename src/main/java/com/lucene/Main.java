package com.lucene;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter fw=new FileWriter("C:\\temp\\testout.txt");
		Random random=new Random();
		DataFactory df = new DataFactory();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < 50000000; i++) {
			sb.append(df.getFirstName()).append(" ").append(df.getLastName()).append(" ").append(random.nextInt(200000)).append("\n"); 
			fw.write(sb.toString());
			//System.out.println(sb);
			sb.setLength(0);
			
		}
		fw.flush();
		fw.close();
		System.out.println("done");
	}
}
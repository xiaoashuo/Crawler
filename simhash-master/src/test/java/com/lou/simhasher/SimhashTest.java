package com.lou.simhasher;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
* @author louxuezheng@hotmail.com
*/

public class SimhashTest {
	
	@Test
	public void testDistance(){
		//String str1 = readAllFile("D:/test/testin2.txt");
		String str1 = "我是一hi哈哈哈哈哈哈哈哈哈哈姐姐道具";
		SimHasher hash1 = new SimHasher(str1);
		System.out.println(hash1.getSignature());
		System.out.println("============================");

		//String str2 = readAllFile("D:/test/testin.txt");
		String str2 = "极为i八四八四八四比八四八四五哈哈哈哈";
		SimHasher hash2 = new SimHasher(str2);
		System.out.println(hash2.getSignature());
		System.out.println("============================");

		System.out.println(hash1.getHammingDistance(hash2.getSignature()));
		
	}
	
	/**
	 * 测试用
	 * @param filename 名字
	 * @return
	 */
	public static String readAllFile(String filename) {
		String everything = "";
		try {
			FileInputStream inputStream = new FileInputStream(filename);
			everything = IOUtils.toString(inputStream);
			inputStream.close();
		} catch (IOException e) {
		}

		return everything;
	}
}

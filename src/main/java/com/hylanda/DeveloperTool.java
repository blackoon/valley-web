package com.hylanda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.google.common.io.Files;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年11月3日 下午2:54:02 
 *根据实体生成其他后台代码以及前台
 */
public class DeveloperTool {
	public static void main(String[] args) {
		//后台代码自动生成
//		readTemplates("Account");
		//前端代码自动生成
		readhtmlTemplates("account","接口账号");
		readjsTemplates("account");
	}
	
	public static void readjsTemplates(String entityName){
		File[] files = new File("temp/js").listFiles();
		for(File file:files){
			try {
				String text = getText(Files.newReader(file, Charset.forName("utf-8")));
				String name=entityName.toLowerCase();
				File modelDir = new File("src/main/resources/static/scripts/"+name);
				writejs(text, name, file.getName(),modelDir);
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void readhtmlTemplates(String entityName,String functionName){
		File[] files = new File("temp/html").listFiles();
		for(File file:files){
			try {
				String text = getText(Files.newReader(file, Charset.forName("utf-8")));
				String name=entityName.toLowerCase();
				File modelDir = new File("src/main/resources/templates/"+name);
				writeHtml(text, name,functionName, file.getName(),modelDir);
			} catch (Exception e) {
				
			}
		}
	}
	
	
	private static BufferedWriter writejs(String text, String name,
			String newFileName, File modelDir) throws IOException {
		BufferedWriter writer;
		// 创建路径
		if (!modelDir.isDirectory()) {
			modelDir.mkdirs();
		}
		writer= Files.newWriter(new File(modelDir, newFileName),Charset.forName("utf-8"));
		text=text.replaceAll("department", name);
		writer.write(text);
		writer.flush();
		writer.close();
		return writer;
	}
	
	private static BufferedWriter writeHtml(String text, String name,String functionName,
			String newFileName, File modelDir) throws IOException {
		BufferedWriter writer;
		// 创建路径
		if (!modelDir.isDirectory()) {
			modelDir.mkdirs();
		}
		writer= Files.newWriter(new File(modelDir, newFileName),Charset.forName("utf-8"));
		text=text.replaceAll("department", name);
		text=text.replace("部门", functionName);
		writer.write(text);
		writer.flush();
		writer.close();
		return writer;
	}

	public static void readTemplates(String entityName){
		File[] files = new File("temp").listFiles();
		for (File file : files) {
					try {
						String text = getText(Files.newReader(file, Charset.forName("utf-8")));
						BufferedWriter writer=null;
						String daxie=String.valueOf(entityName.charAt(0)).toUpperCase().concat(entityName.substring(1));
						String xiaoxie=String.valueOf(entityName.charAt(0)).toLowerCase().concat(entityName.substring(1));
						if(file.getName().endsWith("Controller.java")){
							String newFileName=daxie.concat("Controller.java");
							File modelDir = new File("src/main/java/com/hylanda/controller");
							writer = writeJava(text, daxie, xiaoxie, newFileName,modelDir);
						}else if(file.getName().endsWith("Qo.java")){
							String newFileName=daxie.concat("Qo.java");
							File modelDir = new File("src/main/java/com/hylanda/model");
							writer = writeJava(text, daxie, xiaoxie, newFileName,modelDir);
						}else if(file.getName().endsWith("Redis.java")){
							String newFileName=daxie.concat("Redis.java");
							File modelDir = new File("src/main/java/com/hylanda/redis");
							writer = writeJava(text, daxie, xiaoxie, newFileName,modelDir);
						}else if(file.getName().endsWith("Repository.java")){
							String newFileName=daxie.concat("Repository.java");
							File modelDir = new File("src/main/java/com/hylanda/repository");
							writer = writeJava(text, daxie, xiaoxie, newFileName,modelDir);
						}else if(file.getName().endsWith("Service.java")){
							String newFileName=daxie.concat("Service.java");
							File modelDir = new File("src/main/java/com/hylanda/service");
							writer = writeJava(text, daxie, xiaoxie, newFileName,modelDir);
						}
						if(writer!=null)
							writer.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		}
	}

	private static BufferedWriter writeJava(String text, String daxie, String xiaoxie,
			String newFileName, File modelDir) throws FileNotFoundException,
			IOException {
		BufferedWriter writer;
		// 创建路径
		if (!modelDir.isDirectory()) {
			modelDir.mkdirs();
		}
		writer= Files.newWriter(new File(modelDir, newFileName),Charset.forName("utf-8"));
		text=text.replaceAll("User", daxie);
		text=text.replaceAll("user", xiaoxie);
		writer.write(text);
		writer.flush();
		writer.close();
		return writer;
	}
	public static String getText(Reader reader) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(reader);
			String temp = null;
			StringBuffer sb=new StringBuffer();
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\r\n");
			}
			return sb.toString();
		} finally {
			if (br != null)
				br.close();
		}
	}

}

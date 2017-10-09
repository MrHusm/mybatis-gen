package com.hivescm.mybatisgen.plugin;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liushjia
 * @date   2015年11月19日 下午12:20:31
 */
public class Myruner {

	/**
	 * @param args
	 * @throws XMLParserException 
	 * @throws IOException 
	 * @throws InvalidConfigurationException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		// TODO Auto-generated method stub
		List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   File configFile = new File("src/main/resources/generatorConfig.xml");
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config = cp.parseConfiguration(configFile);
		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		   myBatisGenerator.generate(null);
		   System.out.println("生成成功~");
	}

}

package com.hivescm.mybatisgen.plugin;

import java.util.List;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * extends PluginAdapter
 * Created by Administrator on 2017/6/22.
 */
public class MybatisPaginationPlugin extends PluginAdapter {
	
	private static String MAPPER_PACKAGE="com.hivescm.operation.mapper";
	private static String PAGE_PACKAGE="com.hivescm.pay.common.page.Page";
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
		// add field, getter, setter for limit clause
		addPage(topLevelClass, introspectedTable, "page");        
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}    
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//addGetMapperClassNameMethodToBeans(topLevelClass, introspectedTable, "mapper");
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,IntrospectedTable introspectedTable) {
		XmlElement page = new XmlElement("if");
		page.addAttribute(new Attribute("test", "page != null"));
		page.addElement(new TextElement("limit #{page.begin} , #{page.length}"));
		element.addElement(page);        
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}  
	/**
	 * @param topLevelClass
	 * @param introspectedTable
	 * @param name
	 */
	private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
		topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType(MybatisPaginationPlugin.PAGE_PACKAGE));
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType(MybatisPaginationPlugin.PAGE_PACKAGE));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(MybatisPaginationPlugin.PAGE_PACKAGE), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(MybatisPaginationPlugin.PAGE_PACKAGE));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	} 
	/***
	* 
	* @param topLevelClass
	* @param introspectedTable
	* @param name
	*/
	private void addGetMapperClassNameMethodToBeans(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String name) {
		// TODO Auto-generated method stub
		CommentGenerator commentGenerator = context.getCommentGenerator();  
		Method method = new Method();  
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("java.lang.String")); 
		char c = name.charAt(0);  
		String camel = Character.toUpperCase(c) + name.substring(1); 
		method.setName("get"+camel); 
		method.addBodyLine("return \"" +MybatisPaginationPlugin.MAPPER_PACKAGE + "\"+this.getClass().getName().substring(this.getClass().getName().lastIndexOf(\".\"))+\""+camel+"\";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable); 
		topLevelClass.addMethod(method); 
	}
	/**
	 * This plugin is always valid - no properties are required
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}
}

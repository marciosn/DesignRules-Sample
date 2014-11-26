package design.rules.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import design.rules.DesignRulesSample;
import design.rules.Module;

public class TestDesignRules {

	DesignRulesSample dw = new DesignRulesSample("lib" + File.separator	+ "educaBrasil.jar");

	@Test
	public void testModule1DependsModule2Package() {
		Module module = new Module();
		Module module2 = new Module();
		module.add("org.educabrasil.controller");
		module2.add("org.educabrasil.beans");
		assertTrue(dw.DependencyModules(module, module2));
	}

	@Test
	public void testModule1DependsModule2Classes() {
		Module module = new Module();
		Module module2 = new Module();
		module.add("ControladorDespesas.class"); //classe a ser testada deve ser passada sem as aspas
		module2.add("PreparaSessao.class");		//classe a ser testada deve ser passada sem as aspas
		assertTrue(dw.DependencyClasses(module, module2));
	}

	@Test
	public void testQtdParameters() {
		Module module = new Module();
		module.add("org.educabrasil.parsers");
		assertTrue(dw.manyParameters(module, 2));
	}

	@Test
	public void testQtdMethods() {
		Module module = new Module();
		module.add("org.educabrasil.parsers");
		assertTrue(dw.manyMethods(module, 7));
	}

	@Test
	public void testQtdParametersAllMethods() {
		assertTrue(dw.manyParametersAllMethods(2));
	}

	@Test
	public void testQtdMethodsAllClasses() {
		assertTrue(dw.manyMethodsAllClasses(17));
	}

}

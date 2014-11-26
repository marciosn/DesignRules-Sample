package design.rules;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class DesignRulesSample {
	private DesignWizard designWizard;
	
	public static void main(String[] args) throws IOException {
		DesignRulesSample educaB = new DesignRulesSample("lib" + File.separator + "educaBrasil.jar");
				
		Module module = new Module();
		Module module2 = new Module();
		module.add("org.educabrasil.controller");
		module2.add("org.educabrasil.beans");

		if (educaB.DependencyModules(module, module2)) {
			System.out.println("Verdade!");
		} else {
			System.out.println("Falso!");
		}	
		
	}

	public DesignRulesSample(String appJarPath) {
		try {
			designWizard = new DesignWizard(appJarPath);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public boolean DependencyModules(Module module, Module module2){
		boolean depends = false;
		
		Set<ClassNode> classNodesModule = new HashSet<ClassNode>();
		classNodesModule.addAll(convertPackageNamesToClassNodes(module.getPackageNames()));
				
		for(ClassNode classNode : classNodesModule){
			Set<PackageNode> packages = classNode.getCalleePackages();           //Returns a Set of PackageNode containing the packages that are referenced by this Entity.
			for(PackageNode packageNode : packages){                             //System.out.println(packageNode.getName());
				if(packageNode.getName().contains(module2.getName())){
					depends = true;
				}
			}
		}	
		
		return depends;
	}
	
	public boolean DependencyClasses(Module module, Module module2){
		boolean dependsClasses = false;
		
		Set<ClassNode> classNodes = new HashSet<ClassNode>();
        classNodes.addAll(convertClassTypesToClassNodes(module.getClassTypes()));
        
        Set<ClassNode> classNodes2 = new HashSet<ClassNode>();
        classNodes2.addAll(convertClassTypesToClassNodes(module2.getClassTypes()));
        
        ClassNode classNodeModule2 = null;
        
        
        for(ClassNode classNode : classNodes2){
            classNodeModule2 = classNode.getClassNode();
        }
				
		for(ClassNode classNode : classNodes){
			Set<ClassNode> calleeClasses = classNode.getCalleeClasses();
			
			if(calleeClasses.contains(classNodeModule2)){
			    dependsClasses = true;
			}
		}	
		
		return dependsClasses;
	}
	
	public boolean manyParameters(Module module, int qtd_parameters){
		boolean manyParameters = true;
		
		Set<ClassNode> classNodesModule = new HashSet<ClassNode>();
		classNodesModule.addAll(convertPackageNamesToClassNodes(module.getPackageNames()));
		
		for(ClassNode classNode : classNodesModule){
			Set<MethodNode> methods = classNode.getAllMethods();
			for(MethodNode methodNode : methods){                    //System.out.println(methodNode.getShortName() + " | " + methodNode.getParameters().size());
				if(methodNode.getParameters().size() > qtd_parameters){
					manyParameters = false;
				}
			}
		}	
		
		return manyParameters;
	}
	
	public boolean manyParametersAllMethods(int qtd_parameters){
		boolean manyParameters = true;
		
		Set<MethodNode> allMethods = designWizard.getAllMethods();
			for(MethodNode methodNode : allMethods){	                //System.out.println(methodNode.getShortName() + " | " + methodNode.getParameters().size());
				if(methodNode.getParameters().size() > qtd_parameters){
					manyParameters = false;
				}
		}	
		
		return manyParameters;
	}
	
	public boolean manyMethods(Module module, int qtd_methods){
		boolean manyMethods = true;
		
		Set<ClassNode> classNodesModule = new HashSet<ClassNode>();
		classNodesModule.addAll(convertPackageNamesToClassNodes(module.getPackageNames()));
	
		for(ClassNode classNode : classNodesModule){              //System.out.println(classNode.getShortName() + " " +classNode.getAllMethods().size());
			if(classNode.getAllMethods().size() > qtd_methods){
				manyMethods = false;
			}
		}	
		
		return manyMethods;
	}
	
	public boolean manyMethodsAllClasses(int qtd_methods){
		boolean manyMethods = true;
		
		Set<ClassNode> classNodesModule = designWizard.getAllClasses();
	
		for(ClassNode classNode : classNodesModule){ 	            //System.out.println(classNode.getShortName() + " " +classNode.getAllMethods().size());
			if(classNode.getAllMethods().size() > qtd_methods){
				manyMethods = false;
			}
		}	
		
		return manyMethods;
	}
		
	public void getCalleeClasses(Module module){
	    
	    Set<ClassNode> classNodes = new HashSet<ClassNode>();
        if (module.hasClassTypes()) {
            classNodes.addAll(convertClassTypesToClassNodes(module.getClassTypes()));
        }
        if (module.hasPackageNames()) {
            classNodes.addAll(convertPackageNamesToClassNodes(module.getPackageNames()));
        }
	
		for(ClassNode classNode : classNodes){
			System.out.println(classNode.getCalleeClasses());
		}	
	}
		
	public void getCallerPackages(Module module){
	    
	    Set<ClassNode> classNodes = new HashSet<ClassNode>();
        if (module.hasClassTypes()) {
            classNodes.addAll(convertClassTypesToClassNodes(module.getClassTypes()));
        }
        if (module.hasPackageNames()) {
            classNodes.addAll(convertPackageNamesToClassNodes(module.getPackageNames()));
        }
		
		for(ClassNode classNode : classNodes){
			Set<PackageNode> packages2 = classNode.getCallerPackages();
			for(PackageNode pkg : packages2){
				System.out.println(pkg.getName());
			}
		}	
	}
	
	
	private Set<ClassNode> convertClassTypesToClassNodes(Set<Class<?>> classTypes) {
        Set<ClassNode> classNodes = new HashSet<ClassNode>();
        for (Class<?> classType : classTypes) {
            try {
                classNodes.add(designWizard.getClass(classType));
            } catch (InexistentEntityException iee) {
                throw new RuntimeException(iee);
            }
        }
        return classNodes;
    }
		
	private Set<ClassNode> convertPackageNamesToClassNodes(	Set<String> packageNames) {
		Set<ClassNode> classNodes = new HashSet<ClassNode>();
		for (String packageName : packageNames) {
			try {
				classNodes.addAll(designWizard.getPackage(packageName).getAllClasses());
			} catch (InexistentEntityException iee) {
				throw new RuntimeException(iee);
			}
		}
		return classNodes;
	}

}

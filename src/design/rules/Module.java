package design.rules;

import java.util.HashSet;
import java.util.Set;

public class Module {

	private Set<Class<?>> classTypes;

	private Set<String> packageNames;
	
	private String name;

	public Module() {
		classTypes = new HashSet<Class<?>>();
		packageNames = new HashSet<String>();
	}
	public void add(Class<?> classType) {
		classTypes.add(classType);
	}
	public void add(String packageName) {
		this.name = packageName;
		packageNames.add(packageName);
	}
	public Set<Class<?>> getClassTypes() {
		return classTypes;
	}
	public Set<String> getPackageNames() {
		return packageNames;
	}
	public boolean hasClassTypes() {
		return !classTypes.isEmpty();
	}
	public boolean hasPackageNames() {
		return !packageNames.isEmpty();
	}
	public String getName(){
		return name;
	}
}

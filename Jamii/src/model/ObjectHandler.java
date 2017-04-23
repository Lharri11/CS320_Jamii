package model;

public abstract class ObjectHandler {
	@SuppressWarnings("unchecked")
	public static <T> T castObject(Object obj) {
		if(obj == null)return null;
		
		else return (T) obj;
	}
}

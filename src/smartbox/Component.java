package smartbox;

import java.util.*;
import java.io.Serializable;
import java.lang.reflect.*;



public class Component implements Serializable {

    private Set<Class<?>> requiredInterfaces;
    private Set<Class<?>> providedInterfaces;
    private transient Map<Class<?>, Field> fields; // transient because Field not serializable; interfaces that have fields as required interfaces
    protected Container container;
    protected String name;

    public Component() {
        fields = new HashMap<Class<?>, Field>();
        providedInterfaces = new HashSet<Class<?>>();
        requiredInterfaces = new HashSet<Class<?>>();
        computeRequiredInterfaces();
        computeProvidedInterfaces();
        container = null;
        name = this.getClass().getSimpleName();
    }

    // add needed getters & setters
    public void setContainer(Container c) {
        container = c;
    }

    // get provided interfaces
    public Set<Class<?>> getProvidedInterfaces() {
        return this.providedInterfaces;
    }

    // get required interfaces
    public Set<Class<?>> getRequiredInterfaces() {
        return this.requiredInterfaces;
    }

    public String toString() { return name; }

    // initializes fields and requiredInterfaces
    public void computeRequiredInterfaces() {
        Field[] fieldArray = this.getClass().getDeclaredFields();
        //Field[] fieldArray = this.getClass().getFields();
        for(int i = 0; i < fieldArray.length; i++) {
            //if the type of field[i] is an interface, then add it to fields and requiredInterfaces ???
            //System.out.println("Field1 = " + fieldArray[i]);
            String[] fieldString = fieldArray[i].toString().split("\\s+");
            for (String str : fieldString) {
                //System.out.println("str = " + str);
                try {

                    Class<?> c = Class.forName(str);
                    if (c.isInterface()) {
                        //System.out.println("Field2 = " + fieldArray[i]);
                        //System.out.println("c = " + c.getName());
                        //System.out.println("Field name = " + fieldArray[i].toString());
                        fields.put(c, fieldArray[i]);
                        requiredInterfaces.add(c);
                    }
                } catch (Exception e) {
                    //mvc.Utilities.error(e);
                    //e.printStackTrace();
                }

            }
            /*if (fieldArray[i].getClass().isInterface()) {
                System.out.println("Field2 = " + fieldArray[i]);
                fields.put(fieldArray[i].getClass(), fieldArray[i]);
                requiredInterfaces.add(fieldArray[i].getClass());
            }*/
        }
    }

    // initializes provided interfaces
    public void computeProvidedInterfaces() {
        // get interfaces implemented by the class of this component and add them to providedInterfaces
        Class<?>[] interfaces = this.getClass().getInterfaces();
        providedInterfaces.addAll(Arrays.asList(interfaces));
    }

    // set the field of this object to the provider
    public void setProvider(Class<?> intf, Component provider) throws Exception {
        Field field = fields.get(intf);
        field.set(this, provider); // field probably needs to be public for this.
        //System.out.println("Field0 = " + field);

    }

    // needed by file/open
    public void initComponent() {
        fields = new HashMap<Class<?>, Field>();
        computeProvidedInterfaces();
        computeRequiredInterfaces();
    }
}

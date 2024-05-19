package smartbox;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;

import com.sun.tools.javac.Main;
import mvc.*;
import smartbox.components.StatsCalculator;


public class Container extends Model {

    private Map<Class<?>, Component> providedInterfaces = new HashMap<Class<?>, Component>();
    private Map<Class<?>, Component> requiredInterfaces = new HashMap<Class<?>, Component>();
    private Map<String, Component> components = new HashMap<String, Component>();

    public Collection<Component> getComponents() {
        return components.values();
    }

    public void addComponent(String name) throws Exception {
        String qualName = "smartbox.components." +  name;
        // Object obj = a new instance of qualName
        Class<?> c = Class.forName(qualName);
        Object obj = c.getDeclaredConstructor().newInstance();
        addComponent((Component)obj);
    }


    private void addComponent(Component component) throws Exception {
        component.setContainer(this);
        // add new guy to the components table:
        components.put(component.name, component);
        // update provided interfaces table:
        //System.out.println("Component = " + component.toString());
        for(Class<?> intf: component.getProvidedInterfaces()) {
            //System.out.println("providedInterface = " + intf.getName());
            providedInterfaces.put(intf,  component);
        }
        // update required interfaces table:
        for(Class<?> intf: component.getRequiredInterfaces()) {
            //System.out.println("requiredInterface = " + intf.getName());
            requiredInterfaces.put(intf, component);
        }

        //???

        //find providers for the new component and hook him up:
        findProviders();
        // mvc stuff:
        changed();
    }

    public void remComponent(String name) throws Exception {
        Component component = components.get(name);
        components.remove(name);
        // unhook removed guy from any clients:
        for(Class<?> intf: component.getProvidedInterfaces()) {
            for(Component client: components.values()) {
                if (client.getRequiredInterfaces().contains(intf)) {
                    client.setProvider(intf,  null);
                    requiredInterfaces.put(intf, client);
                }
            }
        }
        changed();
    }

    // each time we add a new component we try to connect as many clients and providers as we can:
    private void findProviders() throws Exception {
        Set<Class<?>> reqInterfaces = requiredInterfaces.keySet();
        for(Class<?> intf: reqInterfaces) {
            Component client = requiredInterfaces.get(intf);
            Component provider = providedInterfaces.get(intf);
            if (client != null && provider != null) {
                //System.out.println("client.name = " + client.name);
                //System.out.println("intf = " + intf);
                //System.out.println("provider.name = " + provider.name);
                client.setProvider(intf, provider);
                //requiredInterfaces.remove(intf); this line makes iterator obsolete
                requiredInterfaces.put(intf, null);
            }
        }
    }

    public void launch(String name) throws Exception {
        try {
            // look up component and call main if it's an App
            String qualName = "smartbox.components." + name;
            Class<?> c = Class.forName(qualName);
            Class<?> argTypes[] = null;
            //System.out.println("c.getName() = " + c.getName());
            //Object obj = c.getDeclaredConstructor().newInstance();
            //System.out.println("obj.toString() = " + obj);
            Method methods[] = c.getMethods();
            //for (Method m : methods) {
                //System.out.println(m.getName());
            //}
            Method method = c.getMethod("main", argTypes);
            //System.out.println("method.getName() = " + method.getName());
            Component component = components.get(name);
            method.invoke(component, null);
        } catch(Exception e) {
            mvc.Utilities.error(e);
            e.printStackTrace();
        }
    }

    // needed by File/Open to restore component.fields
    public void initContainer(){
        for(Component c: components.values()) c.initComponent();
        changed(); // needed?
    }

}

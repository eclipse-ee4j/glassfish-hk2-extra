/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.hk2.osgiresourcelocator;

/**
 * This is a gateway to OSGi bundles as far as META-INF/services files are concerned.
 * Since META-INF/services files are not exportable, clients relying on Java SPI mechanism
 * can't discover all providers. This utility helps in such a situation. It provides a utility method
 * {@link #lookupProviderInstances} which can find META-INF/services being part of OSGi bundles.
 * This class has been carefully coded to avoid any reference to OSGi classes so that
 * it can be called in a non-OSGi environment as well. In such an environment,
 * it simply returns null.
 * In an OSGi environment, we expect the class to be initialized by the bundle activator.
 *
 * @see {@link #lookupProviderInstances}
 *
 * @author Sanjeeb.Sahoo@Sun.COM
 */
public abstract class ServiceLoader {
    /**
     * Singleton
     */
    private static ServiceLoader _me;

    /*package*/ ServiceLoader() {}

    public static synchronized void initialize(ServiceLoader singleton) {
        if (singleton == null) throw new NullPointerException("Did you intend to call reset()?");
        if (_me != null) throw new IllegalStateException("Already initialzed with [" + _me + "]");
        _me = singleton;
    }

    public static synchronized void reset() {
        if (_me == null) {
            throw new IllegalStateException("Not yet initialized");
        }
        _me = null;
    }

    public interface ProviderFactory<T> {
        T make(Class providerClass, Class<T> serviceClass) throws Exception;
    }

    /**
     * Calling this method is equivalent to calling {@link #lookupProviderInstances(Class, ProviderFactory)}
     * with a null factory object.
     *
     * @see #lookupProviderInstances(Class, org.glassfish.hk2.osgiresourcelocator.ServiceLoader.ProviderFactory)
     */
    public static <T> Iterable<? extends T> lookupProviderInstances(Class<T> serviceClass) {
        return lookupProviderInstances(serviceClass, null);
    }

    /**
     * @param serviceClass type of service requested
     * @param factory ProviderFactory used to instantiate provider instance from a provider class. If null is supplied,
     * it calls Class.newInstance to obtain a provider instance from provider class.
     * @param <T>
     * @return provider instances implementing the given service class. 
     */
    public static <T> Iterable<? extends T> lookupProviderInstances(Class<T> serviceClass, ProviderFactory<T> factory) {
        if (_me == null) return null;
        return _me.lookupProviderInstances1(serviceClass, factory);
    }

    /**
     * It is not clear why one needs this method, but it is provided just in case one needs it.
     * Returns classes found in META-INF/services/serviceClass.getName() in OSGi bundles. This method searches for
     * such named resources in every OSGi bundle. For every resource found, it assumes that the file contains
     * a class name. It loads the class name mentioned in that file using the bundle containing the resource.
     * It does not check if the class mentioned in provider file actually implements/extends service class,
     * because there are cases where it does not. JAXB is an example. JAXBContext provider file contains
     * a class that's used as a factory class for JAXBContext. To handle such issues, yet not return classes
     * that are not class loader compatible, this method will only return those classes which see the same service
     * class as supplied in the parameter.
     * Let's take an example. We have JAXB classes exported by JRE and there is a bundle A which contains JAXB API
     * and implementation. The bundle B is wired to itself for JAXB classes. Assume a user supplied bundle
     * B is wired to JRE's JAXB. When bundle B calls JAXBContext.createContext(JAXBContext.class),
     * JRE's JAXBContext will use this API. The implementation will look for META-INF/services/JAXBContext files and
     * find a file in JAXB bundle. Let's say that file contains com.acme.jaxb.JAXBContextFactory.
     * com.acme.jaxb.ContextFactory does not implement JAXBContext.class. Instead, as per JAXB spec,
     * it must provide some factory methods to create JAXBContext. If our implementation simply returns that class
     * without doing any further tests, a nasty class cast exception is going to be resulted, because user's bundle B
     * uses JAXB from JRE, which means JAXBContext.class is loaded by JRE, yet com.acme.jaxb.JAXBContextFactory
     * uses JAXBContext from bundle A. To avoid such problem, we will try to load JAXBContext using class loader of
     * com.acme.jaxb.JAXBContextFactory and see if that's same as supplied JAXBContext.class. If they are same,
     * we return com.acme.jaxb.JAXBContextFactory, else we don't. In this example, we won't.
     *
     * @param serviceClass type of service requested
     * @param <T>
     * @return classes corresponding to entries in META-INF/services file for the service class.
     */
    public static <T> Iterable<Class> lookupProviderClasses(Class<T> serviceClass) {
        return _me.lookupProviderClasses1(serviceClass);
    }

    /*package*/ abstract <T> Iterable<? extends T> lookupProviderInstances1(Class<T> serviceType, ProviderFactory<T> factory);
    /*package*/ abstract <T> Iterable<Class> lookupProviderClasses1(Class<T> serviceType);

}

package com.evilmidget38.bukkitguice.services;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.lang3.AnnotationUtils;

public class ServiceManager {
    private final Map<Class<?>, ServiceImplementations> services = Maps.newHashMap();
    private final Map<Class<? extends Annotation>, Constraint> constraintMap = Maps.newHashMap();
    public void addConstraint(Class<? extends Annotation> annotation, Constraint<?> constraint) {
        constraintMap.put(annotation, constraint);
    }

    @SuppressWarnings("unchecked")
    public void registerClass(Class<?> service, Class<?> clazz) {
        System.out.println("Registering " + service + " and " + clazz);
        boolean passed = true;
        ServiceImplementations impls = services.get(service);
        if (impls == null) {
            impls = new ServiceImplementations();
            services.put(service, impls);
        }
        ServiceImplementation impl = new ServiceImplementation(service, clazz);
        impls.getServiceImplementations().add(impl);
        for (Map.Entry<Class<? extends Annotation>, Constraint> entry : constraintMap.entrySet()) {
            Annotation annotation = clazz.getAnnotation(entry.getKey());
            if (!entry.getValue().apply(annotation)) {
                passed = false;
                break;
            }
        }
        if (passed) {
            impls.getValidImplementations().add(impl);
        }
        System.out.println("Services:"+services);
    }

    public void validateServices(Logger logger) {
        boolean success = true;
        for (Map.Entry<Class<?>, ServiceImplementations> entry : services.entrySet()) {
            if (entry.getValue().getValidImplementations().size() == 0) {
                success = false;
                logger.severe("Found no valid implementations of " + entry.getKey().getSimpleName());
                logger.severe("The following implementations exist:");
                logger.severe("---------------");
                for (ServiceImplementation impl : entry.getValue().getServiceImplementations()) {
                    for (Annotation annotation : impl.getType().getAnnotations()) {
                        logger.severe(AnnotationUtils.toString(annotation));
                    }
                    logger.severe(impl.getType().getName());
                    logger.severe("---------------");
                }
            }
        }
        if (!success) {
            throw new RuntimeException("Failed to find valid implementations of all services");
        }
    }

    public Map<Class<?>, Class<?>> getBindings() {
        System.out.println(services);
        Map<Class<?>, Class<?>> bindings = Maps.newHashMap();
        for (Map.Entry<Class<?>, ServiceImplementations> entry : services.entrySet()) {
            bindings.put(entry.getKey(), entry.getValue().getValidImplementations().iterator().next().getType());
        }
        return bindings;
    }

    private final static class ServiceImplementations {
        private final Set<ServiceImplementation> serviceImplementations = Sets.newHashSet();
        private final Set<ServiceImplementation> validImplementations = Sets.newHashSet();

        public Set<ServiceImplementation> getServiceImplementations() {
            return serviceImplementations;
        }

        public Set<ServiceImplementation> getValidImplementations() {
            return validImplementations;
        }
    }

    private final static class ServiceImplementation {
        private final Class<?> type;
        private final Class<?> service;

        private ServiceImplementation (Class<?> service, Class<?> type) {
            this.type = type;
            this.service = service;
        }

        public Class<?> getService() {
            return service;
        }

        public Class<?> getType() {
            return type;
        }
    }
}

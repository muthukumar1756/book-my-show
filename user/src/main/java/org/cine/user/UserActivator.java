package org.cine.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  User Module Configuration - replaces OSGi Bundle Activator.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "org.cine.user")
public class UserActivator {
}


package org.cine.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  Exception Module Configuration - replaces OSGi Bundle Activator.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "org.cine.exception")
public class ExceptionActivator {
}

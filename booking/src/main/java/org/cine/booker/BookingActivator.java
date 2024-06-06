package org.cine.booker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * Customizes the starting and stopping of a bundle.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class BookingActivator implements BundleActivator {

    private static final Logger LOGGER = LogManager.getLogger(BookingActivator.class);

    /**
     * <p>
     * Invoked when the bundle is started.
     * </p>
     *
     * @param context The execution context of the bundle being started.
     */
    public void start(final BundleContext context) {
        LOGGER.info("Starting the booking bundle");
    }

    /**
     * <p>
     * Invoked when the bundle is stopped.
     * </p>
     *
     * @param context The execution context of the bundle being stopped.
     */
    public void stop(final BundleContext context) {
        LOGGER.info("Stopping the booking bundle");
    }
}
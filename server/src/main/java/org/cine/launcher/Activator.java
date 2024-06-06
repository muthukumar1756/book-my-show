package org.cine.launcher;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.cine.booker.controller.MovieTicketBookingController;
import org.cine.booker.controller.MovieController;
import org.cine.booker.controller.MovieScheduleController;
import org.cine.common.json.JsonFactory;
import org.cine.user.controller.UserController;

/**
 * <p>
 *  Customizes the starting and stopping of a bundle and creates the JAX-RS server instance.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Activator implements BundleActivator {

    private static final Logger LOGGER = LogManager.getLogger(Activator.class);
    private Server server;

    /**
     * <p>
     * Invoked when the bundle is started and creates the server instance.
     * </p>
     *
     * @param context The execution context of the bundle being started.
     */
    @Override
    public void start(final BundleContext context) {
        final JAXRSServerFactoryBean bean = new JAXRSServerFactoryBean();

        bean.setAddress("/ticketnew");
        bean.setProvider(JsonFactory.getInstance().getJsonProvider());
        bean.setServiceBeans(getServiceBeans());
        server = bean.create();

        LOGGER.info("Server Bundle Is Started");
    }

    /**
     * <p>
     * Gets all the service beans.
     * </p>
     *
     * @return The List of instances
     */
    private List<Object> getServiceBeans() {
        final List<Object> beans = new ArrayList<>();

        beans.add(UserController.getInstance());
        beans.add(MovieTicketBookingController.getInstance());
        beans.add(MovieController.getInstance());
        beans.add(MovieScheduleController.getInstance());

        return beans;
    }

    /**
     * <p>
     * Invoked when the bundle is stopped and destroys the sever instance.
     * </p>
     *
     * @param context The execution context of the bundle being stopped.
     */
    @Override
    public void stop(final BundleContext context) {
        if (null != server) {
            server.destroy();
        }
        LOGGER.info("Server Bundle Is Stopped");
    }
}

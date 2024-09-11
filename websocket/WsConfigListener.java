package websocket.echo;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfigurationBuilder;

import org.apache.tomcat.websocket.server.ServerContainerImpl;
import org.apache.tomcat.websocket.server.WsListener;

@WebListener
public class WsConfigListener extends WsListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServerContainerImpl sc = ServerContainerImpl.getServerContainer();
        try {
            sc.deploy(ServerEndpointConfigurationBuilder.create(
                    EchoEndpoint.class, "/websocket/echoProgrammatic").build());
        } catch (DeploymentException e) {
            throw new IllegalStateException(e);
        }
    }
}

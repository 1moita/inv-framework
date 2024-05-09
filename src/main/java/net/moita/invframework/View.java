package net.moita.invframework;

import net.moita.invframework.configurator.Configurator;
import net.moita.invframework.renderer.Renderer;
import net.moita.invframework.renderer.icon.Action;

import static net.moita.invframework.ViewController.unregisterViewer;

public interface View {

    void configureView(Configurator configurator);

    void renderView(Renderer renderer);

    default void handleAction(Action action) {}
    default void handleAction(Action action, Renderer renderer) {}

    default void handleClose(Viewer viewer) {
        unregisterViewer(viewer.getUniqueId());
    }

    default void handleOpen(Viewer viewer) {}

}

package net.moita.invframework.configurator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.moita.invframework.CustomView;

@RequiredArgsConstructor @Getter
public final class Configurator {

    private final CustomView customView;

    private boolean renderInAction = false;

    public Configurator withTitle(String title) {
        this.customView.setTitle(title);
        return this;
    }

    public Configurator withRows(int rows) {
        this.customView.setSize(rows * 9);
        return this;
    }

    public Configurator isRenderInAction(boolean value) {
        this.renderInAction = value;
        return this;
    }

    public static Configurator withCustomView(CustomView customView) {
        return new Configurator(customView);
    }

}
```java
public final class MyView implements View {
    
    public void configureView(Configurator configurator) {
        configurator
                .withTitle("Meu Inventário")
                .withRows(1)
                .isRenderInAction(true);
    }
    
    public void renderView(Renderer renderer) {
        renderer.withIcon(5, Icon
                        .withType(Material.GLASS)
                        .setAmount(getAmount(renderer))
                        .onLeftClick((action) -> renderer.getProperty("value").ifPresent((amount) ->
                                ((int) amount) + 1))
                        .onRightClick((action) -> renderer.getProperty("value").ifPresent((amount) ->
                                ((int) amount) + 1)));
    }
    
    public int getAmount(Renderer renderer) {
        return (int) renderer.getProperty("value").orElse(1);
    }
    
}
```
package portablejim.veinminermodintegration;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 12/07/14
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class VeinminerModIntegrationGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) { }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return VeinminerModIntegrationConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

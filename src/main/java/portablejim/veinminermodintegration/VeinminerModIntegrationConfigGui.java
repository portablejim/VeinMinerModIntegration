package portablejim.veinminermodintegration;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class VeinminerModIntegrationConfigGui extends GuiConfig {
    @SuppressWarnings("unchecked")
    public VeinminerModIntegrationConfigGui(GuiScreen parent) {
        super(parent,
                new ConfigElement(ModIntegration.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
        "veinminermodintegration", true, true, GuiConfig.getAbridgedConfigPath(ModIntegration.configFile.toString()));
    }
}

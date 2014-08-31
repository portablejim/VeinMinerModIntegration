package portablejim.veinminermodintegration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import portablejim.veinminer.api.Permission;
import portablejim.veinminer.api.VeinminerInitalToolCheck;

import java.util.Map;

@Mod(modid = ModIntegration.MODID, version = ModIntegration.VERSION, guiFactory = "portablejim.veinminermodintegration.VeinminerModIntegrationGuiFactory")
public class ModIntegration
{
    public static final String MODID = "veinminermodintegration";
    public static final String VERSION = "1.7.10-0.1";

    public static Configuration configFile;

    public static int enchantmentId = 180;

    public VeinMinerEnchant veinMinerEnchant;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();

            veinMinerEnchant = new VeinMinerEnchant(enchantmentId, 3);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void syncConfig() {
        enchantmentId = configFile.getInt("Enchantment ID", Configuration.CATEGORY_GENERAL, enchantmentId, 0, 255, "Veinminer Enchantment ID");

        if(configFile.hasChanged()) {
            configFile.save();
        }
    }

    @SubscribeEvent
    public void checkEnchantment(VeinminerInitalToolCheck event) {
        boolean allow = false;
        ItemStack item = event.player.getHeldItem();

        if(item == null) return;

        //noinspection unchecked
        Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        for(Integer enchantment : enchantments.keySet()) {
            if(enchantment == enchantmentId) {
                allow = true;
            }
        }

        if(event.allowVeinminerStart != Permission.FORCE_ALLOW && event.allowVeinminerStart != Permission.FORCE_DENY) {
            if(allow) {
                event.allowVeinminerStart = Permission.ALLOW;
            }
            else {
                event.allowVeinminerStart = Permission.DENY;
            }
        }
    }
}

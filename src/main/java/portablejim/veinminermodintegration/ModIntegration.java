package portablejim.veinminermodintegration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import portablejim.veinminer.api.Permission;
import portablejim.veinminer.api.VeinminerInitalToolCheck;
import portablejim.veinminermodintegration.optionals.TinkersConstruct;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod(modid = ModIntegration.MODID, version = ModIntegration.VERSION, guiFactory = "portablejim.veinminermodintegration.VeinminerModIntegrationGuiFactory")
public class ModIntegration
{
    public static final String MODID = "veinminermodintegration";
    public static final String VERSION = "1.7.10-0.2.0";

    public static Configuration configFile;

    public static int enchantmentId = 180;
    public static Set<String> blacklist = new HashSet<String>();
    public static boolean enforceEnchantment = true;
    public static boolean enforceTiconModifier = true;

    public VeinMinerEnchant veinMinerEnchant;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();

            veinMinerEnchant = new VeinMinerEnchant(enchantmentId, 3);
    }
    
    @SuppressWarnings("UnusedParameters")
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("UnusedParameters")
    @EventHandler
    public void postInit(FMLPostInitializationEvent unused) {
        if(Loader.isModLoaded("TConstruct")) {
            TinkersConstruct.LoadTinkers();
        }
    }

    public static void syncConfig() {
        enchantmentId = configFile.getInt("Enchantment ID", Configuration.CATEGORY_GENERAL, enchantmentId, 0, 255, "Veinminer Enchantment ID");
        String blacklistString = configFile.getString("Enchantment blacklist", Configuration.CATEGORY_GENERAL, "", "Items to blacklist from the Veinminer enchant.\nSplit with ','.");
        enforceEnchantment = configFile.getBoolean("Enchantment", Configuration.CATEGORY_GENERAL, true, "Require the 'VeinMiner' enchantment to veinmine with enchantable items.");
        enforceTiconModifier = configFile.getBoolean("Tinkers Construct modifier", Configuration.CATEGORY_GENERAL, true, "Require the 'VeinMiner' modifier to veinmine with Tinkers construct tools");
        blacklist = new HashSet<String>(Arrays.asList(blacklistString.split(",")));

        if(configFile.hasChanged()) {
            configFile.save();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(MODID)) {
            syncConfig();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
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
        if(!enforceEnchantment && item.isItemEnchantable()) {
            allow = true;
        }

        if(Loader.isModLoaded("TConstruct")) {

            if(item.hasTagCompound()) {
                NBTTagCompound tags = item.getTagCompound().getCompoundTag("InfiTool");
                if (tags.hasKey("VeinMiner")) {
                    allow = true;
                    int limit = tags.getInteger("VeinMiner");
                    limit *= 8;
                    event.blockLimit = limit;
                }
                else if(!tags.hasNoTags() && !enforceTiconModifier) {
                    allow = true;
                }
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

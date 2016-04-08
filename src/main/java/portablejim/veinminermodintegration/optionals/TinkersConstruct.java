package portablejim.veinminermodintegration.optionals;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.crafting.ModifyBuilder;

/**
 * Created by james on 8/04/16.
 */
public class TinkersConstruct {
    public static void LoadTinkers(){
        ItemStack item = new ItemStack(Items.glowstone_dust);
        VeinMinerModifer veinminerMod = new VeinMinerModifer(new ItemStack[]{item}, 7, "VeinMiner");
        ModifyBuilder.registerModifier(veinminerMod);
    }
}

package portablejim.veinminermodintegration;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The actual enchantment.
 */
public class VeinMinerEnchant extends Enchantment {
    protected VeinMinerEnchant(int enchantId, int weight) {
        super(enchantId, weight, EnumEnchantmentType.digger);
        this.setName("veinminer");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int p_77321_1_)
    {
        return 15;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int p_77317_1_)
    {
        return this.getMinEnchantability(p_77317_1_) + 50;
    }

    public boolean canApply(ItemStack itemStack)
    {
        if(itemStack != null && itemStack.getItem() != null &&
                Item.itemRegistry.getNameForObject(itemStack.getItem()) != null) {
            GameRegistry.UniqueIdentifier itemUid = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
            return !ModIntegration.blacklist.contains(itemUid.toString()) && super.canApply(itemStack);
        }
        return false;
    }
}

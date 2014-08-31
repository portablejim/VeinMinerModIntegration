package portablejim.veinminermodintegration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 12/07/14
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
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
}

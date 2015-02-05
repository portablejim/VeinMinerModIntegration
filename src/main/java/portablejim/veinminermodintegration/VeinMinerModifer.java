package portablejim.veinminermodintegration;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.modifier.ItemModifier;

/**
 * Code to handle the VeinMiner modifier.
 */
public class VeinMinerModifer extends ItemModifier {
    /**
     * Default constructor
     *
     * @param recipe  Items to compare against when checking the modifier
     * @param effect  Render index for sprite layering
     * @param dataKey NBT string to put on the item
     */
    public VeinMinerModifer(ItemStack[] recipe, int effect, String dataKey) {
        super(recipe, effect, dataKey);
    }

    @Override
    public void modify(ItemStack[] recipe, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int finalRange;
        int preUpgrade = 0;
        int postUpgrade;
        if (tags.hasKey(key))
        {
            int amount = tags.getInteger(key);
            preUpgrade = getModifers(amount);
            amount += 1;
            finalRange = amount;
            postUpgrade = getModifers(amount);
            tags.setInteger(key, amount);
        }
        else
        {
            tags.setInteger(key, 1);
            finalRange = 1;
            postUpgrade = getModifers(1);
        }

        int modifiers = tags.getInteger("Modifiers");
        modifiers -= Math.max(0, postUpgrade - preUpgrade);
        tags.setInteger("Modifiers", modifiers);

        String newTooltip = String.format("VeinMiner [Max: %d]", finalRange * 8);
        updateToolTip(tool, newTooltip);
    }

    private int getModifers(int value) {
        if(value ==  0) return 0;
        if(value <=  8) return 1;
        if(value <= 24) return 2;
        if(value <= 64) return 2;
        else return 3;
    }

    private int updateToolTip(ItemStack tool, String value) {
        String toolTipName = "VeinMiner";
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag(getTagName(tool));
        int tipNum = 0;
        while (true)
        {
            tipNum++;
            String tip = "Tooltip" + tipNum;
            if (!tags.hasKey(tip))
            {
                tags.setString(tip, toolTipName);
                String modTip = "ModifierTip" + tipNum;
                tags.setString(modTip, value);
                return tipNum;
            }
            else
            {
                String tag = tags.getString(tip);
                if (tag.contains(toolTipName))
                {
                    tags.setString(tip, toolTipName);
                    String modTip = "ModifierTip" + tipNum;
                    //tag = tags.getString(modTip);
                    tags.setString(modTip, value);
                    return tipNum;
                }
            }
        }
    }

    protected String getProperName (String tooltip, String tag) {
        return tooltip + " " + tag;
    }
}

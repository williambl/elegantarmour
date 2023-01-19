package xyz.amymialee.elegantarmour.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.amymialee.elegantarmour.config.ElegantPart;
import xyz.amymialee.elegantarmour.util.IEleganttable;

import java.util.EnumSet;
import java.util.Set;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IEleganttable {
    @Unique
    public final Set<ElegantPart> enabledElegantParts = EnumSet.noneOf(ElegantPart.class);

    @Override
    public boolean isElegantPartEnabled(ElegantPart part) {
        return this.enabledElegantParts.contains(part);
    }

    @Override
    public boolean isElegantPartEnabled(EquipmentSlot armorSlot) {
        return switch (armorSlot) {
            case FEET -> this.isElegantPartEnabled(ElegantPart.HIDE_BOOTS);
            case LEGS -> this.isElegantPartEnabled(ElegantPart.HIDE_LEGGINGS);
            case CHEST -> this.isElegantPartEnabled(ElegantPart.HIDE_CHESTPLATE);
            case HEAD -> this.isElegantPartEnabled(ElegantPart.HIDE_HELMET);
            case MAINHAND, OFFHAND -> true;
        };
    }

    @Override
    public void setElegantPart(ElegantPart part, boolean enabled) {
        if (enabled) {
            this.enabledElegantParts.add(part);
        } else {
            this.enabledElegantParts.remove(part);
        }
    }

    @Override
    public Set<ElegantPart> getEnabledParts() {
        return this.enabledElegantParts;
    }
}
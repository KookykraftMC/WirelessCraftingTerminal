package net.p455w0rd.wirelesscraftingterminal.common.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.p455w0rd.wirelesscraftingterminal.common.container.WCTBaseContainer;
import net.p455w0rd.wirelesscraftingterminal.common.container.ContainerWirelessCraftingTerminal;
import appeng.tile.inventory.AppEngInternalInventory;

public class AppEngSlot extends Slot {

	private final int defX;
	private final int defY;
	private boolean isDraggable = true;
	private boolean isPlayerSide = false;
	private Container myContainer = null;
	private int IIcon = -1;
	private hasCalculatedValidness isValid;
	private boolean isDisplay = false;

	public AppEngSlot(final IInventory inv, final int idx, final int x, final int y) {
		super(inv, idx, x, y);
		this.defX = x;
		this.defY = y;
		this.setIsValid(hasCalculatedValidness.NotAvailable);
	}

	public Slot setNotDraggable() {
		this.setDraggable(false);
		return this;
	}

	public Slot setPlayerSide() {
		this.isPlayerSide = true;
		return this;
	}

	public String getTooltip() {
		return null;
	}

	public void clearStack() {
		super.putStack(null);
	}

	@Override
	public boolean isItemValid(final ItemStack par1ItemStack) {
		if (this.isEnabled()) {
			return super.isItemValid(par1ItemStack);
		}
		return false;
	}

	@Override
	public ItemStack getStack() {
		if (!this.isEnabled()) {
			return null;
		}

		if (this.inventory.getSizeInventory() <= this.getSlotIndex()) {
			return null;
		}

		if (this.isDisplay()) {
			this.setDisplay(false);
			return this.getDisplayStack();
		}
		return super.getStack();
	}

	@Override
	public void putStack(final ItemStack par1ItemStack) {
		if (this.isEnabled()) {
			super.putStack(par1ItemStack);

			if (this.getContainer() != null) {
				if (this.getContainer() instanceof ContainerWirelessCraftingTerminal) {
					((ContainerWirelessCraftingTerminal) this.getContainer()).onSlotChange(this);
				}
				if (this.getContainer() instanceof WCTBaseContainer) {
					((WCTBaseContainer) this.getContainer()).onSlotChange(this);
				}
			}
		}
	}

	@Override
	public void onSlotChanged() {
		if (this.inventory instanceof AppEngInternalInventory) {
			((AppEngInternalInventory) this.inventory).markDirty(this.getSlotIndex());
		}
		else {
			super.onSlotChanged();
		}

		this.setIsValid(hasCalculatedValidness.NotAvailable);
	}

	@Override
	public boolean canTakeStack(final EntityPlayer par1EntityPlayer) {
		if (this.isEnabled()) {
			return super.canTakeStack(par1EntityPlayer);
		}
		return false;
	}

	@Override
	public boolean func_111238_b() {
		return this.isEnabled();
	}

	public ItemStack getDisplayStack() {
		return super.getStack();
	}

	public boolean isEnabled() {
		return true;
	}

	public float getOpacityOfIcon() {
		return 0.4f;
	}

	public boolean renderIconWithItem() {
		return false;
	}

	public int getIcon() {
		return this.getIIcon();
	}

	public boolean isPlayerSide() {
		return this.isPlayerSide;
	}

	public boolean shouldDisplay() {
		return this.isEnabled();
	}

	public int getX() {
		return this.defX;
	}

	public int getY() {
		return this.defY;
	}

	private int getIIcon() {
		return this.IIcon;
	}

	public void setIIcon(final int iIcon) {
		this.IIcon = iIcon;
	}

	private boolean isDisplay() {
		return this.isDisplay;
	}

	public void setDisplay(final boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public boolean isDraggable() {
		return this.isDraggable;
	}

	private void setDraggable(final boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	void setPlayerSide(final boolean isPlayerSide) {
		this.isPlayerSide = isPlayerSide;
	}

	public hasCalculatedValidness getIsValid() {
		return this.isValid;
	}

	public void setIsValid(final hasCalculatedValidness isValid) {
		this.isValid = isValid;
	}

	Container getContainer() {
		if (this.myContainer instanceof ContainerWirelessCraftingTerminal) {
			return (ContainerWirelessCraftingTerminal) this.myContainer;
		}
		if (this.myContainer instanceof WCTBaseContainer) {
			return (WCTBaseContainer) this.myContainer;
		}
		return this.myContainer;
	}

	public void setContainer(final Container myContainer) {
		if (this.myContainer instanceof ContainerWirelessCraftingTerminal) {
			this.myContainer = (ContainerWirelessCraftingTerminal) myContainer;
		}
		else if (this.myContainer instanceof WCTBaseContainer) {
			this.myContainer = (WCTBaseContainer) myContainer;
		}
		else {
			this.myContainer = myContainer;
		}
	}

	public enum hasCalculatedValidness {
		NotAvailable, Valid, Invalid
	}
}
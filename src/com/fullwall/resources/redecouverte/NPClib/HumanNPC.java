package com.fullwall.resources.redecouverte.NPClib;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.fullwall.Citizens.NPCTypes.Bandits.BanditNPC;
import com.fullwall.Citizens.NPCTypes.Blacksmiths.BlacksmithNPC;
import com.fullwall.Citizens.NPCTypes.Evil.EvilNPC;
import com.fullwall.Citizens.NPCTypes.Guards.GuardNPC;
import com.fullwall.Citizens.NPCTypes.Healers.HealerNPC;
import com.fullwall.Citizens.NPCTypes.Questers.QuesterNPC;
import com.fullwall.Citizens.NPCTypes.Traders.TraderNPC;
import com.fullwall.Citizens.NPCTypes.Wizards.WizardNPC;
import com.fullwall.Citizens.NPCs.NPCData;
import com.fullwall.resources.redecouverte.NPClib.NPCAnimator.Action;

public class HumanNPC extends NPC {

	private CraftNPC mcEntity;

	private double balance;
	private boolean isTrader = false;
	private boolean isHealer = false;
	private boolean isWizard = false;
	private boolean isBlacksmith = false;
	private boolean isQuester = false;
	private boolean isBandit = false;
	private boolean isGuard = false;
	private boolean isEvil = true;

	private final TraderNPC traderNPC = new TraderNPC(this);
	private final HealerNPC healerNPC = new HealerNPC(this);
	private final WizardNPC wizardNPC = new WizardNPC(this);
	private final BlacksmithNPC blacksmithNPC = new BlacksmithNPC(this);
	private final QuesterNPC questerNPC = new QuesterNPC(this);
	private final BanditNPC banditNPC = new BanditNPC(this);
	private final GuardNPC guardNPC = new GuardNPC(this);
	private final EvilNPC evilNPC = new EvilNPC(this);
	private NPCData npcdata;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger("Minecraft");

	public HumanNPC(CraftNPC entity, int UID, String name) {
		super(UID, name);
		this.mcEntity = entity;
		this.mcEntity.npc = this;
	}

	public Player getPlayer() {
		return (Player) this.mcEntity.getBukkitEntity();
	}

	public CraftNPC getHandle() {
		return this.mcEntity;
	}

	public void setHandle(CraftNPC handle) {
		this.mcEntity = handle;
	}

	public TraderNPC getTrader() {
		return this.traderNPC;
	}

	public HealerNPC getHealer() {
		return this.healerNPC;
	}

	public WizardNPC getWizard() {
		return this.wizardNPC;
	}

	public BlacksmithNPC getBlacksmith() {
		return this.blacksmithNPC;
	}

	public QuesterNPC getQuester() {
		return this.questerNPC;
	}

	public BanditNPC getBandit() {
		return this.banditNPC;
	}

	public GuardNPC getGuard() {
		return this.guardNPC;
	}

	public EvilNPC getEvil() {
		return this.evilNPC;
	}

	protected CraftNPC getMCEntity() {
		return this.mcEntity;
	}

	public void setTrader(boolean enable) {
		this.isTrader = enable;
	}

	public boolean isTrader() {
		return this.isTrader;
	}

	public void setHealer(boolean enable) {
		this.isHealer = enable;
	}

	public boolean isHealer() {
		return this.isHealer;
	}

	public void setWizard(boolean enable) {
		this.isWizard = enable;
	}

	public boolean isWizard() {
		return this.isWizard;
	}

	public void setBlacksmith(boolean enable) {
		this.isBlacksmith = enable;
	}

	public boolean isBlacksmith() {
		return this.isBlacksmith;
	}

	public void setQuester(boolean enable) {
		this.isQuester = enable;
	}

	public boolean isQuester() {
		return this.isQuester;
	}

	public void setBandit(boolean enable) {
		this.isBandit = enable;
	}

	public boolean isBandit() {
		return this.isBandit;
	}

	public void setGuard(boolean enable) {
		this.isGuard = enable;
	}

	public boolean isGuard() {
		return this.isGuard;
	}

	public void setEvil(boolean enable) {
		this.isEvil = enable;
	}

	public boolean isEvil() {
		return this.isEvil;
	}

	public void teleport(double x, double y, double z, float yaw, float pitch) {
		this.mcEntity.setLocation(x, y, z, yaw, pitch);
	}

	public void teleport(Location loc) {
		this.mcEntity.setLocation(loc.getX(), loc.getY(), loc.getZ(),
				loc.getYaw(), loc.getPitch());
	}

	public void updateMovement() {
		this.mcEntity.updateMove();
		this.mcEntity.applyGravity();
	}

	public void performAction(Action action) {
		this.mcEntity.performAction(action);
	}

	public String getOwner() {
		return this.npcdata.getOwner();
	}

	public void setNPCData(NPCData npcdata) {
		this.npcdata = npcdata;
	}

	public Location getLocation() {
		return getPlayer().getLocation();
	}

	public int getHealth() {
		return getPlayer().getHealth();
	}

	public void setHealth(int health) {
		getPlayer().setHealth(health);
	}

	public NPCData getNPCData() {
		return npcdata;
	}

	public PlayerInventory getInventory() {
		return getPlayer().getInventory();
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean createPath(Location loc, int pathTicks, int stationaryTicks,
			float range) {
		return this.mcEntity.startPath(loc, pathTicks, stationaryTicks, range);
	}

	public void target(LivingEntity entity, boolean aggro, int pathTicks,
			int stationaryTicks, float range) {
		this.mcEntity.setTarget(entity, aggro, pathTicks, stationaryTicks,
				range);
	}

	public void setAttackTimes(int times) {
		this.mcEntity.setAttackTimes(times);
	}
}
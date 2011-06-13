package com.fullwall.Citizens.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.PluginManager;

import com.fullwall.Citizens.Citizens;
import com.fullwall.Citizens.CreatureTask;
import com.fullwall.Citizens.Events.CitizensBasicNPCEvent;
import com.fullwall.Citizens.Interfaces.Listener;
import com.fullwall.Citizens.NPCTypes.Questers.Quests.QuestManager;
import com.fullwall.Citizens.NPCs.NPCManager;
import com.fullwall.Citizens.Utils.MessageUtils;
import com.fullwall.Citizens.Utils.StringUtils;
import com.fullwall.resources.redecouverte.NPClib.HumanNPC;
import com.fullwall.resources.redecouverte.NPClib.NPCEntityTargetEvent;
import com.fullwall.resources.redecouverte.NPClib.NPCEntityTargetEvent.NpcTargetReason;

/**
 * Entity Listener
 */
public class EntityListen extends EntityListener implements Listener {
	private final Citizens plugin;
	private PluginManager pm;

	public EntityListen(final Citizens plugin) {
		this.plugin = plugin;
	}

	@Override
	public void registerEvents() {
		pm = plugin.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Normal,
				plugin);
		pm.registerEvent(Event.Type.ENTITY_TARGET, this, Event.Priority.Normal,
				plugin);
		pm.registerEvent(Event.Type.ENTITY_DEATH, this, Event.Priority.Normal,
				plugin);
	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		CreatureTask.onDamage(event.getEntity(), event);

		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			HumanNPC npc = NPCManager.get(e.getEntity());
			if (npc != null) {
				if (e.getEntity() instanceof Player) {
					e.setCancelled(true);
				}
				if (e.getDamager() instanceof Player) {
					Player player = (Player) e.getDamager();
					if (npc.isHealer()) {
						npc.getHealer().onLeftClick(player, npc);
					}
					if (npc.isWizard()) {
						npc.getWizard().onLeftClick(player, npc);
					}
				}
			} else if (e.getDamager() instanceof Player) {
				if (((LivingEntity) e.getEntity()).getHealth() - e.getDamage() <= 0) {
					QuestManager.incrementQuest((Player) e.getDamager(),
							new EntityDeathEvent(e.getEntity(), null));

				}
				CreatureTask.onLeftClick(e);
			}
		}
	}

	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		if (!(event instanceof NPCEntityTargetEvent)) {
			return;
		}
		NPCEntityTargetEvent e = (NPCEntityTargetEvent) event;
		if (CreatureTask.getCreature(event.getEntity()) != null) {
			if (e.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED)
				CreatureTask.getCreature(event.getEntity()).onRightClick(
						(Player) event.getTarget());
		}
		if (NPCManager.isNPC(event.getTarget())) {
			event.setCancelled(true);
		}
		HumanNPC npc = NPCManager.get(e.getEntity());
		if (npc != null && event.getTarget() instanceof Player) {
			// The NPC lib handily provides a right click event.
			if (e.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED) {
				Player player = (Player) event.getTarget();
				if (plugin.validateTool("items.basic.select-items", player
						.getItemInHand().getTypeId(), player.isSneaking()) == true) {
					if (!NPCManager.validateSelected(player, npc.getUID())) {
						NPCManager.selectedNPCs.put(player.getName(),
								npc.getUID());
						player.sendMessage(ChatColor.GREEN
								+ "You selected NPC "
								+ StringUtils.wrap(npc.getStrippedName())
								+ ", ID " + StringUtils.wrap("" + npc.getUID())
								+ ".");
						return;
					}
				}
				// Dispatch text event / select NPC.
				if (plugin.validateTool("items.basic.talk-items", player
						.getItemInHand().getTypeId(), player.isSneaking()) == true) {
					CitizensBasicNPCEvent ev = new CitizensBasicNPCEvent(npc,
							(Player) e.getTarget(), MessageUtils.getText(npc,
									(Player) e.getTarget()));
					plugin.getServer().getPluginManager().callEvent(ev);
				}
				if (npc.isTrader()) {
					npc.getTrader().onRightClick(player, npc);
				}
				if (npc.isWizard()) {
					npc.getWizard().onRightClick(player, npc);
				}
				if (npc.isBlacksmith()) {
					npc.getBlacksmith().onRightClick(player, npc);
				}
				if (npc.isBandit()) {
					npc.getBandit().onRightClick(player, npc);
				}
				if (npc.isQuester()) {
					npc.getQuester().onRightClick(player, npc);
				}
			}
		}
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		CreatureTask.onEntityDeath(event.getEntity());
	}
}
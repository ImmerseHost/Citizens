package com.fullwall.Citizens.Questers.QuestTypes;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.fullwall.Citizens.Questers.Quest;
import com.fullwall.Citizens.Questers.QuestTypes.QuestManager.QuestType;
import com.fullwall.resources.redecouverte.NPClib.HumanNPC;

/**
 * Quest type involving killing monsters
 */
public class MonsterCombatQuest {
	private HumanNPC questGiver;
	private Player killer;
	private CreatureType monster;
	private ItemStack weapon;

	public MonsterCombatQuest(HumanNPC questGiver, Player killer,
			CreatureType monster, ItemStack weapon) {
		this.questGiver = questGiver;
		this.killer = killer;
		this.monster = monster;
		this.weapon = weapon;
		weapon = killer.getItemInHand();
	}

	/**
	 * 
	 * @return the monster killed by a player during a MonsterCombatQuest
	 */
	public CreatureType getMonster() {
		return monster;
	}

	/**
	 * 
	 * @param monster
	 *            the monster killed by a player during a MonsterCombatQuest
	 */
	public void setMonster(CreatureType monster) {
		this.monster = monster;
	}

	/**
	 * 
	 * @return the weapon used by a player to kill a monster during a
	 *         MonsterCombatQuest
	 */
	public ItemStack getWeapon() {
		return weapon;
	}

	/**
	 * 
	 * @param weapon
	 *            the weapon used by a player to kill a monster during a
	 *            MonsterCombatQuest
	 */
	public void setWeapon(ItemStack weapon) {
		killer.setItemInHand(weapon);
	}
}
package com.karkoon.dungeoncrawler;

import java.util.EnumMap;

/**
 * Created by Karkoon on 03.09.2016.
 * Gives access to a map of common game object attributes.
 */

public class Statistics {

    private EnumMap<AttributeType, Attribute> statMap;

    public Statistics(int maxHealth, int maxMana, int armor, int baseDamage, int strength, int dexternity, int wisdom) {
        statMap = new EnumMap<>(AttributeType.class);
        statMap.put(AttributeType.MAX_HEALTH, new Attribute("Max Health", maxHealth));
        statMap.put(AttributeType.MAX_MANA, new Attribute("Mana", maxMana));
        statMap.put(AttributeType.CURRENT_HEALTH, new Attribute("Current Health", maxHealth));
        statMap.put(AttributeType.CURRENT_MANA, new Attribute("Current Mana", maxMana));
        statMap.put(AttributeType.ARMOR, new Attribute("Armor", armor));
        statMap.put(AttributeType.BASE_DAMAGE, new Attribute("Damage", baseDamage));
        statMap.put(AttributeType.STRENGTH, new Attribute("Strength", strength));
        statMap.put(AttributeType.DEXTERITY, new Attribute("Dexterity", dexternity));
        statMap.put(AttributeType.WISDOM, new Attribute("Wisdom", wisdom));
    }

    public Attribute get(AttributeType attributeType) {
        return statMap.get(attributeType);
    }

    public void add(Statistics stats) {
        for (AttributeType type : AttributeType.values()) {
            statMap.get(type).setValue(statMap.get(type).value + stats.get(type).value);
        }
    }

    public void subtract(Statistics stats) {
        for (AttributeType type : AttributeType.values()) {
            statMap.get(type).setValue(statMap.get(type).value - stats.get(type).value);
        }
    }

    public enum AttributeType {

        MAX_HEALTH, MAX_MANA, CURRENT_HEALTH, CURRENT_MANA, ARMOR, BASE_DAMAGE, STRENGTH, DEXTERITY, WISDOM

    }

    public static class Attribute {

        private final String name;
        private int value;

        Attribute(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            if (value >= 0) this.value = value;
        }

        public String getName() {
            return name;
        }
    }

}

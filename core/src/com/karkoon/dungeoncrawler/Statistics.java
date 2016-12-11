package com.karkoon.dungeoncrawler;

import java.util.Collections;
import java.util.EnumMap;

/**
 * Created by Karkoon on 03.09.2016.
 * Gives access to a map of common attributes. Used by Character and Item types.
 */
public class Statistics {

    private EnumMap<AttributeType, Attribute> statMap = new EnumMap<>(AttributeType.class);

    public Statistics(int maxHealth, int mana, int armor, int baseDamage, int strength, int dexternity, int wisdom) {
        statMap.put(AttributeType.MAX_HEALTH, new Attribute("Health", maxHealth));
        statMap.put(AttributeType.MAX_MANA, new Attribute("Mana", mana));
        statMap.put(AttributeType.ARMOR, new Attribute("Armor", armor));
        statMap.put(AttributeType.BASE_DAMAGE, new Attribute("Damage", baseDamage));
        statMap.put(AttributeType.STRENGTH, new Attribute("Strength", strength));
        statMap.put(AttributeType.DEXTERNITY, new Attribute("Dexternity", dexternity));
        statMap.put(AttributeType.WISDOM, new Attribute("Wisdom", wisdom));
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

    public Attribute get(AttributeType attributeType) {
        return statMap.get(attributeType);
    }

    public enum AttributeType {

        MAX_HEALTH, MAX_MANA, ARMOR, BASE_DAMAGE, STRENGTH, DEXTERNITY, WISDOM

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

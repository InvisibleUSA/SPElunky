package com.siemens.spe.spelunky.game;

import com.siemens.spe.spelunky.system.Position;

/**
 * Created by 154374ln on 21.03.2017.
 */
public class Stats {
    public Position position;
    public double maxHealth, currentHealth;
    public double armor;
    public double damage;

    public void setDamage(double damage)
    {
        this.damage = damage;
    }

    public double getDamage()
    {
        return damage;
    }
}

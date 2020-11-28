package kaptainwutax.biomeutils;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

public class Main {
    public static void main(String[] args) {
        OverworldBiomeSource bs = new OverworldBiomeSource(MCVersion.v1_14_4, 4739724806777789925L);
        System.out.println(bs.full.sample(-2524,0,-128));
        System.out.println(bs.voronoi.sample(-2524,0,-128));
    }
}

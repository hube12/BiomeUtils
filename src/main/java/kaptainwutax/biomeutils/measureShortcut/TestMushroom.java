package kaptainwutax.biomeutils.measureShortcut;


import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.land.BaseBiomesLayer;
import kaptainwutax.biomeutils.layer.land.ContinentLayer;
import kaptainwutax.biomeutils.layer.land.MushroomLayer;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;


public class TestMushroom {
    public final static MCVersion VERSION = MCVersion.v1_16;
    public final static OverworldBiomeSource SOURCE = new OverworldBiomeSource(VERSION, 0L);
    public final static BiomeLayer CONTINENT = getLayer(ContinentLayer.class);
    public final static BiomeLayer COLD = getLayer(ClimateLayer.Cold.class);
    public final static BiomeLayer BASEBIOMES = getLayer(BaseBiomesLayer.class);
    public final static BiomeLayer SPECIAL = getLayer(ClimateLayer.Special.class);
    public final static BiomeLayer MUSHROOM = getLayer(MushroomLayer.class);

    public static BiomeLayer getLayer(Class<? extends BiomeLayer> layerClass) {
        for (int i = 0; i < SOURCE.getLayers().size(); i++) {
            if (SOURCE.getLayer(i).getClass().equals(layerClass)) return SOURCE.getLayer(i);
        }
        return SOURCE.voronoi;
    }

    public static long getLocalSeed(BiomeLayer biomeLayer, long seed, int posX, int posZ) {
        long layerSeed = BiomeLayer.getLayerSeed(seed, biomeLayer.salt);
        return BiomeLayer.getLocalSeed(layerSeed, posX, posZ);
    }

    public static void main(String[] args) {
        Biome biome = Biome.MUSHROOM_FIELDS;
        int count = 0;
        int count2 = 0;
        int[][] center = {{0, 0}};
        long counter=0;
        for (long seed = 0; seed < Long.MAX_VALUE; seed++) { // 2^64
            counter++;
            long localSeed;

            // force mushroom at 0, 0},{0, 1}, {0, -1},{-1, 0}, {1, 0}
            boolean bad = false;
            for (int[] pos : center) {
                // INCREASE MUSHROOM
                localSeed = getLocalSeed(MUSHROOM, seed, pos[0], pos[1]);
                if (Math.floorMod(localSeed >> 24, 100) != 0) {
                    bad = true;
                    break;
                }
            }
            if (bad) continue;

            count++;
            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_16, seed, 4, 4);

            if (source.getBiome(0, 0, 0) == biome) {
                System.out.println(seed);
                count2++;
            }
            if (counter > 150000) {
                System.out.println("total branch 1: desert, savannah, plains " + BaseBiomesLayer.count1 / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total branch 2: forest, daark forest, plains, mountains, birch forest, swamp " + BaseBiomesLayer.count2 / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total branch 3: forest, mountains, taiga, plains " + BaseBiomesLayer.count3 / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total branch 4: snowy tundra, snowy taiga " + BaseBiomesLayer.count4 / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total mushroom branch " + BaseBiomesLayer.countd / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total giant taiga " + BaseBiomesLayer.countgianttaiga / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total mesaa " + BaseBiomesLayer.countmesa / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total jungle " + BaseBiomesLayer.countjungle / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println("total ocean or mushroom " + BaseBiomesLayer.count / (float) BaseBiomesLayer.total * 100 + "%");
                System.out.println();
                System.out.println("###################CLIMATE#######################3");
                System.out.println();
                System.out.println("total Cold Forest " + ClimateLayer.countColdForest / (float) ClimateLayer.totalCold * 100 + "%");
                System.out.println("total Cold Mountains " + ClimateLayer.countColdMountains / (float) ClimateLayer.totalCold * 100 + "%");
                System.out.println("total Cold Plains " + ClimateLayer.countColdPlains / (float) ClimateLayer.totalCold * 100 + "%");
                System.out.println();
                System.out.println("total Temp Normal " + ClimateLayer.countTempNormal / (float) ClimateLayer.totalTemp * 100 + "%");
                System.out.println("total Temp Desert " + ClimateLayer.countTempDesert / (float) ClimateLayer.totalTemp * 100 + "%");
                System.out.println();
                System.out.println("total Cool Normal " + ClimateLayer.countCoolNormal / (float) ClimateLayer.totalCool * 100 + "%");
                System.out.println("total Cool Mountains " + ClimateLayer.countCoolMountains / (float) ClimateLayer.totalCool * 100 + "%");
                System.out.println();
                System.out.println("total Special Normal " + ClimateLayer.countSpecialNormal / (float) ClimateLayer.totalSpe * 100 + "%");
                System.out.println("total Special Spec " + ClimateLayer.countSpecialSpe / (float) ClimateLayer.totalSpe * 100 + "%");
                System.out.println();
                System.out.println("###################BIOME#######################3");
                System.out.println("Targeted " + biome.getName() + " over total: " + count2 / (float) count * 100 + "% compared to standard deviated probability:" + count2 / (float) seed * 100 + "%");

                break;
            }
        }
    }
}
package kaptainwutax.biomeutils.layer.temperature;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class ClimateLayer {
    public static int countColdForest = 0;
    public static int countColdMountains = 0;
    public static int countColdPlains = 0;
    public static int totalCold = 0;

    public static int countTempNormal = 0;
    public static int countTempDesert = 0;
    public static int totalTemp = 0;

    public static int countCoolNormal = 0;
    public static int countCoolMountains = 0;
    public static int totalCool = 0;


    public static int countSpecialNormal = 0;
    public static int countSpecialSpe = 0;
    public static int totalSpe = 0;


    public static class Cold extends BiomeLayer {
        public Cold(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
            super(version, worldSeed, salt, parent);
        }

        @Override
        public int sample(int x, int y, int z) {
            int value = this.getParent().get(x, y, z);
            totalCold++;
            if (Biome.isShallowOcean(value)) {
                return value;
            }

            this.setSeed(x, z);
            int i = this.nextInt(6);
            if (i == 0) {
                countColdForest++;
                return Biome.FOREST.getId();
            }
            if (i == 1) {
                countColdMountains++;
                return Biome.MOUNTAINS.getId();
            }//<=1
            // >1
            countColdPlains++;
            return Biome.PLAINS.getId();
        }
    }

    public static class Temperate extends CrossLayer {
        public Temperate(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
            super(version, worldSeed, salt, parent);
        }

        @Override
        public int sample(int n, int e, int s, int w, int center) {
            totalTemp++;
            // escape this one needs plains on center
            // and either mountains or forest on one of the side
            if (center == Biome.PLAINS.getId() && (n == Biome.MOUNTAINS.getId() || e == Biome.MOUNTAINS.getId()
                    || w == Biome.MOUNTAINS.getId() || s == Biome.MOUNTAINS.getId() || n == Biome.FOREST.getId()
                    || e == Biome.FOREST.getId() || w == Biome.FOREST.getId()
                    || s == Biome.FOREST.getId())) {
                countTempDesert++;
                return Biome.DESERT.getId();
            }
            countTempNormal++;
            return center;
        }
    }

    public static class Cool extends CrossLayer {
        public Cool(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
            super(version, worldSeed, salt, parent);
        }

        @Override
        public int sample(int n, int e, int s, int w, int center) {
            totalCool++;
            if (center != Biome.FOREST.getId() || n != Biome.PLAINS.getId() && e != Biome.PLAINS.getId()
                    && w != Biome.PLAINS.getId() && s != Biome.PLAINS.getId() && n != Biome.DESERT.getId()
                    && e != Biome.DESERT.getId() && w != Biome.DESERT.getId()
                    && s != Biome.DESERT.getId()) {
                countCoolNormal++;
                return center;
            }
            else {
                countCoolMountains++;
                return Biome.MOUNTAINS.getId();
            }
        }
    }

    public static class Special extends BiomeLayer {
        public Special(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
            super(version, worldSeed, salt, parent);
        }

        @Override
        public int sample(int x, int y, int z) {
            int i = this.getParent().get(x, y, z);
            totalSpe++;
            if (Biome.isShallowOcean(i)) {
                countSpecialNormal++;
                return i;
            }
            this.setSeed(x, z);

            if (this.nextInt(13) == 0) {
                i |= (1 + this.nextInt(15)) << 8;
                countSpecialSpe++;
            }
            else{
                countSpecialNormal++;
            }
            return i;
        }
    }

}

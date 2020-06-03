package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class BaseBiomesLayer extends BiomeLayer {
    public static int count1 = 0;
    public static int count = 0;
    public static int count2 = 0;
    public static int count3 = 0;
    public static int count4 = 0;
    public static int countd = 0;
    public static int countmesa = 0;
    public static int countjungle = 0;
    public static int countgianttaiga = 0;
    public static int total = 0;
    public static final Biome[] DRY_BIOMES = new Biome[]{Biome.DESERT,
            Biome.DESERT,
            Biome.DESERT,
            Biome.SAVANNA,
            Biome.SAVANNA,
            Biome.PLAINS
    };
    public static final Biome[] TEMPERATE_BIOMES = new Biome[]{Biome.FOREST,
            Biome.DARK_FOREST,
            Biome.MOUNTAINS,
            Biome.PLAINS,
            Biome.BIRCH_FOREST,
            Biome.SWAMP
    };
    public static final Biome[] COOL_BIOMES = new Biome[]{Biome.FOREST,
            Biome.MOUNTAINS,
            Biome.TAIGA,
            Biome.PLAINS
    };
    public static final Biome[] SNOWY_BIOMES = new Biome[]{Biome.SNOWY_TUNDRA,
            Biome.SNOWY_TUNDRA,
            Biome.SNOWY_TUNDRA,
            Biome.SNOWY_TAIGA
    };

    public BaseBiomesLayer(long worldSeed, long salt, BiomeLayer parent) {
        super(worldSeed, salt, parent);
    }

    public BaseBiomesLayer(long worldSeed, long salt) {
        this(worldSeed, salt, null);
    }

    @Override
    public int sample(int x, int z) {
        this.setSeed(x, z);
        int value = this.parent.get(x, z);
        int i = (value & 3840) >> 8;
        value &= -3841; //need to be
        //4 260 516 772 1028 1284 1540 1796 2052 2308 2564 2820 3076 3332 3588 3844
        total++;
        if (!BiomeSource.isOcean(value) && value != Biome.MUSHROOM_FIELDS.getId()) {
            switch (value) {
                case 1: //15%   -> 3% -> nextInt6==0
					count1++;
                    if (i > 0) { //1.11% -> 11% -> nextInt13==0
                    	countmesa++;
                        return this.nextInt(3) == 0 ? Biome.BADLANDS_PLATEAU.getId() : Biome.WOODED_BADLANDS_PLATEAU.getId();
                    }
					// 14% -> 4% -> nextInt13==0
                    return DRY_BIOMES[this.nextInt(DRY_BIOMES.length)].getId();
                case 2: //31%   -> 12% -> nextInt6==0
					count2++;
                    if (i > 0) { // 2.29% -> 23% -> nextInt13==0
                    	countjungle++;
                        return Biome.JUNGLE.getId();
                    }
					// 29% -> 8% -> nextInt13==0
                    return TEMPERATE_BIOMES[this.nextInt(TEMPERATE_BIOMES.length)].getId();
                case 3: //25%   -> 57% -> nextInt6==0
					count3++;
                    if (i > 0) { //1.75% -> 17% -> nextInt13==0       // 4% -> nextInt6==0
                        countgianttaiga++;
                    	return Biome.GIANT_TREE_TAIGA.getId();
                    }
                    // 23.25%-> 7% -> nextInt13==0
                    return COOL_BIOMES[this.nextInt(COOL_BIOMES.length)].getId();
                case 4: // 2%    -> 9% -> nextInt6==0
					count4++;
                    return SNOWY_BIOMES[this.nextInt(SNOWY_BIOMES.length)].getId();
                default: //0%
					countd++;
                    return Biome.MUSHROOM_FIELDS.getId();
            }
        }
        count++;
        //24%              -> 17% -> nextInt6==0
        return value;
    }
}

package kaptainwutax.biomeutils;


import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.land.BaseBiomesLayer;

public class Test {
    public static void main(String[] args) {
        int count = 0;
        int count2 = 0;
        Biome biome=Biome.ICE_SPIKES;
        for (long seed = 0; seed < Long.MAX_VALUE; seed++) {

            long layerSeed, localSeed;
           // layerSeed = BiomeLayer.getLayerSeed(seed, 2L);
           // localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
           // if ((int) Math.floorMod(localSeed >> 24, 6)  != 0) continue;
//
           // layerSeed = BiomeLayer.getLayerSeed(seed, 200L);
           // localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
           // if ((int) Math.floorMod(localSeed >> 24, 4)  ==3) continue;
//
           // layerSeed = BiomeLayer.getLayerSeed(seed, 100L);
           // localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
           // if (((int) Math.floorMod(localSeed >> 24, 299999) %29)!=1) continue;
//
            count++;
            BiomeSource source = new BiomeSource(seed, 4, 4).init();

            if (source.voronoi.sampleBiome(0, 0) == biome) {
                System.out.println(seed);
                count2++;
            }
            if (seed > 150000) {
                //System.out.println("total branch 1: desert, savannah, plains "+BaseBiomesLayer.count1/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total branch 2: forest, daark forest, plains, mountains, birch forest, swamp "+BaseBiomesLayer.count2/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total branch 3: forest, mountains, taiga, plains "+BaseBiomesLayer.count3/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total branch 4: snowy tundra, snowy taiga "+BaseBiomesLayer.count4/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total mushroom branch "+BaseBiomesLayer.countd/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total giant taiga "+BaseBiomesLayer.countgianttaiga/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total mesaa "+BaseBiomesLayer.countmesa/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total jungle "+BaseBiomesLayer.countjungle/(float)BaseBiomesLayer.total*100+"%");
                //System.out.println("total ocean or mushroom "+BaseBiomesLayer.count/(float)BaseBiomesLayer.total*100+"%");
                System.out.println("Targeted "+biome.getName()+" over total: "+count2/(float)count*100+"% compared to standard deviated probability:"+count2/(float)seed*100+"%");
                break;
            }
        }
    }
}

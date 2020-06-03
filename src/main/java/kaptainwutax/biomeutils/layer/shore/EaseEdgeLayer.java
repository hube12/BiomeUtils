package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class EaseEdgeLayer extends CrossLayer {

	public EaseEdgeLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public EaseEdgeLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		int[] is = new int[1];
		if (!this.replaceEdgeIfNeede(is, n, e, s, w, center, Biome.MOUNTAINS, Biome.MOUNTAIN_EDGE)
				&& !this.replaceEdge(is, n, e, s, w, center, Biome.WOODED_BADLANDS_PLATEAU, Biome.BADLANDS)
				&& !this.replaceEdge(is, n, e, s, w, center, Biome.BADLANDS_PLATEAU, Biome.BADLANDS)
				&& !this.replaceEdge(is, n, e, s, w, center, Biome.GIANT_TREE_TAIGA, Biome.TAIGA)) {

			if(center == Biome.DESERT.getId() && anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s)) {
				return Biome.WOODED_MOUNTAINS.getId();
			} else {
				if(center == Biome.SWAMP.getId()) {
					if(anyMatch(Biome.DESERT, n, e, w, s) || anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s) || anyMatch(Biome.SNOWY_TAIGA, n, e, w, s)) {
						return Biome.PLAINS.getId();
					}

					if(anyMatch(Biome.JUNGLE, n, e, w, s) || anyMatch(Biome.BAMBOO_JUNGLE, n, e, w, s)) {
						return Biome.JUNGLE_EDGE.getId();
					}
				}

				return center;
			}
		}

		return is[0];
	}

	public static boolean anyMatch(Biome biome, int... values) {
		for(int value: values) {
			if(value == biome.getId())return true;
		}

		return false;
	}

	private boolean replaceEdgeIfNeede(int[] is, int i, int j, int k, int l, int m, Biome n, Biome o) {
		if (!BiomeSource.areSimilar(m, n)) {
			return false;
		} else {
			if (this.canBeNeighbour(i, n) && this.canBeNeighbour(j, n) && this.canBeNeighbour(l, n) && this.canBeNeighbour(k, n)) {
				is[0] = m;
			} else {
				is[0] = o.getId();
			}

			return true;
		}
	}

	private boolean replaceEdge(int[] is, int i, int j, int k, int l, int m, Biome n, Biome o) {
		if(m != n.getId())return false;

		if(BiomeSource.areSimilar(i, n) && BiomeSource.areSimilar(j, n)
				&& BiomeSource.areSimilar(l, n) && BiomeSource.areSimilar(k, n)) {
			is[0] = m;
		} else {
			is[0] = o.getId();
		}

		return true;
	}

	private boolean canBeNeighbour(int id, Biome b2) {
		if (BiomeSource.areSimilar(id, b2))return true;

		Biome biome = Biome.REGISTRY.get(id);

		if(biome != null && b2 != null) {
			Biome.Temperature t = biome.getTemperatureGroup();
			Biome.Temperature t2 = b2.getTemperatureGroup();
			return t == t2 || t == Biome.Temperature.MEDIUM || t2 == Biome.Temperature.MEDIUM;
		}

		return false;
	}

}


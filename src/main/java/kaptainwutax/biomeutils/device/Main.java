package kaptainwutax.biomeutils.device;

import kaptainwutax.seedutils.mc.MCVersion;

public class Main {

    public static void main(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2);

        for(int x = 0; x < 2; x++) {
            for(int z = 0; z < 2; z++) {
                device.add(Restrictions.MUSHROOM, x, z); // layer 256

            }
        }
        //device.add(Restrictions.MUSHROOM, -2,-2); // layer 64
        device.findSeeds(System.out::println);
    }

}

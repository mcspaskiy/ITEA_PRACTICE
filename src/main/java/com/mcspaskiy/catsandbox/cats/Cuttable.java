package com.mcspaskiy.catsandbox.cats;

public interface Cuttable {
    double checkMeatGrinder();

    default String release() {
        return "Meow-meow-meow. Freedom!";
    }
}

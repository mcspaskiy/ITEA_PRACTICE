package com.mcspaskiy.catsandbox;

import com.mcspaskiy.catsandbox.cats.*;

public class CatFactory {
    public DefaultStreetCat getCat(Class catClass) {
        if (catClass == AglyCat.class) {
            return new AglyCat();
        }
        if (catClass == BlackCat.class) {
            return new BlackCat();
        }
        if (catClass == Cat.class) {
            return new Cat();
        }
        if (catClass == FatCat.class) {
            return new FatCat();
        }
        if (catClass == ThinCat.class) {
            return new ThinCat();
        }
        return null;
    }
}


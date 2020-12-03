package com.mcspaskiy.catsandbox;

import com.mcspaskiy.catsandbox.annotations.Blochable;
import com.mcspaskiy.catsandbox.annotations.CatYears;
import com.mcspaskiy.catsandbox.annotations.Color;
import com.mcspaskiy.catsandbox.cats.Cuttable;

import java.lang.annotation.Annotation;
import java.util.Random;

public class ForceMeatProducer {
    private double storedMeat;

    public boolean produceForceMeat(Cuttable cat) {
        Class<? extends Cuttable> clazz = cat.getClass();

        Annotation annotation = clazz.getAnnotation(Blochable.class);
        if (annotation != null) {
            System.out.println(clazz.getSimpleName() + ": " + cat.release());
            return false;
        }

        annotation = clazz.getAnnotation(CatYears.class);
        if (annotation != null) {
            int years = ((CatYears) annotation).value();
            if (years > 3) {
                System.out.println(clazz.getSimpleName() + ": " + cat.release());
                return false;
            }
        }

        annotation = clazz.getAnnotation(Color.class);
        if (annotation != null) {
            String color = ((Color) annotation).value();
            if ("BLACK".equals(color)) {
                Random random = new Random();
                int side = random.nextInt(2);
                if (side == 1) {
                    System.out.println(clazz.getSimpleName() + ": " + cat.release());
                    return false;
                }
            }
        }

        double checkResult = cat.checkMeatGrinder();
        storedMeat += checkResult;
        System.out.println(clazz.getSimpleName() + " was used");
        return true;
    }

    public double getStoredMeat() {
        return storedMeat;
    }
}

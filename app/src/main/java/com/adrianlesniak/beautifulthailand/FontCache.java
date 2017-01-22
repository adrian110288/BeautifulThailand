package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adrian on 22/01/2017.
 */

public class FontCache {

    private static Map<Integer, String> sFontsMap;
    private static Map<String, Typeface> sFontCache = new HashMap<>();

    static {
        sFontsMap = mapFonts();
    }

    public static Typeface getFont(Context context, int fontIndex) {

        String fontName = sFontsMap.get(fontIndex);
        Typeface typeface;

        if(sFontCache.containsKey(fontName)) {
            typeface = sFontCache.get(fontName);
        }
        else{
            typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            sFontCache.put(fontName, typeface);
        }

        return typeface;
    }

    private static Map<Integer, String> mapFonts() {

        Map<Integer, String> fontsMap = new HashMap<>();
        fontsMap.put(0, "Oswald-Bold.ttf");
        fontsMap.put(1, "Oswald-BoldItalic.ttf");
        fontsMap.put(2, "Oswald-Demi-BoldItalic.ttf");
        fontsMap.put(3, "Oswald-DemiBold.ttf");
        fontsMap.put(4, "Oswald-Extra-LightItalic.ttf");
        fontsMap.put(5, "Oswald-ExtraLight.ttf");
        fontsMap.put(6, "Oswald-Heavy.ttf");
        fontsMap.put(7, "Oswald-HeavyItalic.ttf");
        fontsMap.put(8, "Oswald-Light.ttf");
        fontsMap.put(9, "Oswald-LightItalic.ttf");
        fontsMap.put(10, "Oswald-Medium.ttf");
        fontsMap.put(11, "Oswald-MediumItalic.ttf");
        fontsMap.put(12, "Oswald-Regular.ttf");
        fontsMap.put(13, "Oswald-RegularItalic.ttf");
        fontsMap.put(14, "Oswald-Stencil.ttf");

        return fontsMap;
    }
}

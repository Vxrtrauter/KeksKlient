package net.kekse.util.font;

import lombok.Getter;
import lombok.Setter;

/*
@Author Bat
4/26/25
*/

public class FontHelper {
    @Setter
    @Getter
    private String font;

    public GlyphPageFontRenderer size15;
    public GlyphPageFontRenderer size20;
    public GlyphPageFontRenderer size30;
    public GlyphPageFontRenderer size40;

    public void init() {
        font = "Arial";
        size15 = GlyphPageFontRenderer.create(font, 15, true, true, true);
        size20 = GlyphPageFontRenderer.create(font, 20, true, true, true);
        size30 = GlyphPageFontRenderer.create(font, 30, true, true, true);
        size40 = GlyphPageFontRenderer.create(font, 40, true, true, true);
    }
}

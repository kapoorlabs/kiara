package com.kapoorlabs.kiara.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.SpellCheckTrie;
import com.kapoorlabs.kiara.util.SpellCheckUtil;

public class SpellCheckUtilTest {

    @Test
    public void SpellCheckUtilTest_1() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        assertEquals(null, SpellCheckUtil.getOneEditKeyword(null, spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_2() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        assertEquals(null, SpellCheckUtil.getOneEditKeyword("", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_3() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        assertEquals(null, SpellCheckUtil.getOneEditKeyword("test", spellCheckTrie));
    }

    //auto correct is done for strings at least 4 character long
    @Test
    public void SpellCheckUtilTest_4() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("a");
        assertEquals(null, SpellCheckUtil.getOneEditKeyword("t", spellCheckTrie));
    }

    //auto correct is done for strings at least 4 character long
    @Test
    public void SpellCheckUtilTest_5() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("a");
        assertEquals(null, SpellCheckUtil.getOneEditKeyword("a", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_6() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("test", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_7() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("tests", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_8() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("best", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_9() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        spellCheckTrie.insert("best");
        assertEquals("best", SpellCheckUtil.getOneEditKeyword("best", spellCheckTrie));
    }

    //When one option becomes ambiguous, higher order alphabet takes preference.
    @Test
    public void SpellCheckUtilTest_10() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        spellCheckTrie.insert("best");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("cest", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_11() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        spellCheckTrie.insert("bests");
        assertEquals(null, SpellCheckUtil.getOneEditKeyword("cests", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_12() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        spellCheckTrie.insert("tent");
        spellCheckTrie.insert("bests");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("temt", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_13() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("chest");
        assertEquals("chest", SpellCheckUtil.getOneEditKeyword("hest", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_14() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("test");
        assertEquals("test", SpellCheckUtil.getOneEditKeyword("btest", spellCheckTrie));
    }

    @Test
    public void SpellCheckUtilTest_15() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();
        spellCheckTrie.insert("light");
        spellCheckTrie.insert("bulb");
        spellCheckTrie.insert("switch");
        spellCheckTrie.insert("camera");
        spellCheckTrie.insert("lock");
        spellCheckTrie.insert("phillips");
        assertEquals("bulb", SpellCheckUtil.getOneEditKeyword("bilb", spellCheckTrie));
        assertEquals("camera", SpellCheckUtil.getOneEditKeyword("camra", spellCheckTrie));
        assertEquals("lock", SpellCheckUtil.getOneEditKeyword("loxk", spellCheckTrie));
        assertEquals("light", SpellCheckUtil.getOneEditKeyword("ligt", spellCheckTrie));
        assertEquals("lock", SpellCheckUtil.getOneEditKeyword("dock", spellCheckTrie));
        assertEquals("phillips", SpellCheckUtil.getOneEditKeyword("philips", spellCheckTrie));
        assertEquals("light", SpellCheckUtil.getOneEditKeyword("ligh", spellCheckTrie));
        assertEquals("light", SpellCheckUtil.getOneEditKeyword("lighta", spellCheckTrie));
    }
    
    @Test
    public void SpellCheckUtilTest_16() {
        SpellCheckTrie spellCheckTrie = new SpellCheckTrie();

        spellCheckTrie.insert("philips");
        assertEquals("philips", SpellCheckUtil.getOneEditKeyword("philipr", spellCheckTrie));

    }
}

package net.runelite.client.plugins.devtools;

import java.awt.*;

import net.runelite.api.Point;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class searchFields {
    public static final WidgetSearchMap.Key<Integer> ID = new WidgetSearchMap.Key<>("Id");
    public static final WidgetSearchMap.Key<Integer> TYPE = new WidgetSearchMap.Key<>("Type");
    public static final WidgetSearchMap.Key<Integer> CONTENTTYPE = new WidgetSearchMap.Key<>("ContentType");
    public static final WidgetSearchMap.Key<Integer> PARENTID = new WidgetSearchMap.Key<>("ParentId");
    public static final WidgetSearchMap.Key<Boolean> SELFHIDDEN = new WidgetSearchMap.Key<>("SelfHidden");
    public static final WidgetSearchMap.Key<Boolean> HIDDEN = new WidgetSearchMap.Key<>("Hidden");
    public static final WidgetSearchMap.Key<String> TEXT = new WidgetSearchMap.Key<>("Text");
    public static final WidgetSearchMap.Key<String> TEXTCOLOR = new WidgetSearchMap.Key<>("TextColor");
    public static final WidgetSearchMap.Key<String> NAME = new WidgetSearchMap.Key<>("Name");
    public static final WidgetSearchMap.Key<Integer> ITEMID = new WidgetSearchMap.Key<>("ItemId");
    public static final WidgetSearchMap.Key<Integer> ITEMQUANTITY = new WidgetSearchMap.Key<>("ItemQuantity");
    public static final WidgetSearchMap.Key<Integer> MODELID = new WidgetSearchMap.Key<>("ModelId");
    public static final WidgetSearchMap.Key<Integer> SPRITEID = new WidgetSearchMap.Key<>("SpriteId");
    public static final WidgetSearchMap.Key<Integer> WIDTH = new WidgetSearchMap.Key<>("Width");
    public static final WidgetSearchMap.Key<Integer> HEIGHT = new WidgetSearchMap.Key<>("Height");
    public static final WidgetSearchMap.Key<Integer> RELATIVEX = new WidgetSearchMap.Key<>("RelativeX");
    public static final WidgetSearchMap.Key<Integer> RELATIVEY = new WidgetSearchMap.Key<>("RelativeY");
    public static final WidgetSearchMap.Key<Point> CANVASLOCATION = new WidgetSearchMap.Key<>("CanvasLocation");
    public static final WidgetSearchMap.Key<Rectangle> BOUNDS = new WidgetSearchMap.Key<>("Bounds");
    public static final WidgetSearchMap.Key<Integer> SCROLLX = new WidgetSearchMap.Key<>("ScrollX");
    public static final WidgetSearchMap.Key<Integer> SCROLLY = new WidgetSearchMap.Key<>("ScrollY");
    public static final WidgetSearchMap.Key<Integer> ORIGINALX = new WidgetSearchMap.Key<>("OriginalX");
    public static final WidgetSearchMap.Key<Integer> ORIGINALY = new WidgetSearchMap.Key<>("OriginalY");
    public static final WidgetSearchMap.Key<Integer> PADDINGX = new WidgetSearchMap.Key<>("PaddingX");
    public static final WidgetSearchMap.Key<Integer> PADDINGY = new WidgetSearchMap.Key<>("PaddingY");
}
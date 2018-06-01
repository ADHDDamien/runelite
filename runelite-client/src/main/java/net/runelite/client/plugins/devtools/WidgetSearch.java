/*
 * Copyright (c) 2018, Damien <https://github.com/ADHDDamien>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.devtools;

import java.awt.Rectangle;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;

@Slf4j
public class WidgetSearch
{
	WidgetSearchMap searchTerms = new WidgetSearchMap();
	public void searchRequest(String search)
	{
		//reset hashmap for new searches
		searchTerms.clear();
		//convert to lowercase for easier handling
		search = search.toLowerCase();
		//separate : and spaces in search to get field names and values with regex.
		String segments[] = search.split("[:|]");
		for (int i = 0; i < segments.length; i++)
		{
			switch (segments[i])
			{
				case "id":
					searchTerms.put(ID, Integer.parseInt(segments[i + 1]));
					break;
				case "type":
					searchTerms.put(TYPE, Integer.parseInt(segments[i + 1]));
					break;
				case "contenttype":
					searchTerms.put(CONTENTTYPE, Integer.parseInt(segments[i + 1]));
					break;
				case "parentid":
					searchTerms.put(PARENTID, Integer.parseInt(segments[i + 1]));
					break;
				case "selfhidden":
					if (segments[i + 1].equals("true"))
					{
						searchTerms.put(SELFHIDDEN, true);
					}
					else
					{
						searchTerms.put(SELFHIDDEN, false);
					}
					break;
				case "hidden":
					if (segments[i + 1].equals("true"))
					{
						searchTerms.put(HIDDEN, true);
					}
					else
					{
						searchTerms.put(HIDDEN, false);
					}
					break;
				case "text":
					searchTerms.put(TEXT, segments[i + 1]);
					break;
				case "textcolor":
					searchTerms.put(TEXTCOLOR, segments[i + 1]);
					break;
				case "name":
					searchTerms.put(NAME, segments[i + 1]);
					break;
				case "itemid":
					searchTerms.put(ITEMID, Integer.parseInt(segments[i + 1]));
					break;
				case "itemquantity":
					searchTerms.put(ITEMQUANTITY, Integer.parseInt(segments[i + 1]));
					break;
				case "modelid":
					searchTerms.put(MODELID, Integer.parseInt(segments[i + 1]));
					break;
				case "spriteid":
					searchTerms.put(SPRITEID, Integer.parseInt(segments[i + 1]));
					break;
				case "width":
					searchTerms.put(WIDTH, Integer.parseInt(segments[i + 1]));
					break;
				case "height":
					searchTerms.put(HEIGHT, Integer.parseInt(segments[i + 1]));
					break;
				case "relativex":
					searchTerms.put(RELATIVEX, Integer.parseInt(segments[i + 1]));
					break;
				case "relativey":
					searchTerms.put(RELATIVEY, Integer.parseInt(segments[i + 1]));
					break;
				case "canvaslocation":
					String canvasSegments[] = segments[i + 1].split("[,]");
					searchTerms.put(CANVASLOCATION, new Point(Integer.parseInt(canvasSegments[0]), Integer.parseInt(canvasSegments[1])));
					break;
				case "bounds":
					String boundsSegments[] = segments[i + 1].split("[,]");
					searchTerms.put(BOUNDS, new Rectangle(Integer.parseInt(boundsSegments[0]), Integer.parseInt(boundsSegments[1]), Integer.parseInt(boundsSegments[2]), Integer.parseInt(boundsSegments[3])));
					break;
				case "scrollx":
					searchTerms.put(SCROLLX, Integer.parseInt(segments[i + 1]));
					break;
				case "scrolly":
					searchTerms.put(SCROLLY, Integer.parseInt(segments[i + 1]));
					break;
				case "originalx":
					searchTerms.put(ORIGINALX, Integer.parseInt(segments[i + 1]));
					break;
				case "originaly":
					searchTerms.put(ORIGINALY, Integer.parseInt(segments[i + 1]));
					break;
				case "paddingx":
					searchTerms.put(PADDINGX, Integer.parseInt(segments[i + 1]));
					break;
				case "paddingy":
					searchTerms.put(PADDINGY, Integer.parseInt(segments[i + 1]));
					break;
			}
		}
	}


	public boolean isMatch(Widget widget)
	{
		//For Multi-field searches, if a single search term doesn't also match a widget then false is input causing false to be returned.
		ArrayList<Boolean> multiSearchMatch =  new ArrayList<>();

		if (searchTerms.containsKey("Id"))
		{
			if (widget.getId() == searchTerms.get(ID))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Type"))
		{
			if (widget.getType() == searchTerms.get(TYPE))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ContentType"))
		{
			if (widget.getContentType() == searchTerms.get(CONTENTTYPE))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ParentId"))
		{
			if (widget.getParentId() == searchTerms.get(PARENTID))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("SelfHidden"))
		{
			if (widget.isSelfHidden() == searchTerms.get(SELFHIDDEN))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Hidden"))
		{
			if (widget.isHidden() == searchTerms.get(HIDDEN))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Text"))
		{
			if (widget.getText().toLowerCase().contains(searchTerms.get(TEXT)))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("TextColor"))
		{
			//Textcolor is a hex value but is default to 0 so for some reason it returns as an Int when most of the
			//time we want to treat it as a string due to it being hex, so we convert it here.
			if (Integer.toString(widget.getTextColor(), 16).equals(searchTerms.get(TEXTCOLOR)))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Name"))
		{
			if (widget.getName().toLowerCase().contains(searchTerms.get(NAME)))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ItemId"))
		{
			if (widget.getItemId() == searchTerms.get(ITEMID))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ItemQuantity"))
		{
			if (widget.getItemQuantity() == searchTerms.get(ITEMQUANTITY))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ModelId"))
		{
			if (widget.getModelId() == searchTerms.get(MODELID))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("SpriteId"))
		{
			if (widget.getSpriteId() == searchTerms.get(SPRITEID))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Width"))
		{
			if (widget.getWidth() == searchTerms.get(WIDTH))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Height"))
		{
			if (widget.getHeight() == searchTerms.get(HEIGHT))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("RelativeX"))
		{
			if (widget.getRelativeX() == searchTerms.get(RELATIVEX))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("RelativeY"))
		{
			if (widget.getRelativeY() == searchTerms.get(RELATIVEY))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("CanvasLocation"))
		{
			if (widget.getCanvasLocation().equals(searchTerms.get(CANVASLOCATION)))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("Bounds"))
		{
			if (widget.getBounds().equals(searchTerms.get(BOUNDS)))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ScrollX"))
		{
			if (widget.getScrollX() == searchTerms.get(SCROLLX))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("ScrollY"))
		{
			if (widget.getScrollY() == searchTerms.get(SCROLLY))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("OriginalX"))
		{
			if (widget.getOriginalX() == searchTerms.get(ORIGINALX))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("OriginalY"))
		{
			if (widget.getOriginalY() == searchTerms.get(ORIGINALY))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("PaddingX"))
		{
			if (widget.getPaddingX() == searchTerms.get(PADDINGX))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		if (searchTerms.containsKey("PaddingY"))
		{
			if (widget.getPaddingY() == searchTerms.get(PADDINGY))
			{
				multiSearchMatch.add(true);
			}
			else
			{
				multiSearchMatch.add(false);
			}
		}

		//If not even a single match was found set false to return false.
		if (multiSearchMatch.isEmpty())
		{
			multiSearchMatch.add(false);
		}

		//if at any point a widget didn't match all search terms and assigned false then exit the method and try the next widget
		if (multiSearchMatch.contains(false))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//If necessary this can be moved to it's own file but I figured best not clutter the file system for a group of keys used once.
	private static final WidgetSearchMap.Key<Integer> ID = new WidgetSearchMap.Key<>("Id");
	private static final WidgetSearchMap.Key<Integer> TYPE = new WidgetSearchMap.Key<>("Type");
	private static final WidgetSearchMap.Key<Integer> CONTENTTYPE = new WidgetSearchMap.Key<>("ContentType");
	private static final WidgetSearchMap.Key<Integer> PARENTID = new WidgetSearchMap.Key<>("ParentId");
	private static final WidgetSearchMap.Key<Boolean> SELFHIDDEN = new WidgetSearchMap.Key<>("SelfHidden");
	private static final WidgetSearchMap.Key<Boolean> HIDDEN = new WidgetSearchMap.Key<>("Hidden");
	private static final WidgetSearchMap.Key<String> TEXT = new WidgetSearchMap.Key<>("Text");
	private static final WidgetSearchMap.Key<String> TEXTCOLOR = new WidgetSearchMap.Key<>("TextColor");
	private static final WidgetSearchMap.Key<String> NAME = new WidgetSearchMap.Key<>("Name");
	private static final WidgetSearchMap.Key<Integer> ITEMID = new WidgetSearchMap.Key<>("ItemId");
	private static final WidgetSearchMap.Key<Integer> ITEMQUANTITY = new WidgetSearchMap.Key<>("ItemQuantity");
	private static final WidgetSearchMap.Key<Integer> MODELID = new WidgetSearchMap.Key<>("ModelId");
	private static final WidgetSearchMap.Key<Integer> SPRITEID = new WidgetSearchMap.Key<>("SpriteId");
	private static final WidgetSearchMap.Key<Integer> WIDTH = new WidgetSearchMap.Key<>("Width");
	private static final WidgetSearchMap.Key<Integer> HEIGHT = new WidgetSearchMap.Key<>("Height");
	private static final WidgetSearchMap.Key<Integer> RELATIVEX = new WidgetSearchMap.Key<>("RelativeX");
	private static final WidgetSearchMap.Key<Integer> RELATIVEY = new WidgetSearchMap.Key<>("RelativeY");
	private static final WidgetSearchMap.Key<Point> CANVASLOCATION = new WidgetSearchMap.Key<>("CanvasLocation");
	private static final WidgetSearchMap.Key<Rectangle> BOUNDS = new WidgetSearchMap.Key<>("Bounds");
	private static final WidgetSearchMap.Key<Integer> SCROLLX = new WidgetSearchMap.Key<>("ScrollX");
	private static final WidgetSearchMap.Key<Integer> SCROLLY = new WidgetSearchMap.Key<>("ScrollY");
	private static final WidgetSearchMap.Key<Integer> ORIGINALX = new WidgetSearchMap.Key<>("OriginalX");
	private static final WidgetSearchMap.Key<Integer> ORIGINALY = new WidgetSearchMap.Key<>("OriginalY");
	private static final WidgetSearchMap.Key<Integer> PADDINGX = new WidgetSearchMap.Key<>("PaddingX");
	private static final WidgetSearchMap.Key<Integer> PADDINGY = new WidgetSearchMap.Key<>("PaddingY");
}
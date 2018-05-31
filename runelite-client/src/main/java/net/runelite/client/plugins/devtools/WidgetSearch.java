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
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;

@Slf4j
public class WidgetSearch
{

	WidgetSearchMap searchList = new WidgetSearchMap();
	public void searchRequest(String search)
	{
		//reset hashmap for new searches
		searchList.clear();

		//convert to lowercase for easier handling
		search = search.toLowerCase();
		//separate : and spaces in search to get field names and values with regex.
		String segments[] = search.split("[:|]");
		for (int i = 0; i < segments.length; i++)
		{
			switch (segments[i])
			{
				case "id":
					searchList.put(ID, Integer.parseInt(segments[i + 1]));
					break;
				case "type":
					searchList.put(TYPE, Integer.parseInt(segments[i + 1]));
					break;
				case "contenttype":
					searchList.put(CONTENTTYPE, Integer.parseInt(segments[i + 1]));
					break;
				case "parentid":
					searchList.put(PARENTID, Integer.parseInt(segments[i + 1]));
					break;
				case "selfhidden":
					if (segments[i + 1].equals("true"))
					{
						searchList.put(SELFHIDDEN, true);
					}
					else
						{
						searchList.put(SELFHIDDEN, false);
					}
					break;
				case "hidden":
					if (segments[i + 1].equals("true"))
					{
						searchList.put(HIDDEN, true);
					}
					else
						{
						searchList.put(HIDDEN, false);
					}
					break;
				case "text":
					searchList.put(TEXT, segments[i + 1]);
					break;
				case "textcolor":
					searchList.put(TEXTCOLOR, segments[i + 1]);
					break;
				case "name":
					searchList.put(NAME, segments[i + 1]);
					break;
				case "itemid":
					searchList.put(ITEMID, Integer.parseInt(segments[i + 1]));
					break;
				case "itemquantity":
					searchList.put(ITEMQUANTITY, Integer.parseInt(segments[i + 1]));
					break;
				case "modelid":
					searchList.put(MODELID, Integer.parseInt(segments[i + 1]));
					break;
				case "spriteid":
					searchList.put(SPRITEID, Integer.parseInt(segments[i + 1]));
					break;
				case "width":
					searchList.put(WIDTH, Integer.parseInt(segments[i + 1]));
					break;
				case "height":
					searchList.put(HEIGHT, Integer.parseInt(segments[i + 1]));
					break;
				case "relativex":
					searchList.put(RELATIVEX, Integer.parseInt(segments[i + 1]));
					break;
				case "relativey":
					searchList.put(RELATIVEY, Integer.parseInt(segments[i + 1]));
					break;
				case "canvaslocation":
					String canvasSegments[] = segments[i + 1].split("[,]");
					searchList.put(CANVASLOCATION, new Point(Integer.parseInt(canvasSegments[0]), Integer.parseInt(canvasSegments[1])));
					break;
				case "bounds":
					String boundsSegments[] = segments[i + 1].split("[,]");
					searchList.put(BOUNDS, new Rectangle(Integer.parseInt(boundsSegments[0]), Integer.parseInt(boundsSegments[1]), Integer.parseInt(boundsSegments[2]), Integer.parseInt(boundsSegments[3])));
					break;
				case "scrollx":
					searchList.put(SCROLLX, Integer.parseInt(segments[i + 1]));
					break;
				case "scrolly":
					searchList.put(SCROLLY, Integer.parseInt(segments[i + 1]));
					break;
				case "originalx":
					searchList.put(ORIGINALX, Integer.parseInt(segments[i + 1]));
					break;
				case "originaly":
					searchList.put(ORIGINALY, Integer.parseInt(segments[i + 1]));
					break;
				case "paddingx":
					searchList.put(PADDINGX, Integer.parseInt(segments[i + 1]));
					break;
				case "paddingy":
					searchList.put(PADDINGY, Integer.parseInt(segments[i + 1]));
					break;
			}
		}
	}




	public boolean widgetResults (Widget widget)
	{
		for (int i = 0; i < searchList.size(); i++)
		{
			//we cast all of the widget text strings to lowercase to match the value we put in the hashmap above

			if (searchList.containsKey("Id"))
			{
				if (widget.getId() == searchList.get(ID))
				{
					return true;
				}
			}

			if (searchList.containsKey("Type"))
			{
				if (widget.getType() == searchList.get(TYPE))
				{
					return true;
				}
			}

			if (searchList.containsKey("ContentType"))
			{
				if (widget.getContentType() == searchList.get(CONTENTTYPE))
				{
					return true;
				}
			}

			if (searchList.containsKey("ParentId"))
			{
				if (widget.getParentId() == searchList.get(PARENTID))
				{
					return true;
				}
			}

			if (searchList.containsKey("SelfHidden"))
			{
				if (widget.isSelfHidden() == searchList.get(SELFHIDDEN))
				{
					return true;
				}
			}

			if (searchList.containsKey("Hidden"))
			{
				if (widget.isHidden() == searchList.get(HIDDEN))
				{
					return true;
				}
			}

			if (searchList.containsKey("Text"))
			{
				if (widget.getText().toLowerCase().contains(searchList.get(TEXT)))
				{
					return true;
				}
			}

			if (searchList.containsKey("TextColor"))
			{
				//Textcolor is a hex value but is default to 0 so for some reason it returns as an Int when most of the
				//time we want to treat it as a string due to it being hex, so we convert it here.
				if (Integer.toString(widget.getTextColor(), 16).equals(searchList.get(TEXTCOLOR)))
				{
					return true;
				}


			}

			if (searchList.containsKey("Name"))
			{
				if (widget.getName().toLowerCase().contains(searchList.get(NAME)))
				{
					return true;
				}
			}

			if (searchList.containsKey("ItemId"))
			{
				if (widget.getItemId() == searchList.get(ITEMID))
				{
					return true;
				}
			}

			if (searchList.containsKey("ItemQuantity"))
			{
				if (widget.getItemQuantity() == searchList.get(ITEMQUANTITY))
				{
					return true;
				}
			}

			if (searchList.containsKey("ModelId"))
			{
				if (widget.getModelId() == searchList.get(MODELID))
				{
					return true;
				}
			}

			if (searchList.containsKey("SpriteId"))
			{
				if (widget.getSpriteId() == searchList.get(SPRITEID))
				{
					return true;
				}
			}

			if (searchList.containsKey("Width"))
			{
				if (widget.getWidth() == searchList.get(WIDTH))
				{
					return true;
				}
			}

			if (searchList.containsKey("Height"))
			{
				if (widget.getHeight() == searchList.get(HEIGHT))
				{
					return true;
				}
			}

			if (searchList.containsKey("RelativeX"))
			{
				if (widget.getRelativeX() == searchList.get(RELATIVEX))
				{
					return true;
				}
			}

			if (searchList.containsKey("RelativeY"))
			{
				if (widget.getRelativeY() == searchList.get(RELATIVEY))
				{
					return true;
				}
			}

			if (searchList.containsKey("CanvasLocation"))
			{
				if (widget.getCanvasLocation().equals(searchList.get(CANVASLOCATION)))
				{
					return true;
				}
			}

			if (searchList.containsKey("Bounds"))
			{
				if (widget.getBounds().equals(searchList.get(BOUNDS)))
				{
					return true;
				}
			}

			if (searchList.containsKey("ScrollX"))
			{
				if (widget.getScrollX() == searchList.get(SCROLLX))
				{
					return true;
				}
			}

			if (searchList.containsKey("ScrollY"))
			{
				if (widget.getScrollY() == searchList.get(SCROLLY))
				{
					return true;
				}
			}

			if (searchList.containsKey("OriginalX"))
			{
				if (widget.getOriginalX() == searchList.get(ORIGINALX))
				{
					return true;
				}
			}

			if (searchList.containsKey("OriginalY"))
			{
				if (widget.getOriginalY() == searchList.get(ORIGINALY))
				{
					return true;
				}
			}

			if (searchList.containsKey("PaddingX"))
			{
				if (widget.getPaddingX() == searchList.get(PADDINGX))
				{
					return true;
				}
			}

			if (searchList.containsKey("PaddingY"))
			{
				if (widget.getPaddingY() == searchList.get(PADDINGY))
				{
					return true;
				}
			}
		}
		return false;
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

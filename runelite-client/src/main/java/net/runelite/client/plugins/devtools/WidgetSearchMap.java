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

import java.util.HashMap;
import java.util.Map;

public class WidgetSearchMap
{

	private final Map<String, Object> baseMap;

	public WidgetSearchMap()
	{
		baseMap = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> T put(Key<T> key, T value)
	{
		return (T) baseMap.put(key.getName(), value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Key<T> key)
	{
		T value = (T) baseMap.get(key.getName());
		return value;
	}

	public int size()
	{
		return baseMap.size();
	}

	public void clear()
	{
		baseMap.clear();
	}

	public boolean containsKey(String key)
	{
		return baseMap.containsKey(key);
	}

	public static class Key<T>
	{
		private final String keyName;

		public Key(String key)
		{
			this.keyName = key;
		}

		public String getName()
		{
			return keyName;
		}
	}
}

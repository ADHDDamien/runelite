/*
 * Copyright (c) 2018, Damien <https://github.com/ADHDDamien>
 * Copyright (c) 2018, Abex
 * Copyright (c) 2017, Kronos <https://github.com/KronosDesign>
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.ClientUI;

@Slf4j
class WidgetInspector extends JFrame
{
	private final Client client;
	private final DevToolsPlugin plugin;
	private final DevToolsConfig config;

	private final JTree widgetTree;
	private final WidgetInfoTableModel infoTableModel;
	private final JCheckBox alwaysOnTop;
	private final JButton nextSearch;
	private boolean Ready;
	private boolean isSearching = false;
	private int searchIndex = 0;

	private static final Map<Integer, WidgetInfo> widgetIdMap = new HashMap<>();
	WidgetSearch widgetSearch = new WidgetSearch();
	List<DefaultMutableTreeNode> searchNodes = new ArrayList<>();
	List<Widget> widgetResults = new ArrayList<>();

	@Inject
	WidgetInspector(DevToolsPlugin plugin, Client client, WidgetInfoTableModel infoTableModel, DevToolsConfig config, EventBus eventBus)
	{
		this.plugin = plugin;
		this.client = client;
		this.infoTableModel = infoTableModel;
		this.config = config;

		eventBus.register(this);

		setTitle("RuneLite Widget Inspector");
		setIconImage(ClientUI.ICON);

		// Reset highlight on close
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				plugin.currentWidget = null;
				plugin.itemIndex = -1;
			}
		});

		setLayout(new BorderLayout());




		widgetTree = new JTree(new DefaultMutableTreeNode());
		widgetTree.setRootVisible(false);
		widgetTree.setShowsRootHandles(true);
		widgetTree.getSelectionModel().addTreeSelectionListener(e ->
		{
			Object selected = widgetTree.getLastSelectedPathComponent();
			if (selected instanceof WidgetTreeNode)
			{
				WidgetTreeNode node = (WidgetTreeNode) selected;
				Widget widget = node.getWidget();
				plugin.currentWidget = widget;
				plugin.itemIndex = widget.getItemId();
				refreshInfo();
				log.debug("Set widget to {} and item index to {}", widget, widget.getItemId());
			}
			else if (selected instanceof WidgetItemNode)
			{
				WidgetItemNode node = (WidgetItemNode) selected;
				plugin.itemIndex = node.getWidgetItem().getIndex();
				log.debug("Set item index to {}", plugin.itemIndex);
			}
		});

		final JScrollPane treeScrollPane = new JScrollPane(widgetTree);
		treeScrollPane.setPreferredSize(new Dimension(200, 400));


		final JTable widgetInfo = new JTable(infoTableModel);
		final JScrollPane infoScrollPane = new JScrollPane(widgetInfo);
		infoScrollPane.setPreferredSize(new Dimension(400, 400));



		final JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		final JButton refreshWidgetsBtn = new JButton("Load Widgets");
		refreshWidgetsBtn.addActionListener(e ->
		{
			if (client.getGameState().equals(GameState.LOGGED_IN))
			{
				refreshWidgets(client.getWidgetRoots());
			}
			else
				{
				JOptionPane.showMessageDialog(null, "Please log in to the game before trying to load widgets.");
			}
		});
		bottomPanel.add(refreshWidgetsBtn);

		final JButton EyedropWidgetsBtn = new JButton("Select in-game Widgets");
		EyedropWidgetsBtn.addActionListener(e ->
		{
			if (client.getGameState().equals(GameState.LOGGED_IN))
			{
				Ready = true;
			}
			else
				{
				JOptionPane.showMessageDialog(null, "Please log in to the game before trying to load widgets.");
			}
		});
		bottomPanel.add(EyedropWidgetsBtn);

		final JButton searchHelpBtn = new JButton("Search Help");
		searchHelpBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Search examples: \n\nId:10747904 where 10747904 is the Widget ID you are searching for\n\nCanvasLocation:0,0 with 0,0 being the X & Y in the Point\n\nText:Bank Of Runescape\n\nHidden=true where true is the boolean result you are searching for, etc.\n\nMultiple searches:\n\nChain searches with a pipe separating each search term for example Width:1642|Height:1057 or Text:Bank Of Runescape|Hidden:false\n\nThe enter key submits your search."));
		bottomPanel.add(searchHelpBtn);

		nextSearch = new JButton("Next Result");
		nextSearch.addActionListener(e -> nextResult());
		nextSearch.setEnabled(false);
		bottomPanel.add(nextSearch);

		alwaysOnTop = new JCheckBox("Always on top");
		alwaysOnTop.addItemListener(ev -> config.inspectorAlwaysOnTop(alwaysOnTop.isSelected()));
		onConfigChanged(null);
		bottomPanel.add(alwaysOnTop);


		final JTextField searchField = new JTextField("Search");
		searchField.setBackground(Color.GRAY);
		searchField.setText("Enter search here...");
		searchField.addActionListener(e -> search(searchField.getText()));
		JScrollPane scrollPane = new JScrollPane(searchField);

		add(scrollPane, BorderLayout.NORTH);

		final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, infoScrollPane);
		add(split, BorderLayout.CENTER);
		pack();
	}



	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (Ready)
		{
			Point mouse = client.getMouseCanvasPosition();
			List<Integer> groupIDs = getGroupIDs();
			Widget[] eyedropWidgets = new Widget[1000];
			event.consume();
			for ( int id = 0; id < groupIDs.size(); id++)
			{
				for (int i = 0; i < client.getGroup(groupIDs.get(id)).length; i++)
				{
					if (client.getWidget(groupIDs.get(id), i).contains(mouse))
					{
						eyedropWidgets[i] = client.getWidget(groupIDs.get(id), i);
					}
				}
			}
			refreshWidgets(eyedropWidgets);
			Ready = false;
		}
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged ev)
	{
		boolean onTop = config.inspectorAlwaysOnTop();
		setAlwaysOnTop(onTop);
		alwaysOnTop.setSelected(onTop);
	}

	private void searchHelp()
	{
		JOptionPane.showMessageDialog(null, "Search examples: \n\nId:10747904 where 10747904 is the Widget ID you are searching for\n\nCanvasLocation:0,0 with 0,0 being the X & Y in the Point\n\nText:Bank Of Runescape\n\nHidden=true where true is the boolean result you are searching for, etc.\n\nMultiple searches:\n\nChain searches with a pipe separating each search term for example Width:1642|Height:1057 or Text:Bank Of Runescape|Hidden:false\n\nThe enter key submits your search.");
	}

	private List<Integer> getGroupIDs()
	{
		WidgetInfo[] widgets = WidgetInfo.values();
		List<Integer> groupIDs = new ArrayList<Integer>();
		//Important fix to trigger null errors before we add to list in the try/catch by calling a null ID on purpose
		int errorCheck;
		for (WidgetInfo w : widgets)
		{
			try
			{
				//Do not remove this line of code or everything breaks
				errorCheck = client.getGroup(w.getGroupId()).length;
				if (!groupIDs.contains(w.getGroupId()))
				{
					groupIDs.add(w.getGroupId());
				}
			}
			catch (RuntimeException e)
			{
			}
		}
		return groupIDs;
	}

	private void search(String search)
	{
		if (client.getGameState().equals(GameState.LOGGED_IN))
		{
			searchIndex = 0;
			searchNodes.clear();
			widgetResults.clear();
			isSearching = true;
			widgetSearch.searchRequest(search);
			refreshWidgets(client.getWidgetRoots());
		}
		else
			{
			JOptionPane.showMessageDialog(null, "Please log in to the game before trying to search widgets.");
		}
	}

	private void nextResult()
	{
		searchIndex++;
		if (searchIndex < searchNodes.size())
		{
			plugin.currentWidget = widgetResults.get(searchIndex);
			plugin.itemIndex = -1;
			refreshInfo();
			widgetTree.expandPath(new TreePath(searchNodes.get(searchIndex).getPath()));
			widgetTree.setSelectionPath(new TreePath(searchNodes.get(searchIndex).getPath()));
			nextSearch.setText("Next Result: " + String.valueOf(searchIndex + 1) + "/" + searchNodes.size());
		}

		if (searchIndex >= searchNodes.size())
		{
			searchIndex = 0;
			plugin.currentWidget = widgetResults.get(searchIndex);
			plugin.itemIndex = -1;
			refreshInfo();
			widgetTree.expandPath(new TreePath(searchNodes.get(searchIndex).getPath()));
			widgetTree.setSelectionPath(new TreePath(searchNodes.get(searchIndex).getPath()));
			nextSearch.setText("Next Result: " + String.valueOf(searchIndex + 1) + "/" + searchNodes.size());
		}
	}

	private void updateSearch()
	{
		plugin.currentWidget = widgetResults.get(0);
		plugin.itemIndex = -1;
		refreshInfo();
		widgetTree.expandPath(new TreePath(searchNodes.get(0).getPath()));
		widgetTree.setSelectionPath(new TreePath(searchNodes.get(0).getPath()));
		nextSearch.setEnabled(true);
		nextSearch.setText("Next Result: " + String.valueOf(searchIndex + 1) + "/" + searchNodes.size());
		isSearching = false;
	}

	private void refreshWidgets(Widget[] widgets )
	{
		new SwingWorker<DefaultMutableTreeNode, Void>()
		{
			@Override
			protected DefaultMutableTreeNode doInBackground() throws Exception
			{
				Widget[] rootWidgets = widgets;
				DefaultMutableTreeNode root = new DefaultMutableTreeNode();
				if (!isSearching)
				{
					plugin.currentWidget = null;
					plugin.itemIndex = -1;
				}

				for (Widget widget : rootWidgets)
				{
					DefaultMutableTreeNode childNode = addWidget("R", widget);
					if (childNode != null)
					{
						root.add(childNode);
						if (isSearching)
						{
							if (widgetSearch.widgetResults(widget))
							{
								searchNodes.add(childNode);
								widgetResults.add(widget);
							}
						}
					}
				}

				return root;
			}

			@Override
			protected void done()
			{
				try
				{
					plugin.currentWidget = null;
					plugin.itemIndex = -1;
					refreshInfo();
					widgetTree.setModel(new DefaultTreeModel(get()));
					if (isSearching)
					{
						updateSearch();
					}
					else
						{
						//reset search iterator button if loading widgets and not searching
						searchIndex = 0;
						nextSearch.setText("Next Result");
						nextSearch.setEnabled(false);
					}
				}
				catch (InterruptedException | ExecutionException ex)
				{
					throw new RuntimeException(ex);
				}
			}
		}.execute();
		}

	private DefaultMutableTreeNode addWidget(String type, Widget widget)
	{
		if (widget == null || widget.isHidden())
		{
			return null;
		}

		DefaultMutableTreeNode node = new WidgetTreeNode(type, widget);
		Widget[] childComponents = widget.getDynamicChildren();

		if (childComponents != null)
		{
			for (Widget component : childComponents)
			{
				DefaultMutableTreeNode childNode = addWidget("D", component);
				if (childNode != null)
				{
					node.add(childNode);

					if (isSearching)
					{
						if (widgetSearch.widgetResults(component))
						{
							searchNodes.add(childNode);
							widgetResults.add(component);
						}
					}
				}
			}
		}

		childComponents = widget.getStaticChildren();
		if (childComponents != null)
		{
			for (Widget component : childComponents)
			{
				DefaultMutableTreeNode childNode = addWidget("S", component);
				if (childNode != null)
				{
					node.add(childNode);
					if (isSearching)
					{
						if (widgetSearch.widgetResults(component))
						{
							searchNodes.add(childNode);
							widgetResults.add(component);
						}
					}
				}
			}
		}

		childComponents = widget.getNestedChildren();
		if (childComponents != null)
		{
			for (Widget component : childComponents)
			{
				DefaultMutableTreeNode childNode = addWidget("N", component);
				if (childNode != null)
				{
					node.add(childNode);
					if (isSearching)
					{
						if (widgetSearch.widgetResults(component))
						{
							searchNodes.add(childNode);
							widgetResults.add(component);
						}
					}
				}
			}
		}

		Collection<WidgetItem> items = widget.getWidgetItems();

		if (items != null)
		{
			for (WidgetItem item : items)
			{
				if (item == null)
				{
					continue;
				}
				node.add(new WidgetItemNode(item));
			}
		}

		return node;
	}

	private void refreshInfo()
	{
		infoTableModel.setWidget(plugin.currentWidget);
	}

	public static WidgetInfo getWidgetInfo(int packedId)
	{
		if (widgetIdMap.size() == 0)
		{
			//Initialize map here so it doesn't create the index
			//until it's actually needed.
			WidgetInfo[] widgets = WidgetInfo.values();
			for (WidgetInfo w : widgets)
			{
				widgetIdMap.put(w.getPackedId(), w);
			}
		}
		return widgetIdMap.get(packedId);
	}
}

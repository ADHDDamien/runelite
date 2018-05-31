package net.runelite.client.plugins.devtools;

import com.google.inject.Inject;

//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;

import net.runelite.api.Point;

import javax.swing.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ClientUI;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

@Slf4j
public class WidgetSearch extends JFrame {

    Object[][] multi = new Object[25][3];
    final JTable table = new JTable();
    boolean optionsApplied;
    private List<String> name;
    private List<String> value;

    JButton button;

    @Inject
    WidgetSearch()
    {
        setTitle("RuneLite Widget Searcher");
        setIconImage(ClientUI.ICON);

        setLayout(new BorderLayout());

        String[] columnNames = {"Field", "Value", "Search"};
        Object[][] data = {
                {"Id", 0, false},
                {"Type", 0, false},
                {"ContentType", 0, false},
                {"ParentId", 0, false},
                {"SelfHidden", false, false},
                {"Hidden", false, false},
                {"Text", "", false},
                {"TextColor", 0, false},
                {"Name", "", false},
                {"ItemId", 0, false},
                {"ItemQuantity", 0, false},
                {"ModelId", 0, false},
                {"SpriteId", 0, false},
                {"Width", 0, false},
                {"Height", 0, false},
                {"RelativeX", 0, false},
                {"RelativeY", 0, false},
                {"CanvasLocation", new Point(0,0), false},
                {"Bounds", new Rectangle(0,0,0,0), false},
                {"ScrollX", 0, false},
                {"ScrollY", 0, false},
                {"OriginalX", 0, false},
                {"OriginalY", 0, false},
                {"PaddingX", 0, false},
                {"PaddingY", 0, false}
        };
        final Class[] columnClass = new Class[] {
                String.class, String.class, Boolean.class
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                if(column == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return columnClass[columnIndex];
            }
        };



        table.setModel(model);
        table.setEnabled(true);

        final JScrollPane infoScrollPane = new JScrollPane(table);


        final JPanel bottomPanel = new JPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        button = new JButton("Apply Options");
        button.addActionListener(e -> setSearch(table));
        bottomPanel.add(button);
        add(infoScrollPane, BorderLayout.CENTER);

        pack();
    }




    private void setSearch(JTable table)
    {
        int row = table.getRowCount();
        int column = table.getColumnCount();
        name = new ArrayList<>();
        value = new ArrayList<>();
        for (int j = 0; j  < row; j++)
        {
            if(table.getValueAt(j, 2).equals(true))
            {
                optionsApplied = true;
                for(int i = 0; i < column; i++)
                {
                    switch(i)
                    {
                        case 0: name.add(table.getValueAt(j, i).toString());
                        break;
                        case 1: value.add(table.getValueAt(j, i).toString());
                        break;
                    }
                    //multi[j][i] = table.getValueAt(j, i);
                }

            }
        }
        if(optionsApplied){
            setName(name);
            setValue(value);
        } else{
            JOptionPane.showMessageDialog(null, "You have not toggled any search options!");
        }


    }

    public void setName(List name)
    {
        this.name = name;
    }

    public void setValue(List value)
    {
        this.value = value;
    }

    public List getNames()
    {
        return name;
    }

    public List getSearchValue()
    {
        return value;
    }

}

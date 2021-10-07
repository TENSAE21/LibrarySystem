package librarysystem.UI.members.list;

import business.LibraryMember;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class JTableMembersModel extends AbstractTableModel {
    private List<LibraryMember> members = new ArrayList<LibraryMember>(
            Arrays.asList(
                    new LibraryMember("John", "Silva"),
                    new LibraryMember("Nina", "Borges"),
                    new LibraryMember("Fernanda", "Smith"),
                    new LibraryMember("Junior", "Johson"),
                    new LibraryMember("Evelyn", "Santos")
            )
    );

    private List<LibraryMember> rows = members;

    private String[] columns = {"ID", "NAME", ""};

    public String getColumnName(int column) {
        return columns[column];
    }
    public int getRowCount() {
        return rows.size();
    }
    public int getColumnCount() {
        return columns.length;
    }
    public Object getValueAt(int row, int column) {
        Object value = "??";
        LibraryMember member = members.get(row);
        switch (column) {
            case 0:
                value = member.getMemberId();
                break;
            case 1:
                value = member.getFirstName() + " " + member.getLastName();
                break;
            case 2:
                value = "Edit";
                break;
        }
        return value;
    }
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

}

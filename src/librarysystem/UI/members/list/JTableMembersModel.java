package librarysystem.UI.members.list;

import business.LibraryMember;
import business.SystemController;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class JTableMembersModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private SystemController controller = new SystemController();
	private HashMap<String, LibraryMember> membersSet = controller.getLibraryMembers();
    private List<LibraryMember> members = membersSet.values()
            .stream()
            .collect(Collectors.toList());

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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
    
    public void addRow(LibraryMember member) {
    	members.add(member);
    	fireTableDataChanged();
    }
    
    public void reloadData() {
    	membersSet = controller.getLibraryMembers();
        members = membersSet.values()
                .stream()
                .collect(Collectors.toList());
    	fireTableDataChanged();
    }
    
    public LibraryMember getMemberById(String id) {
    	return members.stream().filter(m -> m.getMemberId() == id).collect(Collectors.toList()).get(0);
    }

}

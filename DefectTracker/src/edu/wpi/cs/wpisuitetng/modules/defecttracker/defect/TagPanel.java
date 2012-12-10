package edu.wpi.cs.wpisuitetng.modules.defecttracker.defect;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Tag;

/**
 * Panel to manage adding and removing tags
 *
 */
@SuppressWarnings("serial")
public class TagPanel extends JPanel {

	protected JTextField txtNewTag;
	protected DefaultListModel lmTags;
	protected JList lstTags;
	protected JButton btnAddTag;
	protected JButton btnRemoveTag;
	
	private final Defect model;
	
	private Border defaultBorder;
	
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/**
	 * Creates a new TagPanel.
	 * 
	 * @param defect	The Defect to use to populate the Tag list and to which the Tag list will be compared.
	 */
	protected TagPanel(Defect defect) {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBorder(BorderFactory.createTitledBorder("Tags"));

		this.model = defect;
		
		addComponents(layout);
		
		// Populate the list of tags
		for (Tag tag : model.getTags()) {
			lmTags.addElement(tag);
		}
		
		addEventListeners();
	}
	
	/**
	 * Adds the components to the panel and places constraints on them
	 * for SpringLayout.
	 * @param layout the layout manager
	 */
	protected void addComponents(SpringLayout layout) {
		txtNewTag = new JTextField(20);
		lmTags = new DefaultListModel();
		lstTags = new JList(lmTags);
		lstTags.setBorder(txtNewTag.getBorder());
		btnAddTag = new JButton("Add");
		btnRemoveTag = new JButton("Remove");
		
		defaultBorder = lstTags.getBorder();
		
		JLabel lblNewTag = new JLabel("Enter a new tag:");
		int labelWidth = lblNewTag.getPreferredSize().width;
		
		layout.putConstraint(SpringLayout.NORTH, lblNewTag, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, lblNewTag, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, lblNewTag, labelWidth, SpringLayout.WEST, lblNewTag);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtNewTag, 0, SpringLayout.VERTICAL_CENTER, lblNewTag);
		layout.putConstraint(SpringLayout.WEST, txtNewTag, HORIZONTAL_PADDING, SpringLayout.EAST, lblNewTag);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, btnAddTag, 0, SpringLayout.VERTICAL_CENTER, txtNewTag);
		layout.putConstraint(SpringLayout.WEST, btnAddTag, HORIZONTAL_PADDING * 5, SpringLayout.EAST, txtNewTag);
		
		layout.putConstraint(SpringLayout.NORTH, lstTags, VERTICAL_PADDING, SpringLayout.SOUTH, txtNewTag);
		layout.putConstraint(SpringLayout.WEST, lstTags, 0, SpringLayout.WEST, txtNewTag);
		layout.putConstraint(SpringLayout.EAST, lstTags, 0, SpringLayout.EAST, txtNewTag);
		layout.putConstraint(SpringLayout.SOUTH, lstTags, txtNewTag.getPreferredSize().height * 6, SpringLayout.NORTH, lstTags);
		
		layout.putConstraint(SpringLayout.WEST, btnRemoveTag, 0, SpringLayout.WEST, btnAddTag);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, btnRemoveTag, 0, SpringLayout.VERTICAL_CENTER, lstTags);
		layout.putConstraint(SpringLayout.EAST, btnAddTag, 0, SpringLayout.EAST, btnRemoveTag);
		
		layout.putConstraint(SpringLayout.SOUTH, this, 15, SpringLayout.SOUTH, lstTags);
		
		add(lblNewTag);
		add(txtNewTag);
		add(btnAddTag);
		add(lstTags);
		add(btnRemoveTag);
	}
	
	/**
	 * Checks if the Tags in this TagPanel differ from the model and highlights the Tag list accordingly.
	 */
	protected void checkIfUpdated() {
		if (model.getTags().size() == lmTags.size()) {
			Iterator<Tag> tagsI = model.getTags().iterator();
			
			lstTags.setBackground(Color.WHITE);
			lstTags.setBorder(defaultBorder);
			
			while (tagsI.hasNext()) {
				if (!lmTags.contains(tagsI.next())) {
					lstTags.setBackground(new Color(243, 243, 209));
					lstTags.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				}
			}
		}
		else {
			lstTags.setBackground(new Color(243, 243, 209));
			lstTags.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}
	
	/**
	 * Adds event listeners to the buttons
	 */
	protected void addEventListeners() {
		
		// Listener for btnAddTag
		btnAddTag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (txtNewTag.getText().length() > 0) {
					lmTags.addElement(txtNewTag.getText());
					txtNewTag.setText("");
					
					checkIfUpdated();
				}
			}
		});
		
		// Listener for btnRemoveTag
		btnRemoveTag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = lstTags.getSelectedIndex();
				if (index > -1) {
					lmTags.removeElementAt(index);
					
					checkIfUpdated();
				}
			}
		});
	}
}
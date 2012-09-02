package it.gas.altichierock.insert;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import it.gas.altichierock.database.entities.Component;
import it.gas.altichierock.database.entities.Product;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ProductPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Product product;
	private HashMap<JCheckBox, Component> mapper; //map checkboxes to components
	private List<OnSelectionListener> listeners;
	private double totalPrice;

	private JCheckBox chkProduct;
	
	public ProductPanel(Product p) {
		this.product = p;
		listeners = new ArrayList<OnSelectionListener>();
		mapper = new HashMap<JCheckBox, Component>();
		totalPrice = product.getBasePrice();
		initComponents();
		initListeners();
	}

	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 5, ins 0"));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		chkProduct = new JCheckBox(product.getDescription());
		Font f = chkProduct.getFont();
		f = new Font(f.getFamily(), Font.BOLD, 24);
		chkProduct.setFont(f);
		updateTitleText();
		add(chkProduct, "growx, span 5");
		
		//adding components
		List<Component> l = product.getComponents();
		for (int i = 0; i < l.size(); i++) {
			Component c = l.get(i);
			JCheckBox chk = new JCheckBox(c.getDescription());
			chk.setEnabled(false);
			mapper.put(chk, c);
			add(chk, "growx");
		}
	}
	
	private void initListeners() {
		chkProduct.addActionListener(this);
		Iterator<JCheckBox> iter = mapper.keySet().iterator();
		while (iter.hasNext())
			iter.next().addActionListener(this);
	}
	
	private void disableComponents(boolean b) {
		Iterator<JCheckBox> iter = mapper.keySet().iterator();
		while (iter.hasNext())
			iter.next().setEnabled(!b);
	}
	
	public void disablePanel(boolean b) {
		chkProduct.setEnabled(!b);
		disableComponents(true);
	}
	
	public String getProduct() {
		return product.getDescription();
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public String[] getComponentsSelected() {
		ArrayList<String> list = new ArrayList<String>();
		Iterator<JCheckBox> iter = mapper.keySet().iterator();
		while (iter.hasNext()) {
			JCheckBox chk = iter.next();
			if (chk.isSelected())
				list.add(mapper.get(chk).getDescription());
		}
		return list.toArray(new String[0]);
	}
	
	public void addListener(OnSelectionListener l) {
		listeners.add(l);
	}
	
	public void removeListener(OnSelectionListener l) {
		listeners.remove(l);
	}
	
	private void updateTitleText() {
		//the title IS the text in the product checkbox.
		StringBuilder build = new StringBuilder();
		build.append(product.getDescription());
		build.append(" (€");
		build.append(Double.toString(totalPrice));
		build.append(")");
		chkProduct.setText(build.toString());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == chkProduct) {
			if (chkProduct.isSelected()) {
				fireOnProductSelected(product);
				disableComponents(false);
			} else {
				fireOnProductDeselected(product);
				disableComponents(true);
			}
		} else if (arg0.getSource() instanceof JCheckBox) {//must be a component
			//recalculate total price
			Iterator<JCheckBox> iter = mapper.keySet().iterator();
			totalPrice = product.getBasePrice();
			while (iter.hasNext()) {
				JCheckBox chk = iter.next();
				if (chk.isSelected())
					totalPrice += mapper.get(chk).getPrice();
			}
			//update label
			updateTitleText();
		}
	}
	
	private void fireOnProductSelected(Product p) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onProductSelected(this);
	}
	
	private void fireOnProductDeselected(Product p) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onProductDeselected(this);
	}
	
	/*private void fireOnComponentSelected(Component c) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onComponentSelected(product, c);
	}
	
	private void fireOnComponentDeselected(Component c) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onComponentDeselected(product, c);
	}*/
	
	public interface OnSelectionListener {
		public void onProductSelected(ProductPanel pp);
		public void onProductDeselected(ProductPanel pp);
		//public void onComponentSelected(Product p, Component c);
		//public void onComponentDeselected(Product p, Component c);
	}
	
}

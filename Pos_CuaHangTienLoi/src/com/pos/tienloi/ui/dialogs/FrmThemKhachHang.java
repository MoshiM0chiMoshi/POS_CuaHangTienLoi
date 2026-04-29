package com.pos.tienloi.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrmThemKhachHang extends JDialog implements ActionListener {

	private JTextField txtTen, txtSDT, txtSoHoaDon, txtDiemTichLuy;
	private JButton btnLuu, btnHuy;
	private JComboBox<String> cbxVaiTro;
	private boolean isEdit = false;

	private boolean isSaved = false;

	public FrmThemKhachHang(Window parent) {
		this(parent, false);
	}

	public FrmThemKhachHang(Window parent, boolean isEdit) {
		super(parent, isEdit ? "Cập nhật khách hàng" : "Thêm khách hàng", ModalityType.APPLICATION_MODAL);
		setSize(700, 450);
		setLocationRelativeTo(parent);
		setResizable(false);

		initUi();
	}

	private void initUi() {
		setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(Color.WHITE);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);

		JPanel rightPanel = new JPanel(new GridLayout(6, 2, 10, 15));
		rightPanel.setBackground(Color.WHITE);
		Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

		rightPanel.add(createLabel("Tên Khách Hàng:", labelFont));
		rightPanel.add(txtTen = new JTextField());

		rightPanel.add(createLabel("Số điện thoại", labelFont));
		rightPanel.add(txtSDT = new JTextField());

		rightPanel.add(createLabel("Số hóa đơn:", labelFont));
		rightPanel.add(txtSoHoaDon = new JTextField());

		rightPanel.add(createLabel("Điểm tích lũy:", labelFont));
		rightPanel.add(txtDiemTichLuy = new JTextField());

		mainPanel.add(rightPanel, BorderLayout.CENTER);

		// 3. PHẦN NÚT BẤM (SOUTH)
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		buttonPanel.setBackground(Color.WHITE);

		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));

		btnLuu = new JButton(isEdit ? "Cập nhật" : "Xác nhận");
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLuu.setBackground(Color.decode("#4A90E2"));
		btnLuu.setForeground(Color.WHITE);

		buttonPanel.add(btnHuy);
		buttonPanel.add(btnLuu);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		if (isEdit) {
			txtSDT.setEditable(false);
		}

		btnLuu.addActionListener(this);
		btnHuy.addActionListener(this);

	}

	private JLabel createLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		return label;
	}

	// XỬ LÝ TẤT CẢ SỰ KIỆN Ở ĐÂY

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(btnLuu)) {
			xuLyLuu();
		} else if (source.equals(btnHuy)) {
			xuLyHuy();
		}

	}

	private void xuLyLuu() {

		String ten = txtTen.getText();
		String sdt = txtSDT.getText();
		String soHoaDon = txtSoHoaDon.getText();
		String diemTichLuy = txtDiemTichLuy.getText();
		if (!ten.matches("^([A-Z][a-z]+)( [A-Z][a-z]+)+$") || ten.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Tên khách hàng phải bắt đầu bằng chữ hoa và ít nhất 2 từ và không rỗng");
			requestFocus();
			return;
		}
		if (!sdt.matches("^0(9|3|5)[0-9]{8}$") || sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Số điện thoại phải bắt đầu bằng các số 03,09 hoặc 05 ,phải đủ 10 số và không rỗng");
			requestFocus();
			return;
		}
		if (soHoaDon.trim().isEmpty() || Integer.parseInt(soHoaDon) <= 0) {
			JOptionPane.showMessageDialog(this, "Số hóa đơn phải lớn hơn 0 và không rỗng");
			requestFocus();
			return;
		}
		if (diemTichLuy.trim().isEmpty() || Integer.parseInt(diemTichLuy) < 0) {
			JOptionPane.showMessageDialog(this, "Điểm tích lũy phải >=0 và không rỗng");
			requestFocus();
			return;
		}
		isSaved = true;
		dispose();
	}

	private void xuLyHuy() {
		isSaved = false;
		dispose();
	}

	// Sử dụng nếu dùng Frm để edit
	public void setKhachHangData(String sdt, String ten, String soHoaDon, String diemTichLuy) {
		txtTen.setText(ten);
		txtSDT.setText(sdt);
		txtSoHoaDon.setText(soHoaDon);
		txtDiemTichLuy.setText(diemTichLuy);
	}

	// CÁC HÀM GETTER ĐỂ MÀN HÌNH CHÍNH LẤY DỮ LIỆU

	public String getTxtTen() {
		return txtTen.getText();
	}

	public String getTxtSDT() {
		return txtSDT.getText();
	}

	public String gettxtSoHoaDon() {
		return txtSoHoaDon.getText();
	}

	public String gettxtDiemTichLuy() {
		return txtDiemTichLuy.getText();
	}

	public String getCbxVaiTro() {
		return cbxVaiTro.getSelectedItem().toString();
	}

	public boolean isSaved() {
		return isSaved;
	}

}
package com.pos.tienloi.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.pos.tienloi.dao.VaiTro_Dao;
import com.pos.tienloi.model.VaiTro;

public class FrmThemNhanVien extends JDialog implements ActionListener {

	private JTextField txtMa, txtTen, txtSDT;
	private JPasswordField txtMatKhau, txtXacNhan;
	private JButton btnLuu, btnHuy;
	private JComboBox<String> cbxVaiTro;

	private VaiTro_Dao vt_Dao;
	private boolean isEdit = false;
	private boolean isSaved = false;
	private JButton btnXoaTrang;

	public FrmThemNhanVien(Window parent) {
		this(parent, false);
	}

	public FrmThemNhanVien(Window parent, boolean isEdit) {
		super(parent, isEdit ? "Cập nhật nhân viên" : "Thêm nhân viên", ModalityType.APPLICATION_MODAL);
		setSize(700, 450);
		setLocationRelativeTo(parent);
		setResizable(false);

		vt_Dao = new VaiTro_Dao();

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

		rightPanel.add(createLabel("Mã nhân viên", labelFont));
		rightPanel.add(txtMa = new JTextField());

		rightPanel.add(createLabel("Tên nhân viên:", labelFont));
		rightPanel.add(txtTen = new JTextField());

		rightPanel.add(createLabel("Số điện thoại", labelFont));
		rightPanel.add(txtSDT = new JTextField());

		rightPanel.add(createLabel("Vai trò:", labelFont));
		cbxVaiTro = new JComboBox<String>();

		ArrayList<VaiTro> listVaiTro = vt_Dao.getallVaiTro();
		for (VaiTro vt : listVaiTro) {
			cbxVaiTro.addItem(vt.getTenVaiTro());

		}

		rightPanel.add(cbxVaiTro);

		rightPanel.add(createLabel("Mật Khẩu:", labelFont));
		rightPanel.add(txtMatKhau = new JPasswordField());

		rightPanel.add(createLabel("Xác nhận mật khẩu:", labelFont));
		rightPanel.add(txtXacNhan = new JPasswordField());

		mainPanel.add(rightPanel, BorderLayout.CENTER);

		// 3. PHẦN NÚT BẤM (SOUTH)
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		buttonPanel.setBackground(Color.WHITE);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));

		
		btnLuu = new JButton(isEdit ? "Cập nhật" : "Xác nhận");
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLuu.setBackground(Color.decode("#4A90E2"));
		btnLuu.setForeground(Color.WHITE);

		buttonPanel.add(btnXoaTrang);
		buttonPanel.add(btnHuy);
		buttonPanel.add(btnLuu);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		if (isEdit) {
			txtSDT.setEditable(false);
		}

		btnXoaTrang.addActionListener(this);
		btnLuu.addActionListener(this);
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
		else if (source.equals(btnXoaTrang)) {
			xuLyXoaTrang();
		}

	}

	private void xuLyXoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		txtSDT.setText("");
		txtMatKhau.setText("");
		txtXacNhan.setText("");
		cbxVaiTro.setSelectedIndex(0);
	}
	private void xuLyLuu() {

		String ma = txtMa.getText();
		String ten = txtTen.getText();
		String sdt = txtSDT.getText();
		String mk = txtMatKhau.getText();
		String xacNhan = txtXacNhan.getText();
		if (!ma.matches("^NV[0-9]{3}") || (ma.trim().isEmpty())) {
			JOptionPane.showMessageDialog(this,
					"Mã nhân viên phải theo form: Bắt đầu là NV kèm theo sau là 3 chữ số và không rỗng vd NV001");
			requestFocus();
			return;
		}
		if (!ten.matches("^([A-Z][a-zà-ỹ]+)( [A-Z][a-zà-ỹ]+)+$") || ten.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Tên nhân viên phải bắt đầu bằng chữ hoa và ít nhất 2 từ và không rỗng");
			requestFocus();
			return;
		}
		if (!sdt.matches("^0(9|3|5)[0-9]{8}$") || sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Số điện thoại phải bắt đầu bằng các số 03,09 hoặc 05 ,phải đủ 10 số và không rỗng");
			requestFocus();
			return;
		}

		if (mk.isEmpty() || mk == null) {
			JOptionPane.showMessageDialog(this, "Bắt buộc phải nhập mật khẩu");
			requestFocus();
			return;
		}

		if (!mk.equals(xacNhan)) {
			JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không khớp");
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
	public void setNhanVienData(String ma, String ten, String sdt, String vaiTro) {
		txtMa.setText(ma);
		txtTen.setText(ten);
		txtSDT.setText(sdt);
		cbxVaiTro.setSelectedItem(vaiTro);
	}

	// CÁC HÀM GETTER ĐỂ MÀN HÌNH CHÍNH LẤY DỮ LIỆU

	public String getTxtMa() {
		return txtMa.getText();
	}

	public String getTxtTen() {
		return txtTen.getText();
	}

	public String getTxtSDT() {
		return txtSDT.getText();
	}

	public String getTxtMatKhau() {
		return new String(txtMatKhau.getPassword());
	}

	public String getTxtXacNhan() {
		return new String(txtXacNhan.getPassword());
	}

	public String getCbxVaiTro() {
		return cbxVaiTro.getSelectedItem().toString();
	}

	public boolean isSaved() {
		return isSaved;
	}

}
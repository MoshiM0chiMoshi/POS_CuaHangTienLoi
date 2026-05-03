package com.pos.tienloi.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pos.tienloi.dao.DanhMuc_Dao;
import com.pos.tienloi.dao.Thue_Dao;
import com.pos.tienloi.model.DanhMuc;
import com.pos.tienloi.model.Thue;

public class FrmThemSanPham extends JDialog implements ActionListener {

	private JTextField txtMa, txtTen, txtTonKho, txtGia;
	private JComboBox<DanhMuc> cbxDanhMuc;
	private JComboBox<Thue> cbxThue;
	private JButton btnLuu, btnHuy, btnChonAnh;
	private JLabel lblHinhAnh;

	private boolean isEdit = false;
	private boolean isSaved = false;

	private String duongDanAnh = "";
	private JButton btnXoaTrang;

	public FrmThemSanPham(Window parent) {
		this(parent, false);
	}

	public FrmThemSanPham(Window parent, boolean isEdit) {
		super(parent, isEdit ? "Cập nhật Sản Phẩm" : "Thêm Sản Phẩm", ModalityType.APPLICATION_MODAL);
		this.isEdit = isEdit;
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

		// ===== LEFT: IMAGE =====
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);

		lblHinhAnh = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
		lblHinhAnh.setPreferredSize(new Dimension(180, 180));
		lblHinhAnh.setMaximumSize(new Dimension(180, 180));
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		btnChonAnh = new JButton("Chọn Ảnh...");
		btnChonAnh.setAlignmentX(CENTER_ALIGNMENT);

		lblHinhAnh.setAlignmentX(CENTER_ALIGNMENT);

		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(lblHinhAnh);
		leftPanel.add(Box.createVerticalStrut(15));
		leftPanel.add(btnChonAnh);

		mainPanel.add(leftPanel, BorderLayout.WEST);

		// ===== RIGHT: FORM =====
		JPanel rightPanel = new JPanel(new GridLayout(6, 2, 10, 15));
		rightPanel.setBackground(Color.WHITE);

		rightPanel.add(createLabel("Mã sản phẩm:"));
		rightPanel.add(txtMa = new JTextField());

		rightPanel.add(createLabel("Tên sản phẩm:"));
		rightPanel.add(txtTen = new JTextField());

		rightPanel.add(createLabel("Tồn kho:"));
		rightPanel.add(txtTonKho = new JTextField());

		rightPanel.add(createLabel("Giá bán:"));
		rightPanel.add(txtGia = new JTextField());

		// ===== THUẾ =====
		rightPanel.add(createLabel("Thuế:"));
		cbxThue = new JComboBox<>();
		Thue_Dao thueDao = new Thue_Dao();
		for (Thue t : thueDao.getallThue()) {
			cbxThue.addItem(t);
		}
		rightPanel.add(cbxThue);

		// ===== DANH MỤC =====
		rightPanel.add(createLabel("Danh mục:"));
		cbxDanhMuc = new JComboBox<>();
		DanhMuc_Dao dmDao = new DanhMuc_Dao();
		ArrayList<DanhMuc> ds = dmDao.getallDanhMuc();
		for (DanhMuc d : ds) {
			cbxDanhMuc.addItem(d);
		}
		rightPanel.add(cbxDanhMuc);

		mainPanel.add(rightPanel, BorderLayout.CENTER);

		// ===== BUTTON =====
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		btnHuy = new JButton("Hủy");
		btnLuu = new JButton("Lưu");
		btnXoaTrang = new JButton("Xóa trắng");
		buttonPanel.add(btnXoaTrang);
		buttonPanel.add(btnHuy);
		buttonPanel.add(btnLuu);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		btnXoaTrang.addActionListener(this);
		btnChonAnh.addActionListener(this);
		btnLuu.addActionListener(this);
		btnHuy.addActionListener(this);
	}

	private JLabel createLabel(String text) {
		return new JLabel(text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnChonAnh) {
			xuLyChonAnh();
		} else if (e.getSource() == btnLuu) {
			xuLyLuu();
		} else if (e.getSource() == btnXoaTrang) {
			xuLyXoaTrang();
		}

		else {
			dispose();
		}
	}

	private void xuLyXoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		txtGia.setText("");
		txtTonKho.setText("");
		duongDanAnh = "";

		lblHinhAnh.setIcon(null);
		lblHinhAnh.setText("Chưa có ảnh");

		cbxDanhMuc.setSelectedIndex(0);
		cbxThue.setSelectedIndex(0);
	}

	private void xuLyChonAnh() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image", "jpg", "png"));

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			duongDanAnh = file.getAbsolutePath();

			ImageIcon icon = new ImageIcon(duongDanAnh);
			Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			lblHinhAnh.setIcon(new ImageIcon(img));
			lblHinhAnh.setText("");
		}
	}

	private void xuLyLuu() {
		try {
			if (txtMa.getText().trim().isEmpty() || !txtMa.getText().matches("^SP[0-9]{3}")) {
				JOptionPane.showMessageDialog(this,
						"Mã nhân viên phải theo form: Bắt đầu là SP kèm theo sau là 3 chữ số và không rỗng vd SP001");
				requestFocus();
				return;
			}

			if (txtTen.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Tên sản phẩm không được rỗng");
				requestFocus();
				return;
			}

			if (!txtTonKho.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "Tồn kho phải là số nguyên >= 0");
				txtTonKho.requestFocus();
				return;
			}

			if (!txtGia.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "Giá phải là số > 0");
				txtGia.requestFocus();
				return;
			}

			if (txtTonKho.getText().trim().isEmpty() || txtGia.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Tồn kho và giá không được rỗng");
				return;
			}

			int ton = Integer.parseInt(txtTonKho.getText());
			double gia = Double.parseDouble(txtGia.getText());

			if (ton < 0 || gia <= 0) {
				JOptionPane.showMessageDialog(this, "Tồn kho và giá tiền phải là số");
				return;
			}

			if (!isEdit && duongDanAnh.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Chưa chọn ảnh");
				return;
			}

			isSaved = true;
			dispose();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}

	private void xuLyHuy() {
		isSaved = false;
		dispose();
	}

	// ===== GETTER =====
	public boolean isSaved() {
		return isSaved;
	}

	public String getDuongDanAnh() {
		return duongDanAnh;
	}

	public String getMaSP() {
		return txtMa.getText();
	}

	public String getTenSP() {
		return txtTen.getText();
	}

	public String getTonKho() {
		return txtTonKho.getText();
	}

	public String getGia() {
		return txtGia.getText();
	}

	public DanhMuc getDanhMuc() {
		return (DanhMuc) cbxDanhMuc.getSelectedItem();
	}

	public Thue getThue() {
		return (Thue) cbxThue.getSelectedItem();
	}

	public void setSanPhamData(String duongDanAnh, String ma, String ten, String tonkho, String gia, String tenThue,
			String tenDM) {
		txtMa.setText(ma);
		txtMa.setEditable(false); // Không cho sửa mã SP khi Edit
		txtTen.setText(ten);
		txtTonKho.setText(tonkho);
		txtGia.setText(gia);
		this.duongDanAnh = duongDanAnh;

		// Chọn đúng Thuế trong ComboBox
		for (int i = 0; i < cbxThue.getItemCount(); i++) {
			Thue t = cbxThue.getItemAt(i);
			if (String.valueOf(Math.round(t.getMucThue() * 100)).equals(tenThue)) {
				cbxThue.setSelectedIndex(i);
				break;
			}
		}

		// Chọn đúng Danh Mục trong ComboBox
		for (int i = 0; i < cbxDanhMuc.getItemCount(); i++) {
			DanhMuc d = cbxDanhMuc.getItemAt(i);
			if (d.getTenDanhMuc().equals(tenDM)) {
				cbxDanhMuc.setSelectedIndex(i);
				break;
			}
		}

		if (duongDanAnh != null && !duongDanAnh.trim().isEmpty())

		{
			ImageIcon icon = new ImageIcon(duongDanAnh);
			Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			lblHinhAnh.setIcon(new ImageIcon(img));
			lblHinhAnh.setText("");
		} else {
			lblHinhAnh.setIcon(null);
			lblHinhAnh.setText("Chưa có ảnh");
		}
	}
}
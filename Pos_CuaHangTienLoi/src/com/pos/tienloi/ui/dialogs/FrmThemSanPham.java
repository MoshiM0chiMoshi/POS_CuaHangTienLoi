package com.pos.tienloi.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrmThemSanPham extends JDialog implements ActionListener {

	private JTextField txtMa, txtTen, txtTonKho, txtGia, txtThue;
	private JComboBox<String> cbxDanhMuc;
	private JButton btnLuu, btnHuy, btnChonAnh;
	private JLabel lblHinhAnh;

	private boolean isSaved = false;
	private String duongDanAnh = ""; // Lưu đường dẫn ảnh để sau này lưu vào DB

	public FrmThemSanPham(Window parent) {
		super(parent, "Thêm Sản Phẩm Mới", ModalityType.APPLICATION_MODAL);
		setSize(700, 450); // Mở rộng chiều ngang một chút để chứa ảnh
		setLocationRelativeTo(parent);
		setResizable(false);

		initUi();
	}

	private void initUi() {
		setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout(20, 0)); // Khoảng cách giữa trái và phải là 20px
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(Color.WHITE);

		// 1. PHẦN BÊN TRÁI: CHỌN HÌNH ẢNH (WEST)

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);

		lblHinhAnh = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
		lblHinhAnh.setPreferredSize(new Dimension(180, 180));
		lblHinhAnh.setMaximumSize(new Dimension(180, 180));
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		btnChonAnh = new JButton("Chọn Ảnh...");
		btnChonAnh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnChonAnh.setAlignmentX(CENTER_ALIGNMENT);

		// Căn giữa components trong BoxLayout
		lblHinhAnh.setAlignmentX(CENTER_ALIGNMENT);

		leftPanel.add(Box.createVerticalStrut(20)); // Căn xuống một chút
		leftPanel.add(lblHinhAnh);
		leftPanel.add(Box.createVerticalStrut(15));
		leftPanel.add(btnChonAnh);

		mainPanel.add(leftPanel, BorderLayout.WEST);

		// 2. PHẦN BÊN PHẢI: NHẬP LIỆU (CENTER)

		JPanel rightPanel = new JPanel(new GridLayout(6, 2, 10, 15));
		rightPanel.setBackground(Color.WHITE);
		Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

		rightPanel.add(createLabel("Mã sản phẩm:", labelFont));
		rightPanel.add(txtMa = new JTextField());

		rightPanel.add(createLabel("Tên sản phẩm:", labelFont));
		rightPanel.add(txtTen = new JTextField());

		rightPanel.add(createLabel("Tồn kho:", labelFont));
		rightPanel.add(txtTonKho = new JTextField());

		rightPanel.add(createLabel("Giá bán:", labelFont));
		rightPanel.add(txtGia = new JTextField());

		rightPanel.add(createLabel("Thuế (%):", labelFont));
		rightPanel.add(txtThue = new JTextField());

		rightPanel.add(createLabel("Danh mục:", labelFont));
		String[] danhMucList = { "Anime", "Manga", "Figure", "Khác" };
		cbxDanhMuc = new JComboBox<>(danhMucList);
		rightPanel.add(cbxDanhMuc);

		mainPanel.add(rightPanel, BorderLayout.CENTER);

		// 3. PHẦN NÚT BẤM (SOUTH)

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		buttonPanel.setBackground(Color.WHITE);

		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));

		btnLuu = new JButton("Lưu Sản Phẩm");
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLuu.setBackground(Color.decode("#4A90E2"));
		btnLuu.setForeground(Color.WHITE);

		buttonPanel.add(btnHuy);
		buttonPanel.add(btnLuu);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		btnChonAnh.addActionListener(this);
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

		if (source.equals(btnChonAnh)) {
			xuLyChonAnh();
		} else if (source.equals(btnLuu)) {
			xuLyLuu();
		} else if (source.equals(btnHuy)) {
			xuLyHuy();
		}
	}

	private void xuLyChonAnh() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn hình ảnh sản phẩm");

		// Lọc chỉ cho phép chọn file ảnh
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Hình ảnh (JPG, PNG, GIF)", "jpg", "jpeg",
				"png", "gif");
		fileChooser.setFileFilter(imageFilter);

		int userSelection = fileChooser.showOpenDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			duongDanAnh = fileToSave.getAbsolutePath();

			// Hiển thị ảnh lên JLabel và resize cho vừa khung 180x180
			ImageIcon icon = new ImageIcon(duongDanAnh);
			Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			lblHinhAnh.setIcon(new ImageIcon(img));
			lblHinhAnh.setText(""); // Xóa chữ "Chưa có ảnh"
		}
	}

	private void xuLyLuu() {
		// TODO: Validate dữ liệu (kiểm tra rỗng, mã trùng...) ở đây
		// Nếu validate thất bại, dùng JOptionPane.showMessageDialog để báo lỗi và
		// return;

		isSaved = true;
		dispose();
	}

	private void xuLyHuy() {
		isSaved = false;
		dispose();
	}

	// ==========================================
	// CÁC HÀM GETTER ĐỂ MÀN HÌNH CHÍNH LẤY DỮ LIỆU
	// ==========================================
	public boolean isSaved() {
		return isSaved;
	}

	// Tạo thêm các hàm này để bên FrmSanPham có thể lấy dữ liệu vừa nhập
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

	public String getThue() {
		return txtThue.getText();
	}

	public String getDanhMuc() {
		return cbxDanhMuc.getSelectedItem().toString();
	}
}
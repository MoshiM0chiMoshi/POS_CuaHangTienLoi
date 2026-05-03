package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.pos.tienloi.dao.ChiTietHoaDon_Dao;
import com.pos.tienloi.dao.ChiTietPhieuDat_Dao;
import com.pos.tienloi.dao.HoaDon_Dao;
import com.pos.tienloi.dao.KhachHang_Dao;
import com.pos.tienloi.dao.PhieuDat_Dao;
import com.pos.tienloi.dao.SanPham_Dao;
import com.pos.tienloi.model.ChiTietHoaDon;
import com.pos.tienloi.model.ChiTietPhieuDat;
import com.pos.tienloi.model.HoaDon;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.PTTT;
import com.pos.tienloi.model.PhieuDatHang;
import com.pos.tienloi.model.SanPham;
import com.pos.tienloi.model.TrangThaiHoaDon;
import com.pos.tienloi.model.TrangThaiPhieuDat;
import com.pos.tienloi.ui.components.PlaceholderTextField;
import com.pos.tienloi.ui.dialogs.FrmThemKhachHang;

public class FrmLapDon extends JPanel {

	private SanPham_Dao spDao = new SanPham_Dao();
	private KhachHang_Dao khDao = new KhachHang_Dao();
	HoaDon_Dao hdDao = new HoaDon_Dao();
	private ChiTietHoaDon_Dao ctDao = new ChiTietHoaDon_Dao();
	private PhieuDat_Dao pdDao = new PhieuDat_Dao();
	private ChiTietPhieuDat_Dao ctpdDao = new ChiTietPhieuDat_Dao();

	// UI Components
	private JTextField txtSearchProduct;
	private JPanel pnlProductContainer;

	private PlaceholderTextField txtSearchPhone;
	private PlaceholderTextField txtAddress;
	private JLabel lblCustomerName;
	private KhachHang currentCustomer = null;
	private SanPham currentSanPham = null;

	private JPanel pnlCartContainer;
	private JLabel lblTotalAmount;
	private JComboBox<PTTT> cbxPaymentMethod;
	private JButton btnLapDon;
	private JButton btnLapPhieuDat;

	private Map<SanPham, Integer> cartMap = new HashMap<>();
	private DecimalFormat df = new DecimalFormat("#,###.## VND");

	public FrmLapDon() {
		setLayout(new BorderLayout(10, 10));
		setSize(1300, 800);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(new Color(249, 249, 249)); // Màu Trắng kem nhạt
		initUi();
		loadProductsToUI();
	}

	public void initUi() {
		JPanel pnlLeft = new JPanel(new BorderLayout(0, 10));
		pnlLeft.setOpaque(false);
		pnlLeft.setPreferredSize(new Dimension((int) (1300 * 0.65), 800));

		// Thanh tìm kiếm sản phẩm
		JPanel pnlSearch = new JPanel(new BorderLayout());
		pnlSearch.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(122, 90, 42), 1, true), new EmptyBorder(5, 10, 5, 10)));
		pnlSearch.setBackground(Color.WHITE);

		JLabel lblSearchIcon = new JLabel("🔍");
		txtSearchProduct = new PlaceholderTextField("Tìm kiếm sản phẩm");
		txtSearchProduct.setBorder(null);
		txtSearchProduct.setFont(new Font("Arial", Font.PLAIN, 16));

		pnlSearch.add(lblSearchIcon, BorderLayout.WEST);
		pnlSearch.add(txtSearchProduct, BorderLayout.CENTER);
		pnlLeft.add(pnlSearch, BorderLayout.NORTH);

		// Vùng chứa thẻ Sản Phẩm
		pnlProductContainer = new JPanel(new GridLayout(0, 3, 15, 15)); // 3 cột, khoảng cách 15px
		pnlProductContainer.setBackground(new Color(249, 249, 249));

		JScrollPane scrollProducts = new JScrollPane(pnlProductContainer);
		scrollProducts.setBorder(null);
		scrollProducts.getVerticalScrollBar().setUnitIncrement(16);
		pnlLeft.add(scrollProducts, BorderLayout.CENTER);
		// 2. CỘT PHẢI (GIỎ HÀNG & THANH TOÁN) - Chiếm 35%

		JPanel pnlRight = new JPanel(new BorderLayout(0, 10));
		pnlRight.setOpaque(false);
		pnlRight.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true), new EmptyBorder(15, 15, 15, 15)));

		// --- Khu vực Khách Hàng ---
		JPanel pnlCustomer = new JPanel(new BorderLayout(5, 5));
		pnlCustomer.setOpaque(false);
		pnlCustomer.setBorder(new TitledBorder(null, "Khách Hàng", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 16)));

		txtSearchPhone = new PlaceholderTextField("Tìm khách hàng(nhập sdt)");

		txtSearchPhone.setFont(new Font("Arial", Font.PLAIN, 14));

		JPanel pnlCustomerDetail = new JPanel();
		pnlCustomerDetail.setLayout(new BoxLayout(pnlCustomerDetail, BoxLayout.Y_AXIS));
		pnlCustomerDetail.setOpaque(false);

		lblCustomerName = new JLabel("Khách lẻ");
		lblCustomerName.setFont(new Font("Arial", Font.ITALIC, 13));
		lblCustomerName.setForeground(Color.GRAY);

		txtAddress = new PlaceholderTextField("Địa chỉ nhận hàng");
		txtAddress.setFont(new Font("Arial", Font.PLAIN, 14));

		pnlCustomerDetail.add(lblCustomerName);
		pnlCustomerDetail.add(javax.swing.Box.createVerticalStrut(5)); // Khoảng cách
		pnlCustomerDetail.add(txtAddress);

		pnlCustomer.add(txtSearchPhone, BorderLayout.NORTH);
		pnlCustomer.add(pnlCustomerDetail, BorderLayout.CENTER);

		// Bắt sự kiện tìm khách hàng khi bấm Enter
		txtSearchPhone.addActionListener(e -> handleSearchCustomer());

		// --- Khu vực Giỏ Hàng (Cart Summary) ---
		JPanel pnlCartArea = new JPanel(new BorderLayout(0, 5));
		pnlCartArea.setOpaque(false);
		JLabel lblCartTitle = new JLabel("Giỏ Hàng");
		lblCartTitle.setFont(new Font("Arial", Font.BOLD, 18));
		pnlCartArea.add(lblCartTitle, BorderLayout.NORTH);

		pnlCartContainer = new JPanel();
		pnlCartContainer.setLayout(new BoxLayout(pnlCartContainer, BoxLayout.Y_AXIS));
		pnlCartContainer.setBackground(Color.WHITE);
		JScrollPane scrollCart = new JScrollPane(pnlCartContainer);
		scrollCart.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
		pnlCartArea.add(scrollCart, BorderLayout.CENTER);

		// --- Khu vực Thanh Toán ---
		JPanel pnlCheckout = new JPanel(new GridLayout(4, 1, 0, 10));
		pnlCheckout.setOpaque(false);

		// Phương thức thanh toán (Dùng Enum bạn cung cấp)
		cbxPaymentMethod = new JComboBox<>(PTTT.values());
		cbxPaymentMethod.setFont(new Font("Arial", Font.PLAIN, 14));

		// Tổng tiền
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.setOpaque(false);
		JLabel lblTotalText = new JLabel("Tổng cộng:");
		lblTotalText.setFont(new Font("Arial", Font.BOLD, 18));
		lblTotalAmount = new JLabel("0 VND");
		lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 22));
		lblTotalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlTotal.add(lblTotalText, BorderLayout.WEST);
		pnlTotal.add(lblTotalAmount, BorderLayout.EAST);

		// Nút bấm
		btnLapDon = new JButton("Lập Đơn (Mua Ngay)");
		btnLapDon.setBackground(new Color(240, 138, 39)); // Cam sáng nổi bật
		btnLapDon.setForeground(Color.WHITE);
		btnLapDon.setFont(new Font("Arial", Font.BOLD, 16));
		btnLapDon.setFocusPainted(false);

		btnLapPhieuDat = new JButton("Lập Phiếu Đặt Hàng");
		btnLapPhieuDat.setBackground(new Color(122, 90, 42)); // Nâu vàng đậm
		btnLapPhieuDat.setForeground(Color.WHITE);
		btnLapPhieuDat.setFont(new Font("Arial", Font.BOLD, 16));
		btnLapPhieuDat.setFocusPainted(false);

		pnlCheckout.add(cbxPaymentMethod);
		pnlCheckout.add(pnlTotal);
		pnlCheckout.add(btnLapDon);
		pnlCheckout.add(btnLapPhieuDat);

		// Lắp ráp cột phải
		pnlRight.add(pnlCustomer, BorderLayout.NORTH);
		pnlRight.add(pnlCartArea, BorderLayout.CENTER);
		pnlRight.add(pnlCheckout, BorderLayout.SOUTH);

		// Add 2 cột vào Frame chính
		add(pnlLeft, BorderLayout.CENTER);
		add(pnlRight, BorderLayout.EAST);

		// Thêm Action cho nút
		setupButtonActions();
	}

	// LOGIC NGHIỆP VỤ

	public void loadProductsToUI() {
		pnlProductContainer.removeAll();
		ArrayList<SanPham> dsSanPham = spDao.getAllSanPham();

		for (SanPham sp : dsSanPham) {
			JPanel card = createProductCard(sp);
			pnlProductContainer.add(card);
		}
		pnlProductContainer.revalidate();
		pnlProductContainer.repaint();
	}

	// Tạo 1 thẻ sản phẩm bên trái

	private JPanel createProductCard(SanPham sp) {

		JPanel card = new JPanel(new BorderLayout(0, 5));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), new EmptyBorder(10, 10, 10, 10)));
		card.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// 1. Tạo Label chứa Hình ảnh
		ImageIcon icon = loadIcon(sp.getHinhAnh());
		JLabel lblImage = new JLabel();
		if (icon != null) {
			lblImage.setIcon(icon);
		} else {
			lblImage.setText("No Image"); // Hiển thị tạm nếu không có ảnh
		}
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);

		// 2. Gom Tên và Giá vào một Panel nhỏ nằm ở dưới
		JPanel pnlInfo = new JPanel(new GridLayout(2, 1, 0, 5));
		pnlInfo.setBackground(Color.WHITE);

		// Tên SP
		JLabel lblName = new JLabel(sp.getTenSP());
		lblName.setFont(new Font("Arial", Font.BOLD, 14));
		lblName.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa text

		// Giá & Tồn kho
		JLabel lblPriceStock = new JLabel(df.format(sp.getGiaBan()) + " | Kho: " + sp.getSoLuongTon());
		lblPriceStock.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPriceStock.setForeground(Color.GRAY);
		lblPriceStock.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa text

		pnlInfo.add(lblName);
		pnlInfo.add(lblPriceStock);

		card.add(lblImage, BorderLayout.CENTER); // Ảnh nằm giữa/trên
		card.add(pnlInfo, BorderLayout.SOUTH); // Thông tin nằm dưới

		// Click để thêm vào giỏ
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addToCart(sp);
			}
		});

		return card;
	}

	private void addToCart(SanPham sp) {
		cartMap.put(sp, cartMap.getOrDefault(sp, 0) + 1);
		updateCartUI();
	}

	public void updateCartUI() {
		pnlCartContainer.removeAll();
		double total = 0;

		for (Map.Entry<SanPham, Integer> entry : cartMap.entrySet()) {
			SanPham sp = entry.getKey();
			int qty = entry.getValue();
			double subTotal = sp.getGiaBan() * qty;
			total += subTotal;

			JPanel pnlItem = new JPanel(new BorderLayout(5, 5));
			pnlItem.setBackground(Color.WHITE);
			pnlItem.setBorder(new EmptyBorder(10, 10, 10, 10));
			pnlItem.setMaximumSize(new Dimension(500, 60));

			JLabel lblInfo = new JLabel(sp.getTenSP() + " x" + qty);
			lblInfo.setFont(new Font("Arial", Font.BOLD, 14));

			JLabel lblPrice = new JLabel(df.format(subTotal));
			lblPrice.setForeground(new Color(240, 138, 39));

			// Nút tăng giảm
			JPanel pnlControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			pnlControls.setOpaque(false);
			JButton btnMinus = new JButton("-");
			JButton btnPlus = new JButton("+");

			btnMinus.addActionListener(e -> {
				if (qty > 1) {
					cartMap.put(sp, qty - 1);
				} else {
					cartMap.remove(sp);
				}
				updateCartUI();
			});

			btnPlus.addActionListener(e -> {
				if (qty < sp.getSoLuongTon()) {
					cartMap.put(sp, qty + 1);
					updateCartUI();
				} else {
					JOptionPane.showMessageDialog(this, "Vượt quá số lượng tồn kho!");
				}
			});

			pnlControls.add(btnMinus);
			pnlControls.add(btnPlus);

			pnlItem.add(lblInfo, BorderLayout.NORTH);
			pnlItem.add(lblPrice, BorderLayout.WEST);
			pnlItem.add(pnlControls, BorderLayout.EAST);

			// Thêm đường kẻ dưới mỗi item
			pnlItem.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));

			pnlCartContainer.add(pnlItem);
		}

		lblTotalAmount.setText(df.format(total));
		pnlCartContainer.revalidate();
		pnlCartContainer.repaint();
	}

	private void handleSearchCustomer() {
		String phone = txtSearchPhone.getText().trim();

		if (phone.isEmpty()) {
			currentCustomer = null;
			lblCustomerName.setText("Khách lẻ");
			lblCustomerName.setForeground(Color.GRAY);
			return;
		}

		KhachHang kh = khDao.findBySdt(phone);

		if (kh != null) {
			currentCustomer = kh;
			lblCustomerName.setText("KH: " + kh.getTenKhachHang() + " - Điểm: " + kh.getDiemTichLuy());
			lblCustomerName.setForeground(new Color(0, 128, 0));
			return;
		}

		currentCustomer = null;
		lblCustomerName.setText("Khách lẻ");
		lblCustomerName.setForeground(Color.GRAY);

		int confirm = JOptionPane.showConfirmDialog(this,
				"Không tìm thấy khách hàng. Bạn có muốn tạo khách hàng mới không?", "Tạo khách hàng",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			Window parent = SwingUtilities.getWindowAncestor(this);
			FrmThemKhachHang dialog = new FrmThemKhachHang(parent);
			dialog.setVisible(true);

			if (dialog.isSaved()) {
				KhachHang khMoi = dialog.getKhachHang();

				if (khMoi != null) {
					khDao.create(khMoi);
					currentCustomer = khMoi;
					txtSearchPhone.setText(khMoi.getSdt());
					lblCustomerName.setText("KH: " + khMoi.getTenKhachHang() + " - Điểm: " + khMoi.getDiemTichLuy());
					lblCustomerName.setForeground(new Color(0, 128, 0));
				}
			}
		}
	}

	private void setupButtonActions() {
		btnLapDon.addActionListener(e -> {
			if (cartMap.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
				return;
			}

			try {

				HoaDon hd = new HoaDon();
				String maHD = hdDao.generateNextMaHD();
				hd.setMaHoaDon(maHD);
				hd.setNgayLap(new java.util.Date());
				hd.setKhachHang(currentCustomer);
				NhanVien nv = new NhanVien();
				nv.setMaNV("NV001");
				hd.setNhanVien(nv);
				PTTT phuongThuc = (PTTT) cbxPaymentMethod.getSelectedItem();
				hd.setPhuongThuc(phuongThuc);
				hd.setTrangThai(TrangThaiHoaDon.Paid);

				List<ChiTietHoaDon> dsCT = new ArrayList<>();

				for (Map.Entry<SanPham, Integer> entry : cartMap.entrySet()) {
					SanPham sp = entry.getKey();
					int soLuong = entry.getValue();

					ChiTietHoaDon ct = new ChiTietHoaDon();
					ct.setSanPham(sp);
					ct.setSoLuong(soLuong);
					ct.setDonGia(sp.getGiaBan());
					dsCT.add(ct);
				}
				hd.setListChiTietHoaDon(dsCT);
				hd.capNhatTongTien();

				boolean success = hdDao.create(hd);
				if (success) {

					boolean allItemsSaved = ctDao.createList(dsCT, maHD);
					if (allItemsSaved) {
						if (currentCustomer != null) {
							currentCustomer.setSoHoaDon(currentCustomer.getSoHoaDon() + 1);
							int diemCong = (int) (hd.getTongTien() / 10000);
							currentCustomer.setDiemTichLuy(currentCustomer.getDiemTichLuy() + diemCong);
							khDao.update(currentCustomer);

						}

						JOptionPane.showMessageDialog(this, "Lập đơn thành công! Mã đơn: " + maHD);
						if (orderSuccessListener != null) {
							orderSuccessListener.onSuccess();
						}
						cartMap.clear();
						updateCartUI();
					} else {
						JOptionPane.showMessageDialog(this, "Lưu chi tiết đơn hàng thất bại!");
					}

				}

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "lỗi");
			}

			cartMap.clear();
			updateCartUI();
		});

		btnLapPhieuDat.addActionListener(e -> {
			if (cartMap.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
				return;
			}

			String diaChiGiao = txtAddress.getText().trim();
			if (diaChiGiao.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Phiếu giao hàng phải nhập địa chỉ nhận hàng!");
				txtAddress.requestFocus();
				return;
			}

			if (currentCustomer == null) {
				JOptionPane.showMessageDialog(this, "Lập phiếu đặt hàng cần thông tin Khách Hàng. Vui lòng nhập SĐT!");
				txtSearchPhone.requestFocus();
				return;
			}

			try {
				PhieuDatHang pd = new PhieuDatHang();
				String maPD = pdDao.generateNextMaPD();
				pd.setMaPhieuDat(maPD);
				pd.setNgayDat(new java.util.Date());
				pd.setKhachHang(currentCustomer);
				NhanVien nv = new NhanVien();
				nv.setMaNV("NV001");
				pd.setNhanVien(nv);
				pd.setTrangThai(TrangThaiPhieuDat.CHO_DUYET);
				pd.setDiaChi(diaChiGiao);

				List<ChiTietPhieuDat> dsCT = new ArrayList<>();

				for (Map.Entry<SanPham, Integer> entry : cartMap.entrySet()) {
					SanPham sp = entry.getKey();
					int soLuong = entry.getValue();
					ChiTietPhieuDat ct = new ChiTietPhieuDat();
					ct.setSanPham(sp);
					ct.setSoLuongDat(soLuong);
					ct.setDonGiaDat(sp.getGiaBan());
					dsCT.add(ct);
				}
				pd.setListChiTietPhieu(dsCT);
				pd.capNhatTongTien();
				boolean success = pdDao.create(pd);
				if (success) {
					boolean allItemsSaved = ctpdDao.createList(dsCT, maPD);
					if (allItemsSaved) {
						if (currentCustomer != null) {
							currentCustomer.setSoHoaDon(currentCustomer.getSoHoaDon() + 1);
							int diemCong = (int) (pd.getTongTien() / 10000);
							currentCustomer.setDiemTichLuy(currentCustomer.getDiemTichLuy() + diemCong);
							khDao.update(currentCustomer);
							JOptionPane.showMessageDialog(this, "Lập đơn thành công! Mã đơn: " + maPD);
							if (orderSuccessListener != null) {
								orderSuccessListener.onSuccess();
							}
							cartMap.clear();
							updateCartUI();
						} else {
							JOptionPane.showMessageDialog(this, "Lưu chi tiết đơn hàng thất bại!");
						}

					}

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			JOptionPane.showMessageDialog(this,
					"Lập phiếu đặt hàng thành công cho KH: " + currentCustomer.getTenKhachHang());
			cartMap.clear();
			updateCartUI();
		});
	}

	private ImageIcon loadIcon(String fileName) {
		if (fileName == null || fileName.isBlank())
			return null;

		ImageIcon temp = null;

		// 1. Tìm trong Resource project
		String resourcePath = fileName.startsWith("/") ? fileName : "/" + fileName;
		java.net.URL imgURL = getClass().getResource(resourcePath);

		if (imgURL != null) {
			temp = new ImageIcon(imgURL);
		} else {
			// 2. Tìm theo đường dẫn tuyệt đối trên ổ đĩa
			java.io.File imgFile = new java.io.File(fileName);
			if (imgFile.exists()) {
				temp = new ImageIcon(fileName);
			}
		}

		if (temp == null)
			return null;

		java.awt.Image img = temp.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	// Thêm đoạn code này vào làm thuộc tính của class FrmLapDon
	public interface OnOrderSuccessListener {
		void onSuccess();
	}

	private OnOrderSuccessListener orderSuccessListener;

	public void setOrderSuccessListener(OnOrderSuccessListener listener) {
		this.orderSuccessListener = listener;
	}

}
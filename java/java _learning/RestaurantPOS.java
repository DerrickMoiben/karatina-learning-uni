import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class RestaurantPOS extends JFrame {

    // ─── Color Palette ─────────────────────────────────────────────────────
    private static final Color BG_DARK       = new Color(18, 18, 30);
    private static final Color BG_PANEL      = new Color(28, 28, 45);
    private static final Color BG_CARD       = new Color(38, 38, 58);
    private static final Color ACCENT_BLUE   = new Color(66, 135, 245);
    private static final Color ACCENT_GREEN  = new Color(52, 199, 89);
    private static final Color ACCENT_RED    = new Color(255, 69, 58);
    private static final Color ACCENT_ORANGE = new Color(255, 149, 0);
    private static final Color ACCENT_PURPLE = new Color(175, 82, 222);
    private static final Color TEXT_PRIMARY  = new Color(245, 245, 250);
    private static final Color TEXT_SECONDARY= new Color(160, 160, 180);
    private static final Color DIVIDER       = new Color(55, 55, 80);

    // ─── Category Colors ─────────────────────────────────────────────────────
    private static final Color[] CAT_COLORS = {
        new Color(220, 50, 50),   // Burgers
        new Color(220, 120, 20),  // Sides
        new Color(40, 120, 220),  // Drinks
        new Color(200, 60, 80),   // Salads
        new Color(200, 140, 20),  // Desserts
        new Color(30, 160, 80),   // Discounts
    };

    // ─── Data ─────────────────────────────────────────────────────────────────
    private final Map<String, List<MenuItem>> menuData = new LinkedHashMap<>();
    private final List<OrderItem> currentOrder = new ArrayList<>();
    private String currentCategory = "Burgers";
    private int tableNumber = 1;
    private int orderCounter = 14;

    // Image cache
    private final Map<String, ImageIcon> imageCache = new HashMap<>();

    // ─── UI refs ──────────────────────────────────────────────────────────────
    private JPanel menuItemsPanel;
    private JLabel tableLabel, orderNumLabel;
    private DefaultTableModel orderTableModel;
    private JLabel subtotalLabel, taxLabel, totalLabel;
    private JLabel statusBar;
    private JTable orderTable;

    // ══════════════════════════════════════════════════════════════════════════
    public RestaurantPOS() {
        buildMenuData();
        setTitle("Omega Gardens Hotel – Restaurant POS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);
        root.add(buildTopBar(),    BorderLayout.NORTH);
        root.add(buildCenter(),    BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);
        setContentPane(root);

        // Pre-load images in background
        preloadImages();

        setVisible(true);
    }

    // ─── Build menu data with local image filenames ───────────────────────────
    private void buildMenuData() {
        // Burgers
        menuData.put("Burgers", Arrays.asList(
            new MenuItem("Cheeseburger",           11.00, "🍔", "cheeseburger.jpeg"),
            new MenuItem("The Classic",            10.50, "🍔", "classic_burger.jpeg"),
            new MenuItem("Veggie Burger",           9.50, "🥦", "veggie_burger.jpg"),
            new MenuItem("Mushroom Burger",        10.00, "🍄", "mushroom_burger.jpeg"),
            new MenuItem("Paradiso Burger",        12.50, "🌶", "spicy_burger.jpg"),
            new MenuItem("Double Bacon",           13.00, "🥓", "bacon_burger.jpg"),
            new MenuItem("BBQ Burger",             12.00, "🔥", "bbq_burger.jpg"),
            new MenuItem("Crispy Chicken",         11.50, "🐔", "crispy_chicken_burger.jpg")
        ));
        
        // Sides
        menuData.put("Sides", Arrays.asList(
            new MenuItem("French Fries",            4.00, "🍟", "french_fries.jpg"),
            new MenuItem("Onion Rings",             4.50, "🧅", "onion_rings.jpg"),
            new MenuItem("Coleslaw",                3.00, "🥗", "coleslaw.jpg"),
            new MenuItem("Garlic Bread",            3.50, "🥖", "garlic_bread.jpg"),
            new MenuItem("Corn on the Cob",         4.00, "🌽", "corn_on_cob.jpg"),
            new MenuItem("Sweet Potato Fries",      5.00, "🍠", "sweet_potato_fries.jpg")
        ));
        
        // Drinks
        menuData.put("Drinks", Arrays.asList(
            new MenuItem("Soda",                    2.50, "🥤", "soda.jpg"),
            new MenuItem("Lemonade",                3.00, "🍋", "lemonade.jpg"),
            new MenuItem("Iced Tea",                2.50, "🧋", "iced_tea.jpg"),
            new MenuItem("Orange Juice",            4.00, "🍊", "orange_juice.jpg"),
            new MenuItem("Coffee",                  3.50, "☕", "coffee.jpg"),
            new MenuItem("Milkshake",               5.00, "🥛", "milkshake.jpg")
        ));
        
        // Salads
        menuData.put("Salads", Arrays.asList(
            new MenuItem("Caesar Salad",            7.50, "🥗", "caesar_salad.jpg"),
            new MenuItem("Greek Salad",             8.00, "🫒", "greek_salad.jpg"),
            new MenuItem("Garden Salad",            6.50, "🌱", "garden_salad.jpg"),
            new MenuItem("Nicoise Salad",           9.00, "🐟", "nicoise_salad.jpg"),
            new MenuItem("Caprese",                 8.50, "🍅", "caprese_salad.jpg")
        ));
        
        // Desserts
        menuData.put("Desserts", Arrays.asList(
            new MenuItem("Chocolate Cake",          6.50, "🍫", "chocolate_cake.jpg"),
            new MenuItem("Ice Cream",               4.50, "🍦", "ice_cream.jpg"),
            new MenuItem("Cheesecake",              7.00, "🍰", "cheesecake.jpg"),
            new MenuItem("Brownie",                 5.00, "🍫", "brownie.jpg"),
            new MenuItem("Fruit Salad",             5.50, "🍓", "fruit_salad.jpg")
        ));
        
        // Discounts (no images needed)
        menuData.put("Discounts", Arrays.asList(
            new MenuItem("Staff Discount 10%",     -0.10, "🏷", "discount.jpg"),
            new MenuItem("Happy Hour 20%",         -0.20, "⏰", "happy_hour.jpg"),
            new MenuItem("Loyalty -$2",            -2.00, "⭐", "loyalty_reward.jpg"),
            new MenuItem("Manager Comp",           -5.00, "🎁", "gift.jpg")
        ));
    }

    // ─── Load image from local "images" folder ────────────────────────────────
    private ImageIcon loadLocalImage(String filename, int width, int height) {
        if (filename == null) return null;
        
        String cacheKey = filename + "_" + width + "x" + height;
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        try {
            // Look for images in the "images" folder (same directory as .java file)
            String path = "images/" + filename;
            File imageFile = new File(path);
            
            if (imageFile.exists()) {
                BufferedImage original = ImageIO.read(imageFile);
                if (original != null) {
                    // Create square crop from center
                    int size = Math.min(original.getWidth(), original.getHeight());
                    int x = (original.getWidth() - size) / 2;
                    int y = (original.getHeight() - size) / 2;
                    BufferedImage cropped = original.getSubimage(x, y, size, size);
                    
                    // Scale to desired size
                    Image scaled = cropped.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaled);
                    imageCache.put(cacheKey, icon);
                    return icon;
                }
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + filename + " - " + e.getMessage());
        }
        
        imageCache.put(cacheKey, null);
        return null;
    }

    private void preloadImages() {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                for (List<MenuItem> items : menuData.values()) {
                    for (MenuItem item : items) {
                        if (item.imageFile != null) {
                            loadLocalImage(item.imageFile, 90, 90);
                        }
                    }
                }
                return null;
            }
            @Override
            protected void process(List<String> chunks) {
                if (menuItemsPanel != null) {
                    menuItemsPanel.revalidate();
                    menuItemsPanel.repaint();
                }
            }
        };
        worker.execute();
    }

    // ─── Top bar ──────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_PANEL);
        bar.setBorder(new MatteBorder(0, 0, 1, 0, DIVIDER));
        bar.setPreferredSize(new Dimension(0, 56));

        JLabel logo = new JLabel("  🌿 Omega Gardens Hotel POS");
        logo.setFont(new Font("SansSerif", Font.BOLD, 16));
        logo.setForeground(TEXT_PRIMARY);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        center.setOpaque(false);
        JLabel tLbl = new JLabel("Table:");
        tLbl.setForeground(TEXT_SECONDARY);
        tLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tableLabel = new JLabel("T" + tableNumber);
        tableLabel.setForeground(ACCENT_BLUE);
        tableLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        JButton prev = iconBtn("◀"), next = iconBtn("▶");
        prev.addActionListener(e -> changeTable(-1));
        next.addActionListener(e -> changeTable(+1));

        orderNumLabel = new JLabel("Order #" + orderCounter);
        orderNumLabel.setForeground(TEXT_SECONDARY);
        orderNumLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        center.add(tLbl); center.add(prev); center.add(tableLabel); center.add(next);
        center.add(new JSeparator(JSeparator.VERTICAL));
        center.add(orderNumLabel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        right.setOpaque(false);
        JLabel clock = new JLabel();
        clock.setForeground(TEXT_SECONDARY);
        clock.setFont(new Font("SansSerif", Font.PLAIN, 13));
        javax.swing.Timer t = new javax.swing.Timer(1000,
                e -> clock.setText(new SimpleDateFormat("HH:mm:ss  dd MMM yyyy").format(new Date())));
        t.start();
        t.getActionListeners()[0].actionPerformed(null);

        JButton newOrder = roundBtn("＋ New Order", ACCENT_GREEN, Color.WHITE);
        newOrder.addActionListener(e -> newOrder());
        right.add(clock); right.add(newOrder);

        bar.add(logo, BorderLayout.WEST);
        bar.add(center, BorderLayout.CENTER);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ─── Centre split pane ────────────────────────────────────────────────────
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildMenuPanel(), buildCheckPanel());
        split.setDividerLocation(740);
        split.setDividerSize(4);
        split.setBackground(DIVIDER);
        split.setBorder(null);
        return split;
    }

    // ─── Menu panel ───────────────────────────────────────────────────────────
    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setBackground(BG_DARK);
        searchRow.setBorder(new EmptyBorder(10, 12, 6, 12));
        JTextField search = new JTextField();
        styleTextField(search, "🔍  Search menu...");
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterMenu(search.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterMenu(search.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
        searchRow.add(search, BorderLayout.CENTER);

        JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 8));
        catPanel.setBackground(BG_DARK);
        catPanel.setBorder(new EmptyBorder(0, 6, 0, 6));
        String[] categories = menuData.keySet().toArray(new String[0]);
        for (int i = 0; i < categories.length; i++) {
            final String cat = categories[i];
            final Color col  = CAT_COLORS[i % CAT_COLORS.length];
            JButton btn = buildCatButton(cat, col);
            btn.addActionListener(e -> selectCategory(cat, col));
            catPanel.add(btn);
        }

        menuItemsPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        menuItemsPanel.setBackground(BG_DARK);
        menuItemsPanel.setBorder(new EmptyBorder(4, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(menuItemsPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_DARK);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_DARK);
        top.add(searchRow, BorderLayout.NORTH);
        top.add(catPanel,  BorderLayout.CENTER);
        panel.add(top,   BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        loadCategory("Burgers");
        return panel;
    }

    private JButton buildCatButton(String name, Color color) {
        JButton btn = new JButton(name);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(name.equals(currentCategory) ? Color.WHITE : TEXT_PRIMARY);
        btn.setBackground(name.equals(currentCategory) ? color : BG_CARD);
        btn.setBorder(new EmptyBorder(7, 14, 7, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("color", color);
        btn.putClientProperty("name", name);
        return btn;
    }

    private void selectCategory(String cat, Color color) {
        currentCategory = cat;
        loadCategory(cat);
        refreshCategoryButtons();
    }

    private void refreshCategoryButtons() {
        refreshCategoryButtonsIn(getContentPane());
    }

    private void refreshCategoryButtonsIn(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton b) {
                String name = (String) b.getClientProperty("name");
                Color  col  = (Color)  b.getClientProperty("color");
                if (name != null && col != null) {
                    b.setBackground(name.equals(currentCategory) ? col : BG_CARD);
                    b.setForeground(name.equals(currentCategory) ? Color.WHITE : TEXT_PRIMARY);
                    b.repaint();
                }
            }
            if (c instanceof Container sub) refreshCategoryButtonsIn(sub);
        }
    }

    private void loadCategory(String cat) {
        renderItems(menuData.getOrDefault(cat, Collections.emptyList()));
    }

    private void filterMenu(String text) {
        if (text == null || text.isBlank()) { loadCategory(currentCategory); return; }
        String q = text.toLowerCase();
        List<MenuItem> filtered = new ArrayList<>();
        for (List<MenuItem> items : menuData.values())
            for (MenuItem m : items)
                if (m.name.toLowerCase().contains(q))
                    filtered.add(m);
        renderItems(filtered);
    }

    private void renderItems(List<MenuItem> items) {
        menuItemsPanel.removeAll();
        for (MenuItem item : items) menuItemsPanel.add(buildItemCard(item));
        menuItemsPanel.revalidate();
        menuItemsPanel.repaint();
    }

    // ─── Item card with local image ──────────────────────────────────────────
    private JPanel buildItemCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
            new LineBorder(DIVIDER, 1, true),
            new EmptyBorder(8, 8, 8, 8)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Load image from local folder
        ImageIcon icon = loadLocalImage(item.imageFile, 90, 90);
        JLabel imageLabel;
        
        if (icon != null) {
            imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            // Fallback to emoji if image not found
            imageLabel = new JLabel(item.emoji, SwingConstants.CENTER);
            imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 48));
        }
        
        imageLabel.setPreferredSize(new Dimension(90, 90));

        JLabel name = new JLabel(item.name, SwingConstants.CENTER);
        name.setFont(new Font("SansSerif", Font.BOLD, 11));
        name.setForeground(TEXT_PRIMARY);

        String priceStr = item.price < 0
                ? String.format("-$%.2f", Math.abs(item.price))
                : String.format("$%.2f", item.price);
        JLabel price = new JLabel(priceStr, SwingConstants.CENTER);
        price.setFont(new Font("SansSerif", Font.BOLD, 12));
        price.setForeground(item.price < 0 ? ACCENT_GREEN : ACCENT_BLUE);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(name,  BorderLayout.CENTER);
        bottom.add(price, BorderLayout.SOUTH);

        card.add(imageLabel, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(50, 50, 75));
                card.setBorder(new CompoundBorder(
                    new LineBorder(ACCENT_BLUE, 1, true),
                    new EmptyBorder(8, 8, 8, 8)));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(BG_CARD);
                card.setBorder(new CompoundBorder(
                    new LineBorder(DIVIDER, 1, true),
                    new EmptyBorder(8, 8, 8, 8)));
            }
            public void mouseClicked(MouseEvent e) { addToOrder(item); }
        });

        return card;
    }

    // ─── Check / Order panel ─────────────────────────────────────────────────
    private JPanel buildCheckPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_PANEL);
        panel.setBorder(new MatteBorder(0, 1, 0, 0, DIVIDER));
        panel.setPreferredSize(new Dimension(380, 0));

        JPanel tabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabs.setBackground(BG_PANEL);
        tabs.setBorder(new MatteBorder(0, 0, 1, 0, DIVIDER));
        for (String t : new String[]{"Check", "Actions", "Guest"}) {
            JLabel tab = new JLabel(t);
            tab.setFont(new Font("SansSerif", t.equals("Check") ? Font.BOLD : Font.PLAIN, 13));
            tab.setForeground(t.equals("Check") ? ACCENT_BLUE : TEXT_SECONDARY);
            tab.setBorder(new EmptyBorder(12, 18, 12, 18));
            tabs.add(tab);
        }

        String[] cols = {"Item", "Qty", "Price"};
        orderTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        orderTable = new JTable(orderTableModel);
        styleOrderTable(orderTable);

        JScrollPane scroll = new JScrollPane(orderTable);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_PANEL);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BG_PANEL);
        footer.add(buildTotalsPanel(),  BorderLayout.NORTH);
        footer.add(buildActionButtons(), BorderLayout.CENTER);
        footer.add(buildBottomNav(),    BorderLayout.SOUTH);

        panel.add(tabs,   BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildTotalsPanel() {
        JPanel p = new JPanel(new GridLayout(3, 2, 0, 4));
        p.setBackground(BG_PANEL);
        p.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 1, 0, DIVIDER),
            new EmptyBorder(10, 16, 10, 16)));
        subtotalLabel = totalRow(p, "Subtotal");
        taxLabel      = totalRow(p, "Tax (9.5%)");
        totalLabel    = totalRow(p, "Total");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setForeground(ACCENT_BLUE);
        return p;
    }

    private JLabel totalRow(JPanel p, String label) {
        boolean isTotal = label.equals("Total");
        JLabel lbl = new JLabel(label);
        lbl.setForeground(isTotal ? TEXT_PRIMARY : TEXT_SECONDARY);
        lbl.setFont(new Font("SansSerif", isTotal ? Font.BOLD : Font.PLAIN, 13));
        JLabel val = new JLabel("$0.00", SwingConstants.RIGHT);
        val.setForeground(isTotal ? ACCENT_BLUE : TEXT_SECONDARY);
        val.setFont(new Font("SansSerif", isTotal ? Font.BOLD : Font.PLAIN, 13));
        p.add(lbl); p.add(val);
        return val;
    }

    private JPanel buildActionButtons() {
        JPanel p = new JPanel(new GridLayout(2, 2, 6, 6));
        p.setBackground(BG_PANEL);
        p.setBorder(new EmptyBorder(8, 16, 8, 16));
        JButton edit   = roundBtn("✏️ Edit",   ACCENT_BLUE,   Color.WHITE);
        JButton remove = roundBtn("🗑️ Remove", ACCENT_RED,    Color.WHITE);
        JButton voidBtn= roundBtn("🚫 Void",   ACCENT_ORANGE, Color.WHITE);
        JButton note   = roundBtn("📝 Note",   ACCENT_PURPLE, Color.WHITE);
        edit.addActionListener(e -> editSelectedItem());
        remove.addActionListener(e -> removeSelectedItem());
        p.add(edit); p.add(remove); p.add(voidBtn); p.add(note);
        return p;
    }

    private JPanel buildBottomNav() {
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        nav.setBackground(BG_PANEL);
        JButton hold  = roundBtn("Hold Order", ACCENT_ORANGE, Color.WHITE);
        JButton print = roundBtn("Print Bill",  ACCENT_PURPLE, Color.WHITE);
        JButton pay   = roundBtn("Pay",         ACCENT_GREEN,  Color.WHITE);
        hold.addActionListener(e -> holdOrder());
        print.addActionListener(e -> printBill());
        pay.addActionListener(e -> processPayment());
        nav.add(hold); nav.add(print); nav.add(pay);
        return nav;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        bar.setBackground(BG_PANEL);
        bar.setBorder(new MatteBorder(1, 0, 0, 0, DIVIDER));
        statusBar = new JLabel("✓ Ready | Table " + tableNumber + " | Items: 0");
        statusBar.setForeground(TEXT_SECONDARY);
        statusBar.setFont(new Font("SansSerif", Font.PLAIN, 11));
        bar.add(statusBar);
        return bar;
    }

    // ─── Order logic ─────────────────────────────────────────────────────────
    private void addToOrder(MenuItem item) {
        for (OrderItem oi : currentOrder) {
            if (oi.item.name.equals(item.name)) { 
                oi.quantity++; 
                updateOrderTable(); 
                updateStatusBar(); 
                
                return; 
            }
        }
        currentOrder.add(new OrderItem(item, 1));
        updateOrderTable();
        updateStatusBar();
        
    }

   

    private void updateOrderTable() {
        orderTableModel.setRowCount(0);
        for (OrderItem oi : currentOrder)
            orderTableModel.addRow(new Object[]{
                oi.item.name, oi.quantity,
                String.format("$%.2f", oi.item.price * oi.quantity)});
        updateTotals();
    }

    private void updateTotals() {
        double sub = currentOrder.stream().mapToDouble(o -> o.item.price * o.quantity).sum();
        double tax = sub * 0.095;
        subtotalLabel.setText(String.format("$%.2f", sub));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", sub + tax));
    }

    private void updateStatusBar() {
        int n = currentOrder.stream().mapToInt(o -> o.quantity).sum();
        statusBar.setText("✓ Ready | Table " + tableNumber + " | Items: " + n);
    }

    private void changeTable(int delta) {
        tableNumber = Math.max(1, tableNumber + delta);
        tableLabel.setText("T" + tableNumber);
        updateStatusBar();
    }

    private void newOrder() {
        if (JOptionPane.showConfirmDialog(this,
                "Start a new order? Current order will be cleared.", "New Order",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            currentOrder.clear();
            updateOrderTable();
            orderNumLabel.setText("Order #" + ++orderCounter);
            updateStatusBar();
        }
    }

    private void editSelectedItem() {
        int row = orderTable.getSelectedRow();
        if (row < 0 || row >= currentOrder.size()) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit."); 
            return;
        }
        String s = JOptionPane.showInputDialog(this, "Enter new quantity:", currentOrder.get(row).quantity);
        if (s == null) return;
        try {
            int qty = Integer.parseInt(s.trim());
            if (qty <= 0) currentOrder.remove(row);
            else          currentOrder.get(row).quantity = qty;
            updateOrderTable(); 
            updateStatusBar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedItem() {
        int row = orderTable.getSelectedRow();
        if (row < 0 || row >= currentOrder.size()) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove."); 
            return;
        }
        if (JOptionPane.showConfirmDialog(this,
                "Remove " + currentOrder.get(row).item.name + "?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            currentOrder.remove(row);
            updateOrderTable(); 
            updateStatusBar();
        }
    }

    private void holdOrder() {
        JOptionPane.showMessageDialog(this, "Order held for Table " + tableNumber, "Order Held",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printBill() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to print.", "Error", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════╗\n");
        sb.append("║      OMEGA GARDENS HOTEL             ║\n");
        sb.append("║         RESTAURANT BILL              ║\n");
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(String.format("║ Order #: %-30s ║\n", orderCounter));
        sb.append(String.format("║ Table:   %-30s ║\n", tableNumber));
        sb.append(String.format("║ Date:    %-30s ║\n",
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())));
        sb.append("╠══════════════════════════════════════╣\n");
        for (OrderItem oi : currentOrder)
            sb.append(String.format("║ %-20s %10s ║\n",
                    oi.item.name + " x" + oi.quantity,
                    String.format("$%.2f", oi.item.price * oi.quantity)));
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(String.format("║ Subtotal:   %26s ║\n", subtotalLabel.getText()));
        sb.append(String.format("║ Tax (9.5%%): %26s ║\n", taxLabel.getText()));
        sb.append(String.format("║ TOTAL:      %26s ║\n", totalLabel.getText()));
        sb.append("╚══════════════════════════════════════╝\n\nThank you!\n");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, sp, "Print Bill", JOptionPane.INFORMATION_MESSAGE);
    }

    private void processPayment() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to pay for.", "Error", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        String[] opts = {"Cash", "Card", "Mobile Payment"};
        int choice = JOptionPane.showOptionDialog(this,
                "Total: " + totalLabel.getText() + "\nSelect payment method:",
                "Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opts, opts[0]);
        if (choice >= 0) {
            JOptionPane.showMessageDialog(this,
                    "Payment successful via " + opts[choice] +
                    "\nAmount: " + totalLabel.getText() +
                    "\nThank you for dining at Omega Gardens Hotel!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            newOrder();
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────
    private void styleTextField(JTextField f, String ph) {
        f.setBackground(BG_CARD);
        f.setForeground(TEXT_SECONDARY);
        f.setCaretColor(ACCENT_BLUE);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(DIVIDER, 1, true), new EmptyBorder(8, 10, 8, 10)));
        f.setText(ph);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(TEXT_PRIMARY); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(ph); f.setForeground(TEXT_SECONDARY); }
            }
        });
    }

    private void styleOrderTable(JTable t) {
        t.setBackground(BG_CARD);
        t.setForeground(TEXT_PRIMARY);
        t.setGridColor(DIVIDER);
        t.setRowHeight(32);
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        t.getTableHeader().setBackground(BG_PANEL);
        t.getTableHeader().setForeground(TEXT_SECONDARY);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.setSelectionBackground(ACCENT_BLUE);
        t.setSelectionForeground(Color.WHITE);
        t.getColumnModel().getColumn(0).setPreferredWidth(150);
        t.getColumnModel().getColumn(1).setPreferredWidth(50);
        t.getColumnModel().getColumn(2).setPreferredWidth(80);
    }

    private JButton iconBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(BG_CARD);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(4, 8, 4, 8));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton roundBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ─── Entry point ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new RestaurantPOS();
        });
    }
}

// ─── Data classes ─────────────────────────────────────────────────────────────
class MenuItem {
    String name;
    double price;
    String emoji;          // fallback emoji if image not found
    String imageFile;      // filename of the image in the "images" folder

    MenuItem(String name, double price, String emoji, String imageFile) {
        this.name      = name;
        this.price     = price;
        this.emoji     = emoji;
        this.imageFile = imageFile;
    }
}

class OrderItem {
    MenuItem item;
    int quantity;
    OrderItem(MenuItem item, int quantity) { 
        this.item = item; 
        this.quantity = quantity; 
    }
}
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

    // ─── Modern Light Color Palette ─────────────────────────────────────────────
    private static final Color BG_WHITE      = new Color(255, 255, 255);
    private static final Color BG_SOFT_GRAY  = new Color(248, 249, 250);
    private static final Color BG_CARD       = new Color(255, 255, 255);
    private static final Color PRIMARY_GREEN = new Color(46, 204, 113);
    private static final Color SECONDARY_BLUE= new Color(52, 152, 219);
    private static final Color ACCENT_ORANGE = new Color(230, 126, 34);
    private static final Color ACCENT_RED    = new Color(231, 76, 60);
    private static final Color ACCENT_PURPLE = new Color(155, 89, 182);
    private static final Color TEXT_DARK     = new Color(44, 62, 80);
    private static final Color TEXT_GRAY     = new Color(127, 140, 141);
    private static final Color TEXT_LIGHT    = new Color(149, 165, 166);
    private static final Color DIVIDER       = new Color(236, 240, 241);
    private static final Color SHADOW        = new Color(0, 0, 0, 10);
    private static final Color HOVER_BLUE    = new Color(41, 128, 185);

    // ─── Category Colors ─────────────────────────────────────────────────────
    private static final Color[] CAT_COLORS = {
        new Color(231, 76, 60),   // Burgers - Red
        new Color(230, 126, 34),  // Sides - Orange
        new Color(52, 152, 219),  // Drinks - Blue
        new Color(46, 204, 113),  // Salads - Green
        new Color(155, 89, 182),  // Desserts - Purple
        new Color(241, 196, 15),  // Discounts - Yellow
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
    private JPanel cardPanel;
    private JPanel orderPanel;
    private JButton[] categoryButtons;

    // ══════════════════════════════════════════════════════════════════════════
    public RestaurantPOS() {
        buildMenuData();
        setTitle("Omega Gardens Hotel – SmartPOS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 850);
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setBackground(BG_WHITE);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Button.arc", 10);
            UIManager.put("Button.margin", new Insets(8, 16, 8, 16));
        } catch (Exception e) {}

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_WHITE);
        root.add(buildTopBar(),    BorderLayout.NORTH);
        root.add(buildCenter(),    BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);
        setContentPane(root);

        preloadImages();
        setVisible(true);
    }

    // ─── Build menu data with KSH prices ─────────────────────────────────────
    private void buildMenuData() {
        menuData.put("Burgers", Arrays.asList(
            new MenuItem("Cheeseburger",           1430.00, "🍔", "cheeseburger.jpeg", "Classic cheeseburger with melted cheese"),
            new MenuItem("The Classic",            1365.00, "🍔", "classic_burger.jpeg", "Traditional beef patty with lettuce"),
            new MenuItem("Veggie Burger",          1235.00, "🥦", "veggie_burger.jpg", "Healthy plant-based patty"),
            new MenuItem("Mushroom Burger",        1300.00, "🍄", "mushroom_burger.jpeg", "Savory mushroom and Swiss cheese"),
            new MenuItem("Paradiso Burger",        1625.00, "🌶", "spicy_burger.jpg", "Spicy jalapeño and pepper jack"),
            new MenuItem("Double Bacon",           1690.00, "🥓", "bacon_burger.jpg", "Two patties with crispy bacon"),
            new MenuItem("BBQ Burger",             1560.00, "🔥", "bbq_burger.jpg", "Smoky BBQ sauce and onion rings"),
            new MenuItem("Crispy Chicken",         1495.00, "🐔", "crispy_chicken_burger.jpg", "Fried chicken breast with coleslaw")
        ));
        
        menuData.put("Sides", Arrays.asList(
            new MenuItem("French Fries",            520.00, "🍟", "french_fries.jpg", "Crispy golden fries"),
            new MenuItem("Onion Rings",             585.00, "🧅", "onion_rings.jpg", "Beer-battered onion rings"),
            new MenuItem("Coleslaw",                390.00, "🥗", "coleslaw.jpg", "Creamy coleslaw salad"),
            new MenuItem("Garlic Bread",            455.00, "🥖", "garlic_bread.jpg", "Toasted bread with garlic butter"),
            new MenuItem("Corn on the Cob",         520.00, "🌽", "corn_on_cob.jpg", "Buttered corn with spices"),
            new MenuItem("Sweet Potato Fries",      650.00, "🍠", "sweet_potato_fries.jpg", "Healthy sweet potato alternative")
        ));
        
        menuData.put("Drinks", Arrays.asList(
            new MenuItem("Soda",                    325.00, "🥤", "soda.jpg", "Assorted sodas"),
            new MenuItem("Lemonade",                390.00, "🍋", "lemonade.jpg", "Fresh squeezed lemonade"),
            new MenuItem("Iced Tea",                325.00, "🧋", "iced_tea.jpg", "Brewed iced tea"),
            new MenuItem("Orange Juice",            520.00, "🍊", "orange_juice.jpg", "Fresh orange juice"),
            new MenuItem("Coffee",                  455.00, "☕", "coffee.jpg", "Premium Kenyan coffee"),
            new MenuItem("Milkshake",               650.00, "🥛", "milkshake.jpg", "Thick creamy milkshake")
        ));
        
        menuData.put("Salads", Arrays.asList(
            new MenuItem("Caesar Salad",            975.00, "🥗", "caesar_salad.jpg", "Romaine lettuce with Caesar dressing"),
            new MenuItem("Greek Salad",             1040.00, "🫒", "greek_salad.jpg", "Feta cheese and olives"),
            new MenuItem("Garden Salad",            845.00, "🌱", "garden_salad.jpg", "Fresh mixed vegetables"),
            new MenuItem("Nicoise Salad",           1170.00, "🐟", "nicoise_salad.jpg", "Tuna and boiled eggs"),
            new MenuItem("Caprese",                 1105.00, "🍅", "caprese_salad.jpg", "Mozzarella and tomatoes")
        ));
        
        menuData.put("Desserts", Arrays.asList(
            new MenuItem("Chocolate Cake",          845.00, "🍫", "chocolate_cake.jpg", "Rich chocolate layer cake"),
            new MenuItem("Ice Cream",               585.00, "🍦", "ice_cream.jpg", "Vanilla ice cream"),
            new MenuItem("Cheesecake",              910.00, "🍰", "cheesecake.jpg", "New York style cheesecake"),
            new MenuItem("Brownie",                 650.00, "🍫", "brownie.jpg", "Walnut brownie with fudge"),
            new MenuItem("Fruit Salad",             715.00, "🍓", "fruit_salad.jpg", "Seasonal fresh fruits")
        ));
        
        menuData.put("Discounts", Arrays.asList(
            new MenuItem("Staff Discount 10%",     -0.10, "🏷", "discount.jpg", "10% staff discount"),
            new MenuItem("Happy Hour 20%",         -0.20, "⏰", "happy_hour.jpg", "Happy hour special"),
            new MenuItem("Loyalty -200 KSH",       -200.00, "⭐", "loyalty_reward.jpg", "Loyalty program discount"),
            new MenuItem("Manager Comp",           -650.00, "🎁", "gift.jpg", "Manager complimentary")
        ));
    }

    private ImageIcon loadLocalImage(String filename, int width, int height) {
        if (filename == null) return null;
        
        String cacheKey = filename + "_" + width + "x" + height;
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        try {
            String path = "images/" + filename;
            File imageFile = new File(path);
            
            if (imageFile.exists()) {
                BufferedImage original = ImageIO.read(imageFile);
                if (original != null) {
                    int size = Math.min(original.getWidth(), original.getHeight());
                    int x = (original.getWidth() - size) / 2;
                    int y = (original.getHeight() - size) / 2;
                    BufferedImage cropped = original.getSubimage(x, y, size, size);
                    Image scaled = cropped.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaled);
                    imageCache.put(cacheKey, icon);
                    return icon;
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + filename);
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
                            loadLocalImage(item.imageFile, 120, 120);
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

    // ─── Modern Top Bar ───────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, DIVIDER));
        bar.setPreferredSize(new Dimension(0, 70));

        // Left side - Logo and Brand
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        leftPanel.setOpaque(false);
        
        JLabel logoIcon = new JLabel("🍽️");
        logoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        
        JLabel logo = new JLabel("Omega Gardens");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(TEXT_DARK);
        
        JLabel tagline = new JLabel("SmartPOS");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tagline.setForeground(PRIMARY_GREEN);
        
        JPanel brandPanel = new JPanel(new BorderLayout());
        brandPanel.setOpaque(false);
        brandPanel.add(logo, BorderLayout.NORTH);
        brandPanel.add(tagline, BorderLayout.SOUTH);
        
        leftPanel.add(logoIcon);
        leftPanel.add(brandPanel);

        // Center - Table selector with modern design
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setOpaque(false);
        
        JLabel tLbl = new JLabel("Table");
        tLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tLbl.setForeground(TEXT_GRAY);
        
        JButton prev = createIconButton("◀");
        JButton next = createIconButton("▶");
        
        tableLabel = new JLabel(String.valueOf(tableNumber));
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tableLabel.setForeground(PRIMARY_GREEN);
        
        JPanel tableCard = new JPanel(new BorderLayout(5, 0));
        tableCard.setBackground(BG_SOFT_GRAY);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DIVIDER, 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        tableCard.add(prev, BorderLayout.WEST);
        tableCard.add(tableLabel, BorderLayout.CENTER);
        tableCard.add(next, BorderLayout.EAST);
        
        orderNumLabel = new JLabel("Order #" + orderCounter);
        orderNumLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        orderNumLabel.setForeground(TEXT_GRAY);
        
        centerPanel.add(tLbl);
        centerPanel.add(tableCard);
        centerPanel.add(Box.createHorizontalStrut(20));
        centerPanel.add(orderNumLabel);

        // Right side - Actions
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        rightPanel.setOpaque(false);
        
        JLabel clock = new JLabel();
        clock.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clock.setForeground(TEXT_GRAY);
        javax.swing.Timer t = new javax.swing.Timer(1000,
                e -> clock.setText(new SimpleDateFormat("HH:mm • EEE, MMM d").format(new Date())));
        t.start();
        
        JButton newOrder = createGradientButton("+ New Order", PRIMARY_GREEN, new Color(39, 174, 96));
        newOrder.addActionListener(e -> newOrder());
        
        rightPanel.add(clock);
        rightPanel.add(newOrder);

        prev.addActionListener(e -> changeTable(-1));
        next.addActionListener(e -> changeTable(+1));

        bar.add(leftPanel, BorderLayout.WEST);
        bar.add(centerPanel, BorderLayout.CENTER);
        bar.add(rightPanel, BorderLayout.EAST);
        
        return bar;
    }

    // ─── Center Panel with Card Design ────────────────────────────────────────
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildMenuPanel(), buildCheckPanel());
        split.setDividerLocation(850);
        split.setDividerSize(2);
        split.setBackground(DIVIDER);
        split.setBorder(null);
        return split;
    }

    // ─── Modern Menu Panel ────────────────────────────────────────────────────
    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_WHITE);

        // Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_WHITE);
        searchPanel.setBorder(new EmptyBorder(20, 20, 15, 20));
        
        JTextField search = new JTextField();
        search.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        search.setBackground(BG_SOFT_GRAY);
        search.setForeground(TEXT_DARK);
        search.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DIVIDER, 1, true),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        search.putClientProperty("JTextField.placeholderText", "🔍 Search menu items...");
        
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterMenu(search.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterMenu(search.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
        
        searchPanel.add(search, BorderLayout.CENTER);

        // Category Pills
        JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        catPanel.setBackground(BG_WHITE);
        catPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        String[] categories = menuData.keySet().toArray(new String[0]);
        categoryButtons = new JButton[categories.length];
        
        for (int i = 0; i < categories.length; i++) {
            final String cat = categories[i];
            final Color color = CAT_COLORS[i % CAT_COLORS.length];
            JButton btn = createPillButton(cat, color, cat.equals(currentCategory));
            final int index = i;
            btn.addActionListener(e -> {
                currentCategory = cat;
                loadCategory(cat);
                for (int j = 0; j < categoryButtons.length; j++) {
                    updatePillButton(categoryButtons[j], categories[j], 
                        CAT_COLORS[j % CAT_COLORS.length], j == index);
                }
            });
            categoryButtons[i] = btn;
            catPanel.add(btn);
        }

        // Menu Items Grid
        menuItemsPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        menuItemsPanel.setBackground(BG_WHITE);
        menuItemsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(menuItemsPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_WHITE);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_WHITE);
        top.add(searchPanel, BorderLayout.NORTH);
        top.add(catPanel, BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        loadCategory("Burgers");
        return panel;
    }

    private JButton createPillButton(String text, Color color, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        if (isActive) {
            btn.setBackground(color);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(BG_SOFT_GRAY);
            btn.setForeground(TEXT_DARK);
        }
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void updatePillButton(JButton btn, String text, Color color, boolean isActive) {
        if (isActive) {
            btn.setBackground(color);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(BG_SOFT_GRAY);
            btn.setForeground(TEXT_DARK);
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
                if (m.name.toLowerCase().contains(q) || m.description.toLowerCase().contains(q))
                    filtered.add(m);
        renderItems(filtered);
    }

    private void renderItems(List<MenuItem> items) {
        menuItemsPanel.removeAll();
        for (MenuItem item : items) menuItemsPanel.add(createModernCard(item));
        menuItemsPanel.revalidate();
        menuItemsPanel.repaint();
    }

    // ─── Modern Item Card with hover effects ─────────────────────────────────
    private JPanel createModernCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DIVIDER, 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Image section
        ImageIcon icon = loadLocalImage(item.imageFile, 100, 100);
        JLabel imageLabel;
        
        if (icon != null) {
            imageLabel = new JLabel(icon);
        } else {
            imageLabel = new JLabel(item.emoji, SwingConstants.CENTER);
            imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(5, 8));
        contentPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(TEXT_DARK);
        
        JLabel descLabel = new JLabel(item.description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(TEXT_LIGHT);
        
        JLabel priceLabel = new JLabel(String.format("KSH %.0f", item.price));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        priceLabel.setForeground(item.price < 0 ? PRIMARY_GREEN : SECONDARY_BLUE);
        
        JButton addBtn = new JButton("+ Add");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        addBtn.setBackground(PRIMARY_GREEN);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> addToOrder(item));
        
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(priceLabel, BorderLayout.WEST);
        bottomPanel.add(addBtn, BorderLayout.EAST);
        
        contentPanel.add(nameLabel, BorderLayout.NORTH);
        contentPanel.add(descLabel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        card.add(imageLabel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY_BLUE, 2, true),
                    BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));
                card.setBackground(new Color(248, 249, 250));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(DIVIDER, 1, true),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                card.setBackground(Color.WHITE);
            }
        });
        
        return card;
    }

    // ─── Modern Check Panel ───────────────────────────────────────────────────
    private JPanel buildCheckPanel() {
        orderPanel = new JPanel(new BorderLayout(0, 0));
        orderPanel.setBackground(BG_WHITE);
        orderPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, DIVIDER));
        orderPanel.setPreferredSize(new Dimension(400, 0));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_WHITE);
        header.setBorder(new EmptyBorder(20, 20, 15, 20));
        
        JLabel orderTitle = new JLabel("Current Order");
        orderTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        orderTitle.setForeground(TEXT_DARK);
        
        JLabel itemCount = new JLabel("0 items");
        itemCount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemCount.setForeground(TEXT_LIGHT);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(orderTitle, BorderLayout.NORTH);
        titlePanel.add(itemCount, BorderLayout.SOUTH);
        
        header.add(titlePanel, BorderLayout.WEST);

        // Order Table
        String[] cols = {"Item", "Qty", "Total"};
        orderTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        orderTable = new JTable(orderTableModel);
        styleOrderTable(orderTable);

        JScrollPane scroll = new JScrollPane(orderTable);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_WHITE);

        // Footer with totals and actions
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BG_WHITE);
        footer.setBorder(new EmptyBorder(15, 20, 20, 20));
        
        // Totals Panel
        JPanel totalsPanel = new JPanel(new GridBagLayout());
        totalsPanel.setBackground(BG_SOFT_GRAY);
        totalsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DIVIDER, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        gbc.gridx = 0; gbc.gridy = 0;
        totalsPanel.add(createTotalRow("Subtotal", "KSH 0"), gbc);
        gbc.gridy = 1;
        totalsPanel.add(createTotalRow("Tax (9.5%)", "KSH 0"), gbc);
        gbc.gridy = 2;
        totalsPanel.add(createTotalRow("Total", "KSH 0", true), gbc);
        
        // Action Buttons
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        
        JButton editBtn = createOutlineButton("Edit", SECONDARY_BLUE);
        JButton removeBtn = createOutlineButton("Remove", ACCENT_RED);
        JButton clearBtn = createOutlineButton("Clear", ACCENT_ORANGE);
        
        editBtn.addActionListener(e -> editSelectedItem());
        removeBtn.addActionListener(e -> removeSelectedItem());
        clearBtn.addActionListener(e -> clearOrder());
        
        actionPanel.add(editBtn);
        actionPanel.add(removeBtn);
        actionPanel.add(clearBtn);
        
        // Payment Buttons
        JPanel paymentPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton cashBtn = createGradientButton("💵 Cash", PRIMARY_GREEN, new Color(39, 174, 96));
        JButton cardBtn = createGradientButton("💳 Card", SECONDARY_BLUE, new Color(41, 128, 185));
        JButton mpesaBtn = createGradientButton("📱 M-Pesa", ACCENT_PURPLE, new Color(142, 68, 173));
        
        cashBtn.addActionListener(e -> processPayment("Cash"));
        cardBtn.addActionListener(e -> processPayment("Card"));
        mpesaBtn.addActionListener(e -> processPayment("M-Pesa"));
        
        paymentPanel.add(cashBtn);
        paymentPanel.add(cardBtn);
        paymentPanel.add(mpesaBtn);
        
        footer.add(totalsPanel, BorderLayout.NORTH);
        footer.add(actionPanel, BorderLayout.CENTER);
        footer.add(paymentPanel, BorderLayout.SOUTH);
        
        orderPanel.add(header, BorderLayout.NORTH);
        orderPanel.add(scroll, BorderLayout.CENTER);
        orderPanel.add(footer, BorderLayout.SOUTH);
        
        // Store references for updates
        Component[] components = totalsPanel.getComponents();
        subtotalLabel = (JLabel)((JPanel)components[0]).getComponent(1);
        taxLabel = (JLabel)((JPanel)components[1]).getComponent(1);
        totalLabel = (JLabel)((JPanel)components[2]).getComponent(1);
        
        return orderPanel;
    }

    private JPanel createTotalRow(String label, String value) {
        return createTotalRow(label, value, false);
    }
    
    private JPanel createTotalRow(String label, String value, boolean isBold) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel labelLbl = new JLabel(label);
        JLabel valueLbl = new JLabel(value, SwingConstants.RIGHT);
        
        if (isBold) {
            labelLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            valueLbl.setForeground(PRIMARY_GREEN);
        } else {
            labelLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            valueLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            valueLbl.setForeground(TEXT_GRAY);
        }
        
        row.add(labelLbl, BorderLayout.WEST);
        row.add(valueLbl, BorderLayout.EAST);
        return row;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        bar.setBackground(BG_SOFT_GRAY);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, DIVIDER));
        
        statusBar = new JLabel("✓ System Ready");
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.setForeground(PRIMARY_GREEN);
        
        JLabel separator = new JLabel("|");
        separator.setForeground(TEXT_LIGHT);
        
        JLabel tableStatus = new JLabel("Table " + tableNumber + " • Order #" + orderCounter);
        tableStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableStatus.setForeground(TEXT_GRAY);
        
        bar.add(statusBar);
        bar.add(separator);
        bar.add(tableStatus);
        
        return bar;
    }

    // ─── Order Logic ─────────────────────────────────────────────────────────
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
                String.format("KSH %.0f", oi.item.price * oi.quantity)});
        updateTotals();
        
        // Update item count
        int totalItems = currentOrder.stream().mapToInt(o -> o.quantity).sum();
        JPanel header = (JPanel) orderPanel.getComponent(0);
        JLabel itemCount = (JLabel) ((JPanel) header.getComponent(0)).getComponent(1);
        itemCount.setText(totalItems + (totalItems == 1 ? " item" : " items"));
    }

    private void updateTotals() {
        double sub = currentOrder.stream().mapToDouble(o -> o.item.price * o.quantity).sum();
        double tax = sub * 0.095;
        subtotalLabel.setText(String.format("KSH %.0f", sub));
        taxLabel.setText(String.format("KSH %.0f", tax));
        totalLabel.setText(String.format("KSH %.0f", sub + tax));
    }

    private void updateStatusBar() {
        int n = currentOrder.stream().mapToInt(o -> o.quantity).sum();
        statusBar.setText("✓ Ready • " + n + " item" + (n != 1 ? "s" : "") + " in order");
    }

    private void changeTable(int delta) {
        tableNumber = Math.max(1, tableNumber + delta);
        tableLabel.setText(String.valueOf(tableNumber));
        updateStatusBar();
    }

    private void newOrder() {
        if (JOptionPane.showConfirmDialog(this,
                "Start a fresh order? Current cart will be cleared.", "New Order",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            currentOrder.clear();
            updateOrderTable();
            orderCounter++;
            orderNumLabel.setText("Order #" + orderCounter);
            updateStatusBar();
            
            // Show success notification
            JOptionPane.showMessageDialog(this, 
                "New order created! Table " + tableNumber + " is ready.",
                "Order Started", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearOrder() {
        if (!currentOrder.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Clear entire order?", "Clear Order",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                currentOrder.clear();
                updateOrderTable();
                updateStatusBar();
            }
        }
    }

    private void editSelectedItem() {
        int row = orderTable.getSelectedRow();
        if (row < 0 || row >= currentOrder.size()) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "No Selection", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String s = JOptionPane.showInputDialog(this, "Enter new quantity:", currentOrder.get(row).quantity);
        if (s == null) return;
        try {
            int qty = Integer.parseInt(s.trim());
            if (qty <= 0) currentOrder.remove(row);
            else currentOrder.get(row).quantity = qty;
            updateOrderTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedItem() {
        int row = orderTable.getSelectedRow();
        if (row < 0 || row >= currentOrder.size()) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.", "No Selection", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this,
                "Remove " + currentOrder.get(row).item.name + " from order?", "Confirm Removal",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            currentOrder.remove(row);
            updateOrderTable();
        }
    }

    private void processPayment(String method) {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to process. Add items to cart first.", 
                "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String total = totalLabel.getText();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Process payment of " + total + " via " + method + "?\n\n" +
            "Customer will receive receipt via " + (method.equals("M-Pesa") ? "SMS" : "print/card"),
            "Confirm Payment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Generate receipt
            String receipt = generateReceipt(method);
            
            JTextArea receiptArea = new JTextArea(receipt);
            receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
            receiptArea.setEditable(false);
            receiptArea.setBackground(BG_WHITE);
            
            JScrollPane scrollReceipt = new JScrollPane(receiptArea);
            scrollReceipt.setPreferredSize(new Dimension(450, 500));
            
            JOptionPane.showMessageDialog(this, scrollReceipt, 
                "Payment Successful - Receipt", JOptionPane.INFORMATION_MESSAGE);
            
            // Start fresh order
            currentOrder.clear();
            updateOrderTable();
            orderCounter++;
            orderNumLabel.setText("Order #" + orderCounter);
            updateStatusBar();
        }
    }

    private String generateReceipt(String paymentMethod) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════╗\n");
        sb.append("║         OMEGA GARDENS HOTEL                ║\n");
        sb.append("║            OFFICIAL RECEIPT                ║\n");
        sb.append("╠════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Order #: %-36s ║\n", orderCounter));
        sb.append(String.format("║ Table:   %-36s ║\n", tableNumber));
        sb.append(String.format("║ Date:    %-36s ║\n", 
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        sb.append(String.format("║ Payment: %-36s ║\n", paymentMethod));
        sb.append("╠════════════════════════════════════════════╣\n");
        sb.append("║ ITEMS                                      ║\n");
        for (OrderItem oi : currentOrder) {
            String itemLine = String.format("  %s x%d", oi.item.name, oi.quantity);
            String priceLine = String.format("KSH %.0f", oi.item.price * oi.quantity);
            sb.append(String.format("║ %-28s %10s ║\n", itemLine, priceLine));
        }
        sb.append("╠════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Subtotal:                 %10s ║\n", subtotalLabel.getText()));
        sb.append(String.format("║ Tax (9.5%%):               %10s ║\n", taxLabel.getText()));
        sb.append("╠════════════════════════════════════════════╣\n");
        sb.append(String.format("║ TOTAL PAYABLE:            %10s ║\n", totalLabel.getText()));
        sb.append("╚════════════════════════════════════════════╝\n\n");
        sb.append("          Thank you for dining with us!\n");
        sb.append("               Visit Again!\n");
        if (paymentMethod.equals("M-Pesa")) {
            sb.append("\n     M-Pesa Confirmation Sent to Phone\n");
        }
        return sb.toString();
    }

    // ─── UI Helper Methods ────────────────────────────────────────────────────
    private JButton createIconButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(BG_SOFT_GRAY);
        btn.setForeground(TEXT_DARK);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createGradientButton(String text, Color color1, Color color2) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private JButton createOutlineButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(color);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(color, 1, true));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleOrderTable(JTable t) {
        t.setBackground(Color.WHITE);
        t.setForeground(TEXT_DARK);
        t.setGridColor(DIVIDER);
        t.setRowHeight(40);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.getTableHeader().setBackground(BG_SOFT_GRAY);
        t.getTableHeader().setForeground(TEXT_DARK);
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setSelectionBackground(new Color(52, 152, 219, 50));
        t.setSelectionForeground(TEXT_DARK);
        t.getColumnModel().getColumn(0).setPreferredWidth(150);
        t.getColumnModel().getColumn(1).setPreferredWidth(60);
        t.getColumnModel().getColumn(2).setPreferredWidth(100);
        t.setShowVerticalLines(false);
    }

    // ─── Entry point ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}
            new RestaurantPOS();
        });
    }
}

// ─── Data classes ─────────────────────────────────────────────────────────────
class MenuItem {
    String name;
    double price;
    String emoji;
    String imageFile;
    String description;

    MenuItem(String name, double price, String emoji, String imageFile, String description) {
        this.name = name;
        this.price = price;
        this.emoji = emoji;
        this.imageFile = imageFile;
        this.description = description;
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
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.Arrays;
    import java.util.Random;
    import java.awt.image.BufferedImage;
    import java.io.ByteArrayInputStream;
    import java.util.Base64;
    import javax.imageio.ImageIO;

    /*
    * SudokuCat
    *
    * Rules:
    * 1. One cat in every row
    * 2. One cat in every column
    * 3. One cat in every coloured region
    * 4. Cats cannot touch, even diagonally
    */

    public class SudokuCat extends JFrame {

        // =========================================================
        // GAME VARIABLES
        // =========================================================

        private int SIZE;

        private int[][] marks;
        private int[][] regions;

        private GameCell[][] buttons;

        private JLabel messageLabel;
        private JLabel movesLabel;
        private JLabel levelLabel;
        private JLabel iqLabel;
        private JLabel timerLabel;
        private JLabel catsLabel;
        private JLabel livesLabel;

        private int moves = 0;
        private int lives = 3;

        private String difficulty = "";

        // solution[row] = column containing the cat
        private int[] currentSolution;

        private Random random = new Random();
        private Timer gameTimer;
        private int elapsedSeconds = 0;
        private BufferedImage catImage;

        // One logical starter cat is shown in every puzzle.
        private int starterRow = 0;
        private int starterCol = 0;

        // Embedded cute cat graphic, so no extra image file is needed.
        private static final String CAT_PNG_BASE64 = "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";

        // =========================================================
        // COLOURS
        // =========================================================

        private Color[] regionColors = {

            new Color(105, 185, 225),
            new Color(195, 105, 145),
            new Color(235, 190, 65),
            new Color(225, 155, 185),
            new Color(125, 190, 135),
            new Color(175, 145, 215),
            new Color(245, 155, 95),
            new Color(100, 175, 180),
            new Color(210, 150, 190),
            new Color(150, 170, 105)
        };

        // =========================================================
        // CONSTRUCTOR
        // =========================================================

        public SudokuCat() {

            setTitle("SudokuCat");

            setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            setLayout(
                new BorderLayout(8, 8)
            );

            SIZE = 4;

            marks = new int[SIZE][SIZE];
            regions = new int[SIZE][SIZE];
            buttons = new GameCell[SIZE][SIZE];

            // Show the difficulty screen first; the actual board is created
            // only after the player chooses a level.
            setSize(470, 700);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);

            SwingUtilities.invokeLater(
                () -> showDifficultyScreen()
            );
        }

        private void createTopPanel() {
            JPanel top = new JPanel();
            top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
            top.setBackground(new Color(249, 247, 244));
            top.setBorder(BorderFactory.createEmptyBorder(10, 12, 6, 12));

            // Clean, cozy header inspired by Meowdoku.
            JPanel stats = new JPanel(new BorderLayout(10, 0));
            stats.setOpaque(false);

            JButton back = roundButton("‹", Color.WHITE);
            back.setPreferredSize(new Dimension(42, 42));
            back.setToolTipText("Choose difficulty");
            back.addActionListener(e -> showDifficultyScreen());
            stats.add(back, BorderLayout.WEST);

            JPanel middle = new JPanel();
            middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
            middle.setOpaque(false);

            JLabel title = new JLabel("SudokuCat  🐾", new CatIcon(24), SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 19));
            title.setForeground(new Color(95, 83, 94));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            levelLabel = new JLabel("Level 1");
            levelLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            levelLabel.setForeground(new Color(145, 127, 138));
            levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            middle.add(title);
            middle.add(Box.createVerticalStrut(1));
            middle.add(levelLabel);
            stats.add(middle, BorderLayout.CENTER);

            JButton settings = roundButton("⚙", Color.WHITE);
            settings.setPreferredSize(new Dimension(42, 42));
            settings.setToolTipText("Choose difficulty");
            settings.addActionListener(e -> showDifficultyScreen());
            stats.add(settings, BorderLayout.EAST);

            top.add(stats);
            top.add(Box.createVerticalStrut(8));

            JPanel status = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 0));
            status.setOpaque(false);
            catsLabel = statusPill("1 / " + SIZE, new CatIcon(18));
            timerLabel = statusPill("00:00", new ClockIcon(17));
            livesLabel = statusPill("", new HeartsIcon(lives));
            status.add(catsLabel);
            status.add(timerLabel);
            status.add(livesLabel);
            top.add(status);
            top.add(Box.createVerticalStrut(8));

            JPanel rules = new JPanel(new GridLayout(1, 3, 6, 0));
            rules.setOpaque(false);
            rules.add(ruleChip("1 cat in each\ncolor region"));
            rules.add(ruleChip("1 cat in every\nrow and column"));
            rules.add(ruleChip("Cats can't touch,\neven diagonally"));
            top.add(rules);
            top.add(Box.createVerticalStrut(5));

            JLabel tip = new JLabel(
                "Tip: start from the locked cat and use X marks to eliminate cells."
            );
            tip.setFont(new Font("Arial", Font.PLAIN, 10));
            tip.setForeground(new Color(145, 130, 140));
            tip.setHorizontalAlignment(SwingConstants.CENTER);
            tip.setAlignmentX(Component.CENTER_ALIGNMENT);
            top.add(tip);

            add(top, BorderLayout.NORTH);
        }

        private JLabel statusPill(String text, Icon icon) {
            JLabel label = new JLabel(text, icon, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setForeground(new Color(107, 93, 104));
            label.setIconTextGap(6);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 227, 222)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            return label;
        }

        private JLabel ruleChip(String text) {
            JLabel label = new JLabel("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
            label.setFont(new Font("Arial", Font.PLAIN, 11));
            label.setForeground(new Color(125, 111, 120));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(new Color(255, 250, 246));
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(239, 231, 224)),
                BorderFactory.createEmptyBorder(8, 4, 8, 4)));
            return label;
        }

        private JButton roundButton(String text, Color bg) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 21));
            button.setForeground(new Color(145, 117, 153));
            button.setBackground(bg);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(new Color(242, 235, 229)));
            button.setOpaque(true);
            return button;
        }

        private void createBoard() {
            JPanel outer = new JPanel(new BorderLayout());
            outer.setBackground(new Color(248, 246, 242));
            outer.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

            JPanel board = new JPanel(new GridLayout(SIZE, SIZE, 4, 4));
            board.setBackground(new Color(255, 255, 255));
            board.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    final int row = r, col = c;
                    GameCell cell = new GameCell(row, col);
                    buttons[r][c] = cell;
                    cell.addActionListener(e -> handleClick(row, col));
                    board.add(cell);
                }
            }
            outer.add(board, BorderLayout.CENTER);
            add(outer, BorderLayout.CENTER);

            JPanel bottom = new JPanel();
            bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
            bottom.setBackground(new Color(248, 246, 242));
            bottom.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));

            messageLabel = new JLabel("🐾 Find all the hidden cats!");
            messageLabel.setFont(new Font("Arial", Font.BOLD, 13));
            messageLabel.setForeground(new Color(120, 105, 115));
            messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            movesLabel = new JLabel("Moves: 0");
            movesLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            movesLabel.setForeground(new Color(145, 132, 140));
            movesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 7));
            actions.setOpaque(false);
            JButton restart = roundButton("↶", Color.WHITE);
            JButton newPuzzle = roundButton("⟳", Color.WHITE);
            JButton hint = roundButton("?", Color.WHITE);
            restart.setPreferredSize(new Dimension(54, 54));
            newPuzzle.setPreferredSize(new Dimension(54, 54));
            hint.setPreferredSize(new Dimension(54, 54));
            restart.setToolTipText("Restart this puzzle");
            newPuzzle.setToolTipText("Choose a difficulty");
            hint.setToolTipText("Reveal one cat");
            restart.addActionListener(e -> setupGame());
            newPuzzle.addActionListener(e -> showDifficultyScreen());
            hint.addActionListener(e -> revealHint());
            actions.add(restart);
            actions.add(newPuzzle);
            actions.add(hint);

            bottom.add(messageLabel);
            bottom.add(Box.createVerticalStrut(3));
            bottom.add(movesLabel);
            bottom.add(actions);
            add(bottom, BorderLayout.SOUTH);
        }

        private void revealHint() {
            for (int r = 0; r < SIZE; r++) {
                int c = currentSolution[r];
                if (marks[r][c] != 2) {
                    marks[r][c] = 2;
                    moves++;
                    messageLabel.setText("😺 Here is your hint! You found a cat!");
                    updateBoard();
                    if (gameWon()) showWinningTab();
                    return;
                }
            }
        }

        // =========================================================
        // DIFFICULTY SCREEN
        // =========================================================

        private void showDifficultyScreen() {

            JDialog dialog =
                new JDialog(
                    this,
                    "Choose Difficulty",
                    true
                );

            dialog.setSize(470, 700);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(false);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(255, 248, 250));
            panel.setBorder(BorderFactory.createEmptyBorder(16, 18, 14, 18));

            // ---------------------------------------------------------
            // CUTE WELCOME HEADER
            // ---------------------------------------------------------
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            header.setOpaque(false);

            JLabel catTitle = new JLabel(new CatIcon(72));
            catTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel title = new JLabel("SudokuCat  🐾", new CatIcon(28), SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 29));
            title.setForeground(new Color(92, 78, 94));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitle = new JLabel("Find the hidden cats using your logic!");
            subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
            subtitle.setForeground(new Color(145, 125, 138));
            subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel choose = new JLabel("CHOOSE YOUR PURR-FECT LEVEL");
            choose.setFont(new Font("Arial", Font.BOLD, 12));
            choose.setForeground(new Color(165, 105, 130));
            choose.setAlignmentX(Component.CENTER_ALIGNMENT);

            header.add(catTitle);
            header.add(Box.createVerticalStrut(1));
            header.add(title);

            JLabel emojiRow = new JLabel("🐾  ✨  🐾");
            emojiRow.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            emojiRow.setForeground(new Color(190, 145, 170));
            emojiRow.setAlignmentX(Component.CENTER_ALIGNMENT);
            header.add(emojiRow);

            header.add(Box.createVerticalStrut(2));
            header.add(subtitle);
            header.add(Box.createVerticalStrut(9));
            header.add(choose);

            panel.add(header);
            panel.add(Box.createVerticalStrut(12));

            // ---------------------------------------------------------
            // PLAYFUL CAT-THEMED LEVEL CARDS
            // ---------------------------------------------------------
            panel.add(createLevelCard(
                "Easy",
                "Relaxed paws",
                "A gentle start to help you get comfortable.",
                new Color(105, 185, 225),
                "LET'S PLAY",
                e -> {
                    difficulty = "Easy";
                    dialog.dispose();
                    startEasyGame();
                }
            ));

            panel.add(Box.createVerticalStrut(10));

            panel.add(createLevelCard(
                "Medium",
                "Curious whiskers",
                "A little more thinking, with clear clues to follow.",
                new Color(235, 190, 65),
                "MEOW ON",
                e -> {
                    difficulty = "Medium";
                    dialog.dispose();
                    startMediumGame();
                }
            ));

            panel.add(Box.createVerticalStrut(10));

            panel.add(createLevelCard(
                "Hard",
                "Playful paws",
                "More choices, but every cat can still be found by logic.",
                new Color(195, 105, 145),
                "CATCH CATS",
                e -> {
                    difficulty = "Hard";
                    dialog.dispose();
                    startHardGame();
                }
            ));

            panel.add(Box.createVerticalStrut(12));

            // ---------------------------------------------------------
            // HOW TO PLAY CARD
            // ---------------------------------------------------------
            JPanel howTo = new JPanel(new BorderLayout(10, 0));
            howTo.setBackground(new Color(255, 253, 251));
            howTo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(236, 224, 229)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            howTo.setAlignmentX(Component.CENTER_ALIGNMENT);
            howTo.setMaximumSize(new Dimension(430, 82));

            JLabel miniCat = new JLabel(new CatIcon(42));
            miniCat.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

            JPanel howTextPanel = new JPanel();
            howTextPanel.setLayout(new BoxLayout(howTextPanel, BoxLayout.Y_AXIS));
            howTextPanel.setOpaque(false);

            JLabel howTitle = new JLabel("🐾  HOW TO PLAY");
            howTitle.setFont(new Font("Arial", Font.BOLD, 11));
            howTitle.setForeground(new Color(120, 101, 115));

            JLabel howText = new JLabel(
                "Use rows, columns and color regions to find every cat."
            );
            howText.setFont(new Font("Arial", Font.PLAIN, 11));
            howText.setForeground(new Color(140, 126, 136));

            JLabel starterText = new JLabel(
                "Mark a box once, check it with the second click, and trust the clues."
            );
            starterText.setFont(new Font("Arial", Font.PLAIN, 10));
            starterText.setForeground(new Color(157, 139, 150));

            howTextPanel.add(howTitle);
            howTextPanel.add(Box.createVerticalStrut(4));
            howTextPanel.add(howText);
            howTextPanel.add(Box.createVerticalStrut(3));
            howTextPanel.add(starterText);

            howTo.add(miniCat, BorderLayout.WEST);
            howTo.add(howTextPanel, BorderLayout.CENTER);

            panel.add(howTo);
            panel.add(Box.createVerticalStrut(9));

            JLabel footer = new JLabel("🐾 Take your time • Think like a cat • Have fun");
            footer.setFont(new Font("Arial", Font.PLAIN, 10));
            footer.setForeground(new Color(165, 137, 151));
            footer.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(footer);

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private JPanel createLevelCard(
            String level,
            String mood,
            String description,
            Color accent,
            String buttonText,
            ActionListener action
        ) {
            JPanel card = new JPanel(new BorderLayout(10, 0));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 226, 229)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            card.setMaximumSize(new Dimension(430, 104));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel left = new JPanel(new BorderLayout());
            left.setBackground(new Color(255, 250, 247));
            left.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(242, 232, 230)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            left.setPreferredSize(new Dimension(66, 78));

            JLabel cat = new JLabel(new CatIcon(52));
            cat.setHorizontalAlignment(SwingConstants.CENTER);
            cat.setVerticalAlignment(SwingConstants.CENTER);
            left.add(cat, BorderLayout.CENTER);

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);

            JLabel levelLabel = new JLabel("  " + level, new CatIcon(24), SwingConstants.LEFT);
            levelLabel.setFont(new Font("Arial", Font.BOLD, 19));
            levelLabel.setForeground(new Color(88, 78, 88));

            JLabel moodLabel = new JLabel(mood);
            moodLabel.setFont(new Font("Arial", Font.BOLD, 11));
            moodLabel.setForeground(accent);

            JLabel descLabel = new JLabel(
                "<html><div style='width:190px'>" + description + "</div></html>"
            );
            descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            descLabel.setForeground(new Color(145, 132, 141));

            info.add(levelLabel);
            info.add(Box.createVerticalStrut(1));
            info.add(moodLabel);
            info.add(Box.createVerticalStrut(4));
            info.add(descLabel);

            JButton button = createDifficultyButton(buttonText, accent);
            button.addActionListener(action);
            button.setPreferredSize(new Dimension(112, 42));
            button.setMaximumSize(new Dimension(112, 42));
            button.setMinimumSize(new Dimension(112, 42));

            card.add(left, BorderLayout.WEST);
            card.add(info, BorderLayout.CENTER);
            card.add(button, BorderLayout.EAST);

            return card;
        }

        // =========================================================
        // DIFFICULTY BUTTON
        // =========================================================

        private JButton createDifficultyButton(
            String text,
            Color color
        ) {

            JButton button =
                new JButton(text);

            button.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    14
                )
            );

            button.setForeground(Color.WHITE);

            button.setBackground(color);

            button.setFocusPainted(false);

            button.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            button.setMaximumSize(
                new Dimension(
                    210,
                    45
                )
            );

            return button;
        }

        // =========================================================
        // EASY
        // =========================================================

        private void startEasyGame() {
            SIZE = 4;
            setupGame();
        }

        // =========================================================
        // MEDIUM
        // =========================================================

        private void startMediumGame() {
            SIZE = 6;
            setupGame();
        }

        // =========================================================
        // HARD
        // =========================================================

        private void startHardGame() {
            SIZE = 8;
            setupGame();
        }

        // =========================================================
        // SETUP GAME
        // =========================================================

        private void setupGame() {

            JPanel freshContent = new JPanel(new BorderLayout(5, 5));
            freshContent.setBackground(new Color(248, 246, 242));
            setContentPane(freshContent);

            marks =
                new int[SIZE][SIZE];

            regions =
                new int[SIZE][SIZE];

            buttons =
                new GameCell[SIZE][SIZE];

            lives = 3;
            moves = 0;
            startGameTimer();

            /*
            * Create a fixed, easy-to-solve puzzle.
            */
            createPuzzle();

            // A locked starter cat gives every level a clear logical beginning.
            marks[starterRow][starterCol] = 2;

            createTopPanel();
            createBoard();
            if (levelLabel != null) levelLabel.setText("Level " + (difficulty.equals("Easy") ? "1" : difficulty.equals("Medium") ? "2" : "3"));

            messageLabel.setText(
                difficulty +
                " mode - Start with the locked cat, then use logic!"
            );

            movesLabel.setText(
                "Moves: 0"
            );

            // Portrait layout inspired by the reference screenshots.
            if (SIZE == 4) {
                setSize(470, 700);
            } else if (SIZE == 6) {
                setSize(510, 750);
            } else {
                setSize(560, 820);
            }

            setLocationRelativeTo(null);

            getContentPane().revalidate();
            getContentPane().repaint();
            revalidate();
            repaint();
        }

        private void startGameTimer() {
            if (gameTimer != null) gameTimer.stop();
            elapsedSeconds = 0;
            if (timerLabel != null) timerLabel.setText("00:00");

            gameTimer = new Timer(1000, e -> {
                elapsedSeconds++;
                int minutes = elapsedSeconds / 60;
                int seconds = elapsedSeconds % 60;
                if (timerLabel != null) {
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                }
            });
            gameTimer.start();
        }

        private void stopGameTimer() {
            if (gameTimer != null) gameTimer.stop();
        }

        private BufferedImage getCatImage() {
            if (catImage != null) return catImage;
            try {
                byte[] bytes = Base64.getDecoder().decode(CAT_PNG_BASE64);
                catImage = ImageIO.read(new ByteArrayInputStream(bytes));
            } catch (Exception ex) {
                catImage = null;
            }
            return catImage;
        }

        // =========================================================
        // CREATE PUZZLE
        // =========================================================

        private void createPuzzle() {
            // These are curated logical boards, not random regions.
            // A small starter cat is shown, then the remaining cats can be
            // found by eliminating cells using rows, columns, regions and
            // the no-touch rule. A reflection/rotation keeps replays fresh
            // without changing the logic of the level.

            if (SIZE == 4) {
                createEasyPuzzle();
            } else if (SIZE == 6) {
                createMediumPuzzle();
            } else {
                createHardPuzzle();
            }

            shuffleRegionColours();
        }

        private void loadLogicalPuzzle(int[][] baseRegions, int[] baseSolution,
                                    int baseStarterRow, int baseStarterCol) {
            int n = baseSolution.length;
            int[][] transformed = new int[n][n];
            int[] transformedSolution = new int[n];
            int transformedStarterRow = baseStarterRow;
            int transformedStarterCol = baseStarterCol;

            int transform = random.nextInt(8);
            boolean flipRows = (transform & 1) != 0;
            boolean flipCols = (transform & 2) != 0;
            boolean transpose = (transform & 4) != 0;

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    int rr = flipRows ? n - 1 - r : r;
                    int cc = flipCols ? n - 1 - c : c;

                    if (transpose) {
                        int temp = rr;
                        rr = cc;
                        cc = temp;
                    }

                    transformed[rr][cc] = baseRegions[r][c];
                }
            }

            for (int r = 0; r < n; r++) {
                int c = baseSolution[r];
                int rr = flipRows ? n - 1 - r : r;
                int cc = flipCols ? n - 1 - c : c;

                if (transpose) {
                    int temp = rr;
                    rr = cc;
                    cc = temp;
                }
                transformedSolution[rr] = cc;
            }

            if (flipRows) transformedStarterRow = n - 1 - transformedStarterRow;
            if (flipCols) transformedStarterCol = n - 1 - transformedStarterCol;
            if (transpose) {
                int temp = transformedStarterRow;
                transformedStarterRow = transformedStarterCol;
                transformedStarterCol = temp;
            }

            regions = transformed;
            currentSolution = transformedSolution;
            starterRow = transformedStarterRow;
            starterCol = transformedStarterCol;
        }

        // =========================================================
        // EASY PUZZLE - 4 X 4
        // =========================================================

        private void createEasyPuzzle() {
            int[][] baseRegions = {
                {1, 1, 0, 0},
                {1, 1, 2, 0},
                {3, 3, 2, 0},
                {3, 3, 2, 2}
            };
            int[] baseSolution = {1, 3, 0, 2};
            loadLogicalPuzzle(baseRegions, baseSolution, 0, 1);
        }

        // =========================================================
        // MEDIUM PUZZLE - 6 X 6
        // =========================================================

        private void createMediumPuzzle() {
            int[][] baseRegions = {
                {3, 3, 3, 3, 3, 5},
                {4, 3, 3, 3, 5, 5},
                {4, 3, 0, 0, 0, 0},
                {4, 4, 4, 4, 4, 0},
                {4, 1, 1, 1, 4, 4},
                {1, 1, 1, 1, 1, 2}
            };
            int[] baseSolution = {1, 4, 2, 0, 3, 5};
            loadLogicalPuzzle(baseRegions, baseSolution, 0, 1);
        }

        // =========================================================
        // HARD PUZZLE - 8 X 8
        // =========================================================

        private void createHardPuzzle() {
            int[][] baseRegions = {
                {4, 4, 4, 3, 3, 2, 2, 2},
                {4, 5, 5, 3, 3, 2, 2, 2},
                {4, 5, 5, 3, 2, 2, 2, 2},
                {1, 1, 1, 0, 0, 2, 2, 0},
                {6, 6, 1, 0, 0, 0, 2, 0},
                {6, 6, 6, 6, 7, 0, 0, 0},
                {6, 6, 6, 7, 7, 0, 7, 7},
                {6, 6, 7, 7, 7, 7, 7, 7}
            };
            int[] baseSolution = {1, 4, 2, 0, 6, 3, 5, 7};
            loadLogicalPuzzle(baseRegions, baseSolution, 0, 1);
        }

        // =========================================================
        // SHUFFLE COLOURS
        // =========================================================

        private void shuffleRegionColours() {

            int[] map =
                new int[SIZE];

            for (int i = 0; i < SIZE; i++) {

                map[i] = i;
            }

            /*
            * Randomly change the colour assigned
            * to each region.
            */
            for (
                int i = SIZE - 1;
                i > 0;
                i--
            ) {

                int j =
                    random.nextInt(
                        i + 1
                    );

                int temp =
                    map[i];

                map[i] =
                    map[j];

                map[j] =
                    temp;
            }

            for (int r = 0; r < SIZE; r++) {

                for (int c = 0; c < SIZE; c++) {

                    regions[r][c] =
                        map[
                            regions[r][c]
                        ];
                }
            }
        }

        // =========================================================
        // CLICK HANDLER
        // =========================================================

        private void handleClick(
            int r,
            int c
        ) {

            // Already found cat
            if (
                marks[r][c] == 2
            ) {

                return;
            }

            // Already marked wrong
            if (
                marks[r][c] == 3
            ) {

                return;
            }

            // -----------------------------------------------------
            // FIRST CLICK
            // -----------------------------------------------------

            if (
                marks[r][c] == 0
            ) {

                marks[r][c] = 1;

                moves++;

                messageLabel.setText(
                    "😼 Meow... click the same box again to check it!"
                );

                updateBoard();

                return;
            }

            // -----------------------------------------------------
            // SECOND CLICK
            // -----------------------------------------------------

            if (
                marks[r][c] == 1
            ) {

                moves++;

                if (
                    isCorrectCat(r, c)
                ) {

                    marks[r][c] = 2;

                    messageLabel.setText(
                        "😸 Purrfect! You found a cat!"
                    );

                    buttons[r][c].playCatAnimation();
                    updateBoard();

                    if (
                        gameWon()
                    ) {

                        showWinningTab();
                    }

                } else {

                    marks[r][c] = 3;

                    lives--;

                    messageLabel.setText(
                        "🙀 Oops! No cat here. One life lost."
                    );

                    updateBoard();

                    if (
                        lives == 0
                    ) {

                        showGameOver();
                    }
                }
            }
        }

        // =========================================================
        // CHECK CAT
        // =========================================================

        private boolean isCorrectCat(
            int r,
            int c
        ) {

            return currentSolution[r] == c;
        }

        // =========================================================
        // CHECK WIN
        // =========================================================

        private boolean gameWon() {

            int catsFound = 0;

            for (int r = 0; r < SIZE; r++) {

                for (int c = 0; c < SIZE; c++) {

                    if (
                        marks[r][c] == 2
                    ) {

                        catsFound++;
                    }
                }
            }

            return catsFound == SIZE;
        }

        // =========================================================
        // UPDATE BOARD
        // =========================================================

        private void updateBoard() {

            for (int r = 0; r < SIZE; r++) {

                for (int c = 0; c < SIZE; c++) {

                    buttons[r][c].repaint();
                }
            }

            movesLabel.setText("Moves: " + moves);
            if (catsLabel != null) {
                int found = 0;
                for (int r = 0; r < SIZE; r++)
                    for (int c = 0; c < SIZE; c++)
                        if (marks[r][c] == 2) found++;
                catsLabel.setText(found + " / " + SIZE);
                catsLabel.setIcon(new CatIcon(18));
            }
            if (livesLabel != null) {
                livesLabel.setText("");
                livesLabel.setIcon(new HeartsIcon(lives));
            }
            repaint();
        }

        // =========================================================
        // GAME OVER
        // =========================================================

        private void showGameOver() {
            stopGameTimer();

            JDialog dialog =
                new JDialog(
                    this,
                    "SudokuCat",
                    true
                );

            dialog.setSize(
                370,
                315
            );

            dialog.setLocationRelativeTo(this);

            dialog.setResizable(false);

            JPanel panel =
                new JPanel();

            panel.setLayout(
                new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
                )
            );

            panel.setBackground(
                new Color(
                    255,
                    248,
                    250
                )
            );

            JLabel title =
                new JLabel(
                    "GAME OVER"
                );

            title.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    25
                )
            );

            title.setForeground(
                new Color(
                    195,
                    105,
                    145
                )
            );

            title.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JLabel cat =
                new JLabel(
                    new CatIcon(65)
                );

            cat.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JLabel message =
                new JLabel(
                    "Oh no! All 3 lives are gone."
                );

            message.setFont(
                new Font(
                    "Arial",
                    Font.PLAIN,
                    14
                )
            );

            message.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JButton retry =
                new JButton(
                    "RETRY"
                );

            retry.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    14
                )
            );

            retry.setForeground(Color.WHITE);

            retry.setBackground(
                new Color(
                    105,
                    185,
                    225
                )
            );

            retry.setFocusPainted(false);

            retry.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            retry.addActionListener(
                e -> {

                    dialog.dispose();

                    showDifficultyScreen();
                }
            );

            JButton exit =
                new JButton(
                    "EXIT"
                );

            exit.setFocusPainted(false);

            exit.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            exit.addActionListener(
                e -> System.exit(0)
            );

            panel.add(
                Box.createVerticalStrut(15)
            );

            panel.add(title);

            panel.add(
                Box.createVerticalStrut(5)
            );

            panel.add(cat);

            panel.add(
                Box.createVerticalStrut(3)
            );

            panel.add(message);

            panel.add(
                Box.createVerticalStrut(15)
            );

            panel.add(retry);

            panel.add(
                Box.createVerticalStrut(6)
            );

            panel.add(exit);

            dialog.add(panel);

            dialog.setVisible(true);
        }

        // =========================================================
        // WINNING TAB
        // =========================================================

        private void showWinningTab() {
            stopGameTimer();

            JDialog dialog =
                new JDialog(
                    this,
                    "SudokuCat",
                    true
                );

            dialog.setSize(
                340,
                295
            );

            dialog.setLocationRelativeTo(this);

            dialog.setResizable(false);

            JPanel panel =
                new JPanel();

            panel.setLayout(
                new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
                )
            );

            panel.setBackground(Color.WHITE);

            JLabel cat =
                new JLabel(
                    new CatIcon(60)
                );

            cat.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JLabel winText =
                new JLabel(
                    "😻 YOU WIN!"
                );

            winText.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    24
                )
            );

            winText.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JLabel levelText =
                new JLabel(
                    difficulty +
                    " mode completed!"
                );

            levelText.setFont(
                new Font(
                    "Arial",
                    Font.PLAIN,
                    13
                )
            );

            levelText.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JLabel movesText =
                new JLabel(
                    "Total moves: " + moves
                );

            movesText.setFont(
                new Font(
                    "Arial",
                    Font.PLAIN,
                    14
                )
            );

            movesText.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            JButton playAgain =
                new JButton(
                    "PLAY AGAIN"
                );

            playAgain.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    14
                )
            );

            playAgain.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            playAgain.addActionListener(
                e -> {

                    dialog.dispose();

                    showDifficultyScreen();
                }
            );

            JButton exit =
                new JButton(
                    "EXIT"
                );

            exit.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            exit.addActionListener(
                e -> System.exit(0)
            );

            panel.add(
                Box.createVerticalStrut(8)
            );

            panel.add(cat);

            panel.add(
                Box.createVerticalStrut(2)
            );

            panel.add(winText);

            panel.add(
                Box.createVerticalStrut(4)
            );

            panel.add(levelText);

            panel.add(
                Box.createVerticalStrut(3)
            );

            panel.add(movesText);

            panel.add(
                Box.createVerticalStrut(12)
            );

            panel.add(playAgain);

            panel.add(
                Box.createVerticalStrut(6)
            );

            panel.add(exit);

            dialog.add(panel);

            dialog.setVisible(true);
        }

        // =========================================================
        // GAME CELL
        // =========================================================

        class GameCell extends JButton {

            private int row;
            private int col;

            // Small "cat found" animation state.
            private Timer catAnimationTimer;
            private int catAnimationFrame = 0;
            private boolean catAnimating = false;

            GameCell(
                int row,
                int col
            ) {

                this.row = row;
                this.col = col;

                setFocusPainted(false);

                setBorderPainted(false);

                setContentAreaFilled(false);

                setPreferredSize(
                    new Dimension(
                        70,
                        70
                    )
                );
            }

            @Override
            protected void paintComponent(
                Graphics g
            ) {

                Graphics2D g2 =
                    (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                // -------------------------------------------------
                // COLOURED REGION
                // -------------------------------------------------

                g2.setColor(
                    regionColors[
                        regions[row][col]
                    ]
                );

                g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    10,
                    10
                );

                g2.setColor(new Color(255, 255, 255, 35));
                g2.fillRoundRect(
                    2, 2,
                    Math.max(1, getWidth() - 4),
                    Math.max(1, getHeight() / 3),
                    8, 8
                );

                // -------------------------------------------------
                // WHITE X
                // -------------------------------------------------

                if (
                    marks[row][col] == 1
                ) {

                    drawX(
                        g2,
                        Color.WHITE
                    );
                }

                // -------------------------------------------------
                // RED X
                // -------------------------------------------------

                if (
                    marks[row][col] == 3
                ) {

                    drawX(
                        g2,
                        Color.RED
                    );
                }

                // -------------------------------------------------
                // CAT
                // -------------------------------------------------

                if (
                    marks[row][col] == 2
                ) {

                    drawCat(g2);
                }

                g2.dispose();
            }

            // =====================================================
            // DRAW X
            // =====================================================

            private void drawX(
                Graphics2D g2,
                Color color
            ) {

                g2.setColor(color);

                int stroke =
                    Math.max(
                        3,
                        getWidth() / 18
                    );

                g2.setStroke(
                    new BasicStroke(
                        stroke
                    )
                );

                int padding =
                    getWidth() / 4;

                g2.drawLine(
                    padding,
                    padding,
                    getWidth() - padding,
                    getHeight() - padding
                );

                g2.drawLine(
                    getWidth() - padding,
                    padding,
                    padding,
                    getHeight() - padding
                );
            }

            // =====================================================
            // CUTE CAT FOUND ANIMATION
            // =====================================================

            private void playCatAnimation() {
                if (catAnimationTimer != null) {
                    catAnimationTimer.stop();
                }

                catAnimationFrame = 0;
                catAnimating = true;
                repaint();

                catAnimationTimer = new Timer(35, e -> {
                    catAnimationFrame++;

                    if (catAnimationFrame >= 22) {
                        catAnimationTimer.stop();
                        catAnimating = false;
                        catAnimationFrame = 0;
                    }

                    repaint();
                });

                catAnimationTimer.start();
            }

            // =====================================================
            // DRAW CAT
            // =====================================================

            private void drawCat(Graphics2D g2) {
                BufferedImage image = getCatImage();

                if (image == null) {
                    g2.setColor(new Color(255, 190, 100));
                    int d = Math.min(getWidth(), getHeight()) / 2;
                    int x = (getWidth() - d) / 2;
                    int y = (getHeight() - d) / 2;
                    g2.fillOval(x, y, d, d);
                    return;
                }

                int padding = Math.max(6, Math.min(getWidth(), getHeight()) / 8);
                int baseSize = Math.max(1, Math.min(getWidth(), getHeight()) - padding * 2);

                double scale = 1.0;
                double bob = 0.0;
                double angle = 0.0;

                if (catAnimating) {
                    // A soft "hop" when the cat is found: pop, bounce and wiggle.
                    double t = catAnimationFrame / 21.0;
                    double pulse = Math.sin(Math.PI * t);
                    scale = 1.0 + 0.16 * pulse;
                    bob = -7.0 * pulse;
                    angle = Math.toRadians(4.0 * Math.sin(2.0 * Math.PI * t));

                    drawSparkle(g2, getWidth() / 2 - baseSize / 2 - 2,
                        getHeight() / 2 - baseSize / 2 + 8, 5, pulse);
                    drawSparkle(g2, getWidth() / 2 + baseSize / 2 - 3,
                        getHeight() / 2 - baseSize / 2 + 13, 4, pulse);
                    drawTinyHeart(g2, getWidth() / 2 + baseSize / 3,
                        getHeight() / 2 - baseSize / 2 - 2, 5, pulse);
                }

                int drawSize = Math.max(1, (int) Math.round(baseSize * scale));
                int x = (getWidth() - drawSize) / 2;
                int y = (getHeight() - drawSize) / 2 + (int) Math.round(bob);

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                double centerX = getWidth() / 2.0;
                double centerY = y + drawSize / 2.0;
                g2.rotate(angle, centerX, centerY);
                g2.drawImage(image, x, y, drawSize, drawSize, null);

                // Restore the graphics transform for the next paint operation.
                g2.rotate(-angle, centerX, centerY);
            }

            private void drawSparkle(Graphics2D g2, int cx, int cy, int size, double alpha) {
                int a = Math.max(0, Math.min(220, (int) Math.round(220 * alpha)));
                g2.setColor(new Color(255, 255, 255, a));
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(cx - size, cy, cx + size, cy);
                g2.drawLine(cx, cy - size, cx, cy + size);
            }

            private void drawTinyHeart(Graphics2D g2, int cx, int cy, int size, double alpha) {
                int a = Math.max(0, Math.min(220, (int) Math.round(210 * alpha)));
                g2.setColor(new Color(255, 132, 165, a));
                Polygon heart = new Polygon();
                heart.addPoint(cx, cy + size);
                heart.addPoint(cx - size - 2, cy);
                heart.addPoint(cx - size, cy - size / 2);
                heart.addPoint(cx - size / 2, cy - size);
                heart.addPoint(cx, cy - size / 3);
                heart.addPoint(cx + size / 2, cy - size);
                heart.addPoint(cx + size, cy - size / 2);
                heart.addPoint(cx + size + 2, cy);
                g2.fillPolygon(heart);
            }
        }

        // =========================================================
        // FISH LIVES
        // =========================================================

        class FishPanel extends JPanel {

            FishPanel() {

                setPreferredSize(
                    new Dimension(
                        85,
                        35
                    )
                );

                setOpaque(false);
            }

            @Override
            protected void paintComponent(
                Graphics g
            ) {

                super.paintComponent(g);

                Graphics2D g2 =
                    (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                for (int i = 0; i < 3; i++) {

                    int x =
                        5 + i * 25;

                    drawFish(
                        g2,
                        x,
                        5,
                        i < lives
                    );
                }

                g2.dispose();
            }

            private void drawFish(
                Graphics2D g2,
                int x,
                int y,
                boolean alive
            ) {

                Color fishColor =
                    alive
                    ? new Color(
                        70,
                        160,
                        210
                    )
                    : new Color(
                        210,
                        210,
                        210
                    );

                g2.setColor(fishColor);

                // BODY
                g2.fillOval(
                    x,
                    y + 4,
                    19,
                    13
                );

                // TAIL
                Polygon tail =
                    new Polygon();

                tail.addPoint(
                    x,
                    y + 10
                );

                tail.addPoint(
                    x - 7,
                    y + 3
                );

                tail.addPoint(
                    x - 7,
                    y + 17
                );

                g2.fillPolygon(tail);

                // EYE
                if (alive) {

                    g2.setColor(Color.WHITE);

                    g2.fillOval(
                        x + 12,
                        y + 7,
                        4,
                        4
                    );

                    g2.setColor(Color.BLACK);

                    g2.fillOval(
                        x + 13,
                        y + 8,
                        2,
                        2
                    );
                }
            }
        }

        // =========================================================
        // SMALL STATUS ICONS
        // =========================================================

        class ClockIcon implements Icon {
            private int size;

            ClockIcon(int size) {
                this.size = size;
            }

            public int getIconWidth() { return size; }
            public int getIconHeight() { return size; }

            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int s = size - 2;
                int ox = x + 1;
                int oy = y + 1;
                g2.setColor(new Color(120, 105, 125));
                g2.setStroke(new BasicStroke(1.6f));
                g2.drawOval(ox, oy, s, s);
                int cx = ox + s / 2;
                int cy = oy + s / 2;
                g2.drawLine(cx, cy, cx, oy + 4);
                g2.drawLine(cx, cy, ox + s - 4, cy);
                g2.dispose();
            }
        }

        class HeartsIcon implements Icon {
            private int alive;

            HeartsIcon(int alive) {
                this.alive = Math.max(0, Math.min(3, alive));
            }

            public int getIconWidth() { return 48; }
            public int getIconHeight() { return 18; }

            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font font = new Font("Segoe UI Symbol", Font.PLAIN, 15);
                g2.setFont(font);
                for (int i = 0; i < 3; i++) {
                    g2.setColor(i < alive ? new Color(214, 86, 116) : new Color(205, 199, 201));
                    g2.drawString("♥", x + i * 16, y + 14);
                }
                g2.dispose();
            }
        }

        // =========================================================
        // CAT ICON
        // =========================================================

        class CatIcon implements Icon {

            private int size;

            CatIcon(int size) {
                this.size = size;
            }

            public int getIconWidth() {
                return size;
            }

            public int getIconHeight() {
                return size;
            }

            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                BufferedImage image = getCatImage();
                if (image != null) {
                    g2.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    );
                    g2.drawImage(image, x, y, size, size, null);
                }
                g2.dispose();
            }
        }

        // =========================================================
        // MAIN
        // =========================================================

        public static void main(
            String[] args
        ) {

            SwingUtilities.invokeLater(
                () -> new SudokuCat()
            );
        }
    }
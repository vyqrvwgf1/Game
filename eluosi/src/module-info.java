public class TetrisPanel extends JPanel{
    private final int[][] map = new int[13][23];// map[列号][行号]。真正的方块区是:21行*10列。边框(2列，1行)
    // 方块的形状：
    // 第一维代表方块类型(包括7种:S、Z、L、J、I、O、T)
    // 第二`在这里插入代码片`维代表旋转次数
    // 第三四维代表方块矩阵
    // shapes[type][turnState][i] i--> block[i/4][i%4]
    int[][][] shapes = new int[][][] {
            /*
             * 模板 { {0,0,0,0,0,0,0,0, 0,0,0,0, 0,0,0,0}, {0,0,0,0,0,0,0,0, 0,0,0,0,
             * 0,0,0,0}, {0,0,0,0,0,0,0,0, 0,0,0,0, 0,0,0,0}, {0,0,0,0,0,0,0,0, 0,0,0,0,
             * 0,0,0,0} }
             */
            // I
            { { 1, 1, 1, 1,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0 },
              { 1, 1, 1, 1,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0 } },
            // S
            { { 0, 0, 1, 1,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 1, 0,
                0, 0, 0, 0 },
              { 0, 0, 1, 1,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 1, 0,
                0, 0, 0, 0 } },
            // Z 第3行: shapes[2][2][]
            { { 1, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                1, 1, 0, 0,
                1, 0, 0, 0,
                0, 0, 0, 0 },
              { 1, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                1, 1, 0, 0,
                1, 0, 0, 0,
                0, 0, 0, 0 } },
            // J
            { { 0, 0, 1, 0,
                0, 0, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 1, 1,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 0,
                0, 1, 0, 0,
                0, 1, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 1,
                0, 0, 0, 1,
                0, 0, 0, 0,
                0, 0, 0, 0 } },
            // O
            { { 0, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 } },
            // L
            { { 0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 1,
                0, 1, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 1, 0,
                0, 0, 1, 0,
                0, 0, 1, 0,
                0, 0, 0, 0 },
              { 0, 0, 0, 1,
                0, 1, 1, 1,
                0, 0, 0, 0,
                0, 0, 0, 0 } },
            // T
            { { 0, 1, 0, 0,
                1, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                1, 1, 0, 0,
                0, 1, 0, 0,
                0, 0, 0, 0 },
              { 0, 0, 0, 0,
                1, 1, 1, 0,
                0, 1, 0, 0,
                0, 0, 0, 0 },
              { 0, 1, 0, 0,
                0, 1, 1, 0,
                0, 1, 0, 0,
                0, 0, 0, 0 } } };

    private int type;
    private int turnState;
    private int x, y;// 当前块的位置---左上角的坐标
    private int score = 0;

    TimerListener listener1 = new TimerListener();    //监听器
    Timer timer = new Timer(1000, listener1);  //定时器

    public TetrisPanel() {
        newGame();
        nextBlock();
        timer.start();
    }

    void newGame() {
        // 初始化游戏地图
        for (int i = 0; i < 12; i++) {  //列
            for (int j = 0; j < 21; j++) {  //行
                if (i == 0 || i == 11) {// 边框
                    map[i][j] = 1;  //透明度
                } else {
                    map[i][j] = 0;
                }
            }
            map[i][21] = 1;
        }
        score = 0;
    }

    void nextBlock() {
        type = (int) (Math.random() * 1000) % 7; // 总共七种类型
        turnState = (int) (Math.random() * 1000) % 4; // 总共四种转换方法

        x = 3;   //起始下落坐标
        y = 0;
        if (crash(x, y, type, turnState) == 0) {
            timer.stop();
            int op = JOptionPane.showConfirmDialog(null,
                    "Game Over!...再来一局吗?!","提示",JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                newGame();
                listener1 = new TimerListener();
                Timer timer = new Timer(1000, listener1);
                timer.start();
            } else if (op == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    void down() {
        if (crash(x, y + 1, type, turnState) == 0) {// 判断当前块往下落一格后是否和地图存在填充块完全重合---注意实参:y+1
            add(x, y, type, turnState);// 把该块加到地图---形成堆积块
            nextBlock();
        } else {
            y++;
        }
        repaint();    //刷新页面
    }

    void left() {
        if (x >= 0) {
            x -= crash(x - 1, y, type, turnState);
        }
        repaint();
    }

    void right() {
        if (x < 8) {
            x += crash(x + 1, y, type, turnState);
        }
        repaint();
    }
    void turn() {
        if (crash(x, y, type, (turnState + 1) % 4) == 1) {
            turnState = (turnState + 1) % 4;
        }
        repaint();
    }

    // 让一个块堆积，其实是把当前块中的填充块信息记录到map[][]中
    private void add(int x, int y, int type, int turnState) {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (shapes[type][turnState][a * 4 + b] == 1) {
                    map[x + b + 1][y + a] = 1;
                }
            }
        }
        tryDelLine();
    }

    // 消块
    private void tryDelLine() {
        // 从上往下，一行行依次遍历，如果某一行的map[i][j]值全是1，则把这一行消掉---上一行往下落
        for (int b = 0; b < 21; b++) {
            int c = 1;
            for (int a = 0; a < 12; a++) {
                c &= map[a][b];
            }
            if (c == 1) {// 全是1--下落一行
                score += 10;
                for (int d = b; d > 0; d--) {
                    for (int e = 0; e < 11; e++) {
                        map[e][d] = map[e][d - 1];
                    }
                }
            }
        }
    }

    private int crash(int x, int y, int blockType, int turnState) {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if ((shapes[blockType][turnState][a * 4 + b] & map[x + b + 1][y + a]) == 1) {// 和填充块或框架重合，都算碰撞
                    return 0; // 碰撞了---方块的填充块和地图中的填充块完全重合
                }
            }
        }
        return 1;// 没有碰撞
    }

    // 表现层
    @Override
    public void paint(Graphics g) {
        super.paint(g);// 清除残影

        // 画当前块
        for (int j = 0; j < 16; j++) {
            if (shapes[type][turnState][j] == 1) {
                g.setColor(Color.green);
                g.fillRect((j % 4 + x + 1) * 30, (j / 4 + y) * 30, 30, 30);
            }
        }

        // 画地图(整个游戏的方块区和边框)
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                if (map[i][j] == 1) {
                    g.setColor(Color.black);    //边框，墙体
                    g.fillRect(i * 30, j * 30, 30, 30);// 填充
                }
            }
        }
        // 显示分数，同时为版面美观，在界面上再加点东西
        // 画方块区右侧部分
        g.setColor(Color.blue);
        g.setFont(new Font("aa", Font.BOLD, 26));
        g.drawString("score : " + score, 395, 100);

        //画游戏区的线格
        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 21; j++) {
                g.setColor(Color.CYAN);
                g.drawRect(i * 30, j * 30, 30, 30);
            }
        }
    }

    class TimerListener extends KeyAdapter implements ActionListener {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    down();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    left();
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    right();
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    turn();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            down();
        }
    }
}


public class Tetris extends JFrame {

    static JButton button1 = new JButton("重来");
    static JButton button2 = new JButton("暂停");
    static JButton button3 = new JButton("退出");

    static int flag = 0;
    Tetris(){
        setTitle("俄罗斯方块");
        setVisible(true);
        setLocation(450, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 697);
        setResizable(false);
        setFocusable(true);  //表明此 Component 是否可以获得焦点
    }
    public static void main(String[] args) {
        Tetris te = new Tetris();
        TetrisPanel tp = new TetrisPanel();
        te.add(tp);

        button1.setBounds(390,300,120,40);
        button2.setBounds(390,390,120,40);
        button3.setBounds(390,480,120,40);
        button1.setFocusable(false);
        button2.setFocusable(false);
        tp.add(button1);
        tp.add(button2);
        tp.add(button3);
        tp.setLayout(null);
        te.addKeyListener(tp.listener1); // 让框架来监听键盘

        button1.addActionListener(e -> {
            if(e.getSource() == button1){
                tp.timer.stop();
                int a = JOptionPane.showConfirmDialog(null, "确定要重新开始吗？","提示",JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.YES_OPTION){
                    button2.setText("暂停");
                    tp.timer.start();
                    tp.newGame();
                    tp.nextBlock();
                }else if(a == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
        });
        button2.addActionListener(e -> {
            if(e.getSource() == button2){
                if(flag == 0){
                    button2.setText("继续游戏");
                    tp.timer.stop();
                    flag = 1;
                }else if(flag == 1){
                    button2.setText("暂停");
                    tp.timer.start();
                    flag = 0;
                }
            }
        });
        button3.addActionListener(e -> {
            if(e.getSource() == button3){
                tp.timer.stop();
                int a = JOptionPane.showConfirmDialog(null,"确定要退出游戏吗？","退出",JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
}
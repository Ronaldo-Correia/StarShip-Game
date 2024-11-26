import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Image background; // Para armazenar a imagem do plano de fundo
    private Configuracoes configuracoes; // Objeto de configurações

    public MenuPrincipal(Configuracoes configuracoes) {
        this.configuracoes = configuracoes; // Recebe as configurações como parâmetro

        // Configura a janela do menu
        setTitle("Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Carrega a imagem de fundo
        background = new ImageIcon(getClass().getResource("/resources/menuImage1.png")).getImage();

        // Criação dos botões
        JButton iniciarButton = new JButton("Iniciar Jogo");
        JButton configuracoesButton = new JButton("Configurações");
        JButton sairButton = new JButton("Sair");
        
        iniciarButton.addActionListener(e -> {
            dispose(); // Fecha o menu principal
            aplicarConfiguracoes(); // Aplica configurações
            GameLauncher.iniciarJogo(configuracoes); // Inicia o jogo
        });

        // Configuração dos botões
        iniciarButton.setFont(new Font("Arial", Font.BOLD, 16));
        configuracoesButton.setFont(new Font("Arial", Font.BOLD, 16));
        sairButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Define as ações dos botões
        iniciarButton.addActionListener(e -> {
            dispose(); // Fecha o menu
            aplicarConfiguracoes(); // Aplica as configurações
            GameLauncher.iniciarJogo(configuracoes); // Chama o método de inicialização do jogo
        });

        configuracoesButton.addActionListener(e -> {
            dispose(); // Fecha o menu
            GameLauncher.abrirConfiguracoes(configuracoes, this); // Chama o método para abrir configurações
        });

        sairButton.addActionListener(e -> System.exit(0)); // Fecha o programa

        // Criação de um painel transparente para os botões
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false); // Define o painel como transparente
            }
        };

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue()); // Adiciona espaço flexível antes dos botões
        buttonPanel.add(iniciarButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Espaço entre os botões
        buttonPanel.add(configuracoesButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(sairButton);
        buttonPanel.add(Box.createVerticalGlue()); // Adiciona espaço flexível depois dos botões

        // Ajusta o alinhamento dos botões no painel
        iniciarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        configuracoesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona o painel ao centro do frame
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
    }

    // Método para aplicar as configurações antes de iniciar o jogo
    private void aplicarConfiguracoes() {
        float volume = configuracoes.getVolume();
        configuracoes.getSoundManager().setVolume(volume);
        System.out.println("Configurações aplicadas: Volume = " + volume);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Desenha o plano de fundo
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Configuracoes configuracoes = new Configuracoes(new SoundManager());
            MenuPrincipal menu = new MenuPrincipal(configuracoes);
            menu.setVisible(true);
        });
    }
    
}

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

public class GameLauncher {
    private static Configuracoes configuracoes = new Configuracoes(new SoundManager());
    private static MenuPrincipal menuPrincipal;

    public static void main(String[] args) {
        configuracoes = new Configuracoes(new SoundManager());
        menuPrincipal = new MenuPrincipal(configuracoes); // Inicializa o menu principal
        menuPrincipal.setVisible(true);      // Exibe o menu principal
        
    }


 // Chama abrirConfiguracoes com os parâmetros necessários
    public static void iniciarJogo(Configuracoes configuracoes2) {
        SwingUtilities.invokeLater(() -> {
            // Cria uma instância do Container
            Container container = new Container(configuracoes.getSoundManager(), configuracoes);

            // Inicializa e começa o jogo
            container.iniciar();
        });
    }



    public static void abrirConfiguracoes() {
        SwingUtilities.invokeLater(() -> {
            // Correção aqui para passar os parâmetros necessários
            abrirConfiguracoes(configuracoes, menuPrincipal);
        });
    }




 // Método corrigido para aceitar os parâmetros de Configurações e MenuPrincipal
    public static void abrirConfiguracoes(Configuracoes configuracoes, MenuPrincipal menu) {
        SwingUtilities.invokeLater(() -> {
            // Passa os parâmetros necessários para ConfiguracoesUI
            ConfiguracoesUI configuracoesUI = new ConfiguracoesUI(configuracoes, menu);

            // Oculta o menu principal
            menu.setVisible(false);

            // Adiciona um WindowListener para reexibir o menu ao fechar as configurações
            configuracoesUI.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    // Reativa o menu principal ao fechar a janela de configurações
                    menu.setVisible(true);
                }
            });

            // Exibe a janela de configurações
            configuracoesUI.setVisible(true);
        });
    }






}














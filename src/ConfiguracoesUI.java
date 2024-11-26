import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfiguracoesUI extends JFrame {
    private Configuracoes configuracoes;
    private MenuPrincipal menu; // Referência ao menu principal

    public ConfiguracoesUI(Configuracoes configuracoes, MenuPrincipal menu) {
        this.configuracoes = configuracoes;
        this.menu = menu;

        setTitle("Configurações");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Exemplo: Volume
        JLabel volumeLabel = new JLabel("Volume:");
        JSlider volumeSlider = new JSlider(0, 100, (int) (configuracoes.getVolume() * 100));
        volumeSlider.addChangeListener(e -> configuracoes.setVolume(volumeSlider.getValue() / 100f));

        // Botão Salvar
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            dispose(); // Fecha a tela de configurações
            menu.setVisible(true); // Reabre o menu principal
        });

        // Adicionar componentes
        add(volumeLabel);
        add(volumeSlider);
        add(salvarButton);
    }
}


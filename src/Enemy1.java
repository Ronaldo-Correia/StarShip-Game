import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy1 {
    private int x, y;
    private int largura = 50, altura = 50;
    private int velocidade = 5;  // Velocidade do inimigo
    private Image imagem;


    public Enemy1(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/alien1.png"));
        this.largura = 100;
        this.altura = 70;
        this.imagem = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
    }

    // Atualiza a posição do inimigo (movimento para baixo)
    public void atualizar() {
        y += velocidade;
    }

    // Métodos getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public void desenhar(Graphics g) {
            g.drawImage(imagem, x, y, largura, altura, null);
        }

	    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
}
    


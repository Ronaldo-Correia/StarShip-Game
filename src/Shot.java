import java.awt.*;

public class Shot {
    private int x, y;
    private int largura = 5, altura = 10;
    private int velocidade = 10;  // Velocidade do tiro

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Atualiza a posição do tiro (move para cima)
    public void atualizar() {
        y -= velocidade;
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

    // Método para desenhar o tiro
    public void desenhar(Graphics g) {
        g.setColor(Color.RED);  // Cor do tiro
        g.fillRect(x, y, largura, altura);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);  // Largura e altura do tiro
    }
}

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Player {
    private int x, y;
    private int largura, altura;
    private int vida=10;
    private Image imagem;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        // Carrega a imagem da nave
      
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/navepng.png"));
            this.largura = 100;
            this.altura = 70;
            this.imagem = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

    }

    public void moverH(int dx) {
        this.x += dx;
    }

    public void moverV(int dy) {
        this.y += dy;
    }

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

    public int getVida() {
        return vida;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void reduzirVida() {
        if (vida > 0) {
            vida--;
        }
    }
    public void desenhar(Graphics g) {
            g.drawImage(imagem, x, y, null);
        
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

}


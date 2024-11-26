import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Container extends Canvas implements Runnable {
    private JFrame janela;
    private boolean emExecucao = false;
    private Player player;
    private ArrayList<Shot> shots;
    private ArrayList<Enemy1> inimigos;
    private Random random;
    private Image imagemFundo;
    private SoundManager soundManager;
    private long ultimoTiro = 0;
    private final int intervaloTiro = 650;  // Intervalo de recarga do tiro
    private int pontos = 0; // Contador de pontos


    public Container(SoundManager soundManager, Configuracoes config) {
        this.soundManager = soundManager;
        this.random = new Random();

        // Inicializa o jogador, tiros e inimigos
        player = new Player(300, 500);
        shots = new ArrayList<>();
        inimigos = new ArrayList<>();
        imagemFundo = new ImageIcon(getClass().getResource("/resources/spacepng.png")).getImage();

        // Configura a janela
        janela = new JFrame("Jogo 2D - Nave");
        janela.setSize(1040, 635);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.add(this);
        janela.setVisible(true);

        // Configura a música de fundo
        soundManager.playBackgroundMusic("musicaFundo.wav");

        // Adiciona os eventos de teclado
        this.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        	    int key = e.getKeyCode();
        	    if (key == KeyEvent.VK_LEFT) {
        	        player.moverH(-10);
        	    } else if (key == KeyEvent.VK_RIGHT) {
        	        player.moverH(10);
        	    } else if (key == KeyEvent.VK_UP) {
        	        player.moverV(-10);
        	    } else if (key == KeyEvent.VK_DOWN) {
        	        player.moverV(10);
        	    } else if (key == KeyEvent.VK_SPACE) {
        	        long agora = System.currentTimeMillis();
        	        if (agora - ultimoTiro >= intervaloTiro) {
        	            shots.add(new Shot(player.getX() + player.getLargura() / 2 - 2, player.getY()));
        	            soundManager.playSound("retro-laser.wav", emExecucao);
        	            ultimoTiro = agora;
        	        }
        	    } else if (key == KeyEvent.VK_R && !player.estaVivo()) {
        	        reiniciar(); // Reinicia o jogo
        	    }
        	}

            
        });
        

        // Configura foco para escutar os eventos do teclado
        this.setFocusable(true);
        this.requestFocus();
    }

    // Método para disparar
    private void atirar() {
        long agora = System.currentTimeMillis();
        if (agora - ultimoTiro >= intervaloTiro) {
            shots.add(new Shot(player.getX() + player.getLargura() / 2 - 2, player.getY()));
            soundManager.playSound("retro-laser.wav", emExecucao);
            ultimoTiro = agora;
        }
    }

    // Método de inicialização do jogo
    public synchronized void iniciar() {
        emExecucao = true;
        new Thread(this).start();
    }

    // Método para parar o jogo
    public synchronized void parar() {
        emExecucao = false;
    }

    @Override
    public void run() {
    	long ultimoInimigo = System.currentTimeMillis();
    	while (emExecucao) {
    	    long agora = System.currentTimeMillis();
    	    
    	    // Gera inimigos a cada 500 ms (2 inimigos por segundo)
    	    if (agora - ultimoInimigo >= 500) { 
    	        for (int i = 0; i < 2; i++) {  // Gera 2 inimigos por ciclo
    	            gerarInimigo();
    	        }
    	        ultimoInimigo = agora;
    	    }

    	    atualizar();
    	    desenhar();

    	    try {
    	        Thread.sleep(16);  // Aproximadamente 60 FPS
    	    } catch (InterruptedException e) {
    	        e.printStackTrace();
    	    }
    	}


    }
    // Gera inimigos com base no tempo
    private void gerarInimigo() {
        if (inimigos.size() < 50) { // Limite máximo de inimigos
            int xAleatorio = random.nextInt(getWidth() - 50);
            inimigos.add(new Enemy1(xAleatorio, -50));
        }
    }

    private void reiniciar() {
        player = new Player(300, 500); // Reinicializa o player
        inimigos.clear(); // Limpa inimigos
        shots.clear(); // Limpa tiros
        pontos = 0; // Reinicia pontos
        emExecucao = true; // Retorna o jogo para execução
        soundManager.playBackgroundMusic("musicaFundo.wav"); // Reinicia a música de fundo
    }


    // Atualiza o estado do jogo (movimentação, colisões, etc)
    private void atualizar() {
        // Atualiza os inimigos
        Iterator<Enemy1> inimigoIterator = inimigos.iterator();
        while (inimigoIterator.hasNext()) {
            Enemy1 inimigo = inimigoIterator.next();
            inimigo.atualizar();

            // Verifica colisão com o player
            if (colidiu(player.getBounds(), inimigo.getBounds())) {
                player.reduzirVida();
                inimigoIterator.remove(); // Remove o inimigo que colidiu
                continue;
            }

            // Remove inimigos que saíram da tela
            if (inimigo.getY() > getHeight()) {
                inimigoIterator.remove();
            }
        }

        // Atualiza os tiros
        Iterator<Shot> tiroIterator = shots.iterator();
        while (tiroIterator.hasNext()) {
            Shot tiro = tiroIterator.next();
            tiro.atualizar();

            // Verifica colisão do tiro com inimigos
            Iterator<Enemy1> inimigosColisao = inimigos.iterator();
            while (inimigosColisao.hasNext()) {
                Enemy1 inimigo = inimigosColisao.next();
                if (colidiu(tiro.getBounds(), inimigo.getBounds())) {
                    tiroIterator.remove();  // Remove o tiro
                    inimigosColisao.remove();  // Remove o inimigo
                    pontos++;  // Incrementa os pontos
                    break;
                }
            }

            // Remove tiros que saíram da tela
            if (tiro.getY() < 0) {
                tiroIterator.remove();
            }
        }
    }

    private boolean colidiu(Rectangle rect1, Rectangle rect2) {
        return rect1.intersects(rect2);
    }
   


    // Método para verificar colisões entre o player e um inimigo
    private boolean colidiu(Player player, Enemy1 inimigo) {
        return player.getX() < inimigo.getX() + inimigo.getLargura() &&
                player.getX() + player.getLargura() > inimigo.getX() &&
                player.getY() < inimigo.getY() + inimigo.getAltura() &&
                player.getY() + player.getAltura() > inimigo.getY();
    }

    // Método para verificar colisões entre o tiro e o inimigo
    private boolean colidiu(Shot tiro, Enemy1 inimigo) {
        return tiro.getX() < inimigo.getX() + inimigo.getLargura() &&
                tiro.getX() + 5 > inimigo.getX() &&
                tiro.getY() < inimigo.getY() + inimigo.getAltura() &&
                tiro.getY() + 10 > inimigo.getY();
    }

    public void desenhar() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); // Inicializa com 3 buffers para suavidade
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, 1040, 635);

        // Desenha o fundo
        g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), null);

        // Desenha o player (se estiver vivo)
        if (player.estaVivo()) {
            player.desenhar(g);
        }

        // Desenha inimigos
        for (Enemy1 inimigo : inimigos) {
            inimigo.desenhar(g);
        }

        // Desenha tiros
        for (Shot tiro : shots) {
            tiro.desenhar(g);
        }

        // Exibe o contador de vidas e pontos
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Vidas: " + player.getVida(), 10, 20);
        g.setColor(Color.GREEN);
        g.drawString("Pontos: " + pontos, 1040 - 150, 40);

        // Exibe mensagem de "Game Over" se o player estiver morto
        if (!player.estaVivo()) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 400, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Pressione 'R' para reiniciar", 400, 350);
        }

        g.dispose();
        bs.show();
    }

       
}

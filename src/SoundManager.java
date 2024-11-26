import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    private Clip backgroundClip; // Para a música de fundo
    private float volume = 0.5f; // Volume padrão (50%)

    // Método para tocar a música de fundo
    public void playBackgroundMusic(String fileName) {
        try {
            if (backgroundClip != null && backgroundClip.isRunning()) {
                backgroundClip.stop();
            }

            URL soundURL = getClass().getResource("/resources/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            setVolume(volume); // Define o volume atual
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduz em loop
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Método para definir o volume
    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(volume, 1)); // Garante que o volume esteja entre 0 e 1

        if (backgroundClip != null) {
            FloatControl gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = 20f * (float) Math.log10(this.volume == 0 ? 0.0001f : this.volume);
            gainControl.setValue(dB);
        }
    }

    // Método para tocar efeitos sonoros
    public void playSound(String fileName, boolean isPlaying) {
        try {
            if (!isPlaying) return;

            URL soundURL = getClass().getResource("/resources/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Define o volume para o efeito sonoro
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = 20f * (float) Math.log10(this.volume == 0 ? 0.0001f : this.volume);
            gainControl.setValue(dB);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}


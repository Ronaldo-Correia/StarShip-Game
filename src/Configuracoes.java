public class Configuracoes {
    private String resolucao;  // Exemplo: "800x600", "1024x768", etc.
    private float volume;      // Valor entre 0.0 (mudo) e 1.0 (volume máximo)
    private SoundManager soundManager; // Para ajustar o volume da música e sons

    public Configuracoes(SoundManager soundManager) {
        this.soundManager = soundManager;

        // Configurações padrão
        this.resolucao = "1024x768";
        this.volume = 0.5f; // Volume inicial padrão (50%)
    }

    // Método para obter o SoundManager
    public SoundManager getSoundManager() {
        return soundManager;
    }

    // Métodos Getters e Setters
    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(volume, 1)); // Garante que o volume esteja entre 0 e 1
        soundManager.setVolume(volume); // Ajusta o volume no SoundPlayer
    }
}

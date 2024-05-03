public class Alphabet <T> {
    private T huruf;

    public Alphabet(T huruf) {
        this.huruf = huruf;
    }

    public T getAlphabet() {
        return huruf;
    }

    public void setAlphabet(T huruf) {
        this.huruf = huruf;
    }
}
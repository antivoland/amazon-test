import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author antivoland
 */
class Chunker<VALUE> implements Closeable {
    private final Consumer<MyList<VALUE>> chunkConsumer;
    private final MyList.Factory factory;
    private final Object[] buffer;
    private int bufferIndex = 0;

    Chunker(int chunkSize, Consumer<MyList<VALUE>> chunkConsumer, MyList.Factory factory) {
        this.chunkConsumer = chunkConsumer;
        this.factory = factory;
        this.buffer = new Object[chunkSize];
    }

    void push(VALUE value) {
        buffer[bufferIndex] = value;
        bufferIndex = (bufferIndex + 1) % buffer.length;
        if (bufferIndex == 0) {
            flush();
        }
    }

    @SuppressWarnings("unchecked")
    private void flush() {
        int bufferSize = bufferIndex > 0 ? bufferIndex : buffer.length;
        Object[] flushed = new Object[bufferSize];
        System.arraycopy(buffer, 0, flushed, 0, bufferSize);
        chunkConsumer.accept(factory.create((VALUE[]) flushed));
    }

    @Override
    public void close() throws IOException {
        if (bufferIndex > 0) {
            flush();
        }
    }

    static class Builder<VALUE> {
        private Integer chunkSize;
        private Consumer<MyList<VALUE>> chunkConsumer;
        private MyList.Factory factory;

        Builder<VALUE> chunkSize(int chunkSize) {
            this.chunkSize = chunkSize;
            return this;
        }

        Builder<VALUE> chunkConsumer(Consumer<MyList<VALUE>> chunkConsumer) {
            this.chunkConsumer = chunkConsumer;
            return this;
        }

        Builder<VALUE> factory(MyList.Factory factory) {
            this.factory = factory;
            return this;
        }

        Chunker<VALUE> build() {
            if (chunkSize == null) {
                throw new NullPointerException("Chunk size must be specified");
            }
            if (chunkSize <= 0) {
                throw new IllegalArgumentException("Chunk size must be positive");
            }
            if (chunkConsumer == null) {
                throw new NullPointerException("Chunk consumer must be specified");
            }
            if (factory == null) {
                throw new NullPointerException("Factory must be specified");
            }
            return new Chunker<>(chunkSize, chunkConsumer, factory);
        }
    }
}

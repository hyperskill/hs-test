package org.hyperskill.hstest.dynamic.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public final class KotlinInput {

    private KotlinInput() { }

    private static final int BUFFER_SIZE = 32;
    private static final int LINE_SEPARATOR_MAX_LENGTH = 2;

    private static boolean tryDecode(
            CharsetDecoder decoder, ByteBuffer byteBuffer,
            CharBuffer charBuffer, boolean isEndOfStream) throws CharacterCodingException {
        final int positionBefore = charBuffer.position();
        byteBuffer.flip();
        CoderResult result = decoder.decode(byteBuffer, charBuffer, isEndOfStream);
        if (result.isError()) {
            result.throwException();
        }
        final boolean isDecoded = charBuffer.position() > positionBefore;
        if (isDecoded) {
            byteBuffer.clear();
        } else {
            byteBuffer.position(byteBuffer.limit());
            byteBuffer.limit(byteBuffer.capacity());
        }
        return isDecoded;
    }

    // copy of kotlin's readLine() written in Java
    public static String readLine() throws IOException {
        final InputStream inputStream = System.in;
        final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        final CharBuffer charBuffer = CharBuffer.allocate(LINE_SEPARATOR_MAX_LENGTH * 2);
        final StringBuilder stringBuilder = new StringBuilder();

        int read = inputStream.read();
        if (read == -1) {
            return null;
        }
        do {
            byteBuffer.put((byte) read);
            if (tryDecode(decoder, byteBuffer, charBuffer, false)) {
                final int p = charBuffer.position();
                boolean endsWithLineSeparator = p > 0 && charBuffer.get(p - 1) == '\n';
                if (endsWithLineSeparator) {
                    break;
                }
                if (charBuffer.remaining() < 2) {
                    charBuffer.flip();
                    final int limit = charBuffer.limit() - 1;
                    for (int i = 0; i < limit; i++) {
                        stringBuilder.append(charBuffer.get());
                    }
                    charBuffer.compact();
                }
            }
            read = inputStream.read();
        } while (read != -1);

        tryDecode(decoder, byteBuffer, charBuffer, true);
        decoder.reset();

        int length = charBuffer.position();
        if (length > 0 && charBuffer.get(length - 1) == '\n') {
            length--;
            if (length > 0 && charBuffer.get(length - 1) == '\r') {
                length--;
            }
        }
        charBuffer.flip();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(charBuffer.get());
        }
        return stringBuilder.toString();
    }
}

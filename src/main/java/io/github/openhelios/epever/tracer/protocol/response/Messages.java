package io.github.openhelios.epever.tracer.protocol.response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.openhelios.epever.tracer.Tracer3210ANListener;
import io.github.openhelios.epever.tracer.protocol.Checksum;
import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.FunctionId;
import io.github.openhelios.epever.tracer.protocol.Index;
import io.github.openhelios.epever.tracer.protocol.Message;

/**
 * Class to parse the bytes from the DPS-150 to create typed response classes and notifies all listeners.
 */
@SuppressWarnings("ArrayRecordComponent")
public class Messages {

  private static final Logger LOG = LoggerFactory.getLogger(Messages.class);

  private final byte[] bytes;
  private final Class<? extends Response> responseType;
  private final Set<Tracer3210ANListener> listeners;

  private int startMessageIndex;
  private int nextMessageIndex;
  private Response response;

  /**
   * Constructor.
   *
   * @param bytes The received bytes.
   * @param responseType The response type.
   * @param listeners The listeners.
   */
  public Messages(final byte[] bytes, final Class<? extends Response> responseType,
      final Set<Tracer3210ANListener> listeners) {
    this.bytes = bytes;
    this.responseType = responseType;
    this.listeners = listeners;
  }

  private boolean hasMore() {
    if (startMessageIndex >= bytes.length) {
      return false;
    }
    final byte deviceId = bytes[startMessageIndex];
    if (Message.DEFAULT_DEVICE_ID != deviceId) {
      LOG.error("expected address ID {}, but was {}", Data.hex(Message.DEFAULT_DEVICE_ID), Data.hex(deviceId));
      return false;
    }
    final byte id = bytes[startMessageIndex + Index.FUNCTION_ID.get()];
    if (FunctionId.hasErrorBit(id)) {
      nextMessageIndex = startMessageIndex + 5;
      response = new Error(Arrays.copyOfRange(bytes, startMessageIndex, nextMessageIndex));
      return true;
    }
    final FunctionId functionId = FunctionId.findById(id);
    if (null == functionId) {
      nextMessageIndex = bytes.length;
      response = new GenericResponse(Arrays.copyOfRange(bytes, startMessageIndex, nextMessageIndex));
      return true;
    }
    if (FunctionId.WRITE_COIL.equals(functionId) || FunctionId.WRITE_HOLDING_REGISTERS.equals(functionId)) {
      nextMessageIndex = startMessageIndex + 8;
      response = new Ok(Arrays.copyOfRange(bytes, startMessageIndex, nextMessageIndex));
      return true;
    }
    final int sizeIndex = startMessageIndex + Index.DATA_SIZE.get();
    if (sizeIndex >= bytes.length) {
      LOG.error("expected sizeIndex={} < bytes.length={}", sizeIndex, bytes.length);
      return false;
    }
    final int dataSize = Data.uint8(bytes[sizeIndex]);
    final int messageSize = Index.DATA.get() + dataSize + Checksum.SIZE;
    nextMessageIndex = startMessageIndex + messageSize;
    if (nextMessageIndex > bytes.length) {
      LOG.error("expected next message starts at {}, but was not inside bytes.length={}", nextMessageIndex,
          bytes.length);
      return false;
    }
    final byte[] message = Arrays.copyOfRange(bytes, startMessageIndex, nextMessageIndex);
    final String error = Checksum.check(message);
    if (null != error) {
      LOG.error(error);
      return false;
    }
    response = switch (functionId) {
      case READ_INPUT_REGISTER -> createResponse(message);
      case READ_HOLDING_REGISTER -> createResponse(message);
      default -> new GenericResponse(message);
    };
    return true;
  }

  private Response createResponse(final byte[] message) {
    Constructor<? extends Response> constructor;
    try {
      responseType.getConstructors();
      constructor = responseType.getConstructor(byte[].class);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new IllegalStateException("getting constructor for " + responseType.getSimpleName() + " failed", e);
    }
    try {
      return constructor.newInstance(message);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new IllegalStateException("creating instance failed", e);
    }
  }

  private Response next() {
    startMessageIndex = nextMessageIndex;
    return response;
  }

  /**
   * Read all bytes, creates the typed response classes and notifies the listeners.
   */
  public void readAll() {
    while (hasMore()) {
      final Response response = next();
      for (final Tracer3210ANListener listener : listeners) {
        listener.onMessage(response);
      }
    }
  }

}

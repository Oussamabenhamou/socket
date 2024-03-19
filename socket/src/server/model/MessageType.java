package server.model;

import java.io.Serializable;

public enum MessageType implements Serializable {
    TEXT,
    IMAGE,
    FILE,
    CMD
}
